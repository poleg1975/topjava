<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
    <style>
        .normal {
            color: green;
        }
        .exceeded {
            color: red;
        }
    </style>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <h2>Meals</h2>
    <table border="1">
        <thead>
            <tr>
                <th>Дата</th>
                <th>Описание</th>
                <th>Калории</th>
            </tr>
        </thead>
            <tbody>
            <c:forEach items = "${meals}" var = "meal">
            <tr class="${meal.isExceed() ? 'exceeded' : 'normal'}">
                <td>
                    <fmt:parseDate value="${meal.getDateTime()}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                    <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}" />
                </td>
                <td>${meal.getDescription()}</td>
                <td>${meal.getCalories()}</td>
            </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>