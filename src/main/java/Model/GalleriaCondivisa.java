package Model;

import java.util.ArrayList;

/**
 * The type Galleria condivisa.
 */
public class GalleriaCondivisa {
    private int codGalleria;
    private String nomeGalleria;
    private ArrayList<Utente> proprietari; // chiavi esterne
    private ArrayList<Foto> photos;

    /**
     * Instantiates a new Galleria condivisa.
     *
     * @param codGalleria  the cod galleria
     * @param nomeGalleria the nome galleria
     */
    public GalleriaCondivisa(int codGalleria, String nomeGalleria) {
        this.codGalleria = codGalleria;
        this.nomeGalleria = nomeGalleria;
        this.proprietari = new ArrayList<>();
        this.photos = new ArrayList<>();
    }

    /**
     * Gets cod galleria.
     *
     * @return the cod galleria
     */
    public int getCodGalleria() {
        return codGalleria;
    }

    /**
     * Sets cod galleria.
     *
     * @param codGalleria the cod galleria
     */
    public void setCodGalleria(int codGalleria) {
        this.codGalleria = codGalleria;
    }

    /**
     * Gets nome galleria.
     *
     * @return the nome galleria
     */
    public String getNomeGalleria() {
        return nomeGalleria;
    }

    /**
     * Sets nome galleria.
     *
     * @param nomeGalleria the nome galleria
     */
    public void setNomeGalleria(String nomeGalleria) {
        this.nomeGalleria = nomeGalleria;
    }

    /**
     * Gets utenti.
     *
     * @return the utenti
     */
    public ArrayList<Utente> getUtenti() {
        return proprietari;
    }

    /**
     * Sets utenti.
     *
     * @param utenti the utenti
     */
    public void setUtenti(ArrayList<Utente> utenti) {
        this.proprietari = utenti;
    }

    /**
     * Add utente.
     *
     * @param utente the utente
     */
    public void addUtente(Utente utente) {
        this.proprietari.add(utente);
    }

    /**
     * Remove utente.
     *
     * @param utente the utente
     */
    public void removeUtente(Utente utente) {
        this.proprietari.remove(utente);
    }
}
