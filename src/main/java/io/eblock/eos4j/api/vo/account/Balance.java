package io.eblock.eos4j.api.vo.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Balance {
    private BigDecimal amount;
    private String symbol;

    public static Balance parse(String balance){
        String[] arr = balance.split("\\s");
        if(arr.length != 2){
            return null;
        }

        return new Balance(new BigDecimal(arr[0]),arr[1]);
    }
}
