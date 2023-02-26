/*
 * Created by JFormDesigner on Fri Feb 17 18:25:42 CET 2023
 */

package GUI;

import Controller.Controller;
import Model.SoggettoFoto;
import Controller.Controller;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author vincenzo
 */
public class InserisciSoggetto extends JFrame {

    ArrayList<SoggettoFoto> soggetti;
    Controller controller;
    public InserisciSoggetto() {

        initComponents();
        controller = new Controller();
        try {
            soggetti = controller.recuperaTuttiSoggettiDB();
        } catch (SQLException s) {
            s.printStackTrace();
        }

        for (SoggettoFoto sf : soggetti) {
            dbSoggettoComboBox.addItem("Nome: " + sf.getNome() + ", Categoria: " + sf.getCategoria());
        }
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
            inserisciSoggettoPanel.setPreferredSize(new Dimension(718, 399));

            //---- inserisciLabel ----
            inserisciLabel.setText("INSERISCI UNO O PIU' SOGGETTI");

            //---- vediSoggettiButton ----
            vediSoggettiButton.setText("VEDI SOGGETTI");

            //---- aggiungiSoggettoButton ----
            aggiungiSoggettoButton.setText("+");

            //---- aggiungiSoggettoDbButton ----
            aggiungiSoggettoDbButton.setText("+");

            //---- confermaButton ----
            confermaButton.setText("CONFERMA");
            confermaButton.setBackground(new Color(0x0099cc));

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
                                .addGroup(inserisciSoggettoPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addGroup(inserisciSoggettoPanelLayout.createSequentialGroup()
                                        .addGroup(inserisciSoggettoPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addComponent(aggiungiSoggettoButton, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                            .addGroup(inserisciSoggettoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addComponent(categoriaComboBox, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                                                .addComponent(nomeSoggettoText)))
                                        .addGroup(inserisciSoggettoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                            .addGroup(inserisciSoggettoPanelLayout.createSequentialGroup()
                                                .addGap(240, 240, 240)
                                                .addComponent(aggiungiSoggettoDbButton, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
                                            .addGroup(inserisciSoggettoPanelLayout.createSequentialGroup()
                                                .addGap(87, 87, 87)
                                                .addComponent(dbSoggettoComboBox))))
                                    .addComponent(inserisciLabel, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)))
                            .addGroup(inserisciSoggettoPanelLayout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(vediSoggettiButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 306, Short.MAX_VALUE)
                                .addComponent(indietroSoggettoButton)))
                        .addGap(18, 18, 18)
                        .addComponent(confermaButton)
                        .addGap(56, 56, 56))
            );
            inserisciSoggettoPanelLayout.setVerticalGroup(
                inserisciSoggettoPanelLayout.createParallelGroup()
                    .addGroup(inserisciSoggettoPanelLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(inserisciLabel, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(inserisciSoggettoPanelLayout.createParallelGroup()
                            .addGroup(inserisciSoggettoPanelLayout.createSequentialGroup()
                                .addComponent(categoriaComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(nomeSoggettoText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(aggiungiSoggettoButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
                                .addGroup(inserisciSoggettoPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(vediSoggettiButton)
                                    .addComponent(confermaButton)
                                    .addComponent(indietroSoggettoButton))
                                .addGap(14, 14, 14))
                            .addGroup(inserisciSoggettoPanelLayout.createSequentialGroup()
                                .addComponent(dbSoggettoComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54)
                                .addComponent(aggiungiSoggettoDbButton)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            );
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(inserisciSoggettoPanel, GroupLayout.DEFAULT_SIZE, 728, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(inserisciSoggettoPanel, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
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
