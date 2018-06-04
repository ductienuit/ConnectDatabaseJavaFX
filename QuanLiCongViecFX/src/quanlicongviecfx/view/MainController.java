/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlicongviecfx.view;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import quanlicongviecfx.modal.CongViec;
import quanlicongviecfx.util.Resources;

/**
 *
 * @author DucTien
 */
public class MainController implements Initializable {
    Connection cnn = null;
    Statement stm = null;
    ResultSet rs = null;
    ObservableList<CongViec> data;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setCellValue();        
        data = FXCollections.observableArrayList();
        cmbPriority.setItems(FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20));
        ShowTable();        
        tableWork_ClickRow();        
    }

    private void ShowTable() {        
        String sql = "Select * from CONGVIEC ORDER BY MaCongViec ASC";
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
//            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
//                //We are using non property style for making dynamic table
//                final int j = i;
//                String nameCol = rs.getMetaData().getColumnName(i + 1);
//                TableColumn col = new TableColumn(nameCol);
//                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
//                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
//                        return new SimpleStringProperty(param.getValue().get(j).toString());
//                    }
//                });
//                tbl.getColumns().addAll(col);
//                System.out.println("Column [" + i + "] ");
//            }

            /**
             * ******************************
             * Data added to ObservableList *
             *******************************
             */
//            while (rs.next()) {
//                //Iterate Row
//                ObservableList<CongViec> row = FXCollections.observableArrayList();
//                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
//                    //Iterate Column
//                    row.add(rs.getString(i));
//                }
//                System.out.println("Row [1] added " + row);
//
//                data.add(new CongViec());
//            }
//</editor-fold>


                        /**
             * ******************************
             * Data added to ObservableList *
             *******************************
             */
                        
            while (rs.next()) {                
                //Iterate Row
                CongViec row = new CongViec(rs.getInt("MACONGVIEC"),
                                rs.getString("TENCONGVIEC"),
                                rs.getString("THOIGIANBATDAU"),
                                rs.getString("THOIGIANKETTHUC"),
                                rs.getInt("DOUUTIEN"),
                                rs.getInt("KHOILUONGHOANTHANH"),      
                                rs.getString("GHICHU"));
                System.out.println("Row [1] added " + row);

                data.add(row);
            }

            //FINALLY ADDED TO TableView
            tbl.setItems(data);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            CloseConnect();
        }
    }

    //thuc hien cap nhat bang du lieu- them-sua -xoa
    public boolean UpdateData(String strSql) {
        getConnectDB();
        int row = 0;        
        try {
            stm = (Statement) cnn.createStatement();
            row = stm.executeUpdate(strSql);
            if (row > 0) {  
                return true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error UpdateData: "+ex);
        } finally {
            CloseConnect();
        }
        return false;
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
    
    /*Cài đặt thuộc tính cho các column table*/
    public void setCellValue(){        
        colID.setCellValueFactory(new PropertyValueFactory<>("macongviec"));
        colWork.setCellValueFactory(new PropertyValueFactory<>("tencongviec"));
        colTimeStart.setCellValueFactory(new PropertyValueFactory<>("thoigianbatdau"));
        colTimeFinish.setCellValueFactory(new PropertyValueFactory<>("thoigianketthuc"));
        colPriority.setCellValueFactory(new PropertyValueFactory<>("douutien"));
        colPerComplete.setCellValueFactory(new PropertyValueFactory<>("khoiluonghoanthanh"));
        colLevelComplete.setCellValueFactory(new PropertyValueFactory<>("xeploai"));
        colNote.setCellValueFactory(new PropertyValueFactory<>("ghichu"));
    }
    
    
    private void tableWork_ClickRow() {
        tableWork.setRowFactory(tv -> {
            TableRow<CongViec> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                        if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY 
                             && event.getClickCount() == 2) {
                            CongViec clickedRow = row.getItem();    
                            txtId.setText(String.valueOf(clickedRow.getMacongviec()));
                            txtNameWork.setText(clickedRow.getTencongviec());
                            txtStartTime.setValue(LocalDate.parse(clickedRow.getThoigianbatdau()));
                            txtFinishTime.setValue(LocalDate.parse(clickedRow.getThoigianketthuc()));
                            txtPerComplete.setText(String.valueOf(clickedRow.getKhoiluonghoanthanh()));
                            txtNote.setText(clickedRow.getGhichu());
                            cmbPriority.setValue(clickedRow.getDouutien());
                            System.out.println("Complete"+clickedRow);
                        }
                    });
            return row ;
        });
        
    }
    void clearTextControl()
    {
        txtId.clear();
        txtNameWork.clear();
        txtPerComplete.clear();
        txtNote.clear();
        txtStartTime.setValue(null);
        txtFinishTime.setValue(null);
        cmbPriority.setValue(null);
    }
    
    @FXML
    void btnAdd_Click(ActionEvent event) {
        
        System.out.println("Inserting records into the table...");
                
        CongViec item = getCongViecInControl(); 
        if(item==null)
        {
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập đầy đủ thông tin");
            return;
        }
       
        String add = "INSERT INTO CONGVIEC " +
                   "VALUES ("+ item.getMacongviec()+","
                             +"'" + item.getTencongviec()+"'"       +","
                             +"'" + item.getThoigianbatdau()+"'"    +","
                             +"'"+ item.getThoigianketthuc()+"'"    +","
                             + item.getDouutien()                   +","   
                             + item.getKhoiluonghoanthanh()         +","
                             +"'"+ item.getXeploai()+"'"            +","
                             +"'"+ item.getGhichu()+"'"              
                +")";
        UpdateData(add);                      
        
        System.out.println("Inserted records into the table...");
        clearTextControl();
        tableWork.refresh();
        ShowTable();
    }

    @FXML
    void btnDelete_Click(ActionEvent event) {
        if(txtId.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn hàng cần sửa");
            return; 
        }
        //Delete value
        String delete = "DELETE FROM CONGVIEC.CONGVIEC WHERE MACONGVIEC = "
                + txtId.getText();
        UpdateData(delete);
        clearTextControl();
        tableWork.refresh();
        ShowTable();
    }

    @FXML
    void btnEdit_Click(ActionEvent event) {
        if(txtId.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn hàng cần sửa");
            return; 
        }
        //Update value
        CongViec item = getCongViecInControl();             
       
        String edit = "UPDATE CONGVIEC.CONGVIEC SET "
                + "\"TENCONGVIEC\" = "
                + "'"
                + item.getTencongviec()
                + "'"
                + ", "
                + "\"THOIGIANBATDAU\" = "
                + "'"
                + item.getThoigianbatdau()
                + "'"
                + ", "
                + "\"THOIGIANKETTHUC\" = "
                + "'"
                + item.getThoigianketthuc()
                + "'"
                + ", "
                + "\"DOUUTIEN\" = "
                + item.getDouutien() 
                + ", "
                + "\"KHOILUONGHOANTHANH\" = "
                + item.getKhoiluonghoanthanh() 
                + ", "
                + "\"XEPLOAI\" = "
                + "'"
                + item.getXeploai()
                + "'"
                + ", "
                + "\"GHICHU\" = "
                + "'"
                + item.getGhichu()
                + "' "
                + "WHERE MACONGVIEC = 5";
        UpdateData(edit);
        clearTextControl();
        tableWork.refresh();
        ShowTable();
    }
    
    @FXML
    void btnStatistic_Click(ActionEvent event) throws IOException {
        try{
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            
            //Initialize fxml and create controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Statistic.fxml")); 
            Parent pane = loader.load();

            // Get the Controller from the FXMLLoader
            StatisticController controller = loader.<StatisticController>getController();
            // Set data in the controller
            

            Scene scene = new Scene(pane);   
            stage.setTitle("Thống kê công việc");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) 
        {
            System.out.println(ex);
        }

    }
    
    // <editor-fold defaultstate="collapsed" desc="Name Variables">
    @FXML
    private Button btnStatistic;

    @FXML
    private TextArea txtNote;

    @FXML
    private Button btnEdit;

    @FXML
    private Label label21;

    @FXML
    private AnchorPane main;

    @FXML
    private DatePicker txtFinishTime;

    @FXML
    private Label label;

    @FXML
    private Label label1;

    @FXML
    private TextField txtNameWork;

    @FXML
    private Label label2;

    @FXML
    private Label label12;

    @FXML
    private Label label3;

    @FXML
    private Label label4;

    @FXML
    private TextField txtId;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnAdd;

    @FXML
    private TextField txtPerComplete;

    @FXML
    private DatePicker txtStartTime;

    @FXML
    private TableView<CongViec> tableWork;
    
    @FXML
    private TableColumn<?, ?> colID;
    @FXML
    private TableColumn<?, ?> colWork;
    @FXML
    private TableColumn<?, ?> colPriority;
    @FXML
    private TableColumn<?, ?> colTimeStart;
    @FXML
    private TableColumn<?, ?> colTimeFinish;
    @FXML
    private TableColumn<?, ?> colPerComplete;
    @FXML
    private TableColumn<?, ?> colLevelComplete;
    @FXML
    private TableColumn<?, ?> colNote;
    @FXML
    private ComboBox<Integer> cmbPriority;
    // </editor-fold>

    private CongViec getCongViecInControl() {
        int barcodeWork= data.get(data.size()-1).getMacongviec()+1;
        String nameWork = "";
        String timeStart = "";
        String timeFinish = "";
        try{
            nameWork = txtNameWork.getText();
            timeStart = txtStartTime.getValue().toString();
            timeFinish = txtFinishTime.getValue().toString();
        }
        catch(Exception x)
        {
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập gì đó: "+x);
                return null;
        }
        int priority = -1;
        priority=cmbPriority.getValue();
        System.out.println(priority);
        int perComplete = 0;
        
        try{
            perComplete = Integer.parseInt(txtPerComplete.getText());
            if(perComplete<0 | perComplete>100)
            {   
                JOptionPane.showMessageDialog(null, perComplete + " Khong phai so hoac khogn thuoc pham vi 0-100");
                return null;
            }
        }
        catch(NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(null, "Khong phai so: " + ex);
        }    
        
        
        String note = txtNote.getText();  
        
        return new CongViec(barcodeWork,nameWork,timeStart,timeFinish,priority,perComplete,note);
    }

}
