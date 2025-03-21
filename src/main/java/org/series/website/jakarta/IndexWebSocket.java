package org.series.website.jakarta;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;

@ServerEndpoint("/index")
public class IndexWebSocket {
    private JsonFileManager jsonFileManager;

    public IndexWebSocket() throws IOException {
        this.jsonFileManager = new JsonFileManager("/home/vincent/Desktop/Repository/Series-Website/Series-Website/src/main/java/org/series/website/jakarta/data/data.json");
    }

    // If WebSocket connection is opend
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Neue WebSocket-Verbindung: " + session.getId());
    }

    // If message from client is resaved
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String[][] receivedMessage = objectMapper.readValue(message, String[][].class);

            this.jsonFileManager.jsonUpdater(receivedMessage);
            session.getBasicRemote().sendText("{\"status\":\"success\", \"message\":\"Server added Series to Storage\"}");
        } catch (IOException e) {
            session.getBasicRemote().sendText("Error processing the message");
        }
    }

    // If WebSocket is closed
    @OnClose
    public void onClose(Session session) {
        System.out.println("Verbindung geschlossen: " + session.getId());
    }
}
