<%@ page language="java"  contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
	<title>订单统计</title>
	<link href="../css/navigation.css" type="text/css" rel="stylesheet" >
</head>

<body>
<div id="body" align="center"  >
	<div class="top" align="center"> <h2>订单统计 </h2></div>

	<div>

		<table width="90%"  border="1"bgcolor="#fff" style="text-align: center;margin: 10px 0" >
			<thead style="height: 50px">
			<th>序号</th><th>订单编号</th><th>客户名称</th><th>运费</th> <th>商品数量</th> <th>商品总价</th> <th>实付金额</th><th>下单日期</th>
			</thead>
			<tbody>
			<c:forEach items="${orderlist}"  var="o" varStatus="s">
				<tr style="height: 40px">
					<td>${s.count}</td>
					<td>${o.order_id}</td>
					<td>${o.name}</td>
                    <td>${o.freight}</td>
					<td>${o.total_count}</td>
					<td>${o.product_price}</td>
					<td>${o.total_price}</td>
					<td>${o.create_time}</td>
				</tr>

			</c:forEach>
			</tbody>
		</table>

		<div class="table_items">
			当前第<span class="badge"> ${curPageNo} </span>页，共有<span class="badge"> ${totalPages} </span>页，总记录数<span class="badge"> ${totalItems} </span>条
		</div>
		<nav aria-label="Page navigation" class="pull-right">
			<ul class="pagination">
				<a href="/orderform/orderInfoSum.do?pageNo=1">首页</a>


				<c:forEach begin="1" end="${totalPages<5?totalPages:5}" step="1" var="itemPage">
					<c:if test="${curPageNo == itemPage}">
						<a href="/orderform/orderInfoSum.do?pageNo=${itemPage}">&nbsp;${itemPage} &nbsp;</a>
					</c:if>
					<c:if test="${curPageNo != itemPage}">
						<a href="/orderform/orderInfoSum.do?pageNo=${itemPage}">&nbsp;${itemPage} &nbsp;</a>
					</c:if>
				</c:forEach>


				<a href="/orderform/orderInfoSum.do?pageNo=${totalPages}">尾页</a>
			</ul>
		</nav>
	</div>

</div>

</body>
</html>