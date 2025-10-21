package com.github.LoRy24.mpu.framework.types;

/**
 * This is the component which contains the name of the server's version and the protocol version number. See the Minecraft
 * protocol wiki for more details.
 *
 * @param name The name of the version.
 * @param protocol The protocol number of it.
 *
 * @author LoRy24
 * @version 1.0.0
 */
public record ServerListVersion(String name, Integer protocol) {
}
