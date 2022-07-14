package com.ukma.yehor.cs_goodsstorage.model.ClientSide;

import java.io.IOException;

public class UDPTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        StoreClientUDP client1 = new StoreClientUDP("Get");
        Thread.sleep(500);
        StoreClientUDP client2 = new StoreClientUDP("Put");
    }
}
