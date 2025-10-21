package com.github.LoRy24.mpu.framework.types;

import java.util.List;

/**
 * This is the component which contains all the data relative to the online players of the network, storing the current
 * amount, the maximum one and a list containing all the connected players. Note that some plugins might replace it with
 * a custom message. So, in that case, it will return that, but stored as they were players.
 *
 * @param max The maximum amount of online players.
 * @param online The amount of connected players to the server.
 * @param players The list of the online players.
 *
 * @author LoRy24
 * @version 1.0.0
 */
public record ServerListPlayersList(Integer max, Integer online, List<ServerListPlayer> players) {
}
