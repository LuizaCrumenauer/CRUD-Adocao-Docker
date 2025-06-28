package br.csi.dao;

import br.csi.model.Cachorro;
import br.csi.model.Usuario;

import java.sql.*;
import java.util.ArrayList;

public class UsuarioDAO {

    // Metodo para verificar se um email já existe (útil antes de inserir)
    // Este metodo nao esta sendo implementado, mas deixei aqui pois pode ser necessário futuramente
    public boolean emailJaExiste(String email) {
        String sql = "SELECT id FROM usuario WHERE email = ?";
        try (Connection conn = ConectarBancoDados.conectarBancoPostgress();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Retorna true se encontrou algum registro (email existe)
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Erro ao verificar email no DAO: " + e.getMessage());
            e.printStackTrace();
            // para evitar inserções duplicadas se a verificação falhar.
            return true;
            // Se retornar true aqui, a inserção será bloqueada.
        }
    }

    public Boolean inserir(Usuario usuario) {
        // aqui eu verificaria se o email já existe

        // if (emailJaExiste(usuario.getEmail())) {
        //     System.out.println("DAO: Tentativa de inserir usuário com email já existente: " + usuario.getEmail());
        //     return false;
        // }

        String sql = "INSERT INTO usuario (nome, endereco, cpf, celular, email, senha, isAdmin) VALUES (?, ?, ?, ?, ?, ?, ?)";
        System.out.println("DAO: Tentando inserir usuário: " + usuario.getEmail() + ", isAdmin: " + usuario.isAdmin());
        try (Connection conn = ConectarBancoDados.conectarBancoPostgress();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEndereco());
            stmt.setString(3, usuario.getCpf());
            stmt.setString(4, usuario.getCelular());
            stmt.setString(5, usuario.getEmail());
            stmt.setString(6, usuario.getSenha());
            stmt.setBoolean(7, usuario.isAdmin());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("DAO: Usuário inserido com sucesso. Linhas afetadas: " + affectedRows);
                return true; // Sucesso se uma ou mais linhas foram afetadas
            } else {
                System.out.println("DAO: Nenhuma linha foi inserida para o usuário: " + usuario.getEmail());
                return false;
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("DAO: Erro ao inserir usuário: " + e.getMessage());
            e.printStackTrace(); // Imprimir o stack trace completo para debug
            return false; // Retorna false em caso de exceção
        }
    }

    public Boolean alterar( Usuario usuario) {
        try {
            Connection conn = ConectarBancoDados.conectarBancoPostgress ();
            PreparedStatement stmt = conn.prepareStatement
                    ( "UPDATE usuario SET nome = ? ,endereco = ?, cpf = ?, celular = ?, email = ?, senha = ?, isAdmin = ? WHERE id = ? " );

            stmt.setString (1, usuario.getNome());
            stmt.setString (2, usuario.getEndereco());
            stmt.setString (3, usuario.getCpf());
            stmt.setString (4, usuario.getCelular());
            stmt.setString (5, usuario.getEmail());
            stmt.setString (6, usuario.getSenha());
            stmt.setBoolean ( 7, usuario.isAdmin () );
            stmt.setInt ( 8, usuario.getId () );

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println ( "DAO: Erro ao alterar usuário: " + e.getMessage () );
            e.printStackTrace ();
            return false;
        }
    }

    public Boolean excluir ( int id) {

        try {
            Connection conn = ConectarBancoDados.conectarBancoPostgress ();
            PreparedStatement stmt = conn.prepareStatement ( "DELETE FROM usuario WHERE id = ? " );

            stmt.setInt ( 1, id );
            stmt.execute ();

        }catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("Erro ao excluir usuario");
        }

        return true;
    }

    public ArrayList<Usuario> listar() {

        ArrayList<Usuario> usuarios = new ArrayList<>();

        try {
            Connection conn = ConectarBancoDados.conectarBancoPostgress ();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("select * from usuario");
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setEndereco(rs.getString("endereco"));
                u.setCpf(rs.getString("cpf"));
                u.setCelular(rs.getString("celular"));
                u.setEmail(rs.getString("email"));
                u.setSenha(rs.getString("senha"));
                u.setAdmin(rs.getBoolean("isAdmin"));

                usuarios.add(u);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao conectar");
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.out.println("Drive não carregou");
            ex.printStackTrace();
        }

        return usuarios;

    }

    public Usuario buscar ( int id) {

        Usuario usuario = new Usuario ();

        try {
            Connection conn = ConectarBancoDados.conectarBancoPostgress ();
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM usuario WHERE id = ?"
            );

            stmt.setInt ( 1, id );

            ResultSet rs = stmt.executeQuery();
            if ( rs.next() ) {
                usuario.setId (rs.getInt ( "id" ) );
                usuario.setNome ( rs.getString ( "nome" ) );
                usuario.setEndereco ( rs.getString ( "endereco" ) );
                usuario.setCpf ( rs.getString ( "cpf" ) );
                usuario.setCelular ( rs.getString ( "celular" ) );
                usuario.setEmail ( rs.getString ( "email" ) );
                usuario.setSenha ( rs.getString ( "senha" ) );
                usuario.setAdmin ( rs.getBoolean ( "isAdmin" ) );
            }

            stmt.setInt(1, id);

        }catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("Erro ao buscar usuario");
        }
        return usuario;

    }

    public Usuario buscar ( String email ) {
        Usuario usuario = new Usuario ();

        try {
            Connection conn = ConectarBancoDados.conectarBancoPostgress ();
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM usuario WHERE email = ?"
            );

            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                usuario.setId(rs.getInt("id"));
                usuario.setNome ( rs.getString("nome"));
                usuario.setEndereco ( rs.getString("endereco"));
                usuario.setCpf ( rs.getString("cpf"));
                usuario.setCelular ( rs.getString("celular"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setAdmin (rs.getBoolean ( "isAdmin" ) );

            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("Erro ao buscar usuario");
        }

        return usuario;
    }



}
