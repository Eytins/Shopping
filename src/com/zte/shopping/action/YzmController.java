package com.zte.shopping.action;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Servlet.CheckyzmServlet", urlPatterns = "/Servlet.CheckyzmServlet")
public class YzmController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        request.setCharacterEncoding("utf-8");
        String      username = request.getParameter("username");
        HttpSession session  = request.getSession();
        String      str      = (String) session.getAttribute("checkCode");
        if (username.equals(str.substring(0, username.length()))) {
            out.print("✔");
        } else {
            out.print("X");
        }
    }
}
