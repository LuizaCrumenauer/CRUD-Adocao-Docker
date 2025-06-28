package br.csi.model;

public class Cachorro {

    private int id;
    private String nome;
    private String raca;
    private String sexo;
    private String porte;
    private boolean adotado;

    public boolean isAdotado () {
        return adotado;
    }

    public void setAdotado ( boolean adotado ) {
        this.adotado = adotado;
    }

    public int getId () {
        return id;
    }

    public void setId ( int id ) {
        this.id = id;
    }

    public String getNome () {
        return nome;
    }

    public void setNome ( String nome ) {
        this.nome = nome;
    }

    public String getRaca () {
        return raca;
    }

    public void setRaca ( String raca ) {
        this.raca = raca;
    }

    public String getSexo () {
        return sexo;
    }

    public void setSexo ( String sexo ) {
        this.sexo = sexo;
    }

    public String getPorte () {
        return porte;
    }

    public void setPorte ( String porte ) {
        this.porte = porte;
    }


}
