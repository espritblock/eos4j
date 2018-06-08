package io.eblock.eos4j.api.vo.transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author espritblock http://eblock.io
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Receipt {

	@JsonProperty("status")
	private String status;

	@JsonProperty("cpu_usage_us")
	private Long cpuUsageUs;

	@JsonProperty("net_usage_words")
	private Long netUsageWords;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getCpuUsageUs() {
		return cpuUsageUs;
	}

	public void setCpuUsageUs(Long cpuUsageUs) {
		this.cpuUsageUs = cpuUsageUs;
	}

	public Long getNetUsageWords() {
		return netUsageWords;
	}

	public void setNetUsageWords(Long netUsageWords) {
		this.netUsageWords = netUsageWords;
	}

}
