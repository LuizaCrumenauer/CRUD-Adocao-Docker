package br.csi.controller;
import br.csi.dao.AdocaoDAO;
import br.csi.model.Cachorro;
import br.csi.service.AdocaoService;
import br.csi.service.CachorroService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("cachorro")
public class CachorroServlet extends HttpServlet {

    private static final CachorroService service = new CachorroService();
    private static final AdocaoService adocaoService = new AdocaoService (); // Adicionado AdocaoService

    @Override
    protected void doPost ( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {

        HttpSession session = req.getSession();
        req.setCharacterEncoding("UTF-8");

        String opcao = req.getParameter("opcao");
        String id = req.getParameter("id");
        String nome = req.getParameter("nome");
        String raca = req.getParameter("raca");
        String sexo = req.getParameter("sexo");
        String porte = req.getParameter("porte");
        boolean adotado = Boolean.parseBoolean(req.getParameter("adotado"));

        Cachorro cachorro = new Cachorro();
        cachorro.setNome(nome);
        cachorro.setRaca(raca);
        cachorro.setSexo(sexo);
        cachorro.setPorte(porte);
        cachorro.setAdotado(adotado);

        String retorno;

        if ("atualizar".equals(opcao) && id != null && !id.isEmpty()) {
            try {
                int cachorroId = Integer.parseInt(id);
                cachorro.setId(cachorroId);
                retorno = service.alterar(cachorro); // Chamada ao serviço de cachorro

                // Se o cachorro foi alterado com sucesso E foi marcado como NÃO ADOTADO
                if (retorno != null && retorno.toLowerCase().contains("sucesso") && !cachorro.isAdotado()) {
                    // Chamar o metodo do AdocaoService para excluir o vínculo de adoção
                    System.out.println("CachorroServlet: Cachorro ID " + cachorroId + " marcado como NÃO ADOTADO. Tentando remover vínculo de adoção via AdocaoService.");
                    boolean exclusaoAdocaoOk = adocaoService.removerVinculoAdocao(cachorroId);

                    if (exclusaoAdocaoOk) {
                        System.out.println("INFO (CachorroServlet): Vínculo de adoção para o cachorro ID " + cachorroId + " removido/verificado com sucesso via Service.");
                        session.setAttribute("msgAdocao", "Vínculo de adoção anterior removido.");
                    } else {
                        System.out.println("WARN (CachorroServlet): Falha ao tentar remover vínculo de adoção para o cachorro ID " + cachorroId + " via Service.");
                        retorno += " (Aviso: problema ao remover adoção anterior.)";
                    }
                }
                session.setAttribute("msg", retorno);
            } catch (NumberFormatException e) {
                session.setAttribute("msg", "Erro: ID do cachorro inválido.");
                e.printStackTrace();
            }
            resp.sendRedirect(req.getContextPath() + "/cachorro"); // Usar getContextPath para URLs relativas corretas
            return;
        }

        // — INSERÇÃO —
        retorno = service.inserir(cachorro);
        session.setAttribute("msg", retorno);
        // redireciona para a listagem
        resp.sendRedirect("cachorro");

    }

    @Override
    protected void doGet ( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {

        HttpSession session = req.getSession();
        // 1) se tiver flash na sessão, joga pro request e limpa
        String flash = (String) session.getAttribute("msg");
        if (flash != null) {
            req.setAttribute("msg", flash);
            session.removeAttribute("msg");
        }

        String opcao = req.getParameter("opcao");
        String info  = req.getParameter("info");

        if ("editar".equals(opcao) && info != null) {
            // — EXIBE FORM DE EDIÇÃO —
            int cachorroId = Integer.parseInt(info);
            Cachorro c  = service.buscar(cachorroId);
            req.setAttribute("cachorro", c);
            RequestDispatcher rd = req.getRequestDispatcher(
                    "WEB-INF/pages/alterarCachorro.jsp");
            rd.forward(req, resp);
            return;
        }

        if ("excluir".equals(opcao) && info != null) {
            String retorno = service.excluir(Integer.parseInt(info));
            session.setAttribute("msg", retorno);
            resp.sendRedirect("cachorro");
            return;
        }

        // — LISTAGEM PADRÃO —
        List<Cachorro> cachorros = service.listar();
        req.setAttribute("cachorros", cachorros);
        RequestDispatcher rd = req.getRequestDispatcher(
                "WEB-INF/pages/cachorros.jsp");
        rd.forward(req, resp);

    }
}
