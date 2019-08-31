package frido.samosprava.domain;

import java.util.HashSet;
import java.util.Set;

public class Prijmy {
    private String title;
    private Set<MoneyPrijmy> money = new HashSet<>();
    private Set<Prijmy> sub = new HashSet<>();

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Set<MoneyPrijmy> getMoney() {
        return money;
    }
    public void setMoney(Set<MoneyPrijmy> money) {
        this.money = money;
    }
    public Set<Prijmy> getSub() {
        return sub;
    }
    public void setSub(Set<Prijmy> sub) {
        this.sub = sub;
    }


}
