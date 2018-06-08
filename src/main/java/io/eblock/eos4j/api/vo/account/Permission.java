package io.eblock.eos4j.api.vo.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author espritblock http://eblock.io
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Permission {

	public Permission() {

	}

	@JsonProperty("perm_name")
	private String permName;

	@JsonProperty("parent")
	private String parent;

	@JsonProperty("required_auth")
	private RequiredAuth requiredAuth;

	public String getPermName() {
		return permName;
	}

	public void setPermName(String permName) {
		this.permName = permName;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public RequiredAuth getRequiredAuth() {
		return requiredAuth;
	}

	public void setRequiredAuth(RequiredAuth requiredAuth) {
		this.requiredAuth = requiredAuth;
	}
}
