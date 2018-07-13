package io.eblock.eos4j.api.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TableRowsReq {

	private String code = "eosio";

	private String scope;

	private String table;
	
	private Boolean json=true;

	private int limit = 10;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public Boolean getJson() {
		return json;
	}

	public void setJson(Boolean json) {
		this.json = json;
	}

}
