package cc.nevsky.otus;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class MyServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder
                .forPort(1111)
                .addService(new ExchangeServiceImpl())
                .build();

        System.out.println("Starting server.");
        server.start();
        server.awaitTermination();
    }
}
