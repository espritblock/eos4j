package io.eblock.eos4j;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.eblock.eos4j.api.service.RpcService;
import io.eblock.eos4j.api.utils.Generator;
import io.eblock.eos4j.api.vo.Block;
import io.eblock.eos4j.api.vo.ChainInfo;
import io.eblock.eos4j.api.vo.RamUsage;
import io.eblock.eos4j.api.vo.account.Account;
import io.eblock.eos4j.api.vo.account.Balance;
import io.eblock.eos4j.api.vo.tablerows.Base;
import io.eblock.eos4j.api.vo.tablerows.Quote;
import io.eblock.eos4j.api.vo.tablerows.RamMarketRows;
import io.eblock.eos4j.api.vo.tablerows.RamMarketTable;
import io.eblock.eos4j.api.vo.transaction.Transaction;
import io.eblock.eos4j.api.vo.transaction.push.Tx;
import io.eblock.eos4j.api.vo.transaction.push.TxAction;
import io.eblock.eos4j.api.vo.transaction.push.TxRequest;
import io.eblock.eos4j.api.vo.transaction.push.TxSign;
import io.eblock.eos4j.ese.Action;
import io.eblock.eos4j.ese.DataParam;
import io.eblock.eos4j.ese.DataType;
import io.eblock.eos4j.ese.Ese;

public class Rpc {

    private final RpcService rpcService;

    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public Rpc(String baseUrl) {
        dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        rpcService = Generator.createService(RpcService.class, baseUrl);
    }

    public RpcService getRpcService() {
        return rpcService;
    }

    /**
     * 获得链信息
     * 
     * @return
     */
    public ChainInfo getChainInfo() {
        return Generator.executeSync(rpcService.getChainInfo());
    }

    /**
     * 获得区块信息
     * 
     * @param blockNumberOrId 区块ID或者高度
     * @return
     */
    public Block getBlock(String blockNumberOrId) {
        return Generator.executeSync(rpcService.getBlock(Collections.singletonMap("block_num_or_id", blockNumberOrId)));
    }

    /**
     * 获得账户信息
     * 
     * @param account 账户名称
     * @return
     */
    public Account getAccount(String account) {
        return Generator.executeSync(rpcService.getAccount(Collections.singletonMap("account_name", account)));
    }

    /**
     * 
     * 查询帐户余额
     * @param code 合约账户
     * @param account 查询账户
     * @param symbol 币种
     * @return 
     * @date 2018年7月26日
     * @author patrick
     */
    public List<Balance> getCurrencyBalance(String code, String account, String symbol) {

        Map<String, String> param = new LinkedHashMap<>();
        param.put("code", code);
        param.put("account", account);
        param.put("symbol", symbol);

        List<String> ss = Generator.executeSync(rpcService.getCurrencyBalance(param));

        if (ss != null && ss.size() > 0) {
            return ss.stream().map(Balance::parse).collect(Collectors.toList());
        }

        return null;
    }

