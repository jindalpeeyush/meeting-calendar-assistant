package utils;

import play.libs.Json;

public class JsonCustomMapper {
	public JsonCustomMapper() {
		CustomObjectMapper mapper = new CustomObjectMapper();
		Json.setObjectMapper(mapper);
		System.out.println("custom mapper is set now");
	}
}
