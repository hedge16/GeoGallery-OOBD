package Model;

import java.util.ArrayList;

public class GalleriaCondivisa {
    private int codGalleria;
    private String nomeGalleria;
    private ArrayList<Utente> proprietari; // chiavi esterne
    private ArrayList<Foto> photos;

    public GalleriaCondivisa(int codGalleria, String nomeGalleria) {
        this.codGalleria = codGalleria;
        this.nomeGalleria = nomeGalleria;
        this.proprietari = new ArrayList<>();
        this.photos = new ArrayList<>();
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

    public ArrayList<Utente> getUtenti() {
        return proprietari;
    }

    public void setUtenti(ArrayList<Utente> utenti) {
        this.proprietari = utenti;
    }

    public void addUtente(Utente utente) {
        this.proprietari.add(utente);
    }

    public void removeUtente(Utente utente) {
        this.proprietari.remove(utente);
    }
}
