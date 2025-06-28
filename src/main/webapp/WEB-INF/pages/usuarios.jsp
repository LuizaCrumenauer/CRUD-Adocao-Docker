<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gerenciamento de Usuários - Lar Doce Lar Animal</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>
</head>
<body>
<div class="wrapper">
    <header>
        <h1>Lar Doce Lar Animal</h1>
        <p class="logo-subtitle">Painel Administrativo - Gerenciar Usuários</p>
    </header>

    <nav>
        <a href="${pageContext.request.contextPath}/usuario?opcao=mostrarDashboardAdmin">Painel Admin</a>
        <a href="${pageContext.request.contextPath}/usuario?opcao=listarTodos" class="active">Gerenciar Usuários</a>
        <a href="${pageContext.request.contextPath}/usuario?opcao=mostrarFormCadastroAdmin">Cadastrar Admin</a>
        <a href="${pageContext.request.contextPath}/cachorro">Gerenciar Pets</a>
        <a href="${pageContext.request.contextPath}/login?logout=true">Sair</a>
    </nav>

    <div class="container">
        <h2 class="page-title">Gerenciamento de Usuários</h2>

        <c:if test="${not empty msg}">
            <div class="message-container ${msg.toLowerCase().contains('sucesso') ? 'success' : 'error'}">
                <p><c:out value="${msg}"/></p>
            </div>
        </c:if>

        <div class="table-responsive-wrapper">
            <table class="data-table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Email</th>
                    <th>CPF</th>
                    <th>Celular</th>
                    <th>Endereço</th>
                    <th>Admin?</th>
                    <th>Ações</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="usuarioItem" items="${usuarios}">
                    <tr>
                        <td data-label="ID">${usuarioItem.id}</td>
                        <td data-label="Nome"><c:out value="${usuarioItem.nome}"/></td>
                        <td data-label="Email"><c:out value="${usuarioItem.email}"/></td>
                        <td data-label="CPF"><c:out value="${usuarioItem.cpf}"/></td>
                        <td data-label="Celular"><c:out value="${usuarioItem.celular}"/></td>
                        <td data-label="Endereço"><c:out value="${usuarioItem.endereco}"/></td>
                        <td data-label="Admin?">${usuarioItem.admin ? 'Sim' : 'Não'}</td>
                        <td data-label="Ações" class="table-actions">
                            <a href="${pageContext.request.contextPath}/usuario?opcao=editar&info=${usuarioItem.id}" class="table-action-button edit">Alterar</a>
                            <c:if test="${sessionScope.usuarioLogado.id != usuarioItem.id}">
                                <a href="${pageContext.request.contextPath}/usuario?opcao=excluir&info=${usuarioItem.id}"
                                   class="table-action-button delete"
                                   onclick="return confirm('Tem certeza que deseja excluir o usuário ${usuarioItem.nome}? Esta ação não pode ser desfeita.');">Excluir</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty usuarios}">
                    <tr>
                        <td colspan="8" class="empty-data-message" style="padding: 20px;">Nenhum usuário cadastrado.</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>

        <div class="page-navigation-links">
            <a href="${pageContext.request.contextPath}/usuario?opcao=mostrarDashboardAdmin" class="action-button secondary">Voltar ao Dashboard</a>
            <a href="${pageContext.request.contextPath}/usuario?opcao=mostrarFormCadastroAdmin" class="action-button primary">Cadastrar Novo Administrador</a>
        </div>
    </div> </div> <footer>
    <p>&copy; <fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy" /> Lar Doce Lar Animal. Todos os direitos reservados.</p>
    <p>Trabalho de Aula - POOW1</p>
</footer>
</body>
</html>