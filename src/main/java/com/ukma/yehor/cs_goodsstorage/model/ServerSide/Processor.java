package com.ukma.yehor.cs_goodsstorage.model.ServerSide;

import com.sun.tools.javac.util.Pair;
import com.ukma.yehor.cs_goodsstorage.model.ProtocolTools.Protocol;
import com.ukma.yehor.cs_goodsstorage.model.StorageTools.Storage;

import java.io.OutputStream;
import java.util.concurrent.LinkedBlockingQueue;

public class Processor extends Thread {
    private LinkedBlockingQueue<byte[]> commandsQueue;
    public boolean workFinished = false;
    private OutputStream os;

    public Processor(OutputStream os) {
        commandsQueue = new LinkedBlockingQueue<byte[]>();
        this.os = os;
        start();
    }

    public void run() {
        try {
            while (!workFinished) {
                process(commandsQueue.take());
            }
            System.out.println("ServerSide.Processor dead");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    //TODO database connection
    private void process(byte[] take) {
        Pair<byte[], OutputStream> clientQuery = new Pair<>(take,os);
        try {
            Storage.getClientQuery().put(clientQuery);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public LinkedBlockingQueue<byte[]> getCommandsQueue() {
        return commandsQueue;
    }
}
