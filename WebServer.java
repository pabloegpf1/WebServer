import java.io.IOException;
import java.net.*;
import java.io.*;

public class WebServer{

 static HttpdConf httpdConf;
 static MimeTypes mimeTypes;
 static Request request;
 static ServerSocket socket;
 static ResponseFactory responseFactory;
 static Response response;
 static Resource resource;

 public static void main( String[] args ) throws IOException,Exception{
  try{
    responseFactory = new ResponseFactory();
    httpdConf = new HttpdConf("conf/httpd.conf");
    mimeTypes = new MimeTypes("conf/mime.types");
    start();

  }catch(Exception ServerException){
    startWith500();
    ServerException.printStackTrace();
  }
 }

 public static void start()throws IOException, Exception{
  socket = new ServerSocket( httpdConf.listen );
  while( true ){
   Socket client = socket.accept();
   request = new Request(client.getInputStream());
   resource = new Resource(request.getUriString(), httpdConf);
   try{
    response = responseFactory.getResponse(request,resource,mimeTypes);
   }catch(Exception e){
    response = new Response(400,"Bad Request");
   }
   request.logRequest(httpdConf.logFile,response.code,response.size,client.getRemoteSocketAddress().toString());
   response.send(client.getOutputStream(),response.isScript);
   client.close();
  }
 }

 public static void startWith500()throws IOException{
  socket = new ServerSocket( httpdConf.listen );
  while( true ){
   Socket client = socket.accept();
   response = new Response(500,"Internal Server Error");
   response.send(client.getOutputStream(),false);
   client.close();
  }
 }

}
