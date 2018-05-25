package io.eblock.eos4j;

public class Test {

	public static void main(String[] args) {

		String pk = EosClient.seedPrivate("test");
		System.out.println("private key £º" + pk);

		String pu = EosClient.privateToPublic(pk);
		System.out.println("public key £º" + pu);

		String sign = EosClient.sign(pk, "test");
		System.out.println("sign :" + sign);
	}

}
