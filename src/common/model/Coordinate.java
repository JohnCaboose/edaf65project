package common.model;

public class Coordinate{
	
	public int x, y;
	
	@Override
	public boolean equals(Object other) {
		if(this == other) {
			return true;
		}
		
		if(other == null || (this.getClass() != other.getClass())) {
			return false;
		}
		
		Coordinate othercoord = (Coordinate) other;
		return (this.x == othercoord.x && this.y == othercoord.y);
	}

}
