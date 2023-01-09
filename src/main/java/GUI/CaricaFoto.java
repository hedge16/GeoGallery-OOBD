package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;
import javax.swing.filechooser.FileFilter;

import Controller.Controller;
import Model.CategoriaSoggetto;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class CaricaFoto extends JFrame {

    protected JFrame mainFrame;
    int result=-1;
    String filePath;
    private int codFoto;

    public CaricaFoto (Controller controller, JFrame frameChiamante, String username) {


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
                if (result == JFileChooser.APPROVE_OPTION) {

                    // Carica l'immagine nel DB
                    try {
                        codFoto = controller.aggiungiFoto(toggleButton1.isSelected(), false, new Date(), controller.getCodG(username), username, controller.getCodDisp((String)selDisp.getSelectedItem(), username), filePath);
                        int i = 0;
                        if (!tags.getText().equals("")) {
                            String[] tagArr = tags.getText().split(",");
                            int numTag = tagArr.length;
                            while (i < numTag) {
                                controller.aggiungiTagFoto(tagArr[i], codFoto);
                            }
                        }
                        controller.aggiungiSoggettoFoto(categorie[categoriaSoggList.getSelectedIndex()], nomeSoggTextField.getText());
                        //controller.aggiungiLuogo
                        JOptionPane.showMessageDialog(mainFrame, "Foto caricata con successo.");
                        mainFrame.setVisible(false);
                        mainFrame.dispose();
                        Home home = new Home(controller, frameChiamante, username);
                        home.mainFrame.setVisible(true);
                    } catch (SQLException s) {
                        JOptionPane.showMessageDialog(mainFrame, "Errore nel caricamento del file.", "Errore col DB", JOptionPane.ERROR_MESSAGE);
                    } catch (FileNotFoundException f) {
                        JOptionPane.showMessageDialog(mainFrame, "File non trovato.", "File not found error", JOptionPane.ERROR_MESSAGE);

                    }


                } else if (result == JFileChooser.ERROR) {
                    JOptionPane.showMessageDialog(mainFrame, "Non è stato possibile caricare il file.", "Errore file", JOptionPane.ERROR_MESSAGE);


                }else{
                    JOptionPane.showMessageDialog(mainFrame, "Non è stato selezionato il file.", "Errore selezione", JOptionPane.ERROR_MESSAGE);
                }

            }
        });


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
        toggleButton1 = new JToggleButton();
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
        label1 = new JLabel();
        label2 = new JLabel();
        categoriaSoggList = new JComboBox();
        nomeSoggTextField = new JTextField();
        label3 = new JLabel();
        label4 = new JLabel();
        textField2 = new JTextField();
        textField3 = new JTextField();
        textField4 = new JTextField();
        label5 = new JLabel();
        label6 = new JLabel();

        //======== this ========
        var contentPane = getContentPane();

        //======== panel1 ========
        {

            //---- apriFoto ----
            apriFoto.setText("Apri Foto");

            //---- toggleButton1 ----
            toggleButton1.setText("privata?");

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

            //---- label1 ----
            label1.setText("Dispositivo :");

            //---- label2 ----
            label2.setText("Tags :");

            //---- label3 ----
            label3.setText("Categoria :");

            //---- label4 ----
            label4.setText("Nome soggetto :");

            //---- label5 ----
            label5.setText("Nome luogo :");

            //---- label6 ----
            label6.setText("Coordinate :");

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                        .addGap(334, 334, 334)
                        .addComponent(goBackButton)
                        .addGap(18, 18, 18)
                        .addComponent(confermaButton)
                        .addContainerGap(76, Short.MAX_VALUE))
                    .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(fotoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(label2)
                                .addGap(18, 18, 18)
                                .addComponent(tags, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(toggleButton1, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(apriFoto))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(label1)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(selDisp, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(label4)
                                .addGap(18, 18, 18)
                                .addComponent(nomeSoggTextField, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(label6, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(textField3, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textField2, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(label5, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textField4, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(label3, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(categoriaSoggList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 74, Short.MAX_VALUE))
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(panel1Layout.createParallelGroup()
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(textField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label5))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label6))
                                .addGap(18, 18, 18)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(categoriaSoggList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label3))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(nomeSoggTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label4))
                                .addGap(18, 18, 18)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(selDisp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label1))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(tags, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label2))
                                .addGap(18, 18, 18)
                                .addGroup(panel1Layout.createParallelGroup()
                                    .addComponent(apriFoto)
                                    .addComponent(toggleButton1)))
                            .addComponent(fotoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(confermaButton)
                            .addComponent(goBackButton))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
    private JToggleButton toggleButton1;
    private JComboBox selDisp;
    private JButton goBackButton;
    private JButton confermaButton;
    private JPanel fotoPanel;
    private JLabel fotoAnteprima;
    private TagTextField tags;
    private JLabel label1;
    private JLabel label2;
    private JComboBox categoriaSoggList;
    private JTextField nomeSoggTextField;
    private JLabel label3;
    private JLabel label4;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JLabel label5;
    private JLabel label6;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
