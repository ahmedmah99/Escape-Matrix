package code;


public class Node{

    Node parent;
    Operators operators;
    String state;
    int depth;
    int pathCost; //redundant


    public Node(){ }


    public Node(Node parent, Operators operators, String state, int depth, int pathCost){
        this.parent = parent;
        this.operators = operators;
        this.state = state;
        this.depth = depth;
        this.pathCost = pathCost;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getDepth() {
        return depth;
    }

    public int getPathCost() {
        return pathCost;
    }

    public Node getParent() {
        return parent;
    }

    public Operators getOperators() {
        return operators;
    }

    public String getState() {
        return state;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setState(String state) {
        this.state = state;
    }


	@Override
	public String toString() {
		if(parent!=null)
			return "Node [parentOperator= " + parent.operators + " operators=" + operators + ", state=" + state + ", depth=" + depth
				+ ", pathCost=" + pathCost + "]";
//			return "Node [parentOperator= " + parent.operators + " operators=" + operators + ", depth=" + depth
//					+ ", pathCost=" + pathCost + "]";
		else {
			return "Node [operators=" + operators + ", state=" + state + ", depth=" + depth
					+ ", pathCost=" + pathCost + "]";
		}
	}


//	@Override
//	public int compareTo(Node n) {
//		String[] stateArray1 = this.state.split(";");
//		String[] stateArray2 = n.state.split(";");
//		
//		int rescued1 = Integer.parseInt(stateArray1[8]);
//		int rescued2 = Integer.parseInt(stateArray2[8]);
//		
//		int hostagesKilled1 = Integer.parseInt(stateArray1[6]);
//		int hostagesKilled2 = Integer.parseInt(stateArray2[6]);
//		
//		int agentsKilled1 = Integer.parseInt(stateArray1[7]);
//		int agentsKilled2 = Integer.parseInt(stateArray2[7]);
//		
//		int hostagesDead1 = Integer.parseInt(stateArray1[5]);
//		int hostagesDead2 = Integer.parseInt(stateArray2[5]);
		
///////////////////////////////////////////////////Approach 0///////////////////////////////////////////////////
//			if(rescued1>rescued2) 
//				return -1;		
//			if(rescued2>rescued1)
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
//						if(this.depth<n.depth)
//							return -1;
//						if(n.depth<this.depth)
//							return 1;
//						else {
//							return 0;
//						}
//					}
//				}	
//			}
		
///////////////////////////////////////////////////Approach 1///////////////////////////////////////////////////
//		if(this.depth>n.depth) {
//			if(agentsKilled2>agentsKilled1) {
//				return -1;
//			}
//			else {
//				if(hostagesKilled2>hostagesKilled1) {
//					return -1;
//				}
//				else {
//					return 1;
//				}
//			}
//		}
//		else if(this.depth<n.depth){
//			if(rescued1>rescued2) 
//				return -1;		
//			if(rescued2>rescued1)
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
//						return 0;
//					}
//				}
//			}
//			
//		}
//		else {
//			return 0;
//		}

///////////////////////////////////////////////////Approach 2///////////////////////////////////////////////////
//		if(rescued1>rescued2) {
//				if(this.depth>n.depth)
//						return 1;
//				else {
//					return -1;
//				}
//		}
//		
//		else if(rescued1<rescued2) {
//				if(this.depth<n.depth)
//						return -1;
//				else {
//					return 1;
//				}
//		}
//		else if(hostagesKilled1>hostagesKilled2) {
//			 if(this.depth>n.depth)
//					return 1;
//			else {
//				return -1;
//			}	
//		}
//		 else if(hostagesKilled2>hostagesKilled1) {
//			 if(this.depth<n.depth)
//					return -1;
//			else {
//				return 1;
//			}
//		 }
//		 else if(agentsKilled1<agentsKilled2) {
//					if(this.depth>n.depth)
//						return 1;
//					else {
//						return -1;
//					}
//		 }
//		 else if(agentsKilled2<agentsKilled1) {
//					if(this.depth<n.depth)
//							return -1;
//					else {
//						return 1;
//					}
//				}
//		else {
//			if(this.depth<n.depth)
//				return -1;
//			if(n.depth<this.depth)
//				return 1;
//			else {
//				return 0;
//			}
//		}
///////////////////////////////////////////////////Approach 3///////////////////////////////////////////////////
//		if(rescued1>rescued2) {
//			if(hostagesKilled2>hostagesKilled1 && agentsKilled2>agentsKilled1)
//				return -1;
//			return 0;
//		}
//		if(rescued2>rescued1) {
//			if(hostagesKilled1>hostagesKilled2 && agentsKilled1>agentsKilled2)
//				return 1;
//			return 0;
//		}
//		else {
//			if(hostagesKilled1>hostagesKilled2)
//				return -1;
//			if(hostagesKilled2>hostagesKilled1)
//				return 1;
//			else {
//				if(agentsKilled1<agentsKilled2)
//					return -1;
//				if(agentsKilled2<agentsKilled1)
//					return 1;
//				else {
//					if(this.depth<n.depth)
//						return -1;
//					if(n.depth<this.depth)
//						return 1;
//					else {
//						return 0;
//					}
//				}
//			}
//		}
		//////////////////////////////Appproach 4///////////////////////
//		if(hostagesDead1<hostagesDead2) 
//			return -1;		
//		if(hostagesDead2<hostagesDead1)
//			return 1;
//		else {
//			if(hostagesKilled1>hostagesKilled2)
//				return -1;
//			if(hostagesKilled2>hostagesKilled1)
//				return 1;
//			else {
//				if(agentsKilled1<agentsKilled2)
//					return -1;
//				if(agentsKilled2<agentsKilled1)
//					return 1;
//				else {
//					if(this.depth<n.depth)
//						return -1;
//					if(n.depth<this.depth)
//						return 1;
//					else {
//						return 0;
//					}
//				}
//			}	
//		}
//		
//	}	
}
		
