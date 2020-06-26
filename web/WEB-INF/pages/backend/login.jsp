<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
  <head>
    <title>在线商城管理系统</title>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/bootstrap.min.css" type="text/css"></link>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/login.css" type="text/css"></link>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/bootstrap.min.js"></script>
    
    <script type="text/javascript">
         $(function()
         {
              // 点击 "验证码图片" 切换  验证码
              $("#image").click(function()
              {
                   $(this).attr("src", "${pageContext.request.contextPath}/code/show?id=" + new Date().getTime());
              });
              
              // 点击 "看不清" 切换  验证码
              $("#invisit").click(function()
              {
                   $("#image").attr("src", "${pageContext.request.contextPath}/code/show?id=" + new Date().getTime());
              });
         });
    </script>
  </head>
  
  <body>
  
  	<!-- 使用自定义css样式 div-signin 完成元素居中-->
    <div class="containercc div-signin">
      <div class="panel panel-primary div-shadow">
      
      	<!-- h3标签加载自定义样式,完成文字居中和上下间距调整 -->
	    <div class="panel-heading">
	    	<h3>在线商城 3.0</h3>
	    	<span>Network Mall Manager System</span>
	    </div>
	    
	    <div class="panel-body">
	      <!-- login form start 修改 action="${pageContext.request.contextPath}/staff/login"-->
	      <form action="${pageContext.request.contextPath}/staff/login" class="form-horizontal ccc" method="post">
		     <div class="form-group">
		       <label class="col-sm-3 control-label">用户名:</label>
		       <div class="col-sm-9">
		         <!-- 添加   name="loginName"  -->
		         <input class="form-control" type="text" name="loginName" placeholder="请输入用户名">
		       </div>
		    </div>
		     <div class="form-group">
		       <label class="col-sm-3 control-label">密&nbsp;&nbsp;&nbsp;&nbsp;码:</label>
		       <div class="col-sm-9">
		         <!-- 添加   name="password"  -->
		         <input class="form-control" type="password" name="password" placeholder="请输入密码">
		       </div>
		    </div>
		    
		    <div class="form-group">
		       <label class="col-sm-3 control-label">身&nbsp;&nbsp;&nbsp;&nbsp;份:</label>
		       <div class="col-sm-9">
		       
		         <!-- 添加   name="role"  -->
		       	 <select class="form-control" name="role">
		       	 
		       	    <!-- 添加: value="-1" -->
		       	 	<option value="-1">--请选择身份--</option>
		       	 	
		       	 	<!-- 添加: value="2001"-->
		       	 	<option value="2001">系统管理员</option>
		       	 	
		       	 	<!-- 添加: value="2002" -->
		       	 	<option value="2002">普通管理员</option>
		       	 	
		       	 </select>
		       </div>
		    </div>
		    
		    <div class="form-group">
		       <label class="col-sm-3 control-label">验证码:</label>
		       <div class="col-sm-4">
		         <!--  添加: name="code"  -->
		         <input class="form-control" type="text" name="code"  placeholder="请输入验证码">
		       </div>
		       <div class="col-sm-2">
		       	  <!-- 
		       	               验证码图片加载(需引入验证码文件)图像高度经过测试,建议不要修改
		       	               
		       	              添加 id="image" 
		       	  -->
			      <img id="image"  class="img-rounded" src="${pageContext.request.contextPath }/code/show" alt="验证码" style="height: 32px; width: 70px;"/>
		       </div>
		       <div class="col-sm-2">
		         <!-- 添加 id="invisit"  -->
		         <button id="invisit" type="button" class="btn btn-link">看不清</button>
		       </div>
		    </div>
		    
		    <div class="form-group">
		       <div class="col-sm-9  col-sm-offset-3 padding-left-0">
		       	 <div class="col-sm-4">
			        <!--  
			                               注释掉: <button type="button" class="btn btn-link btn-block">忘记密码?</button> 
			                               加上:  <span style="color:red;">${logMsg }</span>
			        -->
			        <span style="color:red;">${loginMsg }</span>
		       	 </div>
		       	 <div class="col-sm-4">
			         <button type="reset" class="btn btn-primary btn-block">重&nbsp;&nbsp;置</button>
		       	 </div>
		       	 <div class="col-sm-4">
			         <button type="submit" class="btn btn-primary btn-block">登&nbsp;&nbsp;陆</button>
		       	 </div>
		       </div>
		    </div>
	      </form>
	      <!-- login form end -->
	    </div>
	  </div>
    </div>
  </body>
</html>
