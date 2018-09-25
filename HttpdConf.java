import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.io.IOException;


public class HttpdConf{

 public String serverRoot;
 public int listen = 8080;
 public String documentRoot;
 public String logFile;
 HashMap<String,String> alias = new HashMap<>();
 HashMap<String,String> scriptAlias = new HashMap<>();
 public String AccessFileName = ".htaccess";
 public ArrayList<String> directoryIndex = new ArrayList<String>();

 public HttpdConf(String fileName)throws IOException{
  String entries[];

  ConfigurationReader cf = new ConfigurationReader(fileName);

  while(cf.hasMoreLines() == true){
   entries = cf.getEntries();
   addConfiguration(entries);
  }

 }

 public void addConfiguration(String entries[]){
  switch (entries[0]) {
   case "ServerRoot":  serverRoot = entries[1];
    break;
   case "Listen":  listen = Integer.parseInt(entries[1]);
    break;
   case "DocumentRoot":  documentRoot = entries[1];
    break;
   case "LogFile":  logFile = entries[1];
    break;
   case "AccessFileName":  AccessFileName = entries[1];
    break;
   case "Alias":  addAlias(entries);
    break;
   case "ScriptAlias": addScriptAlias(entries);
    break;
   case "DirectoryIndex": addDirectoryIndex(entries);
    break;
   default: break;
  }
 }

 public void addAlias(String entries[]){
  alias.put(entries[1],entries[2]);
 }

 public void addScriptAlias(String entries[]){
  scriptAlias.put(entries[1],entries[2]);
 }

 public void addDirectoryIndex(String entries[]){

  for(int i=1; i<entries.length;i++){
   directoryIndex.add(entries[i]);
  }

 }

 public void printConfig(){
  System.out.println(serverRoot);
  System.out.println(listen);
  System.out.println(documentRoot);
  System.out.println(logFile);
  System.out.println(AccessFileName);
  System.out.println(alias);
  System.out.println(scriptAlias);
   for (int i = 0; i < directoryIndex.size(); i++) {
     System.out.println(directoryIndex.get(i));
    }
 }

}
