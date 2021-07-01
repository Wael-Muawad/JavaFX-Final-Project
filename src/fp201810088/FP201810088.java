package fp201810088;

import Models.Course;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FP201810088 extends Application {

    private Label lblInt, lblString, lblDouble, lblDate;
    private TextField txtInt, txtString, txtDouble;
    private DatePicker date;
    private Button btnNext, btnPrevious, btnFirst, btnLast;
    private Button btnCreat, btnDelete, btnSort, btnUpdate, btnRetrive;
    private HBox navigationBox, operationBox, hboxDouble, hboxDate;
    private GridPane root;
    private Connection con;
    private Text txtSQL;
    ////////////////////////////////////////////////////////////////////////////////
    private int listIndex = 0;
    private ArrayList<Course> courseList;
    Predicate<Course> condition;
    private EventHandler<ActionEvent> eventCreatConnection,
            eventFirst, eventPrevious, eventNext, eventLast,
            eventRetrive, eventCreat, eventUpdate, eventDelete, eventSort,
            eventGetDate;

    @Override
    public void init() {
        eventCreatConnection = e -> CreatConnection();

        eventRetrive = e -> OnRetrive(e);
        eventCreat = e -> OnCreat(e);
        eventUpdate = e -> OnUpdate(e);
        eventDelete = e -> OnDelete(e);
        eventSort = e -> OnSort(e);

        eventFirst = e -> OnFirst(e);
        eventPrevious = e -> OnPrevious(e);
        eventNext = e -> OnNext(e);
        eventLast = e -> OnLast(e);

        eventGetDate = e -> OnSettingDate(e);

        /////////////////////////////////////////////////////////////////////////////////////
        txtInt = new TextField();
        txtInt.setDisable(true);

        txtString = new TextField();

        txtDouble = new TextField();
        txtDouble.setPrefWidth(210);

        date = new DatePicker(LocalDate.of(2020, Month.JUNE, 18));
        date.setPrefWidth(210);
        date.setEditable(false);
        date.addEventHandler(ActionEvent.ACTION, eventGetDate);
//    LocalDate datevalue = date.getValue();
        /////////////////////////////////////////////////////////////////////////////////////
        lblInt = new Label("_IntAttribute");
        lblInt.setMnemonicParsing(true);
        lblInt.setLabelFor(txtInt);

        lblString = new Label("_StringAttribute");
        lblString.setMnemonicParsing(true);
        lblString.setLabelFor(txtString);

        lblDouble = new Label("_DoubleAttribute");
        lblDouble.setMnemonicParsing(true);
        lblDouble.setLabelFor(txtDouble);

        lblDate = new Label("_LocalDateAttribute");
        lblDate.setMnemonicParsing(true);
        lblDate.setLabelFor(date);
        
        txtSQL = new Text();
        /////////////////////////////////////////////////////////////////////////////////////
        btnFirst = new Button("|<");
        btnFirst.setPrefSize(95, 20);
        btnFirst.addEventHandler(ActionEvent.ACTION, eventFirst);

        btnLast = new Button(">|");
        btnLast.setPrefSize(95, 20);
        btnLast.addEventHandler(ActionEvent.ACTION, eventLast);

        btnNext = new Button(">");
        btnNext.setPrefSize(95, 20);
        btnNext.addEventHandler(ActionEvent.ACTION, eventNext);

        btnPrevious = new Button("<");
        btnPrevious.setPrefSize(95, 20);
        btnPrevious.addEventHandler(ActionEvent.ACTION, eventPrevious);

        /////////////////////////////////////////////////////////////////////////////////////
        btnRetrive = new Button("_Retrieve");
        btnRetrive.setDefaultButton(true);
        btnRetrive.setPrefSize(120, 20);
        btnRetrive.addEventHandler(ActionEvent.ACTION, eventRetrive);
        btnRetrive.addEventHandler(ActionEvent.ACTION, eventCreatConnection);

        btnCreat = new Button("_Creat");
        btnCreat.setPrefSize(120, 20);
        btnCreat.addEventHandler(ActionEvent.ACTION, eventCreat);

        btnUpdate = new Button("_Update");
        btnUpdate.setPrefSize(120, 20);
        btnUpdate.addEventHandler(ActionEvent.ACTION, eventUpdate);

        btnDelete = new Button("_Delete");
        btnDelete.setPrefSize(120, 20);
        btnDelete.addEventHandler(ActionEvent.ACTION, eventDelete);

        btnSort = new Button("_Sort");
        btnSort.setPrefSize(120, 20);
        btnSort.addEventHandler(ActionEvent.ACTION, eventSort);

        /////////////////////////////////////////////////////////////////////////////////////
        hboxDouble = new HBox(10, txtDouble, lblDate, date);

        navigationBox = new HBox(10, btnFirst, btnPrevious, btnNext, btnLast);

        operationBox = new HBox(10, btnRetrive, btnCreat, btnUpdate, btnDelete, btnSort);

        courseList = new ArrayList();

        /////////////////////////////////////////////////////////////////////////////////////
        root = new GridPane();
        root.setPadding(new Insets(50, 100, 50, 100));
        root.setVgap(10);
        root.setHgap(10);
        root.addRow(0, lblInt, txtInt);
        root.addRow(1, lblString, txtString);
        root.add(lblDouble, 0, 2, 2, 1);
        root.add(hboxDouble, 1, 2, 4, 1);
        root.add(navigationBox, 0, 5, 2, 1);
        navigationBox.setAlignment(Pos.CENTER);
        root.add(operationBox, 0, 7, 2, 1);
        root.add(txtSQL, 0, 11, 2, 1);
//        root.setGridLinesVisible(true);
    }

    @Override
    public void start(Stage primaryStage) {

        Scene scene = new Scene(root);

        primaryStage.setTitle("Hello World!");
        primaryStage.setWidth(1010);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

   
    public static void main(String[] args) {
        launch(args);
    }

    private void CreatConnection() {
        if (con == null) {
            con = DatabaseHelper.getConnection();
            String message = "Connection done succuessfully!";
            Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
            alert.show();
        }
    }

    //////////////////////////////////////////////////////////////////////////////// 
    private void SetData(Course course) {
        txtInt.setText(String.valueOf(course.getIntAttribute()));
        txtDouble.setText(String.valueOf(course.getDoubleAttribute()));
        txtString.setText(course.getStringAttribute());
        date.setValue(course.getLocalDateAttribute().getValue());
    }

    private Course GetData() {
        if (txtInt.getText().equals("")) {
            return null;
        }
        
        int intAtr = Integer.parseInt(txtInt.getText());
        String stringAtr = txtString.getText();
        double doubleAtr = Double.parseDouble(txtDouble.getText());

        Course getcourse = Course.GetNew(intAtr);
        getcourse.setDoubleAttribute(doubleAtr);
        getcourse.setStringAttribute(stringAtr);
        getcourse.setLocalDateAttribute(date);

        return getcourse;
    }

    ////////////////////////////////////////////////////////////////////////////////
    private void OnRetrive(ActionEvent e) {
        ////////////////////////////////////////////////////////////////////////////////    
        ///////// retrive all the data from database and add them in the list //////////
        ////////////////////////////////////////////////////////////////////////////////    
        String sqlSelect = "SELECT "
                + "IntAttribute, "
                + "StringAttribute, "
                + "DoubleAttribute, "
                + "LocalDateAttribute "
                + "FROM course";

        if (con == null) {
            con = DatabaseHelper.getConnection();
        }
        try {
            ResultSet rs = DatabaseHelper.getStatement(DatabaseHelper.getConnection()).executeQuery(sqlSelect);
            
            courseList.clear();
            while (rs.next()) {
                Course tempCourse = Course.GetNew(-1);
                tempCourse.setIntAttribute(rs.getInt("IntAttribute"));
                tempCourse.setStringAttribute(rs.getString("StringAttribute"));
                tempCourse.setDoubleAttribute(rs.getDouble("DoubleAttribute"));
                DatePicker tempDate = new DatePicker();
                tempDate.setValue((rs.getDate("LocalDateAttribute")).toLocalDate());
                tempCourse.setLocalDateAttribute(tempDate);

                courseList.add(tempCourse);
            }
            rs.close();
            
            try {
                txtSQL.setText("Data Retrived Succuessfully!");
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(FP201810088.class.getName()).log(Level.SEVERE, null, ex);
            }
            txtSQL.setText(null);
            
            OnFirst(null);
            
        } catch (SQLException ex) {
            Logger.getLogger(FP201810088.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, ex.getMessage(), ButtonType.OK);
            alert.show();
        }

    }

    private void OnCreat(ActionEvent e) {
        ////////////////////////////////////////////////////////////////////////////////    
        ////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////

        String sqlInsert = "INSERT INTO course(StringAttribute, DoubleAttribute, LocalDateAttribute) "
                + " VALUES ('"
                + " "
                + "',"
                + 0.0
                + ",'"
                + date.getValue()
                + "' )";
        if (con == null) {
            con = DatabaseHelper.getConnection();
        }
        try {
            int r = DatabaseHelper.getStatement(DatabaseHelper.getConnection()).executeUpdate(sqlInsert);
            if (r == 1) {
                String message = "New Course Added Succuessfully In Database!\n\n( Please insert your data then press 'Update' )";
                Alert myMessage = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
                myMessage.show();

                int newID = GetPK();
                courseList.add(Course.GetNew(newID));
                
                
            try {
                txtSQL.setText("Data Retrived Succuessfully!");
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(FP201810088.class.getName()).log(Level.SEVERE, null, ex);
            }
            txtSQL.setText(null);
            
                OnLast(null);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FP201810088.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, ex.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    private void OnUpdate(ActionEvent e) {
        if (GetData() == null) {
            return;
        }      
        courseList.set(listIndex, GetData());
        Course tempCourse = courseList.get(listIndex);

        String sqlUpdate = "UPDATE course SET StringAttribute='"
                + tempCourse.getStringAttribute()
                + "',DoubleAttribute="
                + tempCourse.getDoubleAttribute()
                + ",LocalDateAttribute='"
                + tempCourse.getLocalDateAttribute().getValue()
                + "' WHERE IntAttribute="
                + tempCourse.getIntAttribute();

        if (con == null) {
            con = DatabaseHelper.getConnection();
        }
        try {
            int r = DatabaseHelper.getStatement(DatabaseHelper.getConnection()).executeUpdate(sqlUpdate);
            if (r == 1) {
                String message = "The Course is Updated Succuessfully In Database!";
                Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
                alert.show();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FP201810088.class.getName()).log(Level.SEVERE, null, ex);
            Alert myMessage = new Alert(Alert.AlertType.INFORMATION, ex.getMessage(), ButtonType.OK);
            myMessage.show();
        }
    }

    private void OnDelete(ActionEvent e) {
        if (GetData() == null) {
            return;
        }        
        boolean isDeletedWasFirst = false;
        int deleteIndex = Integer.parseInt(txtInt.getText());
        if (courseList.get(0).getIntAttribute() == deleteIndex) {
            isDeletedWasFirst = true;
        }
        String sqlDelete = "DELETE FROM course WHERE IntAttribute="
                + deleteIndex;

        if (con == null) {
            con = DatabaseHelper.getConnection();
        }
        try {
            int r = DatabaseHelper.getStatement(DatabaseHelper.getConnection()).executeUpdate(sqlDelete);
            if (r == 1) {
                String message = "The Course Is Deleted Succuessfully In Database!";
                Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
                alert.show();

                condition = tempcourse -> tempcourse.getIntAttribute() == Integer.parseInt(txtInt.getText());
                courseList.removeIf(condition);

                if (isDeletedWasFirst) {
                    OnFirst(null);
                } else {
                    OnPrevious(null);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FP201810088.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, ex.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    private void OnSort(ActionEvent e) {
        Collections.sort(courseList);
        OnFirst(null);
    }

    ////////////////////////////////////////////////////////////////////////////////
    private void OnPrevious(ActionEvent e) {
        if (!courseList.isEmpty() && listIndex > 0) {
            SetData(courseList.get(--listIndex));
        }
    }

    private void OnNext(ActionEvent e) {
        if ((listIndex + 1) < courseList.size()) {
            SetData(courseList.get(++listIndex));
        }
    }

    private void OnFirst(ActionEvent e) {
        if (!courseList.isEmpty()) {
            SetData(courseList.get(0));
        }
        listIndex = 0;
    }

    private void OnLast(ActionEvent e) {
        if (courseList.size() > 0) {
            SetData(courseList.get(courseList.size() - 1));
        }
        listIndex = courseList.size() - 1;
    }

    ////////////////////////////////////////////////////////////////////////////////
    private int GetPK() {
        ////////////////////////////////////////////////////////////////////////////////    
        ///////////// retrive all the data from DB to get the last PK //////////////////
        ////////////////////////////////////////////////////////////////////////////////    
        String sqlSelect = "SELECT "
                + "IntAttribute "
                + "FROM course";

        int lastIndex = 0;

        if (con == null) {
            con = DatabaseHelper.getConnection();
        }
        try {
            ResultSet rs = DatabaseHelper.getStatement(DatabaseHelper.getConnection()).executeQuery(sqlSelect);
            while (rs.next()) {
                lastIndex = rs.getInt("IntAttribute");
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(FP201810088.class.getName()).log(Level.SEVERE, null, ex);
        }
        ////////////////////////////////////////////////////////////////////////////////    
        /////////////////////////// Return the Last PK /////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////
        return lastIndex;
    }

    private void OnSettingDate(ActionEvent e) {
        date.setValue(((DatePicker) e.getSource()).getValue());
    }
}
