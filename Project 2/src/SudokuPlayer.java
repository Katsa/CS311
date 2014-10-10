import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Queue;
import java.text.DecimalFormat;
import java.util.Scanner;

public class SudokuPlayer implements Runnable, ActionListener {
    // final values must be assigned in vals[][]
    int[][] vals = new int[9][9];
    Board board = null;



/// --- AC-3 Constraint Satisfication --- ///
/**
 * Discussion and Comments about AC3:
 * 
 */

    // Useful but not required Data-Structures;
    ArrayList<Integer>[] globalDomains = new ArrayList[81];
    ArrayList<Integer>[] neighbors = new ArrayList[81];
    Queue<Arc> globalQueue = new LinkedList<Arc>();
    /*
    * This method sets up the data structures and the initial global constraints 
    * (by calling allDiff()) and makes the initial call to AC3_DFS(). 
    */
    private final void AC3Init(){
        //Do NOT remove these 3 lines (required for the GUI)
        board.Clear();
        ops = 0;
        recursions = 0;

        /**
        *  YOUR CODE HERE:
        *  Create Data structures ( or populate the ones defined above ).
        *  These will be the data structures necessary for AC-3. 
        **/
        //Adds domains of each cell to globalDomains
        int count=0;
        ArrayList<Integer> values = new ArrayList<Integer>();
        for(int k=0;k<9;k++){
            values.add(k,k+1);
        }
        for(int i=0;i<9;i++){
            for(int j=0;i<9;i++){
                if(vals[i][j]==0){
                    globalDomains[count]=values;
                }else{
                    ArrayList<Integer> single = new ArrayList<Integer>();
                    single.add(vals[i][j]);
                    globalDomains[count]= single;
                }
                count++;
            }
        }//end

        //Adds neighbors
        int counter=0;
        for(int index=0;index<81;index++){
            //row
            ArrayList<Integer> subneighbors = new ArrayList<Integer>();
            subneighbors.add(counter+((index+1)%9));
            subneighbors.add(counter+((index+2)%9));
            subneighbors.add(counter+((index+3)%9));
            subneighbors.add(counter+((index+4)%9));
            subneighbors.add(counter+((index+5)%9));
            subneighbors.add(counter+((index+6)%9));
            subneighbors.add(counter+((index+7)%9));
            subneighbors.add(counter+((index+8)%9));
            //columns
            subneighbors.add(((index+1*9)%81));
            subneighbors.add(((index+2*9)%81));
            subneighbors.add(((index+3*9)%81));
            subneighbors.add(((index+4*9)%81));
            subneighbors.add(((index+5*9)%81));
            subneighbors.add(((index+6*9)%81));
            subneighbors.add(((index+7*9)%81));
            subneighbors.add(((index+8*9)%81));
            //unit Part 1 of 3
            if(index%3==0){
                subneighbors.add(index+1);
                subneighbors.add(index+2);
                if(((counter/9)%3)==0){
                    subneighbors.add(index+9);
                    subneighbors.add(index+18);
            
                    subneighbors.add(index+10);
                    subneighbors.add(index+19);

                    subneighbors.add(index+11);
                    subneighbors.add(index+20);
                }
                else if(((counter/9)%3)==1){
                    subneighbors.add(index-9);
                    subneighbors.add(index+9);

                    subneighbors.add(index-8);
                    subneighbors.add(index+10);

                    subneighbors.add(index-7);
                    subneighbors.add(index+11);
                }
                else if(((counter/9)%3)==2){
                    subneighbors.add(index-9);
                    subneighbors.add(index-18);
                    
                    subneighbors.add(index-8);
                    subneighbors.add(index-17);
                    
                    subneighbors.add(index-7);
                    subneighbors.add(index-16);
                }
            }
            //unit part 2 of 3
            else if(index%3==1){
                subneighbors.add(index+1);
                subneighbors.add(index-1);

                if(((counter/9)%3)==0){
                    subneighbors.add(index+8);
                    subneighbors.add(index+10);

                    subneighbors.add(index+9);
                    subneighbors.add(index+17);

                    subneighbors.add(index+18);
                    subneighbors.add(index+19);
                }
                else if(((counter/9)%3)==1){
                    subneighbors.add(index+9);
                    subneighbors.add(index-9);

                    subneighbors.add(index-10);
                    subneighbors.add(index+10);

                    subneighbors.add(index+8);
                    subneighbors.add(index-8);

                }
                else if(((counter/9)%3)==2){
                    subneighbors.add(index-9);
                    subneighbors.add(index-18);

                    subneighbors.add(index-10);
                    subneighbors.add(index-19);

                    subneighbors.add(index-8);
                    subneighbors.add(index-17);
                }
            }
            //unit part 3 of 3
            else if(index%3==2){
                subneighbors.add(index-1);
                subneighbors.add(index-2);
                if(((counter/9)%3)==0){
                    subneighbors.add(index+7);
                    subneighbors.add(index+16);

                    subneighbors.add(index+8);
                    subneighbors.add(index+17);

                    subneighbors.add(index+9);
                    subneighbors.add(index+18);
                }
                else if(((counter/9)%3)==1){
                    subneighbors.add(index-11);
                    subneighbors.add(index+7);

                    subneighbors.add(index-10);
                    subneighbors.add(index+8);

                    subneighbors.add(index-9);
                    subneighbors.add(index+9);

                }
                else if(((counter/9)%3)==2){
                    subneighbors.add(index-20);
                    subneighbors.add(index-11);

                    subneighbors.add(index-19);
                    subneighbors.add(index-10);

                    subneighbors.add(index-18);
                    subneighbors.add(index-9);
                }
            }
            //Remove duplicates
            HashSet<Integer> hs = new HashSet<Integer>();
            hs.addAll(subneighbors);
            subneighbors.clear();
            subneighbors.addAll(hs);

            neighbors[index]=subneighbors;
            counter=(counter+9)%81;
        }//end

        //Test
        //System.out.println("A$AP: "+neighbors[0]);
        //System.out.println("A$AP: "+neighbors[40]);

        //Setup of arrays that represent the arcs
        int[] row1 = new int[]{0,1,2,3,4,5,6,7,8};
        int[] row2 = new int[]{9,10,11,12,13,14,15,16,17};
        int[] row3 = new int[]{18,19,20,21,22,23,24,25,26};
        int[] row4 = new int[]{27,28,29,30,31,32,33,34,35};
        int[] row5 = new int[]{36,37,38,39,40,41,42,43,44};
        int[] row6 = new int[]{45,46,47,48,49,50,51,52,53};
        int[] row7 = new int[]{54,55,56,57,58,59,60,61,62};
        int[] row8 = new int[]{63,64,65,66,67,68,69,70,71};
        int[] row9 = new int[]{73,74,75,76,77,78,79,79,80};

        int[] column1 = new int[]{0,9,18,27,36,45,54,63,72};
        int[] column2 = new int[]{1,10,19,28,37,46,55,64,73};
        int[] column3 = new int[]{2,11,20,29,38,47,56,65,74};
        int[] column4 = new int[]{3,12,21,30,39,48,57,66,75};
        int[] column5 = new int[]{4,13,22,31,40,49,58,67,76};
        int[] column6 = new int[]{5,14,23,32,41,50,59,68,77};
        int[] column7 = new int[]{6,15,24,33,42,51,60,69,78};
        int[] column8 = new int[]{7,16,25,34,43,52,61,70,79};
        int[] column9 = new int[]{8,17,26,35,44,53,62,71,80};

        int[] unit1 = new int[]{0,1,2,9,10,11,18,19,20};
        int[] unit2 = new int[]{3,4,5,12,13,14,21,22,23};
        int[] unit3 = new int[]{6,7,8,15,16,17,24,25,26};
        int[] unit4 = new int[]{27,28,29,36,37,38,45,46,47};
        int[] unit5 = new int[]{30,31,32,39,40,41,48,49,50};
        int[] unit6 = new int[]{33,34,35,42,43,44,51,52,53};
        int[] unit7 = new int[]{54,55,56,63,64,65,72,73,74};
        int[] unit8 = new int[]{57,58,59,66,67,68,75,76,77};
        int[] unit9 = new int[]{60,61,62,69,70,71,78,79,80};

        //Setup the constraints with allDiff
        //rows
        allDiff(row1);
        allDiff(row2);
        allDiff(row3);
        allDiff(row4);
        allDiff(row5);
        allDiff(row6);
        allDiff(row7);
        allDiff(row8);
        allDiff(row9);
        //columns
        allDiff(column1);
        allDiff(column2);
        allDiff(column3);
        allDiff(column4);
        allDiff(column5);
        allDiff(column6);
        allDiff(column7);
        allDiff(column8);
        allDiff(column9);
        //units
        allDiff(unit1);
        allDiff(unit2);
        allDiff(unit3);
        allDiff(unit4);
        allDiff(unit5);
        allDiff(unit6);
        allDiff(unit7);
        allDiff(unit8);
        allDiff(unit9);

        // Initial call to AC3_DFS on cell 0 (top left)
        boolean success = AC3_DFS(0,globalDomains);

        // Prints evaluation of run
        Finished(success);
    }


