package com.socket.socketDemo1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    public static void main(String[] args){
        //创建一个ServerSocket对象
        try(ServerSocket server=new ServerSocket(8080)){//监听8080端口
            System.out.println("正在等待客户端连接");
            while(true){    //死循环，可以不断接收客户端请求
                Socket socket = server.accept();   //（无客户端连接时）阻塞线程，直至客户端连接建立，返回一个socket
                System.out.println("客户端连接已建立，IP地址为："+socket.getInetAddress().getHostAddress());
            }
            //ServerSocket实现了AutoCloseable接口，支持try with resource，可自动关闭
            //server.close(); //操作完成，关闭服务器（解除端口占用）
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}