package com.github.LoRy24.mpu.framework.types;

import com.google.gson.JsonElement;

/**
 * This is the main result of a ServerList Ping action. It contains all the data relative to the pinged server, except for
 * the latency one, which will be implemented in the future, maybe in another function.
 *
 * @param version The Minecraft server's version object.
 * @param players The players infos object.
 * @param description The description of the Minecraft server. For newer version, it's a ChatComponent object which should
 *                    be parsed in a successive action. Now it's stored as a common JsonElement.
 * @param favicon The Base64 encoded icon of the server.
 * @param enforcesSecureChat This is a newer versions update, which tells the clients if the server has the secure chat
 *                           enforced. Note that OfflineServers or servers that implements custom chat plugins might remove
 *                           this feature and not telling it to clients.
 *
 * @author LoRy24
 * @version 1.0.0
 *
 * @see ServerListVersion
 * @see ServerListPlayersList
 * @see JsonElement
 * @see com.github.LoRy24.mpu.framework.MPU
 */
public record ServerListPingResult(
        ServerListVersion version,
        ServerListPlayersList players,
        JsonElement description,
        String favicon,
        Boolean enforcesSecureChat
) { }
