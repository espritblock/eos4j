package io.eblock.eos4j.api.vo.tablerows;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.eblock.eos4j.api.vo.tablerows.Base;
import io.eblock.eos4j.api.vo.tablerows.Quote;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RamMarketRows {
    @JsonProperty("supply")
    private String supply;

    @JsonProperty("base")
    private Base base;

    @JsonProperty("quote")
    private Quote quote;
    
    @JsonProperty("max_ram_size")
    private String max_ram_size;

    @JsonProperty("total_ram_bytes_reserved")
    private String total_ram_bytes_reserved;

    public RamMarketRows() {
    }

    public String getSupply() {
        return supply;
    }

    public void setSupply(String supply) {
        this.supply = supply;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
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