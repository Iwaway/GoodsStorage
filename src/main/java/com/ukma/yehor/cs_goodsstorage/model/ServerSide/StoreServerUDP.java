package com.ukma.yehor.cs_goodsstorage.model.ServerSide;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class StoreServerUDP {

    public static void main(String[] args) throws SocketException {
        DatagramSocket serverSocket = new DatagramSocket(8081);
        System.out.println("UDP server started.");
        byte[] buffer = new byte[512];

        while (true)
        {
            try
            {
                DatagramPacket packet =  new DatagramPacket(buffer, buffer.length );
                serverSocket.receive(packet);
                System.out.println("SERVER: Accepted connection.");

                DatagramSocket threadSocket = new DatagramSocket();
                Thread t = new Thread(new ParticularClientUDPConnection(serverSocket, packet));
                t.start();

            } catch (Exception e)
            {
                System.err.println("Error in connection attempt.");
            }
        }
    }

}
