package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Gear;
import util.DatabaseConnection;

public class GearListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Gear> gearList = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            // camping_gearテーブルから必要なフィールドを全て取得
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
            e.printStackTrace(); // エラーメッセージをコンソールに出力
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response); // エラー画面に転送
            return; // エラー発生時はここで処理を終了
        }

        // gearListをリクエスト属性として設定
        request.setAttribute("gearList", gearList);

        // gearListのサイズをログに出力
        System.out.println("gearList size: " + gearList.size());

        // gearListが空の場合のデバッグログ
        if (gearList.isEmpty()) {
            System.out.println("No gears found in the database.");
        }

        // index.jspに転送
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }
}
