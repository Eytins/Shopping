<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!-- 引入JSTL -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh">
	<head>
	    <meta charset="UTF-8" />
	    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
	    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
	    <title>会员管理--后台</title>
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/bootstarp/css/bootstrap.css" />
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/index.css" />
	    
	    <!-- 引入nmms.css文件 -->
	    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/nmms.css" type="text/css"></link>
	    
	    <script src="${pageContext.request.contextPath }/js/jquery.js" type="text/javascript" charset="utf-8"></script>
	    <script src="${pageContext.request.contextPath }/bootstarp/js/bootstrap.js" type="text/javascript" charset="utf-8"></script>
	    <script src="${pageContext.request.contextPath }/js/userSetting.js" type="text/javascript" charset="utf-8"></script>
	    
	    <!-- 引入bootstrap插件 -->
	    <script type="text/javascript" src="${pageContext.request.contextPath }/js/bootstrap-paginator.js" charset="utf-8" ></script>
	    
	    <script type="text/javascript">
	         $(function()
	         {
	              // 
	              $("#userName").val("${userParam.userName }");
	              $("#loginName").val("${userParam.loginName }");
	              $("#phone").val("${userParam.phone }");
	              $("#address").val("${userParam.address }");
	              $("#isValid").find("option[value='${userParam.isValid }']").attr("selected", true);
	              
	              var pages;
	              
	              if ("${pageUserList.pages }" == 0)
	              {
	                   pages = 1;
	              }else
	              {
	                   pages = "${pageUserList.pages }";
	              }
	              
	              var options = {
	                                 bootstrapMajorVersion : 3, // bootstrap版本
		    		                 currentPage           : "${pageUserList.pageNum }",
		    		                 totalPages            : pages,
		    		                 size                  : "normal",
		    		                 aligment              : "center",
		    		                 
		    		                 // 带参数查询使用
		    		                 onPageClicked : function(event, originalEvent, type, page)
		    		                 {
		    		                      $("pageNo").attr("value" , page);
		    		                      $("#searchForm").submit();
		    		                 }
	                            };
	                $("#userPage").bootstrapPaginator(options);
	                
	                // 修改会员信息页面默认值
	                $("input[name='toModifyUser']").click(function()
	                {
	                     $.ajax(
	                     {
	                          type : "post",
	                          url  :  "${pageContext.request.contextPath }/user/findById",
	                          data : {"id": $(this).attr("data-value")},
	                          dataType : "json",
	                          success  : function(result)
	                          {
	                                if (result.responseCode == 1)
	                                {
	                                    $("#modifyId").val(result.returnObject.userId);
	                                    $("#modifyUserName").val(result.returnObject.userName);
	                                    $("#modifyLoginName").val(result.returnObject.loginName);
	                                    $("#modifyPhone").val(result.returnObject.phone);
	                                    $("#modifyAddress").val(result.returnObject.address);
	                                }else
			                        {
			    					    $("#errorMsg").tooltip(
			    					    {
				    						title     : "error",
				    						placement : "center",
				    						template  : "<div class='tooltip errorMsg'>" + result.message + "</div>",
				    						tigger    : "manual"
			    					    }).tooltip("show");
				    			    }
	                          }
	                     });
	                });
	                
	                
	                // 修改会员信息
	                $("#modifyUserById").click(function()
	                {
	                     $.ajax(
	                     {
	                          type : "post",
	                          url  : "${pageContext.request.contextPath }/user/modifyById",
	                          data : {
	                                      "userId"    : $("#modifyId").val(),
	                                      "userName"  : $("#modifyUserName").val(),
	                                      "loginName" : $("#modifyLoginName").val(),
	                                      "phone"     : $("#modifyPhone").val(),
	                                      "address"   : $("#modifyAddress").val()
	                                 },
	                           dataType : "json",
	                           success  : function(result)
	                           {
	                                if (result.responseCode == 1)
	                                {
		                                location.href = "${pageContext.request.contextPath }/user/findByFuzzyParamList";
	                                }else
	                                {
				    					$("#errorMsg").tooltip(
				    					{
				    						title     : "error",
				    						placement : "center",
				    						template  : "<div class='tooltip errorMsg'>" + result.message + "</div>",
				    						tigger    : "manual"
				    					}).tooltip("show");
				    			    }
	                           }
	                     });
	                });
	                
	                // 启用/禁用
	                $("input[name='modifyStatus']").click(function()
	                {
	                     $.ajax(
	                     {
	                          type : "post",
	                          url  : "${pageContext.request.contextPath }/user/modifyStatus",
	                          data : {
	                                     "id"     : $(this).attr("data-id"),
	                                     "status" : $(this).attr("data-value")
	                                 },
	                          dataType : "json",
	                          success  : function(result)
	                          {
	                               if (result.responseCode == 1)
	                               {
	                                    location.href = "${pageContext.request.contextPath }/user/findByFuzzyParamList?pageNo=${pageUserList.pageNum }";
	                               }else
	                               {
	                                    $("#errorMsg").tooltip(
				    					{
				    						title     : "error",
				    						placement : "center",
				    						template  : "<div class='tooltip errorMsg'>" + result.message + "</div>",
				    						tigger    : "manual"
				    					}).tooltip("show");
	                               }
	                          }
	                     });
	                });
	         });
	    </script>
    </head>
	
	<body>
	    <div class="panel panel-default" id="userInfo" id="homeSet">
	        <div class="panel-heading">
	            <h3 class="panel-title">会员管理</h3>
	        </div>
	        <div class="panel-body">
	            <div class="showusersearch">
	                <!-- 添加  
	                       id="searchForm" 
	                       action="${pageContext.request.contextPath }/user/findByFuzzyParamList" 
	                       method="post"
	                -->
	                <form class="form-inline" id="searchForm" action="${pageContext.request.contextPath }/user/findByFuzzyParamList" method="post">
	                
					  <div class="form-group">
					    <label for="exampleInputName2">姓名:</label>
					    <!-- 添加   name="userName" -->
					    <input type="text" class="form-control" id="userName" name="userName" placeholder="请输入姓名">
					  </div>
					  
					  <div class="form-group">
					    <label for="exampleInputName2">帐号:</label>
					    <!-- 添加   name="loginName" -->
					    <input type="text" class="form-control" id="loginName" name="loginName" placeholder="请输入帐号">
					  </div>
					  
					  <div class="form-group">
					    <label for="exampleInputName2">电话:</label>
					    <!-- 添加   name="phone" -->
					    <input type="text" class="form-control" id="phone"  name="phone"  placeholder="请输入电话">
					  </div>
					  
					  <div class="form-group">
					    <label for="exampleInputName2">地址:</label>
					    <!-- 添加   name="address" -->
					    <input type="text" class="form-control" id="address"  name="address"  placeholder="请输入地址">
					  </div>
					  
					  <div class="form-group">
					    <label for="exampleInputName2">状态:</label>
					        <!-- 添加   name="isValid" -->
		                    <select class="form-control" id="isValid" name="isValid">
		                        <option value="-1">全部</option>
		                        <option value="1">---有效---</option>
		                        <option value="0">---无效---</option>
		                    </select>
					  </div>
					  
					  <br/> <br/> 
					  
					  <!-- 把 type="button" 修改成   type="submit"-->
					  <input type="submit" value="查询" class="btn btn-primary" id="doSearch">
					  <input type="reset" value="清空" class="btn btn-primary">
					</form>
	            </div>
	            
	            <div class="show-list" style="position: relative;top: 30px;">
	                <table class="table table-bordered table-hover" style='text-align: center;'>
	                    <thead>
	                        <tr class="text-danger">
	                            <th class="text-center">序号</th>
	                            <th class="text-center">姓名</th>
	                            <th class="text-center">帐号</th>
	                            <th class="text-center">电话</th>
	                            <th class="text-center">地址</th>
	                            <th class="text-center">状态</th>
	                            <th class="text-center">操作</th>
	                        </tr>
	                    </thead>
	                    <tbody id="tb">
	                       
	                        <c:forEach items="${pageUserList.list }" var="user" varStatus="i">
		                        <tr>
		                            <td>${i.count }</td>
		                            <td>${user.userName }</td>
		                            <td>${user.loginName }</td>
		                            <td>${user.phone }</td>
		                            <td>${user.address }</td>
		                            <td>
		                                <c:if test="${user.isValid eq 1 }">有效</c:if>
		                                <c:if test="${user.isValid eq 0 }">无效</c:if>
		                            </td>
		                            
		                            <td class="text-center">
		                                <!-- 
		                                                                                                   添加
		                                      name="doModifyUser" 
		                                      data-value="${user.userId }"
		                                -->
		                                <input type="button" class="btn btn-warning btn-sm doModify" value="修改" name="toModifyUser" data-value="${user.userId }">
		                                
		                                <c:if test="${user.isValid eq 1 }">
		                                    <!-- 
		                                                                                                             添加 
		                                          name="modifyStatus"
		                                          data-id="${user.userId }"
		                                          data-value="${user.isValid }"
		                                    -->
		                                    <input type="button" class="btn btn-danger btn-sm doDisable" value="禁用" name="modifyStatus" data-id="${user.userId }" data-value="${user.isValid }">
		                                </c:if>
		                                
		                                <c:if test="${user.isValid eq 0 }">
		                                    <!-- 
		                                                                                                             添加 
		                                          name="modifyStatus"
		                                          data-id="${user.userId }"
		                                          data-value="${user.isValid }"
		                                    -->
		                                    <input type="button" class="btn btn-danger btn-sm doDisable" value="启用" name="modifyStatus" data-id="${user.userId }" data-value="${user.isValid }">
		                                </c:if>
		                                
		                            </td>
		                        </tr>
	                        </c:forEach>
	                    </tbody>
	                </table>
	                
	                <!-- 添加 ul -->
	                <ul id="userPage"></ul>
	            </div>
	            
	            <!-- 修改会员窗口模块框  start -->
	            <div class="modal fade" tabindex="-1" id="myModal">
	                <!-- 窗口声明 -->
	                <div class="modal-dialog modal-lg">
	                    <!-- 内容声明 -->
	                    <div class="modal-content">
	                        <!-- 头部、主体、脚注 -->
	                        <div class="modal-header">
	                            <button class="close" data-dismiss="modal">&times;</button>
	                            <h4 class="modal-title">用户修改</h4>
	                        </div>
	                        <div class="modal-body text-center">
	                            <div class="row text-right">
	                                <label for="N" class="col-sm-4 control-label">编号:</label>
	                                <div class="col-sm-4">
	                                    <!-- 去除  
	                                           id="N"
	                                                                                                                 添加
	                                           id="modifyId" 
	                                           readonly="readonly" 
	                                    -->
	                                    <input type="text" class="form-control" id="modifyId" readonly="readonly">
	                                </div>
	                            </div>
	                            <br>
	                            <div class="row text-right">
	                                <label for="username" class="col-sm-4 control-label">姓名:</label>
	                                <div class="col-sm-4">
	                                    <!-- 
	                                                                                                                去除
	                                           id="username"
	                                                                                                                添加  
	                                           id="modifyUserName"
	                                    -->
	                                    <input type="text" class="form-control" id="modifyUserName">
	                                </div>
	                            </div>
	                            <br>
	                            <div class="row text-right">
	                                <label for="loginName" class="col-sm-4 control-label">帐号:</label>
	                                <div class="col-sm-4">
	                                    <!-- 
	                                                                                                                去除
	                                           id="loginName"
	                                                                                                                添加  
	                                           id="modifyLoginName"
	                                    -->
	                                    <input type="text" class="form-control" id="modifyLoginName">
	                                </div>
	                            </div>
	                            <br>
	                            <div class="row text-right">
	                                <label for="phone" class="col-sm-4 control-label">电话:</label>
	                                <div class="col-sm-4">
	                                    <!-- 
	                                                                                                                去除
	                                           id="phone"
	                                                                                                                添加  
	                                           id="modifyPhone"
	                                    -->
	                                    <input type="text" class="form-control" id="modifyPhone">
	                                </div>
	                            </div>
	                            <br>
	                            <div class="row text-right">
	                                <label for="adrees" class="col-sm-4 control-label">地址:</label>
	                                <div class="col-sm-4">
	                                    <!-- 
	                                                                                                                去除
	                                           id="adrees"
	                                                                                                                添加  
	                                           id="modifyAddress"
	                                    -->
	                                    <input type="email" class="form-control" id="modifyAddress">
	                                </div>
	                            </div>
	                            <br>
	                        </div>
	                        <div class="modal-footer">
	                            <!-- 添加  id="modifyUserById" -->
	                            <button class="btn btn-warning updateOne" id="modifyUserById">修改</button>
	                            <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
	                        </div>
	                    </div>
	                </div>
	            </div>
	            <!-- 修改会员窗口模块框  end -->
	        </div>
	    </div>
	</body>
</html>
