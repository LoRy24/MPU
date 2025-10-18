package dev.lory24.mpu.framework;

import dev.lory24.mpu.framework.packets.client.StatusResponsePacket;
import dev.lory24.mpu.framework.packets.server.HandshakePacket;
import dev.lory24.mpu.framework.packets.server.StatusRequestPacket;
import dev.lory24.mpu.framework.types.ServerListPingResult;
import lombok.experimental.UtilityClass;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

@SuppressWarnings("unused")
@UtilityClass
public class MPU {

    public boolean isServerOnline(String host, int port) {
        return isServerOnline(host, port, 5000);
    }

    public boolean isServerOnline(String host, int port, int timeout) {
        try {
            Socket socket = new Socket();
            socket.setSoTimeout(timeout);
            socket.connect(new InetSocketAddress(host, port));
            socket.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ServerListPingResult getServerListPingResult(String host, int port, int protocolVersion) throws IOException {
        return getServerListPingResult(host, port, protocolVersion, 5000);
    }

    public ServerListPingResult getServerListPingResult(String host, int port, int protocolVersion, int timeout) throws IOException {
        // Create the socket
        Socket socket = new Socket();
        socket.setSoTimeout(timeout);
        socket.setTrafficClass(18);
        socket.connect(new InetSocketAddress(host, port));

        // I/O Stuff
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        // Send the handshake packet
        HandshakePacket handshakePacket = new HandshakePacket(protocolVersion, host, port, HandshakePacket.NextState.STATUS);
        handshakePacket.writePacket(dos);

        // Send the status request packet
        StatusRequestPacket statusRequestPacket = new StatusRequestPacket();
        statusRequestPacket.writePacket(dos);

        // Read the status response packet
        StatusResponsePacket statusResponsePacket = new StatusResponsePacket();
        statusResponsePacket.readPacket(dis);

        // Close the socket
        socket.close();

        // Return the result
        return statusResponsePacket.getJsonResponse();
    }
}
