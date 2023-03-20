/*
 * Created by JFormDesigner on Fri Feb 17 18:25:42 CET 2023
 */

package GUI.Reference;

import Controller.Controller;
import Model.SoggettoFoto;
import Controller.Controller;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * The type Inserisci soggetto.
 *
 * @author vincenzo
 */
public class InserisciSoggetto extends JFrame {

    /**
     * The Soggetti.
     */
    ArrayList<SoggettoFoto> soggetti;
    /**
     * The Controller.
     */
    Controller controller;

    /**
     * Instantiates a new Inserisci soggetto.
     */
    public InserisciSoggetto() {

        initComponents();

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        inserisciSoggettoPanel = new JPanel();
        inserisciLabel = new JLabel();
        vediSoggettiButton = new JButton();
        categoriaComboBox = new JComboBox();
        aggiungiSoggettoButton = new JButton();
        dbSoggettoComboBox = new JComboBox();
        nomeSoggettoText = new JTextField();
        aggiungiSoggettoDbButton = new JButton();
        confermaButton = new JButton();
        indietroSoggettoButton = new JButton();

        //======== this ========
        var contentPane = getContentPane();

        //======== inserisciSoggettoPanel ========
        {
            inserisciSoggettoPanel.setPreferredSize(new Dimension(600, 399));
            inserisciSoggettoPanel.setBackground(new Color(0x161616));

            //---- inserisciLabel ----
            inserisciLabel.setText("INSERISCI UNO O PIU' SOGGETTI :");
            inserisciLabel.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 18));

            //---- vediSoggettiButton ----
            vediSoggettiButton.setText("VEDI SOGGETTI");

            //---- aggiungiSoggettoButton ----
            aggiungiSoggettoButton.setText("+");

            //---- aggiungiSoggettoDbButton ----
            aggiungiSoggettoDbButton.setText("+");

            //---- confermaButton ----
            confermaButton.setText("CONFERMA");
            confermaButton.setBackground(new Color(0x006699));

            //---- indietroSoggettoButton ----
            indietroSoggettoButton.setText("INDIETRO");

            GroupLayout inserisciSoggettoPanelLayout = new GroupLayout(inserisciSoggettoPanel);
            inserisciSoggettoPanel.setLayout(inserisciSoggettoPanelLayout);
            inserisciSoggettoPanelLayout.setHorizontalGroup(
                inserisciSoggettoPanelLayout.createParallelGroup()
                    .addGroup(inserisciSoggettoPanelLayout.createSequentialGroup()
                        .addGroup(inserisciSoggettoPanelLayout.createParallelGroup()
                            .addGroup(inserisciSoggettoPanelLayout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(inserisciLabel, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE))
                            .addGroup(inserisciSoggettoPanelLayout.createSequentialGroup()
                                .addGap(114, 114, 114)
                                .addGroup(inserisciSoggettoPanelLayout.createParallelGroup()
                                    .addGroup(inserisciSoggettoPanelLayout.createSequentialGroup()
                                        .addComponent(vediSoggettiButton)
                                        .addGap(94, 94, 94)
                                        .addComponent(indietroSoggettoButton)
                                        .addGap(18, 18, 18)
                                        .addComponent(confermaButton))
                                    .addGroup(inserisciSoggettoPanelLayout.createSequentialGroup()
                                        .addGroup(inserisciSoggettoPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(nomeSoggettoText, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                                            .addComponent(aggiungiSoggettoButton, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(categoriaComboBox, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))
                                        .addGap(94, 94, 94)
                                        .addGroup(inserisciSoggettoPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addComponent(aggiungiSoggettoDbButton, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(dbSoggettoComboBox, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE))))))
                        .addContainerGap(42, Short.MAX_VALUE))
            );
            inserisciSoggettoPanelLayout.setVerticalGroup(
                inserisciSoggettoPanelLayout.createParallelGroup()
                    .addGroup(inserisciSoggettoPanelLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(inserisciLabel, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(inserisciSoggettoPanelLayout.createParallelGroup()
                            .addComponent(dbSoggettoComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGroup(inserisciSoggettoPanelLayout.createSequentialGroup()
                                .addComponent(categoriaComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(nomeSoggettoText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(inserisciSoggettoPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(aggiungiSoggettoButton)
                                    .addComponent(aggiungiSoggettoDbButton))))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                        .addGroup(inserisciSoggettoPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(confermaButton)
                            .addComponent(indietroSoggettoButton)
                            .addComponent(vediSoggettiButton))
                        .addGap(26, 26, 26))
            );
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addComponent(inserisciSoggettoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 3, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addComponent(inserisciSoggettoPanel, GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel inserisciSoggettoPanel;
    private JLabel inserisciLabel;
    private JButton vediSoggettiButton;
    private JComboBox categoriaComboBox;
    private JButton aggiungiSoggettoButton;
    private JComboBox dbSoggettoComboBox;
    private JTextField nomeSoggettoText;
    private JButton aggiungiSoggettoDbButton;
    private JButton confermaButton;
    private JButton indietroSoggettoButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
