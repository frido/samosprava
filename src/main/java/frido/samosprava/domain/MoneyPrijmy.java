package frido.samosprava.domain;

import java.math.BigDecimal;

public class MoneyPrijmy {
    private Integer year;
    private BigDecimal suma;

    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }
    public BigDecimal getSuma() {
        return suma;
    }
    public void setSuma(BigDecimal suma) {
        this.suma = suma;
    }

}
