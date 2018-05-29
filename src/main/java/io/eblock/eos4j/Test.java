package io.eblock.eos4j;

import io.eblock.eos4j.ese.Action;
import io.eblock.eos4j.ese.DataParam;
import io.eblock.eos4j.ese.DataType;

public class Test {

	static final String eosjs_seriz = "00f2d4142123e95d0000c85353840ccdb4860100000000000453595300000000046d656d6f";

	public static void main(String[] args) {

		String pk = EosClient.seedPrivate("dsidjioasjd12kmdaksd..cdokadosopas");
		System.out.println("private key :" + pk);

		String pu = EosClient.privateToPublic(pk);
		System.out.println("public key :" + pu);

		String sign = EosClient.sign(pk, "test");
		System.out.println("sign :" + sign);

		String data = EosClient
				.parseData(new DataParam[] { new DataParam("fromaccount", DataType.name, Action.transfer),
						new DataParam("toaccount", DataType.name, Action.transfer),
						new DataParam("10.0020 SYS", DataType.asset, Action.transfer),
						new DataParam("memo", DataType.string, Action.transfer), });
		System.out.println("seriz data :"+data);
		System.out.println("eq eosjs seriz " + data.equals(eosjs_seriz));
	}
}
