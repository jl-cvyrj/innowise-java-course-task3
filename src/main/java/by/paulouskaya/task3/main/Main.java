package by.paulouskaya.task3.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.paulouskaya.task3.entity.Car;
import by.paulouskaya.task3.parser.impl.CarParserImpl;
import by.paulouskaya.task3.reader.RiverFerryFileReader;
import by.paulouskaya.task3.reader.impl.RiverFerryFileReaderImpl;

public class Main {
	private static final Logger logger = LogManager.getLogger();

	public static void main(String[] args) {

		RiverFerryFileReaderImpl reader = new RiverFerryFileReaderImpl();
		CarParserImpl parser = new CarParserImpl();
		List<String> carStringList = reader.readCarInformationFromFile(RiverFerryFileReader.FILEPATH);
		int[] numberOfTypes = parser.parseCarTypeInfo(carStringList);

		int passengerCount = numberOfTypes[0];
		int lorryCount = numberOfTypes[1];

		List<Car> cars = new ArrayList<>();
		for (int i = 0; i < passengerCount; i++) {
			cars.add(new Car(Car.CarType.PASSENGER));
		}
		for (int i = 0; i < lorryCount; i++) {
			cars.add(new Car(Car.CarType.LORRY));
		}

		Collections.shuffle(cars);
		for (Car car : cars) {
			car.run();
		}
		logger.info("RiverFerry finished word successfully");
	}
}
