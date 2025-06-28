package br.csi.dao;

import br.csi.model.Cachorro;

import java.sql.*;
import java.util.ArrayList;

public class CachorroDAO {

    private String status;

    public Boolean inserirCachorro ( Cachorro cachorro) {

        try {
            Connection conn = ConectarBancoDados.conectarBancoPostgress();
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO cachorro (nome, raca, sexo, porte, adotado) VALUES (?, ?, ?, ?, ?)"
            );
            stmt.setString(1, cachorro.getNome());
            stmt.setString(2, cachorro.getRaca());
            stmt.setString(3, cachorro.getSexo());
            stmt.setString(4, cachorro.getPorte());
            stmt.setBoolean ( 5,cachorro.isAdotado ());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        }catch (SQLException | ClassNotFoundException e) {
            System.err.println("Erro ao inserir cachorro: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

    public boolean excluirCachorro (int id) {

        try {
            Connection conn = ConectarBancoDados.conectarBancoPostgress();
            PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM cachorro WHERE id = ?"
            );

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;


        }catch (SQLException | ClassNotFoundException e) {
            System.err.println("Erro ao excluir cachorro: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

    public boolean alterarCachorro(Cachorro cachorro) {
        String sql = "UPDATE cachorro "
                + "SET nome = ?, raca = ?, sexo = ?, porte = ?, adotado = ? "
                + "WHERE id = ?";

        try (
                Connection conn = ConectarBancoDados.conectarBancoPostgress();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, cachorro.getNome());
            stmt.setString(2, cachorro.getRaca());
            stmt.setString(3, cachorro.getSexo());
            stmt.setString(4, cachorro.getPorte());
            stmt.setBoolean(5, cachorro.isAdotado());
            stmt.setInt(6, cachorro.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC não encontrado: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            System.err.println("Erro SQL ao atualizar cachorro: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean marcarComoAdotado(int idCachorro) {
        String sql = "UPDATE cachorro SET adotado = TRUE WHERE id = ?";
        System.out.println("DAO: Tentando marcar cachorro ID " + idCachorro + " como adotado.");
        try (Connection conn = ConectarBancoDados.conectarBancoPostgress();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCachorro);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("DAO: Cachorro ID " + idCachorro + " marcado como adotado com sucesso. Linhas afetadas: " + affectedRows);
                return true;
            } else {
                System.out.println("DAO: Nenhuma linha atualizada para marcar cachorro ID " + idCachorro + " como adotado (cachorro não encontrado ou já estava com status correto?).");
                return false;
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("DAO: Erro ao marcar cachorro ID " + idCachorro + " como adotado: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    public ArrayList<Cachorro> listarCachorro() {
        ArrayList<Cachorro> cachorros = new ArrayList<>();
        String sql = "SELECT * FROM cachorro";

        try (
                Connection conn = ConectarBancoDados.conectarBancoPostgress();
                Statement stmt    = conn.createStatement();
                ResultSet rs      = stmt.executeQuery(sql)
        ) {

            while (rs.next()) {
                Cachorro c = new Cachorro();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setRaca(rs.getString("raca"));
                c.setSexo(rs.getString("sexo"));
                c.setPorte(rs.getString("porte"));
                c.setAdotado(rs.getBoolean("adotado"));

                cachorros.add(c);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro ao listar todos os cachorros: " + e.getMessage());
            e.printStackTrace();
        }

        return cachorros;
    }

    // Lista apenas cachorros disponíveis para adoção (adotado = false)
    public ArrayList<Cachorro> listarCachorrosDisponiveis() {
        ArrayList<Cachorro> cachorros = new ArrayList<>();
        String sql = "SELECT * FROM cachorro WHERE adotado = false ORDER BY nome";
        try (Connection conn = ConectarBancoDados.conectarBancoPostgress();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Cachorro c = new Cachorro();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setRaca(rs.getString("raca"));
                c.setSexo(rs.getString("sexo"));
                c.setPorte(rs.getString("porte"));
                c.setAdotado(rs.getBoolean("adotado"));
                cachorros.add(c);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro ao listar cachorros disponíveis: " + e.getMessage());
            e.printStackTrace();
        }
        return cachorros;
    }

    public Cachorro buscarCachorroPorId(int id) {
        String sql = "SELECT * FROM cachorro WHERE id = ?";
        Cachorro cachorro = new Cachorro ();

        try (
                Connection conn = ConectarBancoDados.conectarBancoPostgress();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cachorro = new Cachorro ();
                    cachorro.setId(rs.getInt("id"));
                    cachorro.setNome(rs.getString("nome"));
                    cachorro.setRaca(rs.getString("raca"));
                    cachorro.setSexo(rs.getString("sexo"));
                    cachorro.setPorte(rs.getString("porte"));
                    cachorro.setAdotado(rs.getBoolean("adotado"));
                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro ao buscar cachorro por ID: " + e.getMessage());
            e.printStackTrace();
        }

        return cachorro;
    }

}
