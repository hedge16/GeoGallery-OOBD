/*
 * Created by JFormDesigner on Thu Mar 16 23:22:23 CET 2023
 */

package GUI;

import Controller.Controller;
import GUI.Components.FotoPanel;

import javax.swing.*;
import javax.swing.GroupLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Vedi galleria condivisa.
 *
 * @author vincenzo
 */
public class VediGalleriaCondivisa extends JFrame {
    /**
     * The Main frame.
     */
    protected JFrame mainFrame;

    private JPanel panel1;
    private JScrollPane scrollPanel;
    private JButton aggiungiFotoButton;

    /**
     * Instantiates a new Vedi galleria condivisa.
     *
     * @param controller   the controller
     * @param home         the home
     * @param panel        the panel
     * @param nomeGalleria the nome galleria
     */
    public VediGalleriaCondivisa (Controller controller, Home home, FotoPanel panel, String nomeGalleria) {
        initComponents();
        mainFrame = new JFrame(nomeGalleria);
        mainFrame.setContentPane(panel1);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(home.mainFrame);
        mainFrame.setResizable(false);
        scrollPanel.setViewportView(panel);

        aggiungiFotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FotoPanel fotopanel = null;
                if (controller.getFotoPanelSelectable() == null) {
                    fotopanel = new FotoPanel(controller.getUtente().getGalleriaPersonale().getPhotos(), false, controller, controller.getUtente().getUsername(), home);
                } else {
                    fotopanel = controller.getFotoPanelSelectable();
                }
                AggiungiFotoGalleriaCondivisa aggiungiFotoGalleriaCondivisa = new AggiungiFotoGalleriaCondivisa(mainFrame, fotopanel, controller);
                mainFrame.dispose();
                aggiungiFotoGalleriaCondivisa.mainFrame.setVisible(true);
            }
        });
    }

    private void initComponents() {
        panel1 = new JPanel();
        scrollPanel = new JScrollPane();
        aggiungiFotoButton = new JButton();

        //======== this ========
        var contentPane = getContentPane();

        //======== panel1 ========
        {

            //---- aggiungiFotoButton ----
            aggiungiFotoButton.setText("AGGIUNGI FOTO");

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(scrollPanel, GroupLayout.PREFERRED_SIZE, 311, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(aggiungiFotoButton)
                        .addContainerGap(37, Short.MAX_VALUE))
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                        .addContainerGap(37, Short.MAX_VALUE)
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(aggiungiFotoButton)
                            .addComponent(scrollPanel, GroupLayout.PREFERRED_SIZE, 316, GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30))
            );
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
    }

}
