package br.csi.controller;

import br.csi.model.Usuario;
import br.csi.service.LoginService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        String redirectTo = req.getParameter("redirectTo");
        String cachorroIdParam = req.getParameter("cachorroId");

        System.out.println("LoginServlet (POST): Tentativa de login para email: " + email);
        if (redirectTo != null) {
            System.out.println("LoginServlet (POST): redirectTo=" + redirectTo + ", cachorroId=" + cachorroIdParam);
        }

        Usuario usuarioAutenticado = new LoginService().autenticar(email, senha);

        if (usuarioAutenticado != null) {
            System.out.println("LoginServlet (POST): Usuário autenticado: " + usuarioAutenticado.getEmail() + ", isAdmin: " + usuarioAutenticado.isAdmin());

            HttpSession session = req.getSession();
            session.setAttribute("usuarioLogado", usuarioAutenticado);
            session.setAttribute("tipoUsuario", usuarioAutenticado.isAdmin() ? "admin" : "comum");

            if ("adocao".equals(redirectTo) && cachorroIdParam != null && !cachorroIdParam.isEmpty()) {
                System.out.println("LoginServlet (POST): Redirecionando para o formulário de adoção do cachorro ID: " + cachorroIdParam);
                resp.sendRedirect(req.getContextPath() + "/adocao?id_cachorro=" + cachorroIdParam);
                return;
            }

            // Redirecionamento padrão para dashboards usando sendRedirect
            if (usuarioAutenticado.isAdmin()) {
                System.out.println("LoginServlet (POST): Redirecionando para dashboard_admin via Servlet");
                resp.sendRedirect(req.getContextPath() + "/usuario?opcao=mostrarDashboardAdmin");
            } else {
                System.out.println("LoginServlet (POST): Redirecionando para dashboard_usuario via Servlet");
                resp.sendRedirect(req.getContextPath() + "/usuario?opcao=mostrarDashboardUsuario");
            }


        } else {
            // Falha na autenticação
            System.out.println("LoginServlet (POST): Falha na autenticação para o email: " + email);
            req.setAttribute("msg", "Login ou senha incorretos!");
            req.setAttribute("email", email);

            if (redirectTo != null) {
                req.setAttribute("redirectTo", redirectTo);
            }
            if (cachorroIdParam != null) {
                req.setAttribute("cachorroId", cachorroIdParam);
            }

            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/pages/paginaLogin.jsp");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String logoutParam = req.getParameter("logout");

        if ("true".equals(logoutParam)) {
            HttpSession session = req.getSession(false);
            if (session != null) {
                System.out.println("LoginServlet (GET): Invalidando sessão para logout.");
                session.removeAttribute("usuarioLogado");
                session.removeAttribute("tipoUsuario");
                session.invalidate();
            }
            HttpSession newSession = req.getSession(true);
            newSession.setAttribute("mensagemGlobal", "Logout realizado com sucesso!");
            resp.sendRedirect(req.getContextPath() + "/"); // Redireciona para a (index.jsp)
            return;
        }

        System.out.println("LoginServlet (GET): Exibindo página de login.");

        String redirectTo = req.getParameter("redirectTo");
        String cachorroId = req.getParameter("cachorroId");
        if (redirectTo != null) {
            req.setAttribute("redirectTo", redirectTo);
        }
        if (cachorroId != null) {
            req.setAttribute("cachorroId", cachorroId);
        }

        HttpSession session = req.getSession(false);  //Se já existe uma sessão para o usuário, ela é retornada.
        //Se não existe uma sessão, ele retorna null (e não cria uma nova sessão).
        if (session != null) {
            if (session.getAttribute("msg_login") != null) {
                req.setAttribute("msg_login", session.getAttribute("msg_login"));
                session.removeAttribute("msg_login");
            }
            if (session.getAttribute("msg_cadastro_sucesso") != null) {
                req.setAttribute("msg_cadastro_sucesso", session.getAttribute("msg_cadastro_sucesso"));
                session.removeAttribute("msg_cadastro_sucesso");
            }
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/pages/paginaLogin.jsp");
        dispatcher.forward(req, resp);
    }
}
