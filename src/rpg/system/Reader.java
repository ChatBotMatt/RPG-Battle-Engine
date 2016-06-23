package rpg.system;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
		
		lineCount = 0;
		linesRead = 0;
		currentLine = "";

		this.filename = filename;

		fileData = new ArrayList<ArrayList<String[]>>();

		try {
			fileReader = new FileReader(filename);
			bufferedReader = new BufferedReader(fileReader);
		} catch (FileNotFoundException e) {
			System.out.println("The file " + filename + " couldn't be found. ");
		}
	}
	
	public ArrayList<ArrayList<String[]>> readFile() {
		try {
			countLines();
			while (linesRead < lineCount) {
				ArrayList<String[]> sectionData = readSection();
				if (!(sectionData.isEmpty())) {
					fileData.add(sectionData);
				}
			}
		} catch (IOException e) {
			System.out.println("Error reading the file.");
		}
		try {
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileData;
	}
	
	private void countLines() throws IOException {
		FileReader fileReaderCount = new FileReader(filename);
		BufferedReader bufferedReaderCount = new BufferedReader(fileReaderCount);
		while ((currentLine = bufferedReaderCount.readLine()) != null) {
			lineCount++;
		}
		bufferedReaderCount.close();
	}

	private int getDelimiterIndex(String line, String delimiter) {
		int index = line.indexOf(delimiter);
		return index;
	}

	private ArrayList<String[]> readSection() throws IOException {
		ArrayList<String[]> sectionData = new ArrayList<String[]>();
		String[] dataArray;
		while (true) {
			dataArray = new String[2];
			currentLine = bufferedReader.readLine();
			if (((currentLine == null) || currentLine.isEmpty())) {
				linesRead++;
				return sectionData;
			} else if (currentLine.startsWith("//")) {
				linesRead++;
			} else if (currentLine.startsWith("Delimiter")) {
				delimiter = currentLine.substring(9); //Get every character following "Delimiter", and set this to the delimiter variable.
				linesRead++; //Get past the delimiter line.
			} else {
				if (delimiter == null) {
					System.out.println("No delimiter set, errors likely. Default delimiter ': ' being used.");
					delimiter = ": ";
				}
				linesRead++;
				int delimiterIndex = getDelimiterIndex(currentLine, delimiter);
				if (delimiterIndex == -1) {
					System.out.println("No delimiter in line " + linesRead + ". Aborting.");
					System.exit(1);
				}
				String heading = currentLine.substring(0, delimiterIndex);
				delimiterIndex += (delimiter.length() - 1); //If the delimiter index is more than one character long, we should get the substring following its last character. 
				String content = currentLine.substring(++delimiterIndex);

				dataArray[0] = heading;
				dataArray[1] = content;
				sectionData.add(dataArray);
			}
		}
	}
	
	public void printData(){
		for (ArrayList<String[]> section: fileData){
			for (String[] pair: section){
				pair.toString();
			}
			System.out.println();
		}
	}
	
	public void run() throws IOException {
		readFile();
	}

	}
