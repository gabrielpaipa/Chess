
public class Coordinates {

	//Heavily inspired in Peter's GoModel way of dealing with locations. We tried arrays of ints, but they weren't as versatile.

	/** X value */
	private int x;

	/** Y value */
	private int y;

	/**Is there an enemy here?*/
	private boolean z;

	/** Creates a new set of coordinates */
	public Coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**Creates a new set of coordinates with the extra boolean that lets know if enemy there*/
	public Coordinates(int x, int y, boolean z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Coordinates addBoolean(){
		Coordinates newloc = new Coordinates(this.x,this.y,true);
		return newloc;
	}

	public boolean getZ(){
		return this.z;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinates other = (Coordinates) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	/**
	 * Returns the column of this location.
	 * 
	 * @see #Location(int, int)
	 */
	public int getX() {
		return this.x;
	}

	public Coordinates change(int i,int j){
		Coordinates newloc = new Coordinates(this.x+i,this.y+j);
		return newloc;
	}

	/**
	 * Returns the row of this location.
	 * 
	 * @see #Location(int, int)
	 */
	public int getY() {
		return this.y;
	}

	@Override
	public String toString(){
		String s="";
		s+=this.getX()+", ";
		s+=this.getY();
		return s;
	}


	public Coordinates north(){
		int x = this.getX();
		int y = this.getY();
		Coordinates newloc = new Coordinates(x,y-1);
		return newloc;
	}
	public Coordinates northeast(){
		int x = this.getX();
		int y = this.getY();
		Coordinates newloc = new Coordinates(x+1,y-1);
		return newloc;
	}
	public Coordinates northwest(){
		int x = this.getX();
		int y = this.getY();
		Coordinates newloc = new Coordinates(x-1,y-1);
		return newloc;
	}
	public Coordinates east(){
		int x = this.getX();
		int y = this.getY();
		Coordinates newloc = new Coordinates(x+1,y);
		return newloc;
	}
	public Coordinates west(){
		int x = this.getX();
		int y = this.getY();
		Coordinates newloc = new Coordinates(x-1,y);
		return newloc;
	}
	public Coordinates south(){
		int x = this.getX();
		int y = this.getY();
		Coordinates newloc = new Coordinates(x,y+1);
		return newloc;
	}
	public Coordinates southwest(){
		int x = this.getX();
		int y = this.getY();
		Coordinates newloc = new Coordinates(x-1,y+1);
		return newloc;
	}
	public Coordinates southeast(){
		int x = this.getX();
		int y = this.getY();
		Coordinates newloc = new Coordinates(x+1,y+1);
		return newloc;
	}

	//		@Override
	//		public int hashCode() {
	//			final int prime = 31;
	//			int result = 1;
	//			result = prime * result + column;
	//			result = prime * result + row;
	//			return result;
	//		}
}
