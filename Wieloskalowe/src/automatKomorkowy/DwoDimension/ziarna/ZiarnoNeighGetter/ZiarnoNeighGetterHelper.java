package automatKomorkowy.DwoDimension.ziarna.ZiarnoNeighGetter;

import automatKomorkowy.DwoDimension.ziarna.Ziarno;

/**
 * Created by Lach on 2017-05-12.
 */
public class ZiarnoNeighGetterHelper {

    public static Ziarno[] GetZiarnosWithOffset(Ziarno[][] source, int startX, int startY, int[][] offsets,boolean crossBorders)
    {
        Ziarno[] ret;
        if(startX<0 || startX>=source.length || startY<0 || startY>=source[0].length ) return null;
        ret = new Ziarno[offsets.length];
        int sasiadX,sasiadY;
        boolean any = false;
        for(int i=0;i<offsets.length;i++)
        {
            sasiadX = startX+offsets[i][0];
            sasiadY = startY+offsets[i][1];

            if(!crossBorders) {
                if (sasiadX < 0 || sasiadX >= source.length || sasiadY < 0 || sasiadY >= source[0].length) continue;
            }
            else
                {
                    while(sasiadX<0) sasiadX+=source.length;
                    while(sasiadY<0) sasiadY+=source[0].length;
                }


            if(!source[(sasiadX)%source.length][(sasiadY)%source[0].length].isEmpty())
            {
                any = true;

            }
            ret[i] = source[sasiadX%source.length][sasiadY%source[0].length];

        }
        if(any) return ret;
        else return null;
    }
    
}
