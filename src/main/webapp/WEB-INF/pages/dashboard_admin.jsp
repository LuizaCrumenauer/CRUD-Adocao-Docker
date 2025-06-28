<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard Administrador - Lar Doce Lar Animal</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>
</head>
<body>
<div class="wrapper">
    <header>
        <h1>Lar Doce Lar Animal</h1>
        <p class="logo-subtitle">Painel Administrativo</p>
    </header>

    <nav>
        <a href="${pageContext.request.contextPath}/usuario?opcao=mostrarDashboardAdmin" class="active">Painel Admin</a>
        <a href="${pageContext.request.contextPath}/usuario?opcao=listarTodos">Gerenciar Usuários</a>
        <a href="${pageContext.request.contextPath}/cachorro">Gerenciar Pets</a>
        <a href="${pageContext.request.contextPath}/">Ver Site</a>
        <a href="${pageContext.request.contextPath}//adocao?opcao=ListarMinhas">Minhas Adoções</a>
        <a href="${pageContext.request.contextPath}/login?logout=true">Sair</a>
    </nav>

    <div class="container">
        <h2 class="page-title">Painel do Administrador</h2>

        <div class="dashboard-content">
            <p class="dashboard-welcome-text">
                Bem-vindo(a), <strong><c:out value="${sessionScope.usuarioLogado.nome}"/></strong>!
            </p>

            <%-- Mensagens de feedback (se houver alguma para o admin dashboard) --%>
            <c:if test="${not empty requestScope.msg_para_exibir_no_dashboard}">
                <div class="message-container success"> <%-- Ou 'error' ou 'info' dependendo da msg --%>
                    <p><c:out value="${requestScope.msg_para_exibir_no_dashboard}"/></p>
                </div>
            </c:if>
            <c:if test="${not empty sessionScope.msg_flash_dashboard}">
                <div class="message-container success">
                    <p><c:out value="${sessionScope.msg_flash_dashboard}"/></p>
                </div>
                <% session.removeAttribute("msg_flash_dashboard"); %>
            </c:if>


            <div class="c admin-management-sections">
                <div class="management-section-item">
                    <h3>Gerenciamento de Usuários</h3>
                    <ul class="admin-link-list">
                        <li><a href="${pageContext.request.contextPath}/usuario?opcao=mostrarFormCadastroAdmin">Cadastrar Novo Administrador</a></li>
                        <li><a href="${pageContext.request.contextPath}/usuario?opcao=listarTodos">Ver/Gerenciar Todos os Usuários</a></li>
                    </ul>
                </div>

                <div class="management-section-item">
                    <h3>Gerenciamento de Pets</h3>
                    <ul class="admin-link-list">
                        <li><a href="${pageContext.request.contextPath}/cachorro">Cadastrar/Gerenciar Pets</a></li>
                    </ul>
                </div>
            </div>

            <hr class="separator">

            <div class="page-navigation-links">
                <a href="${pageContext.request.contextPath}/" class="action-button secondary">Ir para a Home do Site</a>
            </div>
        </div>
    </div> </div> <footer>
    <p>&copy; <fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy" /> Lar Doce Lar Animal. Todos os direitos reservados.</p>
    <p>Trabalho de Aula - POOW1</p>
</footer>
</body>
</html>