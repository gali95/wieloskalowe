import automatKomorkowy.DwoDimension.DwoDimension;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Lach on 2017-04-28.
 */
public class zycie {

    private JPanel contentPane;
    private JButton button1;
    private JPanel paneee;
    private JTextField xSize;
    private JTextField ySize;
    private JButton losujButton;
    private ImagePanel k;

    private boolean paused;

    public void Next()
    {
        if(!paused)
        {
            k.source.NextPhase();
            k.updateUI();
        }
    }

    public void Resized()
    {
        k.source.SetSize(Integer.valueOf(xSize.getText()),Integer.valueOf(ySize.getText()));
        k.updateUI();
    }

    public zycie()
    {
        xSize.setText(Integer.toString(30));
        ySize.setText(Integer.toString(30));
        Resized();
        paused = false;
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==button1) {
                    paused = !paused;
                }
                if(e.getSource()==xSize)
                {
                    Resized();
                }
                if(e.getSource()==ySize)
                {
                    Resized();
                }
                if(e.getSource()==losujButton)
                {
                    k.source.RandContent();
                    k.updateUI();
                }
            }
        };

        button1.addActionListener(al);
        xSize.addActionListener(al);
        ySize.addActionListener(al);
        losujButton.addActionListener(al);
    }

    public static void main(String[] args) {
        zycie k = new zycie();
        JFrame frame = new JFrame("zycie");
        frame.setContentPane(k.contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        while(true)
        {
            try {
                Thread.sleep(100);
                k.Next();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void createUIComponents() {
       k = new ImagePanel();
       paneee = k;
        k.source = new DwoDimension();
        k.source.SetSize(300,300);
    }
}
