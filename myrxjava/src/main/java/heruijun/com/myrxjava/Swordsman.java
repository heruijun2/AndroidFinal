package heruijun.com.myrxjava;

/**
 * Created by heruijun on 2017/10/8.
 */
public class Swordsman {
    private String name;
    private String level;

    public Swordsman(String name, String level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
