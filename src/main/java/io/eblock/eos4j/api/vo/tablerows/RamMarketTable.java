package io.eblock.eos4j.api.vo.tablerows;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RamMarketTable {
    @JsonProperty("more")
    private Boolean more;

    @JsonProperty("rows")
    private List<RamMarketRows> rows;

    public RamMarketTable() {
    }

    public Boolean getMore() {
        return more;
    }

    public void setMore(Boolean more) {
        this.more = more;
    }

    public List<RamMarketRows> getRows() {
        return rows;
    }

    public void setRows(List<RamMarketRows> rows) {
        this.rows = rows;
    }
}