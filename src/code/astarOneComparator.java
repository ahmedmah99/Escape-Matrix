package code;

import java.util.Comparator;

public class astarOneComparator implements Comparator<Node> {
	
	@Override
	public int compare(Node n1, Node n2) {
		int[] DandK1 = getEstimatedDeathsAndKills(n1);
		int[] DandK2 = getEstimatedDeathsAndKills(n2);
		int estimatedDeaths1 = DandK1[0];
		int estimatedDeaths2 = DandK2[0];
		int estimatedKills1 = DandK1[1];
		int estimatedKills2 = DandK2[1];
		

		String[] stateArray1 = n1.state.split(";");
		String[] stateArray2 = n2.state.split(";");
		
		int hostagesKilled1 = Integer.parseInt(stateArray1[6]);
		int hostagesKilled2 = Integer.parseInt(stateArray2[6]);
		
		int agentsKilled1 = Integer.parseInt(stateArray1[7]);
		int agentsKilled2 = Integer.parseInt(stateArray2[7]);
		
		int kills1 = hostagesKilled1+agentsKilled1;
		int kills2 = hostagesKilled2+agentsKilled2;
		
		int hostagesDead1 = Integer.parseInt(stateArray1[5]);
		int hostagesDead2 = Integer.parseInt(stateArray2[5]);
		
		if(hostagesDead1+estimatedDeaths1<hostagesDead2+estimatedDeaths2) 
			return -1;		
		if(hostagesDead2+estimatedDeaths2<hostagesDead1+estimatedDeaths1)
			return 1;
		else {
			if(kills1+estimatedKills1<kills2+estimatedKills2)
				return -1;
			if(kills2+estimatedKills2<kills1+estimatedKills1)
				return 1;
			else {
				if(n1.depth<n2.depth)
					return -1;
				if(n2.depth<n1.depth)
					return 1;
				else {
					return 0;
				}
			}		
		}
	}

//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------

