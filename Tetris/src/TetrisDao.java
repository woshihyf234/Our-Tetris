import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Random;

public class TetrisDao {
	Random rand=new Random();

    int[][] map;
    public int score=0;

    boolean isStarted=false;
    boolean isFailed=false;

    private final int totalwidth;
    private final int totalheight;
    
    private final int keys[];
    
	public Cells block;
    
    public TetrisDao(int[][] Maps, int[] Keys) {
    	this.map=Maps;
    	this.keys = Keys;
    	this.totalheight = map.length;
    	this.totalwidth=map[0].length;
    	int which = rand.nextInt(7) + 1;
    	block = new Cells(this.map, which);
    	setup();
    }
	 
	 //if gameover ,restart param
	 public void setup() {
		 isFailed=false;
		 isStarted=false;
		 score=0;
		 
		 //init map ,make wall
		 for(int j=0;j<this.totalwidth;j++) {// make the lowest row and the highest row to be wall
			 map[0][j]=2;
			 map[this.totalheight - 1][j]=2;
		 }
		 
		 //0~33   0~16 17~33  make col to be wall
		 for(int i=0;i<this.totalheight;i++) {
			 map[i][0]=2;
			 map[i][this.totalwidth - 1]=2;
		 }
	 }

//judge if fail
    public boolean pf_failed(Cells block) {
        int high = block.getHighestRow();
        int low = block.getLowestRow();

        int[][] axis=block.getaxis();

        for(int i=0;i<4;i++) {
            if(map[axis[i][0]+1][axis[i][1]]!=0&&high<=1) {
                this.isFailed=true;
                break;
            }
        }
        return this.isFailed;
    }

    //judge if delete
    public boolean delete() {
        boolean pf=false;
        for(int i=this.totalheight-2;i>=1;i--) {
            pf=true;
            for(int j=1;j<this.totalwidth - 1;j++) {
                if(map[i][j]==0)
                    pf=false;
            }
            if(pf==true) {
                score+=1;
                for(int k=i;k>=1;k--) {
                    for(int j=1;j<this.totalwidth - 1;j++) {
                        if(k!=1)
                            map[k][j]=map[k-1][j];
                        else
                            map[k][j]=0;
                    }
                }
                i++;
            }
        }
        return pf;
    }

    //judge if board
    public boolean isBoarded(Cells block) {
        int[][] axis=block.getaxis();
        for(int i=0;i<4;i++) {
            if(map[axis[i][0]+1][axis[i][1]]!=0) {
                return true;
            }
            if(axis[i][0]==30)
            	return true;
        }
        return false;
    }
    
    public void keyPressedInternal(int code) {
    	if(keys[0] == code) {
    		this.block.rotate();
    	}
    	else if(keys[1] == code) {
    		this.block.D();
    	}
    	else if(keys[2] == code) {
    		this.block.L();
    	}
    	else if(keys[3] == code) {
    		this.block.R();
    	}
    }
    
    public void enableAudoDown() {
    	this.block.enableAudoDown();
    }
    
    public void disableAutoDown() {
    	this.block.disableAutoDown();
    }
    
    public void createBlock() {
		int which=rand.nextInt(7)+1;
		this.block=new Cells(this.map,which);
		this.block.enableAudoDown();
    }
}
