package com.ukma.yehor.cs_goodsstorage.model.ProtocolTools;

import java.nio.ByteBuffer;

public class Protocol {

    //[A,B,C,C,C,C,C,C,C,C,D,D,D,D,E1,E1,M1,M1,M1,M1,M2,M2,M2,M2,MM,MM,MM,MM,E2,E2]
    private final byte bMagic = 0x13; // Packet begin indicator
    private byte bSrc = 0; // Unique client app id (1 by default)
    private static long bPktId = 0; // Number of message
    private int wLen = 0; // Length of packet (number of bytes used by message)
    private byte[] wCrc16 = new byte[2]; // ProtocolTools.CRC16 of bytes [0,13]
    private byte[] bMsq; // ProtocolTools.Message
    private byte[] wCrc16m = new byte[2]; // ProtocolTools.CRC16 of bytes [16,16+wLen-1]
    private byte[] result; // Final packet

    public Protocol(Message msg, byte bSrc){
        if (msg == null)
            throw new NullPointerException("Seems like message is NULL, nothing to deliver");
        if (bSrc < 1)
            throw new IllegalArgumentException("ClientSide.Client app id must be 1 or higher");
        this.bSrc = bSrc;
        this.wLen = msg.getResultMessageByteArr().length;
        this.bMsq = msg.getResultMessageByteArr();
        result = new byte[18+wLen];
        bPktId++;
        fillByteArray();
    }

    private void fillByteArray() {
        result[0] = bMagic;
        result[1] = bSrc;
        System.arraycopy(longToByteArray(bPktId),0,result,2,longToByteArray(bPktId).length);
        System.arraycopy(intToByteArray(wLen),0,result,10,intToByteArray(wLen).length);
        countPacketInfoCRC();
        System.arraycopy(wCrc16,0,result,14,2);
        System.arraycopy(bMsq,0,result,16,bMsq.length);
        countMessageInfoCRC();
        System.arraycopy(wCrc16m,0,result,16+wLen,2);
    }
    private void countPacketInfoCRC(){
        byte[] toCountCrcData = new byte[14];
        System.arraycopy(result,0,toCountCrcData,0,14);
        wCrc16 = countCRC(toCountCrcData);
        //for (int i = 0; i < wCrc16.length; i++)
        //    System.out.println("CRC val: "+wCrc16[i]);
    }

    private void countMessageInfoCRC(){
        wCrc16m = countCRC(bMsq);
        //for (int i = 0; i < wCrc16m.length; i++)
        //    System.out.println("CRC val: "+wCrc16m[i]);
    }

    private byte[] countCRC(byte[] toCountCrcData) {
        CRC16 crc16 = new CRC16();
        return crc16.getCRC(toCountCrcData);
    }

    private byte[] longToByteArray(long l) {
        byte[] res = ByteBuffer.allocate(8).putLong(l).array();
        return res;
    }

    private byte[] intToByteArray(int i) {
        byte[] res = ByteBuffer.allocate(4).putInt(i).array();
        return res;
    }

    public byte[] getBytePacket(){
        return this.result;
    }

}
