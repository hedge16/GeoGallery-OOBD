package Model;

import java.util.ArrayList;

/**
 * The type Soggetto foto.
 */
public class SoggettoFoto {
    private int id;
    private String nome;
    private String categoria;
    private ArrayList<Foto> photos;


    /**
     * Instantiates a new Soggetto foto.
     *
     * @param id        the id
     * @param nome      the nome
     * @param categoria the categoria
     */
    public SoggettoFoto(int id, String nome, String categoria) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
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
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
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
     * Sets nome.
     *
     * @param nome the nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Gets categoria.
     *
     * @return the categoria
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Sets categoria.
     *
     * @param categoria the categoria
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


}
