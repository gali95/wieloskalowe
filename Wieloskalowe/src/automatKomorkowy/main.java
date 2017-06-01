package automatKomorkowy;

/**
 * Created by Lach on 2017-04-21.
 */
public class main {

    public static void main(String[] args) {

        OneDimension example = new OneDimension(60);
        example.CreateArray(31);
        example.SetCell(16,true);

        for(int i=0;i<10;i++)
        {
            example.PrintValues();
            example.NextIteration();
        }

    }

}
