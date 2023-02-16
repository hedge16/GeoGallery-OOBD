package GUI;

import Controller.Controller;
import org.postgresql.util.PSQLException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;



public class Register {

    protected JFrame frame;
    private JPanel mainPanel;
    private JLabel nome;
    private JLabel cognome;
    private JLabel username;
    private JLabel password;
    private JLabel email;
    private JButton registrati;
    private JButton tornaIndietro;
    private JTextField nomeText;
    private JTextField cognomeText;
    private JTextField usernameText;
    private JTextField emailText;
    private JButton registratiButton;
    private JPasswordField passwordField;


    public Register (Controller controller, JFrame frameChiamante){

        frame = new JFrame("Register");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(frameChiamante);

       registratiButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               // qui ci vuole la verifica dei dati e l'eventuale inserimento nella base di dati
               Date data = new Date();
               String passwordOut = new String(passwordField.getPassword());
               if (passwordOut == null || passwordOut.isEmpty()) {
                   // mostra un messaggio di errore all'utente e non esegui il metodo aggiungiUtente()
                   JOptionPane.showMessageDialog(frame, "La password non può essere nulla o vuota", "Errore", JOptionPane.ERROR_MESSAGE);
               } else {
                   // esegui il metodo aggiungiUtente()
                   try{
                       controller.aggiungiUtente(nomeText.getText(), cognomeText.getText(), usernameText.getText(), passwordOut, emailText.getText(), data);
                       //controller.creaGalleria(username);
                       JOptionPane.showMessageDialog(frame, "Registrazione andata a buon fine.");
                       frame.setVisible(false);
                       frameChiamante.setVisible(true);
                       frame.dispose();
                       }
                   catch (PSQLException p1){
                       JOptionPane.showMessageDialog(frame, "Registrazione non andata a buon fine, username già utilizzato.", "Errore Registrazione", JOptionPane.ERROR_MESSAGE);

                   }catch(SQLException s){
                       JOptionPane.showMessageDialog(frame, "Errore nella creazione della Galleria.", "Errore", JOptionPane.ERROR_MESSAGE);
                   }

               }

           }
       });

       tornaIndietro.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               frame.setVisible(false);
               frame.dispose();
               frameChiamante.setVisible(true);
           }
       });

    }



}
