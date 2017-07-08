package main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {
    private Scene scene;

    private CalendarController calendarController;
    private CustomerRecordsController customerRecordsController;
    private LoginController loginController;
    private PopupController popupController;
    private ReportController reportController;

    @FXML
    Button btn_calendar;
    @FXML
    Button btn_customers;
    @FXML
    Button btn_reports;
    @FXML
    VBox content_node;
    @FXML
    HBox nav_buttons;

    /*
    TODO: Creating an instance for each subview
     */

    public AppController() {
        System.out.println("created");
    }

    @Override
    public void initialize(URL u, ResourceBundle b) {

        btn_calendar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    create_calendar_view();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

        btn_customers.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                try {
                    open_records_view();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        btn_reports.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                try {
                    open_reports_view();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        nav_buttons.setVisible(true);
    }

    // create or re-create calendar view
    private void create_calendar_view() throws Exception {
        this.calendarController = new CalendarController();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(this.calendarController);
        fxmlLoader.setLocation(getClass().getResource("resources/calendar_view.fxml"));
        BorderPane root = fxmlLoader.load();
        String css = this.getClass().getResource("_style.css").toExternalForm();
        root.getStylesheets().add(css);

        this.content_node.getChildren().setAll(root);

    }

    // open records view
    private void open_records_view() throws Exception  {
        this.customerRecordsController = new CustomerRecordsController();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(this.customerRecordsController);
        fxmlLoader.setLocation(getClass().getResource("resources/customer_records_view.fxml"));
        BorderPane root = fxmlLoader.load();
        String css = this.getClass().getResource("_style.css").toExternalForm();
        root.getStylesheets().add(css);

        this.content_node.getChildren().setAll(root);
    }

    // open or create report view
    private void open_reports_view() throws Exception  {
        this.reportController = new ReportController();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(this.reportController);
        fxmlLoader.setLocation(getClass().getResource("resources/report_view.fxml"));
        BorderPane root = fxmlLoader.load();
        String css = this.getClass().getResource("_style.css").toExternalForm();
        root.getStylesheets().add(css);

        this.content_node.getChildren().setAll(root);
    }


    public void setScene(Scene s) {
        this.scene = s;
    }
}
