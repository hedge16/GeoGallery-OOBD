package Model;

import java.util.ArrayList;

public class Foto {
    private String codFoto;
    private boolean privata;
    private boolean rimossa;
    private String codAlbum;//chiave esterna
    private String codGalleriap;//chiave esterna
    private String dispositivo;//chiave esterna
    private ArrayList<SoggettoFoto> soggetti;


    public Foto(String codFoto, boolean privata, boolean rimossa, String codAlbum, String codGalleria, String dispositivo) {
        this.codFoto = codFoto;
        this.privata = privata;
        this.rimossa = rimossa;
        this.codAlbum = codAlbum;
        this.codGalleriap = codGalleria;
        this.dispositivo = dispositivo;
        this.soggetti = new ArrayList<>();
    }

    public boolean isPrivata() {
        return privata;
    }

    public boolean isRimossa() {
        return rimossa;
    }

    public void setRimossa(boolean rimossa) {
        this.rimossa = rimossa;
    }

    public String getCodAlbum() {
        return codAlbum;
    }

    public void setCodAlbum(String codAlbum) {
        this.codAlbum = codAlbum;
    }

    public String getCodGalleria() {
        return codGalleriap;
    }

    public void setCodGalleria(String codGalleria) {
        this.codGalleriap = codGalleria;
    }

    public String getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }


}
