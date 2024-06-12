package org.example.turistickivodic.controllers;

import static spark.Spark.*;
import com.google.gson.Gson;
import org.example.turistickivodic.models.Destination;
import org.example.turistickivodic.services.DestinationService;
import org.example.turistickivodic.websocket.NotificationWebSocketServer;

public class DestinationController {
    private static DestinationService destinationService = new DestinationService();
    private static Gson gson = new Gson();

    public static void init(NotificationWebSocketServer socketServer) {
        path("/destinations", () -> {
            get("", (req, res) -> {
                res.type("application/json");
                return gson.toJson(destinationService.getAllDestinations());
            });

            post("", (req, res) -> {
                res.type("application/json");
                Destination destination = gson.fromJson(req.body(), Destination.class);
                System.out.println("Creating destination: " + destination);
                destinationService.createDestination(destination);
                return gson.toJson(destination);
            });

            put("/:id", (req, res) -> {
                res.type("application/json");
                Destination destination = gson.fromJson(req.body(), Destination.class);
                System.out.println("Updating destination: " + destination);
                destinationService.updateDestination(req.params(":id"), destination);
                return gson.toJson(destination);
            });

            delete("/:id", (req, res) -> {
                res.type("application/json");
                System.out.println("Deleting destination with id: " + req.params(":id"));
                destinationService.deleteDestination(req.params(":id"));
                return gson.toJson("Destination deleted");
            });
        });
    }
}
