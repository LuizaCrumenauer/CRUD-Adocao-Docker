<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gerenciar Pets - Lar Doce Lar Animal</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>
</head>
<body>
<div class="wrapper">
    <header>
        <h1>Lar Doce Lar Animal</h1>
        <p class="logo-subtitle">Painel Administrativo - Gerenciar Pets</p>
    </header>

    <nav>
        <a href="${pageContext.request.contextPath}/usuario?opcao=mostrarDashboardAdmin">Painel Admin</a>
        <a href="${pageContext.request.contextPath}/usuario?opcao=listarTodos">Gerenciar Usuários</a>
        <a href="${pageContext.request.contextPath}/cachorro" class="active">Gerenciar Pets</a>
        <a href="${pageContext.request.contextPath}/login?logout=true">Sair</a>
    </nav>

    <div class="container">
        <h2 class="page-title">Gerenciamento de Pets</h2>



        <c:if test="${not empty msg}">
            <div class="message-container ${msg.toLowerCase().contains('sucesso') ? 'success' : 'error'}">
                <p><c:out value="${msg}"/></p>
            </div>
        </c:if>

        <%-- Formulário de Cadastro de Novo Cachorro --%>
        <div class="form-container cadastro" style="margin-bottom: 40px;">
            <h3 class="form-section-title">Cadastrar Novo Pet</h3>
            <form action="${pageContext.request.contextPath}/cachorro" method="post">
                <%-- Ação de cadastro é implícita no POST para /cachorro sem 'opcao=atualizar' --%>
                <div class="form-grid-columns"> <%-- Nova classe para layout em colunas --%>
                    <div class="form-group">
                        <label for="nome">Nome:</label>
                        <input type="text" id="nome" name="nome" required>
                    </div>
                    <div class="form-group">
                        <label for="raca">Raça:</label>
                        <input type="text" id="raca" name="raca" required>
                    </div>
                    <div class="form-group">
                        <label for="sexo">Sexo:</label>
                        <select id="sexo" name="sexo" required>
                            <option value="">Selecione...</option>
                            <option value="Macho">Macho</option>
                            <option value="Fêmea">Fêmea</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="porte">Porte:</label>
                        <select id="porte" name="porte" required>
                            <option value="">Selecione...</option>
                            <option value="Pequeno">Pequeno</option>
                            <option value="Médio">Médio</option>
                            <option value="Grande">Grande</option>
                        </select>
                    </div>
                </div>
                <input type="submit" value="Cadastrar Pet" style="margin-top: 20px;">
            </form>
        </div>

        <hr class="separator">

        <%-- Tabela de Cachorros Cadastrados --%>
        <h3 class="page-title" style="font-size: 1.8em; margin-top: 30px;">Pets Cadastrados</h3>
        <div class="table-responsive-wrapper">
            <table class="data-table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Raça</th>
                    <th>Sexo</th>
                    <th>Porte</th>
                    <th>Adotado?</th>
                    <th>Ações</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="dog" items="${cachorros}">
                    <tr>
                        <td data-label="ID">${dog.id}</td>
                        <td data-label="Nome"><c:out value="${dog.nome}"/></td>
                        <td data-label="Raça"><c:out value="${dog.raca}"/></td>
                        <td data-label="Sexo"><c:out value="${dog.sexo}"/></td>
                        <td data-label="Porte"><c:out value="${dog.porte}"/></td>
                        <td data-label="Adotado?">${dog.adotado ? 'Sim' : 'Não'}</td>
                        <td data-label="Ações" class="table-actions">
                            <a href="${pageContext.request.contextPath}/cachorro?opcao=editar&info=${dog.id}" class="table-action-button edit">Alterar</a>
                            <a href="${pageContext.request.contextPath}/cachorro?opcao=excluir&info=${dog.id}" class="table-action-button delete"
                               onclick="return confirm('Tem certeza que deseja excluir o pet ${dog.nome}? Esta ação não pode ser desfeita.');">Excluir</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty cachorros}">
                    <tr>
                        <td colspan="7" class="empty-data-message" style="padding: 20px;">Nenhum pet cadastrado.</td>
                    </tr>
                </c:if>
                </tbody>

            </table>

        </div>

        <div class="page-navigation-links" style="margin-bottom: 30px;">
            <a href="${pageContext.request.contextPath}/usuario?opcao=mostrarDashboardAdmin" class="action-button secondary">Voltar ao Dashboard</a>
        </div>
    </div> </div> <footer>
    <p>&copy; <fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy" /> Lar Doce Lar Animal. Todos os direitos reservados.</p>
    <p>Trabalho de Aula - POOW1</p>
</footer>
</body>
</html>