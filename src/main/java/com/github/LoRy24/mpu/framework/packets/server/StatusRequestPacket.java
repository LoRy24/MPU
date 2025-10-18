package com.github.LoRy24.mpu.framework.packets.server;

import com.github.LoRy24.mpu.framework.packets.AbstractPacket;

import java.io.ByteArrayInputStream;

public class StatusRequestPacket extends AbstractPacket {
    public StatusRequestPacket() {
        super(0x00);
    }

    @Override
    protected void writeData() {
        // No data to write
    }

    @Override
    protected void readData(ByteArrayInputStream inputStream) {
        // No data to write
    }
}
