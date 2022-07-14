package com.ukma.yehor.cs_goodsstorage.model.ProtocolTools;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.ByteBuffer;

public class Message {
    private int cType; // Command code
    private int bUserId; // User id
    private byte[] message; // ProtocolTools.Message in byte representation (AES-256 ciphered)

    private byte[] resultMessageByteArr; // Command code, user id and ciphered message body in byte array representation
    private SecretKey secretKey; // Secret key in order to decrypt message

    public Message(String msg, int cType, int bUserId){
        if (cType < 1)
            throw new IllegalArgumentException("Incorrect command id");
        if (bUserId < 1)
            throw new IllegalArgumentException("Incorrect user id");
        if (msg.equals(""))
            throw new IllegalArgumentException("ProtocolTools.Message is empty");
        this.cType = cType;
        this.bUserId = bUserId;
        encryptMessage(msg);
        fillByteArray();
    }

    private void encryptMessage(String msg) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            this.secretKey = keyGenerator.generateKey();
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
            message = cipher.doFinal(msg.getBytes());
            //System.out.println("Ciphered message: "+new String(message));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillByteArray() {
        byte[] cTypeArr = intToByteArray(cType);
        byte[] bUserIdArr = intToByteArray(bUserId);
        byte[] keyBytes = secretKey.getEncoded();

        resultMessageByteArr = new byte[4+4+message.length+keyBytes.length];

        System.arraycopy(cTypeArr,0,resultMessageByteArr,0,cTypeArr.length);
        System.arraycopy(bUserIdArr,0,resultMessageByteArr,4,bUserIdArr.length);
        System.arraycopy(message,0,resultMessageByteArr,8,message.length);
        System.arraycopy(keyBytes,0,resultMessageByteArr,8+message.length,keyBytes.length);
    }

    private byte[] intToByteArray(int i) {
        byte[] res = ByteBuffer.allocate(4).putInt(i).array();
        return res;
    }

    public byte[] getResultMessageByteArr() {
        return resultMessageByteArr;
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }
}
