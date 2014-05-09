package maze;

public class Position {
	// x: row, y: column
	private int x;
	private int y;

	@Override
	public boolean equals(Object obj) {
		if (this.x == ((Position) obj).x && this.y == ((Position) obj).y)
			return true;
		else
			return false;
	}

	/**
	 * @description copy position
	 * @param p
	 */
	public void copyPos(Position p) {
		this.x = p.getX();
		this.y = p.getY();
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void addX() {
		//this.x++;
		this.x+=2;
	}

	public void subX() {
		//this.x--;
		this.x-=2;
	}

	public void addY() {
		//this.y++;
		this.y+=2;
	}

	public void subY() {
		//this.y--;
		this.y-=2;
	}
}
