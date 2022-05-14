package MatrixGUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    private static String Grid;
    private static String Solution;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            BorderPane root =
                    (BorderPane) loader.load(getClass().getResource("matrix-gui-sim.fxml").openStream());


            Controller controller = loader.getController();

            /*controller.setGrid("6,6;1;2,2;2,4;0,1,1,0,3,0,4,1,4,3,3,4,1,4,0,3,1,5;0,2;1,3,4,2,4,2,1,3;0,5,90,1,2,92,4,4,2,5,5,1,1,1,98");*/
            controller.setGrid(Grid);
            /*controller.setSolution("up,kill,up,kill,kill,right,right,kill,left,left,takePill,down,right,fly,down,right,right,up,carry,right,up,kill,up,left,drop,down,down,right,down,carry,up,up,left,up,drop");*/
            controller.setSolution(Solution);
            primaryStage.setScene(new Scene(root,1250,650));
            primaryStage.show();


        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    public static void setGrid(String Grid) {
        Main.Grid = Grid;
    }


    public static void setSolution(String Solution) {
        Main.Solution = Solution;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
