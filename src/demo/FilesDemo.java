package demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilesDemo {
    
    public static void main(String[] args) {

        String filePath = "src/demo/demo.txt";
        ReadFile(filePath);
        String outputPath = "src/demo/out.txt";
        WriteFile(outputPath);

    }

    public static void ReadFile(String fname) {
        // Path object
        Path pth = Paths.get(fname);
        File fobj = pth.toFile();

        if (fobj.exists()) {
            
            System.out.println("File Exists");
        }   else {

            System.out.println("File Not Found");
        }
        // File object
        // Reader object
        
        try {
            FileReader fr = new FileReader(fobj);
            BufferedReader bdf = new BufferedReader(fr);
            String line;
            //String line = bdf.readLine();
            //System.out.println("First line --> " + line);

            //line = bdf.readLine();
            //System.out.println("Second line --> " + line);

            while (null != (line = bdf.readLine())) {
                System.out.println("--> " + line);
            }

            bdf.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found. Please check input file: " + e.getMessage());

        }
        catch(IOException e) {
            System.out.println("Unable to read line: " + e.getMessage());
        }
        
            
        


        // Consume content

    }

    public static void WriteFile(String fname) {
        try {
            FileWriter fw = new FileWriter(fname, false);
            //fw.write("apples\n");
            //fw.write("oranges\n");
            //fw.write("pear\n");
            
            BufferedWriter bfw = new BufferedWriter(fw);
            bfw.write("apples\n");
            bfw.write("oranges\n");
            bfw.write("pear\n");

            bfw.flush();
            bfw.close();
        }   catch (IOException e) {
            System.out.println("Unable to write line: " + e.getMessage());
        }

    }
}
