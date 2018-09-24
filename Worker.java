import java.io.IOException;
import java.net.*;
import java.io.*;

public class Worker extends Thread{
  Socket client;
  MimeTypes mimeTypes;
  HttpdConf httpdConf;
  static Request request;
  static ServerSocket socket;
  static ResponseFactory responseFactory;
  static Response response;
  static Resource resource;

 public Worker(HttpdConf httpdConf, MimeTypes mimeTypes, Socket socket){
  client = socket;
  this.mimeTypes = mimeTypes;
  this.httpdConf = httpdConf;
 }

 public void run(){
  System.out.println("Worker on socket: "+client);
  try{
   request = new Request(client.getInputStream());
   resource = new Resource(request.getUriString(), httpdConf);
   response = responseFactory.getResponse(request,resource,mimeTypes);
   /*
   System.out.println("response: "+client);
   request.logRequest(httpdConf.logFile,response.code,response.size);
   System.out.println("log: "+client);
   response.send(client.getOutputStream());
   System.out.println("send: "+client);*/
   client.close();
  }catch(Exception e){
    System.out.println("Error"+client);
    e.printStackTrace();
  }
 }
}
