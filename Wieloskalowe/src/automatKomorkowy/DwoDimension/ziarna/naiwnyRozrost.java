package automatKomorkowy.DwoDimension.ziarna;

import automatKomorkowy.DwoDimension.ziarna.ZiarnoNeighGetter.Export.AvaibleNeighGetters;
import automatKomorkowy.DwoDimension.ziarna.ZiarnoNeighGetter.Export.ZiarnoMooreneighGetter;

import javax.swing.*;
import java.awt.event.*;

/**
 * Created by Lach on 2017-05-09.
 */
public class naiwnyRozrost{
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
    private JButton rekrystalizationButton;
    private JTextField textField1;
    private JButton monteCarlosButton;
    private JTextField textField2;
    private JCheckBox shapeControlCheckBox;
    private JTextField shapeControlChance;
    private JTextPane shapeControlChanceTextPane;
    private JLabel activeToolLabel;
    private JButton monocolorRemainingButton;
    private JComboBox comboBox2;
    private JButton drawBoundariesButton;
    private JButton eraseButBoundariesButton;
    private JComboBox comboBox3;
    private JTextField textField3;
    private JButton placeInclusionsOnBorderButton;
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
        //comboBox1.setModel(new DefaultComboBoxModel(AvaibleNeighGetters.values()));
        //k.setNeighGetter(AvaibleNeighGetters.MOORE.getObj());

        ActionListener act = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(e.getSource()==button1)
                {
                    k.setPaused(!k.isPaused());
                    SetPauseButtonLabel();
                }
                else if(e.getSource()==monocolorRemainingButton)
                {
                    k.MonocolorEveryGrain();
                }
                else if(e.getSource()==shapeControlChance)
                {
                    k.setGrainBoundaryShapeControlChance(Double.parseDouble(shapeControlChance.getText()));
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
                else if(e.getSource()==shapeControlCheckBox)
                {
                    k.setGrainBoundaryShapeControl(shapeControlCheckBox.isSelected());
                }
                else if(e.getSource()==comboBox1)
                {
                    k.setNeighGetter(((AvaibleNeighGetters)comboBox1.getSelectedItem()).getObj());
                }
                else if(e.getSource()==spawnRównomiernyButton)
                {
                    k.SetNewRegularSources(0,0,k.GetXSize()-1,k.GetYSize()-1,Integer.parseInt(regularSpawnXTextField.getText()),Integer.parseInt(regularSpawnYTextField.getText()));
                }
                else if(e.getSource()==spawnZPromieniemButton)
                {
                    k.SetNewRandomAwaySources(Integer.parseInt(randomCountTextField.getText()),Integer.parseInt(promienTextField1.getText()));
                }
                else if(e.getSource()==rekrystalizationButton)
                {
                    k.RekrystalizacjaInit();
                    k.setDoRekryst(true);
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
        spawnZPromieniemButton.addActionListener(act);
        rekrystalizationButton.addActionListener(act);
        shapeControlCheckBox.addActionListener(act);
        shapeControlChance.addActionListener(act);
        monocolorRemainingButton.addActionListener(act);
        textField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                k.setRekrLeftover(Integer.valueOf(textField1.getText()));
            }
        });
        monteCarlosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                k.MonteCarloInit(Integer.valueOf(textField2.getText()));
                k.setDoMonteCarlo(true);
            }
        });

        mainPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                super.keyReleased(keyEvent);
            }
        });
        comboBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (comboBox2.getSelectedIndex() == 0) {
                    //activeToolLabel.setText("click spawn");
                    k.setSelectedToolID(0);
                } else if (comboBox2.getSelectedIndex() == 1) {
                    //activeToolLabel.setText("inclusion");
                    k.setSelectedToolID(1);
                } else if (comboBox2.getSelectedIndex() == 2) {
                    //activeToolLabel.setText("erase");
                    k.setSelectedToolID(2);
                }
            }
        });
        drawBoundariesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                double percentage = k.DrawBoundaries();
                System.out.println(percentage);
            }
        });
        eraseButBoundariesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                k.ClearButBlack();
            }
        });
        comboBox3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (comboBox2.getSelectedIndex() == 0) {
                    //activeToolLabel.setText("click spawn");
                    k.inclusionTypeSquare = true;
                } else if (comboBox2.getSelectedIndex() == 1) {
                    //activeToolLabel.setText("inclusion");
                    k.inclusionTypeSquare = false;
                }
            }
        });
        textField3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                k.inclusionSize = Integer.valueOf(textField3.getText());
            }
        });
        placeInclusionsOnBorderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                k.AddInclusionsAtRandomBoundaries(Integer.parseInt(randomCountTextField.getText()));
            }
        });
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
        gui.k.setClickable(true);
        gui.k.setSelectedToolID(0);

        while(true)
        {
            try {
                Thread.sleep(10);
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
            button1.setText("continue");
        }
        else
        {
            button1.setText("pause");
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
