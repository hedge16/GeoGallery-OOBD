package Model;

import java.util.ArrayList;

public class Luogo {
    private int codLuogo;
    private String nomeMnemonico;
    private double latitudine;
    private double longitudine;
    private ArrayList<Foto> photos;
    private int numeroFoto;

    public Luogo(int codLuogo, String nomeMnemonico, double latitudine, double longitudine) {
        this.codLuogo = codLuogo;
        this.nomeMnemonico = nomeMnemonico;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.photos = new ArrayList<>();
    }

    public int getCodLuogo() {
        return codLuogo;
    }

    public void setCodLuogo(int codLuogo) {
        this.codLuogo = codLuogo;
    }

    public String getNomeMnemonico() {
        return nomeMnemonico;
    }

    public void setNomeMnemonico(String nomeMnemonico) {
        this.nomeMnemonico = nomeMnemonico;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
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
    public int getNumeroFoto() {
        return numeroFoto;
    }
    public void setNumeroFoto(int numeroFoto) {
        this.numeroFoto = numeroFoto;
    }
}

