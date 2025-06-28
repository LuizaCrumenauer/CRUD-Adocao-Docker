package br.csi.controller;

import br.csi.model.Cachorro;
import br.csi.model.Usuario;
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

@WebServlet("") // URL para usuários comuns verem os pets
public class CachorroServletUsuario extends HttpServlet {

    private static final CachorroService cachorroService = new CachorroService();

    private boolean isUserLoggedIn(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return (session != null && session.getAttribute("usuarioLogado") != null);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false); // Pega a sessão se existir, não cria uma nova

        // Lógica para Flash Message (mensagem que aparece uma vez)
        if (session != null) {
            String mensagemGlobal = (String) session.getAttribute("mensagemGlobal");
            if (mensagemGlobal != null) {
                req.setAttribute("mensagemGlobal", mensagemGlobal); // Coloca no request para o JSP
                session.removeAttribute("mensagemGlobal");      // REMOVE da sessão para não aparecer de novo
            }

            // Opcional: Limpar outras mensagens de sessão que não deveriam persistir na vitrine
            if (session.getAttribute("msg_login") != null) {
                session.removeAttribute("msg_login");
            }
            if (session.getAttribute("msg_cadastro_sucesso") != null) {
                session.removeAttribute("msg_cadastro_sucesso");
            }
        }

        List<Cachorro> cachorrosDisponiveis = cachorroService.listarDisponiveisParaAdocao();
        req.setAttribute("cachorrosDisponiveis", cachorrosDisponiveis);

        RequestDispatcher rd = req.getRequestDispatcher("/index.jsp"); // Assumindo que index.jsp está na raiz do webapp
        rd.forward(req, resp);
    }
}
