package com.example.bitcaht.Signaling.Handler;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SocketIOServerRunner implements ApplicationRunner {

    private final SocketIOServer socketIOServer;

    public SocketIOServerRunner(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
    }

    @Override
    public void run(ApplicationArguments args) {
        socketIOServer.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            socketIOServer.stop();
        }));
    }
}