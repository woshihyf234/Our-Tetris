
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import communication.Server;


public class Main{

	
	public static void main(String[] args) {

		JFrame frame=new JFrame("Tetris");// 创建一个游戏界面的框架
		frame.setBounds(0,0,1000,1000);//设置框架的大小    框架大小要协调不然显示不出来
		//frame.setResizable(false);//设置框架大小为不能改变
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 点击关闭按钮 关闭游戏界面
		
		String[] options= {"单人模式","双人模式", "三人模式", "联网模式"};
		int option=JOptionPane.showOptionDialog(null,"模式选择","模式", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null, options,options[0]);
		
		if(option == 0) {
			TetrisWrapper tetriswrapper=new TetrisWrapper(1);	
			frame.add(tetriswrapper);
			frame.setVisible(true);// 允许显示游戏界面
		}
		else if(option == 1){
			TetrisWrapper tetriswrapper=new TetrisWrapper(2);
			frame.add(tetriswrapper);
			frame.setVisible(true);// 允许显示游戏界面
		}
		else if(option == 2){
			TetrisWrapper tetriswrapper=new TetrisWrapper(3);
			frame.add(tetriswrapper);
			frame.setVisible(true);// 允许显示游戏界面
		}
		else {
			String[] options2= {"创建房间","加入房间"};
			int option2=JOptionPane.showOptionDialog(null,"模式选择","模式", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null, options,options[0]);
			if(option2 == 0) {
				int port = 6678;
				Server server = new Server(6678);
			}
		}
	}

}

