# eos4j

Eos for java

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
String data = EosClient.parseData(new DataParam[] { 
	new DataParam("fromaccount", DataType.name, Action.transfer),
	new DataParam("toaccount", DataType.name, Action.transfer),
	new DataParam("10.0020 SYS", DataType.asset, Action.transfer),
	new DataParam("memo", DataType.string, Action.transfer), 
});

```

# join eos open source 

wechat hl_294944589
 
# License

eos4j is released under the Apache License 2.0.
