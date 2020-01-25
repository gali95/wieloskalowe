package automatKomorkowy.DwoDimension.ziarna;

import automatKomorkowy.DwoDimension.ziarna.ZiarnoNeighGetter.Export.ZiarnoFurtherMooreNeighGetter;
import automatKomorkowy.DwoDimension.ziarna.ZiarnoNeighGetter.Export.ZiarnoMooreneighGetter;
import automatKomorkowy.DwoDimension.ziarna.ZiarnoNeighGetter.Export.ZiarnoNeumanNeighGetter;
import automatKomorkowy.DwoDimension.ziarna.ZiarnoNeighGetter.ZiarnoNeighGetterIf;
import automatKomorkowy.DwoDimension.ziarna.rekry.Rekrystalizacja;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.sqrt;
import static java.util.Collections.shuffle;

/**
 * Created by Lach on 2017-05-05.
 */
public class ZiarnaField {

    private ArrayList<Ziarno> contentSources;
    private Ziarno blackNonGrowingZiarno;
    private Ziarno[][] content;
    private ZiarnoNeighGetterIf neighGetter;
    private boolean paused;
    private int freeCellsNumber;
    private boolean crossBorders;
    private boolean clickable;
    private Rekrystalizacja rekrInter;
    private boolean doRekryst;
    private int rekrLeftover;
    private boolean doMonteCarlo;
    private boolean grainBoundaryShapeControl;
    private double grainBoundaryShapeControlChance = 0.2;
    private LinkedList<Ziarno> randomZiarnos;
    private int selectedToolID;
    private ZiarnoNeighGetterIf hardcodedMooreNeighGetter;
    public boolean inclusionTypeSquare = true;
    public int inclusionSize = 1;

    public int getSelectedToolID() {
        return selectedToolID;
    }

    public void setSelectedToolID(int selectedToolID) {
        this.selectedToolID = selectedToolID;
    }

    public boolean isGrainBoundaryShapeControl() {
        return grainBoundaryShapeControl;
    }

    public void setGrainBoundaryShapeControl(boolean grainBoundaryShapeControl) {
        this.grainBoundaryShapeControl = grainBoundaryShapeControl;
    }

    public double getGrainBoundaryShapeControlChance() {
        return grainBoundaryShapeControlChance;
    }

    public void setGrainBoundaryShapeControlChance(double grainBoundaryShapeControlChance) {
        if(grainBoundaryShapeControlChance > 1)
        {
            grainBoundaryShapeControlChance = 1;
        }
        else if(grainBoundaryShapeControlChance < 0)
        {
            grainBoundaryShapeControlChance = 0;
        }
        this.grainBoundaryShapeControlChance = grainBoundaryShapeControlChance;
    }

    public void MonocolorEveryGrain()
    {
        for(Ziarno grain: contentSources)
        {
            if(grain.getMainInfo() != null)
                grain.getMainInfo().setCol(new Color(255,0,255));
        }
    }

    public boolean isDoMonteCarlo() {
        return doMonteCarlo;
    }

    public void setDoMonteCarlo(boolean doMonteCarlo) {
        this.doMonteCarlo = doMonteCarlo;
    }

    public void setRekrLeftover(int rekrLeftover) {
        this.rekrLeftover = rekrLeftover;
    }

    public Rekrystalizacja getRekrInter() {
        return rekrInter;
    }

    public boolean isDoRekryst() {
        return doRekryst;
    }

    public void setDoRekryst(boolean doRekryst) {
        this.doRekryst = doRekryst;
    }

    public ZiarnaField()
    {
        paused = true;
        rekrLeftover = 100;
        hardcodedMooreNeighGetter = new ZiarnoMooreneighGetter();
    }
    
