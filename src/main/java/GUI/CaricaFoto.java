package GUI;

import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.filechooser.FileFilter;

import Controller.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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


        mainFrame = new JFrame();
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
                        return "Image files (*.png, *.jpg)";
                    }
                });
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                result = fileChooser.showOpenDialog(apriFoto);
                if (result == JFileChooser.APPROVE_OPTION) {
                    filePath = fileChooser.getSelectedFile().getAbsolutePath();
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
                        String[] tagArr = tags.getText().split(",");
                        int numTag = tagArr.length;
                        while (i < numTag ) {
                            controller.aggiungiTagFoto(tagArr[i], codFoto);
                        }
                        //controller.aggiungiSoggettoFoto(qua c'è il );
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
        tags = new TagTextField(controller);

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

                GroupLayout fotoPanelLayout = new GroupLayout(fotoPanel);
                fotoPanel.setLayout(fotoPanelLayout);
                fotoPanelLayout.setHorizontalGroup(
                    fotoPanelLayout.createParallelGroup()
                        .addGap(0, 271, Short.MAX_VALUE)
                );
                fotoPanelLayout.setVerticalGroup(
                    fotoPanelLayout.createParallelGroup()
                        .addGap(0, 237, Short.MAX_VALUE)
                );
            }

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup()
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addComponent(goBackButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 241, Short.MAX_VALUE)
                                .addComponent(confermaButton, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE))
                            .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                                .addGap(85, 85, 85)
                                .addComponent(fotoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 137, Short.MAX_VALUE)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(selDisp)
                                    .addComponent(toggleButton1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(apriFoto, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                                .addContainerGap(437, Short.MAX_VALUE)
                                .addComponent(tags, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)))
                        .addGap(76, 76, 76))
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup()
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addComponent(toggleButton1)
                                .addGap(109, 109, 109)
                                .addComponent(selDisp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(apriFoto))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(73, 73, 73)
                                .addComponent(fotoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                        .addGap(8, 8, 8)
                        .addComponent(tags, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(confermaButton)
                            .addComponent(goBackButton))
                        .addGap(26, 26, 26))
            );
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE))
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
    private TagTextField tags;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
