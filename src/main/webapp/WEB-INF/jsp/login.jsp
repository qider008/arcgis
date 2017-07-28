<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="application" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
<jsp:include page="include.jsp"></jsp:include>
<script type="text/javascript">
	$(function() {
		$('#btn-login').click(function() {
			$('form').submit();
		});
	})
</script>
</head>
<body>
<body class="easyui-layout">
	<div data-options="region:'north',split:false,border:false" style="height: 100px;"></div>
	<div data-options="region:'west',split:false,border:false" style="width: 30%;"></div>
	<div data-options="region:'center',split:false,border:false" style="overflow: hidden; padding: 5px;">
		<div class="easyui-panel" title="Login" style="width: 400px; padding: 30px 60px;">
			<form action="${ctx}/api/user/login" method="post">
				<div style="margin-bottom: 20px">
					<div>用户名：</div>
					<input class="easyui-textbox" name="username" data-options="required:true,iconCls:'icon-man'" style="width: 100%; height: 32px">
				</div>
				<div style="margin-bottom: 20px">
					<div>密码:</div>
					<input class="easyui-textbox" type="password" name="password" data-options="required:true,iconCls:'icon-lock'" style="width: 100%; height: 32px">
				</div>
				<div style="margin-bottom: 20px">
					<div>验证码:</div>
					<input class="easyui-textbox" name="checkcode" data-options="required:true" style="width: 65%; height: 32px"/>
					<span style="width:30%;height:32px; display:inline-block;float:right; margin-left:5px;">
						<img src="${ctx}/app/checkcode" title="点击更换验证码" onclick="this.src='${ctx}/app/checkcode?d='+Math.random();" style="height: 30px"/>
					</span>
				</div>
				<div style="margin-bottom: 20px">
					<div>是否记住我:<input type="checkbox" name="rememberMe"></div>
				</div>
				<div style="margin-bottom: 20px">
					<a href="#" id="btn-login" class="easyui-linkbutton c8" iconCls="icon-ok" style="width: 100%; height: 32px">登录</a>
				</div>
				<div>
					<a href="${ctx}/api/user/register.page" class="easyui-linkbutton c1" style="width: 100%; height: 32px">注册</a>
				</div>
			</form>
		</div>
	</div>

</body>


</body>
</html>