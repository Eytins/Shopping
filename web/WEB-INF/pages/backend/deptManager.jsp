<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
	<head>
	    <meta charset="UTF-8" />
	    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
	    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
	    <title>后台部门管理页面</title>
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/bootstarp/css/bootstrap.css" />
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/index.css" />
	    <script src="${pageContext.request.contextPath }/js/jquery.js" type="text/javascript" charset="utf-8"></script>
	    <script src="${pageContext.request.contextPath }/bootstarp/js/bootstrap.js" type="text/javascript" charset="utf-8"></script>
	    <script src="${pageContext.request.contextPath }/js/userSetting.js" type="text/javascript" charset="utf-8"></script>
	    
	    <!-- 引入bootstrap分页插件 -->
		<script src="${pageContext.request.contextPath }/js/bootstrap-paginator.js" type="text/javascript" charset="utf-8"></script>
		
		<script type="text/javascript">
		
		     $(function()
	         {
	              var pages;
	              
	              if ("${pageDeptList.pages }" == 0)
	              {
	                  pages = 1;
	              }else
	              {
	                  pages = "${pageDeptList.pages }";
	              }
	              
	              var options = {
	                                bootstrapMajorVersion : 3, // bootstrap版本
	                                currentPage : "${pageDeptList.pageNum }",
	                                totalPages : pages,
	                                size : "normal",
	                                aligment : "center",
	                                pageUrl : function (type, page, current)
	                                {
	                                     return "${pageContext.request.contextPath}/dept/findAll?pageNo=" + page;
	                                }
	                            };
	                            
	              $("#deptPage").bootstrapPaginator(options);
	              
	              
	              // 添加父部门
	              $("#addFatherDept").click(function()
	              {
	                   $.ajax(
		               {
		                    type : "post",
		                    url  : "${pageContext.request.contextPath }/dept/addFatherDept",
		                    data :  
		                             {
		                                 "deptName" : $("#fatherDeptName").val(),
		                                 "remark"   : $("#fatherRemark").val()
		                             },
		                    dataType : "json",
		                    success : function(result)
		                    {
		                         if (result.responseCode == 1)
		                         {
		                              location.href = "${pageContext.request.contextPath }/dept/findAll?pageNo=${pageDeptList.pageNum }";
		                         }else
		                         {
				    			     $("#errorMsg").tooltip(
				    			     {
				    					  title:"error",
				    					  placement:"center",
				    					  template:"<div class='tooltip errorMsg'>" + result.message + "</div>",
				    					  tigger:"manual"
				    				 }).tooltip("show");
			    				}
		                   }
		              });
	              
	              });
	              
	              
	              $("input[name='doAddSonDept']").click(function()
			      {
			             var deptId = $(this).attr("data-id");
			             var deptName = $(this).attr("data-value");
			              
			             $("#fatherId").val(deptId);
			             $("#fatherName").val(deptName);
			      });
			      
			      
			      // 添加子部门
			      $("#addSonDept").click(function()
			      {
			           $.ajax(
			           {
			               type : "post",
			               url  : "${pageContext.request.contextPath }/dept/addSonDept",
			               data : {
			                           "fatherDeptId" : $("#fatherId").val(),
			                           "deptName" : $("#deptName").val(),
			                           "remark" : $("#deptRemark").val()
			                      },
			               dataType : "json",
			               success : function(result)
			               {
			                    if (result.responseCode == 1)
			                    {
			                         location.href = "${pageContext.request.contextPath}/dept/findAll?pageNo=${pageDeptList.pageNum }";
			                    }else
	                            {
			    					$("#errorMsg").tooltip(
			    					{
			    						title:"error",
			    						placement:"center",
			    						template:"<div class='tooltip errorMsg'>" + result.message + "</div>",
			    						tigger:"manual"
			    					}).tooltip("show");
		    				    }
			               }     
			                 
			           });
			      });
			      
			      
			      // 子部门的 启用/禁用
			      $("input[name='modifyStatus']").click(function()
			      {
			           $.ajax(
			           {
			                type : "post",
			                url  : "${pageContext.request.contextPath }/dept/modifyStatus",
			                data : {
			                            "deptId" : $(this).attr("data-id"),
			                            "isValid" : $(this).attr("data-status")
			                       },
			                dataType : "json",
			    			success : function (result)
			    			{
			    				if (result.responseCode == 1)
			    				{
			    					location.href="${pageContext.request.contextPath}/dept/findAll?pageNo=${pageDeptList.pageNum}";
			    				}else
			    				{
			    					$("#errorMsg").tooltip(
			    					{
			    						title:"error",
			    						placement:"center",
			    						template:"<div class='tooltip errorMsg'>" + result.message + "</div>",
			    						tigger:"manual"
			    					}).tooltip("show");
			    				}
			    			}
			           });
			      });
			      
			      
			      // 跳出 修改部门信息  页面时  根据部门id查询要修改的部门信息 
			      $("input[name='doModifyDept']").click(function()
			      {
			           $.ajax(
			           {
			                type : "post",
    			            url  : "${pageContext.request.contextPath}/dept/findById",
			                data : {"id" : $(this).attr("data-id")},
			                dataType : "json",
			                success : function(result)
			                {
				                if (result.responseCode == 1)
				                {
			    					$("#modifyId").val(result.returnObject.deptId);
			    					$("#modifyName").val(result.returnObject.deptName);
			    					$("#modifyRemark").val(result.returnObject.remark);
			    				}else
			    				{
			    					$("#errorMsg").tooltip(
			    					{
			    						title:"error",
			    						placement:"center",
			    						template:"<div class='tooltip errorMsg'>" + result.message + "</div>",
			    						tigger:"manual"
			    					}).tooltip("show");
			    				}
			                }
			           });
			      });
			      
			      // 修改部门信息
			      $("#updateDept").click(function()
			      {
			           $.ajax(
			           {
			                type : "post",
    			            url  : "${pageContext.request.contextPath}/dept/modifyDept",
			                data : {
			                          "deptId" : $("#modifyId").val(),
			                          "deptName" : $("#modifyName").val(),
			                          "remark" : $("#modifyRemark").val(),
			                       },
			                dataType : "json",
			                success : function(result)
			                {
				                if (result.responseCode == 1)
				                { 
				                    location.href="${pageContext.request.contextPath}/dept/findAll?pageNo=${pageDeptList.pageNum }";
			    				}else
			    				{
			    					$("#errorMsg").tooltip(
			    					{
			    						title:"error",
			    						placement:"center",
			    						template:"<div class='tooltip errorMsg'>" + result.message + "</div>",
			    						tigger:"manual"
			    					}).tooltip("show");
			    				}
			                }
			           });
			      });
	         
	         });
	         
		</script>
	</head>
	
	<body>
	
	    <!-- 部门管理 -->
	    <div class="panel panel-default" id="departmentSet">
	        <div class="panel-heading">
	            <h3 class="panel-title">部门管理</h3>
	        </div>
	        <div class="panel-body">
	            <input type="button" value="添加部门" class="btn btn-primary" id="doAddDept">
	            <br>
	            <br>
	            <div class="modal fade" tabindex="-1" id="Dept">
	                <!-- 窗口声明 -->
	                <div class="modal-dialog modal-lg">
	                    <!-- 内容声明 -->
	                    <div class="modal-content">
	                        <!-- 头部、主体、脚注 -->
	                        <div class="modal-header">
	                            <button class="close" data-dismiss="modal">&times;</button>
	                            <h4 class="modal-title">添加部门</h4>
	                        </div>
	                        
	                        <!-- 添加 父部门  模态框  -->
	                        <div class="modal-body text-center">
	                            <div class="row text-right">
	                                <label for="dept-name" class="col-sm-4 control-label">部门名称:</label>
	                                <div class="col-sm-4">
	                                    <!-- 把 id="dept-name" 修改成  id="fatherDeptName" -->
	                                    <input type="text" class="form-control" id="fatherDeptName">
	                                </div>
	                            </div>
	                            <br>
	                            <div class="row text-right">
	                                <label for="dept-duty" class="col-sm-4 control-label">部门职能:</label>
	                                <div class="col-sm-4">
	                                    <!-- 把id="dept-duty" 修改成  id="fatherRemark" -->
	                                    <input type="text" class="form-control" id="fatherRemark">
	                                </div>
	                            </div>
	                            <br>
	                        </div>
	                        <div class="modal-footer">
	                            <!-- 添加   onclick="addFatherDept();" -->
	                            <button class="btn btn-primary addDept" id="addFatherDept">添加</button>
	                            <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
	                        </div>
	                    </div>
	                </div>
	            </div>
	            
	            <!-- 添加  子部门  模态框  -->
	            <div class="modal fade" tabindex="-1" id="sonDept">
	                <!-- 窗口声明 -->
	                <div class="modal-dialog modal-lg">
	                    <!-- 内容声明 -->
	                    <div class="modal-content">
	                        <!-- 头部、主体、脚注 -->
	                        <div class="modal-header">
	                            <button class="close" data-dismiss="modal">&times;</button>
	                            <h4 class="modal-title">添加子部门</h4>
	                        </div>
	                        <div class="modal-body text-center">
	                            <div class="row text-right">
	                                <label for="dept-name" class="col-sm-4 control-label">父部门名称:</label>
	                                <div class="col-sm-4">
	                                    <!-- 
	                                                                                                          添加隐藏域  <input type="hidden" id="fatherId" />
	                                    -->
	                                    <input type="hidden" id="fatherId" />
	                                    
	                                    <!-- 
	                                                                                                          把 id="dept-name" 的值改成  id="fatherName" 
	                                                                                                          去掉 value="市场部"
	                                     -->
	                                    <input type="text" class="form-control" id="fatherName" readonly="readonly">
	                                </div>
	                            </div>
	                            <br>
	                            <div class="row text-right">
	                                <label for="dept-name" class="col-sm-4 control-label">部门名称:</label>
	                                <div class="col-sm-4">
	                                     <!-- 
	                                                                                                          把 id="dept-name" 修改成  id="deptName" 
	                                     -->
	                                    <input type="text" class="form-control" id="deptName">
	                                </div>
	                            </div>
	                            <br>
	                            <div class="row text-right">
	                                <label for="dept-duty" class="col-sm-4 control-label">部门职能:</label>
	                                <div class="col-sm-4">
	                                     <!-- 
	                                                                                                          把 id="dept-duty" 修改成  id="deptRemark" 
	                                     -->
	                                    <input type="text" class="form-control" id="deptRemark">
	                                </div>
	                            </div>
	                            <br>
	                        </div>
	                        <div class="modal-footer">
	                            <!-- 添加  id="addSonDept" -->
	                            <button class="btn btn-primary addSonDept" id="addSonDept">添加</button>
	                            <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
	                        </div>
	                    </div>
	                </div>
	            </div>
	            
	            
	            <!-- 修改部门模态框 -->
	             <div class="modal fade" tabindex="-1" id="modifyDept">
	                <!-- 窗口声明 -->
	                <div class="modal-dialog modal-lg">
	                    <!-- 内容声明 -->
	                    <div class="modal-content">
	                        <!-- 头部、主体、脚注 -->
	                        <div class="modal-header">
	                            <button class="close" data-dismiss="modal">&times;</button>
	                            <h4 class="modal-title">修改部门</h4>
	                        </div>
	                        <div class="modal-body text-center">
	                            <div class="row text-right">
	                                <label for="dept-name" class="col-sm-4 control-label">id:</label>
	                                <div class="col-sm-4">
	                                    <!-- 
	                                                                                                            把id="dept-name" 修改为    id="modifyId" 
	                                                                                                            去掉 value="1" 
	                                    -->
	                                    <input type="text" class="form-control" id="modifyId" readonly="readonly">
	                                </div>
	                            </div>
	                            <br>
	                            <div class="row text-right">
	                                <label for="dept-name" class="col-sm-4 control-label">部门名称:</label>
	                                <div class="col-sm-4">
	                                    <!-- 把id="dept-name" 修改为    id="modifyName"  -->
	                                    <input type="text" class="form-control" id="modifyName" >
	                                </div>
	                            </div>
	                            <br>
	                            <div class="row text-right">
	                                <label for="dept-duty" class="col-sm-4 control-label">部门职能:</label>
	                                <div class="col-sm-4">
	                                    <!-- 把id="dept-duty" 修改为    id="modifyName"  -->
	                                    <input type="text" class="form-control" id="modifyRemark">
	                                </div>
	                            </div>
	                            <br>
	                        </div>
	                        <div class="modal-footer">
	                            <!-- 添加   id="updateDept" -->
	                            <button class="btn btn-primary modifyDept" id="updateDept">修改</button>
	                            <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
	                        </div>
	                    </div>
	                </div>
	            </div>
	            
	            <div class="show-list">
	                <table class="table table-bordered table-hover" style='text-align: center;'>
	                    <thead>
	                        <tr class="text-danger">
	                            <th class="text-center">序号</th>
	                            <th class="text-center">部门编号</th>
	                            <th class="text-center">部门名称</th>
	                            <th class="text-center">部门职能</th>
	                            <th class="text-center">所属部门</th>
	                            <th class="text-center">部门状态</th>
	                            <th class="text-center">操作</th>
	                        </tr>
	                    </thead>
	                    <tbody id="tb">
	                          <c:forEach items="${pageDeptList.list }" var="dept" varStatus="i">
	                               <tr>
	                                    <td>${i.index + 1 }</td>
		                                <td>${dept.deptNo }</td>
		                                <td>${dept.deptName }</td>
		                                <td>${dept.remark }</td>
		                                <td>
		                                    <c:choose>
		                                         <c:when test="${empty dept.fatherDept }">--</c:when>
		                                         <c:otherwise>${dept.fatherDept.deptName }</c:otherwise>
		                                    </c:choose>
		                                </td>
		                                <td>
		                                    <c:if test="${dept.isValid eq 0 }">无效</c:if>
		                                    <c:if test="${dept.isValid eq 1 }">有效</c:if>
		                                </td>
		                                
		                               <td class="text-center">
		                                    <!-- 
		                                                                                                                添加 
		                                          name="doAddSonDept"
		                                          data-id="${dept.deptId}"
		                                          data-value="${dept.deptName }"
		                                     -->
				                            <input type="button" class="btn btn-info btn-sm doAddSonDept" value="添加子部门" name="doAddSonDept" data-id="${dept.deptId}" data-value="${dept.deptName }">
				                            
				                            
				                            <!-- 添加: name="doModifyDept" data-id="${dept.deptId }" -->
				                            <input type="button" class="btn btn-warning btn-sm doModifyDept" value="修改" name="doModifyDept" data-id="${dept.deptId }">
				                            
				                            <c:if test="${dept.isValid eq 0 }">
				                                 <!--  
				                                                                                                          添加  
				                                         name="modifyStatus" 
				                                         data-id="${dept.deptId }"
				                                         data-status="${dept.isValid }"
				                                 -->
				                                 <input type="button" class="btn btn-success btn-sm doDisable" value="启用" name="modifyStatus" data-id="${dept.deptId }" data-status="${dept.isValid }"/>
				                            </c:if>
				                            
				                            <c:if test="${dept.isValid eq 1 }">
				                                 <!--  
				                                                                                                          添加  
				                                         name="modifyStatus" 
				                                         data-id="${dept.deptId }"
				                                         data-status="${dept.isValid }"
				                                 -->
				                                 <input type="button" class="btn btn-success btn-sm doDisable" value="禁用" name="modifyStatus" name="modifyStatus" data-id="${dept.deptId }" data-status="${dept.isValid }"/>
				                            </c:if>
                                       </td>
	                               </tr>
	                          </c:forEach>
	                    </tbody>
	                </table>
	                
	                <ul id="deptPage"></ul>
	            </div>
	        </div>
	    </div>
	</body>

</html>