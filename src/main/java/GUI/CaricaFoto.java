package GUI;

import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;
import javax.swing.filechooser.FileFilter;

import Controller.Controller;
import GUI.Components.TagTextField;
import Model.Dispositivo;
import Model.Luogo;
import Model.SoggettoFoto;
import Model.Utente;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class CaricaFoto extends JFrame {

    protected JFrame mainFrame;
    int result=-1;
    String filePath;
    private int codFoto;
    ArrayList<SoggettoFoto> soggettiDB;
    ArrayList<SoggettoFoto> nuoviSoggetti;
    ArrayList<SoggettoFoto> soggettiSelezionatiDB;
    private JPanel inserisciLuogoPanel;
    private JPanel inserisciSoggettoPanel;
    private String[] tags;
    private Dispositivo dispositivo;
    private ArrayList<Dispositivo> dispositivi;
    String[] nomiDisp;
    private Luogo luogo;
    private Luogo nuovoLuogo;
    private ArrayList<Luogo> luoghi;
    Controller controller;
    public CaricaFoto (Controller controller, JFrame frameChiamante, String username, Home home) {

        this.controller = controller;
        dispositivi = null;
        try {
            dispositivi = controller.getAllDisp(username);
        } catch (SQLException s) {
            s.printStackTrace();
        }
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
        initMainComponents(controller, username);
        initLuogoComponents();
        initSoggettoComponents();

        SpinnerNumberModel latModel = new SpinnerNumberModel(0.0, -90.0, 90.0, 0.1);
        SpinnerNumberModel lonModel = new SpinnerNumberModel(0.0, -180.0, 180.0, 0.1);
        latitudineSpinner.setModel(latModel);
        longitudineSpinner.setModel(lonModel);
        luoghi = new ArrayList<>();
        try {
            luoghi = controller.recuperaTuttiLuoghiDB();
        } catch (SQLException s) {
            s.printStackTrace();
        }

        if (luoghi.isEmpty()) {
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

        soggettiDB = new ArrayList<>();
        nuoviSoggetti = new ArrayList<>();
        soggettiSelezionatiDB = new ArrayList<>();

        try {
            soggettiDB = controller.recuperaTuttiSoggettiDB();
        } catch (SQLException s) {
            s.printStackTrace();
        }

        for (SoggettoFoto sf : soggettiDB) {
            dbSoggettoComboBox.addItem("Nome: " + sf.getNome() + ", Categoria: " + sf.getCategoria());
        }





        mainFrame = new JFrame("Carica la tua foto");
        mainFrame.setContentPane(panel1);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(frameChiamante);


        apriFoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.getName().toLowerCase().endsWith(".png") || f.getName().toLowerCase().endsWith(".jpg") || f.isDirectory();
                    }

                    @Override
                    public String getDescription() {
                        return "File immagine (*.png, *.jpg)";
                    }
                });

                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                result = fileChooser.showOpenDialog(apriFoto);

                if (result == JFileChooser.APPROVE_OPTION) {
                    filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    try{
                        ImageIcon foto = new ImageIcon(new ImageIcon(filePath).getImage().getScaledInstance(fotoAnteprima.getWidth(), fotoAnteprima.getHeight(), Image.SCALE_SMOOTH));
                        fotoAnteprima.setIcon(foto);

                    } catch (Exception e1){
                        e1.printStackTrace();
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
                            controller.addDisp(username, input);
                            JOptionPane.showMessageDialog(mainFrame, "Registrazione dispositivo andata a buon fine.");
                            selDisp.addItem(input);

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
                if(!nomeLuogoCheckBox.isSelected()){
                    nomeLuogoText.setEnabled(false);
                }else {
                    nomeLuogoText.setEnabled(true);
                }
            }
        });

        coordinateCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!coordinateCheckBox.isSelected()){
                    latitudineSpinner.setEnabled(false);
                    longitudineSpinner.setEnabled(false);
                }else {
                    latitudineSpinner.setEnabled(true);
                    longitudineSpinner.setEnabled(true);
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
                    nomeLuogoCheckBox.setSelected(true);
                    coordinateCheckBox.setSelected(true);
                    if (esistenteButton.isSelected()) {
                        esistenteButton.setSelected(false);
                    }
                    if (nessunoButton.isSelected()) {
                        nessunoButton.setSelected(false);
                    }
                }
            }
        });

        esistenteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(esistenteButton.isSelected()) {
                    if (nessunoButton.isSelected()) {
                        nessunoButton.setSelected(false);
                    }
                    if (nuovoButton.isSelected()) {
                        nuovoButton.setSelected(false);
                    }
                }
            }
        });

        nessunoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nessunoButton.isSelected()) {
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
                        nuovoLuogo = new Luogo(-1, nomeLuogoText.getText(), (Double) latitudineSpinner.getValue(), (Double) longitudineSpinner.getValue());
                    } else if (esistenteButton.isSelected()){
                        luogo = luoghi.get(exLuogoComboBox.getSelectedIndex());
                    }
                    mainFrame.setContentPane(inserisciSoggettoPanel);
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
                    nomeLuogoText.setBorder(UIManager.getLookAndFeelDefaults().getBorder("TextField.border"));
                    SoggettoFoto newSoggetto = new SoggettoFoto(-1, nomeSoggettoText.getText(), (String)categoriaComboBox.getSelectedItem());
                    nuoviSoggetti.add(newSoggetto);
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
                if (!soggettiSelezionatiDB.isEmpty() || !nuoviSoggetti.isEmpty()){
                    try {
                        if (nuovoButton.isSelected()) {
                            controller.caricaFoto(privataSwitch.isSelected(), true, new Date(), username, dispositivi.get(selDisp.getSelectedIndex()).getId(), filePath, nuovoLuogo, nuoviSoggetti, soggettiSelezionatiDB, tags);
                        } else {
                            controller.caricaFoto(privataSwitch.isSelected(), false, new Date(), username, dispositivi.get(selDisp.getSelectedIndex()).getId(), filePath, luogo, nuoviSoggetti, soggettiSelezionatiDB, tags);
                        }
                        JOptionPane.showMessageDialog(mainFrame, "Foto caricata con successo", "", JOptionPane.INFORMATION_MESSAGE);
                        Home home = new Home(controller, null, username);
                        mainFrame.setVisible(true);
                        mainFrame.dispose();
                        home.mainFrame.setVisible(true);

                    } catch (Exception s){
                        JOptionPane.showMessageDialog(mainFrame, "Ops, qualcosa è andato storto", "Errore", JOptionPane.ERROR_MESSAGE);
                        Home home = new Home(controller, null, username);
                        mainFrame.setVisible(true);
                        mainFrame.dispose();
                        home.mainFrame.setVisible(true);
                        s.printStackTrace();
                    }

                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Inserisci almeno un soggetto", "Errore", JOptionPane.ERROR_MESSAGE);
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
            int i = 0;
            ArrayList<Utente> utenti = null;
            try {
                utenti = controller.leggiUtentiDB();
            } catch (SQLException s) {
                s.printStackTrace();
            }
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
        } else {
            return true;
        }
    }



    /*  QUALORA SI DOVESSE MODIFICARE IN AUTOMATICO INITCOMPONENTS
    *   
    selDisp = new JComboBox(nomiDisp);

    * */

    private void initMainComponents(Controller controller, String username) {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
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
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    private void initLuogoComponents() {
        luogoPanel = new JPanel();
        label1 = new JLabel();
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

        //======== this ========
        var contentPane = getContentPane();

        //======== luogoPanel ========
        {
            luogoPanel.setPreferredSize(new Dimension(718, 399));

            //---- label1 ----
            label1.setText("INSERSCI UN NUOVO LUOGO O UTILIZZANE UNO ESISTENTE ?");

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

            GroupLayout luogoPanelLayout = new GroupLayout(luogoPanel);
            luogoPanel.setLayout(luogoPanelLayout);
            luogoPanelLayout.setHorizontalGroup(
                    luogoPanelLayout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, luogoPanelLayout.createSequentialGroup()
                                    .addGap(64, 64, 64)
                                    .addGroup(luogoPanelLayout.createParallelGroup()
                                            .addGroup(luogoPanelLayout.createSequentialGroup()
                                                    .addComponent(latitudineSpinner, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(longitudineSpinner, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(coordinateCheckBox))
                                            .addGroup(luogoPanelLayout.createSequentialGroup()
                                                    .addGap(6, 6, 6)
                                                    .addComponent(nomeLuogoText, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(nomeLuogoCheckBox)
                                                    .addGap(105, 105, 105)
                                                    .addComponent(exLuogoComboBox, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)))
                                    .addContainerGap(240, Short.MAX_VALUE))
                            .addGroup(GroupLayout.Alignment.TRAILING, luogoPanelLayout.createSequentialGroup()
                                    .addGap(127, 127, 127)
                                    .addComponent(nuovoButton)
                                    .addGap(140, 140, 140)
                                    .addComponent(esistenteButton)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 145, Short.MAX_VALUE)
                                    .addComponent(nessunoButton)
                                    .addGap(58, 58, 58))
                            .addGroup(GroupLayout.Alignment.TRAILING, luogoPanelLayout.createSequentialGroup()
                                    .addContainerGap(178, Short.MAX_VALUE)
                                    .addGroup(luogoPanelLayout.createParallelGroup()
                                            .addGroup(GroupLayout.Alignment.TRAILING, luogoPanelLayout.createSequentialGroup()
                                                    .addComponent(indietroLuogoButton)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(avantiLuogoButton)
                                                    .addGap(53, 53, 53))
                                            .addGroup(GroupLayout.Alignment.TRAILING, luogoPanelLayout.createSequentialGroup()
                                                    .addComponent(label1, GroupLayout.PREFERRED_SIZE, 406, GroupLayout.PREFERRED_SIZE)
                                                    .addGap(133, 133, 133))))
            );
            luogoPanelLayout.setVerticalGroup(
                    luogoPanelLayout.createParallelGroup()
                            .addGroup(luogoPanelLayout.createSequentialGroup()
                                    .addGap(21, 21, 21)
                                    .addComponent(label1, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
                                    .addGap(27, 27, 27)
                                    .addGroup(luogoPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(nuovoButton)
                                            .addComponent(nessunoButton)
                                            .addComponent(esistenteButton))
                                    .addGroup(luogoPanelLayout.createParallelGroup()
                                            .addGroup(GroupLayout.Alignment.TRAILING, luogoPanelLayout.createSequentialGroup()
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 187, Short.MAX_VALUE)
                                                    .addGroup(luogoPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                            .addComponent(indietroLuogoButton)
                                                            .addComponent(avantiLuogoButton))
                                                    .addGap(38, 38, 38))
                                            .addGroup(luogoPanelLayout.createSequentialGroup()
                                                    .addGroup(luogoPanelLayout.createParallelGroup()
                                                            .addGroup(luogoPanelLayout.createSequentialGroup()
                                                                    .addGap(24, 24, 24)
                                                                    .addComponent(nomeLuogoText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                    .addGroup(luogoPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                            .addComponent(latitudineSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                            .addComponent(longitudineSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                                            .addGroup(luogoPanelLayout.createSequentialGroup()
                                                                    .addGap(22, 22, 22)
                                                                    .addGroup(luogoPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                                            .addComponent(nomeLuogoCheckBox)
                                                                            .addComponent(exLuogoComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                    .addComponent(coordinateCheckBox)))
                                                    .addGap(65, 159, Short.MAX_VALUE))))
            );
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(luogoPanel, GroupLayout.DEFAULT_SIZE, 717, Short.MAX_VALUE))
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
            inserisciSoggettoPanel.setPreferredSize(new Dimension(718, 399));

            //---- inserisciLabel ----
            inserisciLabel.setText("INSERISCI UNO O PIU' SOGGETTI");

            //---- vediSoggettiButton ----
            vediSoggettiButton.setText("VEDI SOGGETTI");

            //---- aggiungiSoggettoButton ----
            aggiungiSoggettoButton.setText("+");

            //---- aggiungiSoggettoDbButton ----
            aggiungiSoggettoDbButton.setText("+");

            //---- confermaButton ----
            confermaButton.setText("CONFERMA");
            confermaButton.setBackground(new Color(0x0099cc));

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
                                                    .addGroup(inserisciSoggettoPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                            .addGroup(inserisciSoggettoPanelLayout.createSequentialGroup()
                                                                    .addGroup(inserisciSoggettoPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                                            .addComponent(aggiungiSoggettoButton, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                                                            .addGroup(inserisciSoggettoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                                    .addComponent(categoriaComboBox, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                                                                                    .addComponent(nomeSoggettoText)))
                                                                    .addGroup(inserisciSoggettoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                            .addGroup(inserisciSoggettoPanelLayout.createSequentialGroup()
                                                                                    .addGap(240, 240, 240)
                                                                                    .addComponent(aggiungiSoggettoDbButton, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
                                                                            .addGroup(inserisciSoggettoPanelLayout.createSequentialGroup()
                                                                                    .addGap(87, 87, 87)
                                                                                    .addComponent(dbSoggettoComboBox))))
                                                            .addComponent(inserisciLabel, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(inserisciSoggettoPanelLayout.createSequentialGroup()
                                                    .addGap(16, 16, 16)
                                                    .addComponent(vediSoggettiButton)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 306, Short.MAX_VALUE)
                                                    .addComponent(indietroSoggettoButton)))
                                    .addGap(18, 18, 18)
                                    .addComponent(confermaButton)
                                    .addGap(56, 56, 56))
            );
            inserisciSoggettoPanelLayout.setVerticalGroup(
                    inserisciSoggettoPanelLayout.createParallelGroup()
                            .addGroup(inserisciSoggettoPanelLayout.createSequentialGroup()
                                    .addGap(23, 23, 23)
                                    .addComponent(inserisciLabel, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(inserisciSoggettoPanelLayout.createParallelGroup()
                                            .addGroup(inserisciSoggettoPanelLayout.createSequentialGroup()
                                                    .addComponent(categoriaComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(nomeSoggettoText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(aggiungiSoggettoButton)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
                                                    .addGroup(inserisciSoggettoPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                            .addComponent(vediSoggettiButton)
                                                            .addComponent(confermaButton)
                                                            .addComponent(indietroSoggettoButton))
                                                    .addGap(14, 14, 14))
                                            .addGroup(inserisciSoggettoPanelLayout.createSequentialGroup()
                                                    .addComponent(dbSoggettoComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                    .addGap(54, 54, 54)
                                                    .addComponent(aggiungiSoggettoDbButton)
                                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            );
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addComponent(inserisciSoggettoPanel, GroupLayout.DEFAULT_SIZE, 728, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addComponent(inserisciSoggettoPanel, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
        );
        pack();
        setLocationRelativeTo(getOwner());
    }

    private JLabel inserisciLabel;
    private JButton vediSoggettiButton;
    private JComboBox categoriaComboBox;
    private JButton aggiungiSoggettoButton;
    private JComboBox dbSoggettoComboBox;
    private JTextField nomeLuogoText;
    private JButton aggiungiSoggettoDbButton;
    private JButton avantiSoggettoButton;
    private JButton indietroSoggettoButton;
    private JPanel luogoPanel;
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

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
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
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
