/*
 * Created by JFormDesigner on Fri Feb 17 18:25:30 CET 2023
 */

package GUI.Reference;

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
 * The type Inserisci luogo.
 *
 * @author vincenzo
 */
public class InserisciLuogo extends JFrame {
    /**
     * Instantiates a new Inserisci luogo.
     */
    public InserisciLuogo() {
        initComponents();

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        luogoPanel = new JPanel();
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
        label1 = new JLabel();

        //======== this ========
        var contentPane = getContentPane();

        //======== luogoPanel ========
        {
            luogoPanel.setPreferredSize(new Dimension(600, 399));
            luogoPanel.setBackground(new Color(0x161616));

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

            //---- label1 ----
            label1.setText("INSERISCI UN LUOGO :");
            label1.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 24));

            GroupLayout luogoPanelLayout = new GroupLayout(luogoPanel);
            luogoPanel.setLayout(luogoPanelLayout);
            luogoPanelLayout.setHorizontalGroup(
                luogoPanelLayout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, luogoPanelLayout.createSequentialGroup()
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(luogoPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(luogoPanelLayout.createSequentialGroup()
                                .addComponent(indietroLuogoButton)
                                .addGap(18, 18, 18)
                                .addComponent(avantiLuogoButton))
                            .addComponent(nessunoButton))
                        .addGap(76, 76, 76))
                    .addGroup(luogoPanelLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(luogoPanelLayout.createParallelGroup()
                            .addComponent(label1)
                            .addGroup(luogoPanelLayout.createSequentialGroup()
                                .addGroup(luogoPanelLayout.createParallelGroup()
                                    .addGroup(luogoPanelLayout.createSequentialGroup()
                                        .addGap(37, 37, 37)
                                        .addComponent(nuovoButton))
                                    .addGroup(luogoPanelLayout.createSequentialGroup()
                                        .addGroup(luogoPanelLayout.createParallelGroup()
                                            .addGroup(luogoPanelLayout.createSequentialGroup()
                                                .addComponent(latitudineSpinner, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(longitudineSpinner, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
                                            .addComponent(nomeLuogoText, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(luogoPanelLayout.createParallelGroup()
                                            .addComponent(coordinateCheckBox)
                                            .addComponent(nomeLuogoCheckBox))))
                                .addGap(6, 6, 6)
                                .addGroup(luogoPanelLayout.createParallelGroup()
                                    .addGroup(luogoPanelLayout.createSequentialGroup()
                                        .addGap(43, 43, 43)
                                        .addComponent(esistenteButton))
                                    .addGroup(luogoPanelLayout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addComponent(exLuogoComboBox, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(203, Short.MAX_VALUE))
            );
            luogoPanelLayout.setVerticalGroup(
                luogoPanelLayout.createParallelGroup()
                    .addGroup(luogoPanelLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(label1)
                        .addGap(66, 66, 66)
                        .addGroup(luogoPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(nessunoButton)
                            .addComponent(nuovoButton)
                            .addComponent(esistenteButton))
                        .addGroup(luogoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addGroup(GroupLayout.Alignment.TRAILING, luogoPanelLayout.createSequentialGroup()
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(nomeLuogoCheckBox, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
                            .addGroup(luogoPanelLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(luogoPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(nomeLuogoText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(exLuogoComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                        .addGroup(luogoPanelLayout.createParallelGroup()
                            .addGroup(luogoPanelLayout.createSequentialGroup()
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(luogoPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(latitudineSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(longitudineSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(GroupLayout.Alignment.TRAILING, luogoPanelLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(coordinateCheckBox)
                                .addGap(85, 85, 85)
                                .addGroup(luogoPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(avantiLuogoButton)
                                    .addComponent(indietroLuogoButton))
                                .addGap(38, 38, 38))))
            );
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(luogoPanel, GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE))
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
    private JLabel label1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
