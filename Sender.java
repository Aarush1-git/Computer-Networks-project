import java.net.*;

public class Sender {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress registryIP = InetAddress.getByName("localhost");

        while (true) {
            byte[] msg = "HEARTBEAT:service1".getBytes();
            DatagramPacket packet = new DatagramPacket(msg, msg.length, registryIP, 9999);
            socket.send(packet);
            System.out.println("Sent heartbeat");
            Thread.sleep(2000);
        }
    }
}