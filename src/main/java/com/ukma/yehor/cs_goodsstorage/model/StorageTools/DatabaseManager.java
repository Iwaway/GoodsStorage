package com.ukma.yehor.cs_goodsstorage.model.StorageTools;
import java.sql.*;

public class DatabaseManager {
    private static Connection con;
    private static boolean hasData = false;


    private void getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:SQLiteStorage.db");
        initialise();
    }


    private void initialise() throws SQLException {
        if (!hasData) {
            hasData = true;

            Statement state = con.createStatement();
            ResultSet rs = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='storage'");
            if (!rs.next()) {
                Statement state2 = con.createStatement();
                state2.execute("CREATE TABLE storage(id integer, goodName varchar(60), goodCode varchar(60), priceForOne integer, amountOnStorage integer, primary key (id));");
            }
        }
    }


    public void createGood(int id, String name, String code, int price, int amount, int groupID) throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        PreparedStatement ps = con.prepareStatement("INSERT INTO storage values (?,?,?,?,?,?);");
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setString(3, code);
        ps.setInt(4, price);
        ps.setInt(5, amount);
        ps.setInt(6, groupID);
        ps.executeUpdate();
    }

    public void createGroup(int id, String name) throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        PreparedStatement ps = con.prepareStatement("INSERT INTO groups values (?,?);");
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.executeUpdate();
    }

    public ResultSet readGoods() throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("SELECT id, goodName, goodCode, priceForOne, amountOnStorage FROM storage");
        return res;
    }

    public void total() throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("SELECT priceForOne, amountOnStorage FROM storage");
        System.out.println(res.getString(3));
    }


    public void updateGood(int id, String name, String code, int price, int amount) throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        String sql = "UPDATE storage SET goodName = ? , goodCode = ?, priceForOne = ?, amountOnStorage = ? WHERE id = " + id;
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, code);
        ps.setInt(3, price);
        ps.setInt(4, amount);
        ps.executeUpdate();
    }

    public void addGoodAmount(int id, int amount) throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        String sql = "UPDATE storage SET amountOnStorage = amountOnStorage + "+amount+" WHERE id = "+id;
        PreparedStatement ps = con.prepareStatement(sql);
        ps.executeUpdate();
    }

    public void addGoodAmount(String name, int amount) throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        String sql = "UPDATE storage SET amountOnStorage = amountOnStorage + "+amount+" WHERE goodName LIKE '%" + name + "%' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.executeUpdate();
    }

    public void removeGoodAmount(int id, int amount) throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        String sql = "UPDATE storage SET amountOnStorage = amountOnStorage - "+amount+" WHERE id = "+id;
        PreparedStatement ps = con.prepareStatement(sql);
        ps.executeUpdate();
    }

    public void removeGoodAmount(String name, int amount) throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        String sql = "UPDATE storage SET amountOnStorage = amountOnStorage - "+amount+" WHERE goodName LIKE '%" + name + "%' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.executeUpdate();
    }


    public void deleteGoodById(int id) throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        String sql = "DELETE FROM storage WHERE id = " + id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.executeUpdate();
    }

    public void deleteGoodByGroupId(int id) throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        String sql = "DELETE FROM storage WHERE groupId = " + id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.executeUpdate();
    }

    public void deleteGroupById(int id) throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        String sql = "DELETE FROM groups WHERE id = " + id;
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.executeUpdate();
        deleteGoodByGroupId(id);
    }

    public void deleteGoodByName(String name) throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        String sql = "DELETE FROM storage WHERE goodName LIKE '%" + name + "%' ";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.executeUpdate();
    }

    public void editNameById(int idName, String newName) throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        String sql = "UPDATE storage SET goodName = ? WHERE id = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, newName);
        pstmt.setInt(2,idName);
        pstmt.executeUpdate();
    }

    public ResultSet getGoodById(int id) throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        String sql = "SELECT id, goodName, goodCode, priceForOne, amountOnStorage "
                + "FROM storage WHERE id = " + id;
        PreparedStatement pstmt = con.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        return rs;
    }

    public ResultSet getGoodByName(String name) throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        String sql = "SELECT id, goodName, goodCode, priceForOne, amountOnStorage "
                + "FROM storage WHERE goodName LIKE '%" + name + "%' ";
        PreparedStatement pstmt = con.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        return rs;
    }
}