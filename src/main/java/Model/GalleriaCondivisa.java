package Model;

import java.util.ArrayList;

public class GalleriaCondivisa {
    private int codGalleria;
    private String nomeGalleria;
    private ArrayList<String> utenti; // chiavi esterne

    public GalleriaCondivisa(int codGalleria, String nomeGalleria) {
        this.codGalleria = codGalleria;
        this.nomeGalleria = nomeGalleria;
        this.utenti = new ArrayList<String>();
    }

    public int getCodGalleria() {
        return codGalleria;
    }

    public void setCodGalleria(int codGalleria) {
        this.codGalleria = codGalleria;
    }

    public String getNomeGalleria() {
        return nomeGalleria;
    }

    public void setNomeGalleria(String nomeGalleria) {
        this.nomeGalleria = nomeGalleria;
    }

    public ArrayList<String> getUtenti() {
        return utenti;
    }

    public void setUtenti(ArrayList<String> utenti) {
        this.utenti = utenti;
    }

    public void addUtente(String utente) {
        this.utenti.add(utente);
    }

    public void removeUtente(String utente) {
        this.utenti.remove(utente);
    }
}
