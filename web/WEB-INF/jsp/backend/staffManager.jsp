<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh">

<head>
    <meta charset="UTF-8" content=""/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>管理员管理---后台</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/bootstarp/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/index.css"/>
    <script src="${pageContext.request.contextPath }/js/jquery.js" type="text/javascript" charset="utf-8"></script>
    <script src="${pageContext.request.contextPath }/bootstarp/js/bootstrap.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="${pageContext.request.contextPath }/js/userSetting.js" type="text/javascript" charset="utf-8"></script>

    <!-- 添加bootstrap分页js -->
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/bootstrap-paginator.js"></script>
</head>

<script type="text/javascript">
    $(function () {
        $("#staffName").val("${staffParameter.staffName }");
        $("#loginName").val("${staffParameter.loginName }");
        $("#phone").val("${staffParameter.phone }");
        $("#email").val("${staffParameter.email }");
        $("#deptId").find("option[value='${deptId }']").attr("selected", true);
        $("#role").find("option[value='${staffParameter.role }']").attr("selected", true);
        $("#isValid").find("option[value='${staffParameter.isValid }']").attr("selected", true);

        $("#staffName2").val("${staffParameter.staffName }");
        $("#loginName2").val("${staffParameter.loginName }");
        $("#phone2").val("${staffParameter.phone }");
        $("#email2").val("${staffParameter.email }");
        $("#deptId2").find("option[value='${deptId }']").attr("selected", true);
        $("#role2").find("option[value='${staffParameter.role }']").attr("selected", true);
        $("#isValid2").find("option[value='${staffParameter.isValid }']").attr("selected", true);

        const dept = document.getElementById('deptId2');
        const role = document.getElementById('role2');
        const valid = document.getElementById('isValid2');
        dept.style.visibility = 'hidden';
        role.style.visibility = 'hidden';
        valid.style.visibility = 'hidden';

        var pages;

        if ("${pageStaffList.pages } == 0") {
            pages = 1;
        } else {
            pages = "${pageStaffList.pages}";
        }

        var options = {
            bootstrapMajorVersion: 3,
            currentPage: "${pageStaffList.pageNum }",
            totalPages: pages,
            size: "normal",
            aligment: "center",

            onPageClicked: function (event, originalEvent, type, page)  // 带参数查询使用
            {
                $("#pageNo").attr("value", page);

                $("#searchForm").submit();   // 表单提交
            }
        };

        $("#staffPage").bootstrapPaginator(options);

        // 清空
        $("#res").click(function () {
            $("#staffName").val("");
            $("#loginName").val("");
            $("#phone").val("");
            $("#email").val("");
            $("#deptId")[0].options.selectedIndex = 0;
            $("#role")[0].options.selectedIndex = 0;
            $("#isValid")[0].options.selectedIndex = 0;
        });

        // 添加管理员
        $("#addStaff").click(function () {
            $.ajax(
                {
                    type: "post",
                    url: "${pageContext.request.contextPath}/staff/addStaff",
                    data: {
                        "staffName": $("#addStaffName").val(),
                        "loginName": $("#addLoginName").val(),
                        "password": $("#addPassword").val(),
                        "phone": $("#addPhone").val(),
                        "email": $("#addEmail").val(),
                        "deptId": $("#addDept")[0].options[$("#addDept")[0].selectedIndex].value,
                        "role": $("#addRole")[0].value
                    },
                    dataType: "json",
                    success: function (result) {
                        if (result.responseCode == 1) {
                            location.href = "${pageContext.request.contextPath}/staff/findFuzzyByParamList?pageNo=${pageStaffList.pageNum }";
                        } else {
                            $("#errorMsg").tooltip(
                                {
                                    title: "error",
                                    placement: "center",
                                    template: "<div class='tooltip errorMsg'>" + result.message + "</div>",
                                    tigger: "manual"
                                }).tooltip("show");
                        }
                    }
                });
        });

        // 做修改管理员操作时  查询出修改页面的管理员信息
        $(".doMangerModify").click(function () {
            $.ajax(
                {
                    type: "post",
                    url: "${pageContext.request.contextPath}/staff/findById",
                    data: {
                        "staffId": $(this).attr("data-id"),
                        "isValid": $(this).attr("data-status")
                    },
                    dataType: "json",
                    success: function (result) {
                        if (result.responseCode == 1) {
                            $("#modifyStaffId").val(result.returnObject.staffId);
                            $("#modifyStaffName").val(result.returnObject.staffName);
                            $("#modifyLoginName").val(result.returnObject.loginName);
                            $("#modifyPhone").val(result.returnObject.phone);
                            $("#modifyEmail").val(result.returnObject.email);
                            $("#modifyRole").val(result.returnObject.role);

                            if (result.returnObject.dept != null) {
                                $("#modifyDept").val(result.returnObject.dept.deptId);
                            } else {
                                $("#modifyDept").val("-1");
                            }
                        } else {
                            $("#errorMsg").tooltip(
                                {
                                    title: "error",
                                    placement: "center",
                                    template: "<div class='tooltip errorMsg'>" + result.message + "</div>",
                                    tigger: "manual"
                                }).tooltip("show");
                        }
                    }
                });
        });

        // 修改管理员信息
        $(".doMargerModal").click(function () {
            $.ajax(
                {
                    type: "post",
                    url: "${pageContext.request.contextPath }/staff/modifyStaff",
                    data: {
                        "staffId": $("#modifyStaffId").val(),
                        "staffName": $("#modifyStaffName").val(),
                        "phone": $("#modifyPhone").val(),
                        "email": $("#modifyEmail").val(),
                        "role": $("#modifyRole").val(),
                        "deptId": $("#modifyDept").val()
                    },
                    dataType: "json",
                    success: function (result) {
                        if (result.responseCode == 1) {
                            location.href = "${pageContext.request.contextPath }/staff/findFuzzyByParamList?pageNo=${pageStaffList.pageNum }";
                        } else {
                            $("#errorMsg").tooltip(
                                {
                                    title: "error",
                                    placement: "center",
                                    template: "<div class='tooltip errorMsg'>" + result.message + "</div>",
                                    tigger: "manual"
                                }).tooltip("show");
                        }
                    }
                });
        });

        // 管理员 启用/禁用
        $(".doMangerDisable").click(function () {
            $.ajax(
                {
                    type: "post",
                    url: "${pageContext.request.contextPath}/staff/modifyStaffStatus",
                    data: {
                        "staffId": $(this).attr("data-id"),
                        "isValid": $(this).attr("data-status")
                    },
                    dataType: "json",
                    success: function (result) {
                        if (result.responseCode == 1) {
                            location.href = "${pageContext.request.contextPath }/staff/findFuzzyByParamList?pageNo=${pageStaffList.pageNum }";
                        } else {
                            $("#errorMsg").tooltip(
                                {
                                    title: "error",
                                    placement: "center",
                                    template: "<div class='tooltip errorMsg'>" + result.message + "</div>",
                                    tigger: "manual"
                                }).tooltip("show");
                        }
                    }

                });
        });
    });
