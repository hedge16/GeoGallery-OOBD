package GUI;

import javax.swing.*;
import javax.swing.GroupLayout;
import Controller.Controller;
import GUI.Components.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CreaGalleriaCondivisa extends JFrame {

    protected JFrame mainFrame;
    private Controller controller;

    public CreaGalleriaCondivisa (String username, Controller controller, JFrame frameChiamante) {

        this.controller = controller;

        initComponents();

        mainFrame = new JFrame("Crea galleria condivisa");
        mainFrame.setContentPane(rootPane);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(frameChiamante);

        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.aggiungiGalleriaCondivisa(username, collabText.getText(), nomeGalleriaText.getText());
                } catch (SQLException s) {
                    s.printStackTrace();
                    JOptionPane.showMessageDialog(mainFrame, "Errore di connessione col database.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
                //controller.aggiungiFotoGallC();
            }
        });

        annullaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                mainFrame.dispose();
                frameChiamante.setVisible(true);
            }
        });


    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        rootPanel = new JPanel();
        galleriaScrollPanel = new JScrollPane();
        nomeGalleriaText = new JTextField();
        collabText = new TagTextField(controller);
        confermaButton = new JButton();
        annullaButton = new JButton();

        //======== this ========
        var contentPane = getContentPane();

        //======== rootPanel ========
        {

            //---- confermaButton ----
            confermaButton.setText("CONFERMA");

            //---- annullaButton ----
            annullaButton.setText("ANNULLA");

            GroupLayout rootPanelLayout = new GroupLayout(rootPanel);
            rootPanel.setLayout(rootPanelLayout);
            rootPanelLayout.setHorizontalGroup(
                rootPanelLayout.createParallelGroup()
                    .addGroup(rootPanelLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(galleriaScrollPanel, GroupLayout.PREFERRED_SIZE, 314, GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                            .addGroup(rootPanelLayout.createSequentialGroup()
                                .addComponent(annullaButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(confermaButton))
                            .addComponent(nomeGalleriaText)
                            .addComponent(collabText, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
                        .addContainerGap(13, Short.MAX_VALUE))
            );
            rootPanelLayout.setVerticalGroup(
                rootPanelLayout.createParallelGroup()
                    .addGroup(rootPanelLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(galleriaScrollPanel, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE)
                            .addGroup(rootPanelLayout.createSequentialGroup()
                                .addComponent(nomeGalleriaText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(collabText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(confermaButton)
                                    .addComponent(annullaButton))))
                        .addContainerGap(60, Short.MAX_VALUE))
            );
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(rootPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(rootPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel rootPanel;
    private JScrollPane galleriaScrollPanel;
    private JTextField nomeGalleriaText;
    private TagTextField collabText;
    private JButton confermaButton;
    private JButton annullaButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
