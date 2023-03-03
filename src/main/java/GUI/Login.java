package GUI;
import java.awt.*;

import Controller.Controller;
import Model.Utente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import com.formdev.flatlaf.FlatDarculaLaf;


public class Login extends JFrame {

    protected static JFrame frame1;
    private JLabel userIDLabel;
    private JLabel userPassLabel;
    private JLabel loginLabel;
    private Controller controller;


    public Login() {

        try{
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        }catch(Exception e){
            System.out.println("Tema non trovato");
        }

        initComponents();


        String userDir = System.getProperty("user.dir");
        ImageIcon icon = new ImageIcon(userDir + "/src/icons/logo.png");
        logoLabel.setIcon(icon);


        ImageIcon sfondo = new ImageIcon(userDir + "/src/icons/wallpaper.png");
        JLabel labelSfondo = new JLabel(sfondo);
        labelSfondo.setSize(rootPanel.getSize());
        rootPanel.add(labelSfondo);

        rootPanel.setComponentZOrder(labelSfondo, 1);
        rootPanel.setComponentZOrder(loginButton, 0);
        rootPanel.setComponentZOrder(registerButton, 0);
        rootPanel.setComponentZOrder(userIDField, 0);
        rootPanel.setComponentZOrder(userPassField, 0);
        rootPanel.setComponentZOrder(logoLabel, 0);
        rootPanel.setComponentZOrder(usernameLabel, 0);
        rootPanel.setComponentZOrder(passwordLabel, 0);

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
                        JProgressBar progressBar = new JProgressBar();
                        progressBar.setIndeterminate(true);
                        Home home = new Home(controller, frame1, userInput);
                        progressBar.setIndeterminate(false);
                        progressBar.setVisible(false);
                        frame1.setVisible(false);
                        frame1.dispose();
                        home.mainFrame.setVisible(true);

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

        try {
            //set icon for mac
            Taskbar.getTaskbar().setIconImage(Toolkit.getDefaultToolkit().getImage("src/icons/icona.png"));
        } catch (Exception e) {
            //set icon for windows
            frame1.setIconImage(Toolkit.getDefaultToolkit().getImage("src/icons/icona.png"));
        }
        frame1 = new JFrame("Login");
        frame1.setIconImage(Toolkit.getDefaultToolkit().getImage("src/icons/icona.png"));
        frame1.setContentPane(new Login().rootPanel);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.pack();
        frame1.setLocationRelativeTo(null);
        frame1.setResizable(false);
        frame1.setVisible(true);
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel rootPanel;
    private JButton registerButton;
    private JButton loginButton;
    private JPasswordField userPassField;
    private JTextField userIDField;
    private JLabel logoLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        rootPanel = new JPanel();
        registerButton = new JButton();
        loginButton = new JButton();
        userPassField = new JPasswordField();
        userIDField = new JTextField();
        logoLabel = new JLabel();
        usernameLabel = new JLabel();
        passwordLabel = new JLabel();

        //======== this ========
        var contentPane = getContentPane();

        //======== rootPanel ========
        {
            rootPanel.setBorder(null);
            rootPanel.setBackground(new Color(0x2a3c4c));

            //---- registerButton ----
            registerButton.setText("REGISTRATI");

            //---- loginButton ----
            loginButton.setText("ACCEDI");
            loginButton.setBackground(new Color(0x006699));

            //---- usernameLabel ----
            usernameLabel.setText("Username :");

            //---- passwordLabel ----
            passwordLabel.setText("Password :");

            GroupLayout rootPanelLayout = new GroupLayout(rootPanel);
            rootPanel.setLayout(rootPanelLayout);
            rootPanelLayout.setHorizontalGroup(
                rootPanelLayout.createParallelGroup()
                    .addGroup(rootPanelLayout.createSequentialGroup()
                        .addContainerGap(52, Short.MAX_VALUE)
                        .addGroup(rootPanelLayout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, rootPanelLayout.createSequentialGroup()
                                .addGroup(rootPanelLayout.createParallelGroup()
                                    .addGroup(GroupLayout.Alignment.TRAILING, rootPanelLayout.createSequentialGroup()
                                        .addComponent(registerButton, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
                                        .addGap(32, 32, 32))
                                    .addGroup(GroupLayout.Alignment.TRAILING, rootPanelLayout.createSequentialGroup()
                                        .addGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addComponent(passwordLabel)
                                            .addComponent(usernameLabel))
                                        .addGap(18, 18, 18)))
                                .addGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(userPassField, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                                    .addComponent(userIDField, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                                    .addComponent(loginButton, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE))
                                .addGap(150, 150, 150))
                            .addGroup(GroupLayout.Alignment.TRAILING, rootPanelLayout.createSequentialGroup()
                                .addComponent(logoLabel, GroupLayout.PREFERRED_SIZE, 584, GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47))))
            );
            rootPanelLayout.setVerticalGroup(
                rootPanelLayout.createParallelGroup()
                    .addGroup(rootPanelLayout.createSequentialGroup()
                        .addContainerGap(54, Short.MAX_VALUE)
                        .addComponent(logoLabel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(userIDField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(usernameLabel))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(rootPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(userPassField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(passwordLabel))
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




