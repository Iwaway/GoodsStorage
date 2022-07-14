package com.ukma.yehor.cs_goodsstorage.model.ServerSide;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ParticularClientServer extends Thread {

    private InputStream in;
    private OutputStream out;
    private ServerReceiver serverReceiver;
    private Socket socket;

    public ParticularClientServer(Socket socket) {
        System.out.println("New client serving");
        this.socket = socket;
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();
            serverReceiver = new ServerReceiver(in,out);
        }catch (IOException ex){
           ex.printStackTrace();
        }
        start();
    }

    public void run() {
        while (serverReceiver.isAlive()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Stop serving client");
        try {
            socket.close();
            System.out.println("Socket closed");
        } catch (IOException e2) {
            System.err.println("Сокет не закрито");
        }
    }
}
