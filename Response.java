import java.io.IOException;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Calendar; 
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Response{

 public int code;
 public String reasonPhrase;
 public Resource resource;
 public HashMap<String, String> headers = new HashMap<>();
 private static String HTTP_VERSION = "HTTP/1.1";

public Response(Resource resource){
 this.resource = resource;
} 

public void send(){
 headers.put("Date",getServerTime());
 headers.put("Server","Apache/2.4.1 (Unix)");
 System.out.println(HTTP_VERSION+" " + code + " " +reasonPhrase);
 System.out.println(headers);
 }


 public String getServerTime() {
  Calendar calendar = Calendar.getInstance();
  SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
  dateFormat.setTimeZone(TimeZone.getTimeZone("PDT"));
  return dateFormat.format(calendar.getTime());
 }

}



