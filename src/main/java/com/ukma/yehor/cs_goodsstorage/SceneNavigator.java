package com.ukma.yehor.cs_goodsstorage;

import com.ukma.yehor.cs_goodsstorage.controller.*;
import com.ukma.yehor.cs_goodsstorage.services.Vies;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneNavigator {

    private static SceneNavigator navigator;
    private Scene tableOfGoods;
    private Scene addGroup;
    private Scene addGood;
    private Scene addAmount;
    private Scene deleteGood;
    private Scene deleteGroup;
    private Scene deleteAmount;
    private Scene editNameOfGood;
    private Stage window;
    private GroupOfGoodsController groupOfGoodsController;
    private AddGroupController addGroupController;
    private AddGoodController addGoodController;
    private AddAmountOfGoodsController addAmountOfGoodsController;
    private DeleteGoodController deleteGoodController;
    private DeleteGroupController deleteGroupController;
    private DeleteAmountController deleteAmountController;
    private EditGoodController editGoodController;

    public static SceneNavigator init(Stage window) throws IOException {
        if (navigator != null) {
            throw new IllegalStateException("Navigator is already initialized !");
        }

        FXMLLoader loader = new FXMLLoader(SceneNavigator.class.getResource("GroupOfGoods.fxml"));
        Scene tableOfGoods = new Scene((Parent) loader.load());
        GroupOfGoodsController groupOfGoodsController = loader.getController();

        loader = new FXMLLoader(SceneNavigator.class.getResource("AddGroup.fxml"));
        Scene addGroup = new Scene((Parent) loader.load());
        AddGroupController addGroupController = loader.getController();

        loader = new FXMLLoader(SceneNavigator.class.getResource("AddGood.fxml"));
        Scene addGood = new Scene((Parent) loader.load());
        AddGoodController addGoodController = loader.getController();

        loader = new FXMLLoader(SceneNavigator.class.getResource("AddAmountOfGoods.fxml"));
        Scene addAmount = new Scene((Parent) loader.load());
        AddAmountOfGoodsController addAmountOfGoodsController = loader.getController();

        loader = new FXMLLoader(SceneNavigator.class.getResource("DeleteGood.fxml"));
        Scene deleteGood = new Scene((Parent) loader.load());
        DeleteGoodController deleteGoodController = loader.getController();

        loader = new FXMLLoader(SceneNavigator.class.getResource("DeleteGroup.fxml"));
        Scene deleteGroup = new Scene((Parent) loader.load());
        DeleteGroupController deleteGroupController = loader.getController();

        loader = new FXMLLoader(SceneNavigator.class.getResource("DeleteAmount.fxml"));
        Scene deleteAmount = new Scene((Parent) loader.load());
        DeleteAmountController deleteAmountController = loader.getController();

        loader = new FXMLLoader(SceneNavigator.class.getResource("editGood.fxml"));
        Scene editNameOfGoods = new Scene((Parent) loader.load());
        EditGoodController editGoodController = loader.getController();


        navigator = new SceneNavigator(window, tableOfGoods, groupOfGoodsController,addGroupController, addGroup, addGood, addGoodController, addAmount, addAmountOfGoodsController, deleteGood, deleteGoodController, deleteGroup, deleteGroupController, deleteAmount, deleteAmountController, editNameOfGoods, editGoodController);
        return navigator;
    }

    public void navigate(Vies view) {
        switch (view) {
            case TableOfGoods:
                window.setMinWidth(500);
                window.setMaxWidth(500);
                window.setMinHeight(450);
                window.setWidth(500);
                window.setHeight(450);
                window.setScene(tableOfGoods);
                break;
            case AddGroup:
                window.setMinWidth(600);
                window.setMinHeight(450);
                window.setWidth(600);
                window.setHeight(450);
                window.setScene(addGroup);
                break;
            case AddGood:
                window.setMinWidth(600);
                window.setMinHeight(450);
                window.setWidth(600);
                window.setHeight(450);
                window.setScene(addGood);
                break;
            case AddAmount:
                window.setMinWidth(600);
                window.setMinHeight(450);
                window.setWidth(600);
                window.setHeight(450);
                window.setScene(addAmount);
                break;
            case DeleteGood:
                window.setMinWidth(600);
                window.setMinHeight(450);
                window.setWidth(600);
                window.setHeight(450);
                window.setScene(deleteGood);
                break;
            case DeleteGroup:
                window.setMinWidth(600);
                window.setMinHeight(450);
                window.setWidth(600);
                window.setHeight(450);
                window.setScene(deleteGroup);
                break;
            case DeleteAmount:
                window.setMinWidth(600);
                window.setMinHeight(450);
                window.setWidth(600);
                window.setHeight(450);
                window.setScene(deleteAmount);
                break;
            case EditNameOfGood:
                window.setMinWidth(600);
                window.setMinHeight(450);
                window.setWidth(600);
                window.setHeight(450);
                window.setScene(editNameOfGood);
                break;
            default:
                throw new IllegalArgumentException("Such view is not still implemented !");
        }
        window.centerOnScreen();
    }

    public static SceneNavigator getNavigator() {
        if (navigator == null) {
            throw new IllegalStateException("Can't give non-initialized navigator !");
        }
        return navigator;
    }

    public GroupOfGoodsController getGroupOfGoodsController() {
        if (navigator == null) {
            throw new IllegalStateException("Navigator is not instantiated !");
        }
        return navigator.groupOfGoodsController;
    }

    public AddGroupController getAddGroupController() {
        if (navigator == null) {
            throw new IllegalStateException("Navigator is not instantiated !");
        }
        return navigator.addGroupController;
    }

    public AddGoodController getAddGoodController() {
        if (navigator == null) {
            throw new IllegalStateException("Navigator is not instantiated !");
        }
        return navigator.addGoodController;
    }
    public AddAmountOfGoodsController getAddAmountOfGoodsController() {
        if (navigator == null) {
            throw new IllegalStateException("Navigator is not instantiated !");
        }
        return navigator.addAmountOfGoodsController;
    }

    public DeleteGoodController getDeleteGoodController() {
        if (navigator == null) {
            throw new IllegalStateException("Navigator is not instantiated !");
        }
        return navigator.deleteGoodController;
    }

    public DeleteGroupController getDeleteGroupController() {
        if (navigator == null) {
            throw new IllegalStateException("Navigator is not instantiated !");
        }
        return navigator.deleteGroupController;
    }
    public DeleteAmountController getDeleteAmountController() {
        if (navigator == null) {
            throw new IllegalStateException("Navigator is not instantiated !");
        }
        return navigator.deleteAmountController;
    }

    public EditGoodController getEditGooController() {
        if (navigator == null) {
            throw new IllegalStateException("Navigator is not instantiated !");
        }
        return navigator.editGoodController;
    }


    public Stage getWindow() {
        if (navigator == null) {
            throw new IllegalStateException("Navigator is not instantiated !");
        }
        return navigator.window;
    }

    private SceneNavigator(Stage window, Scene tableOfGoods, GroupOfGoodsController groupOfGoodsController, AddGroupController addGroupController, Scene addGroup, Scene addGood, AddGoodController addGoodController, Scene addAmount, AddAmountOfGoodsController addAmountOfGoodsController, Scene deleteGood, DeleteGoodController deleteGoodController, Scene deleteGroup, DeleteGroupController deleteGroupController, Scene deleteAmount, DeleteAmountController deleteAmountController, Scene editNameOfGood, EditGoodController editGoodController) {
        this.window = window;
        this.groupOfGoodsController = groupOfGoodsController;
        this.tableOfGoods = tableOfGoods;
        this.addGroup = addGroup;
        this.addGroupController = addGroupController;
        this.addGood = addGood;
        this.addGoodController = addGoodController;
        this.addAmount = addAmount;
        this.addAmountOfGoodsController = addAmountOfGoodsController;
        this.deleteGood = deleteGood;
        this.deleteGoodController = deleteGoodController;
        this.deleteGroup = deleteGroup;
        this.deleteGroupController = deleteGroupController;
        this.deleteAmount = deleteAmount;
        this.deleteAmountController = deleteAmountController;
        this.editNameOfGood = editNameOfGood;
        this.editGoodController = editGoodController;
    }
}