package automatKomorkowy;

/**
 * Created by Lach on 2017-04-21.
 */
public class OneDimension {

    private boolean[] array;
    Transformator usedRule;
    int iteration;

    public Transformator getUsedRule() {
        return usedRule;
    }

    public void setUsedRule(Transformator usedRule) {
        this.usedRule = usedRule;
    }

    public OneDimension(int rule)
    {
        usedRule = new Transformator(rule);
    }

    public void CreateArray(int size) {
        array = new boolean[size];
        for (int i = 0; i < size; i++) array[i] = false;
        iteration = 0;
    }

    public boolean GetCell(int ind) {
        return array[ind];
    }

    public void SetCell(int ind, boolean value)
    {
        array[ind] = value;
    }

    public boolean[] Get3Values(int ind)
    {
        boolean[] ret= new boolean[3];
        if(ind==0)ret[0] = false;
        else ret[0] = array[ind-1];
        if(ind==array.length-1) ret[2] = false;
        else ret[2] = array[ind+1];
        ret[1] = array[ind];
        return ret;
    }

    public void NextIteration()
    {
        boolean[] temp = new boolean[array.length];
        for(int i=0;i<temp.length;i++)
        {
            boolean[] neigh = Get3Values(i);
            temp[i] = usedRule.GetResult(neigh[0],neigh[1],neigh[2]);
        }
        array = temp;
        iteration++;
    }
    public void PrintValues()
    {
        //System.out.println("iteration: "+iteration);
        for(int i=0;i<array.length;i++)
        {
            if(array[i]) System.out.print("X");
            else System.out.print(" ");
            System.out.print(" ");
        }
        System.out.println("");
    }


}
