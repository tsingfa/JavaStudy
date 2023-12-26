package com.socket.socketDemo2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args){
        //创建一个ServerSocket对象
        try(ServerSocket server=new ServerSocket(8080)){//监听8080端口
            System.out.println("正在等待客户端连接");
            while(true) {    //死循环，可以不断接收客户端请求
                Socket socket = server.accept();   //（无客户端连接时）阻塞线程，直至客户端连接建立，返回一个socket
                socket.setKeepAlive(true);//TCP KeepAlive机制
                System.out.println("客户端连接已建立，IP地址为：" + socket.getInetAddress().getHostAddress());
                //设置连接后消息等待时间，如果长时间无消息则抛出异常
                //socket.setSoTimeout(5000);  //等待5s
                //1.接收客户端数据（将socket输入流读到reader）
                BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String msg=reader.readLine(); //按行读取
                System.out.println("客户端消息："+msg);
                //2.向客户端回复数据
                //由服务端输出给客户端
                OutputStreamWriter writer =new OutputStreamWriter(socket.getOutputStream());
                writer.write("已收到客户端消息，内容为："+msg);//回复内容
                writer.flush();//刷写发送
                System.out.println("已回复给客户端");

                socket.close(); //关闭socket连接（server和socket都要关闭）
            }
            //ServerSocket实现了AutoCloseable接口，支持try with resource，可自动关闭
            //server.close(); //操作完成，关闭服务器（解除端口占用）
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}