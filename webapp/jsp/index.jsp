<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>商家管理后台</title>
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <link rel="stylesheet" href="http://cdn.bootcss.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="http://cdn.bootcss.com/ionicons/2.0.1/css/ionicons.min.css">

</head>
<body>

<div class="panel-group col-sm-2" style="top: 25px" id="hrms_sidebar_left" role="tablist" aria-multiselectable="true">
    <ul class="nav nav-pills nav-stacked emp_sidebar">
        <li role="presentation" class="active">
            <a href="#" data-toggle="collapse" data-target="#collapse_emp">
                <span class="glyphicon glyphicon-user" aria-hidden="true">商品管理</span>
            </a>
            <ul class="nav nav-pills nav-stacked" id="collapse_emp">
                <li role="presentation"><a href="upload.jsp" target="conter" class="upload">商品上传</a></li>
                <li role="presentation"><a target="conter" href="/product/shopSupervise.do" class="shopSupervise">商品管理</a></li>
            </ul>
        </li>
    </ul>
    <ul class="nav nav-pills nav-stacked dept_sidebar">
        <li role="presentation" class="active">
            <a href="#"  data-toggle="collapse" data-target="#collapse_dept">
                <span class="glyphicon glyphicon-cloud" aria-hidden="true">订单管理</span>
            </a>
            <ul class="nav nav-pills nav-stacked" id="collapse_dept">
                <li role="presentation"><a target="conter" href="/orderform/orderInfoSum.do" class="order_infoSum">订单统计</a></li>

            </ul>
        </li>
    </ul>

</div>
<div class="col-sm-10">

    <iframe src="home.jsp" name="conter" id="conter" width="100%" height="100%"></iframe>
</div>

</body>
</html>