    // This defines constraints between a set of variables
    // This is discussed in the book. You may change this method header.
    private final void allDiff(int[] all){
        // YOUR CODE HERE
        for(int i: all){
            for(int j :all){
                if(j!=i){
                    Arc a = new Arc(i,j);
                    globalQueue.add(a);
                }
            }
        }
    }

    // This is the Recursive AC3.	( You may change this method header )
    private final boolean AC3_DFS(int cell, ArrayList<Integer>[] Domains) {
        recursions += 1;
        // YOUR CODE HERE
        if(AC3(Domains) == true) {
        	
            AC3_DFS(cell+1,Domains);
        }
       else {
          
        }
        return false;
    }

	// This is the actual AC-3 Algorithm ( You may change this method header)
	private final boolean AC3(ArrayList<Integer>[] Domains) {
	   // YOUR CODE HERE
	    while(!globalQueue.isEmpty()){
	        Arc a$ap = globalQueue.remove();
	        if (Revise(a$ap,Domains)){
	            if(Domains[a$ap.Xi].size()==0){
	                return false;
	            }
                for(int Xk: neighbors[a$ap.Xi]){
	                if(Xk!=a$ap.Xj){
	                   globalQueue.add(a$ap);
	                }
	            }
	        }
	    }
	    return true;
	}


