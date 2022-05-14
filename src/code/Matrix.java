package code;
import java.lang.reflect.Array;
import java.util.*;

import MatrixGUI.Main;

//import Nodes.Node;
//import Nodes.Operators;
//import Nodes.Position;


public class Matrix extends SearchProblem {
	static int M;                 //Numtber of rows
	static int N;                 //Number of columns
	static int C;                 //Neo Max Capacity
	static String Telephone;      //x,y Telephone booth
	static String[] pads;         //x,y pads 
	static int nodes;             //Number of nodes expanded


    //Constructor
	public Matrix(String initialState) {
		super();
		this.initialState = initialState;	
	}
//-------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------
	//Method used to generate a number number between lower and upper bounds
    public static int nextInt(int lower, int upper){
        SplittableRandom splittableRandom = new SplittableRandom();
        return splittableRandom.nextInt(lower,upper);
    }
//-------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------
    //genGrid
    public static String  genGrid()
    {

        String res = "";

         M = nextInt(5,16);
        int N = nextInt(5,16);
        res+=M+","+N+";";
        System.out.println(String.format("(M: %s N: %s)", M,N));

        //Create a new Arraylist with each entry representing a cell in the grid
        ArrayList<Integer> GridSpace = new ArrayList<Integer>(M*N);
        
        	//fill the ArrayList with an ID of the cell
        for(int i = 0; i<M*N; i++){
        	GridSpace.add(i,i);
        }
        
        //initialise the random variables C, Hostages Number, Agents Number, Pills Number, Pads Number
        int C = nextInt(1,5);
        res+=C+";";
        System.out.println(String.format("(C: %s)", C));

        //Initialise a random number of Hostages from 3 to 10
        int HostagesNum = nextInt(3,11);
        //Initialise a random number of pills from 1 to the number of hostages
        	int Pills = nextInt(1,HostagesNum+1);
        	//Find the remaining number of cells by subracting the 2 cells takes by Neo, telephonebooth, Hostages and Pilss from the
        	//Total number of cells in the grid 
        	int remaining = M*N - 2 - HostagesNum - Pills;
        	//Divide the remaining cells by 2, each half will be used as a maximum limit for the random number of 
        	//agents generated and pads generated
        int AgentsNum = nextInt(1,remaining/2); 
        int Pads = nextInt(1,remaining/4);
        Pads*=2;
        System.out.println(String.format("(Hostages: %s Agents: %s Pills: %s Pads:%s)", HostagesNum,AgentsNum,Pills,Pads));

        //Find the random initial position  of Neo, from the available cells in the Array list and remove that cell from the arrayList
        //To avoid adding two entries in the same cell.
        int NeoLocIndex = nextInt(0,GridSpace.size());
        int NeoLoc = (int) GridSpace.remove(NeoLocIndex);
        int NeoX = NeoLoc % M;
        int NeoY = NeoLoc/M;
        res+=NeoX+","+NeoY+";";
        System.out.println(String.format("(NeoX: %s, NeoY:%s)", NeoX,NeoY));

        //Find a random location of the telephone booth using the same method
        int TelephoneIndex = nextInt(0,GridSpace.size());
        int TelephoneLoc = (int) GridSpace.remove(TelephoneIndex);
        int TelephoneX = TelephoneLoc % M;
        int TelephoneY = TelephoneLoc/M;
        res+=TelephoneX+","+TelephoneY+";";
        System.out.println(String.format("(TelephoneX: %s, TelephoneY:%s)", TelephoneX,TelephoneY));

        //Initialise a random location for each agent
        for (int i=0;i<AgentsNum;i++){
            int AgentLocIndex = nextInt(0,GridSpace.size());
            int AgentLoc = GridSpace.remove(AgentLocIndex);
            int AgentX = AgentLoc%M;
            int AgentY = AgentLoc/M;
            if(i!=0){
                res+=",";
            }
            res+=AgentX+","+AgentY;
            System.out.println(String.format("(Agent%sX: %s, Agent%sY:%s)", i,AgentX,i,AgentY));

        }
        res+=";";
        
        //Initialise a random location for each pill
        for (int i=0;i<Pills;i++){
            int PillLocIndex = nextInt(0,GridSpace.size());
            int PillLoc = GridSpace.remove(PillLocIndex);
            int PillX = PillLoc%M;
            int PillY = PillLoc/M;
            if(i!=0){
                res+=",";
            }
            res+=PillX+","+PillY;
            System.out.println(String.format("(Pill%sX: %s, Pill%sY:%s)", i,PillX,i,PillY));

        }
        res+=";";
        
      //Initialise a random location for each pair of pads
        for (int i=0;i<Pads;i+=2){
            int Pad1LocIndex = nextInt(0,GridSpace.size());
            int Pad1Loc = GridSpace.remove(Pad1LocIndex);
            int Pad1X = Pad1Loc%M;
            int Pad1Y = Pad1Loc/M;
            int Pad2LocIndex = nextInt(0,GridSpace.size());
            int Pad2Loc = GridSpace.remove(Pad2LocIndex);
            int Pad2X = Pad2Loc%M;
            int Pad2Y = Pad2Loc/M;
            if(i!=0){
                res+=",";
            }
            res+=Pad1X+","+Pad1Y+","+Pad2X+","+Pad2Y;
            res+=","+Pad2X+","+Pad2Y+","+Pad1X+","+Pad1Y;
            System.out.println(String.format("(Pad%sX: %s, Pad%sY:%s)", i,Pad1X,i,Pad1Y));
            System.out.println(String.format("(Pad%sX: %s, Pad%sY:%s)", i+1,Pad2X,i+1,Pad2Y));


        }
        res+=";";



      //Initialise a random location for each Hostage
        for (int i=0;i<HostagesNum;i++){
            int HostageLocIndex = nextInt(0,GridSpace.size());
            int HostageLoc = GridSpace.remove(HostageLocIndex);
            int HostageDamage = nextInt(1,100);
            int HostageX = HostageLoc%M;
            int HostageY = HostageLoc/M;
            if(i!=0){
                res+=",";
            }
            System.out.println(String.format("(Hostage%sX: %s, Hostage%sY:%s, Hostage%sDamage: %s)", i,HostageX,i,HostageY,i,HostageDamage));
            res+=HostageX+","+HostageY+","+HostageDamage;
        }
        res+=";";
        return res;


    }
//-------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------
    //Solve
//-------------------------------------------------------------------------------------------------------------    
    public static String solve(String grid, String strategy, boolean visualize) {

        //extracting the grid info from the 'grid' String

        String[] split = grid.split(";");
        String[] Dimension =split[0].split(",");
        M = Integer.parseInt(Dimension[0]);
        N = Integer.parseInt(Dimension[1]);
        C = Integer.parseInt(split[1]);
        String Neo = split[2];
        Telephone =split[3];
        String Agents = split[4];
        String Pills=split[5];
        pads = split[6].split(",");
        String Hostages= split[7];
        
      //set boolean carry for every hostage, 1 = carried, 0 = not carried
        String[] Hostages1 = Hostages.split(",");
        Hostages = "";
        for (int i = 0; i < Hostages1.length; i = i+3){
            if(i==0)
                Hostages += Hostages1[i]+ ","+ Hostages1[i+1] + "," + Hostages1[i+2] + "," +"0";
            else
                Hostages += "," + Hostages1[i]+ "," +Hostages1[i+1] + "," + Hostages1[i+2] + "," +"0";
        }
        
        String InitialState = Neo+",0;0;" + Agents+ ";" + Pills +";" + Hostages + ";0;0;0;0;";
        
        
      //State shape = NeoX,NeoY,NeoDamage;Carried;Agent1X,Agent1Y,Agent2X,Agent2Y,...;Pill1X,Pill1Y,Pill2X,Pill2Y;
      //Hostage1X,Hostage1Y,Hostage1Damage,Hostage1Carried,Hostage2X,Hostage2Y,Hostage2Carried;
      //death;killsHostage;killsAgents;rescued
        
        // call general search method giving it a search problem as input and a search strategy
        Node result = Search.generalSearch(new Matrix(InitialState),strategy); 
        
        //return no solution if it did not find a node that passes the goal test
        if(result==null) {
        		return "No Solution";
        }
        
        //return the path solution concatenated with the number of death, number of kills and nodes expanded
        else {
        System.out.println("Solution");
        String[] stateArray = result.state.split(";");
        
        	int deaths = Integer.parseInt(stateArray[5]);
        	int kills = Integer.parseInt(stateArray[6]) +Integer.parseInt(stateArray[7]) ;
	    String path = getPath(result);
	    if(path.length()>0)	
	        		path = path.substring(1,path.length());
	    
	    	//flag visualise if set to true then initiate the GUI
	     if(visualize) {
	        	Main.setGrid(grid);
	        Main.setSolution(path);
	        Main.main(null);
	     }
	                
	    return path + ";" + deaths + ";" + kills + ";"+nodes;
	    
        }


   
    		
    }
    
//-------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------
    //GeneralSearch passed a Matrix and search strategy as input
    
//    public static Node generalSearch(SearchProblem matrix,String strategy) {
//    	
//    	Node parentNode = new Node(null, null, matrix.initialState, 0, 0);
//    	Node result;
//    	switch (strategy) {
//		case "BF":
//		    result = Search.breadthFirstSearch(parentNode);
//			break;
//		case "DF":
//			result = Search.depthFirstSearch(parentNode,Integer.MAX_VALUE);
//			break;
//		case "ID":
//			result = Search.iterativeDeepeningSearch(parentNode);
//			break;
//		case "UC":
//			result = Search.uniformCostSearch(parentNode,matrix.costComparator);
//			break;
//		case "GR1":
//			result = Search.firstGreedySearch(parentNode,matrix.greedyOneComparator);
//			break;
//		case "GR2":
//			result = Search.secondGreedySearch(parentNode, matrix.greedyThreeComparator);
//			break;
//		case "AS1":
//			result = Search.firstAStarSearch(parentNode, matrix.aStarOneComparator);
//			break;
//		case "AS2":
//			result = Search.secondAStarSearch(parentNode,matrix.aStarThreeComparator);
//			break;
//		default:
//			System.out.println("Enter a correct search strategy name!");
//			return null ;
//		}
//    		return result;
//    }
    
