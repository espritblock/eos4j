package io.eblock.eos4j.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import io.eblock.eos4j.api.vo.transaction.push.TxAction;
import io.eblock.eos4j.api.vo.BaseVo;

public class ObjectUtils {

    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Bean2Map
     * 
     * @param obj
     * @return
     */
    public static Map<String, Object> Bean2Map(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new LinkedHashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++ ) {
            map.put(fields[i].getName(), getFieldValueByName(fields[i].getName(), obj));
        }
        return map;
    }

    public static void writeBytes4RefundData(Object vo, ByteBuffer bf) {
        Map<String, Object> params = null;
        if (vo instanceof Map) {
            params = (Map)vo;
        } else {
            params = Bean2Map(vo);
        }
        Map<String, Object> objMap = new LinkedHashMap<>();
        for (String key : params.keySet()) {
            Object obj = params.get(key);
            if ("owner".equals(key)) {
                bf.concat(ByteUtils.writeName(obj.toString()));
                System.out.println("\tkey: " + key);
            } else {
                System.out.println("in refund action data, unexpected key: " + key);
            }
        }
    }

    public static void writeBytes4RefundAction(TxAction refundAction, ByteBuffer bf) {
        Map<String, Object> _params = Bean2Map(refundAction);
        for (String _key : _params.keySet()) {
            Object _obj = _params.get(_key);
            if ("data".equals(_key)) {
                System.out.println("key: " + _key);
                ByteBuffer _databf = new ByteBuffer();
                writeBytes4RefundData(_obj, _databf);
                bf.concat(new byte[] {(byte)_databf.getBuffer().length});
                bf.concat(_databf.getBuffer());
            } else if ("account".equals(_key)) {
                bf.concat(ByteUtils.writeName(_obj.toString()));
                System.out.println("key: " + _key);
            } else if ("name".equals(_key)) {
                bf.concat(ByteUtils.writeName(_obj.toString()));
                System.out.println("key: " + _key);
            } else if ("authorization".equals(_key)) {
                bf.concat(ByteUtils.writerVarint32(String.valueOf(((List)_obj).size())));
                System.out.println("key: " + _key);
                for (Object auth_ob : (List)_obj) {
                    writeBytes(auth_ob, bf);
                }
            }
        }
    }

    public static void writeBytes(Object vo, ByteBuffer bf) {
        Map<String, Object> params = null;
        if (vo instanceof Map) {
            params = (Map)vo;
        } else {
            params = Bean2Map(vo);
        }
        Map<String, Object> objMap = new LinkedHashMap<>();
        for (String key : params.keySet()) {
            Object obj = params.get(key);
            if (obj instanceof BaseVo || obj instanceof List || obj instanceof Map) {
                if ("authorization".equals(key)) {
                    bf.concat(ByteUtils.writerVarint32(String.valueOf(((List)obj).size())));
                    System.out.println("key: " + key);
                    for (Object ob : (List)obj) {
                        writeBytes(ob, bf);
                    }
                } else if ("data".equals(key)) {
                    ByteBuffer databf = new ByteBuffer();
                    writeBytes(obj, databf);
                    bf.concat(new byte[] {(byte)databf.getBuffer().length});
                    bf.concat(databf.getBuffer());
                } else if ("transaction_extensions".equals(key)) {

                } else {
                    objMap.put(key, obj);
                }
            } else {
                if ("chain_id".equals(key)) {
                    bf.concat(Hex.hexStringToBytes(obj.toString()));
                    System.out.println("key: " + key);
                } else if ("expiration".equals(key)) {
                    bf.concat(ByteUtils.writerUnit32(obj.toString()));
                    System.out.println("key: " + key);
                } else if ("ref_block_num".equals(key)) {
                    bf.concat(ByteUtils.writerUnit16(obj.toString()));
                    System.out.println("key: " + key);
                } else if ("ref_block_prefix".equals(key)) {
                    bf.concat(ByteUtils.writerUnit32(obj.toString()));
                    System.out.println("key: " + key);
                } else if ("net_usage_words".equals(key)) {
                    bf.concat(ByteUtils.writerVarint32(obj.toString()));
                    System.out.println("key: " + key);
                } else if ("max_cpu_usage_ms".equals(key)) {
                    bf.concat(ByteUtils.writerUnit8(obj.toString()));
                    System.out.println("key: " + key);
                } else if ("delay_sec".equals(key)) {
                    bf.concat(ByteUtils.writerVarint32(obj.toString()));
                    System.out.println("key: " + key);
                } else if ("account".equals(key)) {
                    bf.concat(ByteUtils.writeName(obj.toString()));
                    System.out.println("key: " + key);
                } else if ("name".equals(key)) {
                    bf.concat(ByteUtils.writeName(obj.toString()));
                    System.out.println("key: " + key);
                } else if ("actor".equals(key)) {
                    bf.concat(ByteUtils.writeName(obj.toString()));
                    System.out.println("key: " + key);
                } else if ("permission".equals(key)) {
                    bf.concat(ByteUtils.writeName(obj.toString()));
                    System.out.println("key: " + key);
                } else if ("from".equals(key)) {
                    bf.concat(ByteUtils.writeName(obj.toString()));
                    System.out.println("key: " + key);
                } else if ("to".equals(key)) {
                    bf.concat(ByteUtils.writeName(obj.toString()));
                    System.out.println("key: " + key);
                } else if ("quantity".equals(key)) {
                    bf.concat(ByteUtils.writerAsset(obj.toString()));
                    System.out.println("key: " + key);
                } else if ("memo".equals(key)) {
                    bf.concat(ByteUtils.writerString(obj.toString()));
                    System.out.println("key: " + key);
                } else if ("creator".equals(key)) {
                    bf.concat(ByteUtils.writeName(obj.toString()));
                    System.out.println("key: " + key);
                } else if ("owner".equals(key)) {
                    bf.concat(ByteUtils.writerKey(obj.toString()));
                    System.out.println("key: " + key);
                } else if ("active".equals(key)) {
                    bf.concat(ByteUtils.writerKey(obj.toString()));
                    System.out.println("key: " + key);
                } else if ("payer".equals(key)) {
                    bf.concat(ByteUtils.writeName(obj.toString()));
                    System.out.println("key: " + key);
                } else if ("receiver".equals(key)) {
                    bf.concat(ByteUtils.writeName(obj.toString()));
                    System.out.println("key: " + key);
                } else if ("bytes".equals(key)) {
                    bf.concat(ByteUtils.writeUint64(obj.toString()));
                    System.out.println("key: " + key);
                } else if ("stake_net_quantity".equals(key)) {
                    bf.concat(ByteUtils.writerAsset(obj.toString()));
                } else if ("stake_cpu_quantity".equals(key)) {
                    bf.concat(ByteUtils.writerAsset(obj.toString()));
                    System.out.println("key: " + key);
                } else if ("unstake_net_quantity".equals(key)) {
                    bf.concat(ByteUtils.writerAsset(obj.toString()));
                    System.out.println("key: " + key);
                } else if ("unstake_cpu_quantity".equals(key)) {
                    bf.concat(ByteUtils.writerAsset(obj.toString()));
                    System.out.println("key: " + key);
                } else if ("transfer".equals(key)) {
                    bf.concat(ByteUtils.writerUnit8(obj.toString()));
                    System.out.println("key: " + key);
                }
            }
        }
        for (String key : objMap.keySet()) {
            Object obj = params.get(key);
            if ("context_free_actions".equals(key)) {
                bf.concat(ByteUtils.writerVarint32(String.valueOf(((List) obj).size())));
                System.out.println("key: " + key);
                for (Object ob : (List)obj) {
                    writeBytes(ob, bf);
                }
            } else if ("actions".equals(key)) {
                bf.concat(ByteUtils.writerVarint32(String.valueOf(((List) obj).size())));
                System.out.println("key: " + key);
                for (Object ob : (List)obj) {
                    if (ob instanceof TxAction) {
                        TxAction refundAction = (TxAction)ob;
                        if ((refundAction.getName()) == "refund") {
                            writeBytes4RefundAction(refundAction, bf);
                        } else {
                            writeBytes(ob, bf);
                        }
                    }
                }
            } else {
                writeBytes(obj, bf);
            }
        }
    }
}
