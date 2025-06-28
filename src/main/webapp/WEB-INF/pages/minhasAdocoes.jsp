<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Minhas Adoções - Lar Doce Lar Animal</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>
</head>
<body>
<div class="wrapper">
    <header>
        <h1>Lar Doce Lar Animal</h1>
        <p class="logo-subtitle">Acompanhe suas adoções.</p>
    </header>

    <nav>
        <a href="${pageContext.request.contextPath}/">Início</a>
        <%-- Link dinâmico para "Meu Painel" --%>
        <c:choose>
            <c:when test="${sessionScope.usuarioLogado.admin}">
                <%-- Se for admin, o link "Meu Painel" leva para o dashboard de admin.
                     A classe 'active' não seria aqui, pois esta é a dashboard_usuario.jsp.
                     Se o admin estivesse na dashboard_admin.jsp, lá o link para ela seria 'active'. --%>
                <a href="${pageContext.request.contextPath}/usuario?opcao=mostrarDashboardAdmin">Meu Painel</a>
            </c:when>
            <c:otherwise>
                <%-- Se for usuário comum, o link "Meu Painel" leva para o dashboard de usuário (esta página). --%>
                <a href="${pageContext.request.contextPath}/usuario?opcao=mostrarDashboardUsuario" class="active">Meu Painel</a>
            </c:otherwise>
        </c:choose>
        <a href="${pageContext.request.contextPath}/adocao?opcao=ListarMinhas" class="active">Minhas Adoções</a>
        <a href="${pageContext.request.contextPath}/login?logout=true">Sair</a>
    </nav>

    <div class="container">
        <h2 class="page-title">Minhas Adoções</h2>

        <c:if test="${not empty sessionScope.msg_adocao_status}">
            <div class="message-container ${sessionScope.msg_adocao_status.toLowerCase().contains('sucesso') || sessionScope.msg_adocao_status.toLowerCase().contains('atualizadas') ? 'success' : 'error'}">
                <p><c:out value="${sessionScope.msg_adocao_status}"/></p>
            </div>
            <% session.removeAttribute("msg_adocao_status"); %>
        </c:if>

        <%-- Mantendo a lógica original para msg_feedback se ainda for usada em algum fluxo --%>
        <c:if test="${not empty msg_feedback}">
            <div class="message-container ${msg_feedback.toLowerCase().contains('sucesso') ? 'success' : 'error'}">
                <p><c:out value="${msg_feedback}"/></p>
            </div>
        </c:if>


        <c:choose>
            <c:when test="${not empty minhasAdocoes}">
                <div class="table-responsive-wrapper">
                    <table class="data-table">
                        <thead>
                        <tr>
                            <th>Nome do Pet</th>
                            <th>Raça</th>
                            <th>Porte</th>
                            <th>Informações Fornecidas</th>
                            <th>Ações</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="adocao" items="${minhasAdocoes}">
                            <tr>
                                <td >${adocao.cachorro.nome}</td>
                                <td >${adocao.cachorro.raca}</td>
                                <td >${adocao.cachorro.porte}</td>
                                <td><c:out value="${adocao.informacoes}"/></td>
                                <td data-label="Ações" class="table-actions">
                                    <a href="${pageContext.request.contextPath}/adocao?opcao=editarAdocao&id_adocao=${adocao.id}" class="table-action-button edit">Editar</a>
                                        <%-- Adicionar outros botões de ação se necessário --%>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:when>
            <c:otherwise>
                <p class="empty-data-message">Você ainda não realizou nenhuma adoção.</p>
            </c:otherwise>
        </c:choose>

        <div class="page-navigation-links">
            <a href="${pageContext.request.contextPath}/" class="action-button secondary">Ver Pets Disponíveis</a>
            <%-- Link dinâmico para "Meu Painel" --%>
            <c:choose>
                <c:when test="${sessionScope.usuarioLogado.admin}">
                    <%-- Se for admin, o link "Meu Painel" leva para o dashboard de admin.
                         A classe 'active' não seria aqui, pois esta é a dashboard_usuario.jsp.
                         Se o admin estivesse na dashboard_admin.jsp, lá o link para ela seria 'active'. --%>
                    <a href="${pageContext.request.contextPath}/usuario?opcao=mostrarDashboardAdmin" class="action-button primary">Meu Painel</a>
                </c:when>
                <c:otherwise>
                    <%-- Se for usuário comum, o link "Meu Painel" leva para o dashboard de usuário (esta página). --%>
                    <a href="${pageContext.request.contextPath}/usuario?opcao=mostrarDashboardUsuario" class="action-button primary">Meu Painel</a>
                </c:otherwise>
            </c:choose>

        </div>
    </div> </div> <footer>
    <p>&copy; <fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy" /> Lar Doce Lar Animal. Todos os direitos reservados.</p>
    <p>Trabalho de Aula - POOW1</p>
</footer>
</body>
</html>