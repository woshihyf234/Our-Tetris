import java.lang.Object;
import java.util.Timer;
import java.util.TimerTask;

class Cell implements CellComponent{
	private int row;
	private int col;
	
	Cell(int _row, int _col)
	{
		this.row = _row;
		this.col = _col;
	}
	
	public int getrow() {
		return this.row;
	}
	
	public int getcol() {
		return this.col;
	}
	
	public CellComponent Up() {
		throw new UnsupportedOperationException("");
	}
	
	public void Down() {
		row ++;
	}
	
	public void Left() {
		col --;
	}
	
	public void Right() {
		col ++;
	}
}

