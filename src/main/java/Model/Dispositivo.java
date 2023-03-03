package Model;

import java.util.ArrayList;

public class Dispositivo {
    private int id;
    private String nome;
    private String proprietarioUsername;
    private Utente proprietario;
    private ArrayList<Foto> photos;
    public Dispositivo(int id, String nome, String proprietarioUser) {
        this.id = id;
        this.nome = nome;
        this.proprietarioUsername = proprietarioUser;
        this.photos = new ArrayList<>();
    }

    public Dispositivo(int id, String nome, Utente proprietario) {
        this.id = id;
        this.nome = nome;
        this.proprietario = proprietario;
        this.photos = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getProprietarioUsername () {
        return proprietarioUsername;
    }

    public void setProprietarioUsername (String proprietario) {
       this.proprietarioUsername = proprietario;
    }

    public ArrayList<Foto> getPhotos() {
            return photos;
    }
    public void addPhoto(Foto photo) {
        this.photos.add(photo);
    }
    public void removePhoto(Foto photo) {
        this.photos.remove(photo);
    }
    public void setPhotos(ArrayList<Foto> photos) {
        this.photos = photos;
    }

    public void setId(int id) {
        this.id = id;
    }



}
