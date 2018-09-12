import java.util.HashMap;
import java.util.Stream;

public class Request {
  String uri;
  String body;
  String verb;
  public static final String httpVersion;
  private static HashMap<String, String> headers;

  public Request(String uri, String verb, String httpVersion){
    this.uri = uri;
    this.verb = verb;
    this.headers = new HashMap();
  }

  public String request(String test){

  }

  public static void request(Stream client) throws IOException {
    String line;

    BufferedReader reader = new BufferedReader(
      new InputStreamReader( client.getInputStream() )
    );

    while( true ) {
      line = reader.readLine();
      System.out.println( "> " + line );

      
      if(line.contains( "END" )){
        break;
      }
      if(line.contains( "GET" )){

      }
      if(line.contains( "HEAD" )){

      }
      if(line.contains( "POST" )){

      }
      if(line.contains( "PUSH" )){

      }
      if(line.contains( "DELETE" )){

      }
    }
    outputLineBreak();
  }

  public void parse(String methods){
    switch(methods){
      case "GET": 
      break;
      case "HEAD":
      break;
      case"POST":
      break;
      case "PUSH":
      break;
      case "DELETE":
      break;
      default:break;
    }

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
    
}