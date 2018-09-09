
import java.util.Timer;
import java.util.TimerTask;

class Cell{
	private int row;
	private int col;
	
	Timer timer;
	public void setrow(int x) {
		this.row=x;
	}
	
	public int getrow() {
		return this.row;
	}
	
	public void setcol(int x) {
		this.col=x;
	}
	
	public int getcol() {
		return this.col;
	}
	
	public void down() {
		row ++;
	}
	
	public void left() {
		col --;
	}
	
	public void right() {
		col ++;
	}
	
	public void AutoMove(boolean x) {
		if(x==true) {
			timer=new Timer();
			timer.schedule(new TimerTask() {
				public void run() 
				{
					row+=1;
				}
			},0,400);
		}else
			timer.cancel();
	}
}

