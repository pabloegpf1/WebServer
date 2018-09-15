import java.io.IOException;
import java.net.*;
import java.io.*;

public class Server{

 static HttpdConf httpdConf;
 static MimeTypes mimeTypes;
 static Request request;
 static ServerSocket socket;
 static ResponseFactory responseFactory;
 static Response response;


 public static void main( String[] args ) throws IOException{
  
  httpdConf = new HttpdConf("conf/httpd.conf");
  mimeTypes = new MimeTypes("conf/mime.types");

  //httpdConf.printConfig();
  //mimeTypes.printTypes();

  start();

 }

 public static void start(){

  socket = new ServerSocket( httpdConf.listen );
  Socket client = null;
 	
  while( true ){
   client = socket.accept();
   request = createRequest( client );
   response = responseFactory.getResponse(request,);
   response.send();
   client.close();
  }

 }

 protected static void createRequest( Socket client ) throws IOException {
  request = new Request(client.getInputStream());
 }

}

