package automatKomorkowy.DwoDimension.ziarna.ZiarnoNeighGetter.Export.Penta;

import automatKomorkowy.DwoDimension.ziarna.Ziarno;
import automatKomorkowy.DwoDimension.ziarna.ZiarnoNeighGetter.Export.Hexa.ZiarnoHexaLeftNeighGetter;
import automatKomorkowy.DwoDimension.ziarna.ZiarnoNeighGetter.Export.Hexa.ZiarnoHexaRightNeighGetter;
import automatKomorkowy.DwoDimension.ziarna.ZiarnoNeighGetter.ZiarnoNeighGetterIf;

import java.util.Random;

/**
 * Created by Lach on 2017-05-13.
 */
public class GetRandomPentaNeighGetter implements ZiarnoNeighGetterIf {

    public static ZiarnoNeighGetterIf get()
    {
        Random rand = new Random();
        int choice = rand.nextInt(4);
        switch (choice)
        {
            case 0:
                return new ZiarnoPentaLeftNeighGetter();
            case 1:
                return new ZiarnoPentaRightNeighGetter();
            case 2:
                return new ZiarnoPentaTopNeighGetter();
            case 3:
                return new ZiarnoPentaBotNeighGetter();
        }
        return null;
    }

    @Override
    public Ziarno[] GetNeigh(Ziarno[][] tab, int x, int y, boolean crossBorder) {
        ZiarnoNeighGetterIf randed = get();
        return randed.GetNeigh(tab,x,y,crossBorder);
    }

}
