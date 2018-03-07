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
                <th>Редактировать</th>
                <th>Удалить</th>
            </tr>
        </thead>
            <tbody>
            <c:forEach items = "${meals}" var = "meal">
            <tr class="${meal.isExceed() ? 'exceeded' : 'normal'}">
                <td>
                    <fmt:parseDate value="${meal.getDateTime()}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                    <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}" />
                </td>
                <form method="post" action="meals">
                    <input type="hidden" name="id" value="${meal.getId()}">
                    <input type="hidden" name="date" value="${meal.getDateTime()}">
                    <td><input type="text" value="${meal.getDescription()}" size=40 name="description"></td>
                    <td><input type="text" value="${meal.getCalories()}" size=40 name="calories"></td>
                    <td><button type="submit">Update</button></td>
                </form>
                <td><a href="meals?action=delete&id=${meal.getId()}">Delete</a></td>
            </tr>
            </c:forEach>
            <tr>
                <td colspan="5"><a href="meals?action=create">New Meal</a></td>
            </tr>
        </tbody>
    </table>
</body>
</html>