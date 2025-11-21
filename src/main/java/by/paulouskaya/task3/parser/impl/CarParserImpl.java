package by.paulouskaya.task3.parser.impl;

import java.util.List;

import by.paulouskaya.task3.entity.Car;
import by.paulouskaya.task3.parser.CarParser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CarParserImpl implements CarParser {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public int[] parseCarTypeInfo(List<String> lines) {
		int passengerCount = 0;
		int lorryCount = 0;
		if (lines.size() <= 2) {
			for (String line : lines) {
				String[] typeInfo = line.split(CAR_INFO_REGEX);
				if (typeInfo.length == 2) {
					String typeOfCar = typeInfo[0].strip().toUpperCase();
					int count = Integer.parseInt(typeInfo[1].strip());
	
					if (typeOfCar.equals(Car.CarType.PASSENGER.toString())) {
						passengerCount = count;
					} else if (typeOfCar.equals(Car.CarType.LORRY.toString())) {
						lorryCount = count;
					} else {
						logger.warn("Unknown car type in file: {}", typeOfCar);
					}
				} else {
					logger.warn("Invalid line format: {}", line);
				}
			}
		} else {
			logger.warn("Some extra information in the file");
		}
		return new int[] { passengerCount, lorryCount };
	}

}
