package io.eblock.eos4j.api.vo.transaction.receive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlockTxs {

    private String status;// "executed",
    private String cpu_usage_us;// 1418,
    private String net_usage_words;// 18,

    @JsonProperty("trx")
    private BlockTrx trx;//

}