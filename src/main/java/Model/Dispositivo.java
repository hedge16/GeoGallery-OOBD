package Model;

import java.util.ArrayList;

/**
 * The type Dispositivo.
 */
public class Dispositivo {
    private int id;
    private String nome;
    private String proprietarioUsername;
    private Utente proprietario;
    private ArrayList<Foto> photos;

    /**
     * Instantiates a new Dispositivo.
     *
     * @param id               the id
     * @param nome             the nome
     * @param proprietarioUser the proprietario user
     */
    public Dispositivo(int id, String nome, String proprietarioUser) {
        this.id = id;
        this.nome = nome;
        this.proprietarioUsername = proprietarioUser;
        this.photos = new ArrayList<>();
    }

    /**
     * Instantiates a new Dispositivo.
     *
     * @param id           the id
     * @param nome         the nome
     * @param proprietario the proprietario
     */
    public Dispositivo(int id, String nome, Utente proprietario) {
        this.id = id;
        this.nome = nome;
        this.proprietario = proprietario;
        this.photos = new ArrayList<>();
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets nome.
     *
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Gets proprietario username.
     *
     * @return the proprietario username
     */
    public String getProprietarioUsername () {
        return proprietarioUsername;
    }

    /**
     * Sets proprietario username.
     *
     * @param proprietario the proprietario
     */
    public void setProprietarioUsername (String proprietario) {
       this.proprietarioUsername = proprietario;
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
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }



}
