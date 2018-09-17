import java.io.IOException;
import java.util.Scanner;

public class ResponseFactory {
 Response response;

 public Response getResponse(Request request, Resource resource){
  response = new Response(resource);
  return response;
 }

}