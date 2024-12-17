package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import util.DatabaseConnection;

public class GearServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String manufacturer = request.getParameter("manufacturer");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String memo = request.getParameter("memo");

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO camping_gear (name, manufacturer, quantity, memo) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, manufacturer);
                ps.setInt(3, quantity);
                ps.setString(4, memo);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // 新しい道具を追加した後、index.jspにリダイレクト
        response.sendRedirect("index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT name, manufacturer, quantity, memo FROM camping_gear";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                List<Map<String, Object>> gearList = new ArrayList<>();
                while (rs.next()) {
                    Map<String, Object> gear = new HashMap<>();
                    gear.put("name", rs.getString("name"));
                    gear.put("manufacturer", rs.getString("manufacturer"));
                    gear.put("quantity", rs.getInt("quantity"));
                    gear.put("memo", rs.getString("memo"));
                    gearList.add(gear);
                }
                request.setAttribute("gearList", gearList);
                RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
