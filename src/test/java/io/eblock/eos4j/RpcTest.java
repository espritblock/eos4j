package io.eblock.eos4j;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.Test;
import com.alibaba.fastjson.JSONObject;
import io.eblock.eos4j.api.utils.Generator;
import io.eblock.eos4j.api.vo.RamUsage;
import io.eblock.eos4j.api.vo.account.Balance;
import io.eblock.eos4j.api.vo.tablerows.RamMarketTable;
import io.eblock.eos4j.api.vo.transaction.Transaction;
import io.eblock.eos4j.utils.JsonFormatTool;
import retrofit2.Call;

public class RpcTest {

    static Rpc rpc = new Rpc("http://192.168.1.51:8888");

    /**
     * 通过种子生成私钥
     * 
     * @date 2018年7月26日
     * @author patrick
     */
    @Test
    public void seedPrivate() {
        String pk = Ecc.seedPrivate(UUID.randomUUID().toString());
        System.out.println("private key :" + pk + "\n");
    }

    /**
     * 通过私钥生成公钥
     * 
     * @date 2018年7月26日
     * @author patrick
     */
    @Test
    public void privateToPublic() {
        //String pk = "5JVq1HBfx3FePYzC3oq28o9KVS3uHjbrH8ZKARn9KEnn4BJPPDs";//pengchao1
        String pk = "5JLYghzZSXLRYDtmCQTDi4KZWh1dEBbaBixVG7hSwuJPqoZniDU";//pengchao2
        String pu = Ecc.privateToPublic(pk);
        System.out.println("public key :" + pu + " \n ");
    }

    /**
     * 自定义数据签名
     * 
     * @date 2018年7月26日
     * @author patrick
     */
    @Test
    public void sign() {
        String pk = "5JVq1HBfx3FePYzC3oq28o9KVS3uHjbrH8ZKARn9KEnn4BJPPDs";
        String sign = Ecc.sign(pk, UUID.randomUUID().toString());
        System.out.println("sign :" + sign + " \n ");
    }

    /**
     * 转账数据序列化
     * 
     * @date 2018年7月26日
     * @author patrick
     */
    @Test
    public void parseTransferData() {
        String data = Ecc.parseTransferData("fromaccount", "toaccount", "10.0020 SYS", "测试123abcdo./,./!@##$%");
        System.out.println("seriz data :" + data);
    }

    /**
     * 创建账户数据序列化
     * 
     * @date 2018年7月26日
     * @author patrick
     */
    @Test
    public void parseAccountData() {
        String data1 = Ecc.parseAccountData("eosio", "pengchao1", "EOS6TGHAk9KtSu6VUTrG7vw8jPeLDKyDP9gdFahLzcjYixt8x2aUU", "EOS6TGHAk9KtSu6VUTrG7vw8jPeLDKyDP9gdFahLzcjYixt8x2aUU");
        System.out.println("seriz data :" + data1);
    }

