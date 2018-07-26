package io.eblock.eos4j.api.vo.tablerows;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GlobalRows {
    @JsonProperty("max_ram_size")
    private String max_ram_size;

    @JsonProperty("total_ram_bytes_reserved")
    private String total_ram_bytes_reserved;

    public GlobalRows() {
    }

    public String getMax_ram_size() {
        return max_ram_size;
    }

    public void setMax_ram_size(String max_ram_size) {
        this.max_ram_size = max_ram_size;
    }

    public String getTotal_ram_bytes_reserved() {
        return total_ram_bytes_reserved;
    }

    public void setTotal_ram_bytes_reserved(String total_ram_bytes_reserved) {
        this.total_ram_bytes_reserved = total_ram_bytes_reserved;
    }
}