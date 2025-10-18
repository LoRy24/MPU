package com.github.LoRy24.mpu.framework.packets.server;

import com.github.LoRy24.mpu.framework.utils.DataTypesUtilities;
import com.github.LoRy24.mpu.framework.packets.AbstractPacket;
import lombok.RequiredArgsConstructor;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class HandshakePacket extends AbstractPacket {

    @RequiredArgsConstructor
    public enum NextState {
        STATUS(0x01),
        LOGIN(0x02),
        TRANSFER(0x03);

        private final int value;
    }

    private int protocolVersion;
    private String serverAddress;
    private int serverPort;
    private NextState nextState;

    public HandshakePacket(int protocolVersion, String serverAddress, int serverPort, NextState nextState) {
        super(0x00);
        this.protocolVersion = protocolVersion;
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.nextState = nextState;
    }

    @Override
    protected void writeData() throws IOException {
        DataTypesUtilities.writeVarInt(this.byteArrayOutputStream, this.protocolVersion);
        DataTypesUtilities.writeString(this.byteArrayOutputStream, this.serverAddress);
        DataTypesUtilities.writeUnsignedShort(this.byteArrayOutputStream, this.serverPort);
        DataTypesUtilities.writeVarInt(this.byteArrayOutputStream, this.nextState.value);
    }

    @Override
    protected void readData(ByteArrayInputStream inputStream) throws IOException {
        this.protocolVersion = DataTypesUtilities.readVarInt(inputStream);
        this.serverAddress = DataTypesUtilities.readString(inputStream);
        this.serverPort = DataTypesUtilities.readUnsignedShort(inputStream);
        this.nextState = NextState.values()[DataTypesUtilities.readVarInt(inputStream) - 1];
    }
}
