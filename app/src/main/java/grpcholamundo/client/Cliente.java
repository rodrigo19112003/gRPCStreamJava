package grpcholamundo.client;

import com.proto.saludo.GreetingServiceGrpc;
import com.proto.saludo.Holamundo.GreetingRequest;
import com.proto.saludo.Holamundo.GreetingResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class Cliente {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 8080;

        ManagedChannel managedChannel = ManagedChannelBuilder
            .forAddress(host, port)
            .usePlaintext()
            .build();

        // greetUnary(managedChannel);

        // greetStream(managedChannel);

        fileStream(managedChannel);

        System.out.println("Apagando...");
        managedChannel.shutdown();
    }

    public static void greetUnary(ManagedChannel managedChannel) {
        GreetingServiceGrpc.GreetingServiceBlockingStub stub = GreetingServiceGrpc.newBlockingStub(managedChannel);
        GreetingRequest request = GreetingRequest.newBuilder().setName("Rodrigo").build();
        GreetingResponse response = stub.greeting(request);

        System.out.println("Respuesta RPC: " + response.getResult());
    }

    public static void greetStream(ManagedChannel managedChannel) {
        GreetingServiceGrpc.GreetingServiceBlockingStub stub = GreetingServiceGrpc.newBlockingStub(managedChannel);
        GreetingRequest request = GreetingRequest.newBuilder().setName("Rodrigo").build();

        stub.greetingStream(request).forEachRemaining(response -> {
            System.out.println("Respuesta RPC: " + response.getResult());
        });
    }

    public static void fileStream(ManagedChannel managedChannel) {
        GreetingServiceGrpc.GreetingServiceBlockingStub stub = GreetingServiceGrpc.newBlockingStub(managedChannel);
        GreetingRequest request = GreetingRequest.newBuilder().setName("archivote.csv").build();

        stub.fileStream(request).forEachRemaining(response -> {
            System.out.print(response.getResult());
        });
    }
}


