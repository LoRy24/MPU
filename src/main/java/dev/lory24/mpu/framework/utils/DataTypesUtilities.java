package dev.lory24.mpu.framework.utils;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;

@UtilityClass
public class DataTypesUtilities {
    private final int SEGMENT_BITS = 0x7F;
    private final int CONTINUE_BIT = 0x80;

    public int readVarInt(@NotNull InputStream data) throws IOException {
        int value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = (byte) data.read();
            value |= (currentByte & SEGMENT_BITS) << position;

            if ((currentByte & CONTINUE_BIT) == 0) break;

            position += 7;

            if (position >= 32) throw new RuntimeException("VarInt is too big");
        }

        return value;
    }

    public void writeVarInt(OutputStream data, int value) throws IOException {
        while (true) {
            if ((value & ~SEGMENT_BITS) == 0) {
                data.write(value);
                return;
            }

            data.write((value & SEGMENT_BITS) | CONTINUE_BIT);
            value >>>= 7;
        }
    }

    public String readString(InputStream data) throws IOException {
        int length = readVarInt(data);
        return new String(data.readNBytes(length), StandardCharsets.UTF_8);
    }

    public void writeString(OutputStream data, @NotNull String value) throws IOException {
        writeVarInt(data, value.length());
        data.write(value.getBytes(StandardCharsets.UTF_8));
    }

    public void writeUnsignedShort(OutputStream out, int value) throws IOException {
        if (value < 0 || value > 0xFFFF) {
            throw new IllegalArgumentException("Invalid value: " + value);
        }
        out.write((value >>> 8) & 0xFF);
        out.write(value & 0xFF);
    }

    public  int readUnsignedShort(@NotNull InputStream in) throws IOException {
        int high = in.read();
        int low = in.read();
        if (high == -1 || low == -1) {
            return -1; // End of stream
        }
        return (high << 8) | low;
    }
}
