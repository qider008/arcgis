<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="application" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
	<jsp:include page="include.jsp"></jsp:include>
</head>
<body>
	<div id="cc" class="easyui-layout" style="width: 1000px; height: 600px; margin: 0 auto;">
		<div data-options="region:'north',split:true" style="height: 80px;">
			<a href="${ctx}/api/user/logout" id="logout" class="easyui-linkbutton" plain="true" iconCls="icon-back">退出</a>
			<p>
				<shiro.user>
	            	aaaa
	            </shiro.user>
	            <shiro.hasRole name="admin">
	            	拥有角色admin<shiro.principal/>
	            </shiro.hasRole>
	            <shiro.hasPermission name="user:create">
	            	创建操作
	            </shiro.hasPermission>
			</p>
		</div>
		<div id="west" data-options="region:'west',title:'导航',split:true" style="width: 200px;">
			<div class="easyui-accordion">
				<div title="系统管理" data-options="selected:true">
					<ul>
						<c:forEach items="${menus}" var="res">
							<c:if test="${fn:startsWith(res.url,'/')}">
								<c:set target="${res}" property="url" value="${fn:substringAfter(res.url,'/')}" />
							</c:if>
								<li><a href="${ctx}/${res.url}">${res.name}</a></li>
						</c:forEach>
					</ul>
				</div>
			</div>

		</div>
		<div data-options="region:'center'">
			<div id="tt" class="easyui-tabs" data-options="fit:true,border:true,plain:true"></div>

		</div>
		<div data-options="region:'south',split:true" style="height: 70px;"></div>
	</div>
</body>
</html>