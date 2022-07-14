package com.ukma.yehor.cs_goodsstorage.model.ClientSide;

import com.ukma.yehor.cs_goodsstorage.model.ProtocolTools.Message;
import com.ukma.yehor.cs_goodsstorage.model.ProtocolTools.Protocol;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class StoreClientUDP extends Thread {
    DatagramSocket datagramSocket;
    String command;
    InetAddress ip;
    byte[] buf = new byte[512];


    public StoreClientUDP(String command) throws IOException {
        this.command = command;
        try {
            datagramSocket = new DatagramSocket();
            ip = InetAddress.getLocalHost();
        }catch (Exception e){
            e.printStackTrace();
        }
        start();
    }

    public void run() {
        while (true) {
            Message m = new Message(command, 2, 1);
            Protocol p = new Protocol(m, (byte) 1);


            DatagramPacket datagramPacketToSend =
                    new DatagramPacket(p.getBytePacket(), p.getBytePacket().length, ip, 8081);

            try {
                datagramSocket.send(datagramPacketToSend);
            } catch (IOException e) {
                e.printStackTrace();
            }

            buf = new byte[512];
            DatagramPacket datagramPacketToReceive =
                    new DatagramPacket(buf, buf.length);
            try {
                datagramSocket.receive(datagramPacketToReceive);
            } catch (IOException e) {
                e.printStackTrace();
            }


            System.out.println(Thread.currentThread().getName() + " answer = " + new String(buf, 0, 3));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
