package io.eblock.eos4j.api.vo.account;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author espritblock http://eblock.io
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {

	@JsonProperty("account_name")
	private String accountName;

	@JsonProperty("privileged")
	private String privileged;

	@JsonProperty("last_code_update")
	private Date lastCodeUpdate;

	@JsonProperty("created")
	private Date created;

	@JsonProperty("ram_quota")
	private Long ramQuota;

	@JsonProperty("net_weight")
	private Long netWeight;

	@JsonProperty("cpu_weight")
	private Long cpuWeight;

	@JsonProperty("net_limit")
	private NetLimit netLimit;

	@JsonProperty("cpu_limit")
	private CpuLimit cpuLimit;

	@JsonProperty("ram_usage")
	private Long ramUsage;

	@JsonProperty("permissions")
	private List<Permission> permissions;

	public Account() {

	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPrivileged() {
		return privileged;
	}

	public void setPrivileged(String privileged) {
		this.privileged = privileged;
	}

	public Date getLastCodeUpdate() {
		return lastCodeUpdate;
	}

	public void setLastCodeUpdate(Date lastCodeUpdate) {
		this.lastCodeUpdate = lastCodeUpdate;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Long getRamQuota() {
		return ramQuota;
	}

	public void setRamQuota(Long ramQuota) {
		this.ramQuota = ramQuota;
	}

	public Long getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(Long netWeight) {
		this.netWeight = netWeight;
	}

	public Long getCpuWeight() {
		return cpuWeight;
	}

	public void setCpuWeight(Long cpuWeight) {
		this.cpuWeight = cpuWeight;
	}

	public NetLimit getNetLimit() {
		return netLimit;
	}

	public void setNetLimit(NetLimit netLimit) {
		this.netLimit = netLimit;
	}

	public CpuLimit getCpuLimit() {
		return cpuLimit;
	}

	public void setCpuLimit(CpuLimit cpuLimit) {
		this.cpuLimit = cpuLimit;
	}

	public Long getRamUsage() {
		return ramUsage;
	}

	public void setRamUsage(Long ramUsage) {
		this.ramUsage = ramUsage;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

}
