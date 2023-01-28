package GUI.Components;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Controller.Controller;
import Model.Utente;

public class TagTextField extends JTextField {
    private ArrayList<String> suggestions; // lista di suggerimenti
    private JList suggestionList; // oggetto JList per visualizzare i suggerimenti
    private JPopupMenu suggestionPopup; // oggetto JPopupMenu per mostrare i suggerimenti
    ArrayList<Utente> utenti;

    public TagTextField(Controller controller) {
        // crea il DocumentFilter per filtrare gli input dell'utente
        DocumentFilter filter = new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                // filtra gli input in base alla lista di suggerimenti e mostra la lista di suggerimenti se l'input dell'utente corrisponde a uno dei suggerimenti
                String text = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;
                updateSuggestions(text);
                super.insertString(fb, offset, string, attr);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                // filtra gli input in base alla lista di suggerimenti e mostra la lista di suggerimenti se l'input dell'utente corrisponde a uno dei suggerimenti
                String newText = fb.getDocument().getText(0, fb.getDocument().getLength() - length) + text;
                updateSuggestions(newText);
                super.replace(fb, offset, length, text, attrs);
            }
        };

        // aggiunge il DocumentFilter al JTextField
        ((AbstractDocument) getDocument()).setDocumentFilter(filter);


        // recupera la lista di utenti dal database
        try {
            utenti = controller.leggiUtentiDB();
        } catch (SQLException s) {
            s.printStackTrace();
        }
        suggestions = controller.getUsernameFromUtente(utenti);
        System.out.println(suggestions);
        suggestionList = new JList(suggestions.toArray());
        // inizializza l'oggetto JPopupMenu e lo rende visibile quando si clicca con il mouse sulla JList
        suggestionPopup = new JPopupMenu();
        suggestionPopup.add(new JScrollPane(suggestionList));
        suggestionPopup.setVisible(false);

        // aggiunge l'oggetto JPopupMenu al JTextField
        add(suggestionPopup);

        // mostra la lista di suggerimenti quando si digita un carattere nel JTextField
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                updateSuggestions(getText());
            }
        });

        // nasconde la lista di suggerimenti quando si clicca con il mouse fuori dal JTextField
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                suggestionPopup.setVisible(false);
            }
        });

        // seleziona un suggerimento dalla lista di suggerimenti quando si clicca con il mouse su un elemento della lista
        suggestionList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // ottiene la posizione della virgola nella stringa di testo del JTextField
                int commaIndex = getText().lastIndexOf(",");
                // sostituisce il testo dopo la virgola con il suggerimento selezionato
                setText(getText().substring(0, commaIndex + 1) + suggestionList.getSelectedValue() + ",");
                suggestionPopup.setVisible(false);
            }
        });


    }

    private void updateSuggestions(String text) {
        String[] tags = text.split(",");
        String lastTag = tags[tags.length - 1].trim();

        // filtra la lista di suggerimenti in base all'ultimo tag inserito dall'utente
        List<String> filteredSuggestions = suggestions.stream()
                .filter(s -> s.toLowerCase().startsWith(lastTag.toLowerCase()))
                .collect(Collectors.toList());
        suggestionList.setListData(filteredSuggestions.toArray());

        // mostra la lista di suggerimenti se l'input dell'utente corrisponde a uno dei suggerimenti disponibili
        if (!filteredSuggestions.isEmpty()) {
            // posiziona la lista di suggerimenti sotto il JTextField
            suggestionPopup.setPreferredSize(new Dimension(getWidth(), suggestionList.getPreferredSize().height));
            suggestionPopup.show(this, 0, getHeight());
        } else {
            suggestionPopup.setVisible(false);
        }
    }


}
