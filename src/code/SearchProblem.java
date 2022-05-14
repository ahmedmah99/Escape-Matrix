package code;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Operators;

public abstract class SearchProblem {
	Operators operators;
	costComparator costComparator;
	greedyOneComparator greedyOneComparator;
	greedyThreeComparator greedyThreeComparator;
	astarOneComparator aStarOneComparator;
	aStarThreeComparator aStarThreeComparator;
	String initialState;
	Expand expand;
	
	
	public SearchProblem() {
		 operators = new Operators();
		 costComparator = new costComparator();
		 greedyOneComparator = new greedyOneComparator();
		 greedyThreeComparator = new greedyThreeComparator();
		 aStarOneComparator = new astarOneComparator();
		 aStarThreeComparator = new aStarThreeComparator();
		 expand = new Expand();
	}
//The goal test function checks that the hostages_in_the_matrix array is empty and that
//the location of Neo is at the telephone booth
	public static boolean goaltest(Node node) {
		Matrix.nodes += 1;
		String[] stateArray = node.state.split(";");
		String hostageString = stateArray[4];
		String[] Neo = stateArray[0].split(",");
		int neoX = Integer.parseInt(Neo[0]);
		int neoY = Integer.parseInt(Neo[1]);
		String[] tb = Matrix.Telephone.split(",");
		int tbX = Integer.parseInt(tb[0]);
		int tbY = Integer.parseInt(tb[1]);
		return hostageString.length() == 0 && neoX == tbX && neoY == tbY;
	}

}
