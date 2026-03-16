module ca.qc.bdeb.sim.projet_ressort {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;


    opens ca.qc.bdeb.sim.projet_ressort to javafx.fxml;
    exports ca.qc.bdeb.sim.projet_ressort;
}