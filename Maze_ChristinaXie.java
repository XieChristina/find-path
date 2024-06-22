/*=================================================================
Maze_ChristinaXie
Christina Xie
Nov 28, 2022
Java SE 8
=================================================================
Problem Definition – Required to find path from rat/mouse to cheese and then to exit
Input – Text file of the maze
Output – The shortest path from the rat/mouse to cheese and then to exit
Process – Find the number of steps for each possible location in the maze
		  starting from the cheese. Then output the shortest path from the 
		  cheese to the starting position and exit.
=================================================================
List of Static Variables 
 * static Character[][] maze - character 2d array of original maze
 * static Character[][] sol - character 2d array containing the solution maze
 * static int[][] score - integer 2d array that keeps track of the steps to each location from cheese
 * static int[][] path - integer 2d array that keeps track of the direction that the rat/mouse is going
 * static boolean isCheese - boolean to check if there is a cheese
 * static boolean findMouse - boolean to check if rat/mouse has been found/exists
 * static boolean findExit - boolean to check if exit has been found/exists
 * static int R - integer for number of rows
 * static int C - integer for number of columns
 * static Pair start, cheese, exit - Pair objects to keep track of the coordinates of the starting
 * 									 position, cheese, and exit
 * static char ratOrMouse - character to keep track if the starting position is 'R' or 'M'
=================================================================
*/

import java.io.*;
import java.util.*;
public class Maze_ChristinaXie {	//DRIVER CLASS
	
	static Character[][] maze;
	static Character[][] sol;
	static int[][] score;
	static int[][] path;
	static boolean isCheese = false;
	static boolean findMouse=false;
	static boolean findExit = false;
	static int R = 8;
	static int C = 12;
	static Pair start, cheese, exit;
	static char ratOrMouse = ' ';
	
	/**main method:
	* This procedural method is called automatically and is used to organize the calling of other methods defined in the class
	*
	* @param args <type String>
	* @throws IO Exception
	* @return void
	*/
	
	public static void main(String[] args) throws IOException{
		//initialize static arrays
		maze = new Character[R][C];
		sol = new Character[R][C];
		score = new int[R][C];
		path = new int[R][C];
		
		getInput();
		
		//print legend
		System.out.println("\n"
				+ "\n************************************"
				+ "\nLegend"
				+ "\nB   --> Barrier"
				+ "\nR/M --> Rat/mouse (starting position)"
				+ "\nC   --> Cheese"
				+ "\nX   --> Exit"
				+ "\nS   --> Steps"
				+ "\n************************************"
				+ "\n");
		
		printOrigMaze();	//display the original maze
		findPath(0);		//find shortest path from cheese to rat/mouse and exit
		
		if(!isCheese) System.out.println("NO CHEESE, NO SOLUTIONS");	//if there is no cheese, there is no solution
		else {
			System.out.println("STEPS FROM RAT/MOUSE TO CHEESE:");	
			System.out.println();
			
			if(!findMouse) { System.out.println("NO SOLUTION"); System.out.println(); }	//if no rat/mouse, no solution from rat/mouse to cheese
			else {
				//rat/mouse to cheese
				resetSolGrid();									//reset the solution grid
				outputPath(start.getX(),start.getY(),cheese.getX(),cheese.getY());	//fill the solution grid with path from start to cheese
				sol[cheese.getX()][cheese.getY()] = 'C';		//fill the cheese with 'C' in the solution grid
				sol[start.getX()][start.getY()] = ratOrMouse;	//fill the mouse/rat with 'R'/'M' in the solution grid
				printSol();										//print the solution grid
				
				System.out.println("STEPS FROM CHEESE TO EXIT:");	
				System.out.println();
				
				if(!findExit) { System.out.println("NO SOLUTION"); System.out.println(); } //if no exit, no solution from cheese to exit
				else {
					//cheese to exit	
					resetSolGrid();								//reset the solution grid						
					outputPath(exit.getX(),exit.getY(),cheese.getX(),cheese.getY());//fill the solution grid with path from exit to cheese
					sol[cheese.getX()][cheese.getY()] = 'C';	//fill the cheese with 'C' in the solution grid
					sol[exit.getX()][exit.getY()] = 'X';		//fill the exit with 'X' in the solution grid
					printSol();									//print the solution grid
					
				}
			}
		}
	}//end of main method
	
