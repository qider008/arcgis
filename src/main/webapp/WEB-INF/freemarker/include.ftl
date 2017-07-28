[#assign ctx="${request.contextPath}"]
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
<!-- layui -->
<link rel="stylesheet" type="text/css" href="${ctx}/static/layui/css/layui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/define/index.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/define/default.css">
<script type="text/javascript" src="${ctx}/static/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/static/lib/echarts/echarts.min.js"></script>
<script src="${ctx}/static/lib/echarts/macarons.js"></script>
<script src="${ctx}/static/lib/echarts/china.js"></script>
<script type="text/javascript" src="${ctx}/static/js/define/index.js"></script>