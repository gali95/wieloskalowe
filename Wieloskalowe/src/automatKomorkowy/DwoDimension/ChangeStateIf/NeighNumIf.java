package automatKomorkowy.DwoDimension.ChangeStateIf;

/**
 * Created by Lach on 2017-04-28.
 */
public interface NeighNumIf {

    public boolean GetNewState(boolean current,int aliveNeigh);
    public default int GetAliveNeighCount(boolean[][] target, int x, int y)
    {
        int sum=0;
        for(int i=-1;i<2;i++)
        {
            for(int j=-1;j<2;j++)
            {
                if(i==0 && j==0) continue;
                try
                {
                    if(target[x+i][y+j]) sum++;
                }
                catch(IndexOutOfBoundsException ignored)
                {

                }
            }
        }
        return sum;
    }

}
