package utils;

import javax.inject.Singleton;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Singleton
public class CustomObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = 1L;

	public CustomObjectMapper(boolean failOnUnknown) {
		configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, failOnUnknown);
		configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		SimpleModule polymorphicmodule =  new SimpleModule("PolymorphicDeserializerModule");
		this.registerModule(polymorphicmodule);
	}
	public CustomObjectMapper() {
		this(true);
	}
}
