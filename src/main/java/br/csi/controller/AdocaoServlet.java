package br.csi.controller;

import br.csi.model.Adocao;
import br.csi.model.Cachorro;
import br.csi.model.Usuario;
import br.csi.service.AdocaoService;
import br.csi.service.CachorroService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/adocao")
public class AdocaoServlet extends HttpServlet {

    private AdocaoService adocaoService = new AdocaoService();
    private CachorroService cachorroService = new CachorroService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        RequestDispatcher rd;

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            session.setAttribute("msg_login", "Sessão expirada ou usuário não logado. Faça login novamente.");
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        String msgFlash = (String) session.getAttribute("msg_adocao_status");
        if (msgFlash != null) {
            req.setAttribute("msg_para_exibir", msgFlash);
            session.removeAttribute("msg_adocao_status");
        }

        String opcao = req.getParameter("opcao");
        String idAdocaoParam = req.getParameter("id_adocao");
        String idCachorroParam = req.getParameter("id_cachorro");

        if ("editarAdocao".equals(opcao) && idAdocaoParam != null) {

            try {
                int idAdocao = Integer.parseInt(idAdocaoParam);

                Adocao adocaoParaEditar = adocaoService.obterAdocaoParaEdicao(idAdocao);

                if (adocaoParaEditar != null && adocaoParaEditar.getCachorro() != null && adocaoParaEditar.getAdotanteId() == usuarioLogado.getId()) {
                    System.out.println("[AdocaoServlet] Adoção válida e pertence ao usuário. Setando atributo 'adocao' e encaminhando para JSP.");
                    req.setAttribute("adocao", adocaoParaEditar);
                    rd = req.getRequestDispatcher("/WEB-INF/pages/editarAdocao.jsp");
                    rd.forward(req, resp);
                } else {
                    System.out.println("[AdocaoServlet] Adoção NÃO é válida ou NÃO pertence ao usuário, ou cachorro é nulo. Redirecionando.");
                    if (adocaoParaEditar == null) {
                        System.out.println("[AdocaoServlet] Motivo: adocaoParaEditar é null.");
                    } else if (adocaoParaEditar.getCachorro() == null) {
                        System.out.println("[AdocaoServlet] Motivo: adocaoParaEditar.getCachorro() é null. ID Adoção: " + adocaoParaEditar.getId());
                    } else {
                        System.out.println("[AdocaoServlet] Motivo: Verificação de propriedade falhou. ID Adotante: " + adocaoParaEditar.getAdotanteId() + " != ID Usuário Logado: " + usuarioLogado.getId());
                    }
                    session.setAttribute("msg_adocao_status", "Adoção não encontrada, dados incompletos ou acesso negado.");
                    resp.sendRedirect(req.getContextPath() + "/adocao?opcao=ListarMinhas");
                }
            } catch (NumberFormatException e) {
                System.out.println("[AdocaoServlet] NumberFormatException ao converter ID da adoção: " + e.getMessage());
                session.setAttribute("msg_adocao_status", "Erro ao carregar adoção para edição (ID inválido).");
                resp.sendRedirect(req.getContextPath() + "/adocao?opcao=ListarMinhas");
            }
            return;
        } else if (idCachorroParam != null && !"ListarMinhas".equals(opcao) && !"editarAdocao".equals(opcao) ) {
            // Formulário para NOVA adoção
            System.out.println("[AdocaoServlet] doGet - Opção: Formulário para nova adoção");
            try {
                int idCachorro = Integer.parseInt(idCachorroParam);
                Cachorro cachorro = cachorroService.buscar(idCachorro);
                if (cachorro != null && cachorro.getId() != 0) { // Adicionado && cachorro.getId() != 0 para checar se o cachorro foi realmente encontrado
                    System.out.println("[AdocaoServlet] Cachorro para nova adoção encontrado: " + cachorro.getNome());
                    req.setAttribute("cachorroParaAdotar", cachorro);
                    rd = req.getRequestDispatcher("/WEB-INF/pages/formAdocao.jsp");
                    rd.forward(req, resp);
                } else {
                    System.out.println("[AdocaoServlet] Cachorro para nova adoção NÃO encontrado ou ID é 0. ID Cachorro: " + idCachorroParam);
                    session.setAttribute("msg_adocao_status", "Cachorro não encontrado.");
                    resp.sendRedirect(req.getContextPath() + "/");
                }
            } catch (NumberFormatException e) {
                session.setAttribute("msg_adocao_status", "Erro ao carregar dados do cachorro (ID inválido).");
                resp.sendRedirect(req.getContextPath() + "/meus-pets");
            }
            return;
        } else {
            // Padrão: Listar Minhas Adoções
            System.out.println("[AdocaoServlet] doGet - Opção: ListarMinhas (ou padrão)");
            List<Adocao> minhasAdocoes = adocaoService.listarMinhasAdocoes(usuarioLogado.getId());
            req.setAttribute("minhasAdocoes", minhasAdocoes);
            rd = req.getRequestDispatcher("/WEB-INF/pages/minhasAdocoes.jsp");
            rd.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            session.setAttribute("msg_login", "Sessão expirada. Faça login novamente para continuar.");
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        String acao = req.getParameter("acao");
        String resultadoMsg = "Ocorreu um erro inesperado."; // Mensagem padrão
        String redirectPage = req.getContextPath() + "/adocao?opcao=ListarMinhas"; // Página de redirecionamento padrão

        try {
            if ("registrarNovaAdocao".equals(acao)) {
                System.out.println("[AdocaoServlet] doPost - Ação: registrarNovaAdocao");
                int idCachorro = Integer.parseInt(req.getParameter("id_cachorro"));
                String informacoes = req.getParameter("informacoes");

                if (informacoes == null || informacoes.trim().isEmpty()) {
                    resultadoMsg = "O campo 'Informações' é obrigatório para registrar a adoção.";

                } else {
                    Adocao novaAdocao = new Adocao();
                    novaAdocao.setCachorroId(idCachorro);
                    novaAdocao.setAdotanteId(usuarioLogado.getId());
                    novaAdocao.setInformacoes(informacoes.trim());
                    resultadoMsg = adocaoService.registrarAdocao(novaAdocao);
                }
            } else if ("salvarEdicaoAdocao".equals(acao)) {
                System.out.println("[AdocaoServlet] doPost - Ação: salvarEdicaoAdocao");
                int idAdocao = Integer.parseInt(req.getParameter("id_adocao"));
                String informacoes = req.getParameter("informacoes");

                if (informacoes == null || informacoes.trim().isEmpty()) {
                    resultadoMsg = "O campo 'Informações' é obrigatório para salvar as alterações.";

                } else {
                    Adocao adocaoExistente = adocaoService.obterAdocaoParaEdicao(idAdocao); // Re-busca para verificar propriedade
                    if (adocaoExistente != null && adocaoExistente.getAdotanteId() == usuarioLogado.getId()){
                        System.out.println("[AdocaoServlet] doPost - Salvando edição para adoção ID: " + idAdocao);
                        resultadoMsg = adocaoService.salvarAlteracoesInformacoesAdocao(idAdocao, informacoes.trim());
                    } else {
                        System.out.println("[AdocaoServlet] doPost - Tentativa de salvar edição negada. Adoção ID: " + idAdocao);
                        resultadoMsg = "Não foi possível salvar. Adoção não encontrada ou acesso negado.";
                    }
                }
            } else {
                System.out.println("[AdocaoServlet] doPost - Ação desconhecida: " + acao);
                resultadoMsg = "Ação desconhecida ou não permitida.";
            }
        } catch (NumberFormatException e) {
            System.out.println("[AdocaoServlet] doPost - NumberFormatException: " + e.getMessage());
            resultadoMsg = "Erro nos dados fornecidos (ID inválido).";
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("[AdocaoServlet] doPost - Exception: " + e.getMessage());
            resultadoMsg = "Ocorreu um erro interno ao processar sua solicitação.";
            e.printStackTrace();
        }

        session.setAttribute("msg_adocao_status", resultadoMsg);
        resp.sendRedirect(redirectPage);
    }
}
