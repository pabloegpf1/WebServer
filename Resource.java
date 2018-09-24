import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

public class Resource{
 private String uri;
 public HttpdConf httpdConf;
 private Boolean isScript = false;

 public Resource(String uri, HttpdConf httpdConf){
  this.uri = uri;
  this.httpdConf = httpdConf;
 }

 public String absolutePath(){
  String absolutePath;

  if(uriAliased()){
  	absolutePath = httpdConf.alias.get(uri);
  }else if(uriScriptAliased()){
  	absolutePath = httpdConf.scriptAlias.get(uri);
  }else{
  	absolutePath = resolvePath();
  }

  if( isFile(absolutePath) == false && fileExists(absolutePath) == true ){
  	absolutePath = appendDirIndex(absolutePath);
  }
  return absolutePath;
 }

 public String appendDirIndex(String path){
  String filePath = "";
  for(int i=0; i<httpdConf.directoryIndex.size();i++){
   filePath = path + "/" + httpdConf.directoryIndex.get(i).replace("\"", "");
   if(fileExists(filePath) == true){
   	break;
   }
  }
  return filePath;
 }

 public boolean isFile(String path){
   File file = new File(path);
   return file.isFile();
 }

 public boolean fileExists(String path){
  File file = new File(path);
  return file.exists();
 }

 public boolean isScript(){
  return isScript;
 }

 public boolean isProtected(){
  return true;
 }

 public String resolvePath(){
  return httpdConf.documentRoot.replace("\"", "") + uri;
 }

 public boolean uriAliased(){
  String alias = httpdConf.alias.get(uri);
  if(alias == null){
  	return false;
  }else{
  	return true;
  }
 }

 public boolean uriScriptAliased(){
  String alias = httpdConf.scriptAlias.get(uri);
  if(alias == null){
  	return false;
  }else{
  	return true;
  }
 }

}
