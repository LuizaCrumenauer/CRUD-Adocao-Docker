<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Alterar Dados do Pet - Lar Doce Lar Animal</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>
</head>
<body>
<div class="wrapper">
    <header>
        <h1>Lar Doce Lar Animal</h1>
        <p class="logo-subtitle">Painel Administrativo - Alterar Dados do Pet</p>
    </header>

    <nav>
        <a href="${pageContext.request.contextPath}/usuario?opcao=mostrarDashboardAdmin">Painel Admin</a>
        <a href="${pageContext.request.contextPath}/usuario?opcao=listarTodos">Gerenciar Usuários</a>
        <a href="${pageContext.request.contextPath}/cachorro" class="active">Gerenciar Pets</a>
        <a href="${pageContext.request.contextPath}/login?logout=true">Sair</a>
    </nav>

    <div class="container">
        <div class="form-container-wrapper">
            <div class="form-container cadastro">
                <h2 class="page-title" style="font-size: 1.8em; margin-bottom: 20px;">Alterar Pet: <c:out value="${cachorro.nome}"/></h2>

                <c:if test="${not empty msg}">
                    <div class="message-container ${msg.toLowerCase().contains('sucesso') ? 'success' : 'error'}">
                        <p><c:out value="${msg}"/></p>
                    </div>
                </c:if>

                <form action="${pageContext.request.contextPath}/cachorro" method="post">
                    <input type="hidden" name="opcao" value="atualizar">
                    <input type="hidden" name="id" value="${cachorro.id}">

                    <div class="form-group">
                        <label for="nome">Nome:</label>
                        <input type="text" id="nome" name="nome" value="<c:out value="${cachorro.nome}"/>" required>
                    </div>
                    <div class="form-group">
                        <label for="raca">Raça:</label>
                        <input type="text" id="raca" name="raca" value="<c:out value="${cachorro.raca}"/>" required>
                    </div>
                    <div class="form-group">
                        <label for="sexo">Sexo:</label>
                        <select id="sexo" name="sexo" required>
                            <option value="Macho" ${cachorro.sexo == 'Macho' ? 'selected' : ''}>Macho</option>
                            <option value="Fêmea" ${cachorro.sexo == 'Fêmea' ? 'selected' : ''}>Fêmea</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="porte">Porte:</label>
                        <select id="porte" name="porte" required>
                            <option value="Pequeno" ${cachorro.porte == 'Pequeno' ? 'selected' : ''}>Pequeno</option>
                            <option value="Médio" ${cachorro.porte == 'Médio' ? 'selected' : ''}>Médio</option>
                            <option value="Grande" ${cachorro.porte == 'Grande' ? 'selected' : ''}>Grande</option>
                        </select>
                    </div>
                    <div class="form-group form-group-checkbox">
                        <input type="checkbox" id="adotado" name="adotado" value="true" ${cachorro.adotado ? 'checked' : ''}>
                        <label for="adotado" class="checkbox-label">Adotado?</label>
                    </div>

                    <input type="submit" value="Salvar Alterações" style="margin-top: 10px;">
                    <a href="${pageContext.request.contextPath}/cachorro" class="button back-link-button" style="margin-top: 10px;">Cancelar</a>
                </form>
            </div>
        </div>
    </div> </div> <footer>
    <p>&copy; <fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy" /> Lar Doce Lar Animal. Todos os direitos reservados.</p>
    <p>Trabalho de Aula - POOW1</p>
</footer>
</body>
</html>