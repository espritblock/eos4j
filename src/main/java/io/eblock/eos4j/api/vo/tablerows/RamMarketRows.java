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
}