</script>
<body>
<!-- 管理员管理 -->
<div class="panel panel-default" id="adminSet">
    <div class="panel-heading">
        <h3 class="panel-title">管理员管理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="errorMsg"></span></h3>
    </div>
    <div class="panel-body">
        <div class="showmargersearch">
            <!--添加
                       id="searchForm"
                       method="post"
                       action="${pageContext.request.contextPath }/staff/findFuzzyByParams"-->
            <form class="form-inline" id="searchForm" method="post"
                  action="${pageContext.request.contextPath }/staff/findFuzzyByParamList">
                <div class="form-group">
                    <label for="staffName">姓名:</label>
                    <!-- 修改 id=userName" 为  id="staffName"
                                  添加  name="staffName" -->
                    <input type="text" class="form-control" id="staffName" name="staffName" placeholder="请输入姓名">
                </div>

                <div class="form-group">
                    <label for="loginName">帐号:</label>
                    <!-- 添加  name="loginName"  -->
                    <input type="text" class="form-control" id="loginName" name="loginName" placeholder="请输入帐号">
                </div>

                <div class="form-group">
                    <label for="phone">电话:</label>
                    <!-- 添加  name="phone"  -->
                    <input type="text" class="form-control" id="phone" name="phone" placeholder="请输入电话">
                </div>

                <div class="form-group">
                    <label for="email">邮箱:</label>
                    <!-- 添加  name="email"  -->
                    <input type="email" class="form-control" id="email" name="email" placeholder="请输入邮箱">
                </div>

                <br/><br/>

                <div class="form-group">
                    <label for="deptId">部门</label>
                    <!-- 添加 id="deptId" -->
                    <select class="form-control" id="deptId" name="deptId">
                        <option value="-1">--全部--</option>

                        <!-- 和StaffController类中的 @ModelAttribute("deptList") 相对应-->
                        <c:forEach items="${deptList }" var="dept">
                            <option value="${dept.deptId }">${dept.deptName }</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="role">角色</label>
                    <!-- 添加 id="role" -->
                    <select class="form-control" id="role" name="role">
                        <option value="-1">--全部--</option>
                        <option value="2001">系统管理员</option>
                        <option value="2002">普通管理员</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="isValid">部门状态</label>
                    <!-- 添加 id="isValid" -->
                    <select class="form-control" id="isValid" name="isValid">
                        <option value="-1">--全部--</option>
                        <option value="1">有效</option>
                        <option value="0">无效</option>
                    </select>
                </div>

                <!-- 修改   type="button" 为   type="submit" -->
                <input type="submit" value="查询" class="btn btn-primary" id="doSearch">

                <!-- 添加  清空 按钮 -->
                <input type="button" value="清空" class="btn btn-primary" id="res">
            </form>

            <%----------------------------------------------------------------------------------%>
            <form class="form-inline" action="${pageContext.request.contextPath }/staff/exportExcel" id="exportForm"
                  method="post">

                <input type="submit" value="导出管理员" class="btn btn-primary" id="doExportManger" onclick="submitForm()">
                <input type="button" value="添加管理员" class="btn btn-primary" id="doAddManger">

                <div class="form-group">
                    <label for="staffName"></label>
                    <!-- 修改 id=userName" 为  id="staffName"
                                  添加  name="staffName" -->
                    <input type="hidden" class="form-control" id="staffName2" name="staffName" placeholder="请输入姓名">
                </div>

                <div class="form-group">
                    <label for="loginName"></label>
                    <!-- 添加  name="loginName"  -->
                    <input type="hidden" class="form-control" id="loginName2" name="loginName" placeholder="请输入帐号">
                </div>

                <div class="form-group">
                    <label for="phone"></label>
                    <!-- 添加  name="phone"  -->
                    <input type="hidden" class="form-control" id="phone2" name="phone" placeholder="请输入电话">
                </div>

                <div class="form-group">
                    <label for="email"></label>
                    <!-- 添加  name="email"  -->
                    <input type="hidden" class="form-control" id="email2" name="email" placeholder="请输入邮箱">
                </div>

                <div class="form-group">
                    <label for="deptId"></label>
                    <!-- 添加 id="deptId" -->
                    <select class="form-control" id="deptId2" name="deptId" hidden="hidden">
                        <option value="-1">--全部--</option>

                        <!-- 和StaffController类中的 @ModelAttribute("deptList") 相对应-->
                        <c:forEach items="${deptList }" var="dept">
                            <option value="${dept.deptId }">${dept.deptName }</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="role"></label>
                    <!-- 添加 id="role" -->
                    <select type="hidden" class="form-control" id="role2" name="role">
                        <option value="-1">--全部--</option>
                        <option value="2001">系统管理员</option>
                        <option value="2002">普通管理员</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="isValid"></label>
                    <!-- 添加 id="isValid" -->
                    <select class="form-control" id="isValid2" name="isValid">
                        <option value="-1">--全部--</option>
                        <option value="1">有效</option>
                        <option value="0">无效</option>
                    </select>
                </div>
            </form>

            <script type="text/javascript">
                function submitForm1() {
                    document.getElementById("searchForm").submit();
                }

                function submitForm() {
                    document.getElementById("exportForm").submit();
                }
            </script>

            <%----------------------------------------------------------------------------------%>
        </div>

        <!-- 添加管理员 -->
        <div class="modal fade" tabindex="-1" id="myMangerUser">
            <!-- 窗口声明 -->
            <div class="modal-dialog modal-lg">
                <!-- 内容声明 -->
                <div class="modal-content">
                    <!-- 头部、主体、脚注 -->
                    <div class="modal-header">
                        <button class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">添加管理员</h4>
                    </div>
                    <div class="modal-body text-center">
                        <div class="row text-right">
                            <label for="addStaffName" class="col-sm-4 control-label">用户姓名:</label>
                            <div class="col-sm-4">
                                <!-- 把  id="marger-username" 改成  id="addStaffName" -->
                                <input type="text" class="form-control" id="addStaffName">
                            </div>
                        </div>
                        <br>
                        <div class="row text-right">
                            <label for="addLoginName" class="col-sm-4 control-label">登录帐号:</label>
                            <div class="col-sm-4">
                                <!-- 把  id="marger-loginName" 改成  id="addLoginName" -->
                                <input type="text" class="form-control" id="addLoginName">
                            </div>
                        </div>
                        <br>
                        <div class="row text-right">
                            <label for="addPassword" class="col-sm-4 control-label">登录密码:</label>
                            <div class="col-sm-4">
                                <!-- 把  id="marger-password" 改成  id="addPassword" -->
                                <input type="password" class="form-control" id="addPassword">
                            </div>
                        </div>
                        <br>
                        <div class="row text-right">
                            <label for="addPhone" class="col-sm-4 control-label">联系电话:</label>
                            <div class="col-sm-4">
                                <!-- 把  id="marger-phone" 改成  id="addPhone" -->
                                <input type="text" class="form-control" id="addPhone">
                            </div>
                        </div>
                        <br>
                        <div class="row text-right">
                            <label for="addEmail" class="col-sm-4 control-label">邮箱:</label>
                            <div class="col-sm-4">
                                <!-- 把  id="marger-email" 改成  id="addEmail" -->
                                <input type="email" class="form-control" id="addEmail">
                            </div>
                        </div>
                        <br>
                        <div class="row text-right">
                            <label for="addRole" class="col-sm-4 control-label">角&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;色:</label>
                            <div class=" col-sm-4">
                                <!-- 添加  id="addRole" -->
                                <select class="form-control" id="addRole">
                                    <option value="-1">--请选择--</option>
                                    <option value="2001">系统管理员</option>
                                    <option value="2002">普通管理员</option>
                                </select>
                            </div>
                        </div>
                        <br>
                        <div class="row text-right">
                            <label for="addDept" class="col-sm-4 control-label">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门:</label>
                            <div class=" col-sm-4">
                                <!-- 添加    id="addDept" -->
                                <select class="form-control" id="addDept">
                                    <option value="-1">--请选择--</option>

                                    <c:forEach items="${enabledDeptList }" var="dept">
                                        <option value="${dept.deptId }">${dept.deptName }</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <br/>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-primary add-Manger" id="addStaff">添加</button>
                        <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- 管理员修改 -->
        <div class="modal fade" tabindex="-1" id="myModal-Manger">
            <!-- 窗口声明 -->
            <div class="modal-dialog modal-lg">
                <!-- 内容声明 -->
                <div class="modal-content">
                    <!-- 头部、主体、脚注 -->
                    <div class="modal-header">
                        <button class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">管理员修改</h4>
                    </div>
                    <div class="modal-body text-center">
                        <div class="row text-right">
                            <label for="modifyStaffId" class="col-sm-4 control-label">id:</label>
                            <div class="col-sm-4">
                                <!-- 把  id="MargerStaffId"  改成   id="modifyStaffId" -->
                                <input type="text" class="form-control" id="modifyStaffId" readonly="readonly">
                            </div>
                        </div>
                        <br>

                        <div class="row text-right">
                            <label for="modifyStaffName" class="col-sm-4 control-label">管理员姓名:</label>
                            <div class="col-sm-4">
                                <!-- 把  id="MargerStaffname"  改成   id="MargerStaffname" -->
                                <input type="text" class="form-control" id="modifyStaffName">
                            </div>
                        </div>
                        <br>
                        <div class="row text-right">
                            <label for="modifyLoginName" class="col-sm-4 control-label">登录帐号:</label>
                            <div class="col-sm-4">
                                <!-- 把  id="MargerLoginName"  改成   id="modifyLoginName" -->
                                <input type="text" class="form-control" id="modifyLoginName" readonly="readonly">
                            </div>
                        </div>
                        <br>

                        <div class="row text-right">
                            <label for="modifyPhone" class="col-sm-4 control-label">联系电话:</label>
                            <div class="col-sm-4">
                                <!-- 把  id="MargerPhone"  改成   id="modifyPhone" -->
                                <input type="text" class="form-control" id="modifyPhone">
                            </div>
                        </div>
                        <br>

                        <div class="row text-right">
                            <label for="modifyEmail" class="col-sm-4 control-label">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:</label>
                            <div class="col-sm-4">
                                <!-- 把  id="MargerPhone"  改成   id="modifyPhone" -->
                                <input type="email" class="form-control" id="modifyEmail">
                            </div>
                        </div>
                        <br>

                        <!-- 去掉  联系地址:  -->
                        <!--
                        <div class="row text-right">
                            <label for="MargerAdrees" class="col-sm-4 control-label">联系地址:</label>
                            <div class="col-sm-4">
                                <input type="email" class="form-control" id="modifyAdrees">
                            </div>
                        </div>
                        <br>
                        -->

                        <div class="row text-right">
                            <label for="modifyRole" class="col-sm-4 control-label">角&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;色:</label>
                            <div class=" col-sm-4">
                                <!--  把id="MargerRole"  改成  id="modifyRole" -->
                                <select class="form-control" id="modifyRole">
                                    <option value="-1">--请选择--</option>
                                    <option value="2001">系统管理员</option>
                                    <option value="2002">普通管理员</option>
                                </select>
                            </div>
                        </div>
                        <br>

                        <div class="row text-right">
                            <label for="modifyDept" class="col-sm-4 control-label">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门:</label>
                            <div class=" col-sm-4">
                                <select class="form-control" id="modifyDept">
                                    <option value="-1">--请选择--</option>
                                    <c:forEach items="${enabledDeptList }" var="dept">
                                        <option value="${dept.deptId }">${dept.deptName }</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <!-- 添加id="modifyStaff"  -->
                        <button class="btn btn-warning doMargerModal" id="modifyStaff">修改</button>
                        <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="show-list" style="position: relative; top: 10px;">
            <table class="table table-bordered table-hover" style='text-align: center;'>
                <thead>
                <tr class="text-danger">
                    <th class="text-center">序号</th>
                    <th class="text-center">姓名</th>
                    <th class="text-center">帐号</th>
                    <th class="text-center">电话</th>
                    <th class="text-center">邮箱</th>
                    <th class="text-center">部门</th>
                    <th class="text-center">状态</th>
                    <th class="text-center">角色</th>
                    <th class="text-center">操作</th>
                </tr>
                </thead>
                <tbody id="tb">
                <!-- 和StaffController类中findFuzzyByParams()方法中的modelAndView.addObject("pageStaffList", pageStaffList); 相对应-->
                <c:forEach items="${pageStaffList.list }" var="staff" varStatus="i">
                    <tr>
                        <td>${i.count }</td>
                        <td>${staff.staffName }</td>
                        <td>${staff.loginName }</td>
                        <td>${staff.phone }</td>
                        <td>${staff.email }</td>

                        <td>
                            <c:choose>
                                <c:when test="${empty staff.dept }">--</c:when>
                                <c:otherwise>${staff.dept.deptName }</c:otherwise>
                            </c:choose>
                        </td>

                        <td>
                            <c:if test="${staff.isValid eq 0 }">无效</c:if>
                            <c:if test="${staff.isValid eq 1 }">有效</c:if>
                        </td>

                        <td>
                            <c:if test="${staff.role eq 2001 }">系统管理员</c:if>
                            <c:if test="${staff.role eq 2002 }">普通管理员</c:if>
                        </td>

                        <td class="text-center">

                            <!-- 添加   data-id="${staff.staffId }" -->
                            <input type="button" class="btn btn-warning btn-sm doMangerModify" value="修改"
                                   data-id="${staff.staffId }">

                            <c:if test="${staff.isValid eq 0 }">
                                <!-- 添加
                                data-id="${staff.staffId }"
                                data-status="${staff.isValid }"
                                -->
                                <input type="button" class="btn btn-success btn-sm doMangerDisable" value="启用"
                                       data-id="${staff.staffId }" data-status="${staff.isValid }">
                            </c:if>

                            <c:if test="${staff.isValid eq 1 }">
                                <!-- 添加
                                data-id="${staff.staffId }"
                                data-status="${staff.isValid }"
                                -->
                                <input type="button" class="btn btn-danger btn-sm doMangerDisable" value="禁用"
                                       data-id="${staff.staffId }" data-status="${staff.isValid }">
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <!-- 添加分页 -->
            <ul id="staffPage"></ul>
        </div>
    </div>
</div>
</body>

</html>