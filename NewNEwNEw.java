import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


public class newRtfIdf{
    public static void main(String[] args) {
        String directoryPath = args[0];

        HashMap<String,HashMap<String,Integer>> docHashTable = new HashMap<String,HashMap<String,Integer>>();
        HashMap<String,HashMap<String,Double>> rtfHashTable = new HashMap<String,HashMap<String,Double>>();
        HashMap<String,Double> IDF = new HashMap<String,Double>();
        HashMap<String,Double> wordInDocs = new HashMap<String,Double>();
        HashMap<String,HashMap<String,Double>> rtfidfHashTable = new HashMap<String,HashMap<String,Double>>();

        read(directoryPath, docHashTable);
        calculateRTF(directoryPath, docHashTable, rtfHashTable);
        HashMap<String,Integer> lexicon = createLexicon(rtfHashTable, directoryPath);
        calculateIDF(directoryPath, docHashTable, lexicon, IDF, wordInDocs);
        rtfidfCalculation(rtfidfHashTable, IDF, rtfHashTable, lexicon, directoryPath);
    }

    public static void rtfidfCalculation(HashMap<String,HashMap<String,Double>> rtfidfHashTable, HashMap<String,Double> IDF, HashMap<String,HashMap<String,Double>> rtfHashTable, HashMap<String,Integer> lexicon, String directorypath ){
        File directory = new File(directorypath);
        File[] files = directory.listFiles();

        for(int i = 0; i < files.length; i++) {
            File file = files[i];
            rtfidfHashTable.put(file.getName(),new HashMap<String,Double>());
            HashMap<String,Double> rtfidfFordoc = rtfidfHashTable.get(file.getName());
            HashMap<String,Double> rtfFordoc = rtfHashTable.get(file.getName());
            for(String token : IDF.keySet()){
                double rtfidf = IDF.get(token) *  rtfFordoc.get(token);
                rtfidfFordoc.put(token,rtfidf);
            }

        }

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

    public static void calculateRTF(String directoryPath, HashMap<String,HashMap<String,Integer>> docHashTable, HashMap<String,HashMap<String,Double>> rtfHashTable){
        File folder = new File(directoryPath);
        File[] files = folder.listFiles();
        for(int i = 0; i < files.length; i++){
            File file = files[i];
            rtfHashTable.put(file.getName(), new HashMap<String,Double>());
            HashMap<String,Integer> map = docHashTable.get(file.getName());
            HashMap<String,Double> rtfMap = rtfHashTable.get(file.getName());
            double n = countTokens(map);
            for(String token : map.keySet()){
                double rtf = map.get(token)/n;
                rtfMap.put(token,rtf);
            }
        }
    }



    public static HashMap<String,Integer> createLexicon(HashMap<String,HashMap<String,Double>> rtfHashTable, String directoryPath){
        HashMap<String,Integer> lexiconWords = new HashMap<>();
        File folder = new File(directoryPath);
        File[] files = folder.listFiles();
        for(int i = 0; i < files.length; i++){
            File file = files[i];
            HashMap<String,Double> map = rtfHashTable.get(file.getName());
            for(String token : map.keySet()){
                double rtf = map.get(token);
                if (rtf > 0.25 || rtf < 0.75){
                    lexiconWords.put(token,lexiconWords.getOrDefault(token,0) + 1);
                }
            }
        }
        return lexiconWords;
    }


    public static double countTokens(HashMap<String,Integer> map){
        double count = 0.0;
        for(String token : map.keySet()){
            count = count + map.get(token);
        }
        return count;
    }

    public static void calculateIDF(String directoryPath, HashMap<String,HashMap<String,Integer>> docHashTable, HashMap<String,Integer> lexicon, HashMap<String,Double> IDF, HashMap<String,Double> wordInDocs){
        File folder = new File(directoryPath);
        File[] files = folder.listFiles();
        double d = (double)docHashTable.size();
        for(String token : lexicon.keySet()){
            double idf = Math.log(d/wordInDocs.get(token))/Math.log(2);
            IDF.put(token,idf);
        }
    }

    public static void docWword(HashMap<String,HashMap<String,Integer>> docHashTable, HashMap<String,Integer> lexicon, File directoryPath, HashMap<String,Double> wordInDocs){
        File folder = new File(directoryPath.getPath());
        File[] files = folder.listFiles();
        for(int i = 0; i < files.length; i++){
            File file = files[i];
            HashMap<String,Integer> map = docHashTable.get(file.getName());
            for(String token : lexicon.keySet()){
                if(map.containsKey(token)){
                    wordInDocs.put(token,wordInDocs.getOrDefault(token,0.0) + 1.0);
                }
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

