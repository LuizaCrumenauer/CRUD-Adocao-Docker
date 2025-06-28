package br.csi.service;

import br.csi.dao.UsuarioDAO;
import br.csi.model.Usuario;
// Importe uma biblioteca de hashing, por exemplo, BCrypt
// import org.mindrot.jbcrypt.BCrypt; // Exemplo com jBCrypt

import java.util.ArrayList;

public class UsuarioService {

    private static UsuarioDAO dao = new UsuarioDAO();

    public String excluir(int id) {
        if (dao.excluir(id)) {
            return "Sucesso ao excluir usuário";
        } else {
            return "Erro ao excluir usuário";
        }
    }

    public ArrayList<Usuario> listar() {
        return dao.listar();
    }

    public Usuario buscar(int usuarioId) {
        return dao.buscar(usuarioId);
    }

    public String inserir(Usuario usuario) {

        System.out.println("Service - Inserindo Usuário: " + usuario.getEmail() + " Senha (ANTES DO DAO): " + usuario.getSenha()); // Log para debug
        if (dao.inserir(usuario)) {
            return "Sucesso ao inserir usuário";
        } else {
            return "Erro ao inserir usuário";
        }
    }

    public String alterar(Usuario usuario, boolean alterarSenha) {
        // Se a senha não for para ser alterada (veio null do servlet),
        // precisamos buscar a senha atual do banco para não sobrescrevê-la com null.
        if (!alterarSenha) {
            Usuario usuarioExistente = dao.buscar(usuario.getId()); // Assume que buscar(id) não retorna a senha ou retorna a hasheada
            if (usuarioExistente != null) {

                Usuario usuarioDB = dao.buscar(usuario.getId()); // Este buscar DEVE retornar a senha
                if(usuarioDB != null) {
                    usuario.setSenha(usuarioDB.getSenha()); // Mantém a senha antiga se não for para alterar
                } else {
                    return "Erro: Usuário não encontrado para manter senha.";
                }
            } else {
                return "Erro: Usuário não encontrado para manter senha.";
            }
        } else {

            System.out.println("Service - Alterando Usuário: " + usuario.getEmail() + " Nova Senha (ANTES DO DAO): " + usuario.getSenha()); // Log para debug
        }


        if (dao.alterar(usuario)) {
            return "Sucesso ao alterar usuário";
        } else {
            return "Erro ao alterar usuário";
        }
    }
}
