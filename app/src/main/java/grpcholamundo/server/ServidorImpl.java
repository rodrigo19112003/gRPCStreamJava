package grpcholamundo.server;

import com.proto.saludo.Holamundo.GreetingRequest;
import com.proto.saludo.Holamundo.GreetingResponse;
import java.util.Scanner;
import com.proto.saludo.GreetingServiceGrpc;
import io.grpc.stub.StreamObserver;

public class ServidorImpl extends GreetingServiceGrpc.GreetingServiceImplBase {
    
    @Override
    public void greeting (GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {
        GreetingResponse response = GreetingResponse.newBuilder().setResult("Hola " + request.getName()).build();

        responseObserver.onNext(response);

        responseObserver.onCompleted();
    }

    @Override
    public void greetingStream(GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {
        for (int i = 0; i <= 10; i++) {
            GreetingResponse response = GreetingResponse.newBuilder()
                .setResult("Hola " + request.getName() + " por " + i + " vez.").build();

                responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void fileStream(GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {
        String fileName = "/" + request.getName();

        try (Scanner scanner = new Scanner(ServidorImpl.class.getResourceAsStream(fileName))) {
            while (scanner.hasNextLine()) {
                GreetingResponse response = GreetingResponse.newBuilder()
                    .setResult(scanner.nextLine()).build();
                
                    responseObserver.onNext(response);
                System.out.print(".");
            }
        }
        responseObserver.onCompleted();
    }
}


