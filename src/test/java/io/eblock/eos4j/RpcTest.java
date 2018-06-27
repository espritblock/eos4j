package io.eblock.eos4j;


        import com.alibaba.fastjson.JSONObject;
        import io.eblock.eos4j.api.utils.Generator;
        import io.eblock.util.JsonFormatTool;
        import org.junit.Test;
        import retrofit2.Call;

        import java.util.Collections;


public class RpcTest {

    static Rpc rpc = new Rpc("http://192.168.0.246:8889");

    @Test
    public void getCurrencyBalance() {


        System.out.println(rpc.getCurrencyBalance("eosio.token","yanghaijun","EOS"));

    }

    @Test
    public void getBlock() {

        println(rpc.getRpcService().getBlockAll(Collections.singletonMap("block_num_or_id", "2265494")));



        println(rpc.getBlock(2265494+""));

    }



    private static void println(Object object) {

        if(object instanceof Call){
            object = Generator.executeSync((Call)object);
        }


        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+object.getClass());
        System.out.println(JsonFormatTool.formatJson(JSONObject.toJSONString(object)));
    }


}