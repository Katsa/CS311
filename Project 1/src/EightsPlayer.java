
import java.lang.Thread.State;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;


/*
 * Solves the 8-Puzzle Game (can be generalized to n-Puzzle)
 */

public class EightsPlayer {

	static Scanner scan = new Scanner(System.in);
	static int size = 3; //size = 3 for 8-Puzzle. Change this to 4 for 15-Puzzle
	static int numiterations = 100;
	static int numnodes; //number of nodes generated
	static int nummoves; //number of moves required to reach goal
	
	
	public static void main(String[] args)
	{	
		int boardchoice = getBoardChoice();
		int algchoice = getAlgChoice();
			
		int numsolutions = 0;
		
		Node initNode;

		if(boardchoice==0)
			numiterations = 1;

		for(int i=0; i<numiterations; i++){
		
			if(boardchoice==0)
				initNode = getUserBoard();
			else
				initNode = generateInitialState();//create the random board for a new puzzle
			
			boolean result=false; //whether the algorithm returns a solution
			
			switch (algchoice){
				case 0: 
					result = runBFS(initNode); //BFS
					break;
				case 1: 
					result = runAStar(initNode, 0); //A* with Manhattan Distance heuristic
					break;
				case 2: 
					result = runAStar(initNode, 1); //A* with your new heuristic
					break;
			}
			
			
			//if the search returns a solution
			if(result){
				
				numsolutions++;
				
				
				System.out.println("Number of nodes generated to solve: " + numnodes);
				System.out.println("Number of moves to solve: " + nummoves);			
				System.out.println("Number of solutions so far: " + numsolutions);
				System.out.println("_______");		
				
			}//if runBFS
			else
				System.out.print(".");
			
		}//for

		
		
		System.out.println();
		System.out.println("Number of iterations: " +numiterations);
		
		if(numsolutions > 0){
			System.out.println("Average number of moves for "+numsolutions+" solutions: "+nummoves/numsolutions);
			System.out.println("Average number of nodes generated for "+numsolutions+" solutions: "+numnodes/numsolutions);
		}
		else
			System.out.println("No solutions in "+numiterations+"iterations.");
		
	}
	
	
	public static int getBoardChoice()
	{
		
		System.out.println("single(0) or multiple boards(1)");
		int choice = Integer.parseInt(scan.nextLine());
		
		return choice;
	}
	
	public static int getAlgChoice()
	{
		
		System.out.println("BFS(0) or A* Manhattan Distance(1) or A* <Your New Heuristic>(2)");
		int choice = Integer.parseInt(scan.nextLine());
		
		return choice;
	}

	
	public static Node getUserBoard()
	{
		
		System.out.println("Enter board: ex. 012345678");
		String stbd = scan.nextLine();
		
		int[][] board = new int[size][size];
		
		int k=0;
		
		for(int i=0; i<board.length; i++){
			for(int j=0; j<board[0].length; j++){
				System.out.println(stbd.charAt(k));
				board[i][j]= Integer.parseInt(stbd.substring(k, k+1));
				k++;
			}
		}
		
		
		for(int i=0; i<board.length; i++){
			for(int j=0; j<board[0].length; j++){
				System.out.println(board[i][j]);
			}
			System.out.println();
		}
		
		
		Node newNode = new Node(null,0, board);

		return newNode;
		
		
	}

    
	
	
	/**
	 * Generates a new Node with the initial board
	 */
	public static Node generateInitialState()
	{
		int[][] board = getNewBoard();
		
		Node newNode = new Node(null,0, board);

		return newNode;
	}
	
	
	/**
	 * Creates a randomly filled board with numbers from 0 to 8. 
	 * The '0' represents the empty tile.
	 */
	public static int[][] getNewBoard()
	{
		
		int[][] brd = new int[size][size];
		Random gen = new Random();
		int[] generated = new int[size*size];
		for(int i=0; i<generated.length; i++)
			generated[i] = -1;
		
		int count = 0;
		
		for(int i=0; i<size; i++)
		{
			for(int j=0; j<size; j++)
			{
				int num = gen.nextInt(size*size);
				
				while(contains(generated, num)){
					num = gen.nextInt(size*size);
				}
				
				generated[count] = num;
				count++;
				brd[i][j] = num;
			}
		}
		
		/*
		//Case 1: 12 moves
		brd[0][0] = 1;
		brd[0][1] = 3;
		brd[0][2] = 8;
		
		brd[1][0] = 7;
		brd[1][1] = 4;
		brd[1][2] = 2;
		
		brd[2][0] = 0;
		brd[2][1] = 6;
		brd[2][2] = 5;
		*/
		
		return brd;
		
	}
	
