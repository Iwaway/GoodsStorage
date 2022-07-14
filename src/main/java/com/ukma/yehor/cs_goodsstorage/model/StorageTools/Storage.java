package com.ukma.yehor.cs_goodsstorage.model.StorageTools;

import com.sun.tools.javac.util.Pair;
import com.ukma.yehor.cs_goodsstorage.controller.AddGroupController;
import com.ukma.yehor.cs_goodsstorage.model.ProtocolTools.Message;
import com.ukma.yehor.cs_goodsstorage.model.ProtocolTools.PacketDecryptor;
import com.ukma.yehor.cs_goodsstorage.model.ProtocolTools.Protocol;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.LinkedBlockingQueue;

public class Storage extends Thread {
    private static LinkedBlockingQueue<Pair<byte[], OutputStream>> clientQuery;
    private DatabaseManager databaseManager;

    public Storage(){
        databaseManager = new DatabaseManager();
        clientQuery = new LinkedBlockingQueue<>();
        start();
    }

    public void run(){
        while(true){
            try {
                serve(clientQuery.take());
            } catch (InterruptedException | SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void serve(Pair take) throws SQLException, ClassNotFoundException {
        PacketDecryptor pd = new PacketDecryptor((byte[])take.fst);
        int a = pd.getCommandCode();
        String query = new String(pd.getMessage());
        String s = new String(pd.getMessage());
        String[] tokens = s.split(" ");
        switch (tokens[0]){
            case "Get all":

                try {
                    ResultSet rs;
                    rs = databaseManager.readGoods();
                    generateResponse(formatRS(rs), (OutputStream)take.snd);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            break;
            case "AddGood":
                System.out.println("added good");
                int goodId = Integer.parseInt(tokens[4]);
                int price = Integer.parseInt(tokens[3]);
                int amountOfGood = Integer.parseInt(tokens[5]);
                int groupID = Integer.parseInt(tokens[6]);
                try {
                    databaseManager.createGood(goodId,tokens[1], tokens[2], price, amountOfGood, groupID);
                    generateResponse("Good added",(OutputStream)take.snd);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case "RemoveGood":
                System.out.println("removed good");
                try {
                    databaseManager.deleteGoodByName("Coca");
                    generateResponse("Good removed",(OutputStream)take.snd);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case "AddGroup":
                System.out.println("added group");
                try {
                    int groupId = Integer.parseInt(tokens[2]);
                    System.out.println("group");
                    databaseManager.createGroup(groupId, tokens[1]);
                    generateResponse("Group added",(OutputStream)take.snd);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case "AddAmountGood":
                System.out.println("added amount");
                int amount = Integer.parseInt(tokens[2]);
                try {
                    databaseManager.addGoodAmount(tokens[1], amount);
                    generateResponse("Good added",(OutputStream)take.snd);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case "DeleteGood":
                System.out.println("deleted good");
                int deleteGoodId = Integer.parseInt(tokens[1]);
                try {
                    databaseManager.deleteGoodById(deleteGoodId);
                    generateResponse("Good deleted",(OutputStream)take.snd);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case "DeleteGroup":
                System.out.println("deleted group");
                int deleteGroupId = Integer.parseInt(tokens[1]);
                try {
                    databaseManager.deleteGroupById(deleteGroupId);
                    generateResponse("Group deleted",(OutputStream)take.snd);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case "DeleteAmountGood":
                System.out.println("delete amount");
                int deleteAmountOfGood = Integer.parseInt(tokens[2]);
                try {
                    databaseManager.removeGoodAmount(tokens[1], deleteAmountOfGood);
                    generateResponse("Good deleted",(OutputStream)take.snd);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case "EditName":
                System.out.println("edit name");
                int editID = Integer.parseInt(tokens[1]);
                try {
                    databaseManager.editNameById(editID, tokens[2]);
                    generateResponse("Good edited",(OutputStream)take.snd);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;


        }
    }

    private String formatRS(ResultSet rs) {
        String s = "";
        try {
            while (rs.next()) {
                s += rs.getString("goodName") + " " + rs.getString("goodCode") + " " + rs.getInt("priceForOne") + " " + rs.getInt("amountOnStorage");
                s += "\n";
            }
        }catch (SQLException e){
            System.err.println("Error");
        }
        return s;
    }

    private void generateResponse(String s, OutputStream os) {
        Message m = new Message(s,2,1000);
        Protocol p = new Protocol(m,(byte)1);
        try {
            os.write(p.getBytePacket());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static LinkedBlockingQueue<Pair<byte[], OutputStream>> getClientQuery() {
        return clientQuery;
    }
}
