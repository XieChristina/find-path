//*************************************************************************
//Pair Class
//Christina Xie
//Date: November 28, 2022
//*************************************************************************
//<Class>
//This class creates a template for the coordinates of the starting position, cheese, and exit locations
	
//<List Of Identifiers>
//int x - x-coordinate of object location
//int y - y-coordinate of object location
//*************************************************************************


public class Pair{
		private int x, y;
		
		/** Constructor
		*
		* @param x - x-coordinate
		* @param y - y-coordinate
		*/
		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		/** Accessor returns the object's x-coordinates
		 * 
		 * @return x - x-coordinate of object
		 */
		public int getX() {	
			return x;
		}
		
		/** Accessor returns the object's y-coordinates
		 * 
		 * @return y - y-coordinate of object
		 */
		public int getY() {	
			return y;
		}
		
		/** Mutator changes the object's x-coordinates
		 */
		public void setX(int newX) {	
			x=newX;
		}
		
		/** Mutator changes the object's y-coordinates
		 */
		public void setY(int newY) {	
			y=newY;
		}
}//end of Pair