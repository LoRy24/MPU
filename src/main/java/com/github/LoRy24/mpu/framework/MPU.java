package com.github.LoRy24.mpu.framework;

import com.github.LoRy24.mpu.framework.packets.client.StatusResponsePacket;
import com.github.LoRy24.mpu.framework.packets.server.HandshakePacket;
import com.github.LoRy24.mpu.framework.packets.server.StatusRequestPacket;
import com.github.LoRy24.mpu.framework.types.ServerListPingResult;
import lombok.experimental.UtilityClass;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * The {@link MPU} class is the main utility class to interact with any Minecraft server that is reachable from your
 * plugin's host. It contains utility to check the online status of a server, and to get a Server List ping, formatted
 * in the {@link ServerListPingResult} object.
 * <p>
 * Also, in some scenarios, you might need to set a custom timeout value: with the utilities offered by this class, you
 * are allowed to do it.
 *
 * @version 1.0.0
 * @author LoRy24
 *
 * @see ServerListPingResult
 * @see HandshakePacket
 * @see StatusRequestPacket
 * @see StatusResponsePacket
 * @see com.github.LoRy24.mpu.framework.packets.AbstractPacket
 */
@SuppressWarnings("unused")
@UtilityClass
public class MPU {

    /**
     * This function checks if a server is online, so it performs a base socket check setting as default timeout 5000 ms.
     * Uses the {@code isServerOnline(String host, int port, int timeout)} function to achieve the result.
     *
     * @param host The server's host.
     * @param port The server's port.
     *
     * @return If the server is online. No exceptions are thrown.
     */
    public boolean isServerOnline(String host, int port) {
        return isServerOnline(host, port, 5000);
    }

    /**
     * This function, does the same thing as the {@code isServerOnline(String host, int port)} function, but it does that
     * primarily. In fact, this is the function that runs the code to check if the server is online, but it provides the
     * possibility to specify a timeout value.
     *
     * @param host The server's host.
     * @param port The server's port.
     * @param timeout The socket connection timeout value.
     *
     * @return If the server is online.
     */
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

    /**
     * This function performs a ServerList Ping action (see the
     * <a href="https://minecraft.wiki/w/Java_Edition_protocol/Server_List_Ping">Minecraft Protocol Reference</a> for more
     * details) and returns the ping result.
     * <p>
     * The only thing that this function does not return is the server's ping. It will be implemented soon in a new and
     * specific function. It uses the {@code getServerListPingResult(String host, int port, int protocolVersion, int timeout)}
     * function to achieve its result and sets a default timeout value of 5000 ms.
     *
     * @param host The server's host.
     * @param port The server's port.
     * @param protocolVersion The server's protocol version or one used to catch the version issue.
     *
     * @return The result of the ping, parsed in the {@link ServerListPingResult} object.
     *
     * @throws IOException If the connection fails to read the data or to connect.
     */
    public ServerListPingResult getServerListPingResult(String host, int port, int protocolVersion) throws IOException {
        return getServerListPingResult(host, port, protocolVersion, 5000);
    }

    /**
     * This function does the same things as the {@code getServerListPingResult(String host, int port, int protocolVersion)}
     * one, because this is the one called from it. The difference is that with this function you will be able to specify
     * a custom timeout value.
     *
     * @param host The server's host.
     * @param port The server's port.
     * @param protocolVersion The server's protocol version or one used to catch the version issue.
     * @param timeout The custom timeout value.
     *
     * @return The result of the ping, parsed in the {@link ServerListPingResult} object.
     *
     * @throws IOException If the connection fails to read the data or to connect.
     */
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
