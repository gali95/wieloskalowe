package automatKomorkowy.DwoDimension.ziarna.rekry;

import automatKomorkowy.DwoDimension.ziarna.Ziarno;

/**
 * Created by Lach on 2017-05-19.
 */
public class ZiarnoRekrystalizationInfo {

    private boolean isOnEdge;
    private double actualCounter;
    private boolean spawnedZiarno;

    public boolean isOnEdge() {
        return isOnEdge;
    }

    public void setOnEdge(boolean onEdge) {
        isOnEdge = onEdge;
    }

    public double getActualCounter() {
        return actualCounter;
    }

    public void setActualCounter(double actualCounter) {
        this.actualCounter = actualCounter;
    }

}
