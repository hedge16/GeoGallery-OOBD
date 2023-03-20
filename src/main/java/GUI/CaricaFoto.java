package GUI;

import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;
import javax.swing.filechooser.FileFilter;

import Controller.Controller;
import GUI.Components.TagTextField;
import Model.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.*;

/**
 * The type Carica foto.
 */
public class CaricaFoto extends JFrame{

    /**
     * The Main frame.
     */
    protected JFrame mainFrame;
    /**
     * The Result.
     */
    int result=-1;
    /**
     * The File path.
     */
    String filePath;
    /**
     * The Nuovi soggetti.
     */
    ArrayList<SoggettoFoto> nuoviSoggetti;
    /**
     * The Soggetti selezionati db.
     */
    ArrayList<SoggettoFoto> soggettiSelezionatiDB;
    private JPanel luogoPanel;
    private JPanel inserisciSoggettoPanel;
    private String[] tags;
    /**
     * The Nomi disp.
     */
    String[] nomiDisp;
    private Luogo luogo;
    private Luogo nuovoLuogo;
    private ArrayList<Luogo> luoghi;
    /**
     * The Controller.
     */
    Controller controller;

    private JLabel inserisciLabel;
    private JButton vediSoggettiButton;
    private JComboBox categoriaComboBox;
    private JButton aggiungiSoggettoButton;
    private JComboBox dbSoggettoComboBox;
    private JTextField nomeLuogoText;
    private JButton aggiungiSoggettoDbButton;
    private JButton avantiSoggettoButton;
    private JButton indietroSoggettoButton;
    private JLabel label1;
    private JRadioButton nuovoButton;
    private JRadioButton esistenteButton;
    private JCheckBox coordinateCheckBox;
    private JCheckBox nomeLuogoCheckBox;
    private JSpinner latitudineSpinner;
    private JSpinner longitudineSpinner;
    private JComboBox exLuogoComboBox;
    private JButton avantiLuogoButton;
    private JButton indietroLuogoButton;
    private JRadioButton nessunoButton;
    private JButton confermaButton;
    private JTextField nomeSoggettoText;
    private JPanel panel1;
    private JButton apriFoto;
    private JRadioButton privataSwitch;
    private JComboBox selDisp;
    private JButton goBackButton;
    private JPanel fotoPanel;
    private JLabel fotoAnteprima;
    private TagTextField tagsText;
    private JLabel dispLabel;
    private JLabel tagsLabel;
    private JButton avantiButton;

    /**
     * Instantiates a new Carica foto.
     *
     * @param controller     the controller
     * @param frameChiamante the frame chiamante
     * @param home           the home
     */
    public CaricaFoto (Controller controller, JFrame frameChiamante, Home home) {

        this.controller = controller;
        ArrayList<Dispositivo> dispositivi = controller.getUtente().getDispositivi();

        if (dispositivi != null) {
            nomiDisp = new String[dispositivi.size() + 1];
            for (int i = 0; i < dispositivi.size(); i++) {
                nomiDisp[i] = dispositivi.get(i).getNome();
            }
            nomiDisp[nomiDisp.length-1] = "<Aggiungi Dispositivo>";
        } else {
            nomiDisp = new String[1];
            nomiDisp[0] = "<Aggiungi Dispositivo>";
        }
        initMainComponents();
        initLuogoComponents();
        nomeLuogoCheckBox.setEnabled(false);
        coordinateCheckBox.setEnabled(false);
        nomeLuogoText.setEnabled(false);
        latitudineSpinner.setEnabled(false);
        longitudineSpinner.setEnabled(false);
        exLuogoComboBox.setEnabled(false);
        initSoggettoComponents();

        SpinnerNumberModel latModel = new SpinnerNumberModel(0.0, -90.0, 90.0, 0.1);
        SpinnerNumberModel lonModel = new SpinnerNumberModel(0.0, -180.0, 180.0, 0.1);
        latitudineSpinner.setModel(latModel);
        longitudineSpinner.setModel(lonModel);

        tagsText.setToolTipText("Inserisci i tag separati da virgola");

        luoghi = controller.getTuttiLuoghi();
        if (luoghi == null) {
            exLuogoComboBox.setEnabled(false);
        } else {
            for (Luogo l : luoghi) {
                exLuogoComboBox.addItem("Nome: " + l.getNomeMnemonico() + " Lat: " + l.getLatitudine() + " Lon: " + l.getLatitudine());
            }
        }
        String[] categorie = controller.getCategorie();
        for (int i = 0; i < categorie.length; i++){
            categoriaComboBox.addItem(categorie[i]);
        }

        nuoviSoggetti = new ArrayList<>();
        soggettiSelezionatiDB = new ArrayList<>();

        ArrayList<SoggettoFoto> soggettiDB = controller.getTuttiSoggetti();
        if (soggettiDB != null) {
            for (SoggettoFoto sf : soggettiDB) {
                dbSoggettoComboBox.addItem("Nome: " + sf.getNome() + ", Categoria: " + sf.getCategoria());
            }
        }

        mainFrame = new JFrame("Carica la tua foto");
        mainFrame.setContentPane(panel1);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(frameChiamante);
        mainFrame.setResizable(false);
        apriFoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.getName().toLowerCase().endsWith(".png") || f.getName().toLowerCase().endsWith(".jpg") || f.getName().toLowerCase().endsWith("jpeg") || f.isDirectory();
                    }

