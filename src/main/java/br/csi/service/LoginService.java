package br.csi.service;

import br.csi.dao.UsuarioDAO;
import br.csi.model.Usuario;

public class LoginService {

    public Usuario autenticar(String email, String senha) {

        Usuario usuario = new UsuarioDAO ().buscar ( email );

        if (usuario != null && usuario.getSenha() != null) {
            if (usuario.getSenha().equals(senha)) {
                // Autenticação bem-sucedida
                usuario.setSenha(null);
                return usuario;
            }
        }
        // Falha na autenticação ou usuário não encontrado
        return null;
    }
}
