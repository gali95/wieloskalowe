package automatKomorkowy.DwoDimension.ziarna.ZiarnoNeighGetter;

import automatKomorkowy.DwoDimension.ziarna.Ziarno;

/**
 * Created by Lach on 2017-05-05.
 */
public interface ZiarnoNeighGetterIf {

    public default Ziarno[] GetNeigh(Ziarno[][] tab, int x, int y)
    {
        return GetNeigh(tab,x,y,false);
    }

    public Ziarno[] GetNeigh(Ziarno[][] tab, int x, int y,boolean crossBorder);

}
