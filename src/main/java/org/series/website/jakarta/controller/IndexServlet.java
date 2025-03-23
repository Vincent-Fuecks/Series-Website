package org.series.website.jakarta.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.series.website.jakarta.dao.SerieRepository;

import java.io.IOException;
import java.io.PrintWriter;

import static org.series.website.jakarta.util.JsonUtils.createTableContent;


@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    private SerieRepository seriesRepository;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[][] tableContent = createTableContent();
        ObjectMapper mapper = new ObjectMapper();
        String jsonData = mapper.writeValueAsString(tableContent);
        System.out.println(jsonData);

        // Set response content type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.print(jsonData);
            out.flush();
        }
    }
}

