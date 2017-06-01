package automatKomorkowy;

/**
 * Created by Lach on 2017-04-21.
 */
public class Transformator {

    private int rule;

    public Transformator(int rule)
    {
        this.rule = rule;
    }

    public int getRule() {
        return rule;
    }

    public void setRule(int rule) {
        this.rule = rule;
    }

    public boolean GetResult(boolean first, boolean second, boolean third)
    {
        int position = 0;

        if(third) position += 1;
        if(second) position += 2;
        if(first) position += 4;

        if((rule>>position)%2==1) return true;
        else return false;
    }

}
