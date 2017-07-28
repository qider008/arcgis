<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="application" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/main.css" />
<script type="text/javascript">
	window.ctx='${ctx}';
</script>
<script type="text/javascript" src="${ctx}/static/lib/jquery/jquery-1.11.3.min.js"></script>
<script>
jQuery(document).ready(function() {
	$.ajaxSetup( {	 
	    error: function(response, textStatus, errorMsg){ // 出错时默认的处理函数	
	    	if(response.status==401){
	    		window.location.reload(true);//刷新当前页
	    	}     
	    }
	});
});
</script>
<!-- easyui -->
<link rel="stylesheet" type="text/css" href="${ctx}/static/lib/jquery-easyui-1.4.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/lib/jquery-easyui-1.4.3/themes/icon.css">
<script type="text/javascript" src="${ctx}/static/lib/jquery-easyui-1.4.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/main.js"></script>
