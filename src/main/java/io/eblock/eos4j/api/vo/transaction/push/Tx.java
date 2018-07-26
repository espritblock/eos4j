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
public class Tx extends BaseVo{

	private Object expiration;

	private Long ref_block_num;

	private Long ref_block_prefix;

	private Long net_usage_words;

	private Long max_cpu_usage_ms;

	private Long delay_sec;

	private List<Object> context_free_actions = new ArrayList<>();

	private List<TxAction> actions;

	private List<TxExtenstions> transaction_extensions = new ArrayList<>();

	public Object getExpiration() {
		return expiration;
	}

	public void setExpiration(Object expiration) {
		this.expiration = expiration;
	}

	public Long getRef_block_num() {
		return ref_block_num;
	}

	public void setRef_block_num(Long ref_block_num) {
		this.ref_block_num = ref_block_num;
	}

	public Long getRef_block_prefix() {
		return ref_block_prefix;
	}

	public void setRef_block_prefix(Long ref_block_prefix) {
		this.ref_block_prefix = ref_block_prefix;
	}

	public Long getNet_usage_words() {
		return net_usage_words;
	}

	public void setNet_usage_words(Long net_usage_words) {
		this.net_usage_words = net_usage_words;
	}

	public Long getMax_cpu_usage_ms() {
		return max_cpu_usage_ms;
	}

	public void setMax_cpu_usage_ms(Long max_cpu_usage_ms) {
		this.max_cpu_usage_ms = max_cpu_usage_ms;
	}

	public Long getDelay_sec() {
		return delay_sec;
	}

	public void setDelay_sec(Long delay_sec) {
		this.delay_sec = delay_sec;
	}

	public List<Object> getContext_free_actions() {
		return context_free_actions;
	}

	public void setContext_free_actions(List<Object> context_free_actions) {
		this.context_free_actions = context_free_actions;
	}

	public List<TxAction> getActions() {
		return actions;
	}

	public void setActions(List<TxAction> actions) {
		this.actions = actions;
	}

	public List<TxExtenstions> getTransaction_extensions() {
		return transaction_extensions;
	}

	public void setTransaction_extensions(List<TxExtenstions> transaction_extensions) {
		this.transaction_extensions = transaction_extensions;
	}
}
