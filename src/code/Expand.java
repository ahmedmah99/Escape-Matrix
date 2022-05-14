package code;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Expand {
	
	public static ArrayList<Node> getExpandedNodes(Node frontNode){
			
		ArrayList<Node> result = new ArrayList<Node>();
		int Neodamage = Integer.parseInt(((((frontNode.state).split(";"))[0]).split(","))[2]);
		//if Neo is alive in that node's state then continue to expand it
		if (Neodamage < 100) {
			
			//Apply each of the 9 operators on the front node and check if the expanded
			//node is not null add it to the array list of expanded nodes
			
			
			Node takepillNode = TakePill(frontNode);
			if (takepillNode != null) {
				result.add(takepillNode);
			}
			Node carryNode = CARRY(frontNode);
			if (carryNode != null) {
				result.add(carryNode);
			}
			Node upNode = UP(frontNode);
			if (upNode != null) {
				result.add(upNode);
			}
			Node leftNode = LEFT(frontNode);
			if (leftNode != null) {
				result.add(leftNode);
			}
			Node rightNode = RIGHT(frontNode);
			if (rightNode != null) {
				result.add(rightNode);
			}
			Node dropNode = DROP(frontNode);
			if (dropNode != null) {
				result.add(dropNode);
			}
			Node flyNode = FLY(frontNode);
			if (flyNode != null) {
				result.add(flyNode);
			}
			Node killNode = Kill(frontNode);
			if (killNode != null) {
				result.add(killNode);
			}
			Node downNode = DOWN(frontNode);
			if (downNode != null) {
				result.add(downNode);
			}
		}
		return result;
	}
	
	public static Node UP(Node node){
        String[] state = node.state.split(";");
        int NeoX = Integer.parseInt(state[0].split(",")[0]);
        int NeoY = Integer.parseInt(state[0].split(",")[1]);

      //check Neo is not already in top row
        if(NeoX > 0) {
        	
        			//Call helper method agentExists to check if the up movement is legal
                if (!agentExists(state, NeoX, NeoY,Operators.up)){

                		//Call isHostageToAgent to check no mutant exists in cell above and up movement is legal
                    if(!isHostageToAgent(state,NeoX,NeoY,Operators.up)) {

                        //change Neo's Position
                        setNeoLocation(state, NeoX, 0, Operators.up);

                        //increase Hostages Damage by 2
                        increaseHostagesDamage(state);

                        //create newState
                        String newState = String.join(";",state);

                        //return new Node
                        return new Node(node, Operators.up, newState, node.depth + 1, node.pathCost + 1);
                    }
                }
        }
        return null;
    }

//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------
    public static Node DOWN(Node node){

        String[] state = node.state.split(";");
        int NeoX = Integer.parseInt(state[0].split(",")[0]);
        int NeoY = Integer.parseInt(state[0].split(",")[1]);
        
        //check Neo is not already in bottom row
        if(NeoX < Matrix.M-1) {
        	
        		//Call helper method agentExists to check if the up movement is legal
                if (!agentExists(state, NeoX, NeoY,Operators.down)){
                	//Call isHostageToAgent to check no mutant exists in cell above and up movement is legal
                    if(!isHostageToAgent(state,NeoX,NeoY,Operators.down)) {
                        //change Neo's Position
                        setNeoLocation(state, NeoX, 0, Operators.down);

                        //increase Hostages Damage
                        increaseHostagesDamage(state);

                        //create newState
                        String newState = String.join(";",state);

                        //return new Node
                        return new Node(node, Operators.down, newState, node.depth + 1, node.pathCost + 1);
                    }
                   
                }
               
        }
        
        return null;
    }

//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------- 
    
    public static Node LEFT(Node node){

        String[] state = node.state.split(";");
        int NeoX = Integer.parseInt(state[0].split(",")[0]);
        int NeoY = Integer.parseInt(state[0].split(",")[1]);
        
        //check Neo is not already in left most column
        if(NeoY > 0) {
        			
            //Call helper method agentExists to check if the up movement is legal
                if (!agentExists(state, NeoX, NeoY,Operators.left)){
                	
                    //Call isHostageToAgent to check no mutant exists in cell above and up movement is legal
                    if(!isHostageToAgent(state,NeoX,NeoY,Operators.left)) {

                        //change Neo's Position
                        setNeoLocation(state, 0, NeoY, Operators.left);

                        //increase Hostages Damage
                        increaseHostagesDamage(state);


                        //create newState
                        String newState = String.join(";",state);

                        //return new Node
                        return new Node(node, Operators.left, newState, node.depth + 1, node.pathCost + 1);
                    }
                }

        }
        return null;
    }

//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------
    
    public static Node RIGHT(Node node){
        String[] state = node.state.split(";");
        int NeoX = Integer.parseInt(state[0].split(",")[0]);
        int NeoY = Integer.parseInt(state[0].split(",")[1]);
        
      //check Neo is not already in right most column
        if(NeoY < Matrix.N-1) {

        			//Call helper method agentExists to check if the up movement is legal
                if (!agentExists(state, NeoX, NeoY,Operators.right)){

                    //Call isHostageToAgent to check no mutant exists in cell above and up movement is legal

                    if(!isHostageToAgent(state,NeoX,NeoY,Operators.right)) {

                        //change Neo's Position
                        setNeoLocation(state, 0, NeoY, Operators.right);

                        //increase Hostages Damage
                        increaseHostagesDamage(state);

                        //create newState
                        String newState = String.join(";",state);
                        
                        //return new Node
                        return new Node(node, Operators.right, newState, node.depth + 1, node.pathCost + 1);                     
                    
                    }
                }
 
        }
        return null;
    }
    
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------

//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------    
//--------------------------------------------------------------------------------------------------------
      
    public static Node CARRY(Node node){
    		
    	 	String [] stateArray = node.state.split(";");
    		String Neo =stateArray[0]; 
        int NeoX = Integer.parseInt(Neo.split(",")[0]);
        int NeoY = Integer.parseInt(Neo.split(",")[1]);
        int carried = Integer.parseInt(stateArray[1]);
       
        String hostagesString = node.state.split(";")[4];
        String[] hostages = hostagesString.split(",");
        String newHostagesString = "";
   
        
   		//find hostage to carry:
		//check hostage in same cell and not already carried
        //check that hostage is not mutant
        //check carried<C
        boolean found = false;
        
        for(int i=0;i<hostages.length;i+=4) {
        		if(!hostages[i].equals("")) {
	        		int hostageX =Integer.parseInt(hostages[i]);
	        		int hostageY = Integer.parseInt(hostages[i+1]);
	        		int hostagedamage = Integer.parseInt(hostages[i+2]);
	        		int hostageIsCarried = Integer.parseInt(hostages[i+3]);
	        		//if that hostage is found and Neo has capacity to carry them
	        		//then change their carried flag to be 1 and increment the number of hostages carried by Neo
	        		if(hostageX==NeoX && hostageY==NeoY && hostagedamage<100 && carried<Matrix.C && hostageIsCarried==0) {
	        			found = true;
	        			stateArray[1] = ++carried + "";
	        			hostageIsCarried  = 1;
	        		}
	        		newHostagesString+=hostageX+","+hostageY+","+hostagedamage+","+ hostageIsCarried +",";
        		}
        }
        //format string containing hostages array
        if(newHostagesString.length()>0)
        		newHostagesString = newHostagesString.substring(0, newHostagesString.length()-1);
		stateArray[4] = newHostagesString;
		
		//call method to increase hostages damage
		increaseHostagesDamage(stateArray);
		
		//Concatenate new state of node
		String state = "";
		for(int i=0;i<stateArray.length;i++) {
			state+=stateArray[i] + ";";
		}

		//if no hostage was carried then don't create a new node
		if(found)
			return new Node(node,Operators.carry,state,node.depth+1,node.pathCost+1); 
		return null;

    }
    
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------

//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------    
//--------------------------------------------------------------------------------------------------------    
    
    public static Node DROP(Node node){
		
	 	String [] stateArray = node.state.split(";");
		String Neo =stateArray[0]; 
	    int NeoX = Integer.parseInt(Neo.split(",")[0]);
	    int NeoY = Integer.parseInt(Neo.split(",")[1]);
	    int NeoDamage = Integer.parseInt(Neo.split(",")[2]);
	    int TelephoneX = Integer.parseInt(Matrix.Telephone.split(",")[0]);
	    int TelephoneY = Integer.parseInt(Matrix.Telephone.split(",")[1]);
	    
	    boolean dropped = false;
	  //Check Neo Position = position of telephone booth
	    if(NeoX==TelephoneX && NeoY==TelephoneY) {
		  //find hostages in array that neo is carrying 
		   
		    String hostagesString = node.state.split(";")[4];
		    String[] hostages = hostagesString.split(",");
		    String newHostagesString = "";
		    
		    //find hostages to drop
		    for(int i=0;i<hostages.length;i+=4) {
		    		if(!hostages[i].equals("")) {
			    		int hostageX =Integer.parseInt(hostages[i]);
			    		int hostageY = Integer.parseInt(hostages[i+1]);
			    		int hostagedamage = Integer.parseInt(hostages[i+2]);
			    		int hostageIsCarried = Integer.parseInt(hostages[i+3]);
			    		//if that hostage is found and Neo has capacity to carry them
			    		//then change their carried flag to be 1 and increment the number of hostages carried by Neo
			    		if(hostageIsCarried==1) {
			    			int rescued = Integer.parseInt(stateArray[8]);
			    			if(hostagedamage<100)
			    				rescued+=1;
		        	 		stateArray[8] = ""+ rescued;
			    			
			    			//reset carried to be 0	    			
			    			stateArray[1] = "0";
			    			dropped = true;
			    		}
			    		else {
			    		//remove hostages from array by not adding the hostage to the new state   
			    		newHostagesString+=hostageX+","+hostageY+","+hostagedamage+","+ hostageIsCarried +",";
			    		}
		    		}
		    }
		    //format string containing hostages array
		    if(newHostagesString.length()>0)
		    		newHostagesString = newHostagesString.substring(0, newHostagesString.length()-1);
			stateArray[4] = newHostagesString;
			
			//call method to increase hostages damage
			increaseHostagesDamage(stateArray);
			
			//Concatenate new state of node
			String state = "";
			for(int i=0;i<stateArray.length;i++) {
				state+=stateArray[i] + ";";
			}
			if(dropped)
				return new Node(node,Operators.drop,state,node.depth+1,node.pathCost+1); 
			else {
				return null;
			}
	    }
	    return null;
    }
 
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------

//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------


    public static Node TakePill(Node node){
		
	 	String [] stateArray = node.state.split(";");
		String Neo =stateArray[0]; 
	    int NeoX = Integer.parseInt(Neo.split(",")[0]);
	    int NeoY = Integer.parseInt(Neo.split(",")[1]);
	    int NeoDamage = Integer.parseInt(Neo.split(",")[2]);
	    
	    boolean foundPill = false;
 
		   
	    String pillsString = node.state.split(";")[3];
	    String[] pills = pillsString.split(",");
	    String newPillsString = "";
	    
	    //find pill to take
	    for(int i=0;i<pills.length;i+=2) {
	    		if(!pills[i].equals("")) {
		    		int pillX =Integer.parseInt(pills[i]);
		    		int pillY = Integer.parseInt(pills[i+1]);
		    		//check that neo position = pill position
		    		if(NeoX==pillX && NeoY == pillY) {
		    			foundPill = true;
		    			//decrement Neo's health by 20 and doesn't go below 0
		    			NeoDamage = Math.max(0, NeoDamage-=20);
		    			stateArray[0] = NeoX + "," + NeoY + ","  + NeoDamage;
		    			
		    			
		    			//decrement hostages health by 22 if not mutant and doesn't go below 0	    			
		    			String hostagesString = node.state.split(";")[4];
		    		    String[] hostages = hostagesString.split(",");
		    		    String newHostagesString = "";
		    		    
		    		    //change damage for all hostages
		    		    for(int j=0;j<hostages.length;j+=4) {
		    		    		if(!hostages[j].equals("")) {
			    		    		int hostageX =Integer.parseInt(hostages[j]);
			    		    		int hostageY = Integer.parseInt(hostages[j+1]);
			    		    		int hostagedamage = Integer.parseInt(hostages[j+2]);
			    		    		int hostageIsCarried = Integer.parseInt(hostages[j+3]);
			    		    		if(hostagedamage<100) {
			    		    			hostagedamage = Math.max(2, hostagedamage-=22);
			    		    		}
			    		    		newHostagesString+=hostageX+","+hostageY+","+hostagedamage+","+ hostageIsCarried +",";
		    		    		}
	 		 		
		    		    }
		    		  //format string containing hostages array
		    		    if(newHostagesString.length()>0)
		    		    		newHostagesString = newHostagesString.substring(0, newHostagesString.length()-1);
		    			stateArray[4] = newHostagesString;
		    		}
		    		else {
		    			//remove that pill from list
		    			newPillsString+=pillX+"," + pillY+",";
		    		}
	    		}
	    }
	    if(newPillsString.length()>0)
	    		newPillsString = newPillsString.substring(0, newPillsString.length()-1);
	    stateArray[3] = newPillsString;
		
		//call method to increase hostages damage
		increaseHostagesDamage(stateArray);
		
		//Concatenate new state of node
		String state = "";
		for(int i=0;i<stateArray.length;i++) {
			state+=stateArray[i] + ";";
		}
		if(foundPill)
			return new Node(node,Operators.takePill,state,node.depth+1,node.pathCost+1); 
		else {
			return null;
		}

 }
 
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------

//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------


    public static Node FLY(Node node){
		
	 	String [] stateArray = node.state.split(";");
		String Neo =stateArray[0]; 
	    int NeoX = Integer.parseInt(Neo.split(",")[0]);
	    int NeoY = Integer.parseInt(Neo.split(",")[1]);
	    int Neodamage = Integer.parseInt(Neo.split(",")[2]);
	    
	    
	    boolean flied = false;


		//find starting pad in same position as Neo
	    for(int i=0;i<Matrix.pads.length;i+=4) {
	    		if(!Matrix.pads[i].equals("")) {
		    		int startX =Integer.parseInt(Matrix.pads[i]);
		    		int startY = Integer.parseInt(Matrix.pads[i+1]);
		    		int endX = Integer.parseInt(Matrix.pads[i+2]);
		    		int endY = Integer.parseInt(Matrix.pads[i+3]);
		    		//If that pad is found
		    		if(startX==NeoX && startY==NeoY) {
	    				//transport neo's location to new cell
		    			NeoX = endX;		
		    			NeoY = endY;
		    			flied = true;
		    			break;

		    		}
	    }
		    }	
			stateArray[0] = NeoX + "," + NeoY + ","+Neodamage;
			
			//update damage of hostages
			increaseHostagesDamage(stateArray);
			
			//Concatenate new state of node
			String state = "";
			for(int i=0;i<stateArray.length;i++) {
				state+=stateArray[i] + ";";
			}
			if(flied)
				return new Node(node,Operators.fly,state,node.depth+1,node.pathCost+1); 
			else {
				return null;
			}
	  
	    
 }
 
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------

//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------


    public static Node Kill(Node node) {
	 
 String [] stateArray = node.state.split(";");
 String[] NeoArray =stateArray[0].split(","); 
 
 int NeoX = Integer.parseInt(NeoArray[0]);
 int NeoY = Integer.parseInt(NeoArray[1]);
 int NeoDamage = Integer.parseInt(NeoArray[2]);

 String hostagesString = stateArray[4];
 String[] hostages = hostagesString.split(",");
 String newHostagesString = "";
 
 	//call helper method to check that the kil actions is legal (the hostage in the same cell as neo
 	//wont die if Neo performs a kill
 	if(canKill(NeoX, NeoY, hostages)) { 
		 String agentsString = stateArray[2];
		 String[] agents = agentsString.split(",");
		 String newAgentsString= "";
		
		
		 boolean found = false;
		 int hostagesKills = Integer.parseInt(stateArray[6]);
		 //Iterate over hostages to find mutant
		 for(int i=0;i<hostages.length;i+=4) {
		 		if(!hostages[i].equals("")) {
		     		int hostageX =Integer.parseInt(hostages[i]);
		     		int hostageY = Integer.parseInt(hostages[i+1]);
		     		int hostagedamage = Integer.parseInt(hostages[i+2]);
		     		int hostageIsCarried = Integer.parseInt(hostages[i+3]);
		     		 //check if mutant hostage exists in neighbouring cell
		     		if((Math.abs(NeoX-hostageX)==1 && Math.abs(NeoY-hostageY)==0 || 
		     			Math.abs(NeoX-hostageX)==0 && Math.abs(NeoY-hostageY)==1) && hostageIsCarried==0 && hostagedamage==100) {
		     			//if found remove agent/hostage from string
		        		 	//increment kills
		        	 		hostagesKills++;
		     			found = true;
		     		}
		     		else {
			    		//remove hostages from array by not adding the hostage to the new state   
			    		newHostagesString+=hostageX+","+hostageY+","+hostagedamage+","+ hostageIsCarried +",";
			    		}
		     		
		    		}
		    }
		 	//adjust formatting of hostages array string
		    if(newHostagesString.length()>0)
		    		newHostagesString = newHostagesString.substring(0, newHostagesString.length()-1);
			stateArray[4] = newHostagesString;
			
			
			//Iterate over agents
			int agentKills = Integer.parseInt(stateArray[7]);
			for(int i=0;i<agents.length;i+=2) {
		 		if(!agents[i].equals("")) {
		     		int agentX =Integer.parseInt(agents[i]);
		     		int agentY = Integer.parseInt(agents[i+1]);
		     		 //check if agent exists in neighbouring cell
		     		if((Math.abs(NeoX-agentX)==1 && Math.abs(NeoY-agentY)==0 || 
		         			Math.abs(NeoX-agentX)==0 && Math.abs(NeoY-agentY)==1)) {
		     			//if found remove agent/hostage from string
		     			//increment kills
		     			agentKills++;	
		     			found = true;
		     		}
		     		else {
			    		//remove agents from array by not adding the agents to the new state   
			    		newAgentsString+=agentX+","+agentY+",";
			    		}
		    		}
		    }
			//adjust formatting of agents array string
		    if(newAgentsString.length()>0)
		    		newAgentsString = newAgentsString.substring(0, newAgentsString.length()-1);
			stateArray[2] = newAgentsString;
			
			//update values of number of agents killed and hostages killed in state
			stateArray[6] = ""+ hostagesKills;
			stateArray[7] = ""+ agentKills;
			//if neo performed a kill then increase the damage of neo
			if(found) {
				NeoDamage+=20;
				NeoDamage = Math.min(100, NeoDamage);
				stateArray[0] = NeoX + "," + NeoY + ","  + NeoDamage;
			}
			
			//call method to increase hostages damage
			increaseHostagesDamage(stateArray);
			
			
			//Concatenate new state of node
			String state = "";
			for(int i=0;i<stateArray.length;i++) {
				state+=stateArray[i] + ";";
			}
			if(found)
				return new Node(node,Operators.kill,state,node.depth+1,node.pathCost+1); 
			else {
				return null;
			}
	 }
	 else {
		 return null;
	 }
 }
  

//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------
//-------------------------------------------Helper Methods-----------------------------------------------
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------


    //This method updates Neo's location in the state String, it checks the operator applied first.
    private static void setNeoLocation(String[] state, int NeoX,int NeoY,Operators operators){

     switch (operators){
     	
     	//If the operator is up then decrement the X position by 1
         case up : {
             List<String> newState = Arrays.asList(state[0].split(","));
             newState.set(0,String.valueOf(NeoX-1));

             String Joined = String.join(",",newState);
             state[0] = Joined;

         }break;
         
       //If the operator is down then increment the X position by 1
         case down : {

             List<String> newState = Arrays.asList(state[0].split(","));
             newState.set(0,String.valueOf(NeoX+1));

             String Joined = String.join(",",newState);
             state[0] = Joined;

         }break;
         
       //If the operator is right then increment the Y position by 1
         case right : {
             List<String> newState = Arrays.asList(state[0].split(","));
             newState.set(1,String.valueOf(NeoY+1));

             String Joined = String.join(",",newState);
             state[0] = Joined;

         }break;
         
       //If the operator is left then decrement the X position by 1
         case left : {
             List<String> newState = Arrays.asList(state[0].split(","));
             newState.set(1,String.valueOf(NeoY-1));

             String Joined = String.join(",",newState);
             state[0] = Joined;

         }break;
     }
 }
 
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------
 

    //This method checks if there exists a mutant hostage exists in the neighbouring cell
    private static boolean isHostageToAgent(String[] state, int NeoX, int NeoY,Operators operator){
    String hostages = state[4];

    String[] HostagesinArray = hostages.split(",");   
    for(int i=0;i<HostagesinArray.length;i+=4) {
    		if(!HostagesinArray[i].equals("")) {
	        int x = Integer.parseInt(HostagesinArray[i]);
	        int y = Integer.parseInt(HostagesinArray[i+1]);
	        int damage = Integer.parseInt(HostagesinArray[i+2]);
	        int carry = Integer.parseInt(HostagesinArray[i+3]);
	
	        switch (operator)
	        {
	        		//If the operator is up then return true if a mutant exists in the above cell
	            case up : {
	                if((NeoX-1==x) && (NeoY==y) && (damage >= 98) && (carry == 0))
	                    return true;
	            }break;
	            
	          //If the operator is down then return true if a mutant exists in the below cell
	            case down : {
	                if((NeoX+1==x) && (NeoY==y) && (damage  >= 98) && (carry == 0))
	                    return true;
	            }break;
	            
	          //If the operator is left then return true if a mutant exists in the left cell
	            case left : {
	                if((NeoX==x) && (NeoY-1==y) && (damage  >= 98) && (carry == 0))
	                    return true;
	            }break;
	            
	          //If the operator is right then return true if a mutant exists in the right cell
	            case right : {
	                if((NeoX==x) && (NeoY+1==y) && (damage >= 98) && (carry == 0))
	                    return true;
	            }break;
	        }
    		}
    		else {
    			return false;
    		}

    }
    return false;
}

//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------


//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------

  //This method checks if there exists an agent exists in the neighbouring cell
    public static boolean agentExists(String[] state, int NeoX,int  NeoY, Operators operator) {

    	//Iterate over agents 
    String[] agents = state[2].split(",");
    for(int i=0;i<agents.length;i+=2) {
    	if(!agents[i].equals("")) {
    	int x = Integer.parseInt(agents[i]);
    	int y = Integer.parseInt(agents[i+1]);
        switch (operator)
        {
        		//If the operator is up then return true if an agent exists in the above cell
            case up : {
                if((NeoX-1==x) && (NeoY==y) )
                    return true;
            }break;
            
          //If the operator is down then return true if an agent exists in the below cell
            case down : {
                if((NeoX+1==x) && (NeoY==y) )
                    return true;
            }break;
            
          //If the operator is left then return true if an agent exists in the left cell
            case left : {
                if((NeoX==x) && (NeoY-1==y) )
                    return true;
            }break;
            
          //If the operator is right then return true if an agent exists in the right cell
            case right : {
                if((NeoX==x) && (NeoY+1==y))
                    return true;
            }break;
        }
    		}
    }
    return false;
}

//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------


//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------

    //This method applies the increase in damage by 2 in each step for all hostages
    private static void increaseHostagesDamage(String[] state)
    {
	 String hostagesString = state[4];
	 if(hostagesString.length()>0) {
	     List<String> hostages = Arrays.asList(hostagesString.split(","));
	     
	     //Iterate over hostages
	     for(int i=0;i<hostages.size();i+=4) {
	         // increase damage and set the new damage
	         int damage = Integer.parseInt(hostages.get(i+2));
	         int carried = Integer.parseInt(hostages.get(i+3));
	         //If damage is 98 or 99 before increase then the hostage will die in this step
	         if(damage==98 || damage==99) {
	        	 	int deaths = Integer.parseInt(state[5]);
	        	 	deaths++;
	        	 	state[5] = ""+ deaths;
	         }
	         damage += 2;
	         //ensure damage doesn't cross 100
	         damage = Math.min(damage, 100);
	
	         hostages.set(i+2,String.valueOf(damage));

	     }
	
	
	     String newHostages = String.join(",",hostages);
	     state[4] = newHostages;
	 }
}

//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------

//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------


    
    public static boolean canKill(int NeoX, int NeoY, String[] hostages) {
	int length = hostages.length/4;
	
	//Iterate over hostages
	for(int i=0;i<length;i++) {
		int index = i*4;
		int hostageX = Integer.parseInt(hostages[index]);
		int hostageY = Integer.parseInt(hostages[index+1]);
		int hostageDamage = Integer.parseInt(hostages[index+2]);
		int hostageIsCarried = Integer.parseInt(hostages[index+3]);
		
		//If there exists a hostage in the same cell as Neo and this hostage isn't already carried
		//and the damage of the hostage will lead them to die if Neo perfroms a kill then return false
		if(NeoX==hostageX && NeoY==hostageY && hostageIsCarried==0 && hostageDamage>=98) {
			return false;
		}	
	}
	return true;
}


}
