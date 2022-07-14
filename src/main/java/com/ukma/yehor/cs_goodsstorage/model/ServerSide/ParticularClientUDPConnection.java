package com.ukma.yehor.cs_goodsstorage.model.ServerSide;

import com.ukma.yehor.cs_goodsstorage.model.ProtocolTools.PacketDecryptor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;

public class ParticularClientUDPConnection implements Runnable {

    private DatagramSocket clientSocket;
    private DatagramPacket packet;
    private byte[] bytePacket;

    public ParticularClientUDPConnection(DatagramSocket clientSocket, DatagramPacket packet){
        this.clientSocket = clientSocket;
        this.packet = packet;
    }

    @Override
    public void run() {
        byte[] tempPacket = packet.getData();
        byte[] packInfo = new byte[14];
        System.arraycopy(tempPacket,0,packInfo,0,14);

        int msgLen = msgLen(packInfo);
        bytePacket = new byte[msgLen + 18];
        System.arraycopy(packInfo, 0, bytePacket, 0, 14);

        System.arraycopy(tempPacket,14,bytePacket,14,msgLen+4);

        if (bytePacket[0] == 19)
            getCommand();
    }

    public int msgLen(byte[] packInfo) {
        byte[] msgLenBytes = new byte[4];
        System.arraycopy(packInfo, 10, msgLenBytes, 0, 4);
        ByteBuffer wrapped = ByteBuffer.wrap(msgLenBytes);
        return wrapped.getInt();
    }

    private void getCommand() {
        PacketDecryptor pd = new PacketDecryptor(bytePacket);
        System.out.println("SMS decrypted: " + new String(pd.getMessage()));

        int port = packet.getPort();

        byte[] buf = new byte[3];
        String req = new String(pd.getMessage());
        if (req.equals("Put")) {
            String s = "OK!";
            buf = s.getBytes();
        }
        if (req.equals("Get")){
            String s = "NO!";
            buf = s.getBytes();
        }

        DatagramPacket datagramPacketToSend = null;
        datagramPacketToSend = new DatagramPacket(buf, buf.length,
                packet.getAddress(), port);
        try {
            clientSocket.send(datagramPacketToSend);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
