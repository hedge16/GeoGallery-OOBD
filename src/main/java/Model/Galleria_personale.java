package Model;

import java.util.ArrayList;

public class Galleria_personale {
    private int codGalleria;
    private String proprietario;
    private ArrayList<Foto> foto; // chiavi esterne

    public Galleria_personale(int codGalleria, String proprietario) {
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

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public ArrayList<Foto> getFoto() {
        return foto;
    }

    public void setFoto(ArrayList<Foto> foto) {
        this.foto = foto;
    }

    public void addFoto(Foto foto) {
        this.foto.add(foto);
    }

    public void removeFoto(Foto foto) {
        this.foto.remove(foto);
    }
}