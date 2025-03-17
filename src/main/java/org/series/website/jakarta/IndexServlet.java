package org.series.website.jakarta;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;



@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[][] tableContent = createTableContent();
        ObjectMapper mapper = new ObjectMapper();
        String jsonData = mapper.writeValueAsString(tableContent);

        // Set response content type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.print(jsonData);
            out.flush();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[][] tableContent = createTableContent();
        super.doPut(req, resp);
    }

/*
@Path("/add-series")
public class SeriesResource {

    // The POST method to handle adding a new series
    @POST
    @Consumes("application/json") // Expecting JSON data
    public Response addSeries(Series series) {
        // Print out the received series data (just for logging)
        System.out.println("Received series data: " + series);

        // Here you can process the data, such as saving it to a database
        // For example: seriesService.addSeries(series);

        // Send a response back to the client
        return Response.ok("Series added successfully").build();
    }
}
*/


    public static String[][] createTableContent() throws IOException {
        String json = FileUtils.readFileToString(new File("src/main/java/org/series/website/jakarta/data/data.json"), StandardCharsets.UTF_8);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        ElementWrapper elementWrapper = objectMapper.readValue(json, ElementWrapper.class);
        String[][] tableContent = null;
        if (elementWrapper == null) {
            System.out.println("elementWrapper is null");
        } else {
            List<JsonElement> elements = elementWrapper.getElements();
            tableContent = new String[elements.size() + 1][3];
            tableContent[0] = new String[]{"Name", "Rating", "State"};
            for (int i = 0; i < elements.size(); i++){
                JsonElement element = elements.get(i);
                tableContent[i + 1] = new String[]{element.getName(), element.getRating(), element.getSituation()};
            }
        }
        return tableContent;
    }
}

