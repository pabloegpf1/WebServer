import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ConfigurationReader{
 
 File file;
 Scanner sc;
 String fileName;

 public ConfigurationReader(String fileName) throws IOException{

  file = new File(fileName);
  sc = new Scanner(file);
  String nextLine;
 }

 public String[] getEntries(){

  String entries[];
  entries = splitLine(nextLine());
  entries = addSpaces(entries);
  return entries;
 }

 public String[] addSpaces(String[] entries){
  for(int i=0; i<entries.length; i++){
    entries[i] = entries[i].replaceAll("\\\\"," ");
  }

  return entries;
 }

 public String[] splitLine(String line){
  String[] parts = line.split("\\s+");
  return parts;
 }

 public Boolean hasMoreLines(){
  if(sc.hasNextLine()){
  	return true;
  }else{
  	return false;
  }
 }

 public String nextLine(){
  String line = sc.nextLine();
  return line;
 }

 public Boolean isComment(String line){

  Character firstLetter = Character.valueOf(line.charAt(0));

  if(firstLetter == '#' ){
  	return true;
  }else{
  	return false;
  }

 }

}
