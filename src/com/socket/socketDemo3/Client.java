package com.socket.socketDemo3;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args){
        try(Socket socket=new Socket()) {   //先创建socket再连接
            socket.connect(new InetSocketAddress("localhost",8080),1000);
            System.out.println("已连接到服务端");
            //1.读取文件（读到文件输入流）
            FileInputStream fileInputStream=new FileInputStream("src/com/socketDemo3/test.txt");
            //2.将文件从socket输出流发送出去
            OutputStream socketOutputStream=socket.getOutputStream();
            byte[] bytes=new byte[8192];//分次读取，每次8KB（避免直接将这个文件存在内存中）
            int i;  //每次实际读到的字节数，若为末尾则为-1
            while ((i=fileInputStream.read(bytes))!=-1){//将文件输入流读到bytes数组中（不断读满覆盖）
                socketOutputStream.write(bytes,0,i);    //将bytes数组中的内容写到socket的输出流缓冲区
            }
            //文件读到末尾了，可刷写发送
            socketOutputStream.flush();
            socketOutputStream.close();
        } catch (IOException e) {
            System.out.println("服务端连接失败");
            throw new RuntimeException(e);
        }
    }
}
