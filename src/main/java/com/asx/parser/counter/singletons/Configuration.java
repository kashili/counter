package com.asx.parser.counter.singletons;
import java.io.InputStream;
import java.util.Properties;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
/**
 * @author krishnaashili
 *
 */
@Slf4j
public class Configuration {
	private static Properties properties;
	
	private Configuration() {}
	
	public static void load()
	{
		try(InputStream fis = Configuration.class.getClassLoader().getResourceAsStream("configuration.properties")) {  
			properties = new Properties();
			properties.load(fis);  
		}
		catch(Exception e)
		{
			log.error("Could not load settings file. "+Throwables.getStackTraceAsString (e));
		}
	}
	
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

}
