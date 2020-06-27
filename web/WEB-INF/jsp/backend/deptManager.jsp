<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>backend</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/bootstarp/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/index.css"/>
    <script src="${pageContext.request.contextPath }/js/jquery.js" type="text/javascript" charset="utf-8"></script>
    <script src="${pageContext.request.contextPath }/bootstarp/js/bootstrap.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="${pageContext.request.contextPath }/js/userSetting.js" type="text/javascript" charset="utf-8"></script>
    <!-- 引入bootstrap分页插件 -->
    <script src="${pageContext.request.contextPath }/js/bootstrap-paginator.js" type="text/javascript"
            charset="utf-8"></script>

    <script type="text/javascript">

        $(function () {
            var pages;
            var modifydeptname;
            if ("${sysDeptPageInfo.pages }" == 0) {
                pages = 1;
            } else {
                pages = "${sysDeptPageInfo.pages }";
            }

            var options = {
                bootstrapMajorVersion: 3, // bootstrap版本
                currentPage: "${sysDeptPageInfo.pageNum }",
                totalPages: pages,
                size: "normal",
                aligment: "center",
                pageUrl: function (type, page, current) {
                    return "${pageContext.request.contextPath}/dept/findAll?pageNo=" + page;
                }
            };

            $("#deptPage").bootstrapPaginator(options);

            // 跳出 修改部门信息  页面时  根据部门id查询要修改的部门信息
            $("input[name='doModifyDept']").click(function () {
                $.ajax(
                    {
                        type: "post",
                        url: "${pageContext.request.contextPath}/dept/findById",
                        data: {"id": $(this).attr("data-id")},
                        dataType: "json",
                        success: function (result) {
                            if (result.responseCode === 1) {
                                modifydeptname = result.returnObject.deptName;
                                $("#modifyId").val(result.returnObject.deptId);
                                $("#modify-dept-name").val(result.returnObject.deptName);
                                $("#modify-dept-duty").val(result.returnObject.remark);
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

            // 修改部门信息
            $("#updateDept").click(function () {
                $.ajax(
                    {
                        type: "post",
                        url: "${pageContext.request.contextPath}/dept/modifyDept",
                        data: {
                            "redeptName": modifydeptname,
                            "deptId": $("#modifyId").val(),
                            "deptName": $("#modify-dept-name").val(),
                            "remark": $("#modify-dept-duty").val(),
                        },
                        dataType: "json",
                        success: function (result) {
                            if (result.responseCode === 1) {
                                location.href = "${pageContext.request.contextPath}/dept/findAll?pageNo=${sysDeptPageInfo.pageNum }";
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


            $("#addDept").click(function () {
                $.ajax(
                    {
                        type: "post",
                        url: "${pageContext.request.contextPath }/dept/addDept",
                        data:
                            {
                                "deptName": $("#dept-name").val(),
                                "remark": $("#dept-duty").val()
                            },
                        dataType: "json",
                        success: function (result) {
                            if (result.responseCode === 1) {
                                location.href = "${pageContext.request.contextPath }/dept/findAll?pageNo=${sysDeptPageInfo.pageNum }";
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

            $("input[name='doAddSonDept']").click(function () {
                var deptId = $(this).attr("data-id");
                var deptName = $(this).attr("data-value");

                $("#fatherId").val(deptId);
                $("#fatherName").val(deptName);
            });

            $("#addSonDept").click(function () {

                $.ajax(
                    {
                        type: "post",
                        url: "${pageContext.request.contextPath }/dept/addSonDept",
                        data: {
                            "fatherDeptId": $("#fatherId").val(),
                            "deptName": $("#dept-son-name").val(),
                            "remark": $("#dept-son-duty").val()
                        },
                        dataType: "json",
                        success: function (result) {
                            if (result.responseCode === 1) {
                                location.href = "${pageContext.request.contextPath}/dept/findAll?pageNo=${sysDeptPageInfo.pageNum }";
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

            $("input[name='modifyStatus']").click(function () {
                $.ajax(
                    {
                        type: "post",
                        url: "${pageContext.request.contextPath }/dept/modifyStatus",
                        data: {
                            "deptId": $(this).attr("data-id"),
                            "isValid": $(this).attr("data-status")
                        },
                        dataType: "json",
                        success: function (result) {
                            if (result.responseCode === 1) {
                                location.href = "${pageContext.request.contextPath}/dept/findAll?pageNo=${sysDeptPageInfo.pageNum}";
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
                    <div class="modal-body text-center">
                        <div class="row text-right">
                            <label for="dept-name" class="col-sm-4 control-label">部门名称：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="dept-name">
                            </div>
                        </div>
                        <br>
                        <div class="row text-right">
                            <label for="dept-duty" class="col-sm-4 control-label">部门职能：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="dept-duty">
                            </div>
                        </div>
                        <br>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-primary addDept" id="addDept">添加</button>
                        <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- 添加子部门模态框 -->
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
                            <label for="fatherName" class="col-sm-4 control-label">父部门名称：</label>
                            <div class="col-sm-4">
                                <input type="hidden" id="fatherId" />
                                <input type="text" class="form-control" id="fatherName" readonly="readonly">
                            </div>
                        </div>
                        <br>
                        <div class="row text-right">
                            <label for="dept-son-name" class="col-sm-4 control-label">部门名称：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="dept-son-name">
                            </div>
                        </div>
                        <br>
                        <div class="row text-right">
                            <label for="dept-son-duty" class="col-sm-4 control-label">部门职能：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="dept-son-duty">
                            </div>
                        </div>
                        <br>
                    </div>
                    <div class="modal-footer">
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
                            <label for="modifyId" class="col-sm-4 control-label">id:</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="modifyId" readonly="readonly">
                            </div>
                        </div>
                        <br>
                        <div class="row text-right">
                            <label for="modify-dept-name" class="col-sm-4 control-label">部门名称：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="modify-dept-name">
                            </div>
                        </div>
                        <br>
                        <div class="row text-right">
                            <label for="modify-dept-duty" class="col-sm-4 control-label">部门职能：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="modify-dept-duty">
                            </div>
                        </div>
                        <br>
                    </div>
                    <div class="modal-footer">
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
                <c:forEach items="${sysDeptPageInfo.list }" var="dept" varStatus="i">
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

                            <input type="button" class="btn btn-info btn-sm doAddSonDept" value="添加子部门"
                                   name="doAddSonDept" data-id="${dept.deptId}" data-value="${dept.deptName }">


                            <input type="button" class="btn btn-warning btn-sm doModifyDept" value="修改"
                                   name="doModifyDept" data-id="${dept.deptId }">

                            <c:if test="${dept.isValid eq 0 }">
                                <input type="button" class="btn btn-success btn-sm doDisable" value="启用"
                                       name="modifyStatus" data-id="${dept.deptId }" data-status="${dept.isValid }"/>
                            </c:if>

                            <c:if test="${dept.isValid eq 1 }">
                                <input type="button" class="btn btn-danger btn-sm doDisable" value="禁用"
                                       name="modifyStatus" name="modifyStatus" data-id="${dept.deptId }"
                                       data-status="${dept.isValid }"/>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>

</html>