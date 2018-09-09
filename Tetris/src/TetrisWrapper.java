import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;


public class TetrisWrapper extends JPanel implements KeyListener,ActionListener {

	Timer timer;
	
	TetrisDao[] tetrisdao;
	int num;
	
	boolean finished;
	boolean running;
	
    private final int cellwidth = 20;

    private final int totalwidth = 18; // 1-16 (0 and 17 is border)
    private final int totalheight = 32; // 1-30 (0 and 31 is border)
	
    int[][][] Maps;
    
    int[][] Keys = {{KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D}
    ,{KeyEvent.VK_I, KeyEvent.VK_K, KeyEvent.VK_J, KeyEvent.VK_L}
    ,{KeyEvent.VK_I, KeyEvent.VK_K, KeyEvent.VK_J, KeyEvent.VK_L}};
    
    
    //init class 
	public TetrisWrapper(int num) {
        this.setFocusable(true);
        this.addKeyListener(this);
        tetrisdao = new TetrisDao[num];
        
        this.num = num;
        Maps = new int[this.num][this.totalheight][this.totalwidth];
        for(int i = 0 ; i < this.num ; i ++) {
        	tetrisdao[i] = new TetrisDao(this.Maps[i], Keys[i]);
        }

        finished = false;//if there has one player failed,it false
        running = false;//if there have two players start, it true else false
        
        timer=new Timer(50,this);
        System.out.println(num);
        timer.start();
	}
	
	//communicate with keyboard get information ,get direction of block
    @Override
    public void keyPressed(KeyEvent e) {

    	int code=e.getKeyCode();
    	switch(code) {
	    	case KeyEvent.VK_SPACE:
	    	case KeyEvent.VK_ENTER:
	    		if(this.finished) {
	    			for(int i = 0 ; i < this.num ; i ++)
	    			{
	        			tetrisdao[i].setup();
	    			}
	    		}
	    		else {
	    			this.running=!this.running;
	    			for(int i = 0 ; i < this.num ; i ++)
	    			{
	        			tetrisdao[i].isStarted=!tetrisdao[i].isStarted;
		    			if(tetrisdao[i].isStarted)
		    				tetrisdao[i].enableAudoDown();
		    			else
		    				tetrisdao[i].disableAutoDown();
	    			}
	    		}
	    		break;
	    	default:
	    		if(!this.running || this.finished)
	    			return;
	        	for(int i = 0 ; i < this.num ; i ++) {
	        		this.tetrisdao[i].keyPressedInternal(code);
	        	}
    	}
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
    

    public void actionPerformed(ActionEvent e) {
		if(!this.finished&&this.running) {
			
			for(int i=0;i<this.num;i++) {
				if(tetrisdao[i].isBoarded(this.tetrisdao[i].block)) {
					this.tetrisdao[i].block.disableAutoDown();
					int[][] axis=this.tetrisdao[i].block.getaxis();
					for(int j=0;j<4;j++)
					{
						this.Maps[i][axis[j][0]][axis[j][1]]=1;//input is the same
					}
					
					//map is not painted well ,do not know the problem
					tetrisdao[i].delete();
					tetrisdao[i].createBlock();
					boolean pf=tetrisdao[i].pf_failed(this.tetrisdao[i].block);
					if(pf) {
						this.finished=true;
					}
				}
			}
			if(this.finished) {
				for(int i = 0 ; i < this.num ; i ++)
					tetrisdao[i].disableAutoDown();
			}
		}
		repaint();
    }
    
    private void drawCell(Graphics g, int x, int y) {
        g.drawRect(y,x,this.cellwidth, this.cellwidth);
        g.fillRect(y,x,this.cellwidth, this.cellwidth);
    }
    
    public void paint(Graphics g) {

        g.setColor(Color.GRAY);
        g.fillRect(0,0, 1000, 1000);
        g.setColor(Color.WHITE);
        g.fillRect(0,0,cellwidth*totalwidth*num,cellwidth*totalheight);

        
        
        g.setColor(Color.BLUE);
        for(int k = 0 ; k < this.num ; k ++) {
        	for(int i=0;i<this.totalheight;i++) {
                for(int j=0;j<this.totalwidth;j++) {
                	if(Maps[k][i][j] == 0) {
                		continue;
                	}
                	else if(Maps[k][i][j] == 1) {
                		g.setColor(Color.GREEN);
                	}
                	else if(Maps[k][i][j] == 2) {
                		g.setColor(Color.BLUE);
                	}
                	else
                		continue;
                    int x = cellwidth * i;
                    int y = cellwidth * j + k * cellwidth * this.totalwidth;
                    drawCell(g, x, y);
    	        }
            }

        	g.setColor(Color.GREEN);
        	int[][] tmpBlock = tetrisdao[k].block.getaxis();
        	for(int i = 0 ; i < tmpBlock.length ; i ++) {
                int x = cellwidth * tmpBlock[i][0];
                int y = cellwidth * tmpBlock[i][1] + k * cellwidth * this.totalwidth;
                drawCell(g, x, y);
        	}
        }

//problem :  can not disapear
    	if (!this.running){ 
            g.setColor(Color.BLACK);
            g.setFont(new Font("宋体",Font.BOLD, 25));
            g.drawString("Press space to start / pause!", cellwidth*this.totalwidth * num / 4,cellwidth*this.totalheight/2);
        }
		
		
		if (this.finished){
            g.setColor(Color.RED); 
            g.setFont(new Font("宋体",Font.BOLD, 30));
            g.drawString("Game Over ! ", cellwidth*this.totalwidth * num / 4,cellwidth*this.totalheight/2);
        }
		
		g.setColor(Color.BLACK);
        g.setFont(new Font("宋体",Font.BOLD,25));
        if(num==1)
        	g.drawString("Score : "+tetrisdao[0].score, cellwidth*this.totalwidth * num ,cellwidth*this.totalheight/20);
        else
        {
        	g.drawString("Score : "+tetrisdao[0].score, cellwidth*this.totalwidth * num,cellwidth*this.totalheight/20);
        	g.drawString("Score : "+tetrisdao[1].score, cellwidth*this.totalwidth * num,cellwidth*this.totalheight * 2/20);
        }
        
        
       /* g.setColor(Color.RED);
        g.setFont(new Font("宋体",Font.CENTER_BASELINE ,15));
        g.drawString("E:exit",cellwidth*this.totalwidth,cellwidth*this.totalheight/9*8);*/
        
       /* g.setColor(Color.BLACK);
        g.setFont(new Font("宋体",Font.CENTER_BASELINE ,10));
        g.drawString("↑:"+"旋转",cellwidth*this.totalwidth+20,cellwidth*this.totalheight/6);
        g.drawString("↓:"+"下",cellwidth*this.totalwidth+20,cellwidth*this.totalheight/6+100);
        g.drawString("←:"+"左",cellwidth*this.totalwidth,cellwidth*this.totalheight/6+50);///存疑
        g.drawString("→:"+"右",cellwidth*this.totalwidth+40,cellwidth*this.totalheight/6+50);*/
    }
}