    /**
     * 发送请求
     * 
     * @param compression 压缩
     * @param pushTransaction 交易
     * @param signatures 签名
     * @return
     * @throws Exception
     */
    public Transaction pushTransaction(String compression, Tx pushTransaction, String[] signatures)
        throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String mapJakcson = mapper.writeValueAsString(new TxRequest(compression, pushTransaction, signatures));
        System.out.println(mapJakcson);
        return Generator.executeSync(rpcService.pushTransaction(new TxRequest(compression, pushTransaction, signatures)));
    }

    /**
     * 转账
     * 
     * @param pk 私钥
     * @param contractAccount 合约账户
     * @param from 从
     * @param to 到
     * @param quantity 币种金额
     * @param memo 留言
     * @return
     * @throws Exception
     */
    public Transaction transfer(String pk, String contractAccount, String from, String to, String quantity, String memo)
        throws Exception {
        // get chain info
        ChainInfo info = getChainInfo();
        // get block info
        Block block = getBlock(info.getLastIrreversibleBlockNum().toString());
        // tx
        Tx tx = new Tx();
        tx.setExpiration(info.getHeadBlockTime().getTime() / 1000 + 60);
        tx.setRef_block_num(info.getLastIrreversibleBlockNum());
        tx.setRef_block_prefix(block.getRefBlockPrefix());
        tx.setNet_usage_words(0l);
        tx.setMax_cpu_usage_ms(0l);
        tx.setDelay_sec(0l);
        // actions
        List<TxAction> actions = new ArrayList<>();
        // data
        Map<String, Object> dataMap = new LinkedHashMap<>();
        dataMap.put("from", from);
        dataMap.put("to", to);
        dataMap.put("quantity", new DataParam(quantity, DataType.asset, Action.transfer).getValue());
        dataMap.put("memo", memo);
        // action
        TxAction action = new TxAction(from, contractAccount, "transfer", dataMap);
        actions.add(action);
        tx.setActions(actions);
        // sgin
        String sign = Ecc.signTransaction(pk, new TxSign(info.getChainId(), tx));
        // data parse
        String data = Ecc.parseTransferData(from, to, quantity, memo);
        // reset data
        action.setData(data);
        // reset expiration
        tx.setExpiration(dateFormatter.format(new Date(1000 * Long.parseLong(tx.getExpiration().toString()))));
        return pushTransaction("none", tx, new String[] {sign});
    }

    /**
     * 创建账户
     * 
     * @param pk 私钥
     * @param creator 创建者
     * @param newAccount 新账户
     * @param owner 公钥
     * @param active 公钥
     * @param buyRam ram
     * @return
     * @throws Exception
     */
    public Transaction createAccount(String pk, String creator, String newAccount, String owner, String active, Long buyRam)
        throws Exception {
        // get chain info
        ChainInfo info = getChainInfo();
        // get block info
        Block block = getBlock(info.getLastIrreversibleBlockNum().toString());
        // tx
        Tx tx = new Tx();
        tx.setExpiration(info.getHeadBlockTime().getTime() / 1000 + 60);
        tx.setRef_block_num(info.getLastIrreversibleBlockNum());
        tx.setRef_block_prefix(block.getRefBlockPrefix());
        tx.setNet_usage_words(0l);
        tx.setMax_cpu_usage_ms(0l);
        tx.setDelay_sec(0l);
        // actions
        List<TxAction> actions = new ArrayList<>();
        tx.setActions(actions);
        // create
        Map<String, Object> createMap = new LinkedHashMap<>();
        createMap.put("creator", creator);
        createMap.put("name", newAccount);
        createMap.put("owner", owner);
        createMap.put("active", active);
        TxAction createAction = new TxAction(creator, "eosio", "newaccount", createMap);
        actions.add(createAction);
        // buyrap
        Map<String, Object> buyMap = new LinkedHashMap<>();
        buyMap.put("payer", creator);
        buyMap.put("receiver", newAccount);
        buyMap.put("bytes", buyRam);
        TxAction buyAction = new TxAction(creator, "eosio", "buyrambytes", buyMap);
        actions.add(buyAction);
        // sgin
        String sign = Ecc.signTransaction(pk, new TxSign(info.getChainId(), tx));
        // data parse
        String accountData = Ese.parseAccountData(creator, newAccount, owner, active);
        createAction.setData(accountData);
        // data parse
        String ramData = Ese.parseBuyRamData(creator, newAccount, buyRam);
        buyAction.setData(ramData);
        // reset expiration
        tx.setExpiration(dateFormatter.format(new Date(1000 * Long.parseLong(tx.getExpiration().toString()))));
        return pushTransaction("none", tx, new String[] {sign});
    }

    /**
     * 创建账户
     * 
     * @param pk 私钥
     * @param creator 创建者
     * @param newAccount 新账户
     * @param owner 公钥
     * @param active 公钥
     * @param buyRam 购买空间数量
     * @param stakeNetQuantity 网络抵押
     * @param stakeCpuQuantity cpu抵押
     * @param transfer 抵押资产是否转送给对方，0自己所有，1对方所有
     * @return
     * @throws Exception
     */
    public Transaction createAccount(String pk, String creator, String newAccount, String owner, String active, Long buyRam, String stakeNetQuantity, String stakeCpuQuantity, Long transfer)
        throws Exception {
        // get chain info
        ChainInfo info = getChainInfo();
        // info.setChainId("cf057bbfb72640471fd910bcb67639c22df9f92470936cddc1ade0e2f2e7dc4f");
        // info.setLastIrreversibleBlockNum(22117l);
        // get block info
        Block block = getBlock(info.getLastIrreversibleBlockNum().toString());
        // block.setRefBlockPrefix(3920078619l);
        // tx
        Tx tx = new Tx();
        tx.setExpiration(info.getHeadBlockTime().getTime() / 1000 + 60);
        // tx.setExpiration(1528436078);
        tx.setRef_block_num(info.getLastIrreversibleBlockNum());
        tx.setRef_block_prefix(block.getRefBlockPrefix());
        tx.setNet_usage_words(0l);
        tx.setMax_cpu_usage_ms(0l);
        tx.setDelay_sec(0l);
        // actions
        List<TxAction> actions = new ArrayList<>();
        tx.setActions(actions);
        // create
        Map<String, Object> createMap = new LinkedHashMap<>();
        createMap.put("creator", creator);
        createMap.put("name", newAccount);
        createMap.put("owner", owner);
        createMap.put("active", active);
        TxAction createAction = new TxAction(creator, "eosio", "newaccount", createMap);
        actions.add(createAction);
        // buyrap
        Map<String, Object> buyMap = new LinkedHashMap<>();
        buyMap.put("payer", creator);
        buyMap.put("receiver", newAccount);
        buyMap.put("bytes", buyRam);
        TxAction buyAction = new TxAction(creator, "eosio", "buyrambytes", buyMap);
        actions.add(buyAction);
        // buyrap
        Map<String, Object> delMap = new LinkedHashMap<>();
        delMap.put("from", creator);
        delMap.put("receiver", newAccount);
        delMap.put("stake_net_quantity", new DataParam(stakeNetQuantity, DataType.asset, Action.delegate).getValue());
        delMap.put("stake_cpu_quantity", new DataParam(stakeCpuQuantity, DataType.asset, Action.delegate).getValue());
        delMap.put("transfer", transfer);
        TxAction delAction = new TxAction(creator, "eosio", "delegatebw", delMap);
        actions.add(delAction);
        // // sgin
        String sign = Ecc.signTransaction(pk, new TxSign(info.getChainId(), tx));
        // data parse
        String accountData = Ese.parseAccountData(creator, newAccount, owner, active);
        createAction.setData(accountData);
        // data parse
        String ramData = Ese.parseBuyRamData(creator, newAccount, buyRam);
        buyAction.setData(ramData);
        // data parse
        String delData = Ese.parseDelegateData(creator, newAccount, stakeNetQuantity, stakeCpuQuantity, transfer.intValue());
        delAction.setData(delData);
        // reset expiration
        tx.setExpiration(dateFormatter.format(new Date(1000 * Long.parseLong(tx.getExpiration().toString()))));
        return pushTransaction("none", tx, new String[] {sign});
    }

    /**
     * 获得Table Rows
     *
     * @param scope
     * @param code
     * @param table
     * @return
     */
    public RamMarketTable getRamMarketTableRows(String scope, String code, String table, String json) {
        HashMap<String, String> map = new HashMap();
        map.put("scope", scope);
        map.put("code", code);
        map.put("table", table);
        map.put("json", json);
        return Generator.executeSync(rpcService.getRamMarketTableRows(map));
    }

    /**
     * 获得 ram/eos
     *
     * @param
     * @return
     */
    public Float getRamRate() {
        RamMarketTable ramMarket = this.getRamMarketTableRows("eosio", "eosio", "rammarket", "true");
        List<RamMarketRows> rows = ramMarket.getRows();
        RamMarketRows row = rows.get(0);

        Base base = row.getBase();
        String base_balance_str = base.getBalance();
        // System.out.println("base_balance_str: " + base_balance_str);

        String[] sArray = base_balance_str.split("\\s+");
        Float base_balance = Float.parseFloat(sArray[0]);
        // System.out.println("base_balance: " + base_balance);

        Quote quote = row.getQuote();
        String quote_balance_str = quote.getBalance();
        // System.out.println("quote_balance_str: " + quote_balance_str);

        sArray = quote_balance_str.split("\\s+");
        Float quote_balance = Float.parseFloat(sArray[0]);
        // System.out.println("quote_balance: " + quote_balance);

        Float ram_rate = (quote_balance * 1000 / base_balance);
        // System.out.println("ram_rate: " + ram_rate);

        BigDecimal bg = new BigDecimal(ram_rate);
        Float f = bg.setScale(4, BigDecimal.ROUND_HALF_UP).floatValue();

        return f;
    }

    /**
     * 获得 ram 使用量
     *
     * @param
     * @return
     */
    public RamUsage getRamUsage() {
        RamMarketTable mRamMarketTable = this.getRamMarketTableRows("eosio", "eosio", "global", "true");
        List<RamMarketRows> rows = mRamMarketTable.getRows();
        RamMarketRows row = rows.get(0);
        Long max_ram_size = Long.valueOf(row.getMax_ram_size());
        Long total_ram_bytes_reserved = Long.valueOf(row.getTotal_ram_bytes_reserved());

        System.out.println("max_ram_size: " + max_ram_size + ", total_ram_bytes_reserved: " + total_ram_bytes_reserved);

        RamUsage ram_usage = new RamUsage();
        ram_usage.setMax_ram_size_kb((Long)(max_ram_size / 1024));
        ram_usage.setReserved_ram_size_kb((Long)(total_ram_bytes_reserved / 1024));

        return ram_usage;
    }

    // 购买ram
    public Transaction buyRam(String pk, String account, Long buyRam)
        throws Exception {
        // get chain info
        ChainInfo info = getChainInfo();
        // get block info
        Block block = getBlock(info.getLastIrreversibleBlockNum().toString());
        // tx
        Tx tx = new Tx();
        tx.setExpiration(info.getHeadBlockTime().getTime() / 1000 + 60);
        tx.setRef_block_num(info.getLastIrreversibleBlockNum());
        tx.setRef_block_prefix(block.getRefBlockPrefix());
        tx.setNet_usage_words(0l);
        tx.setMax_cpu_usage_ms(0l);
        tx.setDelay_sec(0l);
        // actions
        List<TxAction> actions = new ArrayList<>();
        tx.setActions(actions);
        // buyram
        Map<String, Object> buyMap = new LinkedHashMap<>();
        buyMap.put("payer", account);
        buyMap.put("receiver", account);
        buyMap.put("bytes", buyRam);
        TxAction buyAction = new TxAction(account, "eosio", "buyrambytes", buyMap);
        actions.add(buyAction);

        // sign
        String sign = Ecc.signTransaction(pk, new TxSign(info.getChainId(), tx));

        // data parse
        String ramData = Ese.parseBuyRamData(account, account, buyRam);
        buyAction.setData(ramData);
        System.out.println("buy ramData: " + ramData + "\n");

        // reset expiration
        tx.setExpiration(dateFormatter.format(new Date(1000 * Long.parseLong(tx.getExpiration().toString()))));
        return pushTransaction("none", tx, new String[] {sign});
    }

    // 出售ram
    public Transaction sellRam(String pk, String account, Long sellRam)
        throws Exception {
        // get chain info
        ChainInfo info = getChainInfo();
        // get block info
        Block block = getBlock(info.getLastIrreversibleBlockNum().toString());

        // tx
        Tx tx = new Tx();
        tx.setExpiration(info.getHeadBlockTime().getTime() / 1000 + 60);
        tx.setRef_block_num(info.getLastIrreversibleBlockNum());
        tx.setRef_block_prefix(block.getRefBlockPrefix());
        tx.setNet_usage_words(0l);
        tx.setMax_cpu_usage_ms(0l);
        tx.setDelay_sec(0l);

        // actions
        List<TxAction> actions = new ArrayList<>();
        tx.setActions(actions);

        // sellRam
        Map<String, Object> sellMap = new LinkedHashMap<>();
        sellMap.put("account", account);
        sellMap.put("bytes", sellRam);
        TxAction sellAction = new TxAction(account, "eosio", "sellram", sellMap);
        actions.add(sellAction);

        // sign
        String sign = Ecc.signTransaction(pk, new TxSign(info.getChainId(), tx));

        // data parse
        String ramData = Ese.parseSellRamData(account, sellRam);
        sellAction.setData(ramData);
        System.out.println("sell ramData: " + ramData + "\n");

        // reset expiration
        tx.setExpiration(dateFormatter.format(new Date(1000 * Long.parseLong(tx.getExpiration().toString()))));
        return pushTransaction("none", tx, new String[] {sign});
    }

    // 兑换cpu和net
    public Transaction delegate(String pk, String account, String stakeNetQuantity, String stakeCpuQuantity, Long transfer)
        throws Exception {
        // get chain info
        ChainInfo info = getChainInfo();
        // get block info
        Block block = getBlock(info.getLastIrreversibleBlockNum().toString());
        // tx
        Tx tx = new Tx();
        tx.setExpiration(info.getHeadBlockTime().getTime() / 1000 + 60);
        tx.setRef_block_num(info.getLastIrreversibleBlockNum());
        tx.setRef_block_prefix(block.getRefBlockPrefix());
        tx.setNet_usage_words(0l);
        tx.setMax_cpu_usage_ms(0l);
        tx.setDelay_sec(0l);
        // actions
        List<TxAction> actions = new ArrayList<>();
        tx.setActions(actions);
        // delegate
        Map<String, Object> delMap = new LinkedHashMap<>();
        delMap.put("from", account);
        delMap.put("receiver", account);
        delMap.put("stake_net_quantity", new DataParam(stakeNetQuantity, DataType.asset, Action.delegate).getValue());
        delMap.put("stake_cpu_quantity", new DataParam(stakeCpuQuantity, DataType.asset, Action.delegate).getValue());
        delMap.put("transfer", transfer);
        TxAction delAction = new TxAction(account, "eosio", "delegatebw", delMap);
        actions.add(delAction);
        // sign
        String sign = Ecc.signTransaction(pk, new TxSign(info.getChainId(), tx));
        // data parse
        String delData = Ese.parseDelegateData(account, account, stakeNetQuantity, stakeCpuQuantity, transfer.intValue());
        delAction.setData(delData);
        // reset expiration
        tx.setExpiration(dateFormatter.format(new Date(1000 * Long.parseLong(tx.getExpiration().toString()))));
        return pushTransaction("none", tx, new String[] {sign});
    }

    // 赎回cpu和net
    public Transaction undelegate(String pk, String account, String unstakeNetQuantity, String unstakeCpuQuantity)
        throws Exception {
        // get chain info
        ChainInfo info = getChainInfo();
        // get block info
        Block block = getBlock(info.getLastIrreversibleBlockNum().toString());

        // tx
        Tx tx = new Tx();
        tx.setExpiration(info.getHeadBlockTime().getTime() / 1000 + 60);
        tx.setRef_block_num(info.getLastIrreversibleBlockNum());
        tx.setRef_block_prefix(block.getRefBlockPrefix());
        tx.setNet_usage_words(0l);
        tx.setMax_cpu_usage_ms(0l);
        tx.setDelay_sec(0l);

        // actions
        List<TxAction> actions = new ArrayList<>();
        tx.setActions(actions);

        // delegatebw
        Map<String, Object> delMap = new LinkedHashMap<>();
        delMap.put("from", account);
        delMap.put("receiver", account);
        delMap.put("unstake_net_quantity", new DataParam(unstakeNetQuantity, DataType.asset, Action.delegate).getValue());
        delMap.put("unstake_cpu_quantity", new DataParam(unstakeCpuQuantity, DataType.asset, Action.delegate).getValue());
        TxAction delAction = new TxAction(account, "eosio", "undelegatebw", delMap);
        actions.add(delAction);
        // sign
        String sign = Ecc.signTransaction(pk, new TxSign(info.getChainId(), tx));

        // data parse
        String delData = Ese.parseUnDelegateData(account, account, unstakeNetQuantity, unstakeCpuQuantity);
        delAction.setData(delData);

        // reset expiration
        tx.setExpiration(dateFormatter.format(new Date(1000 * Long.parseLong(tx.getExpiration().toString()))));
        return pushTransaction("none", tx, new String[] {sign});
    }

    // refund
    public Transaction refund(String pk, String account)
        throws Exception {
        // get chain info
        ChainInfo info = getChainInfo();
        // get block info
        Block block = getBlock(info.getLastIrreversibleBlockNum().toString());

        // tx
        Tx tx = new Tx();
        tx.setExpiration(info.getHeadBlockTime().getTime() / 1000 + 60);
        tx.setRef_block_num(info.getLastIrreversibleBlockNum());
        tx.setRef_block_prefix(block.getRefBlockPrefix());
        tx.setNet_usage_words(0l);
        tx.setMax_cpu_usage_ms(0l);
        tx.setDelay_sec(0l);

        // actions
        List<TxAction> actions = new ArrayList<>();
        tx.setActions(actions);

        // refund
        Map<String, Object> refundMap = new LinkedHashMap<>();
        refundMap.put("owner", account);
        TxAction refundAction = new TxAction(account, "eosio", "refund", refundMap);
        actions.add(refundAction);

        // sign
        String sign = Ecc.signTransaction(pk, new TxSign(info.getChainId(), tx));

        // data parse
        String refundData = Ese.parseRefundData(account);
        refundAction.setData(refundData);

        // reset expiration
        tx.setExpiration(dateFormatter.format(new Date(1000 * Long.parseLong(tx.getExpiration().toString()))));
        return pushTransaction("none", tx, new String[] {sign});
    }
}
