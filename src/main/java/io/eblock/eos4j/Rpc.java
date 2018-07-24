package io.eblock.eos4j;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.eblock.eos4j.api.service.RpcService;
import io.eblock.eos4j.api.utils.Generator;
import io.eblock.eos4j.api.vo.Block;
import io.eblock.eos4j.api.vo.ChainInfo;
import io.eblock.eos4j.api.vo.CurrencyBalanceReq;
import io.eblock.eos4j.api.vo.TableRows;
import io.eblock.eos4j.api.vo.TableRowsReq;
import io.eblock.eos4j.api.vo.account.Account;
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
	 * @param blockNumberOrId
	 *            区块ID或者高度
	 * @return
	 */
	public Block getBlock(String blockNumberOrId) {
		return Generator.executeSync(rpcService.getBlock(Collections.singletonMap("block_num_or_id", blockNumberOrId)));
	}

	/**
	 * 获得账户信息
	 * 
	 * @param account
	 *            账户名称
	 * @return
	 */
	public Account getAccount(String account) {
		return Generator.executeSync(rpcService.getAccount(Collections.singletonMap("account_name", account)));
	}
	
	/**
	 * 
	 * 查询帐户余额
	 * @param req
	 * @return 
	 * @date 2018年7月24日
	 * @author patrick
	 */
	public List<String> getCurrencyBalance(CurrencyBalanceReq req) {
	    return Generator.executeSync(rpcService.getCurrencyBalance(req));
	}

	/**
	 * 获得table数据
	 * @param req
	 * @return
	 */
	public TableRows getTableRows(TableRowsReq req) {
		return Generator.executeSync(rpcService.getTableRows(req));
	}
	
	/**
	 * 发送请求
	 * 
	 * @param compression
	 *            压缩
	 * @param pushTransaction
	 *            交易
	 * @param signatures
	 *            签名
	 * @return
	 * @throws Exception
	 */
	public Transaction pushTransaction(String compression, Tx pushTransaction, String[] signatures) throws Exception {
		 ObjectMapper mapper = new ObjectMapper();
		 String mapJakcson = mapper.writeValueAsString(new TxRequest(compression,
		 pushTransaction, signatures));
		 System.out.println(mapJakcson);
		return Generator
				.executeSync(rpcService.pushTransaction(new TxRequest(compression, pushTransaction, signatures)));
	}

	/**
	 * 转账
	 * 
	 * @param pk
	 *            私钥
	 * @param contractAccount
	 *            合约账户
	 * @param from
	 *            从
	 * @param to
	 *            到
	 * @param quantity
	 *            币种金额
	 * @param memo
	 *            留言
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
		return pushTransaction("none", tx, new String[] { sign });
	}

	/**
	 * 创建账户
	 * 
	 * @param pk
	 *            私钥
	 * @param creator
	 *            创建者
	 * @param newAccount
	 *            新账户
	 * @param owner
	 *            公钥
	 * @param active
	 *            公钥
	 * @param buyRam
	 *            ram
	 * @return
	 * @throws Exception
	 */
	public Transaction createAccount(String pk, String creator, String newAccount, String owner, String active,
			Long buyRam) throws Exception {
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
		return pushTransaction("none", tx, new String[] { sign });
	}

	/**
	 * 创建账户
	 * 
	 * @param pk
	 *            私钥
	 * @param creator
	 *            创建者
	 * @param newAccount
	 *            新账户
	 * @param owner
	 *            公钥
	 * @param active
	 *            公钥
	 * @param buyRam
	 *            购买空间数量
	 * @param stakeNetQuantity
	 *            网络抵押
	 * @param stakeCpuQuantity
	 *            cpu抵押
	 * @param transfer
	 *            抵押资产是否转送给对方，0自己所有，1对方所有
	 * @return
	 * @throws Exception
	 */
	public Transaction createAccount(String pk, String creator, String newAccount, String owner, String active,
			Long buyRam, String stakeNetQuantity, String stakeCpuQuantity, Long transfer) throws Exception {
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
		String delData = Ese.parseDelegateData(creator, newAccount, stakeNetQuantity, stakeCpuQuantity,
				transfer.intValue());
		delAction.setData(delData);
		// reset expiration
		tx.setExpiration(dateFormatter.format(new Date(1000 * Long.parseLong(tx.getExpiration().toString()))));
		return pushTransaction("none", tx, new String[] { sign });
	}
}
