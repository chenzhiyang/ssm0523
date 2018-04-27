<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.4.4.min.js"></script>
<title>查询商品列表</title>
</head>
<body> 
<script type="text/javascript">
	function sendJson(){
		$.ajax({
			type:"post",
			url:"${pageContext.request.contextPath }/items/sentJson",
			contentType:"application/json;charset=utf-8",
			data:'{"name":"测试商品","price":99.9}',
			success:function(data){
				alert(data);
			}
		});
	}
</script>
<input type="button" value="sentJson" onclick="sendJson()">
<form action="${pageContext.request.contextPath }/search" method="post">
查询条件：
<table width="100%" border=1>
<tr>
<!-- 如果Contrller中接收的是Vo，则页面上input的name属性值要等于vo中属性.属性.属性... -->
<td>商品名称：<input type="text" name="items.name"></td>
<td>商品价格：<input type="text" name="items.price"></td>
<td><input type="submit" value="查询"/></td>
</tr>
</table>
商品列表：
<table width="100%" border=1>
<tr>
	<td>商品名称</td>
	<td>商品价格</td>
	<td>生产日期</td>
	<td>商品描述</td>
	<td>操作</td>
</tr>
<!-- 批量修改，看视频day2-02集 -->
<!-- 
	如果批量修改，可以使用List<pojo>来接收，页面上input框name属性值=vo中接收集合的属性名称+[List下标]+.+list泛型的属性名称 
	<input type="text" name="itemsList[${status.index} }].createtime" 
		value="<fmt:formatDate value="${item.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
 -->
 
<c:forEach items="${itemList}" var="item" varStatus="status">
<tr>
	<td>${item.name }
		
	</td>
	<td>${item.price }</td>
	<td><fmt:formatDate value="${item.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	<td>${item.detail }</td>
	
	<td><a href="${pageContext.request.contextPath }/items/itemEdit/${item.id}">修改</a></td>

</tr>
</c:forEach>

</table>
</form>
</body>

</html>