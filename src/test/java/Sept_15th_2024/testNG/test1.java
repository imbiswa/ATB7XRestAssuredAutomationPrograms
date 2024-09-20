package Sept_15th_2024.testNG;

import org.testng.annotations.*;

public class test1 {

    @BeforeSuite
    public void test1()
    {
        System.out.println("before suite executed");
    }


    @BeforeTest
    public void test2()
    {
        System.out.println("before Test executed");
    }

    @BeforeClass
    public void test3()
    {
        System.out.println("before class executed");
    }
    @BeforeMethod
    public void test4()
    {
        System.out.println("before method executed");
    }

    @BeforeGroups
    public void test5()
    {
        System.out.println("before group executed");
    }

    @Test
    public void TEST()
    {
        System.out.println("Test is executed");
    }

    @AfterTest
    public void test6()
    {
        System.out.println("After Test executed");
    }

    @AfterClass
    public void test7()
    {
        System.out.println("After class executed");
    }
    @AfterMethod
    public void test8()
    {
        System.out.println("After method executed");
    }

    @AfterGroups
    public void test9()
    {
        System.out.println("After group executed");
    }
}
