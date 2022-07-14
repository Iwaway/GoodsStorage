package com.ukma.yehor.cs_goodsstorage.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import com.ukma.yehor.cs_goodsstorage.SceneNavigator;
import com.ukma.yehor.cs_goodsstorage.services.Vies;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupOfGoodsController {
    public TextField textField;
    public TableView tableView;
    public Label total;
    private ObservableList<ObservableList> data;


    public void addGood(ActionEvent actionEvent) throws UnknownHostException, SQLException, ClassNotFoundException {
        tableView.getItems().clear();
        SceneNavigator.getNavigator().navigate(Vies.AddGood);
//        DatabaseManager dm = new DatabaseManager();
//        dm.total();
    }

    public void delGood(ActionEvent actionEvent) throws UnknownHostException {
        SceneNavigator.getNavigator().navigate(Vies.DeleteGood);
    }

    public void addGroup(ActionEvent actionEvent) throws UnknownHostException {
        SceneNavigator.getNavigator().navigate(Vies.AddGroup);
    }

    public void delGroup(ActionEvent actionEvent) {
        SceneNavigator.getNavigator().navigate(Vies.DeleteGroup);

    }

    public void allGoods(ActionEvent actionEvent) {
        buildData();
    }

    public void addAmount(ActionEvent actionEvent) {
        SceneNavigator.getNavigator().navigate(Vies.AddAmount);
    }

    public void deleteAmount(ActionEvent actionEvent) {
        SceneNavigator.getNavigator().navigate(Vies.DeleteAmount);

    }
    public void buildData(){
        Connection c;
        data = FXCollections.observableArrayList();
        try{
            c = DBConnect.connect();
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = "SELECT * from storage";
            //ResultSet
            ResultSet rs = c.createStatement().executeQuery(SQL);

            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tableView.getColumns().add(col);
                System.out.println("Column ["+i+"] ");
            }

            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added "+row );
                data.add(row);

            }

            //FINALLY ADDED TO TableView
            tableView.setItems(data);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    public void editNameOfGood(ActionEvent actionEvent) {
        SceneNavigator.getNavigator().navigate(Vies.EditNameOfGood);

    }
}
