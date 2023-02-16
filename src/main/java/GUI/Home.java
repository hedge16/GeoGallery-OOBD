package GUI;

import java.awt.*;
import javax.swing.border.*;
import Controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.util.ArrayList;

import GUI.Components.FotoPanel;
import Model.Foto;
import Model.GalleriaCondivisa;


public class Home extends JFrame  {
    public JFrame mainFrame;
    private ArrayList<Foto> photos;
    Home home;
    FotoPanel fotoPanel;





    public Home (Controller controller, JFrame frameChiamante, String username){

        photos = controller.recuperaGallUtente(username);
        home = this;
        fotoPanel = new FotoPanel(photos, true, controller, username, this);

        initComponents(controller);

        //ArrayList<GalleriaCondivisa> gallerieCondivise = controller.recuperaGallCondivise(username);
        searchText.setText("");
        searchText.setForeground(Color.GRAY);

        ricercaComboBox.addItem("Luogo");
        ricercaComboBox.addItem("Soggetto");

        benvenutoLabel.setText("Benvenuto "+username+" !");

        mainFrame = new JFrame("Home");

        mainFrame.setContentPane(panel);
        mainFrame.setJMenuBar(menuBar);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(frameChiamante);

        caricaFoto.addActionListener (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                CaricaFoto caricaFoto = new CaricaFoto(controller, mainFrame, username, home);
                mainFrame.setVisible(false);
                caricaFoto.mainFrame.setVisible(true);
            }
        });

        logoutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login();
                mainFrame.setVisible(false);
                mainFrame.dispose();
                login.frame1.setVisible(true);
            }
        });

        creaGallCButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                CreaGalleriaCondivisa cgc = new CreaGalleriaCondivisa(username, controller, home, photos);
                mainFrame.setVisible(false);
                cgc.mainFrame.setVisible(true);
            }
        });

        cercaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String search = searchText.getText();
                if (!search.isEmpty()){
                    if (ricercaComboBox.getSelectedItem().toString().equals("Soggetto")) {
                        // controlla se il testo immesso corrisponde al formato "categoria:nomeSoggetto"
                        if (search.matches("[a-zA-Z]+:[a-zA-Z]+")) {
                            String[] searchTerms = search.split(":");
                            String categoria = searchTerms[0];
                            String nomeSoggetto = searchTerms[1];
                            try {
                                ArrayList<Foto> foto = controller.ricercaFotoPerSoggetto(categoria, nomeSoggetto);
                                if (foto.isEmpty()){
                                    JOptionPane.showMessageDialog(mainFrame, "La ricerca non ha prodotto risultati", "Nessun risultato", JOptionPane.INFORMATION_MESSAGE);
                                }else {
                                    RisultatoRicerca rr = new RisultatoRicerca(new FotoPanel(foto, false, controller, username, home), mainFrame, foto.size());
                                    rr.mainFrame.setVisible(true);
                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            // se il testo immesso non corrisponde al formato richiesto, mostra un messaggio di errore
                            JOptionPane.showMessageDialog(home, "Il testo di ricerca deve essere nel formato 'categoria:nomeSoggetto'", "Errore", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        try {
                            ArrayList<Foto> foto = controller.ricercaFotoPerLuogo(searchText.getText());
                            if (foto.isEmpty()){
                                JOptionPane.showMessageDialog(mainFrame, "La ricerca non ha prodotto risultati", "Nessun risultato", JOptionPane.INFORMATION_MESSAGE);
                            }else {
                                RisultatoRicerca rr = new RisultatoRicerca(new FotoPanel(foto, true, controller, username, home), mainFrame, foto.size());
                                rr.mainFrame.setVisible(true);
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

        searchText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchText.getText().equals("nome luogo") || searchText.getText().equals("categoria:nome")) {
                    searchText.setText("");
                    searchText.setForeground(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchText.getText().isEmpty() && ricercaComboBox.getSelectedItem().equals("Luogo")) {
                    searchText.setForeground(Color.GRAY);
                    searchText.setText("nome luogo");
                } else if (searchText.getText().isEmpty() && ricercaComboBox.getSelectedItem().equals("Soggetto")){
                    searchText.setForeground(Color.GRAY);
                    searchText.setText("categoria:nome");
                }
            }
        });


    }

    void aggiungiFotoGalleria (Foto foto) {
        fotoPanel.aggiungiFoto(foto);
    }



    /*
    *

        scrollPanel = new JScrollPane(fotoPanel);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    *
    *
    *
    *
    * */





    private void initComponents (Controller controller) {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        menuBar = new JMenuBar();
        azioniMenu = new JMenu();
        logoutItem = new JMenuItem();
        panel = new JPanel();
        caricaFoto = new JButton();
        scrollPanel = new JScrollPane();
        benvenutoLabel = new JLabel();
        searchText = new JTextField();
        creaGallCButton = new JButton();
        label1 = new JLabel();
        ricercaComboBox = new JComboBox();
        cercaButton = new JButton();
        gallerieCondiviseBox = new JComboBox();

        //======== this ========
        var contentPane = getContentPane();

        //======== menuBar ========
        {
            menuBar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

            //======== azioniMenu ========
            {
                azioniMenu.setText("Azioni");
                azioniMenu.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

                //---- logoutItem ----
                logoutItem.setText("Logout");
                azioniMenu.add(logoutItem);
            }
            menuBar.add(azioniMenu);
        }
        setJMenuBar(menuBar);

        //======== panel ========
        {
            panel.setBackground(new Color(0x2a3c4c));

            //---- caricaFoto ----
            caricaFoto.setText("CARICA FOTO");
            caricaFoto.setFont(new Font("Futura Bk BT", Font.PLAIN, 12));
            caricaFoto.setBackground(new Color(0x006699));

            //======== scrollPanel ========
            {
                scrollPanel.setBackground(new Color(0xff9900));
                scrollPanel.setToolTipText("Fai doppio-click per aprire una foto");
            }

            //---- benvenutoLabel ----
            benvenutoLabel.setText("Benvenuto, [username] !");
            benvenutoLabel.setBackground(new Color(0xccccff));
            benvenutoLabel.setFont(new Font("Futura Bk BT", Font.PLAIN, 25));

            //---- searchText ----
            searchText.setForeground(new Color(0xcccccc));
            searchText.setCaretColor(new Color(0xcccccc));
            searchText.setBackground(new Color(0x333333));
            searchText.setToolTipText("Inserisci un testo");

            //---- creaGallCButton ----
            creaGallCButton.setText("CREA GALLERIA CONDIVISA");

            //---- label1 ----
            label1.setText("Le tue galleria condivise :");

            //---- cercaButton ----
            cercaButton.setText("CERCA");

            GroupLayout panelLayout = new GroupLayout(panel);
            panel.setLayout(panelLayout);
            panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup()
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(scrollPanel)
                            .addComponent(benvenutoLabel, GroupLayout.PREFERRED_SIZE, 323, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                        .addGroup(panelLayout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                                .addComponent(ricercaComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchText, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE))
                            .addComponent(cercaButton, GroupLayout.Alignment.TRAILING)
                            .addGroup(GroupLayout.Alignment.TRAILING, panelLayout.createParallelGroup()
                                .addComponent(gallerieCondiviseBox, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
                                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(creaGallCButton, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(caricaFoto, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(label1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addContainerGap(92, Short.MAX_VALUE))
            );
            panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup()
                    .addGroup(panelLayout.createSequentialGroup()
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelLayout.createParallelGroup()
                            .addGroup(panelLayout.createSequentialGroup()
                                .addComponent(benvenutoLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrollPanel, GroupLayout.PREFERRED_SIZE, 409, GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(searchText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ricercaComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cercaButton)
                                .addGap(197, 197, 197)
                                .addComponent(label1)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(gallerieCondiviseBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45)
                                .addComponent(creaGallCButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(caricaFoto, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(27, Short.MAX_VALUE))
            );
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(panel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JMenuBar menuBar;
    private JMenu azioniMenu;
    private JMenuItem logoutItem;
    private JPanel panel;
    private JButton caricaFoto;
    private JScrollPane scrollPanel;
    private JLabel benvenutoLabel;
    private JTextField searchText;
    private JButton creaGallCButton;
    private JLabel label1;
    private JComboBox ricercaComboBox;
    private JButton cercaButton;
    private JComboBox gallerieCondiviseBox;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