    /**
     * 转账
     * 
     * @date 2018年7月26日
     * @author patrick
     */
    @Test
    public void transfer() {
        try {
            Transaction t1 = rpc.transfer("5J7UP2eBtgJiVCeCt368zWuiNdMdAoTwF4worSgHBxnMz7a5476", "maitoken", "maimarket", "pengchao2", "11.000 MAI", "");
            System.out.println("转账成功 = " + t1.getTransactionId() + " \n ");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 创建账户并且抵押
     * 
     * @date 2018年7月26日
     * @author patrick
     */
    @Test
    public void createAccountMortgage() {
        System.out.println("============= 创建账户并且抵押 ===============");
        try {
            Transaction t2 = rpc.createAccount("5JaEk4LzqDGyfQupnXuqTfMgiKbnYkaPaTfCWLhsEmsKMRkJoYG", "eosio", "pengchao1", "EOS6TGHAk9KtSu6VUTrG7vw8jPeLDKyDP9gdFahLzcjYixt8x2aUU", "EOS6TGHAk9KtSu6VUTrG7vw8jPeLDKyDP9gdFahLzcjYixt8x2aUU", 8192l, "100.00 MPC", "100.01 MPC", 0l);
            System.out.println("创建成功 = " + t2.getTransactionId() + " \n ");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 创建账户不抵押
     * 
     * @date 2018年7月26日
     * @author patrick
     */
    @Test
    public void createAccountNotMortgage() {
        System.out.println("============= 创建账户不抵押 ===============");
        try {
            Transaction t3 = rpc.createAccount("5JaEk4LzqDGyfQupnXuqTfMgiKbnYkaPaTfCWLhsEmsKMRkJoYG", "eosio", "pengchao2", "EOS7pvoCFtgwHbPfCKfd99EXB1h4NkJ1AWjpA4WMJ8WJsdzn82sfu", "EOS7pvoCFtgwHbPfCKfd99EXB1h4NkJ1AWjpA4WMJ8WJsdzn82sfu", 8192l);
            System.out.println("创建成功 = " + t3.getTransactionId() + " \n ");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 抵押
     * 
     * @date 2018年7月26日
     * @author patrick
     */
    @Test
    public void delegate() {
        System.out.println("============= 抵押 ===============");
        try {
            Transaction t2 = rpc.delegate("5JAFd3qAYoGBRMn32Yp3uKCL5YqwZqAMUeNm6AvwRQdyMQGPLdK", "zebrawallet1", "0.0001 EOS", "0.0001 EOS", 0l);
            System.out.println("抵押成功 = " + t2.getTransactionId() + " \n ");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 赎回
     * 
     * @date 2018年7月26日
     * @author patrick
     */
    @Test
    public void undelegate() {
        System.out.println("============= 赎回 ===============");
        try {
            Transaction t2 = rpc.undelegate("5JAFd3qAYoGBRMn32Yp3uKCL5YqwZqAMUeNm6AvwRQdyMQGPLdK", "zebrawallet1", "0.01 EOS", "0.01 EOS");
            System.out.println("赎回成功 = " + t2.getTransactionId() + " \n ");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 购买 ram
     * 
     * @date 2018年7月26日
     * @author patrick
     */
    @Test
    public void buyRam() {
        System.out.println("============= 购买 ram ===============");
        try {
            Transaction t2 = rpc.buyRam("5JAFd3qAYoGBRMn32Yp3uKCL5YqwZqAMUeNm6AvwRQdyMQGPLdK", "zebrawallet1", 1l);
            System.out.println("购买成功 = " + t2.getTransactionId() + " \n ");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 卖出 ram
     * 
     * @date 2018年7月26日
     * @author patrick
     */
    @Test
    public void sellRam() {
        System.out.println("============= 卖出 ram ===============");
        try {
            Transaction t2 = rpc.sellRam("5JAFd3qAYoGBRMn32Yp3uKCL5YqwZqAMUeNm6AvwRQdyMQGPLdK", "zebrawallet1", 1000l);
            System.out.println("卖出成功 = " + t2.getTransactionId() + " \n ");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * refund
     * 
     * @date 2018年7月26日
     * @author patrick
     */
    @Test
    public void refund() {
        System.out.println("============= refund ===============");
        try {
            Transaction t2 = rpc.refund("5JAFd3qAYoGBRMn32Yp3uKCL5YqwZqAMUeNm6AvwRQdyMQGPLdK", "zebrawallet1");
            System.out.println("refund成功 = " + t2.getTransactionId() + " \n ");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Get Table Rows
     * 
     * @date 2018年7月26日
     * @author patrick
     */
    @Test
    public void getRamMarketTableRows() {
        System.out.println("============= Get Table Rows ===============");
        try {
            RamMarketTable r = rpc.getRamMarketTableRows("eosio", "eosio", "rammarket", "true"); //
            System.out.println("Get Table Rows成功 = " + r.getRows().get(0).getSupply() + " \n ");
            System.out.println("Get Table Rows成功 = " + r.getRows() + " \n ");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Get RAM Rate
     * 
     * @date 2018年7月26日
     * @author patrick
     */
    @Test
    public void getRamRate() {
        System.out.println("============= Get RAM Rate ===============");
        try {
            Float f = rpc.getRamRate();
            System.out.println("Get RAM Rate 成功 = " + f + " \n ");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 获得 ram 使用量
     * 
     * @date 2018年7月26日
     * @author patrick
     */
    @Test
    public void getRamUsage() {
        System.out.println("============= 获得 ram 使用量 ===============");
        try {
            RamUsage r = rpc.getRamUsage();
            System.out.println("Get RAM Use 成功 = " + r.getMax_ram_size_kb() + "," + r.getReserved_ram_size_kb() + " \n ");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 查询帐户余额
     * 
     * @date 2018年7月26日
     * @author patrick
     */
    @Test
    public void getCurrencyBalance() {
        List<Balance> mBalanceList = rpc.getCurrencyBalance("maitoken", "pengchao2", "MAI");

        if (mBalanceList != null && mBalanceList.size() > 0) {
            for (Balance mBalance : mBalanceList) {
                System.out.println(mBalance.getAmount() + mBalance.getSymbol());
            }
        }
    }

    /**
     * 获取区块信息
     * 
     * @date 2018年7月26日
     * @author patrick
     */
    @Test
    public void getBlock() {
        println(rpc.getRpcService().getBlock(Collections.singletonMap("block_num_or_id", "1000")));
    }

    private static void println(Object object) {

        if (object instanceof Call) {
            object = Generator.executeSync((Call)object);
        }

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + object.getClass());
        System.out.println(JsonFormatTool.formatJson(JSONObject.toJSONString(object)));
    }

}