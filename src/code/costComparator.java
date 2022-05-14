package code;
import java.util.Comparator;

public class costComparator implements Comparator<Node> {

//		@Override
//		public int compare(Node n1, Node n2) {
//			
//			String[] stateArray1 = n1.state.split(";");
//			String[] stateArray2 = n2.state.split(";");
//			
////			int rescued1 = Integer.parseInt(stateArray1[8]);
////			int rescued2 = Integer.parseInt(stateArray2[8]);
//			
//			int hostagesKilled1 = Integer.parseInt(stateArray1[6]);
//			int hostagesKilled2 = Integer.parseInt(stateArray2[6]);
//			
//			int agentsKilled1 = Integer.parseInt(stateArray1[7]);
//			int agentsKilled2 = Integer.parseInt(stateArray2[7]);
//			
//			int hostagesDead1 = Integer.parseInt(stateArray1[5]);
//			int hostagesDead2 = Integer.parseInt(stateArray2[5]);
//			
//			if(hostagesDead1<hostagesDead2) 
//				return -1;		
//			if(hostagesDead2<hostagesDead1)
//				return 1;
//			else {
//				if(hostagesKilled1>hostagesKilled2)
//					return -1;
//				if(hostagesKilled2>hostagesKilled1)
//					return 1;
//				else {
//					if(agentsKilled1<agentsKilled2)
//						return -1;
//					if(agentsKilled2<agentsKilled1)
//						return 1;
//					else {
//						if(n1.depth<n2.depth)
//							return -1;
//						if(n2.depth<n1.depth)
//							return 1;
//						else {
//							return 0;
//						}
//					}
//				}	
//			}
//		}
	@Override
	public int compare(Node n1, Node n2) {
		
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
		
		if(hostagesDead1<hostagesDead2) 
			return -1;		
		if(hostagesDead2<hostagesDead1)
			return 1;
		else {
			if(kills1<kills2)
				return -1;
			if(kills1>kills2)
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
	
}
