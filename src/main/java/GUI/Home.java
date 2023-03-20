package GUI;

import java.awt.*;
import Controller.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

import GUI.Components.FotoPanel;
import Model.*;


/**
 * The type Home.
 */
public class Home extends JFrame {
    /**
     * The Main frame.
     */
    public JFrame mainFrame;
    private ArrayList<Foto> photos;
    /**
     * The Home.
     */
    Home home;
    /**
     * The Foto panel.
     */
    FotoPanel fotoPanel;
    /**
     * The Controller.
     */
    Controller controller;
    private JMenuBar menuBar;
    private JMenu utenteMenu;
    private JMenuItem logoutItem;
    private JPanel panel;
    private JButton caricaFoto;
    /**
     * The Scroll panel.
     */
    public JScrollPane scrollPanel;
    private JLabel benvenutoLabel;
    private JTextField searchText;
    private JButton creaGallCButton;
    private JLabel label1;
    private JComboBox ricercaComboBox;
    private JButton cercaButton;
    /**
     * The Gallerie condivise box.
     */
    protected JComboBox gallerieCondiviseBox;
    private JButton top3Button;
    private JButton vediFotoButton;
    private JSpinner longitudineSpinner;
    private JSpinner latitudineSpinner;
    private JCheckBox coordCheckBox;

