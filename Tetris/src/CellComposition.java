
import java.awt.Color;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;
import java.lang.Object;

public class CellComposition implements CellComponent{
	protected Cell[] cells;
	protected boolean autoDownStatus;
	
	Timer timer;
	
	public CellComposition(int[] rows, int[] cols) {
		if(rows.length != cols.length)
		{
			throw new IllegalArgumentException("rows.length not equal to cols.length");
		}
		
		this.cells = new Cell[rows.length];
		
		for(int i = 0 ; i < rows.length ; i ++)
		{
			this.cells[i] = new Cell(rows[i], cols[i]);
		}
		
		this.timer=new Timer();
		this.timer.schedule(new TimerTask() {
			public void run() 
			{
				if(autoDownStatus) {
					Down();
				}
			}
		},0,100);
		
		autoDownStatus = false;
	}
	
	public void enableAudoDown() {
		autoDownStatus = true;
	}
	
	public void disableAutoDown() {
		autoDownStatus = false;
	}
	
	public void Down() {
		for(int i = 0 ; i < cells.length ; i ++)
			cells[i].Down();
	}
	
	public void Left() {
		for(int i = 0 ; i < cells.length ; i ++)
			cells[i].Left();
	}
	//�����ƶ�
	public void Right() {
		for(int i = 0 ; i < cells.length ; i ++)
			cells[i].Right();
	}
	
	//��ת,û�в��þ�����ת��ʽ���ص㲻������
	public CellComponent Up() {
		int x0=0;
		int y0=0;
		for(int i=0;i<4;i++){
			x0+=this.cells[i].getrow();
			y0+=this.cells[i].getcol();
		}
		x0=x0/4;
		y0=y0/4;
		
		int[] newRows = new int[cells.length];
		int[] newCols = new int[cells.length];
		
		for(int i = 0 ; i < cells.length ; i ++) {
			int x1=y0-this.cells[i].getcol()+x0;
			int y1=this.cells[i].getrow()-x0+y0;
			newRows[i] = x1;
			newCols[i] = y1;
		}
		
		return new CellComposition(newRows, newCols);
	}
	
	//���ط��������
	public int[][] getaxis(){
		int[][] result=new int[4][2];
		for(int i=0;i<4;i++) {
			result[i][0]=this.cells[i].getrow();
			result[i][1]=this.cells[i].getcol();
		}
		return result;
	}
	
	//���ط����������ֵ��Ҳ��������µ�����
	public int getLowestRow() {
		int result=0;
		for(int i=0;i<4;i++) {
			if(cells[i].getrow()>result)
				result=cells[i].getrow();
		}
		
		return result;
	}
	
	//���ط����������ֵ��Ҳ���������������
	public int getHighestRow() {
		int result=cells[0].getrow();
		for(int i=0;i<4;i++) {
			if(cells[i].getrow()<result)
				result=cells[i].getrow();
		}
		return result;
	}
}
