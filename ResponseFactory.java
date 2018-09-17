import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;

public class ResponseFactory {
 Response response;
 Request request;

 public Response getResponse(Request request, Resource resource)  throws IOException, Exception{
  response = new Response(resource);
  this.request = request;

  if( (fileExists(resource.absolutePath())) == false && (request.getVerb().equals("PUT")) == false ){
   fileNotFound();
  }else if(resource.isScript() == true){
  	executeScript(resource);
  }else{
  	identifyVerb(request.getVerb(),resource);
  }

  return response;
 }

 public Boolean fileExists(String path){
  File file = new File(path);
  return file.exists();

 }

 public void identifyVerb(String verb, Resource resource)  throws IOException, Exception{

  switch ( verb ) {
   case "PUT": createFile(resource.absolutePath());
    break;
   case "DELETE": deleteFile(resource.absolutePath());
    break;
   case "POST":  sendFileContents(resource.absolutePath());
    break;
   case "GET":  checkLastModified(resource.absolutePath());
    break;
   case "HEAD":  //HEAD;
    break;
   default: badRequest();
  }

 }

 public void createFile(String path) throws IOException {
  File file = new File(path);
  file.createNewFile();
  response.headers.put("Content-Location", path);
  response.code = 201;
  response.reasonPhrase = "Created";
  response.send();
 }

 public void deleteFile(String path){
  File file = new File(path);
  file.delete();
  response.code = 204;
  response.reasonPhrase = "No Content";
  response.send();
 }

 public void sendFileContents(String path) throws IOException{
  File file = new File(path);
  Scanner sc = new Scanner(file);

  response.code = 200;
  response.reasonPhrase = "OK";
  while(sc.hasNextLine()){
   response.bodyList.add(sc.nextLine()); 
  }

  response.send();
 }

 public void checkLastModified(String path)throws Exception{
  File file = new File(path);
  Date timeModified = new Date(file.lastModified()); 
  Date heatherDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").parse(request.getHeaderContent("If-Modified-Since"));

  if(timeModified.after(heatherDate)){
  	sendFileContents(path);
  }else{
  	notModified(path);
  }
 }

 public void badRequest(){
  response.code = 400;
  response.reasonPhrase = "Bad Request";
  response.send();
 }

 public void fileNotFound(){
  response.code = 404;
  response.reasonPhrase = "File Not Found";
  response.send();
 }

 public void notModified(String path){
  response.headers.put("Content-Location", path);
  response.code = 304;
  response.reasonPhrase = "Not Modified";
  response.send();
 }

 public void executeScript(Resource resource){
  System.out.println("Execute script" + resource.absolutePath());
 }

}
