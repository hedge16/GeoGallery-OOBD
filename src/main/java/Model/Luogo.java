package Model;

public class Luogo {
    private int codLuogo;
    private String nomeMnemonico;
    private double latitudine;
    private double longitudine;

    public Luogo(int codLuogo, String nomeMnemonico, double latitudine, double longitudine) {
        this.codLuogo = codLuogo;
        this.nomeMnemonico = nomeMnemonico;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
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
}

