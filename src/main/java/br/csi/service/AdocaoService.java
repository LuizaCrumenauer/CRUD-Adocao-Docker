package br.csi.service;

import br.csi.dao.AdocaoDAO;
import br.csi.dao.CachorroDAO;
import br.csi.dao.ConectarBancoDados;
import br.csi.model.Adocao;
import br.csi.model.Cachorro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class AdocaoService {

    private AdocaoDAO adocaoDAO;
    private CachorroDAO cachorroDAO;

    public AdocaoService() {
        this.adocaoDAO = new AdocaoDAO();
        this.cachorroDAO = new CachorroDAO();
    }

    public String registrarAdocao(Adocao adocao) {


        Cachorro cachorro = cachorroDAO.buscarCachorroPorId(adocao.getCachorroId ());
        if (cachorro == null) {
            return "Erro: Cachorro não encontrado.";
        }
        if (cachorro.isAdotado()) {
            return "Erro: Este cachorro já foi adotado.";
        }

        adocaoDAO.inserirAdocao(adocao);

        boolean cachorroAtualizado = cachorroDAO.marcarComoAdotado(adocao.getCachorroId ());
        if (cachorroAtualizado) {
                return "Adoção registrada com sucesso!";
        }else {
            return "Erro ao registrar a adoção no banco de dados.";
        }
    }

    public List<Adocao> listarMinhasAdocoes(int idUsuario) {

        return adocaoDAO.listarAdocoesPorUsuario(idUsuario);
    }

    /**
     * Busca uma adoção específica pelo ID para fins de edição.
     * Chama diretamente o buscarAdocaoPorId do DAO (que foi ajustado para incluir dados do cachorro).
     * @param adocaoId O ID da adoção.
     * @return O objeto Adocao com dados do cachorro, ou null se não encontrado.
     */
    public Adocao obterAdocaoParaEdicao(int adocaoId) {
        return adocaoDAO.buscarAdocaoPorId(adocaoId);
    }

    /**
     * Salva as alterações (apenas do campo 'informacoes') de uma adoção.
     * Chama diretamente o atualizarInformacoesAdocao do DAO.
     * @param adocaoId O ID da adoção a ser atualizada.
     * @param novasInformacoes As novas informações.
     * @return Uma mensagem indicando o resultado da operação.
     */
    public String salvarAlteracoesInformacoesAdocao(int adocaoId, String novasInformacoes) {
        // Validação básica no service
        if (novasInformacoes == null || novasInformacoes.trim().isEmpty()) {
            return "Erro: O campo 'Informações' não pode estar vazio.";
        }


        if (adocaoDAO.atualizarInformacoesAdocao(adocaoId, novasInformacoes.trim())) {
            return "Informações da adoção atualizadas com sucesso!";
        } else {
            // Adoção não encontrada ou falha na atualização
            return "Erro ao atualizar as informações da adoção. Verifique se a adoção existe ou tente novamente.";
        }
    }

    public boolean removerVinculoAdocao(int idCachorro) {
        if (adocaoDAO.excluirPorIdCachorro(idCachorro)){
            return true;
        }
        return false;
    }

}
