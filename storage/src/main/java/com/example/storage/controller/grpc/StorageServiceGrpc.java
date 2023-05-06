package com.example.storage.controller.grpc;

import com.example.storage.service.StorageService;
import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import io.grpc.example.storage.StorageServiceGrpc.*;
import org.springframework.stereotype.Component;
import java.io.IOException;

@GRpcService
public class StorageServiceGrpc extends StorageServiceImplBase {
    @Autowired
    private StorageService storageService;

    public StorageServiceGrpc() {}

    public StorageServiceGrpc(StorageService storageService) {
        this.storageService = storageService;
    }

    public void start() throws IOException, InterruptedException {
        Server server = NettyServerBuilder.forPort(8081)
                .addService(this)
                .build();
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            server.shutdown();
            System.err.println("*** server shut down");
        }));
        System.out.println("*** gRPC server started on port " + 8081);
        server.awaitTermination();
    }

    @Override
    public void updateFileMeta(io.grpc.example.storage.UpdateFileMetaRequest request,
                               io.grpc.stub.StreamObserver<io.grpc.example.storage.UpdateFileMetaResponse> responseObserver) {
        storageService.updateFileMeta(request.getLink(), request.getId());
        responseObserver.onCompleted();
    }
}