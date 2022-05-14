package code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Search {
	
//HashSet used to store the strings representing each state, to avoid repeating states
	public static HashSet<String> visitedStates = new HashSet<String>();
	
    public static Node generalSearch(SearchProblem matrix,String strategy) {
    	
    	Node parentNode = new Node(null, null, matrix.initialState, 0, 0);
    	Node result;
    	switch (strategy) {
		case "BF":
		    result = breadthFirstSearch(parentNode);
			break;
		case "DF":
			result = depthFirstSearch(parentNode,Integer.MAX_VALUE);
			break;
		case "ID":
			result = iterativeDeepeningSearch(parentNode);
			break;
		case "UC":
			result = uniformCostSearch(parentNode,matrix.costComparator);
			break;
		case "GR1":
			result = firstGreedySearch(parentNode,matrix.greedyOneComparator);
			break;
		case "GR2":
			result = secondGreedySearch(parentNode, matrix.greedyThreeComparator);
			break;
		case "AS1":
			result = firstAStarSearch(parentNode, matrix.aStarOneComparator);
			break;
		case "AS2":
			result = secondAStarSearch(parentNode,matrix.aStarThreeComparator);
			break;
		default:
			System.out.println("Enter a correct search strategy name!");
			return null ;
		}
    		return result;
    }
    
//------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------
    
//This function performs the search procedure using the bfs algorithm taking the parent
//node of the tree as input and returns the node containing a goal state
	public static Node breadthFirstSearch(Node parentNode) {


		Queue<Node> queue = new LinkedList<Node>();          //initialise the queue used to store the nodes
		visitedStates.clear();								//reset the hashset used to store the states
		Matrix.nodes=0;    									//reset the variable used to store the number of nodes expanded
		queue.add(parentNode);								//add the initial node to the queue
		checkState(parentNode);								//add the initial node to the hashset 
		while (true) {
			//check if the queue is empty then there is no node found that passes the goal test function
			//and therefore return null
			if (queue.isEmpty()) {							
				return null;
			} else {
				//remove the first node in the queue
				Node frontNode = queue.remove();             
//				System.out.println(frontNode);
				
				//if the node passes the goal test function then return that node
				if (SearchProblem.goaltest(frontNode)) {
					return frontNode;
				} else {				
					//Expand the front node and retrieve all new nodes
					ArrayList<Node> expandedNodes = Expand.getExpandedNodes(frontNode);
					//Iterate over all nodes in the array list
					for(int i=0;i<expandedNodes.size();i++) {
						Node newNode = expandedNodes.get(i);
						//check that the state of the new node is not repeated if so add to queue
						if(checkState(newNode))
							queue.add(newNode);
					}
				}

			}
		}
	}

//------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------
	
//This function performs the search procedure using the dfs algorithm taking the parent
//node of the tree as input and returns the node containing a goal state, when the 
//dfs method is called the limit here is set to infinity, however, when the 
//depth-limited search is called the limit is set depending on the iteration
//number in the IDS
	public static Node depthFirstSearch(Node parentNode, int limit) {
		Stack<Node> stack = new Stack<Node>();			    //initialise the stack used to store the nodes
		visitedStates.clear();								//reset the hashset used to store the states
		Matrix.nodes=0;										//reset the variable used to store the number of nodes expanded
		stack.push(parentNode);								//add the initial node to the stack
		checkState(parentNode);								//add the initial node to the hashset
		while (true) {
			//check if the stack is empty then there is no node found that passes the goal test function
			//and therefore return null
			if (stack.isEmpty()) {
				return null;
			} else {
				Node initialNode = stack.pop();
				//if the node passes the goal test function then return that node
				//remove the front node
				if (SearchProblem.goaltest(initialNode)) {
					return initialNode;
				} else {
					

					Node frontNode = new Node(initialNode.parent, initialNode.operators, initialNode.state,
							initialNode.depth, initialNode.pathCost);
					
//					System.out.println(limit + " " + frontNode);
					
					//Expand the front node and retrieve all new nodes
					ArrayList<Node> expandedNodes = Expand.getExpandedNodes(frontNode);
					//Iterate over all nodes in the array list
					for(int i=0;i<expandedNodes.size();i++) {
						Node newNode = expandedNodes.get(i);
						//check that the state of the new node is not repeated and the depth of node is less than
						//limit (infinity in case of DFS) if so add to stack
						if(checkState(newNode) &&newNode.depth<=limit)
							stack.push(newNode);
					}
				}
			}
		}
	}

//------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------
	
///This function performs the search procedure using the IDS algorithm taking the parent
//node of the tree as input and returns the node containing a goal state
	public static Node iterativeDeepeningSearch(Node parentNode) {
		Node result = null;
		visitedStates.clear();								//reset the hashset used to store the states
		Matrix.nodes=0;										//reset the variable used to store the number of nodes expanded
		//looping on the limit starting from zero to infinity, 
		//if no solution is found the increment limit by one
		//else return the solution node
		// for each iteration reset the repeated states hashset
		for (int i = 0; i < Integer.MAX_VALUE; i++) {       
			result = depthFirstSearch(parentNode, i);
			if (result != null) {
				break;
			}
			else {
				
			}

			visitedStates.clear();
//			System.out.println("-------------------------------");
		}
		return result;
	}

//------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------
	
//This function performs the search procedure using the IDS algorithm taking the parent
//node of the tree as input and returns the node containing a goal state
	public static Node uniformCostSearch(Node parentNode, Comparator<Node> comparator) {
		 //initialise the Priority Queue used to store the nodes
		PriorityQueue<Node> priorityQueue = new PriorityQueue<Node>(comparator);
		visitedStates.clear();								//reset the hashset used to store the states
		Matrix.nodes=0;										//reset the variable used to store the number of nodes expanded
		priorityQueue.add(parentNode);						//add the initial node to the Priority Queue
		checkState(parentNode);								//add the initial node to the hashset
		while (true) {
			//check if the queue is empty then there is no node found that passes the goal test function
			//and therefore return null
			if (priorityQueue.isEmpty()) {
				return null;
			} else {
				// System.out.println("not empty");
				//remove the front node
				Node frontNode = priorityQueue.remove();
				//if the node passes the goal test function then return that node
				if (SearchProblem.goaltest(frontNode)) {
					return frontNode;
				} else {
//					System.out.println(frontNode);
				
					//Expand the front node and retrieve all new nodes
					ArrayList<Node> expandedNodes = Expand.getExpandedNodes(frontNode);
					//Iterate over all nodes in the array list
					for(int i=0;i<expandedNodes.size();i++) {
						Node newNode = expandedNodes.get(i);
						//check that the state of the new node is not repeated if so add to queue
						if(checkState(newNode))
							priorityQueue.add(newNode);
					}
				}
			}
		}
	}

//------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------
	
//This function performs the search procedure using the greedy search algorithm and the heuristic
//function defined in the taking the greedyOneComparator it takes the parent
//node of the tree as input and returns the node containing a goal state
	public static Node firstGreedySearch(Node parentNode, Comparator<Node> comparator) {
		//initialise the Priority Queue used to store the nodes
		PriorityQueue<Node> priorityQueue = new PriorityQueue<Node>(comparator);
		visitedStates.clear();								//reset the hashset used to store the states
		Matrix.nodes=0;										//reset the variable used to store the number of nodes expanded
		priorityQueue.add(parentNode);						//add the initial node to the Priority Queue
		checkState(parentNode);								//add the initial node to the hashset
		while (true) {
			//check if the queue is empty then there is no node found that passes the goal test function
			//and therefore return null
			if (priorityQueue.isEmpty()) {
				return null;
			} else {
				// System.out.println("not empty");
				//remove the front node
				Node frontNode = priorityQueue.remove();
				//if the node passes the goal test function then return that node
				if (SearchProblem.goaltest(frontNode)) {
					return frontNode;
				} else {
//					System.out.println(frontNode);
					
					//Expand the front node and retrieve all new nodes
					ArrayList<Node> expandedNodes = Expand.getExpandedNodes(frontNode);
					//Iterate over all nodes in the array list
					for(int i=0;i<expandedNodes.size();i++) {
						Node newNode = expandedNodes.get(i);
						//check that the state of the new node is not repeated if so add to queue
						if(checkState(newNode))
							priorityQueue.add(newNode);
					}
				}
			}
		}
	}

//------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------
	
//This function performs the search procedure using the greedy search algorithm and the heuristic
//function defined in the taking the greedyThreeComparator it takes the parent
//node of the tree as input and returns the node containing a goal state
	public static Node secondGreedySearch(Node parentNode, Comparator<Node> comparator) {
		//initialise the Priority Queue used to store the nodes
		PriorityQueue<Node> priorityQueue = new PriorityQueue<Node>(new greedyThreeComparator());
		visitedStates.clear();								//reset the hashset used to store the states
		Matrix.nodes=0;										//reset the variable used to store the number of nodes expanded
		priorityQueue.add(parentNode);						//add the initial node to the Priority Queue
		checkState(parentNode);								//add the initial node to the hashset
		while (true) {
			//check if the queue is empty then there is no node found that passes the goal test function
			//and therefore return null
			if (priorityQueue.isEmpty()) {
				return null;
			} else {
				// System.out.println("not empty");
				//remove the front node
				Node frontNode = priorityQueue.remove();
				//if the node passes the goal test function then return that node
				if (SearchProblem.goaltest(frontNode)) {
					return frontNode;
				} else {
//					System.out.println(frontNode);
					
					//Expand the front node and retrieve all new nodes
					ArrayList<Node> expandedNodes = Expand.getExpandedNodes(frontNode);
					//Iterate over all nodes in the array list
					for(int i=0;i<expandedNodes.size();i++) {
						Node newNode = expandedNodes.get(i);
						//check that the state of the new node is not repeated if so add to queue
						if(checkState(newNode))
							priorityQueue.add(newNode);
					}
				}

			}
		}
	}

//------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------
	
//This function performs the search procedure using the A* search algorithm and the heuristic
//function defined in the taking the aStarOneComparator it takes the parent
//node of the tree as input and returns the node containing a goal state
	public static Node firstAStarSearch(Node parentNode, Comparator<Node> comparator) {
		//initialise the Priority Queue used to store the nodes
		PriorityQueue<Node> priorityQueue = new PriorityQueue<Node>(comparator);
		visitedStates.clear();								//reset the hashset used to store the states
		Matrix.nodes=0;										//reset the variable used to store the number of nodes expanded
		priorityQueue.add(parentNode);						//add the initial node to the Priority Queue
		checkState(parentNode);								//add the initial node to the hashset
		while (true) {
			//check if the queue is empty then there is no node found that passes the goal test function
			//and therefore return null
			if (priorityQueue.isEmpty()) {
				return null;
			} else {
				//remove the first node
				Node frontNode = priorityQueue.remove();
				//if the node passes the goal test function then return that node
				if (SearchProblem.goaltest(frontNode)) {
					return frontNode;
				} else {
//					System.out.println(frontNode);
					
					//Expand the front node and retrieve all new nodes
					ArrayList<Node> expandedNodes = Expand.getExpandedNodes(frontNode);
					//Iterate over all nodes in the array list
					for(int i=0;i<expandedNodes.size();i++) {
						Node newNode = expandedNodes.get(i);
						//check that the state of the new node is not repeated if so add to queue
						if(checkState(newNode))
							priorityQueue.add(newNode);
					}
				}

			}
		}
	}

//------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------
	
//This function performs the search procedure using the A* search algorithm and the heuristic
//function defined in the taking the aStarThreeComparator it takes the parent
//node of the tree as input and returns the node containing a goal state
	public static Node secondAStarSearch(Node parentNode, Comparator<Node> comparator) {
		//initialise the Priority Queue used to store the nodes
		PriorityQueue<Node> priorityQueue = new PriorityQueue<Node>(comparator);
		visitedStates.clear();								//reset the hashset used to store the states
		Matrix.nodes=0;										//reset the variable used to store the number of nodes expanded
		priorityQueue.add(parentNode);						//add the initial node to the Priority Queue
		checkState(parentNode);								//add the initial node to the hashset
		while (true) {
			//check if the queue is empty then there is no node found that passes the goal test function
			//and therefore return null
			if (priorityQueue.isEmpty()) {
				return null;
			} else {
				//remove the front node
				Node frontNode = priorityQueue.remove();
				//if the node passes the goal test function then return that node
				if (SearchProblem.goaltest(frontNode)) {
					return frontNode;
				} else {
//					System.out.println(frontNode);
					
					//Expand the front node and retrieve all new nodes
					ArrayList<Node> expandedNodes = Expand.getExpandedNodes(frontNode);
					//Iterate over all nodes in the array list
					for(int i=0;i<expandedNodes.size();i++) {
						Node newNode = expandedNodes.get(i);
						//check that the state of the new node is not repeated if so add to queue
						if(checkState(newNode))
							priorityQueue.add(newNode);
					}
				}

			}
		}
	}
	
//------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------	
//------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------

	
//The check state encodes specific attributes from the state as as string and checks if it already
//exists in the visitedStates hashet then it returns false otherwise it returns true and
//adds that new state to the hashset
	public static boolean checkState(Node node) {
		String key = "";
		String[] stateArray = node.state.split(";");
		String[] NeoArray = stateArray[0].split(",");
		String NeoX = NeoArray[0];
		String NeoY = NeoArray[1];
		String damage = NeoArray[2];
		key+=NeoX+","+NeoY+","+damage+";"; 
//		key += NeoX + "," + NeoY + ";";
		String Carried = stateArray[1];
		key += Carried + ";";

		if (stateArray.length >= 3) {
			String[] agentsArray = stateArray[2].split(",");
			int agentsNumber = agentsArray.length / 2;
			key += agentsNumber + ";";
//			 key+=stateArray[2]+";"; 
		}
		if (stateArray.length >= 4) {
			String[] pillsArray = stateArray[3].split(",");
			int pillsNumber = pillsArray.length / 2;
			key += pillsNumber + ";";
			/* key+=stateArray[3]+";"; */
		}
		if (stateArray.length >= 5) {
			String[] hostagesArray = stateArray[4].split(",");
			/* key+=stateArray[4]+";"; */
			for (int i = 0; i < hostagesArray.length; i += 4) {
				if (!hostagesArray[i].equals("")) {
					int hostageX = Integer.parseInt(hostagesArray[i + 0]);
					int hostageY = Integer.parseInt(hostagesArray[i + 1]);
					key += hostageX + ",";
					key += hostageY + ",";
					int hostageDamage = Integer.parseInt(hostagesArray[i + 2]);
					String hostageCarried = hostagesArray[i + 3];
					key += hostageCarried + ",";
					if (hostageDamage < 100) {
						key += "1,";
					} else {
						key += "0,";
					}
				}
			}

		}
		key += stateArray[5] + ";";
		key += stateArray[6] + ";";
		key += stateArray[7] + ";";
		key += stateArray[8] + ";";

		if (visitedStates.contains(key)) {
			return false;
		} else {
			visitedStates.add(key);
			return true;
		}

	}

}
