package io.eblock.eos4j.api.vo;

public class RamUsage {
    private Long max_ram_size_kb;
    private Long  reserved_ram_size_kb;

    public Long getMax_ram_size_kb() {
        return max_ram_size_kb;
    }

    public void setMax_ram_size_kb(Long max_ram_size_kb) {
        this.max_ram_size_kb = max_ram_size_kb;
    }

    public Long getReserved_ram_size_kb() {
        return reserved_ram_size_kb;
    }

    public void setReserved_ram_size_kb(Long reserved_ram_size_kb) {
        this.reserved_ram_size_kb = reserved_ram_size_kb;
    }

    public RamUsage() {
    }
}