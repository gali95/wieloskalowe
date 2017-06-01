package automatKomorkowy.DwoDimension.ChangeStateIf;

/**
 * Created by Lach on 2017-04-28.
 */
public class BasicChangeState implements  NeighNumIf {
    @Override
    public boolean GetNewState(boolean current, int aliveNeigh) {
        if(current)
        {
            if(aliveNeigh==2 || aliveNeigh==3)
            {
                return true;
            }
            return false;
        }
        else
        {
            if(aliveNeigh==3) return true;
            else return false;
        }
    }
}
