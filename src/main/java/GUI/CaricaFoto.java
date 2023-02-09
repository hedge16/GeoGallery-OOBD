package GUI;

import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;
import javax.swing.filechooser.FileFilter;

import Controller.Controller;
import GUI.Components.TagTextField;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Date;

public class CaricaFoto extends JFrame {

    protected JFrame mainFrame;
    int result=-1;
    String filePath;
    private int codFoto;

    public CaricaFoto (Controller controller, JFrame frameChiamante, String username, Home home) {


        initComponents(controller, username);
        String[] categorie = controller.getCategorie();
        for (int i = 0; i < categorie.length; i++){
            categoriaSoggList.addItem(categorie[i]);
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
                        return f.getName().toLowerCase().endsWith(".png") || f.getName().toLowerCase().endsWith(".jpg");
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

        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (result == JFileChooser.APPROVE_OPTION && checkLuogo() && checkDisp() && checkSogg()) {

                    // Carica l'immagine nel DB
                    try {
                        int codLuogo = controller.aggiungiLuogoDB(Double.parseDouble(latitudineText.getText()), Double.parseDouble(longitudineText.getText()), nomeLuogoText.getText());
                        codFoto = controller.aggiungiFoto(privataSwitch.isSelected(), false, new Date(), controller.getCodG(username), username, controller.getCodDisp((String)selDisp.getSelectedItem(), username), filePath, codLuogo);
                        int i = 0;
                        if (!tags.getText().equals("")) {
                            String[] tagArr = tags.getText().split(",");
                            int numTag = tagArr.length;
                            while (i < numTag) {
                                controller.aggiungiTagFoto(tagArr[i], codFoto);
                                i++;
                            }
                        }
                        controller.aggiungiSoggettoFoto(categorie[categoriaSoggList.getSelectedIndex()], nomeSoggTextField.getText());
                        JOptionPane.showMessageDialog(mainFrame, "Foto caricata con successo.");
                        mainFrame.setVisible(false);
                        mainFrame.dispose();
                        home.aggiornaGalleria(controller.getLastFotoDB(username));
                        home.mainFrame.setVisible(true);
                    } catch (SQLException s) {
                        JOptionPane.showMessageDialog(mainFrame, "Errore nel caricamento del file.", "Errore col DB", JOptionPane.ERROR_MESSAGE);
                    } catch (FileNotFoundException f) {
                        JOptionPane.showMessageDialog(mainFrame, "File non trovato.", "File not found error", JOptionPane.ERROR_MESSAGE);

                    }


                } else if (result == JFileChooser.ERROR) {
                    JOptionPane.showMessageDialog(mainFrame, "Non è stato possibile caricare il file.", "Errore file", JOptionPane.ERROR_MESSAGE);


                }else{
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(mainFrame, "Non è stato selezionato alcun file o non sono stati compilati tutti i campi obbligatori.", "Errore", JOptionPane.ERROR_MESSAGE);
                    Border border = BorderFactory.createLineBorder(Color.RED, 1);

                    if (luogoCheckBox.isSelected()) {
                        if (latitudineText.getText().isEmpty() && coordinateCheckBox.isSelected()) {
                            latitudineText.setBorder(border);
                        } else {
                            latitudineText.setBorder(UIManager.getLookAndFeelDefaults().getBorder("TextField.border"));
                        }
                        if (longitudineText.getText().isEmpty() && coordinateCheckBox.isSelected()) {
                            longitudineText.setBorder(border);
                        } else {
                            longitudineText.setBorder(UIManager.getLookAndFeelDefaults().getBorder("TextField.border"));
                        }
                        if (nomeLuogoText.getText().isEmpty() && nomeLuogoCheckBox.isSelected()) {
                            nomeLuogoText.setBorder(border);
                        } else {
                            nomeLuogoText.setBorder(UIManager.getLookAndFeelDefaults().getBorder("TextField.border"));
                        }
                    } else {
                        nomeLuogoText.setBorder(UIManager.getLookAndFeelDefaults().getBorder("TextField.border"));
                        longitudineText.setBorder(UIManager.getLookAndFeelDefaults().getBorder("TextField.border"));
                        latitudineText.setBorder(UIManager.getLookAndFeelDefaults().getBorder("TextField.border"));
                    }
                    if (nomeSoggTextField.getText().isEmpty()){
                        nomeSoggTextField.setBorder(border);
                    } else {
                        nomeSoggTextField.setBorder(UIManager.getLookAndFeelDefaults().getBorder("TextField.border"));
                    }
                }

            }
        });

        luogoCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (luogoCheckBox.isSelected()){
                    nomeLuogoText.setEnabled(true);
                    latitudineText.setEnabled(true);
                    longitudineText.setEnabled(true);
                    nomeLuogoCheckBox.setEnabled(true);
                    coordinateCheckBox.setEnabled(true);
                    nomeLuogoCheckBox.setSelected(true);
                    coordinateCheckBox.setSelected(true);
                }else {
                    nomeLuogoText.setEnabled(false);
                    latitudineText.setEnabled(false);
                    longitudineText.setEnabled(false);
                    nomeLuogoCheckBox.setEnabled(false);
                    coordinateCheckBox.setEnabled(false);
                    nomeLuogoCheckBox.setSelected(false);
                    coordinateCheckBox.setSelected(false);
                }
            }
        });

        nomeLuogoCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nomeLuogoCheckBox.isSelected()){
                    nomeLuogoText.setEnabled(true);
                }else{
                    nomeLuogoText.setEnabled(false);
                }
            }
        });

        coordinateCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (coordinateCheckBox.isSelected()){
                    latitudineText.setEnabled(true);
                    longitudineText.setEnabled(true);
                }else{
                    latitudineText.setEnabled(false);
                    longitudineText.setEnabled(false);                }

            }
        });


    }

    private boolean checkLuogo (){
        if (luogoCheckBox.isSelected()) {
            if ((latitudineText.getText().isEmpty() || longitudineText.getText().isEmpty()) && nomeLuogoCheckBox.isSelected()) {
                return false;
            } else if (nomeLuogoText.getText().isEmpty() && nomeLuogoCheckBox.isSelected()){
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }

    }

    private boolean checkSogg () {
        if(nomeSoggTextField.getText().isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    private boolean checkDisp () {
        String selezione = (String)selDisp.getSelectedItem();
        if(selezione.equals("<Aggiungi dispositivo>")){
            return false;
        }else{
            return true;
        }
    }


    /*  QUALORA SI DOVESSE MODIFICARE IN AUTOMATICO INITCOMPONENTS
    *   String[] dispositivi = new String[0];
        try {
            dispositivi = controller.getDisp(username).toArray(new String[0]);
        } catch (SQLException s) {
            s.printStackTrace();
        }

        selDisp = new JComboBox(dispositivi);

    * */

    private void initComponents(Controller controller, String username) {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        apriFoto = new JButton();
        privataSwitch = new JRadioButton();
        String[] dispositivi = new String[0];
        try {
            dispositivi = controller.getDisp(username).toArray(new String[0]);
        } catch (SQLException s) {
            s.printStackTrace();
        }

        selDisp = new JComboBox(dispositivi);
        goBackButton = new JButton();
        confermaButton = new JButton();
        fotoPanel = new JPanel();
        fotoAnteprima = new JLabel();
        tags = new TagTextField(controller);
        dispLabel = new JLabel();
        tagsLabel = new JLabel();
        categoriaSoggList = new JComboBox();
        nomeSoggTextField = new JTextField();
        label3 = new JLabel();
        nomeSoggLabel = new JLabel();
        longitudineText = new JTextField();
        latitudineText = new JTextField();
        nomeLuogoText = new JTextField();
        nomeLuogoLabel = new JLabel();
        coordLabel = new JLabel();
        hSpacer1 = new JPanel(null);
        luogoCheckBox = new JCheckBox();
        nomeLuogoCheckBox = new JCheckBox();
        coordinateCheckBox = new JCheckBox();

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

            //---- confermaButton ----
            confermaButton.setText("CONFERMA");

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

            //---- label3 ----
            label3.setText("Categoria :");

            //---- nomeSoggLabel ----
            nomeSoggLabel.setText("Nome soggetto :");

            //---- longitudineText ----
            longitudineText.setEnabled(false);

            //---- latitudineText ----
            latitudineText.setEnabled(false);

            //---- nomeLuogoText ----
            nomeLuogoText.setEnabled(false);

            //---- nomeLuogoLabel ----
            nomeLuogoLabel.setText("Nome luogo :");

            //---- coordLabel ----
            coordLabel.setText("Coordinate :");

            //---- luogoCheckBox ----
            luogoCheckBox.setText("Inserisci luogo?");

            //---- nomeLuogoCheckBox ----
            nomeLuogoCheckBox.setEnabled(false);

            //---- coordinateCheckBox ----
            coordinateCheckBox.setEnabled(false);

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addComponent(hSpacer1, GroupLayout.DEFAULT_SIZE, 708, Short.MAX_VALUE)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(goBackButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(confermaButton))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(fotoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addComponent(nomeSoggLabel)
                                        .addGap(18, 18, 18)
                                        .addComponent(nomeSoggTextField, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addComponent(coordLabel, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(latitudineText, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(longitudineText, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addComponent(nomeLuogoLabel, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(nomeLuogoText, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addComponent(label3, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(categoriaSoggList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addComponent(privataSwitch, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(apriFoto))
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addComponent(tagsLabel)
                                        .addGap(18, 18, 18)
                                        .addComponent(tags, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addComponent(dispLabel)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(selDisp, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE))))
                            .addComponent(luogoCheckBox))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel1Layout.createParallelGroup()
                            .addComponent(coordinateCheckBox)
                            .addComponent(nomeLuogoCheckBox, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 77, Short.MAX_VALUE))
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(luogoCheckBox)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup()
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(nomeLuogoText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nomeLuogoLabel)
                                    .addComponent(nomeLuogoCheckBox))
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(longitudineText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(latitudineText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(coordLabel)
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addGap(7, 7, 7)
                                        .addComponent(coordinateCheckBox)))
                                .addGap(18, 18, 18)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(categoriaSoggList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label3))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(nomeSoggTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nomeSoggLabel))
                                .addGap(18, 18, 18)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(selDisp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dispLabel))
                                .addGap(12, 12, 12)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(tags, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tagsLabel))
                                .addGap(18, 18, 18)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(apriFoto)
                                    .addComponent(privataSwitch)))
                            .addComponent(fotoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(confermaButton)
                            .addComponent(goBackButton))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(hSpacer1, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))
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
                .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel panel1;
    private JButton apriFoto;
    private JRadioButton privataSwitch;
    private JComboBox selDisp;
    private JButton goBackButton;
    private JButton confermaButton;
    private JPanel fotoPanel;
    private JLabel fotoAnteprima;
    private TagTextField tags;
    private JLabel dispLabel;
    private JLabel tagsLabel;
    private JComboBox categoriaSoggList;
    private JTextField nomeSoggTextField;
    private JLabel label3;
    private JLabel nomeSoggLabel;
    private JTextField longitudineText;
    private JTextField latitudineText;
    private JTextField nomeLuogoText;
    private JLabel nomeLuogoLabel;
    private JLabel coordLabel;
    private JPanel hSpacer1;
    private JCheckBox luogoCheckBox;
    private JCheckBox nomeLuogoCheckBox;
    private JCheckBox coordinateCheckBox;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
