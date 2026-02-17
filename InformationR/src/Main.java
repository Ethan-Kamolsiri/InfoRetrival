import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;


static class wordPMI implements Comparable<wordPMI> {
    String word;
    Double probability;

    wordPMI(String word, Double probability) {
        this.word = word;
        this.probability = probability;
    }
    @Override
    public int compareTo(wordPMI o) {
        return Double.compare(o.probability, probability);
    }
    @Override
    public String toString() {
        String[] s = word.split(" ");
        return "Word: " + " (" + s[0] + "," + s[1] + ") " + "\t" + "PMI:" + probability;
    }
}

static HashMap<String, Integer> wordCount = new HashMap<String, Integer>();
static HashMap<String, Integer> conCatWord = new HashMap<String, Integer>();
static HashMap<String,Integer> firstwordCount = new HashMap<String,Integer>();
static HashMap<String,Integer> secondwordCount = new HashMap<String,Integer>();


public static void main(String[] args) throws IOException {
    String directoryname = args[0];
    read(directoryname);

    Scanner kb = new Scanner(System.in);
    System.out.println("Insert word: ");
    String lookupWord = kb.nextLine().toLowerCase();

    PriorityQueue<wordPMI> pmi = PMILookup(lookupWord);

    while (!pmi.isEmpty()) {
        wordPMI output;
        output = (wordPMI) pmi.poll();
        System.out.println(output.toString());

    }
    kb.close();
}

public static void read(String directory) throws IOException {

    File folder = new File(directory);
    String[] files = folder.list();

    for (int i = 0; i < files.length; i++) {
        File file = new File(folder, files[i]);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            line = line.toLowerCase();
            String[] words = line.split("\\s+");
            for (int j = 0; j < words.length; j++) {
                wordCount.put(words[j], wordCount.getOrDefault(words[j], 0) + 1);
                if (j < words.length - 1) {
                    String combinedWord = words[j] + " " + words[j + 1];
                    conCatWord.put(combinedWord, conCatWord.getOrDefault(combinedWord, 0) + 1);

                    firstwordCount.put(words[j], firstwordCount.getOrDefault(words[j], 0) + 1);
                    secondwordCount.put(words[j+1], secondwordCount.getOrDefault(words[j+1], 0) + 1);
                }
            }
        }
        br.close();
    }
}

public static PriorityQueue<wordPMI> PMILookup(String lookupWord) {
    PriorityQueue<wordPMI> top5values = new PriorityQueue<wordPMI>();
    int n = WC(conCatWord);
    System.out.println("Total number of Concat words: " + n);
    for (String word : conCatWord.keySet()) {
        String[] wordsplit = word.split("\\s+");
        if (wordsplit[0].equals(lookupWord)) {
            double x1 = (double) conCatWord.get(word)/n;
            double x2 = (double) getRow(wordsplit[0])/n;
            double x3 = (double) getCol(wordsplit[1])/n;
            double pmiofword = Math.log((x1 / (x2 * x3)))/Math.log(2);
            wordPMI wordPMI = new wordPMI(word, pmiofword);
            addtoQ(top5values, wordPMI);

        }
    }

    return top5values;
}

public static int WC(HashMap<String, Integer> x) {
    int count = 0;
    for (String word : x.keySet()) {
        count += x.get(word);
    }
    return count;
}

public static int getRow(String y){
return firstwordCount.getOrDefault(y, 0);
}

public static int getCol(String y){
return secondwordCount.getOrDefault(y, 0);
}

public static void addtoQ(PriorityQueue<wordPMI> top5, wordPMI wordPMI) {
    top5.add(wordPMI);
    if (top5.size() > 5) {
        top5.poll();
    }
}
