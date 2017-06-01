package automatKomorkowy.DwoDimension.ziarna;

import automatKomorkowy.DwoDimension.DwoDimension;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Lach on 2017-05-09.
 */
public class GUIScreen extends JPanel {

    public ZiarnaField source;

    public GUIScreen()
    {
        setPreferredSize(new Dimension(300,300));
        addMouseListener(new MouseAdapter() {                       //  obsłużenie klikniecia
            public void mouseReleased(MouseEvent evt) {             //
                ImagePanelMouseReleased(evt);                       //
            }                                                       //
        });                                                         //
    }

    public void ImagePanelMouseReleased(MouseEvent evt)                 // tutaj juz operuje na logicznej czesci
    {
        double xPart = GUIScreen.this.getWidth()/source.GetXSize();
        double yPart = GUIScreen.this.getHeight()/source.GetYSize();

        int xClicked = (int)(evt.getX()/xPart);
        int yClicked = (int)(evt.getY()/yPart);

        source.FieldClicked(xClicked,yClicked);
        this.updateUI();
    }

    @Override
    protected void paintComponent(Graphics g) {                                // w tej funkcji umieszczac bazgranie
        super.paintComponent(g);
        if(source==null) return;
        if(!source.IsDrawable()) return;

        double xPart = GUIScreen.this.getWidth()/source.GetXSize();
        double yPart = GUIScreen.this.getHeight()/source.GetYSize();

        for(int i=0;i<source.GetXSize();i++)
        {
            for(int j=0;j<source.GetYSize();j++)
            {
                g.setColor(source.GetZiarnoColor(i,j));
                                    // zmiana koloru wypelniania
                g.fillRect((int)(i*xPart),(int)(j*yPart),(int)xPart,(int)yPart);        // kwadracik
            }
        }
    }

}
