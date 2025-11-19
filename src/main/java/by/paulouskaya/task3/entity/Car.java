package by.paulouskaya.task3.entity;

import by.paulouskaya.task3.util.CarGeneratorId;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Car extends Thread {
	
	private long carId;
	private static final CarGeneratorId generatorId = new CarGeneratorId();
  private static final Logger logger = LogManager.getLogger();
	private CarType carType;
	private CarState carState;
	private double carWeight;
	private double carArea;
	
	public enum CarType {
		PASSENGER(2.0, 8.0), 
    LORRY(15.0, 25.0); 
    
    private final double defaultWeight;
    private final double defaultArea;
    
    CarType(double weight, double area) {
        this.defaultWeight = weight;
        this.defaultArea = area;
    }
    
    public double getDefaultWeight() {
        return defaultWeight;
    }
    
    public double getDefaultArea() {
        return defaultArea;
    }
	}
	
	public enum CarState {
		NEW, PROCESSING, FINISHED
	}
	
	public Car(CarType type) {
		this.carId = generatorId.generateId();
		this.carType = type;
		this.carState = CarState.NEW;
		this.carWeight = type.defaultWeight;
		this.carArea = type.defaultArea;
		logger.info("Created car: ID={}, Type={}, Weight={}t, Area={}m²", carId, carType, carWeight, carArea);
	}

	public long getCarId() {
		return carId;
	}

	public CarType getCarType() {
		return carType;
	}

	public CarState getCarState() {
		return carState;
	}

	public void setState(CarState state) {
		this.carState = state;
	}
	
	@Override
	public void run() {
		//RiverFerry ferry = RiverFerry.getInstance();
		
	}
	
	public void prosess (Car car) {
		car.setState(CarState.PROCESSING);
		long carId = car.getCarId();
		logger.info("Ferry started processing car {}", carId);
		
		long timeSleeping = ThreadLocalRandom.current().nextLong(1000, 5001);
		try {
			TimeUnit.MILLISECONDS.sleep(timeSleeping);
		} catch (InterruptedException e) {
			logger.error("Caught an exception {}", e.getMessage());
			Thread.currentThread().interrupt();
		}
		
		car.setState(CarState.FINISHED);
		logger.info("Ferry finished processing car {}", carId);
	}
}
