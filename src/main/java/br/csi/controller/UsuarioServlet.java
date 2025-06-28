package br.csi.controller;

import br.csi.model.Usuario;
import br.csi.service.UsuarioService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/usuario")
public class UsuarioServlet extends HttpServlet {

    private static final UsuarioService service = new UsuarioService();

    // Função auxiliar para verificar se o usuário é admin
    private boolean isAdmin(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
            return usuarioLogado != null && usuarioLogado.isAdmin();
        }
        return false;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        String opcao = req.getParameter("opcao");
        String acao = req.getParameter("acao"); // Para o cadastro de admin

        // --- LÓGICA DE CADASTRO DE ADMIN
        if ("cadastrarAdmin".equals(acao)) {
            if (!isAdmin(req)) {
                session.setAttribute("msg_login", "Acesso negado. Você precisa ser administrador.");
                resp.sendRedirect(req.getContextPath() + "/");
                return;
            }
            System.out.println("UsuarioServlet: Tentando CADASTRAR novo ADMINISTRADOR");
            String nomeAdmin = req.getParameter("nome");
            String emailAdmin = req.getParameter("email");
            String senhaAdmin = req.getParameter("senha");

            if (nomeAdmin == null || nomeAdmin.trim().isEmpty() ||
                    emailAdmin == null || emailAdmin.trim().isEmpty() ||
                    senhaAdmin == null || senhaAdmin.trim().isEmpty()) {
                req.setAttribute("msg_erro_cadastro_admin", "Nome, email e senha são obrigatórios.");
                RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/pages/cadastroAdmin.jsp");
                rd.forward(req, resp);
                return;
            }

            Usuario novoAdmin = new Usuario();
            novoAdmin.setNome(nomeAdmin);
            novoAdmin.setEndereco(req.getParameter("endereco"));
            novoAdmin.setCpf(req.getParameter("cpf"));
            novoAdmin.setCelular(req.getParameter("celular"));
            novoAdmin.setEmail(emailAdmin);
            novoAdmin.setSenha(senhaAdmin);
            novoAdmin.setAdmin(true);

            String retornoAdmin = service.inserir(novoAdmin);

            if (retornoAdmin.toLowerCase().contains("sucesso")) {
                session.setAttribute("msg_flash_dashboard", "Novo administrador '" + novoAdmin.getNome() + "' cadastrado com sucesso!");
                resp.sendRedirect(req.getContextPath() + "/usuario?opcao=mostrarDashboardAdmin");
            } else {
                req.setAttribute("msg_erro_cadastro_admin", retornoAdmin);
                RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/pages/cadastroAdmin.jsp");
                rd.forward(req, resp);
            }
            return;
        }
        // --- LÓGICA DE ATUALIZAÇÃO DE USUÁRIO ---
        else if ("atualizar".equals(opcao)) {
            System.out.println("UsuarioServlet: Entrou no ATUALIZAR");
            if (!isAdmin(req)) {
                session.setAttribute("msg_login", "Acesso negado. Você precisa ser administrador para alterar usuários.");
                resp.sendRedirect(req.getContextPath() + "/index.jsp");
                return;
            }

            String idStr = req.getParameter("id");
            if (idStr == null || idStr.isEmpty()) {
                session.setAttribute("msg", "Erro: ID do usuário não fornecido para atualização.");
                resp.sendRedirect(req.getContextPath() + "/usuario?opcao=listarTodos");
                return;
            }

            Usuario usuarioParaAlterar = new Usuario();
            usuarioParaAlterar.setId(Integer.parseInt(idStr));
            usuarioParaAlterar.setNome(req.getParameter("nome"));
            usuarioParaAlterar.setEndereco(req.getParameter("endereco"));
            usuarioParaAlterar.setCpf(req.getParameter("cpf"));
            usuarioParaAlterar.setCelular(req.getParameter("celular"));
            usuarioParaAlterar.setEmail(req.getParameter("email"));
            usuarioParaAlterar.setAdmin(Boolean.parseBoolean(req.getParameter("admin")));

            String novaSenha = req.getParameter("novaSenha");
            String confirmarNovaSenha = req.getParameter("confirmarNovaSenha");
            boolean alterarSenha = false;

            if (novaSenha != null && !novaSenha.isEmpty()) {
                if (!novaSenha.equals(confirmarNovaSenha)) {
                    req.setAttribute("msg", "Erro: As novas senhas não coincidem.");
                    // Para reenviar os dados ao formulário e manter o que o usuário digitou:
                    req.setAttribute("usuario", usuarioParaAlterar); // Envia o objeto com os dados já preenchidos
                    RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/pages/alterarUsuario.jsp");
                    rd.forward(req, resp);
                    return;
                }
                // Se as senhas coincidem e não estão vazias, prepare para alterar
                usuarioParaAlterar.setSenha(novaSenha);
                alterarSenha = true;
                System.out.println("UsuarioServlet: Senha será alterada para o usuário ID: " + idStr);
            } else {
                // aplicar as mudanças e só mudar a senha se uma nova for fornecida.
                System.out.println("UsuarioServlet: Senha NÃO será alterada para o usuário ID: " + idStr);
                usuarioParaAlterar.setSenha(null); // Sinaliza para o service/DAO não alterar a senha
            }

            System.out.println("UsuarioServlet - Alterando usuário: ID=" + usuarioParaAlterar.getId() +
                    ", Nome=" + usuarioParaAlterar.getNome() +
                    ", Email=" + usuarioParaAlterar.getEmail() +
                    ", Admin=" + usuarioParaAlterar.isAdmin() +
                    ", Senha fornecida para alteração: " + (alterarSenha ? "Sim" : "Não"));

            String retorno = service.alterar(usuarioParaAlterar, alterarSenha); // Passar a flag para o service
            session.setAttribute("msg", retorno);
            resp.sendRedirect(req.getContextPath() + "/usuario?opcao=listarTodos"); // Volta para o form de edição
            return;
        }
        // --- LÓGICA DE CADASTRO DE USUÁRIO COMUM (PÚBLICO) ---
        else {

            System.out.println("UsuarioServlet: Tentando CADASTRAR novo usuário COMUM");
            String nome = req.getParameter("nome");
            String endereco = req.getParameter("endereco");
            String cpf = req.getParameter("cpf");
            String celular = req.getParameter("celular");
            String email = req.getParameter("email");
            String senha = req.getParameter("senha");

            if (nome == null || nome.trim().isEmpty() ||
                    email == null || email.trim().isEmpty() ||
                    senha == null || senha.trim().isEmpty()) {
                req.setAttribute("msg_erro_cadastro", "Nome, email e senha são obrigatórios.");
                RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/pages/cadastroUsuario.jsp");
                rd.forward(req, resp);
                return;
            }

            Usuario novoUsuario = new Usuario();
            novoUsuario.setNome(nome);
            novoUsuario.setEndereco(endereco);
            novoUsuario.setCpf(cpf);
            novoUsuario.setCelular(celular);
            novoUsuario.setEmail(email);
            novoUsuario.setSenha(senha);
            novoUsuario.setAdmin(false);

            String retorno = service.inserir(novoUsuario);

            if (retorno.toLowerCase().contains("sucesso")) {
                session.setAttribute("msg_cadastro_sucesso", "Cadastro realizado com sucesso! Faça o login.");
                resp.sendRedirect(req.getContextPath() + "/");
            } else {
                req.setAttribute("msg_erro_cadastro", retorno);
                RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/pages/cadastroUsuario.jsp");
                rd.forward(req, resp);
            }
            return;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        System.out.println("UsuarioServlet: Entrou no doGet");
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();

        String msgFlashDashboard = (String) session.getAttribute("msg_flash_dashboard");
        if (msgFlashDashboard != null) {
            req.setAttribute("msg_para_exibir_no_dashboard", msgFlashDashboard);
            session.removeAttribute("msg_flash_dashboard");
        }
        String flashMsg = (String) session.getAttribute("msg");
        if (flashMsg != null) {
            req.setAttribute("msg", flashMsg);
            session.removeAttribute("msg");
        }

        String opcao = req.getParameter("opcao");
        String info = req.getParameter("info");
        RequestDispatcher rd;

        if ("mostrarFormCadastro".equals(opcao)) {
            System.out.println("UsuarioServlet: Redirecionando para formulário de cadastro PÚBLICO");
            rd = req.getRequestDispatcher("WEB-INF/pages/cadastroUsuario.jsp");
            rd.forward(req, resp);
            return;
        }

        if ("mostrarDashboardAdmin".equals(opcao) || "mostrarFormCadastroAdmin".equals(opcao) ||
                "editar".equals(opcao) || "excluir".equals(opcao) || "listarTodos".equals(opcao) ||
                (opcao == null && isAdmin(req))) {

            if (!isAdmin(req)) {
                session.setAttribute("msg_login", "Acesso negado. Você precisa ser administrador para esta ação.");
                resp.sendRedirect(req.getContextPath() + "/index.jsp");
                return;
            }

            if ("mostrarDashboardAdmin".equals(opcao)) {
                System.out.println("UsuarioServlet: Redirecionando para dashboard_admin.jsp via GET");
                rd = req.getRequestDispatcher("WEB-INF/pages/dashboard_admin.jsp");
                rd.forward(req, resp);
                return;
            }

            if ("mostrarFormCadastroAdmin".equals(opcao)) {
                System.out.println("UsuarioServlet: Redirecionando para formulário de cadastro de ADMIN");
                rd = req.getRequestDispatcher("WEB-INF/pages/cadastroAdmin.jsp");
                rd.forward(req, resp);
                return;
            }

            if ("editar".equals(opcao) && info != null) {
                System.out.println("UsuarioServlet: Preparando formulário de edição para usuário ID: " + info);
                try {
                    int userId = Integer.parseInt(info);
                    Usuario u = service.buscar(userId);
                    if (u != null) {
                        req.setAttribute("usuario", u);
                        rd = req.getRequestDispatcher("WEB-INF/pages/alterarUsuario.jsp");
                        rd.forward(req, resp);
                    } else {
                        session.setAttribute("msg", "Usuário não encontrado para edição.");
                        resp.sendRedirect(req.getContextPath() + "/usuario?opcao=listarTodos");
                    }
                } catch (NumberFormatException e) {
                    session.setAttribute("msg", "ID inválido para edição.");
                    resp.sendRedirect(req.getContextPath() + "/usuario?opcao=listarTodos");
                }
                return;
            }

            if ("excluir".equals(opcao) && info != null) {
                System.out.println("UsuarioServlet: Tentando excluir usuário ID: " + info);
                try {
                    int userId = Integer.parseInt(info);
                    String retorno = service.excluir(userId);
                    session.setAttribute("msg", retorno);
                } catch (NumberFormatException e) {
                    session.setAttribute("msg", "ID inválido para exclusão.");
                }
                resp.sendRedirect(req.getContextPath() + "/usuario?opcao=listarTodos");
                return;
            }

            System.out.println("UsuarioServlet: Listando usuários para ADMIN");
            List<Usuario> usuarios = service.listar();
            req.setAttribute("usuarios", usuarios);
            rd = req.getRequestDispatcher("WEB-INF/pages/usuarios.jsp");
            rd.forward(req, resp);
            return;
        }

        if ("mostrarDashboardUsuario".equals(opcao)) {
            rd = req.getRequestDispatcher("WEB-INF/pages/dashboard_usuario.jsp");
            rd.forward(req, resp);
            return;
        }


    }
}
