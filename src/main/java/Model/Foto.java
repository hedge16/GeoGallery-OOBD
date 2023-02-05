package Model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;

public class Foto {
    private int codFoto;
    private boolean privata;
    private boolean rimossa;
    private Date dataScatto;
    private int codGalleriap;//chiave esterna
    private String autore;
    private int codDispositivo;//chiave esterna
    private ArrayList<SoggettoFoto> soggetti;
    private ImageIcon foto;
    int codLuogo;


    public Foto(int codFoto, boolean privata, boolean rimossa, Date d, int codGalleria, String autore, int codDispositivo, ImageIcon foto, int codLuogo) {
        this.codFoto = codFoto;
        this.privata = privata;
        this.rimossa = rimossa;
        this.dataScatto = d;
        this.codGalleriap = codGalleria;
        this.autore = autore;
        this.codDispositivo = codDispositivo;
        this.foto = foto;
        this.codLuogo = codLuogo;
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

    public int getCodGalleria() {
        return codGalleriap;
    }

    public void setCodGalleria(int codGalleria) {
        this.codGalleriap = codGalleria;
    }

    public int getDispositivo() {
        return codDispositivo;
    }

    public void setDispositivo(int dispositivo) {
        this.codDispositivo = dispositivo;
    }
    public void aggiungiSoggetto (int idSogg, String nome, CategoriaSoggetto categoria) {
        soggetti.add(new SoggettoFoto(idSogg, nome, categoria));
    };

    public ImageIcon getFoto (){
        return foto;
    }

    public void setFoto (ImageIcon foto) {
        this.foto = foto;
    }
    public int getCodFoto() {
        return codFoto;
    }

    public void setCodFoto(int codFoto) {
        this.codFoto = codFoto;
    }

    public void setPrivata(boolean privata) {
        this.privata = privata;
    }

    public Date getDataScatto() {
        return dataScatto;
    }

    public void setDataScatto(Date dataScatto) {
        this.dataScatto = dataScatto;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public ArrayList<SoggettoFoto> getSoggetti() {
        return soggetti;
    }

    public void setSoggetti(ArrayList<SoggettoFoto> soggetti) {
        this.soggetti = soggetti;
    }

    public int getCodLuogo() {
        return codLuogo;
    }

    public void setCodLuogo(int codLuogo) {
        this.codLuogo = codLuogo;
    }



}
