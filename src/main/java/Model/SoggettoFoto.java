package Model;

import java.util.ArrayList;

public class SoggettoFoto {
    private int id;
    private String nome;
    private CategoriaSoggetto categoria;


    public SoggettoFoto(int id, String nome, CategoriaSoggetto categoria) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public CategoriaSoggetto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaSoggetto categoria) {
        this.categoria = categoria;
    }


}
