package com.ukma.yehor.cs_goodsstorage.model.ServerSide;

import com.ukma.yehor.cs_goodsstorage.model.ProtocolTools.PacketDecryptor;
import com.ukma.yehor.cs_goodsstorage.model.ProtocolTools.Protocol;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class ServerReceiver extends Thread {
    public boolean workFinished = false;
    private byte[] packet;
    private byte[] packInfo;
    private BufferedInputStream is;
    private Processor processor;


    public ServerReceiver(InputStream is, OutputStream os) {
        processor = new Processor(os);
        this.is = new BufferedInputStream(is);
        start();
    }

    public void run() {
        while (processor.isAlive()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
            getBytesFromIS();
        }
        System.out.println("Receiver dead");
    }

    private void getCommand() {
        PacketDecryptor pd = new PacketDecryptor(packet);
        System.out.println("SMS decrypted: " + new String(pd.getMessage()));
        try {
            processor.getCommandsQueue().put(packet);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getBytesFromIS() {
        try {
            packInfo = new byte[14];
            is.read(packInfo, 0, 14);

            int msgLen = msgLen();
            packet = new byte[msgLen + 18];
            System.arraycopy(packInfo, 0, packet, 0, 14);

            is.read(packet, 14, msgLen + 4);

            if (packet[0] == 19)
                getCommand();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public int msgLen() {
        byte[] msgLenBytes = new byte[4];
        System.arraycopy(packInfo, 10, msgLenBytes, 0, 4);
        ByteBuffer wrapped = ByteBuffer.wrap(msgLenBytes);
        return wrapped.getInt();
    }
}
