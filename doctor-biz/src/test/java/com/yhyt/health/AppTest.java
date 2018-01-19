package com.yhyt.health;

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

    /*
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
        /*assertTrue( true );*/
        if("1".equals((byte)1)){
            System.out.println("进来了");
        }
    }

    @org.junit.Test
    public void testString(){
        System.out.println("ss".equals(null));
    }
}
