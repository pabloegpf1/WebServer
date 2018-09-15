import java.util.HashMap;
import java.util.Map; 
import java.util.stream.Stream;
import java.net.*;
import java.io.*;

public class Request {
  private String uri;
  private String body;
  private String verb;
  private String httpVersion;
  private HashMap<String, String> headers = new HashMap<>();
  Boolean endOfRequest = false;
  BufferedReader reader;

 public Request(InputStream stream) throws IOException { 
  reader = new BufferedReader( new InputStreamReader( stream ));
  parse();
 }

 public void parse() throws IOException{
  int arg = 0;
  String line = "";
  
  try{

   while( endOfRequest == false ) {
    line = getNextLine();
    String entries[];

    if(arg == 0){
     entries = line.split("\\s+");
     storeFirstLine(entries[0],entries[1],entries[2]);

    }else if(line.equals("") == true){
     storeBody();

    }else{
     entries = line.split(": ");
     storeHeaders(entries);
    }

   arg++;
  }

 }catch (Exception e){
  badRequest();
 }

}

  public String getNextLine() throws IOException{
    return reader.readLine();
  }

  public void storeBody() throws Exception{
    String line = getNextLine();
    if(line.equals("\n")){
      endOfRequest = true;
    }else{
      //readBody
      endOfRequest = true;
    }

  }

  public void storeHeaders(String[] entries) throws Exception{
    headers.put(entries[0],entries[1]);
  }

  public void badRequest(){
    System.out.println("Error 400");
  }

  public void storeFirstLine(String verb, String uri, String httpVersion){
   this.verb = verb;
   this.uri = uri;
   this.httpVersion = httpVersion;
  }

  public String getUriString(){
    return uri;
  }

  public String getVerb(){
    return verb;
  }

  public String getHttpVersion(){
    return httpVersion;
  }

  public void printRequest(){
   System.out.println( "verb " + verb ); 
   System.out.println( "uri " + uri ); 
   System.out.println( "httpVersion " + httpVersion );
   System.out.println(headers);
  }
    
}