    // This is the Revise() method defined in the book
    // ( You may change this method header )
    private final boolean Revise(Arc a$ap, ArrayList<Integer>[] Domains){
        ops += 1;
        boolean revised=false;
        // YOUR CODE HERE
        boolean allequal=true;
        for(int x:Domains[a$ap.Xi]){
            for (int y:Domains[a$ap.Xj]){
                if(x!=y)
                    allequal=false;
            }
            if(allequal){
                Domains[a$ap.Xi].remove(x);
                revised=true;
            }
        }       
        return revised;
    }


        
    /// ---------- HELPER FUNCTIONS --------- ///
    /// ----   DO NOT EDIT REST OF FILE   --- ///
    public final boolean valid(int x, int y, int val){
        ops +=1;
        if (vals[x][y] == val)
            return true;
        if (rowContains(x,val))
            return false;
        if (colContains(y,val))
            return false;
        if (blockContains(x,y,val))
            return false;
        return true;
    }

    public final boolean blockContains(int x, int y, int val){
        int block_x = x / 3;
        int block_y = y / 3;
        for(int r = (block_x)*3; r < (block_x+1)*3; r++){
            for(int c = (block_y)*3; c < (block_y+1)*3; c++){
                if (vals[r][c] == val)
                    return true;
            }
        }
        return false;
    }

    public final boolean colContains(int c, int val){
        for (int r = 0; r < 9; r++){
            if (vals[r][c] == val)
                return true;
        }
        return false;
    }

    public final boolean rowContains(int r, int val) {
        for (int c = 0; c < 9; c++)
        {
            if(vals[r][c] == val)
                return true;
        }
        return false;
    }

    private void CheckSolution() {
        // If played by hand, need to grab vals
        board.updateVals(vals);

        for (int v = 0; v <= 9; v++){
            // Every row is valid
            for (int r = 0; r < 9; r++)
            {
                if (!rowContains(r,v))
                {
                    board.showMessage("Invalid Row: v: "+v+", r+1: " + (r+1));// + " val: " + v);
                    return;
                }
            }
            // Every row is valid
            for (int c = 0; c < 9; c++)
            {
                if (!colContains(c,v))
                {
                    board.showMessage("Invalid Column: " + (c+1));// + " val: " + v);
                    return;
                }
            }
            // Every block is valid
            for (int r = 0; r < 3; r++){
                for (int c = 0; c < 3; c++){
                    if(!blockContains(r, c, v))
                    {
                        board.showMessage("Invalid Block: " + (r+1) + "," + (c+1));// + " val: " + v);
                        return;
                    }
                }
            }
        }
        board.showMessage("Success!");
    }

