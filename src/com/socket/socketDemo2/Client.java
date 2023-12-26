package com.socket.socketDemo2;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args){
        try(Socket socket=new Socket();
            Scanner scanner=new Scanner(System.in)) {   //先创建socket再连接
            socket.setKeepAlive(true);//TCP KeepAlive机制
            socket.connect(new InetSocketAddress("localhost",8080),1000);
            System.out.println("已连接到服务端");
            //1.发送数据给服务端
            //将输出流通过writer写给服务端
            OutputStreamWriter writer=new OutputStreamWriter(socket.getOutputStream());
            writer.write(scanner.nextLine()+"\n");
            writer.flush(); //刷缓存，发给服务端
            System.out.println("数据已发送");
            //2.接收服务端回复
            BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg=reader.readLine(); //按行读取
            System.out.println("服务端回复："+msg);
        } catch (IOException e) {
            System.out.println("服务端连接失败");
            throw new RuntimeException(e);
        }
    }
}
