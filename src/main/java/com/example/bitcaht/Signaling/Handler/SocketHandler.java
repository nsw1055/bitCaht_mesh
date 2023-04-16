package com.example.bitcaht.Signaling.Handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.example.bitcaht.Signaling.model.Room;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class SocketHandler {
    private final SocketIOServer server;

    public SocketHandler(SocketIOServer server) {
        this.server = server;
        this.server.addConnectListener(onConnect);
        this.server.addDisconnectListener(onDisconnect);
        this.server.addEventListener("join_room", Room.class, onJoinRoom());
    }

    public ConnectListener onConnect = new ConnectListener() {
        @Override
        public void onConnect(SocketIOClient client) {
            log.info("Perform operation on user connect in controller");
        }
    };

    public DisconnectListener onDisconnect = new DisconnectListener() {
        @Override
        public void onDisconnect(SocketIOClient client) {
            log.info("Perform operation on user disconnect in controller");
        }
    };

    public DataListener<Room> onJoinRoom() {
        return (client, data, ackSender) -> {
            Room room = Room.builder()
                    .roomId(data.getRoomId())
                    .userId(data.getUserId())
                    .build();
        };
    }
    @OnEvent("message")
    public void onMessage(SocketIOClient client, String message) {
        System.out.println("Received message from client " + client.getSessionId() + ": " + message);
        server.getBroadcastOperations().sendEvent("message", message);
    }

    @OnEvent("offer")
    public void onOffer(SocketIOClient client, String roomName) {
        server.getRoomOperations(roomName).sendEvent("getOffer", client);
    }
}
