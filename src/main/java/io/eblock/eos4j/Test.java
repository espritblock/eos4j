package io.eblock.eos4j;

import java.util.Map;
import java.util.TreeMap;

public class Test {

	static final String eosjs_transfer_seriz = "00f2d4142123e95d0000c85353840ccdb486010000000000045359530000000019e6b58be8af95313233616263646f2e2f2c2e2f214023232425";
	static final String eosjs_account_seriz = "0000000000ea30550002a2f164772b5601000000010003ee4221c9c3f4f62646e3c758dbb8abaae506a559f67148a76968fa6b0f0868140100000001000000010003ba8de2f029cae85e7ca5c9f591bb17b86d750c5116cec30d94100e16e446d41501000000";

	public static void main(String[] args) {

		String pk = EosClient.seedPrivate("dsidjioasjd12kmdaksd..cdokadosopas");
		System.out.println("\n private key :" + pk);

		String pu = EosClient.privateToPublic(pk);
		System.out.println("\n public key :" + pu);

		String sign = EosClient.sign("5JKstSUdqhF4wKsvourKAiN4fg9JzWQGzqpkh1jbB17vNi73HjK", "{\"actions\":[{\"account\":\"eosio.token\",\"authorization\":[{\"actor\":\"unlimited111\",\"permission\":\"active\"}],\"data\":\"1042482a3be9e2d450c8482a3be9e2d4102700000000000004454f53000000000e676f20676f20676f20676f20676f\",\"name\":\"transfer\"}],\"context_free_actions\":[],\"delay_sec\":0,\"expiration\":\"2018-05-31T04:37:45\",\"max_cpu_usage_ms\":0,\"net_usage_words\":0,\"ref_block_num\":56390,\"ref_block_prefix\":3827450622,\"transaction_extensions\":[]}");
		System.out.println("\n sign :" + sign);

		String data = EosClient.parseTransferData("fromaccount", "toaccount", "10.0020 SYS", "≤‚ ‘123abcdo./,./!@##$%");
		System.out.println("\n seriz data :"+data);
		System.out.println("\n transfer eq eosjs seriz " + data.equals(eosjs_transfer_seriz));
		
		String data1 = EosClient.parseAccountData("eosio","espritbloc1.","EOS8eAX54cJtAngV2V22WZhRCW7e4sTAZz1mC5U22vp8mAGuFdMXx","EOS8FPooohZiiCAYXahWCQRxgXXzUbS2gNELAeYCUgGdDMbd2FHQT");
		System.out.println("\n seriz data :"+data1);
		System.out.println("\n account eq eosjs seriz " + data1.equals(eosjs_account_seriz));
	}
}
