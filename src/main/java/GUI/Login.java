package GUI;
import java.awt.*;
import Controller.Controller;
import Model.Utente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.intellij.uiDesigner.core.*;


public class Login extends JFrame {

    private static JFrame frame1;



    private JLabel userIDLabel;
    private JLabel userPassLabel;

    private JLabel loginLabel;
    private Controller controller;


    public Login() {

        try{
            UIManager.setLookAndFeel(new FlatDarkLaf());
        }catch(Exception e){
            System.out.println("Tema non trovato");
        }

        initComponents();

        ImageIcon icon = new ImageIcon("D:\\Desktop\\GalleriaGeolocalizzata\\src\\icons\\download.png");
        label1.setIcon(icon);


        controller = new Controller();

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Register register = new Register(controller, frame1);

                frame1.setVisible(false);
                register.frame.setVisible(true);

            }

        });




        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String userInput = userIDField.getText();
                    String passInput = new String(userPassField.getPassword());
                    ArrayList<Utente> listaUtenti = controller.leggiUtentiDB();
                    boolean found = false;
                    int i = 0;

                    while (found == false && i < listaUtenti.size()) {
                        if (userInput.equals(listaUtenti.get(i).getUsername()) && passInput.equals(listaUtenti.get(i).getPassword())) {
                            found = true;
                        }
                        i++;
                    }

                    if (found) {

                        Home home = new Home(controller, frame1, userInput);
                        frame1.setVisible(false);
                        home.mainFrame.setVisible(true);
                        frame1.dispose();

                    } else {
                        JOptionPane.showMessageDialog(frame1, "Username o password non corretti", "Errore inserimento dati", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (SQLException s) {
                    JOptionPane.showMessageDialog(frame1, "Errore, non sono presenti utenti registrati.", "Errore", JOptionPane.ERROR_MESSAGE);

                }


            }
        });


    }

    public static void main(String[] args) {

        frame1 = new JFrame("Login");
        frame1.setContentPane(new Login().rootPanel);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.pack();
        frame1.setVisible(true);
    }



    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel rootPanel;
    private JButton registerButton;
    private JButton loginButton;
    private JPasswordField userPassField;
    private JTextField userIDField;
    private JLabel label1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        rootPanel = new JPanel();
        registerButton = new JButton();
        loginButton = new JButton();
        userPassField = new JPasswordField();
        userIDField = new JTextField();
        label1 = new JLabel();

        //======== this ========
        var contentPane = getContentPane();

        //======== rootPanel ========
        {

            //---- registerButton ----
            registerButton.setText("REGISTRATI");

            //---- loginButton ----
            loginButton.setText("ACCEDI");

            GroupLayout rootPanelLayout = new GroupLayout(rootPanel);
            rootPanel.setLayout(rootPanelLayout);
            rootPanelLayout.setHorizontalGroup(
                rootPanelLayout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, rootPanelLayout.createSequentialGroup()
                        .addContainerGap(169, Short.MAX_VALUE)
                        .addComponent(registerButton, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(userPassField, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                            .addComponent(userIDField, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                            .addComponent(loginButton, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE))
                        .addGap(150, 150, 150))
                    .addGroup(rootPanelLayout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(label1, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(423, Short.MAX_VALUE))
            );
            rootPanelLayout.setVerticalGroup(
                rootPanelLayout.createParallelGroup()
                    .addGroup(rootPanelLayout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(label1, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(userIDField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(userPassField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(loginButton, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                            .addComponent(registerButton, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                        .addGap(59, 59, 59))
            );
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(rootPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(rootPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
}




