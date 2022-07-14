package com.ukma.yehor.cs_goodsstorage.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import com.ukma.yehor.cs_goodsstorage.model.ClientSide.Client;
import com.ukma.yehor.cs_goodsstorage.model.ProtocolTools.Message;
import com.ukma.yehor.cs_goodsstorage.SceneNavigator;
import com.ukma.yehor.cs_goodsstorage.services.Vies;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class DeleteGroupController {
    public TextField groupId;

    public void deleteGroup(ActionEvent actionEvent) throws UnknownHostException {
        Message m = new Message("DeleteGroup " + groupId.getText() + " ", 3, 3);
        Client client = new Client(InetAddress.getLocalHost(), m);
    }

    public void prev(ActionEvent actionEvent) {
        SceneNavigator.getNavigator().navigate(Vies.TableOfGoods);

    }
}
