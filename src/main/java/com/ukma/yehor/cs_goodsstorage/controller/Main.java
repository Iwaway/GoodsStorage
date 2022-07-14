package com.ukma.yehor.cs_goodsstorage.controller;

import javafx.application.Application;
import javafx.stage.Stage;
import com.ukma.yehor.cs_goodsstorage.SceneNavigator;
import com.ukma.yehor.cs_goodsstorage.services.Vies;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        SceneNavigator navigator = SceneNavigator.init(primaryStage);
        navigator.navigate(Vies.TableOfGoods);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}