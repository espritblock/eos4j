package io.eblock.eos4j.api.vo;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TableRows {

	private Boolean more;

	private List<Map> rows;

	public Boolean getMore() {
		return more;
	}

	public void setMore(Boolean more) {
		this.more = more;
	}

	public List<Map> getRows() {
		return rows;
	}

	public void setRows(List<Map> rows) {
		this.rows = rows;
	}
}
