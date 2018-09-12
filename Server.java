import java.io.IOException;
import java.net.*;
import java.io.*;

public class Server{

 static HttpdConf httpdConf;
 static MimeTypes mimeTypes;

 public static void main( String[] args ) throws IOException{
  
  httpdConf = new HttpdConf("conf/httpd.conf");
  mimeTypes = new MimeTypes("conf/mime.types");

  //httpdConf.printConfig();
  //mimeTypes.printTypes();

  //System.out.println(httpdConf.listen);

   ServerSocket socket = new ServerSocket( httpdConf.listen );
   Socket client = null;

   while( true ) {
    client = socket.accept();
    System.out.println( "Hello" );
    outputRequest( client );
    client.close();
   }

 }

 protected static void outputRequest( Socket client ) throws IOException {
    String line;

    BufferedReader reader = new BufferedReader(
      new InputStreamReader( client.getInputStream() )
    );

    
  }


}

