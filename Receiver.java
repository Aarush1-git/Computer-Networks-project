import java.net.*;
import java.util.concurrent.*;

public class Receiver {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(9999);
        byte[] buffer = new byte[1024];

        ConcurrentHashMap<String, Long> lastHeartbeat = new ConcurrentHashMap<>();

        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            String received = new String(packet.getData(), 0, packet.getLength());

            String serviceId = received.split(":")[1]; // "service1"
            lastHeartbeat.put(serviceId, System.currentTimeMillis());

            System.out.println(serviceId + " -> " + lastHeartbeat.get(serviceId));
        }
    }
}