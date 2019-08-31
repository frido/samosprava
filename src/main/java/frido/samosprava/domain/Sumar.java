package frido.samosprava.domain;

import java.math.BigDecimal;

public class Sumar {
    private Integer year;
    private BigDecimal vydavky;
    private BigDecimal prijmy;

    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }
    public BigDecimal getVydavky() {
        return vydavky;
    }
    public void setVydavky(BigDecimal vydavky) {
        this.vydavky = vydavky;
    }
    public BigDecimal getPrijmy() {
        return prijmy;
    }
    public void setPrijmy(BigDecimal prijmy) {
        this.prijmy = prijmy;
    }
}
