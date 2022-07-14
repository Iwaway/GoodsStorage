package com.ukma.yehor.cs_goodsstorage.controller;

import com.ukma.yehor.cs_goodsstorage.model.ClientSide.Client;
import com.ukma.yehor.cs_goodsstorage.model.ProtocolTools.Message;
import com.ukma.yehor.cs_goodsstorage.SceneNavigator;
import com.ukma.yehor.cs_goodsstorage.services.Vies;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class AddGroupController {

    public TextField nameOfGroup;
    public TextField groupId;

    public void addGoup(ActionEvent actionEvent) throws UnknownHostException {
        Message m = new Message("AddGroup "+nameOfGroup.getText()+" "+groupId.getText()+" ", 3, 3);
        Client client = new Client(InetAddress.getLocalHost(), m);

//        Good good = new Good("Moloko","MM",50);
//
//        String s = "Create Moloko MM 50";
//
//        String[] tokens = s.split(" ");
//        int num = Integer.parseInt(s);
    }

    public void prev(ActionEvent actionEvent) {
        SceneNavigator.getNavigator().navigate(Vies.TableOfGoods);

    }
}
