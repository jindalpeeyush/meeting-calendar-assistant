package utils;

import com.google.inject.AbstractModule;

public class JsonCustomMapperModule extends AbstractModule {

	@Override
	protected void configure() {
		System.out.println("configuring json mapper module");
		bind(JsonCustomMapper.class).asEagerSingleton();
	}
}
