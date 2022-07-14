package com.ukma.yehor.cs_goodsstorage.model.ClientSide;

import com.ukma.yehor.cs_goodsstorage.model.ProtocolTools.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread {

    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private ClientSender clientSender;
    private ClientReceiver clientReceiver;
    private String command;
    private int times;
    private int tried = 0;

    public Client(InetAddress address, Message m){
        this.command = command;
        this.times = times;
        tryConnect(address);
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();
            clientSender = new ClientSender(out);
            clientReceiver = new ClientReceiver(in);
            clientSender.processMessage(m);


            start();
        }
        catch (IOException e) {
            try {
                socket.close();
            }
            catch (IOException e2) {
                System.err.println("Сокет не закрито");
            }
        }
    }

    private void tryConnect(InetAddress address) {
        tried++;
        try {
            socket = new Socket(address, 8080);
        }catch (ConnectException e){
            System.out.println("Server not responding, reconnecting in 5 sec");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            if (tried < 3) {
                tryConnect(address);
            }else {
                throw new IllegalStateException("Connection issues, going offline");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Message generateMessage(String command){
        Message m = new Message(command, 1,1);
        return m;
    }

    public void run(){
        while (clientReceiver.isAlive()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Client "+ Thread.currentThread().getName()+" offline");
    }
}
