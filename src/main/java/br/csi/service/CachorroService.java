package br.csi.service;

import br.csi.dao.CachorroDAO;
import br.csi.model.Cachorro;
import br.csi.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class CachorroService {

    private static CachorroDAO dao = new CachorroDAO();

    public String inserir( Cachorro cachorro ) {
        cachorro.setAdotado(false);
        if (dao.inserirCachorro ( cachorro )) {
            return "Sucesso ao inserir cachorro";
        }else{
            return "Erro ao inserir cachorro";
        }
    }

    public String alterar(Cachorro cachorro) {
        if (dao.alterarCachorro ( cachorro )) {
            return "Sucesso ao alterar cachorro";
        }
        else{
            return "Erro ao alterar cachorro";
        }
    }

    public String excluir(int id){

        if(dao.excluirCachorro (id)){
            return "Sucesso ao excluir cachorro";
        }else{
            return "Erro ao excluir cachorro";
        }

    }

    public ArrayList<Cachorro> listar(){
        return dao.listarCachorro ();
    }

    // Para listagem de usuário comum
    public List<Cachorro> listarDisponiveisParaAdocao() {
        return dao.listarCachorrosDisponiveis();
    }

    public Cachorro buscar (int id) {
        return dao.buscarCachorroPorId ( id );
    }

}
