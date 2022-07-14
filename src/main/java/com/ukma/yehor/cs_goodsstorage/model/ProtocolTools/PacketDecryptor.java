package com.ukma.yehor.cs_goodsstorage.model.ProtocolTools;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;

public class PacketDecryptor {
    private byte packetStart = 0x13;
    private byte uniqueClientAppId = 0;
    private long msgNumber = 0;
    private int msgLen = 0;
    private byte[] packetCrc = new byte[2];
    private int commandCode = 0;
    private int userId = 0;
    private byte[] message;
    private byte[] messageCrc = new byte[2];

    private boolean packetCrcValid = false;
    private boolean messageCrcValid = false;

    private byte[] packet;
    private SecretKey key;

    public PacketDecryptor (byte[] packet){
        if (packet.length < 26){
            throw new IllegalArgumentException("Invalid data format!");
        }
        this.packet = packet;
        beginDecryption();
    }

    private void beginDecryption() {
        packetStart = packet[0];
        uniqueClientAppId = packet[1];
        parseMsgNumber();
        parseMsgLen();
        checkPacketCrc();
        parseCommandCode();
        parseUserId();
        parseMessage();
        checkMessageCrc();
    }

    private void checkMessageCrc() {
        byte[] initialCrc = new byte[2];
        initialCrc[0] = packet[packet.length-2];
        initialCrc[1] = packet[packet.length-1];
        byte[] toCountMessageCrc = new byte[msgLen];
        System.arraycopy(packet,16,toCountMessageCrc,0,msgLen);
        messageCrc = countCRC(toCountMessageCrc);
        if (messageCrc[0] != initialCrc[0] || messageCrc[1] != initialCrc[1])
            messageCrcValid = false;
            //throw new IllegalStateException("Packet ProtocolTools.CRC16 is not valid");
        else
            messageCrcValid = true;
    }

    private void parseMessage() {
        byte[] rawMessage = new byte[msgLen-8-32];
        System.arraycopy(packet,24,rawMessage,0,msgLen-8-32);
        SecretKey key2 = new SecretKeySpec(packet, 24+rawMessage.length, 32, "AES");
        message = decryptMessage(key2,rawMessage);
    }

    private void parseUserId() {
        byte[] userIdBytes = new byte[4];
        System.arraycopy(packet,20,userIdBytes,0,4);
        ByteBuffer wrapped = ByteBuffer.wrap(userIdBytes);
        userId  = wrapped.getInt();
    }

    private void parseCommandCode() {
        byte[] commandCodeBytes = new byte[4];
        System.arraycopy(packet,16,commandCodeBytes,0,4);
        ByteBuffer wrapped = ByteBuffer.wrap(commandCodeBytes);
        commandCode  = wrapped.getInt();
    }


    private void checkPacketCrc() {
        byte[] initialCrc = new byte[2];
        initialCrc[0] = packet[14];
        initialCrc[1] = packet[15];
        byte[] toCountPacketCrc = new byte[14];
        System.arraycopy(packet,0,toCountPacketCrc,0,14);
        packetCrc = countCRC(toCountPacketCrc);
        if (packetCrc[0] != initialCrc[0] || packetCrc[1] != initialCrc[1])
            packetCrcValid = false;
            //throw new IllegalStateException("Packet ProtocolTools.CRC16 is not valid");
        else
            packetCrcValid = true;
    }


    private void parseMsgLen() {
        byte[] msgLenBytes = new byte[4];
        System.arraycopy(packet,10,msgLenBytes,0,4);
        ByteBuffer wrapped = ByteBuffer.wrap(msgLenBytes);
        msgLen  = wrapped.getInt();
    }

    private void parseMsgNumber() {
        byte[] msgNumberBytes = new byte[8];
        System.arraycopy(packet,2,msgNumberBytes,0,8);
        ByteBuffer wrapped = ByteBuffer.wrap(msgNumberBytes);
        msgNumber  = wrapped.getLong();
    }

    private byte[] decryptMessage(SecretKey key, byte[] rawMessage) {
        if (rawMessage.length == 0)
            throw new IndexOutOfBoundsException("ProtocolTools.Message body is empty");
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte [] output = cipher.doFinal(rawMessage);
            return output;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private byte[] countCRC(byte[] toCountCrcData) {
        CRC16 crc16 = new CRC16();
        return crc16.getCRC(toCountCrcData);
    }

    public byte getPacketStart() {
        return packetStart;
    }

    public byte getUniqueClientAppId() {
        return uniqueClientAppId;
    }

    public long getMsgNumber() {
        return msgNumber;
    }

    public int getMsgLen() {
        return msgLen;
    }

    public byte[] getPacketCrc() {
        return packetCrc;
    }

    public int getCommandCode() {
        return commandCode;
    }

    public int getUserId() {
        return userId;
    }

    public byte[] getMessage() {
        return message;
    }

    public byte[] getMessageCrc() {
        return messageCrc;
    }

    public boolean isPacketCrcValid() {
        return packetCrcValid;
    }

    public boolean isMessageCrcValid() {
        return messageCrcValid;
    }
}
