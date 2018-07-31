package io.eblock.eos4j.api.vo.account;

import java.math.BigDecimal;

public class Balance {
    /**
     * 余额
     */
    private BigDecimal amount;

    /**
     * 币种
     */
    private String symbol;

    public Balance(BigDecimal bigDecimal, String s) {
        amount = bigDecimal;
        symbol = s;
    }

    public static Balance parse(String balance){
        String[] arr = balance.split("\\s");
        if(arr.length != 2){
            return null;
        }

        return new Balance(new BigDecimal(arr[0]),arr[1]);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}