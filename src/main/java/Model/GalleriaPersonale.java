package Model;

import java.util.ArrayList;

public class GalleriaPersonale {
    private int codGalleria;
    private Utente proprietario;
    private ArrayList<Foto> foto; // chiavi esterne

    public GalleriaPersonale(int codGalleria, Utente proprietario) {
        this.codGalleria = codGalleria;
        this.proprietario = proprietario;
        this.foto = new ArrayList<>();
    }

    public int getCodGalleria() {
        return codGalleria;
    }

    public void setCodGalleria(int codGalleria) {
        this.codGalleria = codGalleria;
    }

    public Utente getProprietario() {
        return proprietario;
    }

    public void setProprietario(Utente proprietario) {
        this.proprietario = proprietario;
    }

    public ArrayList<Foto> getFoto() {
        return foto;
    }

    public void setPhotos(ArrayList<Foto> foto) {
        this.foto = foto;
    }

    public void addFoto(Foto foto) {
        this.foto.add(foto);
    }

    public void removeFoto(Foto foto) {
        this.foto.remove(foto);
    }

}