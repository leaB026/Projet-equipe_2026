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
    String energieCinetique;
    String energieDissipee;
    CategoryAxis xAxis;
    NumberAxis yAxis;
    XYChart.Series<String, Number> ePG;
    XYChart.Series<String, Number> ePE;
    XYChart.Series<String, Number> eC;
    XYChart.Series<String, Number> eD;
    double eDissipee =0;
    double eInitiale =0;




    public EnergyChart(String energiePotentielleGravitationnelle, String energiePotentielleElastique, String energieCinetique, String energieDissipee) {
        this.energiePotentielleGravitationnelle = energiePotentielleGravitationnelle;
        this.energiePotentielleElastique = energiePotentielleElastique;
        this.energieCinetique = energieCinetique;
        this.energieDissipee = energieDissipee;
        this.xAxis = new CategoryAxis();
        this.yAxis = new NumberAxis();
        this.bc = new javafx.scene.chart.BarChart<String, Number>(xAxis, yAxis);
        this.ePG = new XYChart.Series<>();
        this.ePE = new XYChart.Series<>();
        this.eC = new XYChart.Series<>();
        this.eD = new XYChart.Series<>();
    }


    public void draw() {


        bc.setTitle("Sommaire des énergies");
        xAxis.setLabel("Type d'énergie");
        yAxis.setLabel("Valeur");


        ePG.setName("Énergie potentielle gravitationnelle");
        ePE.setName("Énergie potentielle élastique");
        eC.setName("Énergie cinétique");
        eD.setName("Énergie dissipée");








        ePG.getData().add(new XYChart.Data<>(energiePotentielleGravitationnelle, 0));
        ePE.getData().add(new XYChart.Data<>(energiePotentielleElastique, 0));
        eC.getData().add(new XYChart.Data<>(energieCinetique, 0));
        eD.getData().add(new XYChart.Data<>(energieDissipee, 0));
        bc.getData().addAll(ePG, ePE, eC, eD);






        bc.setLegendVisible(true);
        bc.setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 12px;");
        bc.setAnimated(false);


//        bc.setMaxWidth(300);






    }


    public void update(double dt, PersonnageQuiSaute perso, Slider slider, Planet planet, Ressort ressort, Simulation simulation) {


        double mgh = perso.masse * planet.gravite * (ProjetIntegration.HEIGHT - perso.position.getY());
        double elastique = 0.5 * slider.getValue()/10 * Math.pow(ressort.compression, 2);
        double k = 0.5 * perso.masse * Math.pow(perso.getVelocite().getY() , 2);
        double eTotale = mgh + elastique + k;
        //Si force pas de force d'amortissement avec un if force d'amortissement =0
        if(!perso.pause) {
            if (perso.estEnTrainDeTirerPersonnage) {
                eInitiale = eTotale;
            }
            eDissipee = Math.abs(eTotale - eInitiale);


            if (perso.position.getY() >= ProjetIntegration.HEIGHT - perso.getTaille().getY()) {

                mgh = 0;
                k = 0;
                eDissipee = 0;

            }
            if (ressort.compression <= 0) {

                elastique = 0;

            }
            if (!simulation.utiliserAmortissement) {
                eDissipee = 0;
            }


            if (ePG != null && ePE != null && eC != null && eD != null) {
                ePG.getData().get(0).setYValue(mgh);
                ePE.getData().get(0).setYValue(elastique);
                eC.getData().get(0).setYValue(k);
                eD.getData().get(0).setYValue(eDissipee);


            }


        }

    }


    public BarChart<String, Number> getBc() {
        return bc;
    }
}
