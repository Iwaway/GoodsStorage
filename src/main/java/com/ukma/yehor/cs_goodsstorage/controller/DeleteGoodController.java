package com.ukma.yehor.cs_goodsstorage.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import com.ukma.yehor.cs_goodsstorage.model.ClientSide.Client;
import com.ukma.yehor.cs_goodsstorage.model.ProtocolTools.Message;
import com.ukma.yehor.cs_goodsstorage.SceneNavigator;
import com.ukma.yehor.cs_goodsstorage.services.Vies;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class DeleteGoodController {
    public TextField nameOfGood;
    public TextField goodId;


    public void deleteGood(ActionEvent actionEvent) throws UnknownHostException {
            Message m = new Message("DeleteGood " + goodId.getText() + " ", 3, 3);
            Client client = new Client(InetAddress.getLocalHost(), m);
    }

    public void prev(ActionEvent actionEvent) {
        SceneNavigator.getNavigator().navigate(Vies.TableOfGoods);

    }
}
