package br.edu.ifpr.tempconv.restful.providers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.ws.rs.ext.ContextResolver;

public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {
	private final ObjectMapper objectMapper;
	
	public ObjectMapperContextResolver() {
		objectMapper = new ObjectMapper();
		
		objectMapper.registerModule(new JavaTimeModule());
	}
	
	@Override
	public ObjectMapper getContext(Class<?> type) {
		return objectMapper;
	}
}
