package automatKomorkowy.DwoDimension.ziarna;

import java.awt.*;

/**
 * Created by Lach on 2017-05-19.
 */
public class WholeZiarnoInfo {

    private int startX,startY;
    private Color col;
    private boolean rekrystalized;
    private boolean wtracenie;
    public boolean stopped = false;

    public boolean isWtracenie() {
        return wtracenie;
    }

    public void setWtracenie(boolean wtracenie) {
        this.wtracenie = wtracenie;
    }

    public boolean isRekrystalized() {
		return rekrystalized;
	}

	public void setRekrystalized(boolean rekrystalized) {
		this.rekrystalized = rekrystalized;
	}

	public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public Color getCol() {
        return col;
    }

    public void setCol(Color col) {
        this.col = col;
    }
}
