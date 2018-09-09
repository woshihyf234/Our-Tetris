
import java.awt.Color;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

public class Cells {
	protected Cell[] cells=new Cell[4];
	protected boolean autoDownStatus = false;
	
	
	protected int which=0;
	private int cellwidth=20;
	
	protected  int[][] map;
	
	Timer timer;
	
	public Cells(int[][] mp,int which) {
		this.which=which;
		AutoMove();
		this.map = mp;
		for(int i = 0 ; i < 4 ; i ++)
			cells[i] = new Cell();
		init_shape();
	}
	
	public void AutoMove() {
		timer=new Timer();
		timer.schedule(new TimerTask() {
			public void run() 
			{
				if(autoDownStatus) {
					D();
				}
			}
		},0,100);
	}
	public void enableAudoDown() {
		autoDownStatus = true;
	}
	
	public void disableAutoDown() {
		autoDownStatus = false;
	}
	
	public void init_shape() {
		
		switch(this.which) {
		case 1:
			this.cells[0].setrow(1);this.cells[1].setrow(1);this.cells[2].setrow(1);this.cells[3].setrow(1);//line
			this.cells[0].setcol(5);this.cells[1].setcol(6);this.cells[2].setcol(7);this.cells[3].setcol(8);
			break;
		case 2:
			this.cells[0].setrow(1);this.cells[1].setrow(1);this.cells[2].setrow(2);this.cells[3].setrow(2);//tian
			this.cells[0].setcol(6);this.cells[1].setcol(7);this.cells[2].setcol(6);this.cells[3].setcol(7);
			break;
		case 3:
			this.cells[0].setrow(1);this.cells[1].setrow(1);this.cells[2].setrow(2);this.cells[3].setrow(2);//Z
			this.cells[0].setcol(5);this.cells[1].setcol(6);this.cells[2].setcol(6);this.cells[3].setcol(7);
			break;
		case 4:
			this.cells[0].setrow(2);this.cells[1].setrow(1);this.cells[2].setrow(2);this.cells[3].setrow(1);//fz
			this.cells[0].setcol(5);this.cells[1].setcol(6);this.cells[2].setcol(6);this.cells[3].setcol(7);
			break;
		case 5:
			this.cells[0].setrow(1);this.cells[1].setrow(2);this.cells[2].setrow(3);this.cells[3].setrow(3);//L
			this.cells[0].setcol(6);this.cells[1].setcol(6);this.cells[2].setcol(6);this.cells[3].setcol(7);
			break;
		case 6:
			this.cells[0].setrow(1);this.cells[1].setrow(2);this.cells[2].setrow(3);this.cells[3].setrow(3);//fL
			this.cells[0].setcol(6);this.cells[1].setcol(6);this.cells[2].setcol(6);this.cells[3].setcol(5);
			break;
		case 7:
			this.cells[0].setrow(1);this.cells[1].setrow(2);this.cells[2].setrow(2);this.cells[3].setrow(2);
			this.cells[0].setcol(6);this.cells[1].setcol(5);this.cells[2].setcol(6);this.cells[3].setcol(7);
			break;
		default:
			break;
			}
	}
	
	//向下移动
	public void D() {
		boolean pf=true;
		for(int i=0;i<4;i++) {
			int x = cells[i].getrow();
			int y = cells[i].getcol();
			if(x >= this.map.length - 1 || map[x + 1][y]!=0) {
				pf=false;
				break;
			}
		}
		/*if(this.getLowestRow()==29)//这个参数很重要
			pf=true;*/
		//simplify this process,the method is showed in  setup() function of class TetrisDao
		if(pf==true) {
			for(int i=0;i<4;i++) {
				cells[i].down();
			}
		}
	}
	//向左移动
	public void L() {
		boolean pf = true;
		for(int k = 0 ; k < 4 ; k ++) {
			int i = cells[k].getrow();
			int j = cells[k].getcol();
			if(j <= 1 || map[i][j - 1] !=0) {
				pf = false;
				break;
			}
		}
		
		if(pf == true) {
			for(int i=0;i<4;i++) {
				this.cells[i].left();
			}
		}
	}
	//向右移动
	public void R() {
		boolean pf = true;
		for(int k = 0 ; k < 4 ; k ++) {
			int i = cells[k].getrow();
			int j = cells[k].getcol();
			if(j >= map[0].length - 1 || map[i][j + 1]!=0) {
				pf = false;
				break;
			}
		}
		if(pf == true) {
			for(int i=0;i<4;i++) {
				this.cells[i].right();
			}
		}
		
	}
	//旋转,没有采用矩阵旋转方式，重点不在这里
	public void rotate() {
		int x0=0;
		int y0=0;
		for(int i=0;i<4;i++){
			x0+=this.cells[i].getrow();
			y0+=this.cells[i].getcol();
		}
		x0=x0/4;
		y0=y0/4;
		
		Cell[] tmpCell = new Cell[4];
		
		for(int i = 0 ; i < 4 ; i ++)
			tmpCell[i] = new Cell();
		
		boolean pf=false;
		for(int i=0;i<4;i++) {
			int x1=y0-this.cells[i].getcol()+x0;
			int y1=this.cells[i].getrow()-x0+y0;
			tmpCell[i].setrow(x1);
			tmpCell[i].setcol(y1);
			if(map[x1][y1]!=0)
				pf=true;
		}
		if(pf==false) {
			for(int i=0;i<4;i++) {
				this.cells[i].setrow(tmpCell[i].getrow());
				this.cells[i].setcol(tmpCell[i].getcol());
			}
		}
	}
	
	

	//返回方块的坐标
	public int[][] getaxis(){
		int[][] result=new int[4][2];
		for(int i=0;i<4;i++) {
			result[i][0]=this.cells[i].getrow();
			result[i][1]=this.cells[i].getcol();
		}
		return result;
	}
	
	
	
	
	//返回方块最下面的值，也就是最底下的行数
	public int getLowestRow() {
		int result=0;
		for(int i=0;i<4;i++) {
			if(cells[i].getrow()>result)
				result=cells[i].getrow();
		}
		
		return result;
	}
	//返回方块最上面的值，也就是最上面的行数
	public int getHighestRow() {
		int result=cells[0].getrow();
		for(int i=0;i<4;i++) {
			if(cells[i].getrow()<result)
				result=cells[i].getrow();
		}
		return result;
	}
}
