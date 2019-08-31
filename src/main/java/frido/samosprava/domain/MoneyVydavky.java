package frido.samosprava.domain;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class MoneyVydavky {
    private Integer year;
    private BigDecimal bv;
    private BigDecimal kv;
    private BigDecimal fv;
    private Set<UseKv> useKv = new HashSet<>();

    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }
    public BigDecimal getBv() {
        return bv;
    }
    public void setBv(BigDecimal bv) {
        this.bv = bv;
    }
    public BigDecimal getKv() {
        return kv;
    }
    public void setKv(BigDecimal kv) {
        this.kv = kv;
    }
    public BigDecimal getFv() {
        return fv;
    }
    public void setFv(BigDecimal fv) {
        this.fv = fv;
    }
    public Set<UseKv> getUseKv() {
        return useKv;
    }
    public void setUseKv(Set<UseKv> useKv) {
        this.useKv = useKv;
    }


}
