package Model;

import java.util.ArrayList;

/**
 * The type Luogo.
 */
public class Luogo {
    private int codLuogo;
    private String nomeMnemonico;
    private double latitudine;
    private double longitudine;
    private ArrayList<Foto> photos;
    private int numeroFoto;

    /**
     * Instantiates a new Luogo.
     *
     * @param codLuogo      the cod luogo
     * @param nomeMnemonico the nome mnemonico
     * @param latitudine    the latitudine
     * @param longitudine   the longitudine
     */
    public Luogo(int codLuogo, String nomeMnemonico, double latitudine, double longitudine) {
        this.codLuogo = codLuogo;
        this.nomeMnemonico = nomeMnemonico;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.photos = new ArrayList<>();
    }

    /**
     * Gets cod luogo.
     *
     * @return the cod luogo
     */
    public int getCodLuogo() {
        return codLuogo;
    }

    /**
     * Sets cod luogo.
     *
     * @param codLuogo the cod luogo
     */
    public void setCodLuogo(int codLuogo) {
        this.codLuogo = codLuogo;
    }

    /**
     * Gets nome mnemonico.
     *
     * @return the nome mnemonico
     */
    public String getNomeMnemonico() {
        return nomeMnemonico;
    }

    /**
     * Sets nome mnemonico.
     *
     * @param nomeMnemonico the nome mnemonico
     */
    public void setNomeMnemonico(String nomeMnemonico) {
        this.nomeMnemonico = nomeMnemonico;
    }

    /**
     * Gets latitudine.
     *
     * @return the latitudine
     */
    public double getLatitudine() {
        return latitudine;
    }

    /**
     * Sets latitudine.
     *
     * @param latitudine the latitudine
     */
    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }

    /**
     * Gets longitudine.
     *
     * @return the longitudine
     */
    public double getLongitudine() {
        return longitudine;
    }

    /**
     * Sets longitudine.
     *
     * @param longitudine the longitudine
     */
    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }

    /**
     * Gets photos.
     *
     * @return the photos
     */
    public ArrayList<Foto> getPhotos() {
        return photos;
    }

    /**
     * Add photo.
     *
     * @param photo the photo
     */
    public void addPhoto(Foto photo) {
        this.photos.add(photo);
    }

    /**
     * Remove photo.
     *
     * @param photo the photo
     */
    public void removePhoto(Foto photo) {
        this.photos.remove(photo);
    }

    /**
     * Sets photos.
     *
     * @param photos the photos
     */
    public void setPhotos(ArrayList<Foto> photos) {
        this.photos = photos;
    }

    /**
     * Gets numero foto.
     *
     * @return the numero foto
     */
    public int getNumeroFoto() {
        return numeroFoto;
    }

    /**
     * Sets numero foto.
     *
     * @param numeroFoto the numero foto
     */
    public void setNumeroFoto(int numeroFoto) {
        this.numeroFoto = numeroFoto;
    }
}

