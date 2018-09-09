import java.io.IOException;

public class Server{

 static HttpdConf httpdConf;
 static MimeTypes mimeTypes;

 public static void main( String[] args ) throws IOException{
  
  httpdConf = new HttpdConf("conf/httpd.conf");
  mimeTypes = new MimeTypes("conf/mime.types");

  //httpdConf.printConfig();
  //mimeTypes.printTypes();

  

 }


}

