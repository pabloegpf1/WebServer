import java.util.Scanner;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ResponseFactory {
 Response response;
 Request request;
 MimeTypes mimetypes;

 public Response getResponse(Request request, Resource resource, MimeTypes mimetypes)  throws IOException, ParseException,ServerException{
  response = new Response(resource);
  this.request = request;
  this.mimetypes = mimetypes;

  if(resource.isScript() == true){
   executeScript(resource);
  }else if( (fileExists(resource.absolutePath())) == false && (request.getVerb().equals("PUT")) == false ){
   fileNotFound();
  }else{
   identifyVerb(request.getVerb(),resource);
  }
  return response;
 }

 public Boolean fileExists(String path){
  File file = new File(path);
  return file.exists();
 }

 public void identifyVerb(String verb, Resource resource)  throws IOException, ParseException, ServerException{
  switch ( verb ) {
   case "PUT": createFile(resource.absolutePath());
    break;
   case "DELETE": deleteFile(resource.absolutePath());
    break;
   case "POST":  sendFileContents(resource.absolutePath(),true);
    break;
   case "GET":  checkLastModified(resource.absolutePath());
    break;
   case "HEAD":  sendFileContents(resource.absolutePath(),false);
    break;
   default: badRequest();
  }
 }

 public void createFile(String path) throws IOException {
  File file = new File(path);
  file.getParentFile().mkdirs();
  file.createNewFile();
  response.headers.put("Content-Location", path);
  response.code = 201;
  response.reasonPhrase = "Created";
 }

 public void deleteFile(String path){
  File file = new File(path);
  file.delete();
  response.code = 204;
  response.reasonPhrase = "No Content";
 }

 public void sendFileContents(String path, Boolean sendBody) throws IOException{
  String line;
  File file = new File(path);
  Scanner scanner = new Scanner(file);
  while(scanner.hasNext()){
   line = scanner.nextLine();
   response.body += (line + "\n");
  }
  byte bytes[] = response.body.getBytes("UTF-8");
  response.headers.put("Content-Length", Integer.toString(bytes.length));
  if( sendBody == false ){
   response.body = null;
  }
  response.size = bytes.length;
  getContentType(path);
  OkResponse();
 }

 public void checkLastModified(String path)throws IOException, ParseException{
  File file;
  String ifModifiedSince = request.getHeaderContent("If-Modified-Since");
  SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
  Date ifModifiedSinceDate;
  Date timeModified;
  try{
   file = new File(path);
  }catch(Exception e){
   fileNotFound();
   return;
  }
  timeModified = new Date(file.lastModified());
  try{
   ifModifiedSinceDate = formatter.parse(ifModifiedSince);
  }catch(Exception e){
   notModified(path);
   return;
  }
  if(timeModified.after(ifModifiedSinceDate)){
   sendFileContents(path,true);
  }else{
   notModified(path);
   response.headers.put("Last-Modified", formatter.format(timeModified));
  }
 }

 public void getContentType(String path){
  File file = new File(path);
  String name = file.getName();
  String extension = "";
  int i = name.lastIndexOf('.');
  if (i > 0) {
   extension = name.substring(i+1);
   System.out.println(extension);
  }
  response.headers.put("Content-Type", mimetypes.lookUp(extension));
 }

 public void OkResponse(){
  response.code = 200;
  response.reasonPhrase = "OK";
 }

 public void badRequest() throws ServerException{
  throw new ServerException();
 }

 public void fileNotFound(){
  response.code = 404;
  response.reasonPhrase = "File Not Found";
 }

 public void notModified(String path){
  response.headers.put("Content-Location", path);
  response.code = 304;
  response.reasonPhrase = "Not Modified";
 }

 public void executeScript(Resource resource) throws IOException {
  response.code = 200;
  response.reasonPhrase = "OK";
  ProcessBuilder build = new ProcessBuilder(resource.absolutePath());
  Process process = build.start();
  BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
  StringBuilder stringBuilder = new StringBuilder();
  String line = null;
  while ((line = stdInput.readLine()) != null){
   stringBuilder.append(line);
  }
  response.body = stringBuilder.toString();
  Map<String, String> env = build.environment();
  env.put("SERVER_PROTOCOL", "HTTP/1.1");
  env.put("QUERY_STRING", resource.absolutePath());
  response.isScript = true;
 }

}
