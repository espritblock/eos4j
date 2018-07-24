package io.eblock.eos4j.api.vo;


public class CurrencyBalanceReq {
    //合约账户
    private String code;
    //查询账户
    private String account;
    //币种
    private String symbol;
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getAccount() {
        return account;
    }
    
    public void setAccount(String account) {
        this.account = account;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    

}
