/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlicongviecfx.view;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import quanlicongviecfx.modal.CongViec;

/**
 * FXML Controller class
 *
 * @author DucTien
 */
public class StatisticController implements Initializable {

    @FXML
    private TableView<String> tableWork;
    
    ObservableList<ObservableList> data;
    Connection cnn = null;
    Statement stm = null;
    ResultSet rs = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO      
        data = FXCollections.observableArrayList();
        ShowTable();                
    }

    private void ShowTable() {        
        String sql = "Select THOIGIANKETTHUC,TENCONGVIEC,DOUUTIEN,KHOILUONGHOANTHANH,XEPLOAI from CONGVIEC WHERE KHOILUONGHOANTHANH>50 ORDER BY THOIGIANKETTHUC ASC";
        LoadDataFromDatabase(sql, tableWork);
    }

    /*Đổ dữ liệu vào bảng theo câu truy vấn sql*/
    public void LoadDataFromDatabase(String strSql, TableView tbl) {
        getConnectDB();
        int stt;
        data.clear();
        try {
            stm = (Statement) cnn.createStatement();
            rs = (ResultSet) stm.executeQuery(strSql);
            ResultSetMetaData rsm = rs.getMetaData();
            
            //<editor-fold defaultstate="collapsed" desc="Load dynamic table">
            /**
             * ********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             *********************************
             */
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                String nameCol = rs.getMetaData().getColumnName(i + 1);
                TableColumn col = new TableColumn(nameCol);
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                tbl.getColumns().addAll(col);
                System.out.println("Column [" + i + "] ");
            }

            /**
             * ******************************
             * Data added to ObservableList *
             *******************************
             */
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);

                data.add(row);
            }
//</editor-fold>

            //FINALLY ADDED TO TableView
            tbl.setItems(data);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            CloseConnect();
        }
    }
    
    /*Kết nối csdl*/
    public void getConnectDB() {
        try {
            String uRL = "jdbc:derby://localhost:1527/CongViec";
            String user = "congviec";
            String pass = "Congviec";
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            //Class.forName("org.apache.derby.jdbc.EmbeddedDriver"); 
            cnn = (Connection) DriverManager.getConnection(uRL, user, pass);
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Can't connect to database\n" + e);
        }
    }

    /*Đóng kết nối cơ sở dữ liệu*/
    public void CloseConnect() {
        if (cnn != null) {
            try {
                cnn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }
      
}
