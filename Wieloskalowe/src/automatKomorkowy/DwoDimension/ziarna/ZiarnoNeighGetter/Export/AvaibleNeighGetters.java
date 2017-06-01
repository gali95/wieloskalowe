package automatKomorkowy.DwoDimension.ziarna.ZiarnoNeighGetter.Export;

import automatKomorkowy.DwoDimension.ziarna.ZiarnoNeighGetter.Export.Hexa.GetRandomHexaNeighGetter;
import automatKomorkowy.DwoDimension.ziarna.ZiarnoNeighGetter.Export.Hexa.ZiarnoHexaLeftNeighGetter;
import automatKomorkowy.DwoDimension.ziarna.ZiarnoNeighGetter.Export.Hexa.ZiarnoHexaRightNeighGetter;
import automatKomorkowy.DwoDimension.ziarna.ZiarnoNeighGetter.Export.Penta.*;
import automatKomorkowy.DwoDimension.ziarna.ZiarnoNeighGetter.ZiarnoNeighGetterIf;

/**
 * Created by Lach on 2017-05-13.
 */
public enum AvaibleNeighGetters {

    MOORE ("Moore'a",ZiarnoMooreneighGetter.class),
    NEUMAN ("Neumana",ZiarnoNeumanNeighGetter.class),
    HEX_R ("Heksagonalne Prawe", ZiarnoHexaRightNeighGetter.class),
    HEX_L ("Heksagonalne Lewe", ZiarnoHexaLeftNeighGetter.class),
    HEX_RAND("Heksagonalne Losowe", GetRandomHexaNeighGetter.class),
    PENTA_T("Pentagonalne Górne", ZiarnoPentaTopNeighGetter.class),
    PENTA_B("Pentagonalne Dolne", ZiarnoPentaBotNeighGetter.class),
    PENTA_R("Pentagonalne Prawe", ZiarnoPentaRightNeighGetter.class),
    PENTA_L("Pentagonalne Lewe", ZiarnoPentaLeftNeighGetter.class),
    PENTA_RAND ("Pentagonalne Losowe" , GetRandomPentaNeighGetter.class);


    AvaibleNeighGetters(String name,Class selObj)
    {
        this.checkBoxLabel = name;
        this.selObj = selObj;
    }

    private final String checkBoxLabel;
    private Class selObj;

    @Override
    public String toString()
    {
        return checkBoxLabel;
    }

    public ZiarnoNeighGetterIf getObj()
    {
        try {
            return (ZiarnoNeighGetterIf)selObj.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
