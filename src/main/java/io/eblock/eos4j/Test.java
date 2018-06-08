package io.eblock.eos4j;

import io.eblock.eos4j.api.vo.transaction.Transaction;

public class Test {

	static final String eosjs_transfer_seriz = "00f2d4142123e95d0000c85353840ccdb486010000000000045359530000000019e6b58be8af95313233616263646f2e2f2c2e2f214023232425";

	static final String eosjs_account_seriz = "0000000000ea30550002a2f164772b5601000000010003ee4221c9c3f4f62646e3c758dbb8abaae506a559f67148a76968fa6b0f0868140100000001000000010003ba8de2f029cae85e7ca5c9f591bb17b86d750c5116cec30d94100e16e446d41501000000";

	public static void main(String[] args) {

		////// Ecc start

//		String pk = Ecc.seedPrivate("sdsdii612812#$%^&9dskskd<><><SAIOsjkJBVHGUPOO{{PW");
//		System.out.println("\n private key :" + pk);
//
//		String pu = Ecc.privateToPublic(pk);
//		System.out.println("\n public key :" + pu);
//
//		String sign = Ecc.sign("5JF7uA3ERcSp8EjMvsnEWLdHVLR9oJsY7FVUj8qfHTnXZv7r98p",
//				"is京東價as看到可可是是是@#￥%……&*（CVBNM《d ");
//		System.out.println("\n sign :" + sign);
//
//		String data = Ecc.parseTransferData("fromaccount", "toaccount", "10.0020 SYS", "测试123abcdo./,./!@##$%");
//		System.out.println("\n seriz data :" + data);
//		System.out.println("\n transfer eq eosjs seriz " + data.equals(eosjs_transfer_seriz));
//
//		String data1 = Ecc.parseAccountData("eosio", "espritbloc1.",
//				"EOS8eAX54cJtAngV2V22WZhRCW7e4sTAZz1mC5U22vp8mAGuFdMXx",
//				"EOS8FPooohZiiCAYXahWCQRxgXXzUbS2gNELAeYCUgGdDMbd2FHQT");
//		System.out.println("\n seriz data :" + data1);
//		System.out.println("\n account eq eosjs seriz " + data1.equals(eosjs_account_seriz));

		////// Ecc end

		/// Rpc start

//		Rpc rpc = new Rpc("http://54.238.242.48:8888");
		//
		// //ChainInfo
		//
//		try {
//			Transaction tx = rpc.transfer("5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3", "eosio.token", "eosio","eosio.token", "12.2821 SYS", "");
//			System.out.println("transfer = " + tx.getTransactionId());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			Transaction tx = rpc.createAccount("5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3", "eosio",
//					"qqqsssdddeee", "EOS8eAX54cJtAngV2V22WZhRCW7e4sTAZz1mC5U22vp8mAGuFdMXx",
//					"EOS8eAX54cJtAngV2V22WZhRCW7e4sTAZz1mC5U22vp8mAGuFdMXx", 8192l, "1.0000 SYS", "1.0000 SYS", 0l);
//			System.out.println("transfer = " + tx.getTransactionId());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		// System.out.println(Ese.parseDelegateData("eosio","mycontract11","1.0000
		// SYS","1.0000 SYS",0));
		// System.out.println("0000000000ea3055104246e6e6499197102700000000000004535953000000001027000000000000045359530000000000");
		// byte [] bs = ByteUtils.writerUnit32("0");
		//
		// bs = ByteUtils.concat(bs,ByteUtils.writerAsset("10.0020 4,SYS@eosio.token"));
		//
		// bs = ByteUtils.concat(bs,ByteUtils.writerUnit32("3755938507"));
		//
		// bs = ByteUtils.concat(bs,ByteUtils.writerVarint32("0"));
		//
		// bs = ByteUtils.concat(bs,ByteUtils.writerVarint32("0"));
		////
		// final byte b [] = bs.clone();
		// int[] a = IntStream.range(0, b.length).map(i -> b[i] & 0xff).toArray();
		// for(int i=1;i<=a.length;i++) {
		// System.out.print(a[i-1]+","+((i%8==0)?"\n":""));
		// }

		/// Rpc end
	}
}
