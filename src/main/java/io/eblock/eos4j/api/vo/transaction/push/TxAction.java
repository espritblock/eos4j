package io.eblock.eos4j.api.vo.transaction.push;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.eblock.eos4j.api.vo.BaseVo;

/**
 * 
 * @author espritblock http://eblock.io
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TxAction extends BaseVo {

	public TxAction() {

	}

	public TxAction(String actor, String account, String name, Object data) {
		this.account = account;
		this.name = name;
		this.data = data;
		this.authorization = new ArrayList<>();
		this.authorization.add(new TxActionAuth(actor, "active"));
	}

	private String account;

	private String name;

	private List<TxActionAuth> authorization;

	private Object data;
	private String hex_data;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TxActionAuth> getAuthorization() {
		return authorization;
	}

	public void setAuthorization(List<TxActionAuth> authorization) {
		this.authorization = authorization;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getHex_data() {
		return hex_data;
	}

	public void setHex_data(String hex_data) {
		this.hex_data = hex_data;
	}
}
