package org.series.website.jakarta.controller;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;


@WebServlet("index")
public class IndexServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Hello World");
    }
}

