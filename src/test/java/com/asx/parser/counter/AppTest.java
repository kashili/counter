package com.asx.parser.counter;
import java.net.URL;

import com.asx.parser.counter.App;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
       try {
    	App app = new App();
    	URL url = ClassLoader.getSystemResource(""); //todo: quick hack..will also load other files from the path
    	app.start(url.getPath());
    	app.printAll();
       }
       catch(Exception e) {
    	   e.printStackTrace( );
       }
    }
}
