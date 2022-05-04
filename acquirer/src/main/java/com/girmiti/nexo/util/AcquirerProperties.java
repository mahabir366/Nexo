package com.girmiti.nexo.util;

import java.io.IOException;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class AcquirerProperties  extends PropertyPlaceholderConfigurer {


	  // Property key for propsExported
	  private static java.util.Properties propsExported = new java.util.Properties();

	  @Override
	  protected java.util.Properties mergeProperties() throws IOException {
	    propsExported = super.mergeProperties();
	    return propsExported;
	  }

	  public static void mergeProperties(java.util.Properties properties) {
	    propsExported = properties;
	  }

	  public static String getProperty(final String key) {
	    return propsExported.getProperty(key);
	  }
}
