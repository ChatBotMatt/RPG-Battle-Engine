/*
 * Decompiled with CFR 0_114.
 */
package rpg.system;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

public class Reader {
    private ArrayList<ArrayList<String[]>> fileData;
    private String filename;
    private FileReader fileReader;
    private BufferedReader bufferedReader;
    private String currentLine;
    private int lineCount;
    private int linesRead;
    private String delimiter;

    public Reader(String filename) {
        this.filename = filename;
        this.lineCount = 0;
        this.linesRead = 0;
        this.currentLine = "";
        this.filename = filename;
        this.fileData = new ArrayList();
        try {
            this.fileReader = new FileReader(filename);
            this.bufferedReader = new BufferedReader(this.fileReader);
        }
        catch (FileNotFoundException e) {
            System.out.println("The file " + filename + " couldn't be found. ");
        }
    }

    public ArrayList<ArrayList<String[]>> readFile() {
        try {
            this.countLines();
            while (this.linesRead < this.lineCount) {
                ArrayList<String[]> sectionData = this.readSection();
                if (sectionData.isEmpty()) continue;
                this.fileData.add(sectionData);
            }
        }
        catch (IOException e) {
            System.out.println("Error reading the file.");
        }
        try {
            this.bufferedReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return this.fileData;
    }

    private void countLines() throws IOException {
        FileReader fileReaderCount = new FileReader(this.filename);
        BufferedReader bufferedReaderCount = new BufferedReader(fileReaderCount);
        while ((this.currentLine = bufferedReaderCount.readLine()) != null) {
            ++this.lineCount;
        }
        bufferedReaderCount.close();
    }

    private int getDelimiterIndex(String line, String delimiter) {
        int index = line.indexOf(delimiter);
        return index;
    }

    private ArrayList<String[]> readSection() throws IOException {
        ArrayList<String[]> sectionData = new ArrayList<String[]>();
        do {
            String[] dataArray = new String[2];
            this.currentLine = this.bufferedReader.readLine();
            if (this.currentLine == null || this.currentLine.isEmpty()) {
                ++this.linesRead;
                return sectionData;
            }
            if (this.currentLine.startsWith("//")) {
                ++this.linesRead;
                continue;
            }
            if (this.currentLine.startsWith("Delimiter")) {
                this.delimiter = this.currentLine.substring(9);
                ++this.linesRead;
                continue;
            }
            if (this.delimiter == null) {
                System.out.println("No delimiter set, errors likely. Default delimiter ': ' being used.");
                this.delimiter = ": ";
            }
            ++this.linesRead;
            int delimiterIndex = this.getDelimiterIndex(this.currentLine, this.delimiter);
            if (delimiterIndex == -1) {
                System.out.println("No delimiter in line " + this.linesRead + ". Aborting.");
                System.exit(1);
            }
            String heading = this.currentLine.substring(0, delimiterIndex);
            delimiterIndex += this.delimiter.length() - 1;
            String content = this.currentLine.substring(++delimiterIndex);
            dataArray[0] = heading;
            dataArray[1] = content;
            sectionData.add(dataArray);
        } while (true);
    }

    public void printData() {
        for (ArrayList<String[]> section : this.fileData) {
            for (String[] pair : section) {
                pair.toString();
            }
            System.out.println();
        }
    }

    public void run() throws IOException {
        this.readFile();
    }
}

