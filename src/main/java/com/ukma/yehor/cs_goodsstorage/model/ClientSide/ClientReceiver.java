package com.ukma.yehor.cs_goodsstorage.model.ClientSide;

import com.ukma.yehor.cs_goodsstorage.model.ProtocolTools.PacketDecryptor;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class ClientReceiver extends Thread {

    public boolean workFinished = false;
    private byte[] packet;
    private byte[] packInfo;
    private BufferedInputStream is;

    public ClientReceiver(InputStream is){
        this.is = new BufferedInputStream(is);
        start();
    }

    public void run() {
        while (!workFinished) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getBytesFromIS();
        }
        System.out.println("ClientSide.Client receiver dead");
    }

    private void getCommand() {
        PacketDecryptor pd = new PacketDecryptor(packet);
        if (new String(pd.getMessage()).equals("Finish confirmed")){
            workFinished = true;
        }
        System.out.println(new String(pd.getMessage()));
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
