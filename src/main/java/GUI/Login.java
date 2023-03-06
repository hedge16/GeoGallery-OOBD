package GUI;
import java.awt.*;

import Controller.Controller;
import Model.GalleriaPersonale;
import Model.Utente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
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


        URL logoUrl = ClassLoader.getSystemResource("logo.png");
        URL sfondoUrl = ClassLoader.getSystemResource("wallpaper.png");
        ImageIcon icon = new ImageIcon(logoUrl);
        ImageIcon sfondo = new ImageIcon(sfondoUrl);
        JLabel labelSfondo = new JLabel(sfondo);
        logoLabel.setIcon(icon);
        labelSfondo.setSize(rootPanel.getSize());
        rootPanel.add(labelSfondo);

        rootPanel.setComponentZOrder(labelSfondo, 7);

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
                            controller.setUtente(listaUtenti.get(i));
                            int codg = controller.getCodGalleriaDB(controller.getUtente().getUsername());
                            controller.getUtente().setGalleriaPersonale(new GalleriaPersonale(codg, controller.getUtente()));
                        }
                        i++;
                    }
                    if (found) {
                        JProgressBar progressBar = new JProgressBar();
                        progressBar.setIndeterminate(true);
                        JOptionPane optionPane = new JOptionPane(progressBar, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
                        JDialog dialog = optionPane.createDialog(frame1, "Caricamento in corso...");
                        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                            @Override
                            protected Void doInBackground() throws Exception {
                                Home home = new Home(controller, frame1);
                                dialog.dispose();
                                frame1.setVisible(false);
                                home.mainFrame.setVisible(true);
                                return null;
                            }
                            @Override
                            protected void done() {
                                dialog.dispose();
                            }
                        };
                        worker.execute();
                        dialog.setVisible(true);

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


        URL iconUrl = ClassLoader.getSystemResource("icona.png");
        try {
            //set icon for mac
            Taskbar.getTaskbar().setIconImage(Toolkit.getDefaultToolkit().getImage(iconUrl));
        } catch (Exception e) {
            System.out.println("Non è possibile impostare l'icona per mac");
        }
        frame1 = new JFrame("Login");
        frame1.setContentPane(new Login().rootPanel);
        frame1.setIconImage(Toolkit.getDefaultToolkit().getImage(iconUrl));
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




