package com.github.LoRy24.mpu.framework.packets.client;

import com.google.gson.Gson;
import com.github.LoRy24.mpu.framework.utils.DataTypesUtilities;
import com.github.LoRy24.mpu.framework.packets.AbstractPacket;
import com.github.LoRy24.mpu.framework.types.ServerListPingResult;
import lombok.Getter;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Getter
public class StatusResponsePacket extends AbstractPacket {
    private final Gson gson = new Gson();
    private ServerListPingResult jsonResponse;

    public StatusResponsePacket() {
        super(0x00);
    }

    @Override
    protected void writeData() throws IOException {
        String response = this.gson.toJson(this.jsonResponse);
        DataTypesUtilities.writeString(this.byteArrayOutputStream, response);
    }

    @Override
    protected void readData(ByteArrayInputStream inputStream) throws IOException {
        String response = DataTypesUtilities.readString(inputStream);
        this.jsonResponse = gson.fromJson(response, ServerListPingResult.class);
    }
}
