package com.ukma.yehor.cs_goodsstorage.model.ClientSide;

import com.ukma.yehor.cs_goodsstorage.model.ProtocolTools.Message;
import com.ukma.yehor.cs_goodsstorage.model.ProtocolTools.Protocol;

import java.io.IOException;
import java.io.OutputStream;

public class ClientSender extends Thread {
    private OutputStream out;
    public ClientSender(OutputStream out){
        this.out = out;
    }

    public void processMessage(Message m){
        Protocol p = new Protocol(m,(byte)1);
        sendMessage(p.getBytePacket());
    }

    private void sendMessage(byte[] bytePacket) {
        try {
            out.write(bytePacket);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
