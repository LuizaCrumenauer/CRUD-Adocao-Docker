<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<%@ page isELIgnored="false" %>--%>

<%--<html>--%>
<%--<head>--%>
<%--    <title>Cachorros Disponíveis para Adoção</title>--%>
<%--    <style>--%>
<%--        body { font-family: 'Verdana', sans-serif; margin: 0; padding: 0; background-color: #fdfefe; color: #424242; }--%>
<%--        .header-main { background: linear-gradient(to right, #5dade2, #2e86c1); color:white; padding: 20px 35px; text-align: center; box-shadow: 0 3px 6px rgba(0,0,0,0.15); }--%>
<%--        .header-main h1 { margin:0; font-size:2.2em; letter-spacing: 1px; }--%>
<%--        .container-main { max-width: 1100px; margin: 30px auto; padding: 10px 20px; }--%>
<%--        .controls-bar { display: flex; justify-content: space-between; align-items: center; margin-bottom: 25px; padding: 10px; background-color: #fff; border-radius:6px; box-shadow: 0 1px 3px rgba(0,0,0,0.07); }--%>
<%--        .dog-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); gap: 30px; }--%>
<%--        .dog-card {--%>
<%--            background-color: #ffffff;--%>
<%--            border: 1px solid #e0e0e0;--%>
<%--            border-radius: 10px;--%>
<%--            padding: 25px;--%>
<%--            box-shadow: 0 4px 8px rgba(0,0,0,0.08);--%>
<%--            text-align: center;--%>
<%--            transition: transform 0.2s ease-in-out, box-shadow 0.2s ease-in-out;--%>
<%--        }--%>
<%--        .dog-card:hover { transform: translateY(-5px); box-shadow: 0 6px 12px rgba(0,0,0,0.12); }--%>
<%--        .dog-card h3 { margin-top: 0; margin-bottom: 12px; color: #2980b9; font-size: 1.5em; }--%>
<%--        .dog-card p { margin: 6px 0; font-size: 1em; color: #566573; }--%>
<%--        .dog-card p strong { color: #34495e; }--%>
<%--        .adopt-button {--%>
<%--            display: inline-block;--%>
<%--            margin-top: 18px;--%>
<%--            padding: 12px 25px;--%>
<%--            background: linear-gradient(to right, #2ecc71, #28b463);--%>
<%--            color: white;--%>
<%--            text-decoration: none;--%>
<%--            border-radius: 25px; /* Botão mais arredondado */--%>
<%--            font-weight: bold;--%>
<%--            font-size: 1.05em;--%>
<%--            transition: background-color 0.25s, transform 0.2s;--%>
<%--            border: none;--%>
<%--            box-shadow: 0 2px 4px rgba(46, 204, 113, 0.4);--%>
<%--        }--%>
<%--        .adopt-button:hover { background: linear-gradient(to right, #28b463, #229954); transform: scale(1.03); }--%>
<%--        .feedback-message { padding: 12px; margin-bottom: 20px; border-radius: 5px; text-align: center; font-size: 1.05em; }--%>
<%--        .feedback-message.success { color: #155724; background-color: #d4edda; border: 1px solid #c3e6cb; }--%>
<%--        .feedback-message.error { color: #721c24; background-color: #f8d7da; border: 1px solid #f5c6cb; }--%>
<%--        .back-link-btn {--%>
<%--            padding: 10px 20px; background-color:#5499c7; color:white; text-decoration:none; border-radius:5px;--%>
<%--            font-weight:500; transition: background-color 0.2s;--%>
<%--        }--%>
<%--        .back-link-btn:hover { background-color:#41729f; }--%>
<%--        .no-pets-message { grid-column: 1 / -1; text-align:center; font-size:1.3em; color: #7f8c8d; padding: 40px 20px; background-color:#f0f3f4; border-radius:8px;}--%>
<%--    </style>--%>
<%--</head>--%>
<%--<body>--%>
<%--<div class="header-main"><h1>Nossos Anjinhos de Quatro Patas</h1></div>--%>
<%--<div class="container-main">--%>

<%--    <div class="controls-bar">--%>
<%--        <a href="${pageContext.request.contextPath}/usuario?opcao=mostrarDashboardUsuario" class="back-link-btn">Voltar para Meu Painel</a>--%>

<%--    </div>--%>

<%--    <h2>Pronto para encontrar seu novo melhor amigo?</h2>--%>


<%--    <c:if test="${not empty msg_feedback}">--%>
<%--        <p class="feedback-message ${msg_feedback.toLowerCase().contains('sucesso') ? 'success' : 'error'}">--%>
<%--            <c:out value="${msg_feedback}"/>--%>
<%--        </p>--%>
<%--    </c:if>--%>
<%--    &lt;%&ndash; Mensagem específica de status de adoção, se houver &ndash;%&gt;--%>
<%--    <c:if test="${not empty sessionScope.msg_adocao_status}">--%>
<%--        <p class="feedback-message ${sessionScope.msg_adocao_status.toLowerCase().contains('sucesso') ? 'success' : 'error'}">--%>
<%--            <c:out value="${sessionScope.msg_adocao_status}"/>--%>
<%--        </p>--%>
<%--        <% session.removeAttribute("msg_adocao_status"); %>--%>
<%--    </c:if>--%>

<%--    <div class="dog-grid">--%>
<%--        <c:forEach var="dog" items="${cachorros}">--%>
<%--            <div class="dog-card">--%>
<%--                <h3><c:out value="${dog.nome}"/></h3>--%>
<%--                <p><strong>Raça:</strong> <c:out value="${dog.raca}"/></p>--%>
<%--                <p><strong>Sexo:</strong> <c:out value="${dog.sexo}"/></p>--%>
<%--                <p><strong>Porte:</strong> <c:out value="${dog.porte}"/></p>--%>
<%--                <a href="${pageContext.request.contextPath}/adocao?id_cachorro=${dog.id}" class="adopt-button">Quero Adotar!</a>--%>
<%--            </div>--%>
<%--        </c:forEach>--%>

<%--        <c:if test="${empty cachorros}">--%>
<%--            <p class="no-pets-message">Que pena! Parece que todos os nossos anjinhos encontraram um lar. Volte em breve!</p>--%>
<%--        </c:if>--%>
<%--    </div>--%>
<%--</div>--%>
<%--</body>--%>
<%--</html>--%>
