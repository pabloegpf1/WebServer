import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
  String line = "";
  while(line.equals("") || isComment(line) == true){
    line = nextLine();
  }
  entries = splitLine(line);
  return entries;
 }

 public String[] splitLine(String line){
  List<String> partsList = new ArrayList<String>();
  Pattern regex = Pattern.compile("[^\\s\"']+|\"[^\"]*\"|'[^']*'");
  Matcher matcher = regex.matcher(line);
  while(matcher.find()){
    partsList.add(matcher.group().replace("\"", ""));
  }
  String[] parts = new String[partsList.size()];
  for (int i =0; i < partsList.size(); i++){
    parts[i] = partsList.get(i);
  }
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
