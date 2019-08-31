package frido.samosprava.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Vydavky {
    private String program;
    private String title;
    private String comment;
    private List<String> use = new ArrayList<>();
    private Set<MoneyVydavky> money = new HashSet<>();
    private Set<Vydavky> sub = new HashSet<>();

    public String getProgram() {
        return program;
    }
    public void setProgram(String program) {
        this.program = program;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public List<String> getUse() {
        return use;
    }
    public void setUse(List<String> use) {
        this.use = use;
    }
    public Set<MoneyVydavky> getMoney() {
        return money;
    }
    public void setMoney(Set<MoneyVydavky> money) {
        this.money = money;
    }
    public Set<Vydavky> getSub() {
        return sub;
    }
    public void setSub(Set<Vydavky> sub) {
        this.sub = sub;
    }


}
