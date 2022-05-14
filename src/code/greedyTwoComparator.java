package code;
import java.util.Arrays;
import java.util.Comparator;

public class greedyTwoComparator implements Comparator<Node> {
	@Override
	public int compare(Node n1, Node n2) {
		

		//if neo is carrying==C or no more hostages to carry then find min((distance from neo to nearest pad then from
		//pad pair to telephone booth), (distance from neo to telephone booth))
		//else then find hostage with avg of min((neo to closest pad then from pad to hostage),(distance from neo to hostages))

		double evalNode1 = getAppropriateDistance(n1);
		double evalNode2 = getAppropriateDistance(n2);
		return Double.compare(evalNode1, evalNode2);
	}
	//checked
	//This method returns the values of the heuristic function that evaluates the node
	//if Neo is on his way to TB (no capacity to carry more or no more hostages left to carry)
	//then the value for the heuristic function is the shortest distance from Neo to telephone booth
	//else then there are remaining hostages for neo to deal with either to carry or kill
	//for each hostages we find the minimum distance from neo to that hostage
	//then we calculate the average distance from neo to all hostages remaining
	public double getAppropriateDistance(Node n) {
		String[] stateArray = n.state.split(";");
		String[] hostages = stateArray[4].split(",");
		int carried = Integer.parseInt(stateArray[1]);
		String[] Neo = stateArray[0].split(",");
		int neoX = Integer.parseInt(Neo[0]);
		int neoY = Integer.parseInt(Neo[1]);
		if(carried==Matrix.C || !isRemainingHostages(hostages)) {
			String[] Telephone = Matrix.Telephone.split(",");
			int tbX = Integer.parseInt(Telephone[0]);
			int tbY = Integer.parseInt(Telephone[1]);
			return getMinDistanceBetweenNeoAndItem(neoX, neoY,tbX,tbY);
		}
		else {
			int length = hostages.length/4;
			double sum =0;
			int count = 0;
			for(int i=0;i<length;i++) {
				int index = i*4;
				int isCarried = Integer.parseInt(hostages[index+3]);
				if(isCarried==0) {
					int hostageX = Integer.parseInt(hostages[index]);
					int hostageY = Integer.parseInt(hostages[index+1]);
					double hostageDistance = getMinDistanceBetweenNeoAndItem(neoX, neoY, hostageX, hostageY);
					sum+=hostageDistance;
					count++;
				}
			}
//			double averageDistance = sum/count;
//			return averageDistance;
			return sum;
		}
	}
	//checked
	//finds whether there are any hostages remaining in the grid to carry, returns true if at least one exists,
	//false otherwise
	public boolean isRemainingHostages(String[] hostages) {
		int length = hostages.length/4;
		for(int i=0;i<length;i++) {
			int index = i*4;
			int isCarried = Integer.parseInt(hostages[index+3]);
			if(isCarried==0)
				return true;
		}
		return false;
	}
	//checked
	//This method finds all 3 possible routes for neo to reach and item and returns the minimum one
	//possible route 1: Neo takes pad closest to him and ends at another location and goes from there to the intended item
	//possible route 2: Neo takes a pad whose initial position is not necessarily closest to neo, but whose final position
	//is closest to the intended item
	//possible route 3: Neo goes directly to item without any pads
	public double getMinDistanceBetweenNeoAndItem(int neoX,int neoY,int itemX, int itemY) {
		double[] closestPadPair = getClosestPadPair(neoX, neoY);
		double distanceWithNeosPad = closestPadPair[4] + getDistance(closestPadPair[2], closestPadPair[3], itemX, itemY);
		double directDistance = getDistance(itemX, itemY, neoX, neoY);
		double[] closestPadPairToItem = getClosestPadPair(itemX, itemY);
		double distanceNeoToPad = getDistance(closestPadPairToItem[2], closestPadPairToItem[3],
				neoX, neoY);
		double distanceWithItemsPad = distanceNeoToPad + closestPadPairToItem[4];
		return Math.min(Math.min(distanceWithNeosPad,distanceWithItemsPad), directDistance);
		
	}

	
	
	
	//checked
	//This method takes the location of an item on the grid as input and searches through the whole pads array to find 
	//the pad whose initial position is closest to the item given to the method as input
	//The method returns double[]: {startingX,startingY, endingX, endingY, distance from starting pad to item}
	public double[] getClosestPadPair(int itemX,int itemY) {
		double minDistance = Double.MAX_VALUE;
		double[] res = new double[5];
		
		for(int i=0;i<Matrix.pads.length;i+=4) {
			int pad1X = Integer.parseInt(Matrix.pads[i]);
			int pad1Y = Integer.parseInt(Matrix.pads[i+1]);
			double currDistance = getDistance(pad1X,pad1Y, itemX, itemY);
			if(currDistance<minDistance) {
				int pad2X = Integer.parseInt(Matrix.pads[i+2]);
				int pad2Y = Integer.parseInt(Matrix.pads[i+3]);
				res[0] = pad1X;
				res[1] = pad1Y;
				res[2] = pad2X;
				res[3] = pad2Y;
				res[4] = currDistance;
				minDistance = currDistance;
			}
		}
		return res;
	}
	//checked
	//This Method computes the distance between any 2 points on the grid
	public double getDistance(double x1, double y1, double x2, double y2){
		return Math.sqrt(Math.pow((x2-x1), 2) + Math.pow((y2-y1), 2));
	}

}
