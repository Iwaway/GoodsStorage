package com.ukma.yehor.cs_goodsstorage.model.ServerSide;

import com.ukma.yehor.cs_goodsstorage.model.StorageTools.Storage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class StoreServerTCP {
    static final int PORT = 8080;
    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(PORT);
        System.out.println("Сервер запущено.");

        Storage storage = new Storage();
        System.out.println("Склад ініціалізовано.");

        try {
            while (true) {
                Socket socket = s.accept();
                new ParticularClientServer(socket);
            }
        } finally {
            s.close();
        }
    }
}
