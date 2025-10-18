package com.github.LoRy24.mpu.framework.types;

import java.util.List;

public record ServerListPlayersList(Integer max, Integer online, List<ServerListPlayer> players) {
}
