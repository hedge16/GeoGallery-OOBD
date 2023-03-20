/*
 * Created by JFormDesigner on Tue Feb 07 17:03:08 CET 2023
 */

package GUI;

import GUI.Components.FotoPanel;
import Model.Dispositivo;
import Model.Foto;
import Model.Luogo;
import Model.SoggettoFoto;
import org.imgscalr.Scalr;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.GroupLayout;
import Controller.Controller;

/**
 * The type Preview.
 */
public class Preview extends JFrame {

    /**
     * The Frame.
     */
    public JFrame frame;
    /**
     * The Luogo.
     */
    Luogo luogo;

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
    private JButton vediSoggettiButton;
    private JLabel luogoLabel2;

    /**
     * Instantiates a new Preview.
     *
     * @param controller the controller
     * @param f          the f
     * @param home       the home
     * @param isHome     the is home
     */
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

        dispINLabel.setText(f.getDispositivo().getNome());
        // Definisci il formato della data
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        // Converti la data in formato stringa
        String formattedDate = formatter.format(f.getDataScatto());

        dataINLabel.setText(formattedDate);
        autoreINLabel.setText(f.getAutore());

        luogo = f.getLuogo();
        if (luogo != null ){
            luogoINLabel.setText(luogo.getNomeMnemonico());
            luogoINLabel2.setText("Lat : "+ luogo.getLatitudine() + " Long : " + luogo.getLongitudine());
        } else {
            luogoINLabel.setText("Nessun luogo");
            luogoINLabel2.setText("");
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
                    if (JOptionPane.showConfirmDialog(frame, "Sei sicuro di voler eliminare questa foto?") == JOptionPane.OK_OPTION) {
                        controller.eliminaFotoDB(f);
                        home.mainFrame.setVisible(false);
                        home.fotoPanel.removeFoto(f);
                        JOptionPane.showMessageDialog(frame, "Foto eliminata con successo.", "", JOptionPane.WARNING_MESSAGE);
                        frame.setVisible(false);
                        frame.dispose();
                        home.mainFrame.setVisible(true);
                    }
                } catch (SQLException s) {
                    s.printStackTrace();
                    JOptionPane.showMessageDialog(panel1, "Errore nella connessione col DB", "ERRORE", JOptionPane.ERROR_MESSAGE);
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
                    f.setPrivata(!f.isPrivata());
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
        vediSoggettiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<SoggettoFoto> soggetti = controller.getSoggettiFromFoto(f.getCodFoto());
                    if (!soggetti.isEmpty()) {
                        VediSoggetti vediSoggetti = new VediSoggetti(soggetti, frame);
                        vediSoggetti.mainFrame.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(panel1, "Nessun soggetto trovato", "ERRORE", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception s) {
                    s.printStackTrace();
                    JOptionPane.showMessageDialog(panel1, "Errore nella connessione col DB", "ERRORE", JOptionPane.ERROR_MESSAGE);
                    frame.setVisible(false);
                    frame.dispose();
                }
            }
        });

    }

    private void initComponents() {
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
        vediSoggettiButton = new JButton();
        luogoLabel2 = new JLabel();

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

            //---- vediSoggettiButton ----
            vediSoggettiButton.setText("VEDI SOGGETTI");

            //---- luogoLabel2 ----
            luogoLabel2.setText("Coordinate :");

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
                                    .addComponent(dispLabel, GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addGroup(panel1Layout.createParallelGroup()
                                            .addComponent(privacyLabel)
                                            .addComponent(luogoLabel)
                                            .addComponent(autoreLabel))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE))
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addComponent(luogoLabel2)
                                        .addGap(0, 99, Short.MAX_VALUE)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel1Layout.createParallelGroup()
                                    .addComponent(autoreINLabel)
                                    .addComponent(dataINLabel)
                                    .addComponent(dispINLabel)
                                    .addComponent(privacyINLabel)
                                    .addComponent(luogoINLabel)
                                    .addComponent(luogoINLabel2))
                                .addGap(23, 23, 23))
                            .addComponent(vediSoggettiButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(changePrivacyButton, GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                            .addComponent(deleteButton, GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(dataLabel, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 96, Short.MAX_VALUE)))
                        .addGap(59, 59, 59))
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGroup(panel1Layout.createParallelGroup()
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addComponent(dataLabel)
                                        .addGap(18, 18, 18)
                                        .addComponent(dispLabel)
                                        .addGap(18, 18, 18)
                                        .addComponent(privacyLabel)
                                        .addGap(18, 18, 18)
                                        .addComponent(autoreLabel)
                                        .addGap(18, 18, 18)
                                        .addComponent(luogoLabel)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(luogoLabel2)
                                            .addComponent(luogoINLabel2)))
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addComponent(dataINLabel)
                                        .addGap(18, 18, 18)
                                        .addComponent(dispINLabel)
                                        .addGap(18, 18, 18)
                                        .addComponent(privacyINLabel)
                                        .addGap(18, 18, 18)
                                        .addComponent(autoreINLabel)
                                        .addGap(18, 18, 18)
                                        .addComponent(luogoINLabel)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(vediSoggettiButton, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(changePrivacyButton, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(deleteButton, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
                            .addComponent(miniatura, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE))
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
    }

}
