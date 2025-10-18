package dev.lory24.mpu.framework;

import dev.lory24.mpu.framework.types.ServerListPingResult;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.io.IOException;

@SuppressWarnings("FieldCanBeLocal")
@Testable
public class MPUTest {
    private final String testHost = "play.magimc.net";
    private final int testPort = 25565;
    private final int testProtocolVersion = 767;
    private final int testTimeout = 250;

    @Test
    public void genericDebugPing() {
        System.out.println("// TEST 1 //");
        try {
            ServerListPingResult serverListPingResult = MPU.getServerListPingResult(testHost, testPort, testProtocolVersion, testTimeout);
            System.out.println("Online Players: " + serverListPingResult.players().online() + "/" + serverListPingResult.players().max());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void genericDebugOnline() {
        System.out.println("// TEST 2 //");
        System.out.println("play.magimc.net is " + (MPU.isServerOnline(testHost, testPort, testTimeout) ? "online" : "offline") + "!");
    }
}