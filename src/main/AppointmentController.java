package main;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

public class AppointmentController implements Initializable,ListLoader {

    @FXML
    private Label appointment_date;
    @FXML
    private ComboBox<Customer> customer;
    @FXML
    private ComboBox<Integer> time_hour;
    @FXML
    private ComboBox<Integer> time_minute;

    LocalDate appointmentTime;

    public AppointmentController(LocalDate ld) {
        this.appointmentTime = ld;
    }
    public class AmPm {
        public String val;

        public AmPm(String v) {
            this.val = v;
        }
        @Override
        public String toString() {
            return this.val;
        }
    }

    @FXML
    private ComboBox<AmPm> time_ampm;

    @Override
    public void initialize(URL u, ResourceBundle b) {
        populate_time_combo_boxes();
        populate_customers();

        appointment_date.setText("Create Appointment for "+appointmentTime.getDayOfWeek()+", "+appointmentTime.getMonth()+" "+appointmentTime.getDayOfMonth()+", "+appointmentTime.getYear());
    }

    private void populate_time_combo_boxes() {
        time_ampm.getItems().add(new AmPm("AM"));
        time_ampm.getItems().add(new AmPm("PM"));

        for (Integer h=1; h<=12;h++) {
            time_hour.getItems().add(h);
        }

        for (Integer m=1; m<=60;m++) {
            time_minute.getItems().add(m);
        }
    }

    private void populate_customers() {
        ListLoader listloader = this;
        Service service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        customerConnector.getAllCustomersList(listloader);

                        return null;

                    }
                };
            }
        };
        service.start();

    }

    @Override
    public void update_customer_list(ArrayList<Customer> customerList) {
        customer.getItems().clear();

        Platform.runLater(new Runnable() {
            public void run() {
                // update combobox
                for (Customer c : customerList) {
                    customer.getItems().add(c);
                }
                customer.getItems().sort(new Comparator<Customer>() {
                    @Override
                    public int compare(Customer o1, Customer o2) {
                        String name = o1.getLast_name();
                        String name2  = o2.getLast_name();
                        return name.compareTo(name2);
                    }
                });

            }
        });
    }
}
