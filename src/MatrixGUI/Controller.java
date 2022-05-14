package MatrixGUI;


import javafx.application.Platform;
import javafx.fxml.FXML;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;


import model.Simulation;



public class Controller {



    @FXML
    BorderPane borderPane = new BorderPane();

    @FXML
    Label saved = new Label();

    @FXML
    Label death = new Label();

    @FXML
    Label killed = new Label();

    Simulation simulation;

    String grid;
    String solution;


    @FXML
    public void initialize()
    {

    }

    @FXML
    public void Start(){

        if( simulation.getSteps() < simulation.getSolutionSize()) {
            simulation.simActions();
        }
    }


    @FXML
    public void Draw(){
        simulation = new Simulation(borderPane,grid,solution,saved,death,killed);
        simulation.setSteps(0);
        simulation.restore();
        saved.setText("SAVED: 0");
        death.setText("DEATH: 0");
        killed.setText("KILLED: 0");
    }

    public void setGrid(String grid){
        this.grid = grid;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
}
