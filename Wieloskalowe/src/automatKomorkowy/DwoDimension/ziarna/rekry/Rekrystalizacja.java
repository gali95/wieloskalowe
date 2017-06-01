package automatKomorkowy.DwoDimension.ziarna.rekry;

import automatKomorkowy.DwoDimension.ziarna.Ziarno;
import automatKomorkowy.DwoDimension.ziarna.ZiarnoNeighGetter.ZiarnoNeighGetterIf;

/**
 * Created by Lach on 2017-05-19.
 */
public class Rekrystalizacja {

    private ZiarnoNeighGetterIf neighGetter;
    private boolean crossBorder;
    private int normalModifier,edgeModifier;

    public void NextIteration(Ziarno[][] entry)
    {
        if(entry == null) return;
        for(int i=0;i<entry.length;i++)
        {
            for(int j=0;j<entry[0].length;j++)
            {

            }
        }

        return;
    }

    private boolean IsOnEdge(Ziarno[][] entry, int x, int y)
    {
        Ziarno[] neighs = neighGetter.GetNeigh(entry,x,y,crossBorder);
        for(int i=0;i<neighs.length;i++)
        {
            if(neighs[i].getMainInfo() != entry[x][y].getMainInfo()) return true;
        }
        return false;
    }

    public void ResetRekrystalizationInfo(Ziarno[][] entry)
    {
        if(entry == null) return;
        for(int i=0;i<entry.length;i++)
        {
            for(int j=0;j<entry[0].length;j++)
            {
                entry[i][j].setRekrInfo(new ZiarnoRekrystalizationInfo());
            }
        }
    }

    public void SetIsOnEdgeInfo(Ziarno[][] entry)
    {
        if(entry == null) return;
        for(int i=0;i<entry.length;i++)
        {
            for(int j=0;j<entry[0].length;j++)
            {
                entry[i][j].getRekrInfo().setOnEdge(IsOnEdge(entry,i,j));
            }
        }
    }

    public void SetIsOnEdgeInfo(Ziarno[][] entry, int xStart,int yStart, int xEnd,int yEnd)
    {
        if(entry == null) return;
        for(int i=xStart;i<=xEnd;i++)
        {
            for(int j=yStart;j<yEnd;j++)
            {
                try {
                    entry[i][j].getRekrInfo().setOnEdge(IsOnEdge(entry,i,j));
                }
                catch (IndexOutOfBoundsException exc)
                {

                }
            }
        }
    }

    public void SetZiarnosCounterValue(Ziarno[][] entry, int newVal)
    {
        if(entry == null) return;
        for(int i=0;i<entry.length;i++)
        {
            for(int j=0;j<entry[0].length;j++)
            {
                entry[i][j].getRekrInfo().setActualCounter(newVal);
            }
        }
    }

    public double GetIncreaseBase()
    {
        return 5;
    }

}
