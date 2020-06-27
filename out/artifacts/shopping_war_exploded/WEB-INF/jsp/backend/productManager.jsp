<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="zh">
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
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/bootstrap-paginator.js"></script>
    <style>
        .file {
            position: relative;
            display: inline-block;
            background: #D0EEFF;
            border: 1px solid #99D3F5;
            border-radius: 4px;
            padding: 4px 12px;
            overflow: hidden;
            color: #1E88C7;
            text-decoration: none;
            text-indent: 0;
            line-height: 20px;
            width: 100%;
            text-align: center;
        }

        .file input {
            position: absolute;
            font-size: 100px;
            right: 0;
            top: 0;
            opacity: 0;
        }

        .file:hover {
            background: #AADFFD;
            border-color: #78C3F3;
            color: #004974;
            text-decoration: none;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            var pages;

            if ("${sysProductPageInfo.pages }" === 0) {
                pages = 1;
            } else {
                pages = "${sysProductPageInfo.pages }";
            }

            var options =
                {
                    bootstrapMajorVersion: 3,  // bootstrap版本
                    currentPage: "${sysProductPageInfo.pageNum }",
                    totalPages: pages,
                    aligment: "center",
                    pageUrl: function (type, page, current) {
                        return "${pageContext.request.contextPath }/product/findAll?pageNo=" + pages;
                    }
                };
            //配置分页
            $("#productPage").bootstrapPaginator(options);
        });

        // 添加商品
        function addProduct() {
            $("#addFrmProduct").submit();
        }

        $(function () {
            // 修改商品
            $("input[name='toModify']").click(function () {
                $.ajax(
                    {
                        type: "post",
                        url: "${pageContext.request.contextPath}/product/findById",
                        data: {"id": $(this).attr("data-value")},
                        dataType: "json",
                        success: function (result) {
                            if (result.responseCode == 1) {
                                // alert(result.returnObject.name);
                                $("#modifyId").val(result.returnObject.productId);
                                $("#modifyName").val(result.returnObject.name);
                                $("#modifyPrice").val(result.returnObject.price);
                                $("#modifyTypeId").find("option[value='" + result.returnObject.productType.id + "']").attr("selected", true);
                            }
                        }
                    });
            });

        });

    </script>
</head>

