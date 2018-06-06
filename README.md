# eos4j

eosio 1.0.1 for java

# use

## seedPrivate

```

EosClient.seedPrivate("test");

```

## privateToPublic

```

EosClient.privateToPublic(privateKey);

```

## sign

```
EosClient.sign(pk, "test");

```

## data serializa

```
//transfer parse
String data = EosClient.parseTransferData("fromaccount", "toaccount", "10.0020 SYS", "memo");

//account parse
String data1 = EosClient.parseAccountData("eosio","espritbloc1.",
	"EOS8eAX54cJtAngV2V22WZhRCW7e4sTAZz1mC5U22vp8mAGuFdMXx",
	"EOS8FPooohZiiCAYXahWCQRxgXXzUbS2gNELAeYCUgGdDMbd2FHQT");

```

# join eos open source 

wechat hl_294944589
 
# License

eos4j is released under the Apache License 2.0.
