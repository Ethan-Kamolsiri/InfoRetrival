import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


class Tree_Initialize{
    public static void main(String [] args){
        int numFiles = 0; //25
        int numLines = 0; //500
        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        String line;
        while((line = br.readLine()) != null){
            numLines++;
            while(numLines < 500){
                line = line.toLowerCase();
            }
        }
    }
}