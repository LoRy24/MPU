package com.github.LoRy24.mpu.framework.packets.server;

import com.github.LoRy24.mpu.framework.packets.AbstractPacket;

import java.io.ByteArrayInputStream;

/**
 * This class represents the StatusRequest packet of the Minecraft protocol. It is sent after a completed Handshake where
 * the NextState value is set to STATUS.
 *
 * @author LoRy24
 * @version 1.0.0
 *
 * @see AbstractPacket
 * @see HandshakePacket
 */
public class StatusRequestPacket extends AbstractPacket {

    /**
     * The constructor for the packet that sets its ID.
     */
    public StatusRequestPacket() {
        super(0x00);
    }

    /**
     * Write the data of the packet (nothing)
     */
    @Override
    protected void writeData() {
        // No data to write
    }

    /**
     * Reads the data from the packet frame (nothing)
     *
     * @param inputStream The input stream from where to read the data.
     */
    @Override
    protected void readData(ByteArrayInputStream inputStream) {
        // No data to write
    }
}
