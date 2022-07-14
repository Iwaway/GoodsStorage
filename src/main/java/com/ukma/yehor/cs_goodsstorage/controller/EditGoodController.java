package com.ukma.yehor.cs_goodsstorage.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import com.ukma.yehor.cs_goodsstorage.model.ClientSide.Client;
import com.ukma.yehor.cs_goodsstorage.model.ProtocolTools.Message;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class EditGoodController {
    public TextField nameOfGood;
    public TextField newName;

    public void editNameOfGood(ActionEvent actionEvent) throws UnknownHostException {
        Message m = new Message("EditName " + nameOfGood.getText() + " " + newName.getText() + " ", 3, 3);
        Client client = new Client(InetAddress.getLocalHost(), m);
    }
}