    /**
     * Instantiates a new Home.
     *
     * @param controller     the controller
     * @param frameChiamante the frame chiamante
     */
    public Home (Controller controller, JFrame frameChiamante) {

        try{
            // recupero tutte le foto dell'utente dal database
            photos = controller.recuperaGallUtenteDB(controller.getUtente().getUsername());
            controller.getUtente().getGalleriaPersonale().setPhotos(photos);

            // recupero tutti i dispositivi dell'utente dal database
            ArrayList<Dispositivo> dispositivi = controller.getAllDispDB(controller.getUtente().getUsername());
            controller.getUtente().setDispositivi(dispositivi);

            // recupero tutti i luoghi dal database
            ArrayList<Luogo> luoghiDB = controller.recuperaTuttiLuoghiDB();
            controller.setTuttiLuoghi(luoghiDB);

            // recupero tutti i soggetti dal database
            ArrayList<SoggettoFoto> soggettiDB = controller.recuperaTuttiSoggettiDB();
            controller.setTuttiSoggetti(soggettiDB);

        } catch (SQLException s){
            // mostra un messaggio di errore
            JOptionPane.showMessageDialog(null, "Errore nel recupero delle foto dal database", "Errore", JOptionPane.ERROR_MESSAGE);
            mainFrame.dispose();
            s.printStackTrace();
        }

        initComponents();
        SpinnerNumberModel latModel = new SpinnerNumberModel(0.0, -90.0, 90.0, 0.1);
        SpinnerNumberModel lonModel = new SpinnerNumberModel(0.0, -180.0, 180.0, 0.1);
        latitudineSpinner.setModel(latModel);
        longitudineSpinner.setModel(lonModel);


        try {
            // recupero tutte le gallerie condivise dell'utente dal database
            ArrayList<GalleriaCondivisa> gallerieCondivise = controller.recuperaGallerieCondiviseDB(controller.getUtente().getUsername());
            if (gallerieCondivise.size() == 0) {
                gallerieCondiviseBox.setEnabled(false);
                vediFotoButton.setEnabled(false);
            } else {
                gallerieCondiviseBox.setEnabled(true);
                vediFotoButton.setEnabled(true);
                controller.getUtente().setGallerieCondivise(gallerieCondivise);
                for (GalleriaCondivisa gc : gallerieCondivise) {
                    gallerieCondiviseBox.addItem(gc.getNomeGalleria());
                }
            }
        } catch (SQLException s){
            gallerieCondiviseBox.setEnabled(false);
            vediFotoButton.setEnabled(false);
            s.printStackTrace();
        }


        home = this;
        this.controller = controller;

        fotoPanel = new FotoPanel(controller.getUtente().getGalleriaPersonale().getPhotos(), true, controller, controller.getUtente().getUsername(), this);

        scrollPanel.setViewportView(fotoPanel);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        URL sfondoUrl = ClassLoader.getSystemResource("wallpaper.png");
        ImageIcon sfondoIcon = new ImageIcon(sfondoUrl);
        JLabel sfondoLabel = new JLabel(sfondoIcon);
        panel.add(sfondoLabel);
        sfondoLabel.setBounds(0, 0, 800, 600);
        sfondoLabel.setVisible(true);
        sfondoLabel.setOpaque(true);
        panel.setComponentZOrder(sfondoLabel, 14);

        top3Button.setText("");
        URL top3URL = ClassLoader.getSystemResource("award.png");
        top3Button.setIcon(new ImageIcon(top3URL));


        searchText.setText("nome luogo");
        searchText.setForeground(Color.GRAY);

        ricercaComboBox.addItem("Luogo");
        ricercaComboBox.addItem("Soggetto");

        benvenutoLabel.setText("Benvenuto, "+controller.getUtente().getUsername()+" !");

        mainFrame = new JFrame("Home");

        mainFrame.setContentPane(panel);
        mainFrame.setJMenuBar(menuBar);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(frameChiamante);
        mainFrame.setResizable(false);

        caricaFoto.addActionListener (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CaricaFoto caricaFoto = new CaricaFoto(controller, mainFrame, home);
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
                CreaGalleriaCondivisa cgc = new CreaGalleriaCondivisa(controller, home);
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
                                ArrayList<Foto> foto = controller.ricercaFotoPerSoggettoDB(categoria, nomeSoggetto);
                                if (foto.isEmpty()){
                                    JOptionPane.showMessageDialog(mainFrame, "La ricerca non ha prodotto risultati", "Nessun risultato", JOptionPane.INFORMATION_MESSAGE);
                                }else {
                                    RisultatoRicerca rr = new RisultatoRicerca(new FotoPanel(foto, false, controller, controller.getUtente().getUsername(), home), mainFrame, foto.size());
                                    rr.mainFrame.setVisible(true);
                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            // se il testo immesso non corrisponde al formato richiesto, mostra un messaggio di errore
                            JOptionPane.showMessageDialog(null, "Il testo di ricerca deve essere nel formato 'categoria:nomeSoggetto'", "Errore", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        try {

                            ArrayList<Foto> foto = null;
                            if (coordCheckBox.isSelected()){
                                foto = controller.ricercaFotoPerLuogoDB(searchText.getText(), (Double)latitudineSpinner.getValue(), (Double) longitudineSpinner.getValue());
                            }else {
                                foto = controller.ricercaFotoPerLuogoDB(searchText.getText());
                            }

                            if (foto.isEmpty()){
                                JOptionPane.showMessageDialog(mainFrame, "La ricerca non ha prodotto risultati", "Nessun risultato", JOptionPane.INFORMATION_MESSAGE);
                            }else {
                                RisultatoRicerca rr = new RisultatoRicerca(new FotoPanel(foto, false, controller, controller.getUtente().getUsername(), home), mainFrame, foto.size());
                                rr.mainFrame.setVisible(true);
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

        top3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<Luogo> luoghi = controller.ricercaLuoghiTop3DB();
                    if (luoghi.isEmpty()) {
                        JOptionPane.showMessageDialog(mainFrame, "Non ci sono luoghi in TOP 3", "Nessun risultato", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        DefaultTableModel model = new DefaultTableModel();
                        //aggiungi colonna per la posizione
                        model.addColumn("Posizione");
                        model.addColumn("Luogo");
                        model.addColumn("Numero di foto");
                        int i = 1;
                        for (Luogo l : luoghi) {
                            model.addRow(new Object[]{i, l.getNomeMnemonico(), l.getNumeroFoto()});
                            i++;
                        }
                        JTable table = new JTable(model);
                        JScrollPane scrollPane = new JScrollPane(table);
                        JOptionPane.showMessageDialog(mainFrame, scrollPane, "Luoghi in TOP 3", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
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

        ricercaComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ricercaComboBox.getSelectedItem().equals("Soggetto")){
                    searchText.setForeground(Color.GRAY);
                    searchText.setText("categoria:nome");
                    coordCheckBox.setVisible(false);
                    longitudineSpinner.setVisible(false);
                    latitudineSpinner.setVisible(false);

                } else {
                    searchText.setForeground(Color.GRAY);
                    searchText.setText("nome luogo");
                    coordCheckBox.setVisible(true);
                    coordCheckBox.setSelected(false);
                    longitudineSpinner.setVisible(true);
                    latitudineSpinner.setVisible(true);

                }
            }
        });

        vediFotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int codgalleria = controller.getUtente().getGallerieCondivise().get(gallerieCondiviseBox.getSelectedIndex()).getCodGalleria();
                    controller.setCodGalleriaCondivisa(codgalleria);
                    FotoPanel fotoPanel = new FotoPanel(controller.getFotoGalleriaC(codgalleria), false, controller, controller.getUtente().getUsername(), home);
                    VediGalleriaCondivisa vgc = new VediGalleriaCondivisa(controller, home, fotoPanel, gallerieCondiviseBox.getSelectedItem().toString());
                    vgc.mainFrame.setVisible(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(mainFrame, "Errore durante il caricamento delle foto", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        coordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (coordCheckBox.isSelected()){
                    longitudineSpinner.setEnabled(true);
                    latitudineSpinner.setEnabled(true);
                } else {
                    longitudineSpinner.setEnabled(false);
                    latitudineSpinner.setEnabled(false);
                }
            }
        });


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





    private void initComponents () {
        menuBar = new JMenuBar();
        utenteMenu = new JMenu();
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
        top3Button = new JButton();
        vediFotoButton = new JButton();
        longitudineSpinner = new JSpinner();
        latitudineSpinner = new JSpinner();
        coordCheckBox = new JCheckBox();

        //======== this ========
        var contentPane = getContentPane();

        //======== menuBar ========
        {
            menuBar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

            //======== utenteMenu ========
            {
                utenteMenu.setText("Utente");
                utenteMenu.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

                //---- logoutItem ----
                logoutItem.setText("Logout");
                utenteMenu.add(logoutItem);
            }
            menuBar.add(utenteMenu);
        }
        setJMenuBar(menuBar);

        //======== panel ========
        {
            panel.setBackground(Color.white);

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

            //---- top3Button ----
            top3Button.setText("TOP 3");

            //---- vediFotoButton ----
            vediFotoButton.setText("VEDI FOTO");

            GroupLayout panelLayout = new GroupLayout(panel);
            panel.setLayout(panelLayout);
            panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup()
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(scrollPanel)
                            .addComponent(benvenutoLabel, GroupLayout.PREFERRED_SIZE, 323, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                        .addGroup(panelLayout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(creaGallCButton, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(caricaFoto, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(label1, GroupLayout.Alignment.TRAILING))
                            .addComponent(top3Button, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
                            .addComponent(vediFotoButton, GroupLayout.Alignment.TRAILING)
                            .addComponent(gallerieCondiviseBox, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGroup(GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                                .addComponent(ricercaComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(searchText, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addComponent(coordCheckBox)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(latitudineSpinner, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(longitudineSpinner, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cercaButton)))
                        .addGap(72, 72, 72))
            );
            panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup()
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(benvenutoLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(searchText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ricercaComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(8, 8, 8)
                                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(cercaButton)
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(coordCheckBox))
                                    .addComponent(latitudineSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(longitudineSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(top3Button)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(label1)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(gallerieCondiviseBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(vediFotoButton)
                                .addGap(13, 13, 13)
                                .addComponent(creaGallCButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(caricaFoto, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                            .addComponent(scrollPanel, GroupLayout.PREFERRED_SIZE, 409, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(32, Short.MAX_VALUE))
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
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
    }

}
