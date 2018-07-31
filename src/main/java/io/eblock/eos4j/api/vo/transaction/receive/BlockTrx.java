package io.eblock.eos4j.api.vo.transaction.receive;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.eblock.eos4j.api.vo.transaction.push.Tx;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BlockTrx {

    private String id; // "5570e69024eb42f97129eb9e15b5e549f9d88a961d9af12ba53248b6c4e2d7fd",
    private List<String> signatures; // [ "SIG_K1_K2GDmgZZESMETRjrvcyry92imAvMYkGXHXfadRjH7vD1Yj5JCiLWDxtdppikV29JbZj75bK2DpLxwsUDYUQ9Zbyx48ZPr2"],
    private String compression; // "none",
    private String packed_context_free_data; // "",
    private List<String> context_free_data; // [],
    private String packed_trx; // "88982f5b5590b192a63c000000000100a6823403ea3055000000572d3ccdcd0100c0d4cf99c6a6f100000000a8ed32323200c0d4cf99c6a6f10000006088e0b799983a00000000000004454f53000000001174657374207472616e7366657220312e3500",


    Tx transaction;
}