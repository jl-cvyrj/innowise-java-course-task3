package by.paulouskaya.task3.util;

public class CarGeneratorId {
	
	public static long nextId = 1;
	public static long generateId() {
		return nextId++;
	}
}
