import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

public class MimeTypes{

 HashMap<String,String> types = new HashMap<>();

 public MimeTypes(String fileName)throws IOException{
  String entries[];

  ConfigurationReader cf = new ConfigurationReader(fileName);

  while(cf.hasMoreLines() == true){
   entries = cf.getEntries();
   addType(entries);
  }

 }

 public void addType(String entries[]){

  if(entries.length>2 && isComment(entries) == false){
  	for(int i=1; i<entries.length;i++){
  		types.put(entries[i],entries[0]);
  	}
  }
 }

 public void printTypes(){
  System.out.println(types);
 }

 public Boolean isComment(String entries[]){
  if(Character.valueOf(entries[0].charAt(0)) == '#'){
  	return true;
  }else{
  	return false;
  }
 }

 public String lookUp(String extension){
 	String type = types.get(extension);
  if(type == null){
    type = "text/text";
  }
  return type;
 }

}
