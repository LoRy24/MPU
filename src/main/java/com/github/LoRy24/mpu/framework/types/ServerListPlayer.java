package com.github.LoRy24.mpu.framework.types;

/**
 * This is the element that is stored in the players list contained in the {@link ServerListPlayersList} record.
 *
 * @param name The name of the player.
 * @param id It's UUID.
 *
 * @author LoRy24
 * @version 1.0.0
 */
public record ServerListPlayer(String name, String id) {
}
