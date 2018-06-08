package io.eblock.eos4j.api.vo.transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author espritblock http://eblock.io
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Processed {

	@JsonProperty("id")
	private String id;

	@JsonProperty("receipt")
	private Receipt receipt;

	@JsonProperty("elapsed")
	private Long elapsed;

	@JsonProperty("net_usage")
	private Long netUsage;

	@JsonProperty("scheduled")
	private Boolean scheduled;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Receipt getReceipt() {
		return receipt;
	}

	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}

	public Long getElapsed() {
		return elapsed;
	}

	public void setElapsed(Long elapsed) {
		this.elapsed = elapsed;
	}

	public Long getNetUsage() {
		return netUsage;
	}

	public void setNetUsage(Long netUsage) {
		this.netUsage = netUsage;
	}

	public Boolean getScheduled() {
		return scheduled;
	}

	public void setScheduled(Boolean scheduled) {
		this.scheduled = scheduled;
	}
}
