package automatKomorkowy.DwoDimension.ziarna;

import automatKomorkowy.DwoDimension.ziarna.ZiarnoNeighGetter.Export.AvaibleNeighGetters;
import automatKomorkowy.DwoDimension.ziarna.ZiarnoNeighGetter.Export.ZiarnoMooreneighGetter;
import automatKomorkowy.DwoDimension.ziarna.ZiarnoNeighGetter.ZiarnoNeighGetterIf;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Lach on 2017-05-09.
 */
public class naiwnyRozrost {
    private JButton button1;
    private JTextField sizeXTextField;
    private JPanel panel1;
    public JPanel mainPane;
    private JTextField sizeYTextField;
    private JTextField randomCountTextField;
    private JButton spawnButton;
    private JCheckBox crossBordersCheckBox;
    private JComboBox comboBox1;
    private JButton spawnRównomiernyButton;
    private JTextField regularSpawnYTextField;
    private JTextField regularSpawnXTextField;
    private JButton spawnZPromieniemButton;
    private JTextField promienTextField1;
    private JCheckBox spawnowanieKlikniecemCheckBox;
    public boolean paused;
    public ZiarnaField k;
    public GUIScreen sele;
    private AvaibleNeighGetters selectedGetter;

    private void createUIComponents() {
        // TODO: place custom component creation code here
        sele = new GUIScreen();
        panel1 = sele;
    }

    public void SetZiarnaField(ZiarnaField noffy)
    {
        k = noffy;
        k.setNeighGetter(AvaibleNeighGetters.MOORE.getObj());
        sele.source = noffy;
    }

    public naiwnyRozrost()
    {
        comboBox1.setModel(new DefaultComboBoxModel(AvaibleNeighGetters.values()));

        ActionListener act = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==button1)
                {
                    k.setPaused(!k.isPaused());
                    SetPauseButtonLabel();
                }
                else if(e.getSource()==spawnButton)
                {
                    SpawnNewZiarnosButton();
                }
                else if(e.getSource()==sizeXTextField || e.getSource()==sizeYTextField)
                {
                    SetNewSizeButton();
                }
                else if(e.getSource()==crossBordersCheckBox)
                {
                    k.setCrossBorders(crossBordersCheckBox.isSelected()) ;
                }
                else if(e.getSource()==comboBox1)
                {
                    k.setNeighGetter(((AvaibleNeighGetters)comboBox1.getSelectedItem()).getObj());
                }
                else if(e.getSource()==spawnRównomiernyButton)
                {
                    k.SetNewRegularSources(0,0,k.GetXSize()-1,k.GetYSize()-1,Integer.parseInt(regularSpawnXTextField.getText()),Integer.parseInt(regularSpawnYTextField.getText()));
                }
                else if(e.getSource()==spawnowanieKlikniecemCheckBox)
                {
                    k.setClickable(spawnowanieKlikniecemCheckBox.isSelected());
                }
                else if(e.getSource()==spawnZPromieniemButton)
                {
                    k.SetNewRandomAwaySources(Integer.parseInt(randomCountTextField.getText()),Integer.parseInt(promienTextField1.getText()));
                }
            }
        };
        button1.addActionListener(act);
        spawnButton.addActionListener(act);
        sizeXTextField.addActionListener(act);
        sizeYTextField.addActionListener(act);
        crossBordersCheckBox.addActionListener(act);
        comboBox1.addActionListener(act);
        spawnRównomiernyButton.addActionListener(act);
        spawnowanieKlikniecemCheckBox.addActionListener(act);
        spawnZPromieniemButton.addActionListener(act);
    }

    public static void main(String[] args) {
        naiwnyRozrost gui = new naiwnyRozrost();
        JFrame frame = new JFrame("naiwnyRozrost");
        frame.setContentPane(gui.mainPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        gui.SetZiarnaField(new ZiarnaField());
        gui.k.setNeighGetter(new ZiarnoMooreneighGetter());

        while(true)
        {
            try {
                Thread.sleep(100);
                gui.k.NextIteration();
                gui.sele.updateUI();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void SetPauseButtonLabel()
    {
        boolean paused = k.isPaused();
        if(paused)
        {
            button1.setText("Wznow");
        }
        else
        {
            button1.setText("Pauza");
        }
    }
    public void SetNewSizeButton()
    {
        k.CreateEmpty(Integer.parseInt(sizeXTextField.getText()),Integer.parseInt(sizeYTextField.getText()));
    }
    public void SpawnNewZiarnosButton()
    {
        k.SetNewRandomSources(Integer.parseInt(randomCountTextField.getText()));
    }
}
