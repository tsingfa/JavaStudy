## socketDemo1：建立连接
建立基本的socket服务端、客户端，二者建立连接。

try with resource 语法糖

## socketDemo2：双向传输消息
建立连接，通过socket输入流和输出流，实现客户端与服务端之间双向收发数据（发送、回复功能）


一些零碎的功能：
```Java
socket.setKeepAlive(true);//TCP KeepAlive机制

//如有需要，可以关闭临时的输入输出流：
socket.shutdownOutput();
socket.shutdownInput();

//设置连接后消息等待时间，如果长时间无消息则抛出异常
socket.setSoTimeout(5000);  //等待5s

//手动设置TCP缓冲区大小
socket.setReceiveBufferSize(25565);//接收缓冲区
socket.setSendBufferSize(25565);//发送缓冲区
```

## socketDemo3：传输文件

```Java
//1.Client
//文件 -> 文件输入流 ->buffer分次读取，直至读取完
// -> socket输出流缓冲区 -> flush刷写清空，从socket发送出去

//1.1读取文件（读到文件输入流）
FileInputStream fileInputStream=new FileInputStream("src/com/socketDemo3/test.txt");
//1.2将文件从socket输出流发送出去
OutputStream socketOutputStream=socket.getOutputStream();
byte[] bytes=new byte[8192];//分次读取，每次8KB（避免直接将这个文件存在内存中）
int i;  //每次实际读到的字节数，若为末尾则为-1
while ((i=fileInputStream.read(bytes))!=-1){//将文件输入流读到bytes数组中（不断读满覆盖）
    socketOutputStream.write(bytes,0,i);    //将bytes数组中的内容写到socket的输出流缓冲区
}
//文件读到末尾了，可刷写发送
socketOutputStream.flush();
socketOutputStream.close();



//2.Server
//socket输入流缓冲区 -> buffer分次读取，直至读取完
// -> 文件系统输出流缓冲区 -> flush刷写清空，存到文件系统

//2.1接收客户端文件（流式输入，socket输入流）
InputStream stream=socket.getInputStream();
//2.2保存到服务端本地文件的文件输出流
FileOutputStream fileOutputStream=new FileOutputStream("saved/test.txt");
byte[] bytes=new byte[8192];//分次读取，每次8KB（避免直接将这个文件存在内存中）
int i;  //每次实际读到的字节数，若为末尾则为-1
while ((i=stream.read(bytes))!=-1){//将socket输入流读到bytes数组中（不断读满覆盖）
        fileOutputStream.write(bytes,0,i);    //将bytes数组中的内容写到文件系统输出流
}
fileOutputStream.flush();//刷写至文件系统
fileOutputStream.close();
```

## socketDemo4：浏览器访问socket服务器

本节仅实现socketServer，使用浏览器作为client

OutputStream 适用于处理字节流
OutputStreamWriter 适用于处理文本字符流
BufferedWriter 适用于处理大批量的文本字符流

