# eos4j

eosio 1.0.1 for java 

# use

## transfer

```
/**
 * 转账
 * @param pk 私钥
 * @param contractAccount 合约账户
 * @param from 从
 * @param to 到
 * @param quantity 币种金额
 * @param memo 留言
 * @return
 * @throws Exception
 */

 rpc.transfer(
 	"5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3",
 	"eosio.token",
 	"eosio",
 	"eosio.token",
 	"12.2821 SYS",
 	"");

```
## createAccount

```
/**
 * 创建账户
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
 rpc.createAccount(
 	"5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3", 
 	"eosio",
 	"newaccount22",
 	"EOS8eAX54cJtAngV2V22WZhRCW7e4sTAZz1mC5U22vp8mAGuFdMXx",
 	"EOS8eAX54cJtAngV2V22WZhRCW7e4sTAZz1mC5U22vp8mAGuFdMXx", 
 	8192l, 
 	"1.0000 SYS",
 	"1.0000 SYS",
 	0l);
 	
```
## seedPrivate

```

Ecc.seedPrivate("test");

```


## seedPrivate

```

Ecc.seedPrivate("test");

```

## privateToPublic

```

Ecc.privateToPublic(privateKey);

```

## sign

```
Ecc.sign(pk, "test");

```

## data serializa

```
//transfer parse
String data = Ecc.parseTransferData(
	"fromaccount", 
	"toaccount", 
	"10.0020 SYS", 
	"memo"
);

//account parse
String data1 = Ecc.parseAccountData(
	"eosio",
	"espritbloc1.",
	"EOS8eAX54cJtAngV2V22WZhRCW7e4sTAZz1mC5U22vp8mAGuFdMXx",
	"EOS8FPooohZiiCAYXahWCQRxgXXzUbS2gNELAeYCUgGdDMbd2FHQT");

```

# join eos open source 

wechat hl_294944589
 
# License

eos4j is released under the Apache License 2.0.
