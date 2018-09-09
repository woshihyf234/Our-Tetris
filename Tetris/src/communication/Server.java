package communication;

import java.awt.Dimension;
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
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Panel;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class Server{

    private Map<Integer, Socket> clients = new HashMap<Integer, Socket>();
    
    private int port;
    public Server(int port) {
        this.port = port;
        listenClient();
    }

    public void listenClient(){
        String temp = "";
        try {
            ServerSocket server = new ServerSocket(this.port);
            // server尝试接收其他Socket的连接请求，server的accept方法是阻塞式的
            while (true) {
                Socket socket = server.accept();
                clients.put(socket.getPort(), socket);
                temp = "客户端"+socket.getPort()+":连接";
                if(clients.size() == 2) {
                    new mythread(socket, this).start();
                    System.out.println("Everyone is here, ready.");
                }
                else
                	continue;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    
	public static void main(String[] args) {
		String str = JOptionPane.showInputDialog("请输入房间号:");
		Server server = new Server(Integer.parseInt(str));
	}

    public void sendMsgToAll(Socket fromSocket, String msg) {
        Set<Integer> keset = this.clients.keySet();
        java.util.Iterator<Integer> iter = keset.iterator();
        while(iter.hasNext()){
            int key = iter.next();
            Socket socket = clients.get(key);
            if(socket != fromSocket){
                try {
                    if(socket.isClosed() == false){
                        if(socket.isOutputShutdown() == false){

                            Writer writer = new OutputStreamWriter(
                                    socket.getOutputStream());
                            writer.write(msg);
                            writer.flush();
                            writer.close();
                        }
                    }
                } catch (SocketException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }
    }
}

class mythread extends Thread{

    private Socket socket = null;
    private Server server = null;
    private InputStreamReader reader = null;
    char chars[] = new char[64];
    int len;
    private String temp = null;
    public mythread(Socket socket, Server server) {
        // TODO Auto-generated constructor stub
        this.socket = socket;
        this.server = server;
        init();
    }

    private void init(){
        try {
            reader = new InputStreamReader(socket.getInputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        System.out.println("子线程开始工作");
        while(true){
            try {
                System.out.println("线程"+this.getId()+":开始从客户端读取数据——>");
                while ((len = ((Reader) reader).read(chars)) != -1) {
                    temp = new String(chars, 0, len);
                    System.out.println(temp);
                    server.sendMsgToAll(this.socket, temp);
                }

                if(socket.getKeepAlive() == false){
                    ((Reader) reader).close();
                    System.out.println("Server is down.");
                    socket.close();
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                try {
                    ((Reader) reader).close();
                    socket.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }
    }
}