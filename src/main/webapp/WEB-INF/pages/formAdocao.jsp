<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulário de Adoção - Lar Doce Lar Animal</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>
</head>
<body>
<div class="wrapper">
    <header>
        <h1>Lar Doce Lar Animal</h1>
        <p class="logo-subtitle">Quase lá! Conte-nos um pouco mais.</p>
    </header>

    <nav>
        <a href="${pageContext.request.contextPath}/">Início</a>
        <%-- Links de navegação podem ser ajustados dependendo do fluxo do usuário logado --%>
        <c:if test="${not empty sessionScope.usuarioLogado}">
            <a href="${pageContext.request.contextPath}/usuario?opcao=mostrarDashboardUsuario">Meu Painel</a>
            <a href="${pageContext.request.contextPath}/adocao?opcao=ListarMinhas">Minhas Adoções</a>
            <a href="${pageContext.request.contextPath}/login?logout=true">Sair</a>
        </c:if>
        <c:if test="${empty sessionScope.usuarioLogado}">
            <a href="${pageContext.request.contextPath}/login">Login</a>
            <a href="${pageContext.request.contextPath}/usuario?opcao=mostrarFormCadastro">Criar Conta</a>
        </c:if>
    </nav>

    <div class="container">
        <h2 class="page-title">Formulário de Adoção</h2>

        <div class="form-container" style="max-width: 700px; margin: 0 auto;"> <%-- Centraliza e define largura --%>

            <c:if test="${not empty requestScope.cachorroParaAdotar}">
                <div class="pet-details-info"> <%-- Reutilizando a classe da página de edição --%>
                    <h4 class="pet-details-header">Você está prestes a mudar a vida do(a): <strong><c:out value="${requestScope.cachorroParaAdotar.nome}"/></strong>!</h4>
                    <p><strong>Raça:</strong> <c:out value="${requestScope.cachorroParaAdotar.raca}"/></p>
                    <p><strong>Sexo:</strong> <c:out value="${requestScope.cachorroParaAdotar.sexo}"/></p>
                    <p><strong>Porte:</strong> <c:out value="${requestScope.cachorroParaAdotar.porte}"/></p>
                </div>
            </c:if>

            <c:if test="${empty requestScope.cachorroParaAdotar && not empty requestScope.id_cachorro_para_adocao}">
                <div class="message-container info">
                    <p>Informações do cachorro não carregadas, mas o ID <strong>${requestScope.id_cachorro_para_adocao}</strong> foi recebido. Por favor, tente novamente ou contate o suporte.</p>
                </div>
            </c:if>

            <c:if test="${not empty requestScope.cachorroParaAdotar}">
                <form action="${pageContext.request.contextPath}/adocao" method="post" class="form-containerEdicao">
                    <input type="hidden" name="acao" value="registrarNovaAdocao">
                    <input type="hidden" name="id_cachorro" value="${requestScope.cachorroParaAdotar.id}">

                    <div class="form-group">
                        <label for="informacoes">Registre aqui as informações importantes sobre o (a) <c:out value="${requestScope.cachorroParaAdotar.nome}"/>:</label>
                        <textarea id="informacoes" name="informacoes" rows="6" required minlength="10"><c:out value="${requestScope.informacoes_previas}"/></textarea>
                        <small class="form-text-muted" style="display: block; margin-top: 5px; font-size: 0.85em; color: #707160;">
                            Registre a data da adoção, para não esquecer deste momento especial!
                        </small>
                        <c:if test="${not empty requestScope.msg_erro_form_adocao}">
                            <div class="message-container error" style="margin-top: 10px;">
                                <p><c:out value="${requestScope.msg_erro_form_adocao}"/></p>
                            </div>
                        </c:if>
                    </div>

                    <input type="submit" value="Finalizar Adoção">
                    <a href="${pageContext.request.contextPath}/" class="button back-link-button" style="margin-top: 10px;">Cancelar</a>
                </form>
            </c:if>
            <c:if test="${empty requestScope.cachorroParaAdotar && empty requestScope.id_cachorro_para_adocao}">
                <div class="message-container error">
                    <p>Não foi possível carregar os dados do pet para adoção. Por favor, selecione um pet na página inicial.</p>
                </div>
                <div class="page-navigation-links" style="margin-top: 20px;">
                    <a href="${pageContext.request.contextPath}/" class="action-button secondary">Voltar para Início</a>
                </div>
            </c:if>
        </div> </div> </div> <footer>
    <p>&copy; <fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy" /> Lar Doce Lar Animal. Todos os direitos reservados.</p>
    <p>Trabalho de Aula - POOW1</p>
</footer>
</body>
</html>