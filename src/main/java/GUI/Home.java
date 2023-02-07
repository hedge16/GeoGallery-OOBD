package GUI;

import java.awt.*;
import javax.swing.border.*;
import Controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import GUI.Components.FotoPanel;
import Model.Foto;




public class Home extends JFrame  {
    protected JFrame mainFrame;
    private ArrayList<Foto> photos;





    public Home (Controller controller, JFrame frameChiamante, String username){

        photos = controller.recuperaGallUtente(username);

        initComponents();

        String userDir = System.getProperty("user.dir");
        ImageIcon icon = new ImageIcon(userDir + "/src/icons/icona.png");

        label2.setIcon(icon);


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

                CaricaFoto caricaFoto = new CaricaFoto(controller, mainFrame, username);
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

                CreaGalleriaCondivisa cgc = new CreaGalleriaCondivisa(username, controller, mainFrame, photos);
                mainFrame.setVisible(false);
                cgc.mainFrame.setVisible(true);


            }
        });



    }



    /*
    *

        scrollPanel = new JScrollPane(new FotoPanel(photos, true));
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    *
    *
    *
    *
    * */





    private void initComponents () {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        menuBar = new JMenuBar();
        azioniMenu = new JMenu();
        logoutItem = new JMenuItem();
        panel = new JPanel();
        caricaFoto = new JButton();
        scrollPanel = new JScrollPane();
        benvenutoLabel = new JLabel();
        searchBar = new JTextField();
        creaGallCButton = new JButton();
        label1 = new JLabel();
        label2 = new JLabel();

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
            }

            //---- benvenutoLabel ----
            benvenutoLabel.setText("Benvenuto, [username] !");
            benvenutoLabel.setBackground(new Color(0xccccff));
            benvenutoLabel.setFont(new Font("Futura Bk BT", Font.PLAIN, 25));

            //---- searchBar ----
            searchBar.setText("@username o #luogo");
            searchBar.setForeground(new Color(0xcccccc));
            searchBar.setCaretColor(new Color(0xcccccc));
            searchBar.setBackground(new Color(0x666666));

            //---- creaGallCButton ----
            creaGallCButton.setText("CREA GALLERIA CONDIVISA");

            //---- label1 ----
            label1.setText("Le tue galleria condivise :");

            //---- label2 ----
            label2.setMaximumSize(new Dimension(30, 30));
            label2.setPreferredSize(new Dimension(30, 30));

            GroupLayout panelLayout = new GroupLayout(panel);
            panel.setLayout(panelLayout);
            panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup()
                    .addGroup(panelLayout.createSequentialGroup()
                        .addContainerGap(32, Short.MAX_VALUE)
                        .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(scrollPanel, GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                            .addComponent(benvenutoLabel, GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE))
                        .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addGroup(panelLayout.createParallelGroup()
                                    .addComponent(caricaFoto, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addGroup(panelLayout.createParallelGroup()
                                            .addComponent(label1)
                                            .addComponent(creaGallCButton))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(label2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchBar, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(51, Short.MAX_VALUE))
            );
            panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup()
                    .addGroup(panelLayout.createSequentialGroup()
                        .addContainerGap(14, Short.MAX_VALUE)
                        .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(searchBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(63, 63, 63)
                                .addComponent(label1)
                                .addGap(255, 255, 255)
                                .addComponent(creaGallCButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(caricaFoto, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addComponent(benvenutoLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrollPanel, GroupLayout.PREFERRED_SIZE, 409, GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(23, Short.MAX_VALUE))
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
    private JTextField searchBar;
    private JButton creaGallCButton;
    private JLabel label1;
    private JLabel label2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
