package com.github.LoRy24.mpu.framework.packets.server;

import com.github.LoRy24.mpu.framework.utils.DataTypesUtilities;
import com.github.LoRy24.mpu.framework.packets.AbstractPacket;
import lombok.RequiredArgsConstructor;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * This class represent the Handshake packet, the most important one when talking with a Minecraft server. This determines
 * the action that the connection wants to perform, such as enter the game or just ping the server.
 * <p>
 * So, to start the conversation client <-> Server, this is required.
 *
 * @author LoRy24
 * @version 1.0.0
 *
 * @see AbstractPacket
 */
public class HandshakePacket extends AbstractPacket {

    /**
     * This is the enum which represent the handshake next state value. Each option contains its relative VarInt value that
     * is sent during handshake.
     *
     * @version 1.0.0
     */
    @RequiredArgsConstructor
    public enum NextState {
        STATUS(0x01),
        LOGIN(0x02),
        TRANSFER(0x03);

        private final int value;
    }

    /* Internal Packet Fields */
    private int protocolVersion;
    private String serverAddress;
    private int serverPort;
    private NextState nextState;

    /**
     * The constructor for the Handshake packet which gives the possibility to create a packet with custom fields.
     *
     * @param protocolVersion The protocol version.
     * @param serverAddress The address of the server.
     * @param serverPort The port of the server.
     * @param nextState The Handshake Next state. See the enum for more details.
     */
    public HandshakePacket(int protocolVersion, String serverAddress, int serverPort, NextState nextState) {
        super(0x00);
        this.protocolVersion = protocolVersion;
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.nextState = nextState;
    }

    /**
     * Write packet's data into the buffer.
     *
     * @throws IOException If a writing error occurs
     */
    @Override
    protected void writeData() throws IOException {
        DataTypesUtilities.writeVarInt(this.byteArrayOutputStream, this.protocolVersion);
        DataTypesUtilities.writeString(this.byteArrayOutputStream, this.serverAddress);
        DataTypesUtilities.writeUnsignedShort(this.byteArrayOutputStream, this.serverPort);
        DataTypesUtilities.writeVarInt(this.byteArrayOutputStream, this.nextState.value);
    }

    /**
     * Read the packet's data from an InputStream.
     *
     * @param inputStream The input stream from where to read the data.
     *
     * @throws IOException If a reading error occurs.
     */
    @Override
    protected void readData(ByteArrayInputStream inputStream) throws IOException {
        this.protocolVersion = DataTypesUtilities.readVarInt(inputStream);
        this.serverAddress = DataTypesUtilities.readString(inputStream);
        this.serverPort = DataTypesUtilities.readUnsignedShort(inputStream);
        this.nextState = NextState.values()[DataTypesUtilities.readVarInt(inputStream) - 1];
    }
}
