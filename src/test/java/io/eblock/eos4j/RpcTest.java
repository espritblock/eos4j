package io.eblock.eos4j;

import java.util.Collections;
import org.junit.Test;
import com.alibaba.fastjson.JSONObject;
import io.eblock.eos4j.api.utils.Generator;
import io.eblock.eos4j.utils.JsonFormatTool;
import retrofit2.Call;

public class RpcTest {

    static Rpc rpc = new Rpc("http://192.168.1.50:8888");

    @Test
    public void getCurrencyBalance() {
        System.out.println(rpc.getCurrencyBalance("eosio.token", "yanghaijun", "EOS"));
    }

    @Test
    public void getBlock() {
        println(rpc.getRpcService().getBlock(Collections.singletonMap("block_num_or_id", "1000")));
    }

    private static void println(Object object) {

        if (object instanceof Call) {
            object = Generator.executeSync((Call)object);
        }

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + object.getClass());
        System.out.println(JsonFormatTool.formatJson(JSONObject.toJSONString(object)));
    }

}