    /// ---- GUI + APP Code --- ////
    /// ----   DO NOT EDIT  --- ////
    enum algorithm {
        AC3
    }
    class Arc implements Comparable<Object>{
        int Xi, Xj;
        public Arc(int cell_i, int cell_j){
            if (cell_i == cell_j){
                try {
                    throw new Exception(cell_i+ "=" + cell_j);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
            Xi = cell_i;      Xj = cell_j;
        }

        public int compareTo(Object o){
            return this.toString().compareTo(o.toString());
        }

        public String toString(){
            return "(" + Xi + "," + Xj + ")";
        }
    }

    enum difficulty {
        easy, medium, noSolution, hardNoSolution, random
    }
    
    public void actionPerformed(ActionEvent e){
        String label = ((JButton)e.getSource()).getText();
        if (label.equals("AC-3"))
            AC3Init();
        else if (label.equals("Clear"))
            board.Clear();
        else if (label.equals("Check"))
            CheckSolution();
    }
    
    public void run() {
        board = new Board(gui,this);
        while(!initialize());
        if (gui)
            board.initVals(vals);
        else {
            board.writeVals();
            System.out.println("Algorithm: " + alg);
            switch(alg) {
                default:
                case AC3:
                    AC3Init();
                    break;
            }
            CheckSolution();
        }
    }
    
    public final boolean initialize(){
        switch(level) {
            case easy:
                vals[0] = new int[] {0,0,0,1,3,0,0,0,0};
                vals[1] = new int[] {7,0,0,0,4,2,0,8,3};
                vals[2] = new int[] {8,0,0,0,0,0,0,4,0};
                vals[3] = new int[] {0,6,0,0,8,4,0,3,9};
                vals[4] = new int[] {0,0,0,0,0,0,0,0,0};
                vals[5] = new int[] {9,8,0,3,6,0,0,5,0};
                vals[6] = new int[] {0,1,0,0,0,0,0,0,4};
                vals[7] = new int[] {3,4,0,5,2,0,0,0,8};
                vals[8] = new int[] {0,0,0,0,7,3,0,0,0};
                break;
            case medium:
                vals[0] = new int[] {0,4,0,0,9,8,0,0,5};
                vals[1] = new int[] {0,0,0,4,0,0,6,0,8};
                vals[2] = new int[] {0,5,0,0,0,0,0,0,0};
                vals[3] = new int[] {7,0,1,0,0,9,0,2,0};
                vals[4] = new int[] {0,0,0,0,8,0,0,0,0};
                vals[5] = new int[] {0,9,0,6,0,0,3,0,1};
                vals[6] = new int[] {0,0,0,0,0,0,0,7,0};
                vals[7] = new int[] {6,0,2,0,0,7,0,0,0};
                vals[8] = new int[] {3,0,0,8,4,0,0,6,0};
                break;
            case noSolution:
                vals[0] = new int[] {0,0,6,0,0,0,0,0,0};
                vals[1] = new int[] {0,0,0,0,0,0,0,0,0};
                vals[2] = new int[] {0,0,0,0,0,0,3,0,2};
                vals[3] = new int[] {0,0,0,0,0,0,0,0,0};
                vals[4] = new int[] {0,0,0,0,0,8,0,0,0};
                vals[5] = new int[] {0,0,0,0,0,0,0,0,0};
                vals[6] = new int[] {3,8,0,0,0,0,9,0,0};
                vals[7] = new int[] {6,0,1,0,5,0,0,0,0};
                vals[8] = new int[] {0,9,0,3,0,0,7,2,4};
                break;
            case hardNoSolution:
                vals[0] = new int[] {2,0,0,0,0,0,0,0,0};
                vals[1] = new int[] {0,4,8,0,0,0,0,5,0};
                vals[2] = new int[] {0,0,0,0,0,0,7,9,8};
                vals[3] = new int[] {0,0,0,0,0,0,2,0,0};
                vals[4] = new int[] {0,0,0,0,0,0,0,0,5};
                vals[5] = new int[] {5,0,0,0,0,0,0,0,0};
                vals[6] = new int[] {8,6,0,0,0,0,1,0,0};
                vals[7] = new int[] {0,5,0,0,0,0,0,0,0};
                vals[8] = new int[] {0,0,0,0,0,0,4,0,0};
                break;
            case random:
            default:
                ArrayList<Integer> preset = new ArrayList<Integer>();
                while (preset.size() < numCells)
                {
                    int r = rand.nextInt(81);
                    if (!preset.contains(r))
                    {
                        preset.add(r);
                        int x = r / 9;
                        int y = r % 9;
                        if (!assignRandomValue(x, y))
                            return false;
                    }
                }
                break;
        }
        return true;
    }
    
    public final boolean assignRandomValue(int x, int y){
        ArrayList<Integer> pval = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        
        while(!pval.isEmpty()){
            int ind = rand.nextInt(pval.size());
            int i = pval.get(ind);
            if (valid(x,y,i)) {
                vals[x][y] = i;
                return true;
            } else 
                pval.remove(ind);
        }
        System.err.println("No valid moves exist.  Recreating board.");
        for (int r = 0; r < 9; r++){
            for(int c=0;c<9;c++){
                vals[r][c] = 0;
            }    }
        return false;
    }
    
    private void Finished(boolean success){
        if(success) {
            board.writeVals();
            board.showMessage("Solved in " + myformat.format(ops) + " ops \t(" + myformat.format(recursions) + " recusive ops)");
        } else {
            board.showMessage("No valid configuration found in " + myformat.format(ops) + " ops \t(" + myformat.format(recursions) + " recursive ops)");
        }
    }
    
    public static void main(String[] args) {
        
        @SuppressWarnings("resource")
        Scanner scan = new Scanner(System.in);
        alg = algorithm.valueOf("AC3");

        System.out.println("difficulty? \teasy (e), medium (m), noSolution (n), hardNoSolution (h), random (r)");
     
        char c='*';
    
        
        while(c!='e'&& c!='m'&&c!='n'&&c!='h'&&c!='r'){
            c = scan.nextLine().charAt(0);
            
            if(c=='e')
                level = difficulty.valueOf("easy");
            else if(c=='m')
                level = difficulty.valueOf("medium");
            else if(c=='n')
                level = difficulty.valueOf("noSolution");
            else if(c=='h')
                level = difficulty.valueOf("hardNoSolution");
            else if(c=='r')
                level = difficulty.valueOf("random");
            else{
                System.out.println("difficulty? \teasy (e), medium (m), noSolution (n), hardNoSolution (h), random(r)");
            }
            //System.out.println("2: "+c+" "+level);
        }
        
        System.out.println("Gui? y or n ");
        c=scan.nextLine().charAt(0);
        
        if (c=='n')
            gui = false;
        else 
            gui = true;

        //System.out.println("c: "+c+", Difficulty: " + level);

        //System.out.println("Difficulty: " + level);
        
        SudokuPlayer app = new SudokuPlayer();
        app.run();
    }


    class Board {
        GUI G = null;
        boolean gui = true;
        
        public Board(boolean X, SudokuPlayer s) {
            gui = X;
            if (gui)
                G = new GUI(s);
        }

        public void initVals(int[][] vals){
            G.initVals(vals);
        }

        public void writeVals(){
            if (gui)
                G.writeVals();
            else {
                for (int r = 0; r < 9; r++) {
                    if (r % 3 == 0)
                        System.out.println(" ----------------------------");
                    for (int c = 0; c < 9; c++) {
                        if (c % 3 == 0)
                            System.out.print (" | ");
                        if (vals[r][c] != 0) {
                            System.out.print(vals[r][c] + " ");
                        } else {
                            System.out.print("_ ");
                        }
                    }
                    System.out.println(" | ");
                }
                System.out.println(" ----------------------------");
            }
        }

        public void Clear(){
            if(gui)
                G.clear();
        }

        public void showMessage(String msg) {
            if (gui)
                G.showMessage(msg);
            System.out.println(msg);
        }

        public void updateVals(int[][] vals){
            if (gui)
                G.updateVals(vals);
        }

    }

    class GUI {
        // ---- Graphics ---- //
        int size = 40;
        JFrame mainFrame = null;
        JTextField[][] cells;
        JPanel[][] blocks;

        public void initVals(int[][] vals){
            // Mark in gray as fixed
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    if (vals[r][c] != 0) {
                        cells[r][c].setText(vals[r][c] + "");
                        cells[r][c].setEditable(false);
                        cells[r][c].setBackground(Color.lightGray);
                    }
                }
            }
        }

