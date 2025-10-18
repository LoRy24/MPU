package com.github.LoRy24.mpu.framework.packets;

import com.github.LoRy24.mpu.framework.utils.DataTypesUtilities;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.*;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractPacket {
    /* Internal Packet Fields */
    private final int ID;
    protected ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    protected abstract void writeData() throws IOException;
    protected abstract void readData(ByteArrayInputStream inputStream) throws IOException;

    public void writeID() throws IOException {
        DataTypesUtilities.writeVarInt(this.byteArrayOutputStream, this.ID);
    }

    public void writePacket(DataOutputStream outputStream) throws IOException {
        // Create the packet content
        writeID();
        writeData();

        // Send the length of the frame
        DataTypesUtilities.writeVarInt(outputStream, this.byteArrayOutputStream.size());

        // Send the data
        outputStream.write(byteArrayOutputStream.toByteArray());
    }

    public void readPacket(DataInputStream inputStream) throws IOException {
        int length = DataTypesUtilities.readVarInt(inputStream);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(inputStream.readNBytes(length));
        DataTypesUtilities.readVarInt(byteArrayInputStream); // Skip the ID
        this.readData(byteArrayInputStream);
    }
}
