package communication;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client extends JFrame implements ActionListener{

    // Ϊ�˼���������е��쳣��ֱ��������
    String host = "localhost"; // Ҫ���ӵķ����IP��ַ
    int port; // Ҫ���ӵķ���˶�Ӧ�ļ����˿�
    Thread thread  = null;
    Socket client = null;
    Writer writer = null;

    private JTextArea msg = new JTextArea("�ͻ�����Ϣ������\r\n");
    private JTextArea input = new JTextArea();
    private JButton msgSend = new JButton("����Ⱥ��Ϣ");
    public Client(int port) {
        // TODO Auto-generated constructor stub
    	this.port = port;
        initSocket();
        input.setColumns(40);
        input.setRows(10);
        input.setAutoscrolls(true);
        msgSend.addActionListener(this);
        msgSend.setActionCommand("sendMsg");
        msg.setAutoscrolls(true);
        msg.setColumns(40);
        msg.setRows(25);
        JScrollPane spanel = new JScrollPane(msg);
        JScrollPane editpanel = new JScrollPane(input);
        this.add(spanel);
        this.add(editpanel);
        this.add(msgSend);
    }
    
    public void initSocket(){
        try {
            client = new Socket(this.host, this.port);
            writer = new OutputStreamWriter(client.getOutputStream());
            // �������Ӻ�Ϳ����������д������
            this.appendMsg("�����Ϸ�����");
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.appendMsg("���������Ϸ�����");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.appendMsg("���������Ϸ�����");
        }
    }

    public void appendMsg(String msg){
        this.msg.append(msg+"\r\n");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        String temp = "";
        try {
            if("sendMsg".equals(e.getActionCommand())){
                if((temp = this.input.getText()) != null){
                    writer.write(temp);
                    writer.flush();
                    this.appendMsg("��("+this.client.getLocalPort()+")˵����>"+temp);
                    this.input.setText("");
                }
            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

}