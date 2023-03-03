/*
 * Created by JFormDesigner on Tue Feb 07 17:03:08 CET 2023
 */

package GUI;

import GUI.Components.FotoPanel;
import Model.Dispositivo;
import Model.Foto;
import Model.Luogo;
import org.imgscalr.Scalr;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.GroupLayout;
import Controller.Controller;

public class Preview extends JFrame {

    public JFrame frame;
    Luogo luogo;
    public Preview (Controller controller, Foto f, Home home, boolean isHome) {

        initComponents();
        Image newimg = f.getFoto().getImage().getScaledInstance(275, 275, Image.SCALE_SMOOTH);
        ImageIcon nuovaFoto = new ImageIcon(newimg);
        miniatura.setIcon(nuovaFoto);

        if (!f.isPrivata()){
            privacyINLabel.setText("Pubblica");
        } else {
            privacyINLabel.setText("Privata");
        }

        try {
            Dispositivo disp = controller.getDispositivo(f.getCodDispositivo());
            dispINLabel.setText(disp.getNome());
        } catch (SQLException s) {
            s.printStackTrace();
        }
        // Definisci il formato della data
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        // Converti la data in formato stringa
        String formattedDate = formatter.format(f.getDataScatto());

        dataINLabel.setText(formattedDate);
        autoreINLabel.setText(f.getAutore());
        try{
            luogo = controller.getLuogoFromFoto(f.getCodFoto());
            if (luogo != null ){
                luogoINLabel.setText(luogo.getNomeMnemonico());
                luogoINLabel2.setText("Lat : "+ luogo.getLatitudine() + " Long : " + luogo.getLongitudine());
            } else {
                luogoINLabel.setText("Nessun luogo");
                luogoINLabel2.setText("");
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        if (!isHome){
            deleteButton.setVisible(false);
            changePrivacyButton.setVisible(false);
        }

        frame = new JFrame();
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (JOptionPane.showConfirmDialog(frame, "Sei sicuro?") == JOptionPane.OK_OPTION) {
                        controller.eliminaFotoDB(f);
                        home.mainFrame.setVisible(false);
                        home.mainFrame.dispose();
                        Home newHome = new Home(controller, home, f.getAutore());
                        JOptionPane.showMessageDialog(frame, "Foto eliminata con successo.", "", JOptionPane.WARNING_MESSAGE);
                        frame.setVisible(false);
                        frame.dispose();
                        newHome.mainFrame.setVisible(true);
                    }
                } catch (SQLException s) {
                    s.printStackTrace();
                    frame.setVisible(false);
                    frame.dispose();
                }
            }
        });

        changePrivacyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    controller.setPrivacyDB(f.getCodFoto(), !f.isPrivata());
                    Home home = new Home(controller, null, f.getAutore());
                    JOptionPane.showMessageDialog(panel1, "Privacy cambiata correttamente", "OK", JOptionPane.INFORMATION_MESSAGE);
                    frame.setVisible(false);
                    frame.dispose();
                    home.mainFrame.setVisible(true);

                } catch (SQLException s) {
                    s.printStackTrace();
                    JOptionPane.showMessageDialog(panel1, "Errore nella connessione col DB", "ERRORE", JOptionPane.ERROR_MESSAGE);
                    frame.setVisible(false);
                    frame.dispose();
                }

            }
        });





    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        miniatura = new JLabel();
        dataLabel = new JLabel();
        dispLabel = new JLabel();
        dataINLabel = new JLabel();
        dispINLabel = new JLabel();
        privacyLabel = new JLabel();
        privacyINLabel = new JLabel();
        changePrivacyButton = new JButton();
        deleteButton = new JButton();
        autoreLabel = new JLabel();
        autoreINLabel = new JLabel();
        luogoLabel = new JLabel();
        luogoINLabel = new JLabel();
        luogoINLabel2 = new JLabel();

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
            dataINLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            dataINLabel.setHorizontalTextPosition(SwingConstants.RIGHT);

            //---- dispINLabel ----
            dispINLabel.setText("text");
            dispINLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            dispINLabel.setHorizontalTextPosition(SwingConstants.RIGHT);

            //---- privacyLabel ----
            privacyLabel.setText("Privacy :");

            //---- privacyINLabel ----
            privacyINLabel.setText("text");
            privacyINLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            privacyINLabel.setHorizontalTextPosition(SwingConstants.RIGHT);

            //---- changePrivacyButton ----
            changePrivacyButton.setText("CAMBIA PRIVACY");

            //---- deleteButton ----
            deleteButton.setText("ELIMINA");
            deleteButton.setBackground(new Color(0xcc0000));

            //---- autoreLabel ----
            autoreLabel.setText("Autore :");

            //---- autoreINLabel ----
            autoreINLabel.setText("text");
            autoreINLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            autoreINLabel.setHorizontalTextPosition(SwingConstants.RIGHT);

            //---- luogoLabel ----
            luogoLabel.setText("Luogo :");

            //---- luogoINLabel ----
            luogoINLabel.setText("text");
            luogoINLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
            luogoINLabel.setHorizontalAlignment(SwingConstants.RIGHT);

            //---- luogoINLabel2 ----
            luogoINLabel2.setText("text");
            luogoINLabel2.setHorizontalTextPosition(SwingConstants.RIGHT);
            luogoINLabel2.setHorizontalAlignment(SwingConstants.RIGHT);

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(miniatura, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addGroup(panel1Layout.createParallelGroup()
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGroup(panel1Layout.createParallelGroup()
                                    .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                            .addComponent(deleteButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(changePrivacyButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                                        .addGroup(panel1Layout.createParallelGroup()
                                            .addGroup(panel1Layout.createSequentialGroup()
                                                .addComponent(privacyLabel)
                                                .addGap(103, 103, 103))
                                            .addGroup(panel1Layout.createSequentialGroup()
                                                .addGroup(panel1Layout.createParallelGroup()
                                                    .addComponent(dataLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addGroup(panel1Layout.createSequentialGroup()
                                                        .addGroup(panel1Layout.createParallelGroup()
                                                            .addComponent(dispLabel, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(autoreLabel))
                                                        .addGap(0, 0, Short.MAX_VALUE)))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)))
                                        .addGroup(panel1Layout.createParallelGroup()
                                            .addComponent(autoreINLabel)
                                            .addComponent(dataINLabel)
                                            .addComponent(dispINLabel)
                                            .addComponent(privacyINLabel)
                                            .addComponent(luogoINLabel)
                                            .addComponent(luogoINLabel2))))
                                .addGap(59, 59, 59))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(luogoLabel)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(miniatura, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel1Layout.createSequentialGroup()
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
                                    .addComponent(privacyLabel)
                                    .addComponent(privacyINLabel))
                                .addGap(18, 18, 18)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(autoreLabel)
                                    .addComponent(autoreINLabel))
                                .addGap(18, 18, 18)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(luogoLabel)
                                    .addComponent(luogoINLabel))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(luogoINLabel2)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(changePrivacyButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(deleteButton)))
                        .addContainerGap(41, Short.MAX_VALUE))
            );
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
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
    private JLabel privacyLabel;
    private JLabel privacyINLabel;
    private JButton changePrivacyButton;
    private JButton deleteButton;
    private JLabel autoreLabel;
    private JLabel autoreINLabel;
    private JLabel luogoLabel;
    private JLabel luogoINLabel;
    private JLabel luogoINLabel2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
