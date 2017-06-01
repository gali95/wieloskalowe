
import automatKomorkowy.DwoDimension.DwoDimension;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by gali95 on 12.08.16.
 */
public class ImagePanel extends JPanel {

    DwoDimension source;

    public ImagePanel()
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
        double xPart = ImagePanel.this.getWidth()/source.getContent().length;
        double yPart = ImagePanel.this.getHeight()/source.getContent()[0].length;

        int xClicked = (int)(evt.getX()/xPart);
        int yClicked = (int)(evt.getY()/yPart);

        source.FieldClicked(xClicked,yClicked);
        this.updateUI();
    }

    @Override
    protected void paintComponent(Graphics g) {                                // w tej funkcji umieszczac bazgranie
        super.paintComponent(g);
        if(source==null) return;

        double xPart = ImagePanel.this.getWidth()/source.getContent().length;
        double yPart = ImagePanel.this.getHeight()/source.getContent()[0].length;

        for(int i=0;i<source.getContent().length;i++)
        {
            for(int j=0;j<source.getContent()[0].length;j++)
            {
                if(source.getContent()[i][j]) g.setColor(new Color(0,0,0));
                else g.setColor(new Color(255,255,255));                                // zmiana koloru wypelniania
                g.fillRect((int)(i*xPart),(int)(j*yPart),(int)xPart,(int)yPart);        // kwadracik
            }
        }
    }

}
