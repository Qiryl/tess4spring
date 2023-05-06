package com.example.storage.controller.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GrpcConfiguration {
    @Autowired
    StorageServiceGrpc service;

    private int port = 8081;
    private Server server;

    public void start() throws IOException {
        server = ServerBuilder.forPort(port).addService(service).build().start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            this.stop();
        }));
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public void block() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
}
