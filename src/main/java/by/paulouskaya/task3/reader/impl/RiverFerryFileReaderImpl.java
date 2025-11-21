package by.paulouskaya.task3.reader.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import by.paulouskaya.task3.reader.RiverFerryFileReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RiverFerryFileReaderImpl implements RiverFerryFileReader{
	private static final Logger logger = LogManager.getLogger();

	@Override
	public List<String> readCarInformationFromFile(String filename) {
		List<String> lines = new ArrayList<>();
		try {
			lines = Files.readAllLines(Paths.get("my_file.txt"));
		} catch (IOException e) {
			logger.error("Error reading information from file {}", filename);
			e.printStackTrace();
		}
		return lines;
	}

}