    public static String getPath(Node n) {
    		if(n.parent==null)
    			return "";
    		else {
    			return getPath(n.parent) + ","+ n.operators;
    		}
//    		if(n.parent==null)
//			return "";
//		else {
//			return getPath(n.parent) + "\n"+ n;
//		}
    }
//-------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------
    
    public static void main(String[] args){
    	Matrix.C = 1;
    	Matrix.M=5;
    	Matrix.N = 5;

//	    	System.out.println(Expand.DOWN(new Node(null,null,"0,0,20;0;0,1,1,1;2,1;1,0,100,0;0;0;0;0;",0,0)));
	    	
//	    System.out.println(solve("3,3;2;1,0;2,1;2,2;0,1;0,1,0,2,0,2,0,0;1,1,20;", "ID", false));
//	    System.out.println(solve("4,4;2;0,0;2,1;0,1,1,1;2,2;0,1,0,2,0,2,0,0;0,2,16;", "DF", false));
//	    System.out.println(solve("4,4;1;0,0;3,0;0,1,1,1,2,1;2,2;3,1,3,3,3,3,3,1;0,2,16,0,3,12,1,3,15;", "ID", false));
    	
//    	System.out.println(solve("5,5;2;0,4;1,4;0,1,1,1,2,1,3,1,3,3,3,4;1,0,2,4;0,3,4,3,4,3,0,3;0,0,30,3,0,80,4,4,80", "AS1", false));
//    	
//    	System.out.println(solve("5,5;2;0,0;1,2;0,1,0,2,2,1,2,2,2,3,3,1,4,3;2,4;0,4,3,4,3,4,0,4;0,3,40,1,4,74,4,4,10,4,0,30", "AS1", false));
    	
    	
    	String grid0 = "5,5;2;4,3;2,1;2,0,0,4,0,3,0,1;3,1,3,2;4,4,3,3,3,3,4,4;4,0,17,1,2,54,0,0,46,4,1,22";
    	String grid1 = "5,5;1;1,4;1,0;0,4;0,0,2,2;3,4,4,2,4,2,3,4;0,2,32,0,1,38";
    	String grid2 = "8,8;1;2,4;5,3;0,4,1,4,3,0,7,7,5,6;0,1,1,3;4,4,3,1,3,1,4,4,0,7,7,0,7,0,0,7;0,2,28,4,0,30,5,5,5";
    	String grid3 = "6,6;2;2,4;2,2;0,4,1,4,3,0,4,2;0,1,1,3;4,4,3,1,3,1,4,4;0,0,94,1,2,38,4,1,76,4,0,80";
    	String grid4 = "7,7;3;0,0;0,6;0,3,0,4,2,3,4,5,6,6,5,4;0,2,4,3;2,0,0,5,0,5,2,0;1,0,83,2,5,38,6,4,66,2,6,20";
    	String grid5 = "5,5;2;0,4;1,4;0,1,1,1,2,1,3,1,3,3,3,4;1,0,2,4;0,3,4,3,4,3,0,3;0,0,30,3,0,80,4,4,80";
    	String grid6 = "6,6;2;2,2;2,4;0,1,1,0,3,0,4,1,4,3,3,4,1,4,0,3;0,2;1,3,4,2,4,2,1,3;0,0,2,0,4,2,4,0,2,4,4,98,1,1,98";
    	String grid7 = "7,7;4;3,3;0,2;0,1,1,0,1,1,1,2,2,0,2,2,2,4,2,6,1,4;5,5,5,0;5,1,2,5,2,5,5,1;0,0,98,3,2,98,4,4,98,0,3,98,0,4,98,0,5,98,5,4,98";
    	String grid8 = "7,7;4;5,3;2,5;0,0,0,2,0,6,2,3,5,1;1,1,2,6,6,0;4,0,1,6,1,6,4,0;0,4,33,1,4,1,4,3,11,5,4,2,5,5,69,3,1,95";
    	String grid9 = "7,7;3;4,3;3,2;1,0,1,1,1,3,2,5,5,3,5,5;4,2,2,6,6,2,4,4;1,4,6,4,6,4,1,4,4,0,6,6,6,6,4,0,6,0,0,6,0,6,6,0;0,2,98,1,5,26,3,3,70,6,3,90,6,5,32";
    	String grid10 = "6,6;1;2,2;2,4;0,1,1,0,3,0,4,1,4,3,3,4,1,4,0,3,1,5;0,2;1,3,4,2,4,2,1,3;0,5,90,1,2,92,4,4,2,5,5,1,1,1,98";
    	String grid11 = "9,9;2;8,0;3,5;0,1,0,3,1,0,1,1,1,2,0,7,1,8,3,8,6,1,6,5;0,6,2,8;8,1,4,5,4,5,8,1;0,0,95,0,2,98,0,8,94,2,5,13,2,6,39";

    	String grid0b = "5,5;2;3,4;1,2;0,3,1,4;2,3;4,4,0,2,0,2,4,4;2,2,91,2,4,62";
    	String grid1b = "5,5;1;1,4;1,0;0,4;0,0,2,2;3,4,4,2,4,2,3,4;0,2,32,0,1,38";
    	String grid2b = "5,5;2;3,2;0,1;4,1;0,3;1,2,4,2,4,2,1,2,0,4,3,0,3,0,0,4;1,1,77,3,4,34";
    	String grid3b = "5,5;1;0,4;4,4;0,3,1,4,2,1,3,0,4,1;4,0;2,4,3,4,3,4,2,4;0,2,98,1,2,98,2,2,98,3,2,98,4,2,98,2,0,1";
    	String grid4b = "5,5;1;0,4;4,4;0,3,1,4,2,1,3,0,4,1;4,0;2,4,3,4,3,4,2,4;0,2,98,1,2,98,2,2,98,3,2,98,4,2,98,2,0,98,1,0,98";
    	String grid5b = "5,5;2;0,4;3,4;3,1,1,1;2,3;3,0,0,1,0,1,3,0;4,2,54,4,0,85,1,0,43";
    	String grid6b = "5,5;2;3,0;4,3;2,1,2,2,3,1,0,0,1,1,4,2,3,3,1,3,0,1;2,4,3,2,3,4,0,4;4,4,4,0,4,0,4,4;1,4,57,2,0,46";
    	String grid7b = "5,5;3;1,3;4,0;0,1,3,2,4,3,2,4,0,4;3,4,3,0,4,2;1,4,1,2,1,2,1,4,0,3,1,0,1,0,0,3;4,4,45,3,3,12,0,2,88";
    	String grid8b = "5,5;2;4,3;2,1;2,0,0,4,0,3,0,1;3,1,3,2;4,4,3,3,3,3,4,4;4,0,17,1,2,54,0,0,46,4,1,22";
    	
    	String grid9b = "5,5;2;0,4;1,4;0,1,1,1,2,1,3,1,3,3,3,4;1,0,2,4;0,3,4,3,4,3,0,3;0,0,30,3,0,80,4,4,80";
    	String grid10b = "5,5;4;1,1;4,1;2,4,0,4,3,2,3,0,4,2,0,1,1,3,2,1;4,0,4,4,1,0;2,0,0,2,0,2,2,0;0,0,62,4,3,45,3,3,39,2,3,40";
    	
    String	gridc = "5,5;2;0,4;1,4;0,1,1,1,2,1,3,1,3,3,3,4;1,0,2,4;0,3,4,3,4,3,0,3;0,0,30,3,0,80,4,4,80";
    	
//    System.out.println("Grid 0");
//    System.out.println(solve(grid0b, "UC", false) + " UC");
////    System.out.print("UC");
//    	System.out.println();
//    	System.out.println(solve(grid0b, "AS1", false)+ " AS1");
////    	System.out.print("AS1");
//    	System.out.println();
//    	System.out.println(solve(grid0b, "AS2", false) + " AS2");
////    	System.out.print("AS2");
//    	System.out.println();
////    	System.out.println("----------------------------------------------------------------------------------------------");
////    	System.out.println(solve(grid1b, "UC", false));
////    	System.out.println();
////    	System.out.println(solve(grid1b, "AS1", false));
////    	System.out.println();
////    	System.out.println(solve(grid1b, "AS2", false));
////    	System.out.println("----------------------------------------------------------------------------------------------");
////    	System.out.println(solve(grid2b, "UC", false));
////    	System.out.println();
////    	System.out.println(solve(grid2b, "AS1", false));
////    	System.out.println();
////    	System.out.println(solve(grid2b, "AS2", false));
//    	System.out.println("----------------------------------------------------------------------------------------------");
//    System.out.println("Grid 3");
//    	System.out.println(solve(grid3b, "UC", false) + " UC");
////    System.out.print("UC");
//    	System.out.println();
//    	System.out.println(solve(grid3b, "AS1", false)+ " AS1");
////    	System.out.print("AS1");
//    	System.out.println();
//    	System.out.println(solve(grid3b, "AS2", false) + " AS2");
////    	System.out.print("AS2");
//    	System.out.println();
//    	System.out.println("----------------------------------------------------------------------------------------------");
//    	System.out.println("Grid 5");
//    	System.out.println(solve(grid5b, "UC", false) + " UC");
////    	System.out.print("UC");
//    	System.out.println();
//    	System.out.println(solve(grid5b, "AS1", false) + " AS1");
////    	System.out.print("AS1");
//    	System.out.println();
//    	System.out.println(solve(grid5b, "AS2", false) + " AS2");
////    	System.out.print("AS2");
//    	System.out.println();
////    	System.out.println("----------------------------------------------------------------------------------------------");
////    	System.out.println(solve(grid6b, "UC", false));
////    	System.out.println();
////    	System.out.println(solve(grid6b, "AS1", false));
////    	System.out.println();
////    	System.out.println(solve(grid6b, "AS2", false));
////    	System.out.println("----------------------------------------------------------------------------------------------");
////    System.out.println(solve(grid7b, "UC", false));
////    	System.out.println();
////    	System.out.println(solve(grid7b, "AS1", false));
////    	System.out.println();
////    	System.out.println(solve(grid7b, "AS2", false));
////    	System.out.println("----------------------------------------------------------------------------------------------");
////    	System.out.println(solve(grid8b, "UC", false));
////    	System.out.println();
////    	System.out.println(solve(grid8b, "AS1", false));
////    	System.out.println();
////    	System.out.println(solve(grid8b, "AS2", false));
////    	System.out.println("----------------------------------------------------------------------------------------------");
////    	System.out.println(solve(grid9b, "UC", false));
////    	System.out.println();
////    	System.out.println(solve(grid9b, "AS1", false));
////    	System.out.println();
////    	System.out.println(solve(grid9b, "AS2", false));
////    	System.out.println("----------------------------------------------------------------------------------------------");
////    System.out.println(solve(grid10b, "UC", false));
////    	System.out.println();
////    	System.out.println(solve(grid10b, "AS1", false));
////    	System.out.println();
////    	System.out.println(solve(grid10b, "AS2", false));
//    	System.out.println("----------------------------------------------------------------------------------------------");
//    	System.out.println("----------------------------------------------------------------------------------------------");
//    	System.out.println("----------------------------------------------------------------------------------------------");
//    	System.out.println("----------------------------------------------------------------------------------------------");
//    	System.out.println("----------------------------------------------------------------------------------------------");
//    	System.out.println("----------------------------------------------------------------------------------------------");
////    	System.out.println(solve(grid0, "UC", false));
////    	System.out.println();
////    	System.out.println(solve(grid0, "AS1", false));
////    	System.out.println();
////    	System.out.println(solve(grid0, "AS2", false));
////    	System.out.println("----------------------------------------------------------------------------------------------");
////    System.out.println(solve(grid1, "UC", false));
////    	System.out.println();
////    	System.out.println(solve(grid1, "AS1", false));
////    	System.out.println();
////    	System.out.println(solve(grid1, "AS2", false));
////    	System.out.println("----------------------------------------------------------------------------------------------");
////    	System.out.println(solve(grid2, "UC", false));
////    	System.out.println();
////    	System.out.println(solve(grid2, "AS1", false));
////    	System.out.println();
////    	System.out.println(solve(grid2, "AS2", false));
////    	System.out.println("----------------------------------------------------------------------------------------------");
////    	System.out.println(solve(grid3, "UC", false));
////    	System.out.println();
////    	System.out.println(solve(grid3, "AS1", false));
////    	System.out.println();
////    	System.out.println(solve(grid3, "AS2", false));
////    	System.out.println("----------------------------------------------------------------------------------------------");
////    System.out.println(solve(grid4, "UC", false));
////    	System.out.println();
////    	System.out.println(solve(grid4, "AS1", false));
////    	System.out.println();
////    	System.out.println(solve(grid4, "AS2", false));
////    	System.out.println("----------------------------------------------------------------------------------------------");
////    System.out.println(solve(grid5, "UC", false));
////    	System.out.println();
////    	System.out.println(solve(grid5, "AS1", false));
////    	System.out.println();
////    	System.out.println(solve(grid5, "AS2", false));
////    	System.out.println("----------------------------------------------------------------------------------------------");
////    	System.out.println(solve(grid6, "UC", false));
////    	System.out.println();
////    	System.out.println(solve(grid6, "AS1", false));
////    	System.out.println();
////    	System.out.println(solve(grid6, "AS2", false));
////    	System.out.println("----------------------------------------------------------------------------------------------");
////    System.out.println(solve(grid8, "UC", false));
////    	System.out.println();
////    	System.out.println(solve(grid8, "AS1", false));
////    	System.out.println();
////    	System.out.println(solve(grid8, "AS2", false));
////    	System.out.println("----------------------------------------------------------------------------------------------");
////    	System.out.println(solve(grid9, "UC", false));
////    	System.out.println();
////    	System.out.println(solve(grid9, "AS1", false));
////    	System.out.println();
////    	System.out.println(solve(grid9, "AS2", false));
//    	
//    	System.out.println("----------------------------------------------------------------------------------------------");
//    	System.out.println("Grid 10 old Version of test cases");
//    	System.out.println(solve(grid10, "UC", false) + " UC");
////    	System.out.print("UC");
//    	System.out.println();
//    	System.out.println(solve(grid10, "AS1", false) + " AS1");
////    	System.out.print("AS1");
//    	System.out.println();
//    	System.out.println(solve(grid10, "AS2", false)+ " AS2");
////    	System.out.print("AS2");
//    	System.out.println();
//    	System.out.println("----------------------------------------------------------------------------------------------");
//    	System.out.println("Grid 11 old Version of test cases");
//    System.out.println(solve(grid11, "UC", false) + " UC");
////    System.out.print("UC");
//    	System.out.println();
//    	System.out.println(solve(grid11, "AS1", false) + " AS1");
////    	System.out.print("AS1");
//    	System.out.println();
//    	System.out.println(solve(grid11, "AS2", false)+ " AS2");
////    	System.out.print("AS2");
//    	System.out.println();
//    	System.out.println("----------------------------------------------------------------------------------------------");

//    String gridTest = genGrid();
//    System.out.println("grid1");
//    System.out.println(gridTest);
    
    String gridTest = "8,10;2;4,6;0,4;0,9,7,1,0,1,4,2,3,1,3,9,6,4,1,1,0,8,0,6,5,7;1,7;4,3,6,2,6,2,4,3,5,9,0,7,0,7,5,9,2,9,7,5,7,5,2,9,1,8,3,7,3,7,1,8,0,5,7,4,7,4,0,5,6,8,2,1,2,1,6,8,0,0,1,9,1,9,0,0,1,6,0,2,0,2,1,6,1,2,7,2,7,2,1,2;7,6,91,7,8,98,6,0,17,7,0,91,3,0,71;";
    String gridTest2 = "7,12;4;1,9;5,3;3,3,3,10,2,4,3,0,6,7,6,5,2,8,5,2,0,1,2,7,5,4,2,10,2,2,0,9,1,7,3,11,3,4,6,10,2,5,6,3,5,9,5,10,6,9,6,8,4,5,5,0,0,0,6,0,3,1,4,4,3,9,1,2,6,6,1,11,5,7,3,5;0,4;4,1,3,7,3,7,4,1,5,1,5,6,5,6,5,1,4,9,2,6,2,6,4,9;3,6,96,3,2,79,4,11,36,0,10,87,5,11,60";
//    String gridTest2 = genGrid();
//    System.out.println("grid2");
//    System.out.println(gridTest2);
    
//  System.out.println("1st Example:");
//  System.out.println("BFS");
//  System.out.println(solve(gridTest, "BF", false));
  System.out.println("DFS");
  System.out.println(solve(gridTest, "DF", false));
//  System.out.println("IDS");
//  System.out.println(solve(gridTest, "ID", false));
//  System.out.println("UCS");
//  System.out.println(solve(gridTest, "UC", false));
//  System.out.println("Greedy using first heuristic function");
//  System.out.println(solve(gridTest, "GR1", false));
//  System.out.println("Greedy using second heuristic function");
//  System.out.println(solve(gridTest, "GR2", false));
//  System.out.println("A* using first heuristic function");
//  System.out.println(solve(gridTest, "AS1", false));
//  System.out.println("A* using second heuristic function");
//  System.out.println(solve(gridTest, "AS2", false));
  
//  System.out.println("2nd Example:");
//  System.out.println("BFS");
//  System.out.println(solve(gridTest2, "BF", false));
//  System.out.println("DFS");
//  System.out.println(solve(gridTest2, "DF", false));
//  System.out.println("IDS");
//  System.out.println(solve(gridTest2, "ID", false));
//  System.out.println("UCS");
//  System.out.println(solve(gridTest2, "UC", false));
//  System.out.println("Greedy using first heuristic function");
//  System.out.println(solve(gridTest2, "GR1", false));
//  System.out.println("Greedy using second heuristic function");
//  System.out.println(solve(gridTest2, "GR2", false));
//  System.out.println("A* using first heuristic function");
//  System.out.println(solve(gridTest2, "AS1", false));
//  System.out.println("A* using second heuristic function");
//  System.out.println(solve(gridTest2, "AS2", false));

//    String gridTest = genGrid();
//    System.out.println(gridTest);
//    	System.out.println(solve(grid3b, "UC", true));
   
    }





   



}
