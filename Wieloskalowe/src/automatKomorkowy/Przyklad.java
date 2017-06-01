package automatKomorkowy;

/**
 * Created by Lach on 2017-05-09.
 */
public class Przyklad {

    boolean[][]  entry;

    int countSasiads(boolean[][] wej, int x, int y,PrzykladSasiedztwo[] setting)
    {
        int suma = 0;
        if(x<0 || x>=entry.length || y<0 || y>=entry.length ) return 0;
        for(int i=0;i<setting.length;i++)
        {
            if(x+setting[i].x<0 || x+setting[i].x>=entry.length || y+setting[i].y<0 || y+setting[i].y>=entry.length ) continue;

            if(wej[x+setting[i].x][y+setting[i].y]) suma += 1;
        }
        return suma;
    }

    public static void main(String[] args)
    {

        Przyklad example = new Przyklad();
        example.entry = new boolean[100][100];

        // tu ustawiamy ich wartosci


    }

}