                    @Override
                    public String getDescription() {
                        return "File immagine (*.png, *.jpg)";
                    }



                });

                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                result = fileChooser.showOpenDialog(apriFoto);

                if (result == JFileChooser.APPROVE_OPTION) {
                    long fileSize = fileChooser.getSelectedFile().length();
                    double fileSizeInMB = (double) fileSize / (1024 * 1024);
                    if (fileSizeInMB > 1) {
                        JOptionPane.showMessageDialog(mainFrame, "Il file selezionato supera la dimensione massima di 1MB.", "Errore", JOptionPane.ERROR_MESSAGE);
                    } else {
                        filePath = fileChooser.getSelectedFile().getAbsolutePath();
                        try {
                            ImageIcon foto = new ImageIcon(new ImageIcon(filePath).getImage().getScaledInstance(fotoAnteprima.getWidth(), fotoAnteprima.getHeight(), Image.SCALE_SMOOTH));
                            fotoAnteprima.setIcon(foto);

                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }

            }
        });

        selDisp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String selezione = (String)selDisp.getSelectedItem();

                if (selezione.equals("<Aggiungi Dispositivo>")){

                    String input = JOptionPane.showInputDialog("Inserisci nome dispositivo: ");
                    if(!input.equals("")) {
                        try {
                            Dispositivo dispositivo = controller.addDispDB(controller.getUtente().getUsername(), input);
                            JOptionPane.showMessageDialog(mainFrame, "Registrazione dispositivo andata a buon fine.");
                            selDisp.insertItemAt(input, selDisp.getItemCount() - 1);
                            selDisp.setSelectedItem(input);
                            controller.getUtente().addDispositivo(dispositivo);
                        } catch (SQLException s) {
                            JOptionPane.showMessageDialog(mainFrame, "Errore nella creazione dispositivo", "DB error", JOptionPane.ERROR_MESSAGE);
                        }
                    }else {
                        JOptionPane.showMessageDialog(mainFrame, "Inserisci un nome dispositivo valido.","Errore input", JOptionPane.ERROR_MESSAGE);
                    }
                    
                }

            }
        });

        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                mainFrame.dispose();
                frameChiamante.setVisible(true);
            }
        });

        nomeLuogoCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (coordinateCheckBox.isSelected()){
                    if(!nomeLuogoCheckBox.isSelected()){
                        nomeLuogoText.setEnabled(false);
                    }else {
                        nomeLuogoText.setEnabled(true);
                    }
                } else {
                    nomeLuogoCheckBox.setSelected(true);
                }
            }
        });

        coordinateCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nomeLuogoCheckBox.isSelected()) {
                    if (!coordinateCheckBox.isSelected()) {
                        latitudineSpinner.setEnabled(false);
                        longitudineSpinner.setEnabled(false);
                    } else {
                        latitudineSpinner.setEnabled(true);
                        longitudineSpinner.setEnabled(true);
                    }
                } else {
                    coordinateCheckBox.setSelected(true);
                }
            }
        });

        vediSoggettiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<SoggettoFoto> allSoggetti = (ArrayList<SoggettoFoto>) soggettiSelezionatiDB.clone();
                allSoggetti.addAll(nuoviSoggetti);
                VediSoggetti vs = new VediSoggetti(allSoggetti, mainFrame);
                vs.mainFrame.setVisible(true);
            }
        });
        
        avantiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!tagsText.getText().equals("")) {
                    tags = tagsText.getText().split(",");
                } else {
                    tags = null;
                }
                if (filePath != null && checkDisp() && checkTags(tags)) {
                    mainFrame.setContentPane(luogoPanel);
                } else if (filePath == null) {
                    JOptionPane.showMessageDialog(mainFrame, "Inserisci un immagine", "Errore", JOptionPane.ERROR_MESSAGE);
                } else if (!checkDisp()){
                    JOptionPane.showMessageDialog(mainFrame, "Inserisci un dispositivo", "Errore", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Inserisci dei tag validi", "Errore", JOptionPane.ERROR_MESSAGE);

                }

            }
        });

        nuovoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nuovoButton.isSelected()) {
                    nomeLuogoCheckBox.setEnabled(true);
                    coordinateCheckBox.setEnabled(true);
                    nomeLuogoText.setEnabled(true);
                    latitudineSpinner.setEnabled(true);
                    longitudineSpinner.setEnabled(true);
                    nomeLuogoCheckBox.setSelected(true);
                    coordinateCheckBox.setSelected(true);
                    exLuogoComboBox.setEnabled(false);
                    if (esistenteButton.isSelected()) {
                        esistenteButton.setSelected(false);
                    } else if (nessunoButton.isSelected()) {
                        nessunoButton.setSelected(false);
                    }
                } else {
                    nomeLuogoText.setEnabled(false);
                    latitudineSpinner.setEnabled(false);
                    longitudineSpinner.setEnabled(false);
                    nomeLuogoCheckBox.setEnabled(false);
                    coordinateCheckBox.setEnabled(false);
                }
            }
        });

        esistenteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(esistenteButton.isSelected()) {
                    exLuogoComboBox.setEnabled(true);
                    if (nessunoButton.isSelected()) {
                        nessunoButton.setSelected(false);
                    }
                    if (nuovoButton.isSelected()) {
                        nuovoButton.setSelected(false);
                        nomeLuogoText.setEnabled(false);
                        latitudineSpinner.setEnabled(false);
                        longitudineSpinner.setEnabled(false);
                        nomeLuogoCheckBox.setEnabled(false);
                        coordinateCheckBox.setEnabled(false);
                    }
                } else {
                    exLuogoComboBox.setEnabled(false);
                }
            }
        });

        nessunoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nessunoButton.isSelected()) {
                    exLuogoComboBox.setEnabled(false);
                    nomeLuogoText.setEnabled(false);
                    latitudineSpinner.setEnabled(false);
                    longitudineSpinner.setEnabled(false);
                    nomeLuogoCheckBox.setEnabled(false);
                    coordinateCheckBox.setEnabled(false);
                    if (nuovoButton.isSelected()) {
                        nuovoButton.setSelected(false);
                    }
                    if (esistenteButton.isSelected()) {
                        esistenteButton.setSelected(false);
                    }

                }
            }
        });

        avantiLuogoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nuovoButton.isSelected() || esistenteButton.isSelected() || nessunoButton.isSelected()) {
                    if (nuovoButton.isSelected()){
                        if (!nomeLuogoText.getText().equals("") && nomeLuogoCheckBox.isSelected()) {
                            nomeLuogoText.setBorder(UIManager.getLookAndFeelDefaults().getBorder("TextField.border"));
                            nuovoLuogo = new Luogo(-1, nomeLuogoText.getText(), (Double) latitudineSpinner.getValue(), (Double) longitudineSpinner.getValue());
                            mainFrame.setContentPane(inserisciSoggettoPanel);
                        } else if (nomeLuogoCheckBox.isSelected() && nomeLuogoText.getText().equals("")) {
                            JOptionPane.showMessageDialog(mainFrame, "Inserisci un nome per il luogo", "Errore", JOptionPane.ERROR_MESSAGE);
                            nomeLuogoText.setBorder(BorderFactory.createLineBorder(Color.RED));
                        } else {
                            nuovoLuogo = new Luogo(-1, null, (Double) latitudineSpinner.getValue(), (Double) longitudineSpinner.getValue());
                            mainFrame.setContentPane(inserisciSoggettoPanel);
                        }
                    } else if (esistenteButton.isSelected()){
                        luogo = luoghi.get(exLuogoComboBox.getSelectedIndex());
                        mainFrame.setContentPane(inserisciSoggettoPanel);
                    } else {
                        mainFrame.setContentPane(inserisciSoggettoPanel);
                    }

                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Scegli un'opzione", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        indietroLuogoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setContentPane(panel1);
            }
        });

        indietroSoggettoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setContentPane(luogoPanel);
            }
        });

        aggiungiSoggettoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nomeSoggettoText.getText().equals("")){
                    Border border = BorderFactory.createLineBorder(Color.RED, 1);
                    nomeSoggettoText.setBorder(border);
                    JOptionPane.showMessageDialog(mainFrame, "Inserisci un nome al soggetto", "Errore", JOptionPane.ERROR_MESSAGE);
                } else {
                    nomeSoggettoText.setBorder(UIManager.getLookAndFeelDefaults().getBorder("TextField.border"));
                    boolean giaPresente = false;
                    for (SoggettoFoto sf : nuoviSoggetti){
                        if (sf.getNome().equals(nomeSoggettoText.getText()) && sf.getCategoria().equals(categoriaComboBox.getSelectedItem())){
                            giaPresente = true;
                            break;
                        }
                    }
                    if (!giaPresente) {
                        SoggettoFoto newSoggetto = new SoggettoFoto(-1, nomeSoggettoText.getText(), (String)categoriaComboBox.getSelectedItem());
                        nuoviSoggetti.add(newSoggetto);
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "Hai già inserito questo soggetto", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        aggiungiSoggettoDbButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean giaPresente = false;
                for (SoggettoFoto sf : soggettiSelezionatiDB){
                    if (soggettiDB.get(dbSoggettoComboBox.getSelectedIndex()).equals(sf)){
                        giaPresente = true;
                        break;
                    }
                }
                if (!giaPresente) {
                    soggettiSelezionatiDB.add(soggettiDB.get(dbSoggettoComboBox.getSelectedIndex()));
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Hai già inserito questo soggetto", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Foto foto;
                    Utente utente = controller.getUtente();
                    if (nuovoButton.isSelected()) {
                        controller.getTuttiLuoghi().add(nuovoLuogo);
                        foto = controller.caricaFotoDB(privataSwitch.isSelected(), true, new Date(), utente.getUsername(), dispositivi.get(selDisp.getSelectedIndex()).getId(), filePath, nuovoLuogo, nuoviSoggetti, soggettiSelezionatiDB, tags);
                        foto.setLuogo(nuovoLuogo);
                        controller.getTuttiLuoghi().add(nuovoLuogo);
                    } else if (esistenteButton.isSelected()) {
                        foto = controller.caricaFotoDB(privataSwitch.isSelected(), false, new Date(), utente.getUsername(), dispositivi.get(selDisp.getSelectedIndex()).getId(), filePath, luogo, nuoviSoggetti, soggettiSelezionatiDB, tags);
                        foto.setLuogo(luogo);
                    } else {
                        foto = controller.caricaFotoDB(privataSwitch.isSelected(), false, new Date(), utente.getUsername(), dispositivi.get(selDisp.getSelectedIndex()).getId(), filePath, null, nuoviSoggetti, soggettiSelezionatiDB, tags);
                    }

                    utente.getGalleriaPersonale().addFoto(foto);
                    ArrayList<SoggettoFoto> soggetti = soggettiSelezionatiDB;
                    soggetti.addAll(nuoviSoggetti);
                    foto.setSoggetti(soggetti);

                    if (!nuoviSoggetti.isEmpty()){
                        controller.getTuttiSoggetti().addAll(nuoviSoggetti);
                    }
                    JOptionPane.showMessageDialog(mainFrame, "Foto caricata con successo", "", JOptionPane.INFORMATION_MESSAGE);
                    home.fotoPanel.addFoto(foto);
                    mainFrame.setVisible(true);
                    mainFrame.dispose();
                    home.mainFrame.setVisible(true);

                } catch (Exception s){
                    JOptionPane.showMessageDialog(mainFrame, "Ops, qualcosa è andato storto", "Errore", JOptionPane.ERROR_MESSAGE);
                    mainFrame.setVisible(false);
                    mainFrame.dispose();
                    home.mainFrame.setVisible(true);
                    s.printStackTrace();
                }

            }
        });


    }

    private boolean checkDisp () {
        if(selDisp.getSelectedItem().equals("") || selDisp.getSelectedItem().equals("<Aggiungi Dispositivo>")){
            return false;
        } else {
            return true;
        }
    }

    private boolean checkTags (String[] tags){
        if (tags != null) {
            boolean hasMatch;
            ArrayList<Utente> utenti = null;
            //Rimuovo eventuali duplicati dal vettore dei tags inizizializzando un HashSet con i tags
            HashSet<String> tagsHashSet = new HashSet<>();
            for (String tag : tags) {
                tagsHashSet.add(tag);
            }
            try {
                utenti = controller.leggiUtentiDB();
                for (String tag : tagsHashSet){
                    hasMatch = false;
                    for (Utente utente : utenti) {
                        if (utente.getUsername().equals(tag) && !controller.getUtente().getUsername().equals(tag)) {
                            hasMatch = true;
                            break;
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




    private void initMainComponents() {
        panel1 = new JPanel();
        apriFoto = new JButton();
        privataSwitch = new JRadioButton();
        selDisp = new JComboBox(nomiDisp);
        goBackButton = new JButton();
        fotoPanel = new JPanel();
        fotoAnteprima = new JLabel();
        tagsText = new TagTextField(controller);
        dispLabel = new JLabel();
        tagsLabel = new JLabel();
        avantiButton = new JButton();

        //======== this ========
        var contentPane = getContentPane();

        //======== panel1 ========
        {

            //---- apriFoto ----
            apriFoto.setText("Apri Foto");

            //---- privataSwitch ----
            privataSwitch.setText("Privata?");

            //---- goBackButton ----
            goBackButton.setText("TORNA INDIETRO");

            //======== fotoPanel ========
            {
                fotoPanel.setBorder(new TitledBorder("Anteprima"));

                GroupLayout fotoPanelLayout = new GroupLayout(fotoPanel);
                fotoPanel.setLayout(fotoPanelLayout);
                fotoPanelLayout.setHorizontalGroup(
                    fotoPanelLayout.createParallelGroup()
                        .addGroup(fotoPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(fotoAnteprima, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                            .addContainerGap())
                );
                fotoPanelLayout.setVerticalGroup(
                    fotoPanelLayout.createParallelGroup()
                        .addGroup(fotoPanelLayout.createSequentialGroup()
                            .addComponent(fotoAnteprima, GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                            .addContainerGap())
                );
            }

            //---- dispLabel ----
            dispLabel.setText("Dispositivo :");

            //---- tagsLabel ----
            tagsLabel.setText("Tags :");

            //---- avantiButton ----
            avantiButton.setText("AVANTI");

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(26, 26, Short.MAX_VALUE)
                        .addComponent(fotoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(goBackButton, GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(avantiButton))
                            .addComponent(apriFoto)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(dispLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(selDisp, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(tagsLabel)
                                .addGap(18, 18, 18)
                                .addComponent(tagsText, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
                            .addComponent(privataSwitch, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(36, Short.MAX_VALUE))
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(selDisp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dispLabel))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(apriFoto)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(tagsText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tagsLabel))
                                .addGap(18, 18, 18)
                                .addComponent(privataSwitch))
                            .addComponent(fotoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(avantiButton)
                            .addComponent(goBackButton))
                        .addContainerGap(48, Short.MAX_VALUE))
            );
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 3, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pack();
        setLocationRelativeTo(getOwner());
    }

    private void initLuogoComponents() {
        luogoPanel = new JPanel();
        nuovoButton = new JRadioButton();
        esistenteButton = new JRadioButton();
        coordinateCheckBox = new JCheckBox();
        nomeLuogoCheckBox = new JCheckBox();
        nomeLuogoText = new JTextField();
        latitudineSpinner = new JSpinner();
        longitudineSpinner = new JSpinner();
        exLuogoComboBox = new JComboBox();
        avantiLuogoButton = new JButton();
        indietroLuogoButton = new JButton();
        nessunoButton = new JRadioButton();
        label1 = new JLabel();

        //======== this ========
        var contentPane = getContentPane();

        //======== luogoPanel ========
        {
            luogoPanel.setPreferredSize(new Dimension(600, 399));

            //---- nuovoButton ----
            nuovoButton.setText("NUOVO");

            //---- esistenteButton ----
            esistenteButton.setText("ESISTENTE");

            //---- avantiLuogoButton ----
            avantiLuogoButton.setText("AVANTI");

            //---- indietroLuogoButton ----
            indietroLuogoButton.setText("INDIETRO");

            //---- nessunoButton ----
            nessunoButton.setText("NESSUNO");

            //---- label1 ----
            label1.setText("INSERISCI UN LUOGO :");
            label1.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 24));

            GroupLayout luogoPanelLayout = new GroupLayout(luogoPanel);
            luogoPanel.setLayout(luogoPanelLayout);
            luogoPanelLayout.setHorizontalGroup(
                    luogoPanelLayout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, luogoPanelLayout.createSequentialGroup()
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(luogoPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addGroup(luogoPanelLayout.createSequentialGroup()
                                                    .addComponent(indietroLuogoButton)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(avantiLuogoButton))
                                            .addComponent(nessunoButton))
                                    .addGap(76, 76, 76))
                            .addGroup(luogoPanelLayout.createSequentialGroup()
                                    .addGap(53, 53, 53)
                                    .addGroup(luogoPanelLayout.createParallelGroup()
                                            .addComponent(label1)
                                            .addGroup(luogoPanelLayout.createSequentialGroup()
                                                    .addGroup(luogoPanelLayout.createParallelGroup()
                                                            .addGroup(luogoPanelLayout.createSequentialGroup()
                                                                    .addGap(37, 37, 37)
                                                                    .addComponent(nuovoButton))
                                                            .addGroup(luogoPanelLayout.createSequentialGroup()
                                                                    .addGroup(luogoPanelLayout.createParallelGroup()
                                                                            .addGroup(luogoPanelLayout.createSequentialGroup()
                                                                                    .addComponent(latitudineSpinner, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
                                                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                    .addComponent(longitudineSpinner, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
                                                                            .addComponent(nomeLuogoText, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE))
                                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                    .addGroup(luogoPanelLayout.createParallelGroup()
                                                                            .addComponent(coordinateCheckBox)
                                                                            .addComponent(nomeLuogoCheckBox))))
                                                    .addGap(6, 6, 6)
                                                    .addGroup(luogoPanelLayout.createParallelGroup()
                                                            .addGroup(luogoPanelLayout.createSequentialGroup()
                                                                    .addGap(43, 43, 43)
                                                                    .addComponent(esistenteButton))
                                                            .addGroup(luogoPanelLayout.createSequentialGroup()
                                                                    .addGap(21, 21, 21)
                                                                    .addComponent(exLuogoComboBox, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)))))
                                    .addContainerGap(203, Short.MAX_VALUE))
            );
            luogoPanelLayout.setVerticalGroup(
                    luogoPanelLayout.createParallelGroup()
                            .addGroup(luogoPanelLayout.createSequentialGroup()
                                    .addGap(38, 38, 38)
                                    .addComponent(label1)
                                    .addGap(66, 66, 66)
                                    .addGroup(luogoPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(nessunoButton)
                                            .addComponent(nuovoButton)
                                            .addComponent(esistenteButton))
                                    .addGroup(luogoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                            .addGroup(GroupLayout.Alignment.TRAILING, luogoPanelLayout.createSequentialGroup()
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(nomeLuogoCheckBox, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
                                            .addGroup(luogoPanelLayout.createSequentialGroup()
                                                    .addGap(24, 24, 24)
                                                    .addGroup(luogoPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                            .addComponent(nomeLuogoText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(exLuogoComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(luogoPanelLayout.createParallelGroup()
                                            .addGroup(luogoPanelLayout.createSequentialGroup()
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addGroup(luogoPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                            .addComponent(latitudineSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(longitudineSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(GroupLayout.Alignment.TRAILING, luogoPanelLayout.createSequentialGroup()
                                                    .addGap(20, 20, 20)
                                                    .addComponent(coordinateCheckBox)
                                                    .addGap(85, 85, 85)
                                                    .addGroup(luogoPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                            .addComponent(avantiLuogoButton)
                                                            .addComponent(indietroLuogoButton))
                                                    .addGap(38, 38, 38))))
            );
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(luogoPanel, GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(luogoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
    }

    private void initSoggettoComponents() {
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
            inserisciSoggettoPanel.setPreferredSize(new Dimension(600, 399));

            //---- inserisciLabel ----
            inserisciLabel.setText("INSERISCI UNO O PIU' SOGGETTI :");
            inserisciLabel.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 18));

            //---- vediSoggettiButton ----
            vediSoggettiButton.setText("VEDI SOGGETTI");

            //---- aggiungiSoggettoButton ----
            aggiungiSoggettoButton.setText("+");

            //---- aggiungiSoggettoDbButton ----
            aggiungiSoggettoDbButton.setText("+");

            //---- confermaButton ----
            confermaButton.setText("CONFERMA");

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
                                                    .addComponent(inserisciLabel, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE))
                                            .addGroup(inserisciSoggettoPanelLayout.createSequentialGroup()
                                                    .addGap(114, 114, 114)
                                                    .addGroup(inserisciSoggettoPanelLayout.createParallelGroup()
                                                            .addGroup(inserisciSoggettoPanelLayout.createSequentialGroup()
                                                                    .addComponent(vediSoggettiButton)
                                                                    .addGap(94, 94, 94)
                                                                    .addComponent(indietroSoggettoButton)
                                                                    .addGap(18, 18, 18)
                                                                    .addComponent(confermaButton))
                                                            .addGroup(inserisciSoggettoPanelLayout.createSequentialGroup()
                                                                    .addGroup(inserisciSoggettoPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                                            .addComponent(nomeSoggettoText, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                                                                            .addComponent(aggiungiSoggettoButton, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                                                            .addComponent(categoriaComboBox, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))
                                                                    .addGap(94, 94, 94)
                                                                    .addGroup(inserisciSoggettoPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                                            .addComponent(aggiungiSoggettoDbButton, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                                                            .addComponent(dbSoggettoComboBox, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE))))))
                                    .addContainerGap(42, Short.MAX_VALUE))
            );
            inserisciSoggettoPanelLayout.setVerticalGroup(
                    inserisciSoggettoPanelLayout.createParallelGroup()
                            .addGroup(inserisciSoggettoPanelLayout.createSequentialGroup()
                                    .addGap(23, 23, 23)
                                    .addComponent(inserisciLabel, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(inserisciSoggettoPanelLayout.createParallelGroup()
                                            .addComponent(dbSoggettoComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addGroup(inserisciSoggettoPanelLayout.createSequentialGroup()
                                                    .addComponent(categoriaComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(nomeSoggettoText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                    .addGroup(inserisciSoggettoPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                            .addComponent(aggiungiSoggettoButton)
                                                            .addComponent(aggiungiSoggettoDbButton))))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                                    .addGroup(inserisciSoggettoPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(confermaButton)
                                            .addComponent(indietroSoggettoButton)
                                            .addComponent(vediSoggettiButton))
                                    .addGap(26, 26, 26))
            );
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addComponent(inserisciSoggettoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 3, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addComponent(inserisciSoggettoPanel, GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                                .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());

    }

}
