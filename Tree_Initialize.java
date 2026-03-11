import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


class Tree_Initialize{
    public static final int MAX_LINES = 25000;
    public static final int MAX_FILES = 25;
    public static HashMap<String, HashMap<String, Integer>> TF;  
    public static Path WetDir;
    public static Path ProjectDir;     
    public static void main(String [] args) throws Exception{

       String wetDirName = args[0];
       Path WetDir = Paths.get(wetDirName);
       Path ProjectDir = WetDir.getParent();
    }

    public static void readDirectory(String wetDir)throws Exception{
        File folder = new File(wetDir);
        File[] files = folder.listFiles();
        for(File f : files){
            Pass_1_WET(f.getName());
        }
    }

    public static String[] Pass_1_WET(String f)throws Exception{        
        int numFiles = 0; //25
        int numLines = 0; //500
        BufferedReader br = new BufferedReader(new FileReader(f));
        Path path = Paths.get(f);
        try {
            // This creates the folder and any missing parents
            Files.createDirectories(path);
            System.out.println("Directory created successfully!");
        } catch (IOException e) {
            System.err.println("Failed to create directory: " + e.getMessage());
        }
        String line;
        while((line = br.readLine()) != null){
            String[] tokens = line.split("\\s+");
            for(String token : tokens){
                boolean isEnglish = true;
                for(int i = 0; i < token.length(); i++){
                    if((int)token.charAt(i) < 33 || (int)token.charAt(i) > 126){
                        isEnglish = false;
                        break;
                    }
                }
            }

            
        }
    }

}


