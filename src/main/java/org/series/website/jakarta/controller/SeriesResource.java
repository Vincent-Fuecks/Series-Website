package org.series.website.jakarta.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.series.website.jakarta.model.Series;
import org.series.website.jakarta.services.SeriesService;

import java.util.List;

@Path("/series")
public class SeriesResource {

    @Inject
    private SeriesService seriesService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listSeries() {
        List<Series> seriesList = seriesService.list();
        return Response.ok(seriesList).build();
    }
}
