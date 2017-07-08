package main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public class CalendarController implements Initializable {

    @FXML
    private GridPane calendar_grid;
    @FXML
    private Button previous;
    @FXML
    private Button next;
    @FXML
    private Button view_switch;
    @FXML
    private Label calendar_current_label;

    private LocalDateTime currentViewDate;

    private boolean view_as_month = true;
    @Override
    public void initialize(URL u, ResourceBundle b) {
        calendar_current_label.setStyle("-fx-font-size:18;");
        calendar_grid.setMinHeight(654);
        calendar_grid.setMaxWidth(714);
        currentViewDate = LocalDateTime.now();
        show_calendar_month(currentViewDate);


        previous.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                if (view_as_month) {
                    currentViewDate = currentViewDate.minusMonths(1);
                    show_calendar_month(currentViewDate);
                } else {
                    currentViewDate = currentViewDate.minusWeeks(1);
                    show_calendar_week(currentViewDate);
                }

            }
        });

        view_switch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (view_as_month) {
                    show_calendar_week(currentViewDate);
                    view_switch.setText("View By Month");
                    view_as_month = false;
                } else {
                    show_calendar_month(currentViewDate);
                    view_switch.setText("View By Week");
                    view_as_month = true;
                }
            }
        });

        next.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (view_as_month) {
                    currentViewDate = currentViewDate.plusMonths(1);
                    show_calendar_month(currentViewDate);
                } else {
                    currentViewDate = currentViewDate.plusWeeks(1);
                    show_calendar_week(currentViewDate);
                }
            }
        });
    }

    private void add_weekdays_to_calendar() {
        for (int i = 0; i< 7;i++) {
            VBox cell_container = new VBox();
            cell_container.setMinHeight(40);
            cell_container.setMinWidth(100);
            cell_container.setAlignment(Pos.CENTER);
            //cell_container.setStyle("-fx-border-width: 1px;-fx-border-color:#000000;");
            String weekdayText= "";
            switch (i) {
                case 0: weekdayText = "Sunday"; break;
                case 1: weekdayText = "Monday"; break;
                case 2: weekdayText = "Tuesday"; break;
                case 3: weekdayText = "Wednesday"; break;
                case 4: weekdayText = "Thursday"; break;
                case 5: weekdayText = "Friday"; break;
                case 6: weekdayText = "Saturday"; break;
            }
            Text wlabel = new Text(weekdayText);
            cell_container.getChildren().add(wlabel);
            calendar_grid.add(cell_container,i,0);
        }

    }
    private void show_calendar_month(LocalDateTime currentTime) {
        calendar_grid.getChildren().clear();
        add_weekdays_to_calendar();

        LocalDateTime presentDate = LocalDateTime.now();
        boolean isCurrentMonth = (currentTime.getYear()==presentDate.getYear() && currentTime.getMonthValue()==presentDate.getMonthValue());

        int currentDayOfMonth = currentTime.getDayOfMonth();  // current day of month
        int currentMonthOfYearValue = currentTime.getMonthValue(); // current month of year integer
        Month currentMonthofYearText = currentTime.getMonth(); // current month of year text
        int currentYear = currentTime.getYear();

        calendar_current_label.setText(""+currentMonthofYearText+" "+String.valueOf(currentYear));

        Calendar currentCal = new GregorianCalendar(currentYear,currentMonthOfYearValue-1, 1);

        int firstDayOfWeekIndex = currentCal.get(Calendar.DAY_OF_WEEK);
        // get the column index for that first day

        // get the total days in that month
        // 7 x 6
        int index_date_label = 1;
        boolean begin_index=false;
        LocalDate loopTime = LocalDate.of(currentYear,currentTime.getMonthValue(),1);
        for (int row = 1; row <= 6; row++) {

            for (int col = 0; col < 7; col++) {
                VBox cell_container = new VBox();
                cell_container.setMinHeight(100);
                cell_container.setMinWidth(100);
                cell_container.setStyle("-fx-border-width: 1px;-fx-border-color:#000000;");

                if (row==1) {
                    if (col>=(firstDayOfWeekIndex -1)) {
                        begin_index = true;
                    }
                }
                if (begin_index && index_date_label<= currentCal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                    Text dateText = new Text();
                    dateText.setText(String.valueOf(index_date_label));

                    if (currentDayOfMonth==index_date_label && isCurrentMonth) {
                        dateText.setStyle("-fx-fill: blue");
                    }

                    cell_container.getChildren().add(dateText);
                    // get appointments on this date and add here
                    Button schedule_btn = new Button("+");
                    LocalDate set_date = LocalDate.of(loopTime.getYear(),loopTime.getMonthValue(),index_date_label);
                    schedule_btn.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            try {
                                show_scheduler(set_date);
                            } catch (IOException ed) {
                                ed.printStackTrace();
                            }

                        }
                    });
                    schedule_btn.setAlignment(Pos.CENTER);
                    cell_container.getChildren().add(schedule_btn);
                    loopTime = loopTime.plusDays(1);

                    index_date_label++;

                }
                calendar_grid.add(cell_container,col,row);
            }
        }

    }

    private void show_calendar_week(LocalDateTime currentTime) {
        calendar_grid.getChildren().clear();
        add_weekdays_to_calendar();
        LocalDateTime presentDate = LocalDateTime.now();
        presentDate = presentDate.minusDays(presentDate.getDayOfWeek().getValue()) ;
        int currentDayOfWeek = currentTime.getDayOfWeek().getValue();
        currentTime = currentTime.minusDays(currentDayOfWeek) ;

        boolean isCurrentWeek = (currentTime.getYear()==presentDate.getYear() && currentTime.getDayOfYear()==presentDate.getDayOfYear());

        int currentDayOfMonth = currentTime.getDayOfMonth();  // current day of month
        Month currentMonthofYearText = currentTime.getMonth(); // current month of year text
        int currentYear = currentTime.getYear();

        calendar_current_label.setText("Week of Sunday "+currentMonthofYearText+" "+String.valueOf(currentDayOfMonth)+" ,"+String.valueOf(currentYear));
        int index_date_label = currentDayOfMonth;

        LocalDate loopTime = LocalDate.of(currentYear,currentTime.getMonthValue(),1);
        for (int col = 0; col < 7; col++) {
            VBox cell_container = new VBox();
            cell_container.setMinHeight(300);
            cell_container.setMinWidth(100);
            cell_container.setStyle("-fx-border-width: 1px;-fx-border-color:#000000;");

            Text dateText = new Text();
            dateText.setText(String.valueOf(index_date_label));

            if (currentDayOfWeek==index_date_label && isCurrentWeek) {
                dateText.setStyle("-fx-fill: blue");
            }

            cell_container.getChildren().add(dateText);
            // get appointments on this date and add here
            Button schedule_btn = new Button("+");
            LocalDate set_date = LocalDate.of(loopTime.getYear(),loopTime.getMonthValue(),index_date_label);
            schedule_btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        show_scheduler(set_date);
                    } catch (IOException ed) {
                        ed.printStackTrace();
                    }

                }
            });
            cell_container.getChildren().add(schedule_btn);
            loopTime = loopTime.plusDays(1);
            index_date_label++;


            calendar_grid.add(cell_container,col,1);
        }

    }

    public void show_scheduler(LocalDate date) throws IOException {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setTitle("Schedule an Appointment");

        AppointmentController appointmentController = new AppointmentController(date);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(appointmentController);
        fxmlLoader.setLocation(getClass().getResource("resources/appointment_view.fxml"));
        BorderPane root = fxmlLoader.load();
        String css = this.getClass().getResource("_style.css").toExternalForm();
        root.getStylesheets().add(css);

        dialogStage.setScene(new Scene(root, 350, 300));
        dialogStage.setAlwaysOnTop(true);
        dialogStage.show();
    }
}