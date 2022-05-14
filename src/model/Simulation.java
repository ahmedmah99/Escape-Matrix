package model;


import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;

import javafx.scene.Node;
import javafx.scene.control.Label;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javafx.scene.text.Font;

import java.sql.SQLOutput;
import java.util.ArrayList;


public class Simulation {



    static int steps = 0;

    GridPane gridPane;
    String grid;
    String[] solution;


    int NeoX;
    int NeoY;
    int NeoDamage = 0;
    int carry = 0;

    int numCols;
    int numRows;


    Label Saved;
    Label Death;
    Label Killed;


    int saved = 0;
    int dead = 0;
    int killed = 0;
    String[] hostages;

    ArrayList<Integer> hostageCarriedDamage = new ArrayList<Integer>();

    public Simulation(BorderPane borderPane,String grid, String solution,Label Saved, Label Death, Label killed){

        gridPane = new GridPane();
        this.grid = grid;
        this.solution = solution.split(",");
        this.Saved = Saved;
        this.Death = Death;
        this.Killed = killed;
        initGrid(borderPane);

    }



    public void initGrid(BorderPane borderPane){


        borderPane.setCenter(gridPane);

        String[] split = grid.split(";");
        String Matrix = split[0];
        String[] mat = Matrix.split(",");


        gridPane.setGridLinesVisible(true);
        numRows = Integer.parseInt(mat[0]) ;
        numCols = Integer.parseInt(mat[1]) ;



        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / numCols);
            gridPane.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numRows);
            gridPane.getRowConstraints().add(rowConst);
        }

        //gridPane.setMaxSize(600,600);

        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                VBox vBox = new VBox();
                vBox.setAlignment(Pos.CENTER);
                gridPane.add(vBox,i,j);
                System.out.println("COL: " + i + "ROWS: " + j);
            }
        }


        String Neo = split[2];
        String[] NeoArr = Neo.split(",");
        Label NeoPos = new Label("NEO" +"("+0+")" + "("+carry+")");
        NeoPos.setTextFill(Color.rgb(20,194,20));
        //NeoPos.setFont(new Font(26));
        //NeoPos.setMaxSize(300,300);
        NeoPos.setStyle("-fx-font-weight: bold");
        NeoPos.setWrapText(true);


        NeoX = Integer.parseInt(NeoArr[0]);
        NeoY = Integer.parseInt(NeoArr[1]);

        VBox vBox = (VBox)getNodeByRowColumnIndex(NeoX, NeoY);
        vBox.getChildren().add(NeoPos);
        GridPane.setHalignment(vBox, HPos.CENTER);
        GridPane.setValignment(vBox, VPos.CENTER);



        String Telephone = split[3];
        String[] teleArr = Telephone.split(",");
        Label TelePos = new Label("TB");
        TelePos.setTextFill(Color.rgb(20,20,194));
        //TelePos.setFont(new Font(26));
        //TelePos.setMaxSize(80,80);
        TelePos.setStyle("-fx-font-weight: bold");
        TelePos.setWrapText(true);
        GridPane.setHalignment(TelePos, HPos.CENTER);
        GridPane.setValignment(TelePos, VPos.CENTER);
        vBox = (VBox)getNodeByRowColumnIndex(Integer.parseInt(teleArr[0]), Integer.parseInt(teleArr[1]));
        vBox.getChildren().add(TelePos);


        String Agents = split[4];
        String[] agents = Agents.split(",");

        for (int i = 0; i < agents.length; i+=2) {
            Label Agent = new Label("AGENT");
            Agent.setTextFill(Color.rgb(175,59,39));
            //Agent.setFont(new Font(26));
            //Agent.setMaxSize(80,80);
            Agent.setStyle("-fx-font-weight: bold");
            Agent.setWrapText(true);
            GridPane.setHalignment(Agent, HPos.CENTER);
            GridPane.setValignment(Agent, VPos.CENTER);
            vBox = (VBox)getNodeByRowColumnIndex(Integer.parseInt(agents[i]), Integer.parseInt(agents[i+1]));
            vBox.getChildren().add(Agent);
        }



        String Pills = split[5];
        String[] pills = Pills.split(",");
        for (int i = 0; i < pills.length; i+=2) {
            Label Pill = new Label("PILL");
            Pill.setTextFill(Color.rgb(199,179,23));
            //Pill.setFont(new Font(26));
            //Pill.setMaxSize(80,80);
            Pill.setStyle("-fx-font-weight: bold");
            Pill.setWrapText(true);
            GridPane.setHalignment(Pill, HPos.CENTER);
            GridPane.setValignment(Pill, VPos.CENTER);
            vBox = (VBox)getNodeByRowColumnIndex(Integer.parseInt(pills[i]), Integer.parseInt(pills[i+1]));
            vBox.getChildren().add(Pill);
        }



        String Pads = split[6];
        String[] pads = Pads.split(",");
        for (int i = 0; i < pads.length; i+=4) {
            Label Pad = new Label("PAD" + "("+pads[i+2] +","+ pads[i+3] +")");
            Pad.setTextFill(Color.rgb(23,193,199));
            //Pad.setFont(new Font(26));
            //Pad.setMaxSize(80,80);
            Pad.setStyle("-fx-font-weight: bold");
            Pad.setWrapText(true);
            GridPane.setHalignment(Pad, HPos.CENTER);
            GridPane.setValignment(Pad, VPos.CENTER);
            vBox = (VBox)getNodeByRowColumnIndex(Integer.parseInt(pads[i]), Integer.parseInt(pads[i+1]));
            vBox.getChildren().add(Pad);
        }


        String Hostages = split[7];
        hostages = Hostages.split(",");
        for (int i = 0; i < hostages.length; i+=3) {

            Label hostage = new Label("HOSTAGE" +"("+hostages[i+2]+")");
            hostage.setTextFill(Color.rgb(132,25,206));
            //hostage.setFont(new Font(20));
            //hostage.setMaxSize(80,80);
            hostage.setStyle("-fx-font-weight: bold");
            hostage.setWrapText(true);
            GridPane.setHalignment(hostage, HPos.CENTER);
            GridPane.setValignment(hostage, VPos.CENTER);
            vBox = (VBox)getNodeByRowColumnIndex(Integer.parseInt(hostages[i]), Integer.parseInt(hostages[i+1]));
            vBox.getChildren().add(hostage);
        }

    }


    public void simActions(){ //take parameter String solution

        String action = solution[steps].toUpperCase();
        switch (action){
            case "UP":actionUP();break;
            case "DOWN": actionDown();break;
            case "LEFT":actionLeft();break;
            case "RIGHT":actionRight();break;
            case "KILL": Kill();break;
            case "FLY":Fly();break;
            case "TAKEPILL":takePill();break;
            case "CARRY":Carry();break;
            case "DROP":DropHostage();break;
        }
        steps +=1;


    }


    public void actionDown(){
        Label NeoPos = new Label("NEO"+"("+NeoDamage+")"+"("+carry+")");
        NeoPos.setTextFill(Color.rgb(20,194,20));
        //NeoPos.setFont(new Font(26));
        //NeoPos.setMaxSize(80,80);
        NeoPos.setStyle("-fx-font-weight: bold");
        NeoPos.setWrapText(true);
        removeNodeByRowColumnIndex(NeoX,NeoY,gridPane,"NEO");
        VBox theBox = (VBox)getNodeByRowColumnIndex(NeoX+1,NeoY);
        theBox.getChildren().add(NeoPos);
        NeoX++;

        //change damage of the hostages
        for (int i = 0; i < hostages.length; i+=3)
            changeDamage(Integer.parseInt(hostages[i]),Integer.parseInt(hostages[i+1]),gridPane,2);


        updateCarriedHostageDamage(false);
        checkDeadCarriedHostages();
    }
    public void actionUP(){
        Label NeoPos = new Label("NEO"+"("+NeoDamage+")"+"("+carry+")");
        NeoPos.setTextFill(Color.rgb(20,194,20));
        //NeoPos.setFont(new Font(26));
        //NeoPos.setMaxSize(80,80);
        NeoPos.setStyle("-fx-font-weight: bold");
        NeoPos.setWrapText(true);
        removeNodeByRowColumnIndex(NeoX,NeoY,gridPane,"NEO");
        VBox theBox = (VBox)getNodeByRowColumnIndex(NeoX-1,NeoY);
        theBox.getChildren().add(NeoPos);
        NeoX--;

        //change damage of the hostages
        for (int i = 0; i < hostages.length; i+=3)
            changeDamage(Integer.parseInt(hostages[i]),Integer.parseInt(hostages[i+1]),gridPane,2);


        updateCarriedHostageDamage(false);
        checkDeadCarriedHostages();
    }
    public void actionLeft(){

        Label NeoPos = new Label("NEO"+"("+NeoDamage+")"+"("+carry+")");
        NeoPos.setTextFill(Color.rgb(20,194,20));
        //NeoPos.setFont(new Font(26));
        //NeoPos.setMaxSize(80,80);
        NeoPos.setStyle("-fx-font-weight: bold");
        NeoPos.setWrapText(true);
        removeNodeByRowColumnIndex(NeoX,NeoY,gridPane,"NEO");
        VBox theBox = (VBox)getNodeByRowColumnIndex(NeoX,NeoY-1);
        theBox.getChildren().add(NeoPos);
        NeoY--;

        //change damage of the hostages
        for (int i = 0; i < hostages.length; i+=3)
            changeDamage(Integer.parseInt(hostages[i]),Integer.parseInt(hostages[i+1]),gridPane,2);


        updateCarriedHostageDamage(false);
        checkDeadCarriedHostages();

    }
    public void actionRight(){
        Label NeoPos = new Label("NEO"+"("+NeoDamage+")"+"("+carry+")");
        NeoPos.setTextFill(Color.rgb(20,194,20));
        //NeoPos.setFont(new Font(26));
        //NeoPos.setMaxSize(80,80);
        NeoPos.setStyle("-fx-font-weight: bold");
        NeoPos.setWrapText(true);
        removeNodeByRowColumnIndex(NeoX,NeoY,gridPane,"NEO");
        VBox theBox = (VBox)getNodeByRowColumnIndex(NeoX,NeoY+1);
        theBox.getChildren().add(NeoPos);
        NeoY++;

        //change damage of the hostages
        for (int i = 0; i < hostages.length; i+=3)
            changeDamage(Integer.parseInt(hostages[i]),Integer.parseInt(hostages[i+1]),gridPane,2);

        updateCarriedHostageDamage(false);
        checkDeadCarriedHostages();
    }
    public void Fly(){
        Label NeoPos = new Label("NEO"+"("+NeoDamage+")"+"("+carry+")");
        NeoPos.setTextFill(Color.rgb(20,194,20));
        //NeoPos.setFont(new Font(26));
        //NeoPos.setMaxSize(80,80);
        NeoPos.setStyle("-fx-font-weight: bold");
        NeoPos.setWrapText(true);
        VBox padBox = removeNodeByRowColumnIndex(NeoX,NeoY,gridPane,"NEO");
        String[] index = flyIndex(padBox);

        NeoX = Integer.parseInt(index[0]);
        NeoY = Integer.parseInt(index[1]);

        VBox theBox = (VBox)getNodeByRowColumnIndex(NeoX,NeoY);
        theBox.getChildren().add(NeoPos);

        //change damage of the hostages
        for (int i = 0; i < hostages.length; i+=3)
            changeDamage(Integer.parseInt(hostages[i]),Integer.parseInt(hostages[i+1]),gridPane,2);


        updateCarriedHostageDamage(false);
        checkDeadCarriedHostages();
    }
    public void Kill(){

        VBox theBoxRight;
        VBox theBoxLeft;
        VBox theBoxDown;
        VBox theBoxUp;
        if(NeoY < numCols - 1) {
            theBoxRight = (VBox) getNodeByRowColumnIndex(NeoX, NeoY + 1);
            killAgent(theBoxRight);
        }
        if(NeoY > 0) {
            theBoxLeft = (VBox) getNodeByRowColumnIndex(NeoX, NeoY - 1);
            killAgent(theBoxLeft);
        }
        if(NeoX < numRows - 1) {
            theBoxDown = (VBox) getNodeByRowColumnIndex(NeoX + 1, NeoY);
            killAgent(theBoxDown);
        }
        if(NeoX > 0) {
            theBoxUp = (VBox) getNodeByRowColumnIndex(NeoX - 1, NeoY);
            killAgent(theBoxUp);
        }

        //change neo's Damage
        VBox neoBos = (VBox)getNodeByRowColumnIndex(NeoX,NeoY);
        changeNeoDamage(neoBos,NeoDamage,true);

        Killed.setText("KILLED: " + killed);

        //change damage of the hostages
        for (int i = 0; i < hostages.length; i+=3)
            changeDamage(Integer.parseInt(hostages[i]),Integer.parseInt(hostages[i+1]),gridPane,2);

        updateCarriedHostageDamage(false);
        checkDeadCarriedHostages();

    }
    public void takePill(){
        VBox pillBox =  (VBox) getNodeByRowColumnIndex(NeoX,NeoY);
        takeAPill(pillBox);

        //change neo's Damage
        VBox neoBos = (VBox)getNodeByRowColumnIndex(NeoX,NeoY);
        changeNeoDamage(neoBos,NeoDamage,false);

        //change damage of the hostages
        for (int i = 0; i < hostages.length; i+=3)
            changeDamage(Integer.parseInt(hostages[i]),Integer.parseInt(hostages[i+1]),gridPane,-20);

        updateCarriedHostageDamage(true);
        checkDeadCarriedHostages();

    }
    public void Carry(){

        VBox hostageBox = (VBox) getNodeByRowColumnIndex(NeoX,NeoY);
        carryHostage(hostageBox);
        saved+=1;

        for (int i = 0; i < hostages.length; i+=3)
            changeDamage(Integer.parseInt(hostages[i]),Integer.parseInt(hostages[i+1]),gridPane,2);

        updateCarriedHostageDamage(false);
        checkDeadCarriedHostages();
    }
    public void DropHostage(){
        hostageCarriedDamage.clear();
        VBox drop = (VBox) getNodeByRowColumnIndex(NeoX,NeoY);
        Saved.setText("SAVED: " + saved);
        dropHostage(drop);
        for (int i = 0; i < hostages.length; i+=3)
            changeDamage(Integer.parseInt(hostages[i]),Integer.parseInt(hostages[i+1]),gridPane,2);

    }

    public void dropHostage(VBox vBox){
        ObservableList<Node> children2 = vBox.getChildren();

        Label neo = null;
        Label NeoCarryOld = null;
        for(Node node2: children2) {

            Label label1 = (Label)node2;
            // use what you want to remove

            if(label1.getText().contains("NEO")){
                carry = 0;
                NeoCarryOld = label1;

                String host = "NEO"+"("+NeoDamage+")"+"("+carry+")";
                neo = new Label(host);

                neo.setTextFill(Color.rgb(20,194,20));
                //neo.setFont(new Font(26));
                //neo.setMaxSize(80,80);
                neo.setStyle("-fx-font-weight: bold");
                neo.setWrapText(true);
            }

        }
        vBox.getChildren().remove(NeoCarryOld);
        vBox.getChildren().add(neo);
    }


    public VBox removeNodeByRowColumnIndex(final int row, final int column, GridPane gridPane,String type) {

        ObservableList<Node> childrens = gridPane.getChildren();
        for(Node node : childrens) {
            if(node instanceof VBox && GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                VBox vboxNode = (VBox)node;

                ObservableList<Node> children1 = vboxNode.getChildren();
                for(Node node1: children1) {
                    Label label = (Label)node1; // use what you want to remove
                    if (label.getText().contains(type)) {

                        vboxNode.getChildren().remove(label);
                        return vboxNode;
                    }
                }

            }
        }
        return null;
    }

    public void changeDamage(final int row, final int column, GridPane gridPane, int Damage) {

        ObservableList<Node> childrens = gridPane.getChildren();
        for(Node node : childrens) {
            if(node instanceof VBox && GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                VBox vboxNode = (VBox)node;

                ObservableList<Node> children1 = vboxNode.getChildren();
                for(Node node1: children1) {
                    Label label = (Label)node1; // use what you want to remove
                    if (label.getText().length() > 6 && label.getText().substring(0,7).equals("HOSTAGE")) {

                        vboxNode.getChildren().remove(label);
                        int damage = Integer.parseInt(label.getText().split("[\\(\\)]")[1]);
                        damage = Math.min(100,damage+Damage);
                        damage = Math.max(0,damage);

                        String host = (damage == 100 ? "MUTANT AGENT" : "HOSTAGE"+"("+damage+")");

                        if(damage == 100) {
                            dead += 1;
                            Death.setText("DEAD: " + dead);
                        }
                        Label hostage = new Label(host);

                        hostage.setTextFill(Color.rgb(132,25,206));
                        //hostage.setFont(new Font(20));
                        //hostage.setMaxSize(80,80);
                        hostage.setStyle("-fx-font-weight: bold");
                        hostage.setWrapText(true);
                        GridPane.setHalignment(hostage, HPos.CENTER);
                        GridPane.setValignment(hostage, VPos.CENTER);

                        vboxNode.getChildren().add(hostage);
                        break;
                    }
                }
            }
        }
    }



    public String[] flyIndex(VBox vBox) {

        ObservableList<Node> children = vBox.getChildren();
        for(Node node : children) {

            Label label = (Label)node;

            // use what you want to remove
            if (label.getText().substring(0,3).equals("PAD")) {

                String flyIdx = label.getText().split("[\\(\\)]")[1];
                return flyIdx.split(",");
            }
        }
        return null;
    }

    public void killAgent(VBox vBox)
    {

        ObservableList<Node> children = vBox.getChildren();
        Label killedAgent = null;
        for(Node node : children) {

            Label label = (Label)node;

            // use what you want to remove
            if (label.getText().contains("AGENT")  || label.getText().contains("MUTANT")) {
                killed+=1;
                killedAgent = label;

            }
        }
        vBox.getChildren().remove(killedAgent);

    }

    public void changeNeoDamage(VBox vBox, int damage, boolean agent){


        ObservableList<Node> children = vBox.getChildren();

        Label addedNeo = null;
        Label removedNeo = null;
        for(Node node : children) {

            Label label = (Label)node;

            if(label.getText().contains("NEO")){
                removedNeo = label;
                if(agent)
                    damage = Math.min(100,damage+20);
                else
                    damage = Math.max(0,damage-20);

                NeoDamage = damage;
                String host = "NEO"+"("+damage+")"+"("+carry+")";
                Label neo = new Label(host);

                neo.setTextFill(Color.rgb(20,194,20));
                //neo.setFont(new Font(26));
                //neo.setMaxSize(80,80);
                neo.setStyle("-fx-font-weight: bold");
                neo.setWrapText(true);


                addedNeo = neo;
            }
        }

        vBox.getChildren().remove(removedNeo);
        vBox.getChildren().add(addedNeo);


    }

    public void takeAPill(VBox vBox){
        ObservableList<Node> children = vBox.getChildren();
        Label removedPill = null;
        for(Node node : children) {

            Label label = (Label)node;

            // use what you want to remove
            if (label.getText().contains("PILL")) {
                removedPill  = label;

            }
        }
        vBox.getChildren().remove(removedPill);
    }

    public Node getNodeByRowColumnIndex (final int row, final int column) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if(node instanceof VBox && GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }

    public void carryHostage(VBox vBox){

        ObservableList<Node> children2 = vBox.getChildren();

        Label labelRemoved=null;
        Label neo = null;
        Label NeoCarryOld = null;
        for(Node node2: children2) {

            Label label1 = (Label)node2;
            // use what you want to remove

            if(label1.getText().contains("HOSTAGE")) {
                //vBox.getChildren().remove(label1);
                labelRemoved = label1;
                int damage = Integer.parseInt(label1.getText().split("[\\(\\)]")[1]);
                hostageCarriedDamage.add(damage);
            }
            if(label1.getText().contains("NEO")){
                carry+=1;
                NeoCarryOld = label1;

                String host = "NEO"+"("+NeoDamage+")"+"("+carry+")";
                neo = new Label(host);

                neo.setTextFill(Color.rgb(20,194,20));
                //neo.setFont(new Font(26));
                neo.setStyle("-fx-font-weight: bold");
                neo.setWrapText(true);

            }

        }

        vBox.getChildren().remove(labelRemoved);
        vBox.getChildren().remove(NeoCarryOld);
        vBox.getChildren().add(neo);


    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        Simulation.steps = steps;
    }

    public void restore(){
        this.dead = 0;
        this.killed = 0;
        this.saved = 0;
    }

    public int getSolutionSize(){
        return solution.length;
    }


    public void checkDeadCarriedHostages(){

        ArrayList<Integer> idx = new ArrayList<>();
        for (int i = 0; i < hostageCarriedDamage.size(); i++) {
            if(hostageCarriedDamage.get(i) == 100) {
                idx.add(i);
                dead += 1;
                saved -=1;
                Death.setText("DEATH: " + dead);
            }
        }

        for(int index: idx)
            hostageCarriedDamage.remove(index);
    }

    public void updateCarriedHostageDamage(boolean pill){
        for (int i = 0; i < hostageCarriedDamage.size(); i++) {
            int  damage = hostageCarriedDamage.get(i);

            if(pill){
                damage = Math.max(0,damage-20);
                hostageCarriedDamage.set(i,damage);
            }
            else
            {
                damage = Math.min(100,damage+2);
                hostageCarriedDamage.set(i,damage);
            }

        }
    }

    public void toStringList(){
        System.out.println(hostageCarriedDamage.toString());
    }

}
