package GUI;

import javax.swing.*;
import javax.swing.GroupLayout;
import Controller.Controller;
import GUI.Components.*;
import Model.Foto;
import Model.GalleriaCondivisa;
import Model.Utente;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * The type Crea galleria condivisa.
 */
public class CreaGalleriaCondivisa extends JFrame {

    /**
     * The Main frame.
     */
    protected JFrame mainFrame;
    /**
     * The Foto panel.
     */
    FotoPanel fotoPanel;
    /**
     * The Controller.
     */
    Controller controller;
    /**
     * The Photos.
     */
    ArrayList<Foto> photos;

    private JPanel rootPanel;
    private JScrollPane galleriaScrollPanel;
    private JTextField nomeGalleriaText;
    private TagTextField collabText;
    private JButton confermaButton;
    private JButton annullaButton;
    private JLabel label1;
    private JLabel label2;

    /**
     * Instantiates a new Crea galleria condivisa.
     *
     * @param controller the controller
     * @param home       the home
     */
    public CreaGalleriaCondivisa (Controller controller, Home home) {

        this.controller = controller;
        photos = controller.getUtente().getGalleriaPersonale().getPhotos();

        fotoPanel = new FotoPanel(photos, false, controller, controller.getUtente().getUsername(), home);
        initComponents();
        galleriaScrollPanel.setViewportView(fotoPanel);
        collabText.setToolTipText("Inserisci i tag separati da virgola");


        mainFrame = new JFrame("Crea galleria condivisa");
        mainFrame.setContentPane(rootPanel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(home.mainFrame);
        mainFrame.setResizable(false);

        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean tagsCheck = checkTags(collabText.getText().split(","));
                    if (!nomeGalleriaText.getText().equals("") && !collabText.getText().equals("") && tagsCheck) {
                        int codg = controller.aggiungiGalleriaCondivisaDB(controller.getUtente().getUsername(), collabText.getText(), nomeGalleriaText.getText());
                        boolean[] selectedPhotos = fotoPanel.getSelectedPhotos();
                        for (int i = 0; i < photos.size(); i++) {
                            if (selectedPhotos[i]) {
                                controller.aggiungiPresenzaFotoDB(photos.get(i).getCodFoto(), codg);
                            }
                        }
                        if (controller.getUtente().getGallerieCondivise() == null) {
                            controller.getUtente().setGallerieCondivise(new ArrayList<GalleriaCondivisa>());
                        }
                        controller.getUtente().getGallerieCondivise().add(new GalleriaCondivisa(codg, nomeGalleriaText.getText()));
                        JOptionPane.showMessageDialog(mainFrame, "Galleria condivisa creata con successo.", "Successo", JOptionPane.INFORMATION_MESSAGE);
                        mainFrame.setVisible(false);
                        mainFrame.dispose();
                        home.gallerieCondiviseBox.addItem(nomeGalleriaText.getText());
                        home.gallerieCondiviseBox.setEnabled(true);
                        home.mainFrame.setVisible(true);
                    } else {
                        if (nomeGalleriaText.getText().equals("") && collabText.getText().equals("")) {
                            JOptionPane.showMessageDialog(mainFrame, "Uno o più campi sono vuoti.", "Errore", JOptionPane.ERROR_MESSAGE);
                            nomeGalleriaText.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
                            collabText.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
                        } else if (nomeGalleriaText.getText().equals("") && !tagsCheck && !collabText.getText().equals("")) {
                            JOptionPane.showMessageDialog(mainFrame, "Il campo nome galleria è vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
                            nomeGalleriaText.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
                            collabText.setBorder(UIManager.getLookAndFeelDefaults().getBorder("TextField.border"));
                        } else if (collabText.getText().equals("") && !tagsCheck && !nomeGalleriaText.getText().equals("")) {
                            JOptionPane.showMessageDialog(mainFrame, "Il campo collaboratori è vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
                            collabText.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
                            nomeGalleriaText.setBorder(UIManager.getLookAndFeelDefaults().getBorder("TextField.border"));
                        } else if (!tagsCheck && !nomeGalleriaText.getText().equals("") && !collabText.getText().equals("")) {
                            JOptionPane.showMessageDialog(mainFrame, "Uno o più collaboratori non esistono.", "Errore", JOptionPane.ERROR_MESSAGE);
                            collabText.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
                            nomeGalleriaText.setBorder(UIManager.getLookAndFeelDefaults().getBorder("TextField.border"));
                        }
                    }
                } catch (SQLException s) {
                    s.printStackTrace();
                    JOptionPane.showMessageDialog(mainFrame, "Errore di connessione col database.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        annullaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                mainFrame.dispose();
                home.mainFrame.setVisible(true);
            }
        });


    }

    private boolean checkTags (String[] tags){
        if (tags != null) {
            boolean hasMatch;
            ArrayList<Utente> utenti;
            // rimuovo eventuali duplicati
            HashSet<String> set = new HashSet<String>();
            for (String s : tags) {
                set.add(s);
            }

            try {
                utenti = controller.leggiUtentiDB();
                for (String tag : tags) {
                    hasMatch = false;
                    for (Utente utente : utenti) {
                        if (utente.getUsername().equals(tag)) {
                            hasMatch = true;
                        }
                    }
                    if (!hasMatch) {
                        return false;
                    }
                }
                return true;
            } catch (SQLException s) {
                s.printStackTrace();
                return false;
            }
        } else {
            return true;
        }
    }



    private void initComponents () {
        rootPanel = new JPanel();
        galleriaScrollPanel = new JScrollPane();
        nomeGalleriaText = new JTextField();
        collabText = new TagTextField(controller);
        confermaButton = new JButton();
        annullaButton = new JButton();
        label1 = new JLabel();
        label2 = new JLabel();

        //======== this ========
        var contentPane = getContentPane();

        //======== rootPanel ========
        {

            //---- confermaButton ----
            confermaButton.setText("CONFERMA");

            //---- annullaButton ----
            annullaButton.setText("ANNULLA");

            //---- label1 ----
            label1.setText("Nome Galleria :");

            //---- label2 ----
            label2.setText("Cofondatori: ");

            GroupLayout rootPanelLayout = new GroupLayout(rootPanel);
            rootPanel.setLayout(rootPanelLayout);
            rootPanelLayout.setHorizontalGroup(
                rootPanelLayout.createParallelGroup()
                    .addGroup(rootPanelLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(galleriaScrollPanel, GroupLayout.PREFERRED_SIZE, 314, GroupLayout.PREFERRED_SIZE)
                        .addGroup(rootPanelLayout.createParallelGroup()
                            .addGroup(rootPanelLayout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(annullaButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(confermaButton))
                            .addGroup(rootPanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(label1)
                                    .addComponent(collabText, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                                    .addComponent(label2)
                                    .addComponent(nomeGalleriaText, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE))))
                        .addContainerGap(13, Short.MAX_VALUE))
            );
            rootPanelLayout.setVerticalGroup(
                rootPanelLayout.createParallelGroup()
                    .addGroup(rootPanelLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(galleriaScrollPanel, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE)
                            .addGroup(rootPanelLayout.createSequentialGroup()
                                .addComponent(label1)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nomeGalleriaText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label2)
                                .addGap(8, 8, 8)
                                .addComponent(collabText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(confermaButton)
                                    .addComponent(annullaButton))))
                        .addContainerGap(64, Short.MAX_VALUE))
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
    }

}
