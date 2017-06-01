package automatKomorkowy.DwoDimension.ziarna.rekry;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import automatKomorkowy.DwoDimension.ziarna.WholeZiarnoInfo;
import automatKomorkowy.DwoDimension.ziarna.ZiarnaField;
import automatKomorkowy.DwoDimension.ziarna.Ziarno;
import automatKomorkowy.DwoDimension.ziarna.ZiarnoNeighGetter.ZiarnoNeighGetterIf;

/**
 * Created by Lach on 2017-05-19.
 */
public class Rekrystalizacja {

    private ZiarnoNeighGetterIf neighGetter;  // TODO tak wlasciwie to wszystko poustawiac
    private boolean crossBorder;
    private double normalModifier,edgeModifier;  // TODO ustawic
    private int partsOfLeftover;
    private double energyNeededToZiarno;   // TODO ustawic
    private ZiarnaField ziarnaField;
    
    private ArrayList<Ziarno> Graniczne;

    
    
    public ZiarnoNeighGetterIf getNeighGetter() {
		return neighGetter;
	}

	public void setNeighGetter(ZiarnoNeighGetterIf neighGetter) {
		this.neighGetter = neighGetter;
	}

	public boolean isCrossBorder() {
		return crossBorder;
	}

	public void setCrossBorder(boolean crossBorder) {
		this.crossBorder = crossBorder;
	}

	public double getNormalModifier() {
		return normalModifier;
	}

	public void setNormalModifier(double normalModifier) {
		this.normalModifier = normalModifier;
	}

	public double getEdgeModifier() {
		return edgeModifier;
	}

	public void setEdgeModifier(double edgeModifier) {
		this.edgeModifier = edgeModifier;
	}

	public int getPartsOfLeftover() {
		return partsOfLeftover;
	}

	public void setPartsOfLeftover(int partsOfLeftover) {
		this.partsOfLeftover = partsOfLeftover;
	}

	public double getEnergyNeededToZiarno() {
		return energyNeededToZiarno;
	}

	public void setEnergyNeededToZiarno(double energyNeededToZiarno) {
		this.energyNeededToZiarno = energyNeededToZiarno;
	}

	public ZiarnaField getZiarnaField() {
		return ziarnaField;
	}

	public void setZiarnaField(ZiarnaField ziarnaField) {
		this.ziarnaField = ziarnaField;
	}

	public void SetPartsOfLeftover(Ziarno[][] content)  // TODO wywylac na poczatku
    {
    	partsOfLeftover = (int)(content.length * content[0].length * 0.01);
    }
    
    public void NextIteration(Ziarno[][] content)
    {
    	if(content == null) return;
    	double energyLeftover = 0;
        for (int i = 0; i < content.length; i++)
        {
            for (int j = 0; j < content[0].length; j++) {

                if (!content[i][j].getMainInfo().isRekrystalized()) {     // nie jest zrekrystalizowany
                	
                	double counterIncrease = GetIncreaseBase();   // zwiekszenie licznika w kazdej komorce
                	if(content[i][j].getRekrInfo().isOnEdge())
                	{
                		counterIncrease *= edgeModifier;
                		energyLeftover += GetIncreaseBase() - counterIncrease;
                	}
                	content[i][j].getRekrInfo().setActualCounter(content[i][j].getRekrInfo().getActualCounter() + counterIncrease);
                	
                }
            }
        }
        
        int replays = partsOfLeftover * 4;       ///// rozrzut resztek energi miedzy brzegi
        int chosen;
        Random rand = new Random();
        for( int i=0; i< partsOfLeftover && replays > 0;i++)
        {
        	chosen = rand.nextInt(Graniczne.size());
        	if(!Graniczne.get(chosen).getMainInfo().isRekrystalized())
        	{
        		Graniczne.get(chosen).getRekrInfo().setActualCounter(Graniczne.get(chosen).getRekrInfo().getActualCounter()+energyLeftover/partsOfLeftover);
        	}
        	else
        	{
        		i--;
        		replays--;
        	}
        }
        
        for (int i = 0; i < content.length; i++)                     ////  tworzenie nowych ziaren jesli przekrocza granice energi
        {
            for (int j = 0; j < content[0].length; j++) {
                if(!content[i][j].getMainInfo().isRekrystalized() && content[i][j].getRekrInfo().getActualCounter() > energyNeededToZiarno)
                {
                	Ziarno created = ziarnaField.CreateRandomZiarnos(1).get(0);
                	created.getMainInfo().setRekrystalized(true);
                	content[i][j].setFreshMainInfo(created.getMainInfo());
                }
            }
        }
        
        for (int i = 0; i < content.length; i++)        /// rozrost nowych ziaren powstalych przy rekrystalizacji
        {
            for (int j = 0; j < content[0].length; j++) {
            	if(!content[i][j].getMainInfo().isRekrystalized() && !content[i][j].getFreshMainInfo().isRekrystalized())
            	{
            		WholeZiarnoInfo hegemon = GetMostOftenNeighbour(content,i, j);
                	if (hegemon != null) {
                    	content[i][j].setFreshMainInfo(hegemon);
                	}
            	}
            }
        }
        for (int i = 0; i < content.length; i++)         ////   ustawienie nowych stanow na komorkach
        {
            for (int j = 0; j < content[0].length; j++) {
                content[i][j].ReplaceOldMainInfo();
            }
        }
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
        Graniczne = new ArrayList();
        
        for(int i=0;i<entry.length;i++)
        {
            for(int j=0;j<entry[0].length;j++)
            {
                boolean result = IsOnEdge(entry,i,j);
                if(result)
                {
                	Graniczne.add(entry[i][j]);
                }
            	entry[i][j].getRekrInfo().setOnEdge(result);
            }
        }
    }

    /*
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
    */

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

    public double GetIncreaseBase()  // TODO cała ta funkcja
    {
        return 5;
    }

    private WholeZiarnoInfo GetMostOftenNeighbour(Ziarno[][] content, int x, int y)
    {
        class ZiarnoCounter
        {
            public WholeZiarnoInfo z;
            public int c;
        }

        Ziarno[] neigh = neighGetter.GetNeigh(content,x,y,crossBorder);

        if(neigh==null)
        {
            return null;
        }

        ZiarnoCounter[] ziarC = new ZiarnoCounter[neigh.length];
        int ziarCFilled = 0;
        boolean insertNewIntoZiarC;

        for(int i=0;i<neigh.length;i++)
        {
            if(neigh[i]==null || neigh[i].isEmpty() || !neigh[i].getMainInfo().isRekrystalized()) continue;
            insertNewIntoZiarC = true;
            for(int j=0;j<ziarCFilled;j++)
            {
                if(neigh[i].getMainInfo()==ziarC[j].z)
                {
                    ziarC[j].c++;
                    insertNewIntoZiarC = false;
                    break;
                }

            }
            if(insertNewIntoZiarC)
            {
                ziarC[ziarCFilled] = new ZiarnoCounter();
                ziarC[ziarCFilled].z = neigh[i].getMainInfo();
                ziarC[ziarCFilled++].c = 1;
            }
        }

        int highest = 0;
        for(int i=0;i<ziarCFilled;i++)
        {
            if(ziarC[i].c > highest) highest = ziarC[i].c;
        }
        ArrayList<Integer> dolosowania = new ArrayList<>();
        for(int i=0;i<ziarCFilled;i++)
        {
            if(ziarC[i].c == highest) dolosowania.add(i);
        }
        Random rand = new Random();

        return ziarC[dolosowania.get(rand.nextInt(dolosowania.size()))].z;
    }

}
