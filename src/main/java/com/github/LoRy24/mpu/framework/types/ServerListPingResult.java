package com.github.LoRy24.mpu.framework.types;

import com.google.gson.JsonElement;

public record ServerListPingResult(
        ServerListVersion version,
        ServerListPlayersList players,
        JsonElement description,
        String favicon,
        Boolean enforcesSecureChat
) { }
