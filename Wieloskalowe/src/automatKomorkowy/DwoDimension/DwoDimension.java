package automatKomorkowy.DwoDimension;

import automatKomorkowy.DwoDimension.ChangeStateIf.BasicChangeState;
import automatKomorkowy.DwoDimension.FieldActions.DwoDimensionFieldAction;
import automatKomorkowy.DwoDimension.FieldActions.SwapFieldAction;

import java.util.Random;

/**
 * Created by Lach on 2017-04-28.
 */
public class DwoDimension {

    DwoDimensionFieldAction clickAction;
    BasicChangeState changeRule;

    public DwoDimension()
    {
        clickAction = new SwapFieldAction();
        changeRule = new BasicChangeState();
    }

    public void NextPhase()
    {
        boolean[][] newOne = new boolean[content.length][content[0].length];

        for(int i=0;i<newOne.length;i++)
        {
            for(int j=0;j<newOne[0].length;j++)
            {
                newOne[i][j] = changeRule.GetNewState(content[i][j],changeRule.GetAliveNeighCount(content,i,j));
            }
        }

        content = newOne;
    }

    private boolean content[][];

    public boolean[][] getContent() {
        return content;
    }

    public void FieldClicked(int x, int y)
    {
        clickAction.Action(this,x,y);
    }

    public void setContent(boolean[][] content) {
        this.content = content;
    }

    public void SetSize(int x, int y)
    {
        content = new boolean[x][y];
    }
    public void RandContent()
    {
        if(content==null) return;

        Random r = new Random();

        for(int i=0;i<content.length;i++)
        {
            for(int j=0;j<content[0].length;j++)
            {
                if(r.nextInt(1000)%2==0)
                {
                    content[i][j] = true;
                }
                else
                {
                    content[i][j] = false;
                }
            }
        }
    }

}
