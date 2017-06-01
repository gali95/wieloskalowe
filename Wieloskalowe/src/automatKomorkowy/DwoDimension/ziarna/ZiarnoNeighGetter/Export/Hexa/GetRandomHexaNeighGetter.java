package automatKomorkowy.DwoDimension.ziarna.ZiarnoNeighGetter.Export.Hexa;

import automatKomorkowy.DwoDimension.ziarna.Ziarno;
import automatKomorkowy.DwoDimension.ziarna.ZiarnoNeighGetter.ZiarnoNeighGetterIf;

import java.util.Random;

/**
 * Created by Lach on 2017-05-13.
 */
public class GetRandomHexaNeighGetter implements ZiarnoNeighGetterIf{

    public static ZiarnoNeighGetterIf get()
    {
        Random rand = new Random();
        int choice = rand.nextInt(2);
        switch (choice)
        {
            case 0:
                return new ZiarnoHexaLeftNeighGetter();
            case 1:
                return new ZiarnoHexaRightNeighGetter();
        }
        return null;
    }

    @Override
    public Ziarno[] GetNeigh(Ziarno[][] tab, int x, int y, boolean crossBorder) {
        ZiarnoNeighGetterIf randed = get();
        return randed.GetNeigh(tab,x,y,crossBorder);
    }
}
