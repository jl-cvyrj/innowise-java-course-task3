package by.paulouskaya.task3.entity;

import java.util.LinkedList;
import java.util.Queue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RiverFerry {
	private static final Logger logger = LogManager.getLogger();
	private final Queue<Car> waitingCars = new LinkedList<>();
	private static int loadedCarsCount;

	private static RiverFerry instance = new RiverFerry();
	private final double maxWeight = 50.0;
	private final double maxArea = 100.0;
	private double currentWeight = 0;
	private double currentArea = 0;
	private boolean isOnTrip = false;

	public static RiverFerry getInstance() {
		return instance;
	}

	public void prosess(Car car) {
		RiverFerry ferry = RiverFerry.getInstance();
		waitingCars.add(car);
		car.setState(Car.CarState.WAITING);
		logger.info("Car {} added to queue. Queue size: {}", car.getCarId(), waitingCars.size());

		loadCar(car);
		waitingCars.remove(car);
		logger.info("Car {} loaded on ferry. Current weight: {}/{}, area: {}/{}", car.getCarId(), currentWeight, maxWeight,
				currentArea, maxArea);

		if (shouldStartTrip()) {
			startTrip();
		}
	}

	private void loadCar(Car car) {
		currentWeight += car.getCarWeight();
		currentArea += car.getCarArea();
		loadedCarsCount++;
	}

	private boolean shouldStartTrip() {
		return currentWeight >= maxWeight * 0.8 || currentArea >= maxArea * 0.8;
	}

	private void startTrip() {
		isOnTrip = true;
		logger.info("Ferry starting trip with {} cars. Weight: {}/{}, Area: {}/{}", getLoadedCarsCount(), currentWeight,
				maxWeight, currentArea, maxArea);

		unloadFerry();
		isOnTrip = false;
		logger.info("Ferry trip completed. Unloaded all cars.");
		loadedCarsCount = 0;
	}

	private Object getLoadedCarsCount() {
		return loadedCarsCount;
	}

	private void unloadFerry() {
		currentWeight = 0;
		currentArea = 0;
	}
}
