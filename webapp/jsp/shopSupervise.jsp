<%@ page language="java"  contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
	<title>商品管理</title>
	<script src="/webjars/jquery/3.1.1/jquery.min.js"></script>
	<link href="../css/navigation.css" type="text/css" rel="stylesheet" >

</head>

<body>
<div id="body" align="center" >
	<div class="top" align="center"> <h2>商品管理 </h2></div>
	<div>

		<table width="90%"  border="1"bgcolor="#fff" style="text-align: center;margin: 10px 0" >
			<thead style="height: 50px">
				<th>序号</th><th>商品ID</th><th>图片</th> <th>商品名称</th> <th>价格</th> <th>库存量</th><th>操作</th>
			</thead>
			<tbody>
			<c:forEach items="${plist}" var="plist" varStatus="s">
				<tr>
					<td>${s.count}</td>
					<td>${plist.p_id}</td>
					<td><img src="${plist.url}" width="100px" height="100px" alt=""></td>
					<td>${plist.pname}</td>
					<td>${plist.price}</td>
					<td>${plist.stock}</td>
					<td>
						<a href="#"  class="p_delete_btn">删除</a>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<div class="table_items">
			当前第<span class="badge"> ${curPageNo} </span>页，共有<span class="badge"> ${totalPages} </span>页，总记录数<span class="badge"> ${totalItems} </span>条
		</div>
		<nav aria-label="Page navigation" class="pull-right">
			<ul class="pagination">
				<a href="/product/shopSupervise.do?pageNo=1">首页</a>


				<c:forEach begin="1" end="${totalPages<5?totalPages:5}" step="1" var="itemPage">
					<c:if test="${curPageNo == itemPage}">
						<a href="/product/shopSupervise.do?pageNo=${itemPage}">&nbsp;${itemPage} &nbsp;</a>
					</c:if>
					<c:if test="${curPageNo != itemPage}">
						<a href="/product/shopSupervise.do?pageNo=${itemPage}">&nbsp;${itemPage} &nbsp;</a>
					</c:if>
				</c:forEach>


				<a href="/product/shopSupervise.do?pageNo=${totalPages}">尾页</a>
			</ul>
		</nav>
	</div>

</div>

<script  type="text/javascript">

    <!--删除操作-->
   $(".p_delete_btn").click(function () {
       var p_id = $(this).parent().parent().find("td:eq(1)").text();
       var pname = $(this).parent().parent().find("td:eq(3)").text();
       var curPageNo = ${curPageNo};
       if (confirm("确认删除【" + pname + "】吗？")) {
           $.ajax({
                         url: "/product/deleteEmp.do?p_id=" + p_id,
                         type: "GET",
                         success: function (result) {
                             if (result.code == 100) {
                                 alert("删除成功！");
                                 window.location.href = "/product/shopSupervise.do?pageNo="+curPageNo;
                             } else {
                 				alert(result.extendInfo.del_dept_error);
                             }
                         }
                     });
       }
   } )


</script>
</body>
</html>