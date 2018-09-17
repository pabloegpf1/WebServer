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
 static Resource resource;


 public static void main( String[] args ) throws IOException{
  
  responseFactory = new ResponseFactory();
  httpdConf = new HttpdConf("conf/httpd.conf");
  mimeTypes = new MimeTypes("conf/mime.types");

  start();

 }

 public static void start()throws IOException{

  socket = new ServerSocket( httpdConf.listen );
  Socket client = null;
 	
  while( true ){
   client = socket.accept();
   request = createRequest( client );
   resource = new Resource(request.getUriString(), httpdConf);
   response = responseFactory.getResponse(request,resource);
   client.close();
  }

 }

 protected static Request createRequest( Socket client ) throws IOException {
  return request = new Request(client.getInputStream());
 }

}

