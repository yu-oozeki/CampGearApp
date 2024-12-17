<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.sql.*, java.util.*, model.Gear, util.DatabaseConnection" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>キャンプ道具一覧</title>
</head>
<body>
    <h1>キャンプ道具一覧</h1>

    <%
        List<Gear> gearList = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT name, manufacturer, quantity, memo FROM camping_gear";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Gear gear = new Gear();
                    gear.setName(rs.getString("name"));
                    gear.setManufacturer(rs.getString("manufacturer"));
                    gear.setQuantity(rs.getInt("quantity"));
                    gear.setMemo(rs.getString("memo"));
                    gearList.add(gear);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("gearList", gearList);
    %>

    <table border="1">
        <thead>
            <tr>
                <th>アイテム名</th>
                <th>メーカー</th>
                <th>個数</th>
                <th>メモ</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="gear" items="${gearList}">
                <tr>
                    <td>${gear.name}</td>
                    <td>${gear.manufacturer}</td>
                    <td>${gear.quantity}</td>
                    <td>${gear.memo}</td>
                </tr>
            </c:forEach>
            <c:if test="${empty gearList}">
                <tr>
                    <td colspan="4">No gears available.</td>
                </tr>
            </c:if>
        </tbody>
    </table>

    <br>
    <a href="add_gear.jsp">新しい道具を追加</a>
</body>
</html>
