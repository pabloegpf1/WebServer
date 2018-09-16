import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

public class Resource{
 String uri;
 HttpdConf httpdConf;
 File file;

 public Resource(String uri, HttpdConf httpdConf){
  
  this.uri = uri;
  this.httpdConf = httpdConf;

 }

 public String absolutePath(){
  String absolutePath;

  if(uriAliased()){
  	absolutePath = httpdConf.alias.get(uri).replace("\"", "");
  }else if(uriScriptAliased()){
  	absolutePath = httpdConf.scriptAlias.get(uri).replace("\"", "");
  }else{
  	absolutePath = resolvePath();
  }

  if(isDirectory(absolutePath)){
  	absolutePath = appendDirIndex(absolutePath);
  }

  return absolutePath;

 }

 public String appendDirIndex(String path){
  String filePath = "DOESNOTEXIST";
  for(int i=0; i<httpdConf.directoryIndex.size();i++){
   filePath = httpdConf.directoryIndex.get(i).replace("\"", "") + path;
   if(fileExists(filePath) == true){
   	break;
   }
  }
  return filePath;
 }

 public boolean isDirectory(String path){
  file = new File(path);
  return file.isDirectory();
 }

 public boolean fileExists(String path){
  file = new File(path);
  return file.exists();
 }

 public boolean isScript(){
  return true;
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
