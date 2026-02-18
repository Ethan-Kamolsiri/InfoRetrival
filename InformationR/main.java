import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


public class main{
    public static void main(String[] args) {

        HashMap<String,HashMap<String,Integer>> docHashTable = new HashMap<String,HashMap<String,Integer>>();
        
    }


     public static void read(String directoryPath, HashMap<String,HashMap<String,Integer>> docHashTable){
        File folder = new File(directoryPath);
        File[] files = folder.listFiles();

        for(int i = 0; i < files.length; i++){
            File file = files[i];
            docHashTable.put(file.getName(), new HashMap<String,Integer>());
            try(BufferedReader br = new BufferedReader(new FileReader(file))){
                String line;
                HashMap<String,Integer> map = docHashTable.get(file.getName());

                while ((line = br.readLine()) != null) {
                    String[] tokens = line.split("\\s+");
                    for(int j = 0; j < tokens.length; j++){
                        map.put(tokens[i], map.getOrDefault(tokens, 0));
                        
                    }
                }


            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static double IDF(String w, String d){
        return 0;
    }

    public static double RTF(String w, String d){
        return 0;
    }

}

