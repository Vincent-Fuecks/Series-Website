package org.series.website.jakarta.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.series.website.jakarta.model.Message;
import org.series.website.jakarta.model.MessageEncoder;
import org.series.website.jakarta.model.Series;
import org.series.website.jakarta.services.SeriesService;

import java.io.IOException;
import java.util.List;

@ServerEndpoint(
        value = "/index-web-socket",
        encoders = { MessageEncoder.class }
)
public class IndexWebSocket {

    @Inject
    private SeriesService seriesService;

    // If WebSocket connection is opend
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Neue WebSocket-Verbindung: " + session.getId());
    }

    // If message from client is resaved
    @OnMessage
    @Produces(MediaType.APPLICATION_JSON)
    public void onMessage(String message, Session session) throws IOException, EncodeException {
            System.out.println(message);
            ObjectMapper objectMapper = new ObjectMapper();
            String[] receivedMessage = objectMapper.readValue(message, String[].class);
            Long seriesId = seriesService.storeSeries(receivedMessage[0], Integer.parseInt(receivedMessage[1]), receivedMessage[2]);
            Message response = new Message("success", seriesService.findById(seriesId));
            session.getBasicRemote().sendObject(response);
    }

    // If WebSocket is closed
    @OnClose
    public void onClose(Session session) {
        System.out.println("Verbindung geschlossen: " + session.getId());
    }
}
