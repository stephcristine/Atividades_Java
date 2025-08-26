package br.edu.ifpr.tempconv.utils;

public final class TemperatureConverter {
	public static Double celsius2Fahrenheit(Double celsius) {
		return celsius * 1.8 + 32.;
	}
	
	public static Double celsius2Kelvin(Double celsius) {
		return celsius + 273.;
	}
	
	public static Double fahrenheit2Celsius(Double fahrenheit) {
		return (fahrenheit - 32) / 1.8;
	}
	
	public static Double fahrenheit2Kelvin(Double fahrenheit) {
		return (fahrenheit - 32.) * (5. / 9.) + 273.;
	}
	
	public static Double kelvin2Celsius(Double kelvin) {
		return kelvin - 273.;
	}
	
	public static Double kelvin2Fahrenheit(Double kelvin) {
		return (kelvin - 273.) * 1.8 + 32.;
	}
}
