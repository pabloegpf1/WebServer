import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.List;
import java.net.*;
import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.io.BufferedWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Request {
 private String uri;
 private char[] body;
 private String verb;
 private String httpVersion;
 private HashMap<String, String> headers = new HashMap<>();
 private Boolean endOfRequest = false;
 private BufferedReader reader;

 public Request(InputStream stream) throws IOException {
  InputStreamReader inputStream = new InputStreamReader( stream );
  reader = new BufferedReader( inputStream );
  parse();
 }

 public void parse() throws IOException{
  int arg = 0;
  String line = "";
  while( endOfRequest == false ) {
   line = getNextLine();
   String entries[];
   if(arg == 0){
    entries = line.split(" ");
    storeFirstLine(entries[0],entries[1],entries[2]);
   }else if(line.equals("") == true){
    storeBody();
   }else{
    entries = line.split(": ",2);
    storeHeaders(entries);
   }
  arg++;
  }
 }

 public String getNextLine() throws IOException{
  return reader.readLine();
 }

 public void storeBody() throws IOException{
  String line = getNextLine();
  if(line.equals("")){
   endOfRequest = true;
  }else if(getHeaderContent("Content-Length") == null){
   endOfRequest = true;
 }else{
   int bytes = Integer.parseInt(getHeaderContent("Content-Length"));
   body = new char[bytes];
   reader.read(body);
   endOfRequest = true;
  }
 }

 public String getHeaderContent(String key) throws IOException{
  return headers.get(key);
 }

 public void storeHeaders(String[] entries) throws IOException{
  headers.put(entries[0],entries[1]);
 }

 public void storeFirstLine(String verb, String path, String httpVersion){
  this.verb = verb;
  this.uri = path.replaceAll("\\\\"," ");
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

 public void logRequest(String path, int code, int size,String ip){
  try{
    SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
    String currDate = sdf.format(new Date());
    BufferedWriter writer = new BufferedWriter( new FileWriter(path, true ));
    writer.write(ip);
    writer.write(" - ");
    writer.write("[" + currDate + "] ");
    writer.write("\"" + verb +" "+uri+" "+httpVersion + "\"");
    writer.write(" "+code);
    System.out.print(ip);
    System.out.print(" - ");
    System.out.print("[" + currDate + "] ");
    System.out.print("\"" + verb +" "+uri+" "+httpVersion + "\"");
    System.out.print(" "+code);
    if(code == 200){
     writer.write(" "+size);
     System.out.print(" "+size);
    }else{
     writer.write(" -");
     System.out.print(" -");
    }
    writer.newLine();
    writer.close();
   }catch(Exception e){
    return;
   }
 }

}
