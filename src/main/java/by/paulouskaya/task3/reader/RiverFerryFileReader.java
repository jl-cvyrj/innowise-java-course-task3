package by.paulouskaya.task3.reader;

import java.util.List;

public interface RiverFerryFileReader {
	
	public static final String FILEPATH = "src/main/resources/input.txt";
	public List<String> readCarInformationFromFile(String filename);
}
