package main;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.ResourceBundle;

public class CustomerRecordsController implements Initializable {

    @FXML
    private TextField first_name;
    @FXML
    private TextField last_name;
    @FXML
    private TextField phone_area;
    @FXML
    private TextField phone_prefix;
    @FXML
    private TextField phone_phone;
    @FXML
    private TextField address;
    @FXML
    private Button btn_add_record;
    @FXML
    private TableView record_table;
    @FXML
    private ScrollPane record_table_scroll_pane;
    @FXML
    private Button del_selected_user;

    private Customer current_selected_customer = null;

    ObservableList<Customer> data;
    @Override

    public void initialize(URL u, ResourceBundle b) {
        del_selected_user.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (current_selected_customer!=null) {
                    current_selected_customer.delete_self();
                    data.remove(current_selected_customer);
                    del_selected_user.setVisible(false);
                }
            }
        });
        del_selected_user.setVisible(false);
        btn_add_record.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ArrayList<String> error_messages = validate_add_customer();

                if (error_messages.size()==0) {
                    // add the record
                    Customer c = new Customer(first_name.getText(),last_name.getText(), phone_area.getText(),phone_prefix.getText(),phone_phone.getText(),address.getText());
                    c.saveAll();
                    data.add(c);

                } else {
                    // show error list
                    String msg = "UNABLE TO CREATE RECORD\n\nThe following errors were found:\n\n";

                    for (String emsg : error_messages) {
                        msg+=emsg+"\n";

                    }

                    int window_height = (error_messages.size()*30)+80;
                    AlertWindow win = new AlertWindow("Record validation errors",300, window_height );
                    win.show_message(msg);
                }
            }
        });

        record_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                current_selected_customer = (Customer) newSelection;
                del_selected_user.setVisible(true);
            } else {
                del_selected_user.setVisible(false);
                current_selected_customer = null;
            }
        });

        record_table_scroll_pane.setFitToHeight(true);
        record_table_scroll_pane.setFitToWidth(true);
        populate_record_table();

    }

    private void populate_record_table() {
        record_table.setEditable(true);

        TableColumn firstNameCol = new TableColumn("First Name");
            firstNameCol.setPrefWidth(200);
        TableColumn lastNameCol = new TableColumn("Last Name");
            lastNameCol.setPrefWidth(200);
        TableColumn fullName = new TableColumn("Name");
        fullName.getColumns().addAll(firstNameCol, lastNameCol);

        TableColumn areaCol = new TableColumn("Area Code");
            areaCol.setPrefWidth(70);
        TableColumn prefixCol = new TableColumn("Prefix");
            prefixCol.setPrefWidth(70);
        TableColumn phoneCol = new TableColumn("Line");
            phoneCol.setPrefWidth(90);
        TableColumn phoneCompleteCol = new TableColumn("Phone Number");
        phoneCompleteCol.getColumns().addAll(areaCol, prefixCol, phoneCol);

        TableColumn addressCol = new TableColumn("Address");
            addressCol.setPrefWidth(300);

        record_table.getColumns().addAll(fullName, phoneCompleteCol, addressCol);

        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Customer,String>("first_name")
        );
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Customer, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Customer, String> t) {
                        ((Customer) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFirst_name(t.getNewValue());
                    }
                }
        );

        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Customer,String>("last_name")
        );
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Customer, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Customer, String> t) {
                        ((Customer) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setLast_name(t.getNewValue());
                    }
                }
        );

        areaCol.setCellValueFactory(
                new PropertyValueFactory<Customer,String>("phone_area")
        );
        areaCol.setCellFactory(TextFieldTableCell.forTableColumn());
        areaCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Customer, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Customer, String> t) {
                        ((Customer) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setPhone_area(t.getNewValue());
                    }
                }
        );

        prefixCol.setCellValueFactory(
                new PropertyValueFactory<Customer,String>("phone_prefix")
        );
        prefixCol.setCellFactory(TextFieldTableCell.forTableColumn());
        prefixCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Customer, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Customer, String> t) {
                        ((Customer) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setPhone_prefix(t.getNewValue());
                    }
                }
        );

        phoneCol.setCellValueFactory(
                new PropertyValueFactory<Customer,String>("phone_phone")
        );
        phoneCol.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Customer, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Customer, String> t) {
                        ((Customer) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setPhone_phone(t.getNewValue());
                    }
                }
        );

        addressCol.setCellValueFactory(
                new PropertyValueFactory<Customer,String>("address")
        );
        addressCol.setCellFactory(TextFieldTableCell.forTableColumn());
        addressCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Customer, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Customer, String> t) {
                        ((Customer) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setAddress(t.getNewValue());
                    }
                }
        );

        // insert records from database...
        try {
            data = customerConnector.getAllCustomers();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


        record_table.setItems(data);
    }

    private ArrayList<String> validate_add_customer() {

        ArrayList<String> error_messages = new ArrayList<>();

        // first name filled out
        if (first_name.getText().equals("")) {
            error_messages.add("First name cannot be blank");
        }
        // last name filled out
        if (last_name.getText().equals("")) {
            error_messages.add("Last name cannot be blank");
        }
        // phone area filled out
        if (phone_area.getText().equals("")) {
            error_messages.add("Area code cannot be blank");
        }
        //3 numbers
        if (phone_area.getText().length()!=3) {
            error_messages.add("Area code must be 3 digits");
        }
        // phone prefix filled out
        if (phone_prefix.getText().equals("")) {
            error_messages.add("Phone prefix cannot be blank");
        }
        //3 numbers
        if (phone_prefix.getText().length()!=3) {
            error_messages.add("Phone prefix must be 3 digits");
        }
        // phone_phone filled out
        if (phone_phone.getText().equals("")) {
            error_messages.add("Phone Line number cannot be blank");
        }
        //4 numbers
        if (phone_phone.getText().length()!=4) {
            error_messages.add("Phone Line number must be 4 digits");
        }
        // address filled out
        if (address.getText().equals("")) {
            error_messages.add("Address cannot be blank");
        }

        return error_messages;
    }


}
