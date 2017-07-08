package main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by nibbc_000 on 5/9/2017.
 */
public class AlertWindow {
    private int boxWidth;
    private int boxHeight;
    private String title;

    public AlertWindow(String title, int x, int y) {
        this.boxWidth = x;
        this.boxHeight = y;
        this.title = title;
    }
    public void show_message(String alertText) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setTitle(this.title);

        HBox hbox = new HBox(new Text(alertText));
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(15));
        dialogStage.setScene(new Scene(hbox, this.boxWidth, this.boxHeight));
        dialogStage.show();
    }
}