    public void RekrystalizacjaInit()
    {
    	rekrInter = new Rekrystalizacja();
        rekrInter.setNeighGetter(neighGetter);
        rekrInter.setCrossBorder(crossBorders);
        rekrInter.setNormalModifier(0.2);
        rekrInter.setEdgeModifier(0.8);
        rekrInter.setEnergyNeededToZiarno(46842668.24);
        rekrInter.setZiarnaField(this);
        rekrInter.setModA(86710969050178.5);
        rekrInter.setModB(9.41268203527779);
        doRekryst = false;
        rekrInter.StartRekrystalization(content,rekrLeftover);
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
        if(rekrInter != null)
        {
            rekrInter.setNeighGetter(neighGetter);
        }
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    private boolean IsCellOnBorder(int x, int y)
    {
        Ziarno[] neigh = hardcodedMooreNeighGetter.GetNeigh(content,x,y,crossBorders);

        if(neigh==null)
        {
            return false;
        }

        ZiarnoCounter[] ziarC = new ZiarnoCounter[neigh.length];
        int ziarCFilled = 0;
        boolean insertNewIntoZiarC;

        for(int i=0;i<neigh.length;i++)
        {
            if(neigh[i].getMainInfo()==blackNonGrowingZiarno.getMainInfo()) continue;
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

        if(ziarCFilled > 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private WholeZiarnoInfo GetMostOftenNeighbour(int x, int y)
    {
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
            if(neigh[i]==null || neigh[i].isEmpty() || neigh[i].getMainInfo().stopped) continue;
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
        if(dolosowania.size() < 1)
        {
            return null;
        }


        return ziarC[dolosowania.get(rand.nextInt(dolosowania.size()))].z;
    }

    private WholeZiarnoInfo GetMostOftenNeighbour(int x, int y, GetMostOftenNeighbourRule rule, ZiarnoNeighGetterIf neighType)
    {
        Ziarno[] neigh = neighType.GetNeigh(content,x,y,crossBorders);

        if(neigh==null)
        {
            return null;
        }

        ZiarnoCounter[] ziarC = new ZiarnoCounter[neigh.length];
        int ziarCFilled = 0;
        boolean insertNewIntoZiarC;

        for(int i=0;i<neigh.length;i++)
        {
            if(neigh[i]==null || neigh[i].isEmpty() || neigh[i].getMainInfo().stopped) continue;
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

        return rule.SelectNeighbour(ziarC,ziarCFilled);
    }

    public void FreezeAll()
    {
        for(Ziarno grain: contentSources)
        {
            grain.getMainInfo().stopped = true;
        }
    }

    public void NextIteration() {

        if (content == null) return;
        if (paused) return;
        if (doRekryst) {
            rekrInter.NextIteration(content);
            return;
        }
        if (doMonteCarlo) {
            MonteCarloNextIteration();
            return;
        }
        if (grainBoundaryShapeControl)
        {
            GrainBoundaryShapeControlNextIteration();
            return;
        }
        Ziarno newContent[][] = new Ziarno[content.length][content[0].length];
        for (int i = 0; i < content.length; i++)
        {
            for (int j = 0; j < content[0].length; j++) {
                newContent[i][j] = content[i][j];
                if (content[i][j] != null && content[i][j].isEmpty()) {
                    if (content[i][j].getMainInfo() == blackNonGrowingZiarno.getMainInfo())
                    {
                        continue;
                    }
                    WholeZiarnoInfo hegemon = GetMostOftenNeighbour(i, j);
                    if (hegemon != null) {
                        newContent[i][j].setFreshMainInfo(hegemon);
                        freeCellsNumber--;
                        if(freeCellsNumber == 0)
                        {
                            FreezeAll();
                        }
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

    private void GrainBoundaryShapeControlNextIteration()
    {
        Ziarno newContent[][] = new Ziarno[content.length][content[0].length];
        for (int i = 0; i < content.length; i++)
        {
            for (int j = 0; j < content[0].length; j++) {
                newContent[i][j] = content[i][j];
                if (content[i][j] != null && content[i][j].isEmpty()) {


                    AtLeastX firstRule = new AtLeastX(5);
                    WholeZiarnoInfo newZiarno = GetMostOftenNeighbour(i,j,firstRule, new ZiarnoMooreneighGetter());

                    if(newZiarno == null)
                    {
                        AtLeastX secondAndThirdRule = new AtLeastX(3);
                        newZiarno = GetMostOftenNeighbour(i,j,secondAndThirdRule, new ZiarnoNeumanNeighGetter());
                        if(newZiarno == null)
                        {
                            newZiarno = GetMostOftenNeighbour(i,j,secondAndThirdRule, new ZiarnoFurtherMooreNeighGetter());
                            if(newZiarno == null)
                            {
                                Random rand = new Random();
                                double roll = rand.nextDouble();

                                if(roll < grainBoundaryShapeControlChance)
                                {
                                    newZiarno = GetMostOftenNeighbour(i,j);
                                }
                            }
                        }
                    }

                    if(newZiarno != null)
                    {
                        newContent[i][j].setFreshMainInfo(newZiarno);
                        freeCellsNumber--;
                        if(freeCellsNumber == 0)
                        {
                            FreezeAll();
                        }
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
        doRekryst = false;
        doMonteCarlo = false;

        blackNonGrowingZiarno = new Ziarno();
        blackNonGrowingZiarno.setMainInfo(new WholeZiarnoInfo());
        blackNonGrowingZiarno.setCol(new Color(0,0,0));
        blackNonGrowingZiarno.getMainInfo().stopped = true;
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

    public ArrayList<Ziarno> CreateRandomZiarnos(int quantity)
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
                for(int j=0;j<contentSources.size();j++)
                {
                	if (!IsColorDifferent(randed,contentSources.get(j).getCol()))
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
        if(Math.abs(col.getRed() - arrCol[0]) > 5) return true;
        if(Math.abs(col.getGreen() - arrCol[1]) > 5) return true;
        if(Math.abs(col.getBlue() - arrCol[2]) > 5) return true;
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
        if(selectedToolID == 0)   // click spawn
        {
            if(content[x][y].isEmpty())
            {
                Ziarno created = CreateRandomZiarnos(1).get(0);
                content[x][y] = created;
                freeCellsNumber--;
            }
        }
        else if(selectedToolID == 1)
        {
            AddInclusion(x,y);
        }
        else if(selectedToolID == 2)
        {
            RemoveGrain(content[x][y].getMainInfo());
        }

    }

    public void DrawBlackCircle(int centerX, int centerY)
    {
        for (int i = 0; i < content.length; i++)
        {
            for (int j = 0; j < content[0].length; j++)
            {
                double distance = sqrt((i - centerX)*(i - centerX) + (j - centerY)*(j - centerY));
                if(distance < inclusionSize)
                {
                    content[i][j].setMainInfo(blackNonGrowingZiarno.getMainInfo());
                    content[i][j].setCol(content[i][j].getMainInfo().getCol());
                }
            }
        }
    }

    public void DrawBlackSquare(int centerX, int centerY)
    {
        for (int i = 0; i < content.length; i++)
        {
            for (int j = 0; j < content[0].length; j++)
            {
                double distance = sqrt((i - centerX)^2 + (j - centerY)^2);
                if(abs(i - centerX) < inclusionSize && abs(j - centerY) < inclusionSize)
                {
                    content[i][j].setMainInfo(blackNonGrowingZiarno.getMainInfo());
                    content[i][j].setCol(content[i][j].getMainInfo().getCol());
                }
            }
        }
    }

    public void AddInclusion(int x,int y) {

        if (inclusionTypeSquare)
        {
            DrawBlackSquare(x,y);
        }
        else
        {
            DrawBlackCircle(x,y);
        }
    }

    public double DrawBoundaries()
    {
        double cellMaxnumber = content.length * content[0].length;
        double boundariesNumber = 0;
        for (int i = 0; i < content.length; i++)
        {
            for (int j = 0; j < content[0].length; j++)
            {
                if(IsCellOnBorder(i,j))
                {
                    content[i][j].setMainInfo(blackNonGrowingZiarno.getMainInfo());
                    content[i][j].setCol(content[i][j].getMainInfo().getCol());
                    boundariesNumber++;
                }
            }
        }
        return boundariesNumber / cellMaxnumber;
    }

    public void ClearButBlack()
    {
        for (int i = 0; i < content.length; i++)
        {
            for (int j = 0; j < content[0].length; j++)
            {
                if(content[i][j].getMainInfo() != blackNonGrowingZiarno.getMainInfo())
                {
                    content[i][j].setMainInfo(null);
                    freeCellsNumber++;
                }
            }
        }
        contentSources = null;
    }

    public ArrayList<Vector2> GetPlacesOnBorder()
    {
        ArrayList<Vector2> borderOnes = new ArrayList<>();
        for (int i = 0; i < content.length; i++)
        {
            for (int j = 0; j < content[0].length; j++)
            {
                if(IsCellOnBorder(i,j))
                {
                    Vector2 added = new Vector2();
                    added.x = i;
                    added.y = j;
                    borderOnes.add(added);
                }
            }
        }
        return borderOnes;
    }

    public void AddInclusionsAtRandomBoundaries(int number)
    {
        double minDistance = 2* inclusionSize;
        ArrayList<Vector2> possiblePlaces = GetPlacesOnBorder();
        Random rand = new Random();
        Vector2 newInclusionPlaces[] = new Vector2[number];
        Vector2 newLocation;
        for(int i=0;i<number;i++)
        {
            int tryNumber = 10000;
            boolean enoughDistance = true;
            do {
                newLocation = possiblePlaces.get(rand.nextInt(possiblePlaces.size()));
                for(int j=0;j<i;j++)
                {
                     double distance = sqrt((newLocation.x - newInclusionPlaces[j].x)*(newLocation.x - newInclusionPlaces[j].x) + (newLocation.y - newInclusionPlaces[j].y)*(newLocation.y - newInclusionPlaces[j].y));
                     if(distance < minDistance)
                     {
                         enoughDistance = false;
                         break;
                     }
                }
                tryNumber--;
                if(tryNumber == 0)
                {
                    System.out.println("failed looking for optimal places, try again!");
                }
            }
            while(!enoughDistance);
            newInclusionPlaces[i] = new Vector2();
            newInclusionPlaces[i].x = newLocation.x;
            newInclusionPlaces[i].y = newLocation.y;
        }
        for(int i=0;i<number;i++)
        {
            AddInclusion(newInclusionPlaces[i].x,newInclusionPlaces[i].y);
        }
    }

    public void RemoveGrain(WholeZiarnoInfo toRemove)
    {
        for (int i = 0; i < content.length; i++)
        {
            for (int j = 0; j < content[0].length; j++)
            {
                if(content[i][j].getMainInfo() == toRemove)
                {
                    content[i][j].setMainInfo(null);
                    freeCellsNumber++;
                }
            }
        }
        contentSources.remove(toRemove);
    }

    public boolean IsDrawable()
    {
        if(content==null) return false;
        return true;
    }

    public void MonteCarloInit(int quantity)
    {
        CreateEmpty(GetXSize(),GetYSize());
        contentSources.addAll(CreateRandomZiarnos(quantity));
        Random rand = new Random();

        randomZiarnos = new LinkedList<>();
        for(int i=0;i<content.length;i++)
        {
            for(int j=0;j<content[0].length;j++)
            {
                content[i][j].setMainInfo(contentSources.get(rand.nextInt(contentSources.size())).getMainInfo());
                content[i][j].setIndexX(i);
                content[i][j].setIndexY(j);
                randomZiarnos.add(content[i][j]);
            }
        }
    }

    private int MonteCarloCountOthers(int x,int y)
    {
        Ziarno[] neigh = neighGetter.GetNeigh(content,x,y,crossBorders);

        if(neigh==null)
        {
            return 0;
        }
        int ret = 0;
        for(int i=0;i<neigh.length;i++)
        {
            if(neigh[i]!=null)
            {
                if(neigh[i].getMainInfo()!=content[x][y].getMainInfo())
                    ret++;
            }
        }
        return ret;
    }

    private void MonteCarloNextIteration()
    {
        shuffle(randomZiarnos);
        ListIterator<Ziarno> it = randomZiarnos.listIterator(0);
        Random rand = new Random();
        Ziarno selected = randomZiarnos.getFirst();
        int previousVal;
        WholeZiarnoInfo prevInfo;

        try {
            while (true) {
                previousVal = MonteCarloCountOthers(selected.getIndexX(), selected.getIndexY());
                prevInfo = selected.getMainInfo();

                selected.setMainInfo(contentSources.get(rand.nextInt(contentSources.size())).getMainInfo());
                if (previousVal < MonteCarloCountOthers(selected.getIndexX(), selected.getIndexY()))
                    selected.setMainInfo(prevInfo);

                if (!it.hasNext()) break;
                selected = it.next();
            }
        }
        catch (NullPointerException c)
        {

        }
    }

}
