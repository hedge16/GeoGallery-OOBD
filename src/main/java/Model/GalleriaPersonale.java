package Model;

import java.util.ArrayList;

/**
 * The type Galleria personale.
 */
public class GalleriaPersonale {
    private int codGalleria;
    private Utente proprietario;
    private ArrayList<Foto> foto; // chiavi esterne

    /**
     * Instantiates a new Galleria personale.
     *
     * @param codGalleria  the cod galleria
     * @param proprietario the proprietario
     */
    public GalleriaPersonale(int codGalleria, Utente proprietario) {
        this.codGalleria = codGalleria;
        this.proprietario = proprietario;
        this.foto = new ArrayList<>();
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
     * Gets proprietario.
     *
     * @return the proprietario
     */
    public Utente getProprietario() {
        return proprietario;
    }

    /**
     * Sets proprietario.
     *
     * @param proprietario the proprietario
     */
    public void setProprietario(Utente proprietario) {
        this.proprietario = proprietario;
    }

    /**
     * Gets photos.
     *
     * @return the photos
     */
    public ArrayList<Foto> getPhotos() {
        return foto;
    }

    /**
     * Sets photos.
     *
     * @param foto the foto
     */
    public void setPhotos(ArrayList<Foto> foto) {
        this.foto = foto;
    }

    /**
     * Add foto.
     *
     * @param foto the foto
     */
    public void addFoto(Foto foto) {
        this.foto.add(foto);
    }

    /**
     * Remove foto.
     *
     * @param foto the foto
     */
    public void removeFoto(Foto foto) {
        this.foto.remove(foto);
    }

}