	/**
	 * Helper method for getNewBoard()
	 */
	public static boolean contains(int[] array, int x)
	{ 
		int i=0;
		while(i < array.length){
			if(array[i]==x)
				return true;
			i++;
		}
		return false;
	}
	
	
	/**
	 * TO DO:
     * Prints out all the steps of the puzzle solution and sets the number of moves used to solve this board.
     */
    public static void printSolution(Node node) {
    	
    	/*TO DO*/
    	
    	int nummoves = 0;

    	while(node.getparent() != null) {
    		node.print(node);
    		nummoves ++;
    	}



    	/*if(parent != null) {
    		printSolution(parent);
    	}
    	parentArray[0] = parent;
    	//current = parent.getChild();

    	nummoves += 1;
    	length = parent.getdepth();

    	for(int i = 1; i < length; i++) {
    		parentArray[i] = current;
    		current = current.getChild();
    		nummoves += 1;
    	}

    	for(int i = 0; i<parentArray.length; i++) {
			System.out.println(parentArray[i].getboard());

    	}
    	System.out.println(nummoves);
    	*/
    }
	
	
	
	
	/**
	 * TO DO:
	 * Runs Breadth First Search to find the goal state.
	 * Return true if a solution is found; otherwise returns false.
	 */
	public static boolean runBFS(Node initNode) {		
		if(initNode.isGoal())
			return true;
		Queue<Node> Frontier = new LinkedList<Node>();
		Frontier.add(initNode);
		ArrayList<Node> Explored = new ArrayList<Node>();
		int maxDepth = 20;
		//Explore 
		while(!Frontier.isEmpty()){
			Node node = Frontier.remove();
			if(node.getdepth() > maxDepth){
				return false;
			}
			Explored.add(node);
			node.print(node);
			nummoves++;
			//Create child with node info, check is it is solution, then return true, 
			//or add child to the frontier and continue searching
			for(int[][] bb : node.expand()){
				Node child = new Node(node,node.getdepth()+1,bb);
				if (!(Explored.contains(child)| Frontier.contains(child))){
					if(child.isGoal())
						return true;
					Frontier.add(child);
					numnodes++;
				}
			}
		}
		return false;
	}//endofBFS
	
	
	
	/***************************A* Code Starts Here ***************************/
	
	/**
	 * TO DO:
	 * Runs A* Search to find the goal state.
	 * Return true if a solution is found; otherwise returns false.
	 * heuristic = 0 for Manhattan Distance, heuristic = 1 for your new heuristic
	 */
	public static boolean runAStar(Node initNode, int heuristic)
	{
	
		if(initNode.isGoal())
			return true;
		PriorityQueue<Node> Frontier = new PriorityQueue<Node>();
		Queue<Node> Explored = new LinkedList<Node>();
		initNode.setfvalue();
		Frontier.add(initNode);

		int maxDepth = 20;
		//Explore 
		while(!Frontier.isEmpty()){
			Node node = Frontier.remove();
			if(node.isGoal())
					return true;
			if(node.getdepth()>=maxDepth)
				return false;
			Explored.add(node);
			nummoves++;
			//Create child with node info, check is it is solution, then return true, 
			//or add child to the frontier and continue searching
			for(int[][] bb : node.expand()){
				Node child = new Node(node,node.getgvalue()+1, node.evaluateHeuristic(),bb);
				if (!Explored.contains(child)){
					child.setfvalue();
					if(Frontier.contains(child)) {
						Node tempChild = Frontier.peek();
						Frontier.remove();
						Frontier.remove();
						Frontier.add(tempChild); //this might need to be fixed
					}
					else {
						Frontier.add(child);
					}
				}
				else {
					numnodes++;
				}
			}		
		}
		return false;
	}
	
}