import java.io.IOException;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class Response{

 public int code;
 public String reasonPhrase;
 public Resource resource;
 public HashMap<String, String> headers = new HashMap<>();
 public String body;
 public int size = 0;

 private static String HTTP_VERSION = "HTTP/1.1";
 private static String SERVER = "Apache/2.4.1 (Unix)";

 public Response(Resource resource){
  this.resource = resource;
 }

 public Response(int code, String reasonPhrase){
  this.code = code;
  this.reasonPhrase = reasonPhrase;
 }

public void send(OutputStream client){
 PrintWriter out = new PrintWriter( client, true );
 out.println(HTTP_VERSION+" " + code + " " +reasonPhrase);

 headers.put("Date",getServerTime());
 headers.put("Server",SERVER);

 Iterator iterator = headers.entrySet().iterator();
 while (iterator.hasNext()) {
  Map.Entry pair = (Map.Entry)iterator.next();
  out.println(pair.getKey() + ": " + pair.getValue());
  iterator.remove();
 }
 out.println("");

 if(body != null){
  out.println(body);
 }
}


public String getServerTime() {
  Calendar calendar = Calendar.getInstance();
  SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
  dateFormat.setTimeZone(TimeZone.getTimeZone("PDT"));
  return dateFormat.format(calendar.getTime());
 }

}
