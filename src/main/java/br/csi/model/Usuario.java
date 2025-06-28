package br.csi.model;

public class Usuario {

    private int id;
    private String nome;
    private String endereco;
    private String cpf;
    private String celular;
    private String email;
    private String senha;
    private boolean isAdmin;

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

    public String getEndereco () {
        return endereco;
    }

    public void setEndereco ( String endereco ) {
        this.endereco = endereco;
    }

    public String getCpf () {
        return cpf;
    }

    public void setCpf ( String cpf ) {
        this.cpf = cpf;
    }

    public String getCelular () {
        return celular;
    }

    public void setCelular ( String celular ) {
        this.celular = celular;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail ( String email ) {
        this.email = email;
    }

    public String getSenha () {
        return senha;
    }

    public void setSenha ( String senha ) {
        this.senha = senha;
    }

    public boolean isAdmin () {
        return isAdmin;
    }

    public void setAdmin ( boolean admin ) {
        isAdmin = admin;
    }
}
