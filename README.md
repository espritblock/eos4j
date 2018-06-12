# eos4j

eosio 1.0.1 for java 

# use

## transfer

```
/**
 * ת��
 * @param pk ˽Կ
 * @param contractAccount ��Լ�˻�
 * @param from ��
 * @param to ��
 * @param quantity ���ֽ��
 * @param memo ����
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
 * �����˻�
 * @param pk ˽Կ
 * @param creator ������
 * @param newAccount ���˻�
 * @param owner ��Կ
 * @param active ��Կ
 * @param buyRam ����ռ�����
 * @param stakeNetQuantity �����Ѻ
 * @param stakeCpuQuantity cpu��Ѻ
 * @param transfer ��Ѻ�ʲ��Ƿ�ת�͸��Է���0�Լ����У�1�Է�����
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

eos4j is released under GNU/GPL Version 3
