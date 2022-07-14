package com.ukma.yehor.cs_goodsstorage.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import com.ukma.yehor.cs_goodsstorage.model.ClientSide.Client;
import com.ukma.yehor.cs_goodsstorage.model.ProtocolTools.Message;
import com.ukma.yehor.cs_goodsstorage.SceneNavigator;
import com.ukma.yehor.cs_goodsstorage.services.Vies;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class AddGoodController {
    public TextField nameOfGood;
    public TextField codeOfGood;
    public TextField countOfGood;
    public TextField priceOfGoods;
    public TextField goodId;
    public TextField amountOfGoods;
    public TextField groupId;


    public void addGood(ActionEvent actionEvent) throws UnknownHostException {
        Message m = new Message("AddGood "+nameOfGood.getText() + " "+codeOfGood.getText()+ " "+priceOfGoods.getText()+" "+goodId.getText()+ " "+ amountOfGoods.getText()+ " " + groupId.getText()+ " ", 3, 3);
        InetAddress addr = InetAddress.getByName("172.20.10.7");
        Client client = new Client(InetAddress.getLocalHost(), m);
    }

    public void prev(ActionEvent actionEvent) {
        SceneNavigator.getNavigator().navigate(Vies.TableOfGoods);

    }
}
