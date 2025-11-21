package by.paulouskaya.task3.reader;

import java.util.List;

public interface RiverFerryFileReader {
	
	public static final String CAR_INFO_REGEX = "=";
	public List<String> readCarInformationFromFile(String filename);
}
