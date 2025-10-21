package com.github.LoRy24.mpu.framework.packets;

import com.github.LoRy24.mpu.framework.utils.DataTypesUtilities;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.*;

/**
 * The {@link AbstractPacket} object is the parent of all the Minecraft protocol packets implemented in this framework.
 * This contains all the features that a packet should implement, such as the I/O functions, and some utilities used by
 * the {@link com.github.LoRy24.mpu.framework.MPU} caller class, to perform that actions directly on the socket's streams.
 * <p>
 * Another thing that it contains is a {@link ByteArrayOutputStream} used to write the data of the packet, which will then
 * be framed and sent via the socket.
 *
 * @author LoRy24
 * @version 1.0.0
 *
 * @see com.github.LoRy24.mpu.framework.packets.server.HandshakePacket
 * @see com.github.LoRy24.mpu.framework.packets.client.StatusResponsePacket
 * @see com.github.LoRy24.mpu.framework.packets.server.StatusRequestPacket
 * @see com.github.LoRy24.mpu.framework.MPU
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractPacket {
    /* Internal Packet Fields */
    private final int ID;
    protected ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    /**
     * This function writes the data internally, so if called it writes the data to the 'byteArrayOutputStream' object.
     *
     * @throws IOException If a writing error occurs.
     */
    protected abstract void writeData() throws IOException;

    /**
     * This function reads the data from an InputStream and parses it into the class' internal fields, specific for
     * each packet.
     *
     * @param inputStream The input stream from where to read the data.
     *
     * @throws IOException If a reading exception occurs.
     */
    protected abstract void readData(ByteArrayInputStream inputStream) throws IOException;

    /**
     * This function writes the packet ID (as VarInt) into the internal bytes array output stream.
     *
     * @throws IOException If an error writing the value occurs.
     */
    public void writeID() throws IOException {
        DataTypesUtilities.writeVarInt(this.byteArrayOutputStream, this.ID);
    }

    /**
     * This function is meant to create the frame and send the packet through the socket.
     *
     * @param outputStream The output stream where to write the packet.
     *
     * @throws IOException If an error occurs while writing the data.
     */
    public void writePacket(DataOutputStream outputStream) throws IOException {
        // Create the packet content
        writeID();
        writeData();

        // Send the length of the frame
        DataTypesUtilities.writeVarInt(outputStream, this.byteArrayOutputStream.size());

        // Send the data
        outputStream.write(byteArrayOutputStream.toByteArray());
    }

    /**
     * This function reads the packet frame and passes it to the readData internal function, to make the packet read
     * what it has to.
     *
     * @param inputStream The input stream from where to read the packet.
     *
     * @throws IOException If a reading error occurs.
     */
    public void readPacket(DataInputStream inputStream) throws IOException {
        // Read the frame length
        int length = DataTypesUtilities.readVarInt(inputStream);

        // Read the bytes and skip the ID
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(inputStream.readNBytes(length));
        DataTypesUtilities.readVarInt(byteArrayInputStream);

        // Read the packet
        this.readData(byteArrayInputStream);
    }
}
