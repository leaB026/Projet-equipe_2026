module projet2026.projet2026_ressorts {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens projet2026.projet2026_ressorts to javafx.fxml;
    exports projet2026.projet2026_ressorts;
}