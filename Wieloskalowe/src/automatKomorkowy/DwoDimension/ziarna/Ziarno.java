package automatKomorkowy.DwoDimension.ziarna;

import automatKomorkowy.DwoDimension.ziarna.rekry.ZiarnoRekrystalizationInfo;

import java.awt.*;

/**
 * Created by Lach on 2017-05-05.
 */
public class Ziarno {

    private WholeZiarnoInfo mainInfo;
    private WholeZiarnoInfo freshMainInfo;
    private int monteCarloAlikes;
    private int indexX,indexY;

    public int getMonteCarloAlikes() {
        return monteCarloAlikes;
    }

    public void setMonteCarloAlikes(int monteCarloAlikes) {
        this.monteCarloAlikes = monteCarloAlikes;
    }

    public void ReplaceOldMainInfo()
    {
        if(freshMainInfo!=null) {
            setMainInfo(freshMainInfo);
            freshMainInfo = null;
        }
    }

    public int getIndexX() {
        return indexX;
    }

    public void setIndexX(int indexX) {
        this.indexX = indexX;
    }

    public int getIndexY() {
        return indexY;
    }

    public void setIndexY(int indexY) {
        this.indexY = indexY;
    }

    public WholeZiarnoInfo getFreshMainInfo() {
        return freshMainInfo;
    }

    public void setFreshMainInfo(WholeZiarnoInfo freshMainInfo) {
        this.freshMainInfo = freshMainInfo;
    }

    public WholeZiarnoInfo getMainInfo() {
        return mainInfo;
    }

    public void setMainInfo(WholeZiarnoInfo mainInfo) {
        this.mainInfo = mainInfo;
        if(this.mainInfo != null) isEmpty = false;
        else isEmpty = true;
    }

    public Ziarno()
    {
        isEmpty = true;
    }



    public Color getCol() {
        if(mainInfo != null)
            return mainInfo.getCol();
        else
            return Color.white;

    }

    public void setCol(Color col) {
        mainInfo.setCol(col);
    }

    //
    private boolean isEmpty;
    private int lastSuccess;
    //
    private ZiarnoRekrystalizationInfo rekrInfo;

    public ZiarnoRekrystalizationInfo getRekrInfo() {
        return rekrInfo;
    }

    public void setRekrInfo(ZiarnoRekrystalizationInfo rekrInfo) {
        this.rekrInfo = rekrInfo;
    }

    public void setStart(int startX, int startY)
    {
        mainInfo.setStartX(startX);
        mainInfo.setStartY(startY);
    }

    public int getStartX() {
        return mainInfo.getStartX();
    }

    public void setStartX(int startX) {
        mainInfo.setStartX(startX);
    }

    public int getStartY() {
        return mainInfo.getStartY();
    }

    public void setStartY(int startY) {
        mainInfo.setStartY(startY);
    }

    public int getLastSuccess() {
        return lastSuccess;
    }

    public void setLastSuccess(int lastSuccess) {
        this.lastSuccess = lastSuccess;
    }

    public boolean isEmpty() {
        return isEmpty;
    }
}