        public void showMessage(String msg){
            JOptionPane.showMessageDialog(null,
               msg,"Message",JOptionPane.INFORMATION_MESSAGE);
        }

        public void updateVals(int[][] vals) {
            
            System.out.println("calling update");
            for (int r = 0; r < 9; r++) {
                for (int c=0; c < 9; c++) {
                    try {
                        vals[r][c] = Integer.parseInt(cells[r][c].getText());
                    } catch (java.lang.NumberFormatException e) {
                        System.out.println("Invalid Board: rc: "+r+" "+c);
                        showMessage("Invalid Board: rc: "+r+" "+c);
                        return;
                    }
                }
            }
        }

        public void clear() {
            for (int r = 0; r < 9; r++){
                for (int c = 0; c < 9; c++){
                    if (cells[r][c].isEditable())
                    {
                        cells[r][c].setText("");
                        vals[r][c] = 0;
                    } else {
                        cells[r][c].setText("" + vals[r][c]);
                    }
                }
            }
        }

        public void writeVals(){
            for (int r=0;r<9;r++){
                for(int c=0; c<9; c++){
                    cells[r][c].setText(vals[r][c] + "");
            }   }
        }

        public GUI(SudokuPlayer s){

            mainFrame = new javax.swing.JFrame();
            mainFrame.setLayout(new BorderLayout());
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            JPanel gamePanel = new javax.swing.JPanel();
            gamePanel.setBackground(Color.black);
            mainFrame.add(gamePanel, BorderLayout.NORTH);
            gamePanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            gamePanel.setLayout(new GridLayout(3,3,3,3));
            
            blocks = new JPanel[3][3];
            for (int i = 0; i < 3; i++){
                for(int j =2 ;j>=0 ;j--){
                    blocks[i][j] = new JPanel();
                    blocks[i][j].setLayout(new GridLayout(3,3));
                    gamePanel.add(blocks[i][j]);
                }
            }
            
            cells = new JTextField[9][9];
            for (int cell = 0; cell < 81; cell++){
                int i = cell / 9;
                int j = cell % 9;
                cells[i][j] = new JTextField();
                cells[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cells[i][j].setHorizontalAlignment(JTextField.CENTER);
                cells[i][j].setSize(new java.awt.Dimension(size, size));
                cells[i][j].setPreferredSize(new java.awt.Dimension(size, size));
                cells[i][j].setMinimumSize(new java.awt.Dimension(size, size));
                blocks[i/3][j/3].add(cells[i][j]);
            }
            
            JPanel buttonPanel = new JPanel(new FlowLayout());
            mainFrame.add(buttonPanel, BorderLayout.SOUTH);
            //JButton DFS_Button = new JButton("DFS");
            //DFS_Button.addActionListener(s);
            JButton AC3_Button = new JButton("AC-3");
            AC3_Button.addActionListener(s);
            JButton Clear_Button = new JButton("Clear");
            Clear_Button.addActionListener(s);
            JButton Check_Button = new JButton("Check");
            Check_Button.addActionListener(s);
            //buttonPanel.add(DFS_Button);
            buttonPanel.add(AC3_Button);
            buttonPanel.add(Clear_Button);
            buttonPanel.add(Check_Button);
            
            mainFrame.pack();
            mainFrame.setVisible(true);

        }
    }

    Random rand = new Random();
    
    // ----- Helper ---- //
    static algorithm alg = algorithm.AC3;
    static difficulty level = difficulty.easy;
    static boolean gui = true;
    static int ops;
    static int recursions;  
    static int numCells = 15;
    static DecimalFormat myformat = new DecimalFormat("###,###");
}