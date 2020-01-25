package automatKomorkowy.DwoDimension.ziarna;

public class AtLeastX implements GetMostOftenNeighbourRule{

    private int min;

    public AtLeastX(int min) {
        this.min = min;
    }

    @Override
    public WholeZiarnoInfo SelectNeighbour(ZiarnoCounter[] neighData, int neighDataCount) {

        int highest = 0;
        for(int i=0;i<neighDataCount;i++)
        {
            if(neighData[i].c >= min) return neighData[i].z;
        }
        return null;
    }
}
