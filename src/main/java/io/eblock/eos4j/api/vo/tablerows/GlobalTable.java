package io.eblock.eos4j.api.vo.tablerows;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GlobalTable {
    @JsonProperty("rows")
    private List<GlobalRows> rows;

    public GlobalTable() {
    }

    public List<GlobalRows> getRows() {
        return rows;
    }

    public void setRows(List<GlobalRows> rows) {
        this.rows = rows;
    }
}
