package br.csi.model;

public class Adocao {

    private int id;
    private int cachorro_id;
    private int adotante_id;
    private String informacoes;
    private Cachorro cachorro;

    public Cachorro getCachorro () {
        return cachorro;
    }

    public void setCachorro ( Cachorro cachorro ) {
        this.cachorro = cachorro;
    }

    public String getInformacoes () {
        return informacoes;
    }

    public void setInformacoes ( String informacoes ) {
        this.informacoes = informacoes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCachorroId () {
        return cachorro_id;
    }

    public void setCachorroId(int cachorroId) {
        this.cachorro_id = cachorroId;
    }

    public int getAdotanteId() {
        return adotante_id;
    }

    public void setAdotanteId(int adotanteId) {
        this.adotante_id = adotanteId;
    }


}
