package code;
import java.util.Comparator;

public class greedyOneComparator implements Comparator<Node> {

	@Override
	public int compare(Node n1, Node n2) {
		
		String[] stateArray1 = n1.state.split(";");
		String[] stateArray2 = n2.state.split(";");
				
		int hostagesLength1 =  stateArray1[4].length()/4;
		int hostagesLength2 =  stateArray2[4].length()/4;
		
		//The heuristic function predicts that all hostages in the array will die
		//and return the node with the least number of hostages in array
		if(hostagesLength1<hostagesLength2)
			return -1;
		if(hostagesLength1>hostagesLength2)
			return 1;
		return 0;
		
		
	}
}

