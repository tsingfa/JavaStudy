package com.socket.socketDemo1;

import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args){
        try(Socket socket=new Socket("localhost",8080)) {
            System.out.println("已连接到服务端");
        } catch (IOException e) {
            System.out.println("服务端连接失败");
            throw new RuntimeException(e);
        }

    }
}
