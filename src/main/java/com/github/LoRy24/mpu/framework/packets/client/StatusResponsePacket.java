package com.github.LoRy24.mpu.framework.packets.client;

import com.google.gson.Gson;
import com.github.LoRy24.mpu.framework.utils.DataTypesUtilities;
import com.github.LoRy24.mpu.framework.packets.AbstractPacket;
import com.github.LoRy24.mpu.framework.types.ServerListPingResult;
import lombok.Getter;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * This class represents the StatusResponse packet of the Minecraft protocol, which is sent after the
 * {@link com.github.LoRy24.mpu.framework.packets.server.StatusRequestPacket} one. It contains a JSON string which
 * represents the status content.
 *
 * @author LoRy24
 * @version 1.0.0
 *
 * @see com.github.LoRy24.mpu.framework.packets.server.StatusRequestPacket
 */
@Getter
public class StatusResponsePacket extends AbstractPacket {
    /* Internal fields and JSON to format the response */
    private final Gson gson = new Gson();
    private ServerListPingResult jsonResponse;

    /**
     * The constructor which passes the packet ID.
     */
    public StatusResponsePacket() {
        super(0x00);
    }

    /**
     * Writes the packet's data, taken from the fields of this class.
     *
     * @throws IOException If a writing error occurs.
     */
    @Override
    protected void writeData() throws IOException {
        String response = this.gson.toJson(this.jsonResponse);
        DataTypesUtilities.writeString(this.byteArrayOutputStream, response);
    }

    /**
     * This function reads the response from the InputStream.
     *
     * @param inputStream The input stream from where to read the data.
     *
     * @throws IOException If a reading exception occurs
     */
    @Override
    protected void readData(ByteArrayInputStream inputStream) throws IOException {
        String response = DataTypesUtilities.readString(inputStream);
        this.jsonResponse = gson.fromJson(response, ServerListPingResult.class);
    }
}
