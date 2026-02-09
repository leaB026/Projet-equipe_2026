module projet2026.integrationprojet26ressorts {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens projet2026.integrationprojet26ressorts to javafx.fxml;
    exports projet2026.integrationprojet26ressorts;
}