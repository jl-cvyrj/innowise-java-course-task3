package by.paulouskaya.task3.entity;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RiverFerry {
	private static final Logger logger = LogManager.getLogger();
	
	private static RiverFerry instance;
	private final Queue<Car> waitingCars = new ArrayDeque<>();
	private final Queue<Car> onboardCars = new ArrayDeque<>();
	private static Lock dequeLock = new ReentrantLock();
	private static Lock lock = new ReentrantLock();
	private static AtomicInteger loadedCarsCount;

	private final double maxWeight = 50.0;
	private final double maxArea = 100.0;
	private DoubleAdder currentWeight;
	private DoubleAdder currentArea;
	
	private RiverFerry() {
		loadedCarsCount = new AtomicInteger();
		currentWeight = new DoubleAdder();
		currentArea = new DoubleAdder();
	}

	public static RiverFerry getInstance() {
		if (instance == null) {
			lock.lock();
			try {
				if (instance == null) {
					instance = new RiverFerry();
				}
			} finally {
				lock.unlock();
			}
		}
		return instance;
	}

	public void process(Car car) {
		addtoQueueCar(car);
		car.setState(Car.CarState.WAITING);
		logger.info("Car {} added to queue. Queue size: {}", car.getCarId(), waitingCars.size());
		
		if (shouldStartTrip(car)) {
			logger.info("Car {} can't be loaded on ferry. Current weight: {}/{}, area: {}/{}", car.getCarId(), currentWeight, maxWeight,
					currentArea, maxArea);
			startTrip();
		} else {
			loadCar(car);
			car.setState(Car.CarState.PROCESSING);
			logger.info("Car {} loaded on ferry. Current weight: {}/{}, area: {}/{}", car.getCarId(), currentWeight, maxWeight,
					currentArea, maxArea);
		}
	}
	
	private void addtoQueueCar(Car car) {
		dequeLock.lock();
		try {
			waitingCars.add(car);
		} finally {
			dequeLock.unlock();
		}
	}

	private void loadCar(Car car) {
		dequeLock.lock();
		try {
			waitingCars.remove(car);
			onboardCars.add(car);
			double currentCarWeight = car.getCarWeight();
			double currentCarArea = car.getCarArea();
			currentWeight.add(currentCarWeight);
			currentArea.add(currentCarArea);
			loadedCarsCount.incrementAndGet();
		} finally {
			dequeLock.unlock();
		}
	}

	private boolean shouldStartTrip(Car car) {
		return currentWeight.sum() + car.getCarWeight() > maxWeight || currentArea.sum() + car.getCarArea() > maxArea;
	}

	private void startTrip() {
		logger.info("Ferry starting trip with {} cars. Weight: {}/{}, Area: {}/{}", getLoadedCarsCount(), currentWeight,
				maxWeight, currentArea, maxArea);
		
		long timeSleeping = ThreadLocalRandom.current().nextLong(1000, 5001);
		try {
			TimeUnit.MILLISECONDS.sleep(timeSleeping);
		} catch (InterruptedException e) {
			logger.error("Caught an exception {}", e.getMessage());
			Thread.currentThread().interrupt();
		}
		
		unloadFerry();
		logger.info("Ferry trip completed. Unloaded all cars.");
		loadedCarsCount.set(0);
	}

	private int getLoadedCarsCount() {
		return loadedCarsCount.get();
	}

	private void unloadFerry() {
		dequeLock.lock();
    try {
    	while (!onboardCars.isEmpty()) {
            Car car = onboardCars.poll();
            logger.info("Car {} finished trip and removed from onboard", car.getCarId());
        }
    	loadedCarsCount.set(0);
			currentWeight.reset();
			currentArea.reset();
    } finally {
			dequeLock.unlock();
		}
	}
}
