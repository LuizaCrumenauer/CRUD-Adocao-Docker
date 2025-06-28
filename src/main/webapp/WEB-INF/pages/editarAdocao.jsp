<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Informações da Adoção - Lar Doce Lar Animal</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>
</head>
<body>
<div class="wrapper">
    <header>
        <h1>Lar Doce Lar Animal</h1>
        <p class="logo-subtitle">Atualize os detalhes da sua adoção.</p>
    </header>

    <nav>
        <a href="${pageContext.request.contextPath}/">Início</a>
        <a href="${pageContext.request.contextPath}/usuario?opcao=mostrarDashboardUsuario">Meu Painel</a>
        <a href="${pageContext.request.contextPath}/adocao?opcao=ListarMinhas" class="active">Minhas Adoções</a>
        <a href="${pageContext.request.contextPath}/login?logout=true">Sair</a>
    </nav>

    <div class="container">
        <h2 class="page-title">Editar Informações da Adoção</h2>

        <c:choose>
            <c:when test="${not empty adocao && not empty adocao.cachorro}">
                <div class="form-container" style="max-width: 700px; margin: 0 auto;">
                    <div class="pet-details-info mb-4">
                        <h4 class="pet-details-header">Detalhes do Pet (Não editável)</h4>
                        <p><strong>Nome:</strong> <c:out value="${adocao.cachorro.nome}"/></p>
                        <p><strong>Raça:</strong> <c:out value="${adocao.cachorro.raca}"/></p>
                        <p><strong>Porte:</strong> <c:out value="${adocao.cachorro.porte}"/></p>
                    </div>

                    <form method="POST" action="${pageContext.request.contextPath}/adocao">
                        <input type="hidden" name="acao" value="salvarEdicaoAdocao">
                        <input type="hidden" name="id_adocao" value="${adocao.id}">

                        <div class="form-containerEdicao">
                            <label for="informacoes">Suas Informações sobre a Adoção:</label>
                            <textarea id="informacoes" name="informacoes" rows="5" required><c:out value="${not empty adocao.informacoes ? adocao.informacoes : ''}"/></textarea>
                        </div>

                        <c:if test="${not empty msg_erro_form_edicao}">
                            <div class="message-container error mt-2">
                                <p><c:out value="${msg_erro_form_edicao}"/></p>
                            </div>
                        </c:if>

                        <input type="submit" value="Salvar Alterações">
                        <a href="${pageContext.request.contextPath}/adocao?opcao=ListarMinhas" class="button back-link-button" style="margin-top: 10px;">Cancelar</a>
                    </form>
                </div>
            </c:when>
            <c:otherwise>
                <div class="message-container error">
                    <p>Não foi possível carregar os dados da adoção para edição.</p>
                </div>
                <div class="page-navigation-links" style="margin-top: 20px;">
                    <a href="${pageContext.request.contextPath}/adocao?opcao=ListarMinhas" class="action-button secondary">Voltar para Minhas Adoções</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div> </div> <footer>
    <p>&copy; <fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy" /> Lar Doce Lar Animal. Todos os direitos reservados.</p>
    <p>Trabalho de Aula - POOW1</p>
</footer>
</body>
</html>