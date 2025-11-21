package by.paulouskaya.task3.parser;

import java.util.List;

public interface CarParser {

	public static final String CAR_INFO_REGEX = "=";
	public int[] parseCarTypeInfo(List<String> lines);
}
