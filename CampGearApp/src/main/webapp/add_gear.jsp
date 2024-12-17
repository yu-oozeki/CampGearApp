<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>道具登録</title>
</head>
<body>
    <div class="container">
        <h1>新しい道具を追加</h1>
        <form action="gear" method="post">
            <label for="name">アイテム名:</label>
            <input type="text" id="name" name="name" required>
            
            <label for="manufacturer">メーカー:</label>
            <input type="text" id="manufacturer" name="manufacturer">
            
            <label for="quantity">個数:</label>
            <input type="number" id="quantity" name="quantity" min="1" required>
            
            <label for="memo">メモ:</label>
            <textarea id="memo" name="memo" rows="4"></textarea>
            
            <input type="submit" value="保存">
        </form>
        <br>
        <a href="gearlist">戻る</a>
    </div>
</body>
</html>