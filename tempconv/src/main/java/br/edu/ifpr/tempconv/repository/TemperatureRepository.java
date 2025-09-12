package br.edu.ifpr.tempconv.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import br.edu.ifpr.tempconv.model.Temperature;
import br.edu.ifpr.tempconv.model.types.TemperatureTypes;
import br.edu.ifpr.tempconv.utils.TemperatureUtils;

public enum TemperatureRepository {
	INSTANCE;
	// fonte de dados em memória
	private Map<Long, Temperature> temps = new HashMap<>();

	TemperatureRepository() {
		// preencher "temps" com dados para simulação???
	}

	public boolean insert(Temperature temp) {
		Long millis = TemperatureUtils.timestampToMillis(temp.getTimestamp());

		return temps.put(millis,temp) != null;
	}

	public boolean update(Temperature temp) {
		Optional<Temperature> existe = findTemperature(temp);

		if (existe.isPresent()) {
			Temperature t = existe.get();

			t.setTempi(temp.getTempi());
			t.setTypei(temp.getTypei());
			t.setTempo(temp.getTempo());
			t.setTypeo(temp.getTypeo());

			return true;
		}
		return false;
	}

	public boolean delete(Temperature temp) {
		Long millis = TemperatureUtils.timestampToMillis(temp.getTimestamp());

		return delete(millis);
	}

	public boolean delete(Long millis) {
		return temps.entrySet().removeIf(entry ->
		                              Long.compare(entry.getKey(),millis) == 0);
	}

	public int delete() {
		int total = temps.size();

		temps.clear();

		return total;
	}

	public List<Temperature> findAll() {
		return new ArrayList<>(temps.values());
	}

	public List<Temperature> findByTemperatureInput(TemperatureTypes tt) {
		if (tt == null) return Collections.emptyList();

		return temps.entrySet()
					   .stream()
					   .filter(entry -> entry.getValue().getTypei().equals(tt))
					   .map(Map.Entry::getValue)
					   .collect(Collectors.toList());
	}

	public List<Temperature> findByTemperatureOutput(TemperatureTypes tt) {
		if (tt == null) return Collections.emptyList();

		return temps.values()
					   .stream()
					   .filter(t -> t.getTypeo().equals(tt))
					   .collect(Collectors.toList());
	}

	private boolean existTemperature(Temperature temp) {
		return findTemperature(temp).isPresent();
	}

	private Optional<Temperature> findTemperature(Temperature temp) {
		Long millis = TemperatureUtils.timestampToMillis(temp.getTimestamp());

		return temps.entrySet()
					   .stream()
					   .filter(entry -> Long.compare(entry.getKey(),millis) == 0)
					   .map(Map.Entry::getValue)
					   .findAny();
	}
}