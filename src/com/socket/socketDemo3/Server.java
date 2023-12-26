package com.socket.socketDemo3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args){
        //创建一个ServerSocket对象
        try(ServerSocket server=new ServerSocket(8080)){//监听8080端口
            System.out.println("正在等待客户端连接");
            while(true) {    //死循环，可以不断接收客户端请求
                Socket socket = server.accept();   //（无客户端连接时）阻塞线程，直至客户端连接建立，返回一个socket
                System.out.println("客户端连接已建立，IP地址为：" + socket.getInetAddress().getHostAddress());
                //1.接收客户端文件（流式输入，socket输入流）
                InputStream stream=socket.getInputStream();
                //2.保存到服务端本地文件的文件输出流
                FileOutputStream fileOutputStream=new FileOutputStream("saved/test.txt");
                byte[] bytes=new byte[8192];//分次读取，每次8KB（避免直接将这个文件存在内存中）
                int i;  //每次实际读到的字节数，若为末尾则为-1
                while ((i=stream.read(bytes))!=-1){//将socket输入流读到bytes数组中（不断读满覆盖）
                    fileOutputStream.write(bytes,0,i);    //将bytes数组中的内容写到文件系统输出流
                }
                fileOutputStream.flush();//刷写至文件系统
                fileOutputStream.close();
                socket.close(); //关闭socket连接（server和socket都要关闭）
            }
            //ServerSocket实现了AutoCloseable接口，支持try with resource，可自动关闭
            //server.close(); //操作完成，关闭服务器（解除端口占用）
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
