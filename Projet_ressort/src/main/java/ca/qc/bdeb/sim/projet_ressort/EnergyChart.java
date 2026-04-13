package ca.qc.bdeb.sim.projet_ressort;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Slider;

public class EnergyChart {
    private javafx.scene.chart.BarChart<String, Number> bc;
    String energiePotentielleGravitationnelle;
    String energiePotentielleElastique;
    CategoryAxis xAxis;
    NumberAxis yAxis;
    XYChart.Series<String, Number> ePG;
    XYChart.Series<String, Number> ePE;


    public EnergyChart(String energiePotentielleGravitationnelle, String energiePotentielleElastique) {
        this.energiePotentielleGravitationnelle = energiePotentielleGravitationnelle;
        this.energiePotentielleElastique = energiePotentielleElastique;
        this.xAxis = new CategoryAxis();
        this.yAxis = new NumberAxis();
        this.bc = new javafx.scene.chart.BarChart<String, Number>(xAxis, yAxis);
        this.ePG = new XYChart.Series();
        this.ePE = new XYChart.Series();
    }

    public void draw() {



        bc.setTitle("Sommaire des énergies");
        xAxis.setLabel("Type d'énergie");
        yAxis.setLabel("Valeur");

        ePG.setName("Énergie potentielle gravitationnelle");
        ePE.setName("Énergie potentielle élastique");

// Source - https://stackoverflow.com/a/43396420
// Posted by Crislips
// Retrieved 2026-04-13, License - CC BY-SA 3.0

//        Node n = bc.lookup(".series0.data0.chart-bar");
//        if (n != null) {
//            n.setStyle("-fx-bar-fill: red");
//        }
//        n = bc.lookup(".series0.data1.chart-bar");
//        if (n != null) {
//            n.setStyle("-fx-bar-fill: blue");
//
//        }

        ePG.getData().add(new XYChart.Data(energiePotentielleGravitationnelle, 0));
        ePE.getData().add(new XYChart.Data(energiePotentielleElastique, 0));
        bc.getData().addAll(ePG, ePE);

        bc.setLegendVisible(true);
//        bc.setMaxWidth(300);



    }

    public void update(double dt, PersonnageQuiSaute perso, Slider slider, Planet planet) {


        double mgh = 30 * planet.gravite * (ProjetIntegration.HEIGHT - perso.position.getY());
        double elastique = 0.5 * slider.getValue() * Math.pow(perso.position.getY(), 2);

        if(perso.position.getY() >= ProjetIntegration.HEIGHT - perso.getTaille().getY()){

            mgh = 0;


        }
        if(ePG != null && ePE != null ) {
            ePG.getData().get(0).setYValue(mgh);
            ePE.getData().get(0).setYValue(elastique);
        }
    }

    public BarChart<String, Number> getBc() {
        return bc;
    }

}
