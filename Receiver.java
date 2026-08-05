import java.net.*;
import java.util.concurrent.*;
import java.util.Map;

public class Receiver {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(9999);
        byte[] buffer = new byte[1024];

        ConcurrentHashMap<String, Long> lastHeartbeat = new ConcurrentHashMap<>();

        // Watchdog thread: checks for dead services every 1s
        Thread watchdog = new Thread(() -> {
            while (true) {
                long now = System.currentTimeMillis();
                for (Map.Entry<String, Long> entry : lastHeartbeat.entrySet()) {
                    if (now - entry.getValue() > 6000) {
                        System.out.println(entry.getKey() + " is DEAD");
                        lastHeartbeat.remove(entry.getKey());
                    }
                }
                try { Thread.sleep(1000); } catch (InterruptedException e) {}
            }
        });
        watchdog.start();

        // Main thread: keeps receiving heartbeats
        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            String received = new String(packet.getData(), 0, packet.getLength());
            String serviceId = received.split(":")[1];
            lastHeartbeat.put(serviceId, System.currentTimeMillis());
        }
    }
}