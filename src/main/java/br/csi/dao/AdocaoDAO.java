package br.csi.dao;

import br.csi.model.Adocao;
import br.csi.model.Cachorro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdocaoDAO {

    private String sql;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private String status;

    public boolean inserirAdocao( Adocao adocao) {
        String sql = "INSERT INTO adocao (cachorro_id, adotante_id, informacoes) VALUES (?, ?, ?)";
        try (Connection conn = ConectarBancoDados.conectarBancoPostgress();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, adocao.getCachorroId ());
            stmt.setInt(2, adocao.getAdotanteId());
            stmt.setString(3, adocao.getInformacoes ());
//            stmt.setString(4, adocao.getStatus());

            stmt.execute();

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro ao inserir adoção: " + e.getMessage());
        }
        return false;
    }

    public boolean excluirAdocao(int id) {
        String sql = "DELETE FROM adocao WHERE id = ?";
        try (Connection conn = ConectarBancoDados.conectarBancoPostgress();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            stmt.execute();

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro ao excluir adoção: " + e.getMessage());
        }
        return true;
    }

    public boolean excluirPorIdCachorro(int cachorro_id) {

        String sql = "DELETE FROM adocao WHERE cachorro_id = ?";
        try (Connection conn = ConectarBancoDados.conectarBancoPostgress();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cachorro_id);

            stmt.execute();

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro ao excluir adoção: " + e.getMessage());
        }
        return true;

    }

    public boolean atualizarInformacoesAdocao(int adocaoId, String novasInformacoes) {
        String sql = "UPDATE adocao SET informacoes = ? WHERE id = ?";
        System.out.println("DAO: Tentando atualizar informações para adocao ID: " + adocaoId);
        try (Connection conn = ConectarBancoDados.conectarBancoPostgress();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novasInformacoes);
            stmt.setInt(2, adocaoId);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("DAO: Informações da adoção ID " + adocaoId + " atualizadas com sucesso.");
                return true;
            } else {
                System.out.println("DAO: Nenhuma linha atualizada para adoção ID " + adocaoId + ". Adoção não encontrada ou informações já eram as mesmas.");
                return false;
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("DAO: Erro ao atualizar informações da adoção ID " + adocaoId + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    public List<Adocao> listarAdocoes() {
        List<Adocao> lista = new ArrayList<> ();
        String sql = "SELECT * FROM adocao";
        try (Connection conn = ConectarBancoDados.conectarBancoPostgress();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Adocao a = new Adocao();
                a.setId(rs.getInt("id"));
                a.setCachorroId(rs.getInt("cachorro_id"));
                a.setAdotanteId(rs.getInt("adotante_id"));
                a.setInformacoes (rs.getString("informacoes"));
//                a.setStatus(rs.getString("status"));
                lista.add(a);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro ao listar adoções: " + e.getMessage());
        }
        return lista;
    }

    // METODO buscarAdocaoPorId para incluir detalhes do cachorro
    public Adocao buscarAdocaoPorId(int id) {
        String sql = "SELECT a.id as adocao_id, a.cachorro_id, a.adotante_id, a.informacoes, " +
                "c.nome as nome_cachorro, c.raca as raca_cachorro, c.porte as porte_cachorro " +
                "FROM adocao a " +
                "JOIN cachorro c ON a.cachorro_id = c.id " +
                "WHERE a.id = ?";
        Adocao adocao = null;
        try (Connection conn = ConectarBancoDados.conectarBancoPostgress();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    adocao = new Adocao();
                    adocao.setId(rs.getInt("adocao_id"));
                    adocao.setCachorroId(rs.getInt("cachorro_id")); // Mantém o ID do cachorro
                    adocao.setAdotanteId(rs.getInt("adotante_id"));
                    adocao.setInformacoes (rs.getString("informacoes"));

                    // Popula o objeto Cachorro dentro de Adocao
                    Cachorro cachorro = new Cachorro();
                    cachorro.setId(rs.getInt("cachorro_id")); // ID do cachorro
                    cachorro.setNome(rs.getString("nome_cachorro"));
                    cachorro.setRaca(rs.getString("raca_cachorro"));
                    cachorro.setPorte(rs.getString("porte_cachorro"));
                    // Adicione outros atributos do cachorro aqui se desejar exibi-los
                    adocao.setCachorro(cachorro);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro ao buscar adoção por ID com detalhes do cachorro: " + e.getMessage());
            e.printStackTrace(); // Adicionado
        }
        return adocao;
    }

    public List<Adocao> listarAdocoesPorUsuario(int idUsuario) {
        List<Adocao> lista = new ArrayList<>();
        // A query já busca o ID do cachorro e o nome do cachorro.
        String sql = "SELECT a.id as adocao_id, a.cachorro_id, a.adotante_id, a.informacoes, " +
                "c.nome as nome_cachorro, c.raca as raca_cachorro, c.porte as porte_cachorro " +
                "FROM adocao a " +
                "JOIN cachorro c ON a.cachorro_id = c.id " +
                "WHERE a.adotante_id = ?";
        try (Connection conn = ConectarBancoDados.conectarBancoPostgress();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Adocao a = new Adocao();
                    a.setId(rs.getInt("adocao_id"));
                    a.setAdotanteId(rs.getInt("adotante_id"));
                    a.setInformacoes(rs.getString("informacoes"));
//                    a.setCachorroId(rs.getInt("cachorro_id"));

                    Cachorro c = new Cachorro ();
                    c.setId(rs.getInt("cachorro_id"));
                    c.setNome(rs.getString("nome_cachorro"));
                    c.setRaca(rs.getString("raca_cachorro"));
                    c.setPorte(rs.getString("porte_cachorro"));

                    a.setCachorro(c); // Associa o objeto Cachorro ao Adocao
                    lista.add(a);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro ao listar adoções do usuário: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }
}
