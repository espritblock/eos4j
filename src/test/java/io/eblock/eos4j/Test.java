package io.eblock.eos4j;

import io.eblock.eos4j.api.vo.RamUsage;

public class Test {

    static final String eosjs_transfer_seriz = "00f2d4142123e95d0000c85353840ccdb486010000000000045359530000000019e6b58be8af95313233616263646f2e2f2c2e2f214023232425";

    static final String eosjs_account_seriz = "0000000000ea30550002a2f164772b5601000000010003ee4221c9c3f4f62646e3c758dbb8abaae506a559f67148a76968fa6b0f0868140100000001000000010003ba8de2f029cae85e7ca5c9f591bb17b86d750c5116cec30d94100e16e446d41501000000";

    public static void main(String[] args) {

        // System.out.println("******************* Ecc Start *******************\n");
        //
        // System.out.println("============= 通过种子生成私钥 ===============");
        // String pk = Ecc.seedPrivate("!@#$%^&*(lajdlkjaksjdlkjaskldM<>?87126162kajsdjlaksd
        // kajdlkaslkd heiuheijpe f[a- si0ausd9asd ahsdvcyasdcasdc ajhsdg8ca" + "we asds
        // JHDKAHDKKASDKJALSKDKA ooidjajsdua09sid0asdo[paksdajsdlklasdmlk FJKLIKNLK;B/;LP[P'NC;PO';
        // OOPO;L0[" + "XP'C'[FG[" + "19218728909107328972309289832098012");
        // System.out.println("private key :" + pk + "\n");
        //
        // System.out.println("============= 通过私钥生成公钥 ===============");
        // String pu = Ecc.privateToPublic(pk);
        // System.out.println("public key :" + pu + " \n ");
        //
        // System.out.println("============= 自定义数据签名 ===============");
        // String sign = Ecc.sign(pk, "is京東價as看到可可是是是@#￥%……&*（CVBNM《d ");
        // System.out.println("sign :" + sign + " \n ");
        //
        // System.out.println("============= 转账数据序列化 ===============");
        // String data = Ecc.parseTransferData("fromaccount", "toaccount", "10.0020 SYS",
        // "测试123abcdo./,./!@##$%");
        // System.out.println("seriz data :" + data);
        // System.out.println("transfer eq eosjs seriz " + data.equals(eosjs_transfer_seriz) + " \n
        // ");
        //
        // System.out.println("============= 创建账户数据序列化 ===============");
        // String data1 = Ecc.parseAccountData("eosio", "espritbloc1.",
        // "EOS8eAX54cJtAngV2V22WZhRCW7e4sTAZz1mC5U22vp8mAGuFdMXx",
        // "EOS8FPooohZiiCAYXahWCQRxgXXzUbS2gNELAeYCUgGdDMbd2FHQT");
        // System.out.println("seriz data :" + data1);
        // System.out.println("account eq eosjs seriz " + data1.equals(eosjs_account_seriz));
        //
        // System.out.println("\n******************* Ecc End *******************\n\n\n");
        //
        // System.out.println("******************* Rpc Start *******************\n");

        Rpc rpc = new Rpc("http://192.168.1.50:8888");
        //
        // System.out.println("============= 转账 ===============");
        // try {
        // Transaction t1 = rpc.transfer("5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3",
        // "espritblocke", "inita", "initb", "12.2821 MSP", "");
        // System.out.println("转账成功 = " + t1.getTransactionId() + " \n ");
        // }
        // catch (Exception ex) {
        // ex.printStackTrace();
        // }
        //
        // System.out.println("============= 创建账户并且抵押 ===============");
        // try {
        // Transaction t2 =
        // rpc.createAccount("5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3", "eosio",
        // "ccccc..bbbbb", "EOS8eAX54cJtAngV2V22WZhRCW7e4sTAZz1mC5U22vp8mAGuFdMXx",
        // "EOS8eAX54cJtAngV2V22WZhRCW7e4sTAZz1mC5U22vp8mAGuFdMXx", 8192l, "0.01 SYS", "0.01 SYS",
        // 0l);
        // System.out.println("创建成功 = " + t2.getTransactionId() + " \n ");
        // }
        // catch (Exception ex) {
        // ex.printStackTrace();
        // }
        // System.out.println("============= 创建账户不抵押 ===============");
        // try {
        // Transaction t3 =
        // rpc.createAccount("5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3", "eosio",
        // "bbbb..54321", "EOS8eAX54cJtAngV2V22WZhRCW7e4sTAZz1mC5U22vp8mAGuFdMXx",
        // "EOS8eAX54cJtAngV2V22WZhRCW7e4sTAZz1mC5U22vp8mAGuFdMXx", 8192l);
        // System.out.println("创建成功 = " + t3.getTransactionId() + " \n ");
        // }
        // catch (Exception ex) {
        // ex.printStackTrace();
        // }
        //

        /*
         * System.out.println("============= 抵押 ==============="); try { Transaction t2 =
         * rpc.delegate("5JAFd3qAYoGBRMn32Yp3uKCL5YqwZqAMUeNm6AvwRQdyMQGPLdK", "zebrawallet1",
         * "0.0001 EOS", "0.0001 EOS", 0l); System.out.println("抵押成功 = " +
         * t2.getTransactionId()+" \n "); }catch(Exception ex) { ex.printStackTrace(); }
         */

        /*
         * System.out.println("============= 赎回 ==============="); try { Transaction t2 =
         * rpc.undelegate("5JAFd3qAYoGBRMn32Yp3uKCL5YqwZqAMUeNm6AvwRQdyMQGPLdK", "zebrawallet1",
         * "0.01 EOS", "0.01 EOS"); System.out.println("赎回成功 = " + t2.getTransactionId()+" \n ");
         * }catch(Exception ex) { ex.printStackTrace(); }
         */

        /*
         * System.out.println("============= 购买 ram ==============="); try { Transaction t2 =
         * rpc.buyRam("5JAFd3qAYoGBRMn32Yp3uKCL5YqwZqAMUeNm6AvwRQdyMQGPLdK", "zebrawallet1", 1l);
         * System.out.println("购买成功 = " + t2.getTransactionId()+" \n "); }catch(Exception ex) {
         * ex.printStackTrace(); }
         */

        /*
         * System.out.println("============= 卖出 ram ==============="); try { Transaction t2 =
         * rpc.sellRam("5JAFd3qAYoGBRMn32Yp3uKCL5YqwZqAMUeNm6AvwRQdyMQGPLdK", "zebrawallet1",
         * 1000l); System.out.println("卖出成功 = " + t2.getTransactionId()+" \n "); }catch(Exception
         * ex) { ex.printStackTrace(); }
         */

        /*
         * System.out.println("============= refund ==============="); try { Transaction t2 =
         * rpc.refund("5JAFd3qAYoGBRMn32Yp3uKCL5YqwZqAMUeNm6AvwRQdyMQGPLdK", "zebrawallet1");
         * System.out.println("refund成功 = " + t2.getTransactionId()+" \n "); }catch(Exception ex) {
         * ex.printStackTrace(); }
         */

        /*
         * System.out.println("============= Get Table Rows ==============="); try { TableRows r =
         * rpc.getTableRows("eosio", "eosio", "rammarket", "true"); //
         * System.out.println("Get Table Rows成功 = " + r.getRows()[0].getSupply() +" \n ");
         * System.out.println("Get Table Rows成功 = " + r.getRows() + " \n "); }catch(Exception ex) {
         * ex.printStackTrace(); }
         */

        /*
         * System.out.println("============= Get RAM Rate ==============="); try { Float f =
         * rpc.getRamRate(); System.out.println("Get RAM Rate 成功 = " + f + " \n ");
         * }catch(Exception ex) { ex.printStackTrace(); }
         */

        System.out.println("============= Get RAM Use ===============");
        try {
            RamUsage r = rpc.getRamUsage();
            System.out.println("Get RAM Use 成功 = " + r.getMax_ram_size_kb() + "," + r.getReserved_ram_size_kb() + " \n ");
        }
        catch (Exception ex) {
            ex.printStackTrace();

            // System.out.println("\n******************* Rpc End *******************");
        }
    }
}
