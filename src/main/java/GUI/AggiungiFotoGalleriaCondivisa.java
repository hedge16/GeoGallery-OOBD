/*
 * Created by JFormDesigner on Thu Mar 16 23:38:40 CET 2023
 */

package GUI;

import Controller.Controller;
import GUI.Components.FotoPanel;

import javax.swing.*;
import javax.swing.GroupLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Aggiungi foto galleria condivisa.
 *
 * @author vincenzo
 */
public class AggiungiFotoGalleriaCondivisa extends JFrame {
    /**
     * The Main frame.
     */
    protected JFrame mainFrame;
    private JPanel panel1;
    private JScrollPane scrollPanel;
    private JButton confermaSelezioneButton;

    /**
     * Instantiates a new Aggiungi foto galleria condivisa.
     *
     * @param frameChiamante the frame chiamante
     * @param panel          the panel
     * @param controller     the controller
     */
    public AggiungiFotoGalleriaCondivisa (JFrame frameChiamante, FotoPanel panel, Controller controller) {
        initComponents();
        mainFrame = new JFrame("Aggiungi foto");
        mainFrame.setContentPane(panel1);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(frameChiamante);
        mainFrame.setResizable(false);
        scrollPanel.setViewportView(panel);

        confermaSelezioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean[] selected = panel.getSelectedPhotos();
                if (selected.length == 0) {
                    JOptionPane.showMessageDialog(mainFrame, "Nessuna foto selezionata");
                } else {
                    try {
                        for (int i = 0; i < selected.length; i++) {
                            if (selected[i]) {
                                controller.aggiungiPresenzaFotoDB(controller.getUtente().getGalleriaPersonale().getPhotos().get(i).getCodFoto(), controller.getCodGalleriaCondivisa());
                            }
                        }
                        JOptionPane.showMessageDialog(mainFrame, "Foto aggiunte alla galleria condivisa");
                        mainFrame.dispose();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        JOptionPane.showMessageDialog(mainFrame, "Errore durante l'aggiunta della foto alla galleria condivisa");
                        mainFrame.dispose();
                    }
                }
            }
        });

    }

    private void initComponents() {
        panel1 = new JPanel();
        scrollPanel = new JScrollPane();
        confermaSelezioneButton = new JButton();

        //======== this ========
        var contentPane = getContentPane();

        //======== panel1 ========
        {

            //---- confermaSelezioneButton ----
            confermaSelezioneButton.setText("CONFERMA SELEZIONE");

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(scrollPanel, GroupLayout.PREFERRED_SIZE, 309, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(confermaSelezioneButton, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(36, Short.MAX_VALUE))
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addContainerGap(47, Short.MAX_VALUE)
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(confermaSelezioneButton, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                            .addComponent(scrollPanel, GroupLayout.PREFERRED_SIZE, 301, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(65, Short.MAX_VALUE))
            );
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 28, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());
    }

}
