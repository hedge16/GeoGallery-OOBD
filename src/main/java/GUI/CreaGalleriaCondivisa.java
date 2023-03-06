package GUI;

import javax.swing.*;
import javax.swing.GroupLayout;
import Controller.Controller;
import GUI.Components.*;
import Model.Foto;
import Model.Utente;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class CreaGalleriaCondivisa extends JFrame {

    protected JFrame mainFrame;
    FotoPanel fotoPanel;
    Controller controller;
    ArrayList<Foto> photos;

    public CreaGalleriaCondivisa (Controller controller, Home home) {

        this.controller = controller;
        photos = controller.getUtente().getGalleriaPersonale().getPhotos();

        fotoPanel = new FotoPanel(photos, false, controller, controller.getUtente().getUsername(), home);
        initComponents(controller);

        mainFrame = new JFrame("Crea galleria condivisa");
        mainFrame.setContentPane(rootPane);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(home.mainFrame);

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
            int i = 0;
            ArrayList<Utente> utenti;
            // rimuovo eventuali duplicati
            for (int j = 0; j < tags.length; j++) {
                for (int k = j + 1; k < tags.length; k++) {
                    if (tags[j].equals(tags[k])) {
                        tags[k] = "";
                    }
                }
            }
            try {
                utenti = controller.leggiUtentiDB();
                while (i < tags.length) {
                    hasMatch = false;
                    for (Utente utente : utenti) {
                        if (utente.getUsername().equals(tags[i])) {
                            hasMatch = true;
                        }
                    }
                    if (!hasMatch) {
                        return false;
                    }
                    i++;
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

    /*
        galleriaScrollPanel = new JScrollPane(fotoPanel);
        galleriaScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        Galleria.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


     */

    private void initComponents (Controller controller) {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        rootPanel = new JPanel();
        galleriaScrollPanel = new JScrollPane(fotoPanel);
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
                        .addGap(32, 32, 32)
                        .addGroup(rootPanelLayout.createParallelGroup()
                            .addGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                .addGroup(rootPanelLayout.createSequentialGroup()
                                    .addComponent(annullaButton)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(confermaButton))
                                .addComponent(nomeGalleriaText))
                            .addComponent(label1)
                            .addComponent(collabText, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE)
                            .addComponent(label2))
                        .addContainerGap(13, Short.MAX_VALUE))
            );
            rootPanelLayout.setVerticalGroup(
                rootPanelLayout.createParallelGroup()
                    .addGroup(rootPanelLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(label1)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(galleriaScrollPanel, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE)
                            .addGroup(rootPanelLayout.createSequentialGroup()
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
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel rootPanel;
    private JScrollPane galleriaScrollPanel;
    private JTextField nomeGalleriaText;
    private TagTextField collabText;
    private JButton confermaButton;
    private JButton annullaButton;
    private JLabel label1;
    private JLabel label2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
