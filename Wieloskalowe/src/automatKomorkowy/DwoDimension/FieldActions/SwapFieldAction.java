package automatKomorkowy.DwoDimension.FieldActions;

import automatKomorkowy.DwoDimension.DwoDimension;

/**
 * Created by Lach on 2017-04-28.
 */
public class SwapFieldAction implements DwoDimensionFieldAction{


    @Override
    public void Action(DwoDimension target, int x, int y) {
        target.getContent()[x][y] = !target.getContent()[x][y];
    }
}
