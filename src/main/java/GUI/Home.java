package GUI;

import Controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;



public class Home extends JFrame {
    protected JFrame mainFrame;
    private JPanel panel;




    public Home (Controller controller, JFrame frameChiamante, String username){


        initComponents(controller, username);


        mainFrame = new JFrame("Home");
        mainFrame.setContentPane(panel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(frameChiamante);




        caricaFoto.addActionListener (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                CaricaFoto caricaFoto = new CaricaFoto(controller, mainFrame, username);
                mainFrame.setVisible(false);
                caricaFoto.mainFrame.setVisible(true);



            }
        });



    }



    /*
    *    ArrayList<ImageIcon> miniature = controller.recuperaGallUtente(username);

        scrollPanel = new JScrollPane(new FotoPanel(miniature));
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    *
    *
    *
    *
    * */





    private void initComponents(Controller controller, String username) {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel = new JPanel();
        caricaFoto = new JButton();
        ArrayList<ImageIcon> miniature = controller.recuperaGallUtente(username);

        scrollPanel = new JScrollPane(new FotoPanel(miniature));
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        //======== this ========
        var contentPane = getContentPane();

        //======== panel ========
        {

            //---- caricaFoto ----
            caricaFoto.setText("CARICA FOTO");

            GroupLayout panelLayout = new GroupLayout(panel);
            panel.setLayout(panelLayout);
            panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scrollPanel, GroupLayout.PREFERRED_SIZE, 402, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(caricaFoto)
                        .addGap(94, 94, 94))
            );
            panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup()
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup()
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addComponent(caricaFoto))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPanel, GroupLayout.PREFERRED_SIZE, 380, GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(33, Short.MAX_VALUE))
            );
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JButton caricaFoto;
    private JScrollPane scrollPanel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
