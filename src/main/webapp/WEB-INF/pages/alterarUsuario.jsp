<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Alterar Usuário - Lar Doce Lar Animal</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>
</head>
<body>
<div class="wrapper">
    <header>
        <h1>Lar Doce Lar Animal</h1>
        <p class="logo-subtitle">Painel Administrativo - Alterar Usuário</p>
    </header>

    <nav>
        <a href="${pageContext.request.contextPath}/usuario?opcao=mostrarDashboardAdmin">Painel Admin</a>
        <a href="${pageContext.request.contextPath}/usuario?opcao=listarTodos" class="active">Gerenciar Usuários</a>
        <a href="${pageContext.request.contextPath}/usuario?opcao=mostrarFormCadastroAdmin">Cadastrar Admin</a>
        <a href="${pageContext.request.contextPath}/cachorro">Gerenciar Pets</a>
        <a href="${pageContext.request.contextPath}/login?logout=true">Sair</a>
    </nav>

    <div class="container">
        <div class="form-container-wrapper">
            <div class="form-container cadastro"> <%-- Usando a classe 'cadastro' para formulários mais longos --%>
                <h2 class="page-title" style="font-size: 1.8em; margin-bottom: 20px;">Alterar Usuário: <c:out value="${usuario.nome}"/></h2>

                <c:if test="${not empty msg}">
                    <div class="message-container ${msg.toLowerCase().contains('sucesso') ? 'success' : 'error'}">
                        <p><c:out value="${msg}"/></p>
                    </div>
                </c:if>

                <form action="${pageContext.request.contextPath}/usuario" method="post">
                    <input type="hidden" name="opcao" value="atualizar">
                    <input type="hidden" name="id" value="${usuario.id}">

                    <div class="form-group">
                        <label for="nome">Nome Completo:</label>
                        <input type="text" id="nome" name="nome" value="<c:out value="${usuario.nome}"/>" required>
                    </div>
                    <div class="form-group">
                        <label for="endereco">Endereço:</label>
                        <input type="text" id="endereco" name="endereco" value="<c:out value="${usuario.endereco}"/>">
                    </div>
                    <div class="form-group">
                        <label for="cpf">CPF:</label>
                        <input type="text" id="cpf" name="cpf" value="<c:out value="${usuario.cpf}"/>" placeholder="000.000.000-00">
                    </div>
                    <div class="form-group">
                        <label for="celular">Celular:</label>
                        <input type="tel" id="celular" name="celular" value="<c:out value="${usuario.celular}"/>" placeholder="(00) 90000-0000">
                    </div>
                    <div class="form-group">
                        <label for="email">E-mail:</label>
                        <input type="email" id="email" name="email" value="<c:out value="${usuario.email}"/>" placeholder="usuario@email.com" required>
                    </div>
                    <div class="form-group">
                        <label for="admin">Tipo de Usuário:</label>
                        <select id="admin" name="admin">
                            <option value="false" ${!usuario.admin ? 'selected' : ''}>Comum</option>
                            <option value="true" ${usuario.admin ? 'selected' : ''}>Administrador</option>
                        </select>
                    </div>

                    <hr class="form-separator">
                    <p class="form-note">Deixe os campos de senha em branco se não desejar alterá-la.</p>
                    <div class="form-group">
                        <label for="novaSenha">Nova Senha:</label>
                        <input type="password" id="novaSenha" name="novaSenha">
                    </div>
                    <div class="form-group">
                        <label for="confirmarNovaSenha">Confirmar Nova Senha:</label>
                        <input type="password" id="confirmarNovaSenha" name="confirmarNovaSenha">
                    </div>
                    <hr class="form-separator">

                    <input type="submit" value="Salvar Alterações">
                    <a href="${pageContext.request.contextPath}/usuario?opcao=listarTodos" class="button back-link-button" style="margin-top: 10px;">Cancelar</a>
                </form>
            </div> </div> </div> </div> <footer>
    <p>&copy; <fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy" /> Lar Doce Lar Animal. Todos os direitos reservados.</p>
    <p>Trabalho de Aula - POOW1</p>
</footer>
</body>
</html>