	public int[] getEstimatedDeathsAndKills(Node n) {
		int deaths = 0;
		int kills = 0;
		//Retrieve Neo's location, the hostages array, the pills array from the state
		//And split the string containing the location of the telephone booth
		String[] stateArray = n.state.split(";");
		String[] hostages = stateArray[4].split(",");
		String[] Neo = stateArray[0].split(",");
		String[] pills = stateArray[3].split(",");
		int neoX = Integer.parseInt(Neo[0]);
		int neoY = Integer.parseInt(Neo[1]);
		String[] Telephone = Matrix.Telephone.split(",");
		int tbX = Integer.parseInt(Telephone[0]);
		int tbY = Integer.parseInt(Telephone[1]);
		
		//Iterate over hostages array
		int length = hostages.length/4;
		for(int i=0;i<length;i++) {
			int index = i*4;
			int isCarried = Integer.parseInt(hostages[index+3]);
			int hostageDamage = Integer.parseInt(hostages[index+2]);
			//For each hostage consider 3 cases
			
			//First we check that the hostage is already alive and not carried
			if(isCarried==0 &&hostageDamage<100) {
				int stepsNeeded =0;
				int hostageX = Integer.parseInt(hostages[index]);
				int hostageY = Integer.parseInt(hostages[index+1]);
				String[] stepsNeoToH = getMinSteps(neoX, neoY, hostageX, hostageY,pills , "", "");
				stepsNeeded += Integer.parseInt(stepsNeoToH[0]) ;
				//Case 1: The damage of the hostage is not sufficient for Neo to reach them, found by calculating
				//the minimum number of steps to reach hostage then we predict that Neo will
				//have to kill the hostage
				if(stepsNeeded+hostageDamage>=100) {
					kills++;
				}
				String[] stepsHToTB = getMinSteps(hostageX, hostageY, tbX, tbY,pills , stepsNeoToH[1], stepsNeoToH[2]);
				stepsNeeded+=Integer.parseInt(stepsHToTB[0]);
				//Case 2: The damage of the hostage is not sufficient for Neo to reach that individual
				//hostage and take them to TB which is calculated by finding the minum damages than can be
				//imposed on hostage for Neo just to reach them and take them to TB using the getMinSteps function
				//then we predict the hostage will die
				if(stepsNeeded + hostageDamage>=100) {
					deaths++;
				}
			}
			//Case 3: The hostage is already dead and neo is not carrying the hostage therefore we predict
			//neo will kill the hostage
			if(isCarried==0 && hostageDamage>=100)
				kills++;
		}
		//{deaths,kills}
		int[] res = new int[2];
		res[0] = deaths;
		res[1] = kills;
		
		return res;
	}
	
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------	

//This method calls a helper function getMinDistanceBetweenNeoAndItem which return the minumum
//distance between any 2 points in the grid considering the pads, then using this distance
//it considers a number of paths between any 2 points and returns the minimum damage that can
//be obtained by following any of these paths, first it find the damage (distance * 2) by going
//directly from point A to point B then it iterates over all the pills in the array
//and retrieves the pill that if neo takes a detour to and then goes to point B will cause
//the least damage and cause a damage less than moving directly
	public String[] getMinSteps(int startX, int startY, int endX, int endY, String[] pills,String takenX, String takenY) {
		int totalSteps = getMinDistanceBetweenNeoAndItem(startX, startY, endX, endY, true, Matrix.pads)*2;
		String minPillX="";
		String minPillY="";
		int minSteps = totalSteps;
		for(int i=0;i<pills.length;i+=2) {
			if(!pills[i].equals("")) {
				if(!(pills[i].equals(takenX) && pills[i+1].equals(takenY))) {
					int pillX = Integer.parseInt(pills[i]);
					int pillY = Integer.parseInt(pills[i+1]);
					int detourDistance = getMinDistanceBetweenNeoAndItem(startX, startY, pillX, pillY, true, Matrix.pads);
					detourDistance+= getMinDistanceBetweenNeoAndItem(pillX, pillY, endX, endY, true, Matrix.pads);
					if((detourDistance*2)-20<minSteps) {
						minSteps = (detourDistance*2)-20;
						minPillX = pillX+"";
						minPillY = pillY+"";
					}
				}
			}
		}
		//steps //pillX //pillY
		String[] res = new String[3];
		res[0] = minSteps+"";
		res[1] = minPillX;
		res[2] = minPillY;
		return res;	
	}
	
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------

//For each pair of point consider the paths and obtain the minimum of the 3 
//A. Move directly from point A to point B
//B. Take pad closest to point A then go to the pad's pair at some point C and recursively
//find the minimum distance from point C to point B while removing the pad taken in the
//first step
//C. Same as B however starting with the pad closest to point B instead	
	public int getMinDistanceBetweenNeoAndItem(int neoX,int neoY,int itemX, int itemY,boolean flag,String[] pads) {
		if(neoX==itemX && neoY==itemY)
			return 0;
		int directDistance = getDistance(itemX, itemY, neoX, neoY);
		if(pads.length==0)
			return directDistance;
		int[] neosPad = getClosestPadPair(neoX, neoY,false,pads);
		String[] newPads1 = removePadPair(neosPad[0], neosPad[1], neosPad[2], neosPad[3], pads);
		int distanceNeosPad;
		distanceNeosPad = neosPad[4] + getMinDistanceBetweenNeoAndItem(neosPad[2], neosPad[3], itemX, itemY,false,newPads1);
		int[] closestPadPairToItem = getClosestPadPair(itemX, itemY,false,pads);
		String[] newPads2 = removePadPair(closestPadPairToItem[0], closestPadPairToItem[1], closestPadPairToItem[2], closestPadPairToItem[3], pads);
		int distanceNeoToPad = getDistance(closestPadPairToItem[2], closestPadPairToItem[3],
				neoX, neoY);
		int distanceItemsPad = distanceNeoToPad + getMinDistanceBetweenNeoAndItem(closestPadPairToItem[0],
			 closestPadPairToItem[1], itemX, itemY,false,newPads2);
		return Math.min(directDistance, Math.min(distanceNeosPad, distanceItemsPad));

	}
	
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------

//The method takes the location of pad pair used and iterates over the pads array and removes
//that pad pair from the array returning the new pads array
	public String[] removePadPair(int pad1X,int pad1Y,int pad2X,int pad2Y,String[] pads) {
		String[] newPads = new String[pads.length-8];
		int newPadsIndex = 0;
		for(int i=0;i<pads.length;i+=4) {
			int padi = Integer.parseInt(pads[i]);
			int padi1 = Integer.parseInt(pads[i+1]);
			int padi2 = Integer.parseInt(pads[i+2]);
			int padi3 = Integer.parseInt(pads[i+3]);
			if(!(padi==pad1X && padi1==pad1Y && padi2==pad2X && padi3==pad2Y) &&
					!((padi==pad2X && padi1==pad2Y && padi2==pad1X && padi3==pad1Y))) {
				newPads[newPadsIndex] = ""+padi;
				newPads[newPadsIndex+1] = ""+padi1;
				newPads[newPadsIndex+2] =""+ padi2;
				newPads[newPadsIndex+3] =""+ padi3;
				newPadsIndex+=4;
			}
				
		}
		return newPads;
		
	}
	
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------

//This method takes x and y coordinates of a point and the pads array and iterates over all pads
//finding the pad with the closest distance using the getDistance method to the point
//given as input, the started boolean flag is used to help in the recursion	
	public int[] getClosestPadPair(int itemX,int itemY,boolean started,String[] pads) {
		double minDistance = Double.MAX_VALUE;
		int[] res = new int[5];
		for(int i=0;i<pads.length;i+=4) {
			int pad1X = Integer.parseInt(pads[i]);
			int pad1Y = Integer.parseInt(pads[i+1]);
			if(!(pad1X==itemX &&pad1Y==itemY) || started) {
				int currDistance = getDistance(pad1X,pad1Y, itemX, itemY);
				if(currDistance<minDistance) {
					int pad2X = Integer.parseInt(pads[i+2]);
					int pad2Y = Integer.parseInt(pads[i+3]);
					res[0] = pad1X;
					res[1] = pad1Y;
					res[2] = pad2X;
					res[3] = pad2Y;
					res[4] = currDistance;
					minDistance = currDistance;
				}
			}
		}
		return res;
	}
	
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------

//|x1-x2| + |y1-y2|
	public int getDistance(int x1, int y1, int x2, int y2){
		return Math.abs(x2-x1) + Math.abs(y2-y1);
	}


}
