
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

		JFrame frame=new JFrame("Tetris");// ����һ����Ϸ����Ŀ��
		frame.setBounds(0,0,1000,1000);//���ÿ�ܵĴ�С    ��ܴ�СҪЭ����Ȼ��ʾ������
		//frame.setResizable(false);//���ÿ�ܴ�СΪ���ܸı�
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// ����رհ�ť �ر���Ϸ����
		
		String[] options= {"����ģʽ","˫��ģʽ", "����ģʽ", "����ģʽ"};
		int option=JOptionPane.showOptionDialog(null,"ģʽѡ��","ģʽ", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null, options,options[0]);
		
		if(option == 0) {
			TetrisWrapper tetriswrapper=new TetrisWrapper(1);	
			frame.add(tetriswrapper);
			frame.setVisible(true);// ������ʾ��Ϸ����
		}
		else if(option == 1){
			TetrisWrapper tetriswrapper=new TetrisWrapper(2);
			frame.add(tetriswrapper);
			frame.setVisible(true);// ������ʾ��Ϸ����
		}
		else if(option == 2){
			TetrisWrapper tetriswrapper=new TetrisWrapper(3);
			frame.add(tetriswrapper);
			frame.setVisible(true);// ������ʾ��Ϸ����
		}
		else {
			String[] options2= {"��������","���뷿��"};
			int option2=JOptionPane.showOptionDialog(null,"ģʽѡ��","ģʽ", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null, options,options[0]);
			if(option2 == 0) {
				int port = 6678;
				Server server = new Server(6678);
			}
		}
	}

}

