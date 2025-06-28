<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Meu Painel - Lar Doce Lar Animal</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>
</head>
<body>
<div class="wrapper">
    <header>
        <h1>Lar Doce Lar Animal</h1>
        <p class="logo-subtitle">Seu espaço pessoal em nossa comunidade.</p>
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

        <a href="${pageContext.request.contextPath}/adocao?opcao=ListarMinhas">Minhas Adoções</a>
        <a href="${pageContext.request.contextPath}/login?logout=true">Sair</a>
    </nav>

    <div class="container">
        <h2 class="page-title">Painel do Usuário</h2>

        <div class="dashboard-content">
            <p class="dashboard-welcome-text">
                Olá, <strong><c:out value="${sessionScope.usuarioLogado.nome}"/></strong>! Seja bem-vindo(a) de volta.
            </p>

            <c:if test="${not empty sessionScope.msg_adocao_status || not empty requestScope.msg_para_exibir_no_dashboard}">
                <div class="message-container ${ (not empty sessionScope.msg_adocao_status && sessionScope.msg_adocao_status.toLowerCase().contains('sucesso')) || (not empty requestScope.msg_para_exibir_no_dashboard && requestScope.msg_para_exibir_no_dashboard.toLowerCase().contains('sucesso')) ? 'success' : 'info'}">
                    <p>
                        <c:if test="${not empty sessionScope.msg_adocao_status}">
                            <c:out value="${sessionScope.msg_adocao_status}"/>
                            <% session.removeAttribute("msg_adocao_status"); %>
                        </c:if>
                        <c:if test="${not empty requestScope.msg_para_exibir_no_dashboard}">
                            <c:out value="${requestScope.msg_para_exibir_no_dashboard}"/>
                        </c:if>
                    </p>
                </div>
            </c:if>

            <c:if test="${not empty sessionScope.msg_login && empty sessionScope.msg_adocao_status && empty requestScope.msg_para_exibir_no_dashboard}">
                <div class="message-container error">
                    <p><c:out value="${sessionScope.msg_login}"/></p>
                    <% session.removeAttribute("msg_login"); %>
                </div>
            </c:if>

            <div class="dashboard-actions">
                <a href="${pageContext.request.contextPath}/adocao?opcao=ListarMinhas" class="action-button primary">
                    Minhas Adoções
                </a>
                <a href="${pageContext.request.contextPath}/" class="action-button secondary">
                    Ver Pets para Adoção
                </a>

            </div>
        </div>
    </div>
</div>

<footer>
    <p>&copy; <fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy" /> Lar Doce Lar Animal. Todos os direitos reservados.</p>
    <p>Trabalho de Aula - POOW1</p>
</footer>
</body>
</html>
```
