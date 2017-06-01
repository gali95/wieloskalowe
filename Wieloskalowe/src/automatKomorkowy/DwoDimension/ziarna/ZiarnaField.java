package automatKomorkowy.DwoDimension.ziarna;

import automatKomorkowy.DwoDimension.ziarna.ZiarnoNeighGetter.ZiarnoNeighGetterIf;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Lach on 2017-05-05.
 */
public class ZiarnaField {

    private ArrayList<Ziarno> contentSources;
    private Ziarno[][] content;
    private ZiarnoNeighGetterIf neighGetter;
    private boolean paused;
    private int freeCellsNumber;
    private boolean crossBorders;
    private boolean clickable;

    public ZiarnaField()
    {
        paused = true;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public boolean isCrossBorders() {
        return crossBorders;
    }

    public void setCrossBorders(boolean crossBorders) {
        this.crossBorders = crossBorders;
    }

    public ZiarnoNeighGetterIf getNeighGetter() {
        return neighGetter;
    }

    public void setNeighGetter(ZiarnoNeighGetterIf neighGetter) {
        this.neighGetter = neighGetter;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    private WholeZiarnoInfo GetMostOftenNeighbour(int x, int y)
    {
        class ZiarnoCounter
        {
            public WholeZiarnoInfo z;
            public int c;
        }

        Ziarno[] neigh = neighGetter.GetNeigh(content,x,y,crossBorders);

        if(neigh==null)
        {
            return null;
        }

        ZiarnoCounter[] ziarC = new ZiarnoCounter[neigh.length];
        int ziarCFilled = 0;
        boolean insertNewIntoZiarC;

        for(int i=0;i<neigh.length;i++)
        {
            if(neigh[i]==null || neigh[i].isEmpty()) continue;
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

    public void NextIteration() {

        if(content == null) return;
        if(paused) return;
        Ziarno newContent[][] = new Ziarno[content.length][content[0].length];
        for (int i = 0; i < content.length; i++)
        {
            for (int j = 0; j < content[0].length; j++) {
                newContent[i][j] = content[i][j];
                if (content[i][j] != null && content[i][j].isEmpty()) {
                    WholeZiarnoInfo hegemon = GetMostOftenNeighbour(i, j);
                    if (hegemon != null) {
                        newContent[i][j].setFreshMainInfo(hegemon);
                        freeCellsNumber--;
                    }
                }
            }
        }
        for (int i = 0; i < newContent.length; i++)
        {
            for (int j = 0; j < newContent[0].length; j++) {
                newContent[i][j].ReplaceOldMainInfo();
            }
        }
        content = newContent;

    }

    public void CreateEmpty(int x,int y)
    {
        content = new Ziarno[x][y];
        for(int i=0;i<x;i++)
        {
            for(int j=0;j<y;j++)
            {
                content[i][j] = new Ziarno();
            }
        }
        freeCellsNumber = x*y;
        contentSources = new ArrayList<>();
    }

    public int GetXSize()
    {
        return content.length;
    }

    public int GetYSize()
    {
        return content[0].length;
    }

    public Color GetZiarnoColor(int x,int y)
    {
        if(content[x][y] != null)
        {
            Color ret = content[x][y].getCol();
            if(ret != null) return ret;
            else
                return Color.white;
        }
        else return Color.white;
    }

    private ArrayList<Ziarno> CreateRandomZiarnos(int quantity)
    {
        Random rand = new Random();
        int[] randed = new int[3];
        ArrayList<Ziarno> ret = new ArrayList<>();
        Ziarno nowe;
        boolean alike;
        int rerollCounter;
        for(int i=0;i<quantity;i++)
        {
            rerollCounter = 0;
            do {
                if(rerollCounter>1000) return null;
                for(int Coli=0;Coli<3;Coli++)
                {
                    randed[Coli] = rand.nextInt(256);
                }
                alike = false;
                for(int j=0;j<ret.size();j++)
                {
                    if (!IsColorDifferent(randed,ret.get(j).getCol()))
                    {
                        alike = true;
                        rerollCounter++;
                        break;
                    }
                }
            } while (alike);

            nowe = new Ziarno();
            nowe.setMainInfo(new WholeZiarnoInfo());
            nowe.setCol(new Color(randed[0],randed[1],randed[2]));
            ret.add(nowe);
        }
        return ret;
    }
    private boolean IsColorDifferent(int[] arrCol,Color col)
    {
        if(Math.abs(col.getRed() - arrCol[0]) > 30) return true;
        if(Math.abs(col.getGreen() - arrCol[1]) > 30) return true;
        if(Math.abs(col.getBlue() - arrCol[2]) > 30) return true;
        return false;
    }

    public void SetNewRandomSources(int quantity)
    {
        if(freeCellsNumber < quantity) quantity = freeCellsNumber;
        if(quantity<1) return;
        PlaceZiarnosRandomly(CreateRandomZiarnos(quantity));
    }

    public void SetNewRegularSources(int startX, int startY,int endX, int endY, int columns, int rows)
    {
        if(startX < 0 || startY < 0 || endX <= startX || endY <= startY || endX > content.length || endY > content[0].length) return;

        PlaceZiarnosRegular(CreateRandomZiarnos(columns*rows),startX, startY,endX, endY, columns, rows);
    }

    public void SetNewRandomAwaySources(int quantity,int distance)
    {
        PlaceZiarnosAwayFromThemselves(CreateRandomZiarnos(quantity),distance);
    }

    private void PlaceZiarnosRandomly(ArrayList<Ziarno> ziarnosy)
    {
        if(ziarnosy == null) return;
        Random rand = new Random();

        int x,y,repeat;
        for(int i=0;i<ziarnosy.size();i++) {
            repeat = 0;
            do {
                x = rand.nextInt(content.length);
                y = rand.nextInt(content[0].length);
                repeat++;
                if(repeat>1000) return;
            } while(!content[x][y].isEmpty());
            content[x][y] = ziarnosy.get(i);
            ziarnosy.get(i).setStart(x,y);
            freeCellsNumber--;
            contentSources.add(ziarnosy.get(i));
        }
    }

    private void PlaceZiarnosRegular(ArrayList<Ziarno> ziarnosy, int startX, int startY,int endX, int endY, int columns, int rows)
    {
        if(ziarnosy == null) return;
        if(ziarnosy.size() != columns * rows) return;
        int spawnX,spawnY;
        for(int i=0;i<columns;i++)
        {
            for(int j=0;j<rows;j++)
            {
                spawnX = startX + i * ((endX - startX) / (columns-1));
                spawnY = startY + j * ((endY - startY) / (rows-1));
                if(content[spawnX][spawnY].isEmpty())
                {
                    content[spawnX][spawnY] = ziarnosy.get(i*rows+j);
                    contentSources.add(ziarnosy.get(i*rows+j));
                    content[spawnX][spawnY].setStart(spawnX,spawnY);
                    freeCellsNumber--;
                }
            }
        }
    }

    private void PlaceZiarnosAwayFromThemselves(ArrayList<Ziarno> ziarnosy,int distance)
    {
        if(ziarnosy == null) return;
        Random rand = new Random();

        int x,y,repeat;
        boolean disqualified;
        for(int i=0;i<ziarnosy.size();i++) {
            repeat = 0;
            do {
                if(repeat>1000) return;
                disqualified = false;
                x = rand.nextInt(content.length);
                y = rand.nextInt(content[0].length);
                repeat++;
                if(!content[x][y].isEmpty()) disqualified = true;
                for(int j=0;j<i;j++)
                {
                    double actDistance = Math.sqrt(Math.pow(x-ziarnosy.get(j).getStartX(),2)+Math.pow(y-ziarnosy.get(j).getStartY(),2));
                    //System.out.println(actDistance);
                    if(actDistance<distance)
                    {
                        disqualified = true;
                        break;
                    }
                }
            } while(disqualified);
            content[x][y] = ziarnosy.get(i);
            ziarnosy.get(i).setStart(x,y);
            freeCellsNumber--;
            contentSources.add(ziarnosy.get(i));
        }
    }

    public void FieldClicked(int x,int y)
    {
        if(!clickable) return;
        if(content[x][y].isEmpty())
        {
            Ziarno created = CreateRandomZiarnos(1).get(0);
            content[x][y] = created;
            freeCellsNumber--;
        }
    }

    public boolean IsDrawable()
    {
        if(content==null) return false;
        return true;
    }

}
