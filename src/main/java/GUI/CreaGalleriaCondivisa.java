package GUI;

import javax.swing.*;
import javax.swing.GroupLayout;
import Controller.Controller;
import GUI.Components.*;
import Model.Foto;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class CreaGalleriaCondivisa extends JFrame {

    protected JFrame mainFrame;
    private ArrayList<Foto> photos;
    FotoPanel fotoPanel;

    public CreaGalleriaCondivisa (String username, Controller controller, Home home, ArrayList<Foto> photos) {

        this.photos = photos;
        fotoPanel = new FotoPanel(photos, false, controller, username, home);
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
                    int codg = controller.aggiungiGalleriaCondivisa(username, collabText.getText(), nomeGalleriaText.getText());
                    boolean[] selectedPhotos = fotoPanel.getSelectedPhotos();
                    for (int i = 0; i < photos.size(); i++){
                        if (selectedPhotos[i]) {
                            controller.aggiungiPresenzaFoto(photos.get(i).getCodFoto(), codg);
                        }
                    }
                    mainFrame.setVisible(false);
                    mainFrame.dispose();
                    home.mainFrame.setVisible(true);
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
                home.mainFrame.setVisible(true);
            }
        });


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
