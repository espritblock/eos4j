package io.eblock.eos4j.ese;

import io.eblock.eos4j.utils.ByteUtils;
import io.eblock.eos4j.utils.EException;

/**
 * DataParam
 * 
 * @author espritblock http://eblock.io
 *
 */
public class DataParam {

	public DataParam(String value, DataType type, Action action) {
		this.value = value;
		this.type = type;
		if (type == DataType.asset || type == DataType.symbol ) {
			if (action == action.transfer || action == action.delegate || action == action.close) {
				String vs[] = value.split(" ");
				if (vs.length < 2) {throw new EException("error", "quantity error");}
				String ammount = vs[0];
				String ams [] = ammount.split("[.]");
				int precision = 0;
				if(ams.length>1) {precision = ams[1].length();}
				this.value = vs[0] + " " + action.getCode().replace("${precision}",String.valueOf(precision)).replace("${quantity}", vs[1]);
			}else {
				this.value = value;
			}
		}
	}

	private String value;

	private DataType type;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public DataType getType() {
		return type;
	}

	public void setType(DataType type) {
		this.type = type;
	}

	public byte[] seria() {
		if (this.type == DataType.name) {
			return ByteUtils.writeName(this.value);
		} else if (this.type == DataType.asset) {
			return ByteUtils.writerAsset(this.value);
		}else if (this.type == DataType.symbol) {
			return ByteUtils.writerSymbol(this.value);
		} else if (this.type == DataType.unit32) {
			return ByteUtils.writerUnit32(this.value);
		} else if (this.type == DataType.unit16) {
			return ByteUtils.writerUnit16(this.value);
		} else if (this.type == DataType.key) {
			return ByteUtils.writerKey(this.value);
		} else if (this.type == DataType.varint32) {
			return ByteUtils.writerVarint32(this.value);
		} else if (this.type == DataType.unit64) {
			return ByteUtils.writeUint64(this.value);
		} else {
			return ByteUtils.writerString(this.value);
		}
	}
}
