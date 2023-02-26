/*
 * Created by JFormDesigner on Fri Feb 17 18:25:30 CET 2023
 */

package GUI;

import Model.Luogo;
import Controller.Controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author vincenzo
 */
public class InserisciLuogo extends JFrame {
    public InserisciLuogo() {
        initComponents();
        Controller controller = new Controller();
        SpinnerNumberModel latModel = new SpinnerNumberModel(0.0, -90.0, 90.0, 0.1);
        SpinnerNumberModel lonModel = new SpinnerNumberModel(0.0, -180.0, 180.0, 0.1);
        latitudineSpinner.setModel(latModel);
        longitudineSpinner.setModel(lonModel);
        ArrayList<Luogo> luoghi = new ArrayList<>();
        try {
            luoghi = controller.recuperaTuttiLuoghiDB();
        } catch (SQLException s) {
            s.printStackTrace();
        }

        if (luoghi.isEmpty()) {
            exLuogoComboBox.setEnabled(false);
        } else {
            for (Luogo l : luoghi) {
                exLuogoComboBox.addItem("Nome: " + l.getNomeMnemonico() + " Lat: " + l.getLatitudine() + " Lon: " + l.getLatitudine());
            }
        }

        nuovoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nuovoButton.isSelected()) {
                    nomeLuogoCheckBox.setSelected(true);
                    coordinateCheckBox.setSelected(true);
                    if (esistenteButton.isSelected()) {
                        esistenteButton.setSelected(false);
                    }
                    if (nessunoButton.isSelected()) {
                        nessunoButton.setSelected(false);
                    }
                }
            }
        });

        esistenteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(esistenteButton.isSelected()) {
                    if (nessunoButton.isSelected()) {
                        nessunoButton.setSelected(false);
                    }
                    if (nuovoButton.isSelected()) {
                        nuovoButton.setSelected(false);
                    }
                }
            }
        });

        nessunoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nessunoButton.isSelected()) {
                    if (nuovoButton.isSelected()) {
                        nuovoButton.setSelected(false);
                    }
                    if (esistenteButton.isSelected()) {
                        esistenteButton.setSelected(false);
                    }
                }
            }
        });

        nomeLuogoCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!nomeLuogoCheckBox.isSelected()){
                    nomeLuogoText.setEnabled(false);
                }else {
                    nomeLuogoText.setEnabled(true);
                }
            }
        });

        coordinateCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!coordinateCheckBox.isSelected()){
                    latitudineSpinner.setEnabled(false);
                    longitudineSpinner.setEnabled(false);
                }else {
                    latitudineSpinner.setEnabled(true);
                    longitudineSpinner.setEnabled(true);
                }
            }
        });


    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        luogoPanel = new JPanel();
        label1 = new JLabel();
        nuovoButton = new JRadioButton();
        esistenteButton = new JRadioButton();
        coordinateCheckBox = new JCheckBox();
        nomeLuogoCheckBox = new JCheckBox();
        nomeLuogoText = new JTextField();
        latitudineSpinner = new JSpinner();
        longitudineSpinner = new JSpinner();
        exLuogoComboBox = new JComboBox();
        avantiLuogoButton = new JButton();
        indietroLuogoButton = new JButton();
        nessunoButton = new JRadioButton();

        //======== this ========
        var contentPane = getContentPane();

        //======== luogoPanel ========
        {
            luogoPanel.setPreferredSize(new Dimension(718, 399));

            //---- label1 ----
            label1.setText("INSERSCI UN NUOVO LUOGO O UTILIZZANE UNO ESISTENTE ?");

            //---- nuovoButton ----
            nuovoButton.setText("NUOVO");

            //---- esistenteButton ----
            esistenteButton.setText("ESISTENTE");

            //---- avantiLuogoButton ----
            avantiLuogoButton.setText("AVANTI");

            //---- indietroLuogoButton ----
            indietroLuogoButton.setText("INDIETRO");

            //---- nessunoButton ----
            nessunoButton.setText("NESSUNO");

            GroupLayout luogoPanelLayout = new GroupLayout(luogoPanel);
            luogoPanel.setLayout(luogoPanelLayout);
            luogoPanelLayout.setHorizontalGroup(
                luogoPanelLayout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, luogoPanelLayout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addGroup(luogoPanelLayout.createParallelGroup()
                            .addGroup(luogoPanelLayout.createSequentialGroup()
                                .addComponent(latitudineSpinner, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(longitudineSpinner, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(coordinateCheckBox))
                            .addGroup(luogoPanelLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(nomeLuogoText, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(nomeLuogoCheckBox)
                                .addGap(105, 105, 105)
                                .addComponent(exLuogoComboBox, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(240, Short.MAX_VALUE))
                    .addGroup(GroupLayout.Alignment.TRAILING, luogoPanelLayout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(nuovoButton)
                        .addGap(140, 140, 140)
                        .addComponent(esistenteButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 145, Short.MAX_VALUE)
                        .addComponent(nessunoButton)
                        .addGap(58, 58, 58))
                    .addGroup(GroupLayout.Alignment.TRAILING, luogoPanelLayout.createSequentialGroup()
                        .addContainerGap(178, Short.MAX_VALUE)
                        .addGroup(luogoPanelLayout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, luogoPanelLayout.createSequentialGroup()
                                .addComponent(indietroLuogoButton)
                                .addGap(18, 18, 18)
                                .addComponent(avantiLuogoButton)
                                .addGap(53, 53, 53))
                            .addGroup(GroupLayout.Alignment.TRAILING, luogoPanelLayout.createSequentialGroup()
                                .addComponent(label1, GroupLayout.PREFERRED_SIZE, 406, GroupLayout.PREFERRED_SIZE)
                                .addGap(133, 133, 133))))
            );
            luogoPanelLayout.setVerticalGroup(
                luogoPanelLayout.createParallelGroup()
                    .addGroup(luogoPanelLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(label1, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addGroup(luogoPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(nuovoButton)
                            .addComponent(nessunoButton)
                            .addComponent(esistenteButton))
                        .addGroup(luogoPanelLayout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, luogoPanelLayout.createSequentialGroup()
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 187, Short.MAX_VALUE)
                                .addGroup(luogoPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(indietroLuogoButton)
                                    .addComponent(avantiLuogoButton))
                                .addGap(38, 38, 38))
                            .addGroup(luogoPanelLayout.createSequentialGroup()
                                .addGroup(luogoPanelLayout.createParallelGroup()
                                    .addGroup(luogoPanelLayout.createSequentialGroup()
                                        .addGap(24, 24, 24)
                                        .addComponent(nomeLuogoText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(luogoPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(latitudineSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(longitudineSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(luogoPanelLayout.createSequentialGroup()
                                        .addGap(22, 22, 22)
                                        .addGroup(luogoPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addComponent(nomeLuogoCheckBox)
                                            .addComponent(exLuogoComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(coordinateCheckBox)))
                                .addGap(65, 159, Short.MAX_VALUE))))
            );
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(luogoPanel, GroupLayout.DEFAULT_SIZE, 717, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(luogoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel luogoPanel;
    private JLabel label1;
    private JRadioButton nuovoButton;
    private JRadioButton esistenteButton;
    private JCheckBox coordinateCheckBox;
    private JCheckBox nomeLuogoCheckBox;
    private JTextField nomeLuogoText;
    private JSpinner latitudineSpinner;
    private JSpinner longitudineSpinner;
    private JComboBox exLuogoComboBox;
    private JButton avantiLuogoButton;
    private JButton indietroLuogoButton;
    private JRadioButton nessunoButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
