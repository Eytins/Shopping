<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>在线商城管理系统</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css" type="text/css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(function () {
            // 点击 "验证码图片" 切换  验证码
            $("#image").click(function () {
                $(this).attr("src", "${pageContext.request.contextPath}/code/show?id=" + new Date().getTime());
            });

            // 点击 "看不清" 切换  验证码
            $("#changeImg").click(function () {
                $("#image").attr("src", "${pageContext.request.contextPath}/code/show?id=" + new Date().getTime());
            });
            $("#check").keyup("blur", function () {
                $.ajax(
                    {
                        url: "${pageContext.request.contextPath}/code/check",
                        type: "post",
                        success: function (data) {
                            // 验证码不正确,初始化个框
                            if (data !== $("#check").val()) {
                                $("#check").tooltip(
                                    {
                                        title: "请输入正确的验证码",
                                        placement: "top",// left/right/auto/buttom
                                        trigger: "manual"// 手动触发
                                    }).tooltip("show");// 显示
                                $("#check").parent().parent().addClass("has-error");
                            } else {
                                $("#check").parent().parent().removeClass("has-error");
                                $("#check").tooltip('hide');
                            }
                        },
                        dataType: "text"
                    });
            });
            $("#username").on('blur', function () {
                if ($(this).val().trim() === '') {
                    $(this).tooltip(
                        {
                            'title': '用户名不能为空',
                            'placement': 'top',   // top|buttom|left|right|auto
                            'trigger': 'manual'
                        }).tooltip('show'); // (2)tooltip显示

                    $("#username").parent().parent().addClass('has-error');
                } else {
                    $("#username").parent().parent().removeClass('has-error');
                    $("#username").tooltip('hide');
                }
            });
            $("#password").blur(function () {
                if ($(this).val().trim() === '') {
                    $("#password").tooltip(
                        {
                            title: '密码不能为空',
                            placement: 'top',
                            trigger: 'manual'
                        }).tooltip('show');

                    $(this).parent().parent().addClass('has-error');
                } else {
                    $("#password").parent().parent().removeClass('has-error');
                    $("#password").tooltip('hide');
                }

            });
            $("#userRole").blur(function () {
                if ($("#userRole").val().trim() == '') {
                    $("#userRole").tooltip(
                        {
                            title: '请选择身份',
                            placement: 'top',
                            trigger: 'manual'
                        }).tooltip('show');

                    $("#userRole").parent().parent().addClass('has-error');
                } else {
                    $("#userRole").parent().parent().removeClass('has-error');
                    $("#userRole").tooltip('hide');
                }
            });
            // 当用户信息(用户名、密码、身份)校验通过  之后   2秒钟之后关闭提示框tooltip

            $("#login").submit(function () {

                if ($("#username").val().trim() === '') {
                    // (1)初始化tooltip
                    // $("#username").tooltip({}).tooltip('show');
                    $(this).tooltip(
                        {
                            'title': '用户名不能为空',
                            'placement': 'top',   // top|buttom|left|right|auto
                            'trigger': 'manual'
                        }).tooltip('show'); // (2)tooltip显示
                    $("#username").parent().parent().addClass('has-error');
                    return false;  // 阻止表单的默认提交行为
                } else {
                    $("#username").parent().parent().removeClass('has-error');
                    $("#username").tooltip('hide');
                }
                if ($("#password").val().trim() === '') {
                    $(this).tooltip(
                        {
                            title: '密码不能为空',
                            placement: 'top',
                            trigger: 'manual'
                        }).tooltip('show');

                    $("#password").parent().parent().addClass('has-error');
                    return false;
                } else {
                    $("#password").parent().parent().removeClass('has-error');
                    $("#password").tooltip('hide');
                }
                if ($("#userRole").val().trim() === '') {
                    $(this).tooltip(
                        {
                            title: '请选择身份',
                            placement: 'top',
                            trigger: 'manual'
                        }).tooltip('show');
                    $("#userRole").parent().parent().addClass('has-error');
                    return false;
                } else {
                    $("#userRole").parent().parent().removeClass('has-error');
                    $("#userRole").tooltip('hide');
                }

                // 是否可以提交的标志,true代表提交id为frmLogin的表单
                var flag = true;
                // 这里代表验证码时出现的,要验证
                if ($("#check").css("display") !== "none") {
                    $.ajax(
                        {
                            // 这里必须为false,不能异步,只能同步
                            async: false,
                            url: "${pageContext.request.contextPath}/code/check",
                            type: "post",
                            success: function (data) {
                                // 这里判断输入的验证码和显示的验证码一不一致,一致提交表单,不一致就不能提交表单
                                if (data != $("#check").val()) {
                                    flag = false;
                                    $("#check").tooltip({
                                        title: "请输入正确的验证码",
                                        placement: "top",// left/right/auto/buttom
                                        trigger: "manual"// 手动触发
                                    }).tooltip("show");// 显示
                                    $("#s").html("");
                                    $("#check").parent().parent().addClass("has-error");
                                }
                            },
                            dataType: "text"
                        });
                }
                return flag;
            });
        });

    </script>

</head>
<body>
<!-- 使用自定义css样式 div-signin 完成元素居中-->
<div class="containercc div-signin">
    <div class="panel panel-primary div-shadow">
        <!-- h3标签加载自定义样式，完成文字居中和上下间距调整 -->
        <div class="panel-heading">
            <h3>在线商城 3.0</h3>
            <span>Network Mall Manager System</span>
        </div>
        <div class="panel-body">
            <!-- login form start -->
            <form class="form-horizontal ccc" method="post" id="login"
                  action="${pageContext.request.contextPath}/staff/login">
                <div class="form-group">
                    <label class="col-sm-3 control-label">用户名：</label>
                    <div class="col-sm-9">
                        <input class="form-control" type="text" id="username" name="username" placeholder="请输入用户名">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">密&nbsp;&nbsp;&nbsp;&nbsp;码：</label>
                    <div class="col-sm-9">
                        <input class="form-control" type="password" id="password" name="password" placeholder="请输入密码">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">身&nbsp;&nbsp;&nbsp;&nbsp;份：</label>
                    <div class="col-sm-9">
                        <select class="form-control" name="userRole" id="userRole">
                            <option value="">-请选择身份-</option>
                            <option value="2002">普通管理员</option>
                            <option value="2001">超级管理员</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">验证码：</label>
                    <div class="col-sm-4">
                        <input class="form-control" type="text" id="check" name="Code" placeholder="请输入验证码">
                    </div>
                    <div class="col-sm-2">
                        <!-- 验证码图片加载（需引入验证码文件）图像高度经过测试，建议不要修改 -->
                        <img class="img-rounded" id="image" src="${pageContext.request.contextPath}/code/show"
                             style="height: 32px; width: 70px;"/>
                    </div>
                    <div class="col-sm-2">
                        <button type="button" class="btn btn-link" id="changeImg">看不清</button>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-9  col-sm-offset-3 padding-left-0">
                        <div class="col-sm-4">
                            <button type="button" class="btn btn-link btn-block">忘记密码？</button>
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
<!-- 页尾 版权声明 -->
<%--<div class="containercc">
    <div class="col-sm-12 foot-css" id="ccc">
        <p class="text-muted credit">
            Copyright © 2017 南京中兴 版权所有
        </p>
    </div>
</div>--%>
</body>
<script></script>
</html>
