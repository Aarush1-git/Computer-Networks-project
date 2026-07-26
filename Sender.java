import java.net.*;

public class Sender {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        byte[] msg = "HEARTBEAT:service1".getBytes();
        InetAddress registryIP = InetAddress.getByName("localhost");
        DatagramPacket packet = new DatagramPacket(msg, msg.length, registryIP, 9999);
        socket.send(packet);
    }
}