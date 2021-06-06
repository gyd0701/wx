<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<title>批量上传商品</title>
<style type="text/css">
    <!--
    body {
        background: #6cb0f6eb;
        margin: 0;
        padding: 0;
    }
    .container{width:95%;
        background-color:#FFF;
        margin:0 auto;
    }
    .top{
        width:100%;
        padding-top:50px;
        height:120px;
        border-bottom-width: 2px;
        border-bottom-style: outset;
        border-bottom-color: #000;
    }

    .borea{ width:100%;
        margin:120px 100px;
        font-size:18px;
        padding-bottom:50PX;
    }
</style></head>

<body>

<div class="container">
    <div class="top" align="center"> <h2>批量上传商品 </h2></div>

    <div class="borea">
        <form action="/ShoppingCenter/product/uploadProduct.do" method="post" enctype="multipart/form-data">
            选择文件：<input type="file" name="productFile" ><br><br><br>
            <input type="submit" value="提交">
        </form>

    </div>
</div>
</body>
</html>