<body>
<div class="panel panel-default" id="userPic">
    <div class="panel-heading">
        <h3 class="panel-title">商品管理</h3>
    </div>
    <div class="panel-body">
        <input type="button" value="添加商品" class="btn btn-primary" id="doAddPro">
        <div class="modal fade" tabindex="-1" id="Product">
            <!-- 窗口声明 -->
            <div class="modal-dialog modal-lg">
                <!-- 内容声明 -->
                <div class="modal-content">
                    <!-- 头部、主体、脚注 -->
                    <div class="modal-header">
                        <button class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">添加商品</h4>
                    </div>
                    <form action="${pageContext.request.contextPath }/product/addProduct" method="post" id="addFrmProduct" enctype="multipart/form-data">
                        <div class="modal-body text-center">

                            <!-- 添加  商品编号 div  -->
                            <div class="row text-right">
                                <label for="product-name" class="col-sm-4 control-label">商品编号</label>
                                <div class="col-sm-4">

                                    <!--  添加 name="productNo"-->
                                    <input type="text" class="form-control" id="productNo" name="productNo">

                                </div>
                            </div>

                            <br/>

                            <div class="row text-right">
                                <label for="product-name" class="col-sm-4 control-label">商品名称:</label>
                                <div class="col-sm-4">

                                    <!--  添加 一个隐藏域 用于传递pageNo(用户点击的是第几页) 到后台ProductController类中的addProduct()方法中去 -->
                                    <input type="hidden" name="pageNo" value="${sysProductPageInfo.pageNum }" />

                                    <!--  添加 name="productName"-->
                                    <input type="text" class="form-control" id="product-name" name="productName">

                                </div>
                            </div>
                            <br>
                            <div class="row text-right">
                                <label for="product-price" class="col-sm-4 control-label">商品价格:</label>
                                <div class="col-sm-4">
                                    <!--  添加 name="price"-->
                                    <input type="text" class="form-control" id="product-price" name="price">
                                </div>
                            </div>
                            <br>

                            <div class="row text-right">
                                <label for="product-image" class="col-sm-4 control-label">商品图片:</label>
                                <div class="col-sm-4">
                                    <a href="javascript:" class="file">选择文件
                                        <input type="file" name="file" id="file">
                                    </a>
                                </div>
                            </div>
                            <br>
                            <div class="row text-right">
                                <label for="product-type" class="col-sm-4 control-label">商品类型:</label>
                                <div class="col-sm-4">
                                    <!-- 添加 name="typeId" -->
                                    <select class="form-control" name="typeId">
                                        <!-- 添加  value = "-1" -->
                                        <option value = "-1">--请选择--</option>
                                        <c:forEach items="${typeList }" var="type">
                                            <option value="${type.id }">${type.name }</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <br>
                        </div>
                    </form>
                    <div class="modal-footer">
                        <input type="submit" class="btn btn-primary addProduct" value="添加" onclick="addProduct();"/>
                        <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
                    </div>
                </div>
            </div>
        </div>
        <br>
        <br>
        <div class="show-list">
            <table class="table table-bordered table-hover" style='text-align: center;'>
                <thead>
                <tr class="text-danger">
                    <th class="text-center">编号</th>
                    <th class="text-center">商品</th>
                    <th class="text-center">价格</th>
                    <th class="text-center">产品类型</th>
                    <th class="text-center">状态</th>
                    <th class="text-center">操作</th>
                </tr>
                </thead>
                <tbody id="tb">
                <c:forEach items="${sysProductPageInfo.list }" var="p" varStatus="i">
                    <tr>
                        <td>${i.index + 1 }</td>
                        <td>${p.name }</td>
                        <td>${p.price }</td>
                        <td>${p.sysProductType.name }</td>
                        <td>
                            <c:if test="${p.sysProductType.status eq 1 }">有效商品</c:if>
                            <c:if test="${p.sysProductType.status eq 0 }">无效商品</c:if>
                        </td>
                        <td class="text-center">
                            <!-- 添加 name="toModify" 和 data-value="${p.productId }" -->
                            <input type="button" class="btn btn-warning btn-sm doProModify" value="修改" name="toModify" data-value="${p.productId }">

                            <!-- <input type="button" class="btn btn-danger btn-sm doProDisable" value="禁用"> -->
                            <!-- 注释掉上一行的input标签  添加   href="${pageContext.request.contextPath }/product/removeById?id=${p.productId }&pageNo=${sysProductPageInfo.pageNum }" -->
                            <a class="btn btn-danger btn-sm doProDisable" href="${pageContext.request.contextPath }/product/removeById?id=${p.productId }&pageNo=${sysProductPageInfo.pageNum }">删除</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 添加: 分页 ul -->
            <ul id="productPage">
            </ul>
        </div>
        <div class="modal fade" tabindex="-1" id="myProduct">
            <!-- 窗口声明 -->
            <div class="modal-dialog modal-lg">
                <!-- 内容声明 -->
                <div class="modal-content">
                    <!-- 头部、主体、脚注 -->
                    <div class="modal-header">
                        <button class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">商品修改</h4>
                    </div>
                    <div class="modal-body text-center">
                        <div class="row text-right">
                            <label for="pro-num" class="col-sm-4 control-label">序号：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="pro-num">
                            </div>
                        </div>
                        <br>
                        <div class="row text-right">
                            <label for="pro-name" class="col-sm-4 control-label">商品名称</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="pro-name">
                            </div>
                        </div>
                        <br>
                        <div class="row text-right">
                            <label for="pro-price" class="col-sm-4 control-label">商品价格：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="pro-price">
                            </div>
                        </div>
                        <br>
                        <div class="row text-right">
                            <label for="pro-state" class="col-sm-4 control-label">商品状态：</label>
                            <div class="col-sm-4">
                                <input type="email" class="form-control" id="pro-state">
                            </div>
                        </div>
                        <br>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-primary updatePro">修改</button>
                        <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>