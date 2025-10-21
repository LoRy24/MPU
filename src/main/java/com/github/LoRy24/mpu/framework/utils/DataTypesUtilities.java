package com.github.LoRy24.mpu.framework.utils;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * This is an internal utility used by the core of this API to send data following the Minecraft Protocol standards. For
 * example, this utility implements the VarInt datatype and special string formatting.
 *
 * @author LoRy24
 * @version 1.0.0
 *
 * @see com.github.LoRy24.mpu.framework.packets.AbstractPacket
 * @see com.github.LoRy24.mpu.framework.MPU
 */
@UtilityClass
public class DataTypesUtilities {
    /* Constant Values */
    private final int SEGMENT_BITS = 0x7F;
    private final int CONTINUE_BIT = 0x80;

    /**
     * This function reads a VarInt value from an InputStream.
     *
     * @param data The InputStream from where to load the value.
     *
     * @return The fetched value.
     *
     * @throws IOException If an error occurs during the reading of the value. For example, if it's too big.
     */
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

    /**
     * This function writes a VarInt value in an OutputStream.
     *
     * @param output The OutputStream where to write the value.
     * @param value The value that has to be written.
     *
     * @throws IOException If an error occurs during the writing of the value.
     */
    public void writeVarInt(OutputStream output, int value) throws IOException {
        while (true) {
            if ((value & ~SEGMENT_BITS) == 0) {
                output.write(value);
                return;
            }

            output.write((value & SEGMENT_BITS) | CONTINUE_BIT);
            value >>>= 7;
        }
    }

    /**
     * This function reads a string from an InputStream.
     *
     * @param data The InputStream from where to read the string.
     *
     * @return The loaded string from the InputStream.
     *
     * @throws IOException If an error occurs during the reading of the value.
     */
    public String readString(InputStream data) throws IOException {
        int length = readVarInt(data);
        return new String(data.readNBytes(length), StandardCharsets.UTF_8);
    }

    /**
     * This function writes a string into an OutputStream.
     *
     * @param output The OutputStream where to write the value.
     * @param value The string to write in the output stream.
     *
     * @throws IOException If an error occurs during the writing of the value.
     */
    public void writeString(OutputStream output, @NotNull String value) throws IOException {
        writeVarInt(output, value.length());
        output.write(value.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * This function reads an unsigned short from an InputStream.
     *
     * @param data The InputStream from where to load the value.
     *
     * @return The red unsigned short.
     *
     * @throws IOException If an error occurs during the reading of the value.
     */
    public int readUnsignedShort(@NotNull InputStream data) throws IOException {
        int high = data.read();
        int low = data.read();
        if (high == -1 || low == -1) {
            return -1;
        }
        return (high << 8) | low;
    }

    /**
     * This function writes an unsigned short to an OutputStream.
     *
     * @param out The OutputStream where to write the value.
     * @param value The value to write.
     *
     * @throws IOException If an error occurs during the writing of the value.
     */
    public void writeUnsignedShort(OutputStream out, int value) throws IOException {
        // Check the range
        if (value < 0 || value > 0xFFFF) {
            throw new IllegalArgumentException("Invalid value: " + value);
        }
        out.write((value >>> 8) & 0xFF);
        out.write(value & 0xFF);
    }
}
