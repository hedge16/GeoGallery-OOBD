/*
 * Created by JFormDesigner on Tue Feb 07 17:03:08 CET 2023
 */

package GUI;

import Model.Foto;
import org.imgscalr.Scalr;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.GroupLayout;

public class Preview extends JFrame {

    public JFrame frame;
    public Preview (ImageIcon foto) {

        initComponents();
        Image newimg = foto.getImage().getScaledInstance(184, 172, Image.SCALE_SMOOTH);
        ImageIcon nuovaFoto = new ImageIcon(newimg);
        miniatura.setIcon(nuovaFoto);


        frame = new JFrame();
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);



    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        miniatura = new JLabel();
        dataLabel = new JLabel();
        dispLabel = new JLabel();
        dataINLabel = new JLabel();
        dispINLabel = new JLabel();
        statoLabel = new JLabel();
        statoINLabel = new JLabel();
        changePrivacyButton = new JButton();
        deleteButton = new JButton();

        //======== this ========
        var contentPane = getContentPane();

        //======== panel1 ========
        {

            //---- dataLabel ----
            dataLabel.setText("Data caricamento :");

            //---- dispLabel ----
            dispLabel.setText("Dispositivo :");

            //---- dataINLabel ----
            dataINLabel.setText("text");

            //---- dispINLabel ----
            dispINLabel.setText("text");

            //---- statoLabel ----
            statoLabel.setText("Stato :");

            //---- statoINLabel ----
            statoINLabel.setText("text");

            //---- changePrivacyButton ----
            changePrivacyButton.setText("CAMBIA PRIVACY");

            //---- deleteButton ----
            deleteButton.setText("ELIMINA");
            deleteButton.setBackground(new Color(0xcc0000));

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(changePrivacyButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(deleteButton))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(miniatura, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
                                .addGap(109, 109, 109)
                                .addGroup(panel1Layout.createParallelGroup()
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addComponent(statoLabel)
                                        .addGap(103, 103, 103))
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addGroup(panel1Layout.createParallelGroup()
                                            .addComponent(dataLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(dispLabel, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(panel1Layout.createParallelGroup()
                                    .addComponent(dataINLabel)
                                    .addComponent(dispINLabel)
                                    .addComponent(statoINLabel))))
                        .addContainerGap(53, Short.MAX_VALUE))
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup()
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(63, 63, 63)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addComponent(dataINLabel)
                                        .addGap(18, 18, 18)
                                        .addComponent(dispINLabel))
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addComponent(dataLabel)
                                        .addGap(18, 18, 18)
                                        .addComponent(dispLabel)))
                                .addGap(18, 18, 18)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(statoLabel)
                                    .addComponent(statoINLabel)))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(miniatura, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(deleteButton)
                            .addComponent(changePrivacyButton))
                        .addGap(74, 74, 74))
            );
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addComponent(panel1, GroupLayout.PREFERRED_SIZE, 290, GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel panel1;
    private JLabel miniatura;
    private JLabel dataLabel;
    private JLabel dispLabel;
    private JLabel dataINLabel;
    private JLabel dispINLabel;
    private JLabel statoLabel;
    private JLabel statoINLabel;
    private JButton changePrivacyButton;
    private JButton deleteButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