	/**getInput method:
	* This functional method reads input
	*
	* List of Local Variables
	* BufferedReader br - a BufferedReader object used to read file input
	* String input - a String variable to get input for each row
	* String[] s - a String array for splitting input by spaces
	*
	* @param none
	* @throws IO Exception
	* @return void
	*/
	public static void getInput() throws IOException, FileNotFoundException{
		//*****************************************************************************
		//*****************************************************************************
		//Input path located here

		BufferedReader br = new BufferedReader (new FileReader ("mazefile0.txt"));

		//******************************************************************************
		//******************************************************************************
		
		//get input
		for(int i=0;i<R;i++) {							//for each row
			String input = br.readLine();				//get the string input for the whole row
			String[] s = input.split(" ");				//split each line of input by space
			for(int j=0;j<C;j++) {						//for each column
				maze[i][j] = s[j].charAt(0);			//fill the maze array with each character
				
				score[i][j] = -1;						//set everything in the score array to -1
				if(s[j].charAt(0)=='C') score[i][j]=0;	//find and set the score of cheese to 0
														//(since it's the starting level)
				
				//find and store location of rat, cheese, and exit
				if(s[j].charAt(0) == 'R'|| s[j].charAt(0) == 'M') {	start = new Pair(i, j); ratOrMouse = s[j].charAt(0); }
				if(s[j].charAt(0) == 'C') {cheese = new Pair(i, j); isCheese = true;}
				if(s[j].charAt(0) == 'X') exit = new Pair(i, j); 
			}
		}
	}//end of getInput method
	
	/**resetSolGrid method:
	* This functional method resets the solution grid by copying the original maze
	*
	* @param none
	* @throws none
	* @return void
	*/
	public static void resetSolGrid() {	
		for(int i=0;i<R;i++) {		
			for(int j=0;j<C;j++) {
				sol[i][j] = maze[i][j];
			}
		}
	}//end of resetSolGrid
	
	/**printOrigMaze method:
	* This functional method prints the original maze
	*
	* @param none
	* @throws none
	* @return void
	*/
	public static void printOrigMaze() {
		System.out.println("ORIGINAL MAZE:");
		System.out.println();
		for(int i=0;i<R;i++) {					//go through the whole 2d array and output the characters
			for(int j=0;j<C;j++) {
				System.out.print(maze[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}//end of printOrigMaze 
	
	/**printSol method:
	* This functional method prints the solution maze
	*
	* @param none
	* @throws none
	* @return void
	*/
	public static void printSol() {
		for(int i=0;i<R;i++) {
			for(int j=0;j<C;j++) {
				System.out.print(sol[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}//end of printSol 
	
	/**findPath method:
	* This functional method that finds the shortest path from the cheese to the rat/mouse and to the exit
	*
	* List of Local Variables
	* boolean change - keep track if there is any more positions that the rat/mouse can move to
	* int[][] dir - to keep an array for the directions that the rat/mouse can go
	* int x - keep new x-coordinate
	* int y - keep new y-coordinate
	*
	* @param int level - to keep track of the level of this step
	* @throws none
	* @return void
	*/
	public static void findPath(int level) {
		int[][] dir = {{0,1},{0,-1}, {1,0}, {-1,0} };
		boolean change = false;					//set boolean change to false to keep track if there is any more positions that the rat/mouse can move to
		for(int r=0; r<R; r++) {				//loop through whole grid
			for(int c=0; c<C; c++) {
				if(score[r][c]==level) {		//if the level of a position is equal to level
					for(int k=0; k<4; k++) {	//loop through 4 directions
						//get new coordinates
						int x=r+dir[k][0];							//set new coordinates
						int y=c+dir[k][1];							//set new coordinates
						if( x<0 || y<0 || x>=R || y>=C) continue;	//if x and/or y are out of bounds, continue
						if( maze[x][y]=='B') continue;				//if this position is a barrier, continue
						
						//if the level is -1 (which means it has not been visited)
						if( score[x][y] == -1 ) { 
							score[x][y]=level+1; //set score[x][y] to level+1 since this position in the maze can be reached one step more than the previous position
							path[x][y]=k; 		 //set path[x][y] = k to keep track of direction the rat/mouse goes(to help with output)
							change=true; 		 //set change to true to indicate that there was a move possible
						} 
						if( maze[x][y]=='M' || maze[x][y]=='R') { findMouse=true; }	//if the rat/mouse is found, set findMouse to true
						if( maze[x][y]=='X') { findExit=true;}						//if 'X' is found, set findExit to true
					}
				}
			}
		}
		if(change) findPath(level+1);  //call recursion for next level
	}
	
	/**outputPath method:
	* This functional method fills the path of the solution to sol[][]
	*
	* @param 	int sx - the starting x value
	* 			int sy - the starting y value
	* 			int tx - the target x value
	* 			int ty - the target y value
	* @throws none
	* @return void
	*/
	public static void outputPath(int sx, int sy, int tx, int ty) {
		while( true ) {
			switch( path[sx][sy]){	
			   case 0: { sol[sx][sy-1] = 'S'; sy-=1; break;}	//if it is 0, fill position on left with 'S' 
			   case 1: { sol[sx][sy+1] = 'S'; sy+=1; break;}	//if it is 1, fill position on right with 'S' 
			   case 2: { sol[sx-1][sy] = 'S'; sx-=1; break;}	//if it is 2, fill position on up with 'S' 
			   case 3: { sol[sx+1][sy] = 'S'; sx+=1; break;}	//if it is 3, fill position on down with 'S' 
			}
			if( sx==tx && sy==ty) break;	//if target is reached, it is finished
		}
	}//end of outputPath
}//end of Maze_ChristinaXie 

