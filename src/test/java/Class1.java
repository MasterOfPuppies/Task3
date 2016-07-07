import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.runners.Suite;
import org.testng.annotations.*;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

/**
 * Created by EgorZhuravlev on 7/7/2016.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Class1 {

    private static Mathematics m = new Mathematics();

    private static void println(String string) {
        System.out.println(string);
    }


    @DataProvider(name = "DataForAdd")
    public static Object[][] paramDataForAdd() {
        return new Object[][]{
                {0, 4, 4},
                {3, 5, 8},
                {9, 6, 15}
        };
    }

    @DataProvider(name = "DataForDeduct")
    public static Object[][] paramDataForDeduct() {
        return new Object[][]{
                {6, 4, 2},
                {8, 5, 3},
                {9, 6, 3}
        };
    }

    @DataProvider(name = "DataForDiv")
    public static Object[][] paramDataForDiv() {
        return new Object[][]{
                {60, 4, 15},
                {15, 5, 3},
                {12, 6, 2}
        };
    }


    @BeforeTest
    public void beforeTests() {
        //code
    }

    @AfterTest
    public void afterTests() {
        //code
    }

    @BeforeClass
    public static void testsStarted() {
        m = new Mathematics();
        println("All tests have started");
    }

    @AfterClass
    public static void testsFinished() throws IOException {
        println("All tests have finished");
        m = null;
    }

    @BeforeMethod
    public void testStarted() {
        m.setResult(0);
        println("Before Test");
    }

    @AfterMethod
    public void testFinished() {

        println("After Test");
    }


    @Test(dataProvider = "DataForAdd")
    public void add_Test(int x, int y, int z) {

        m.add(x, y);

        assertEquals(z, m.getResult());
    }

    @Test(timeOut = 10, dataProvider = "DataForDeduct", dependsOnMethods = {"divideExceptionTest"})
    public void deduct_Test(int x, int y, int z) {

        m.deduct(x, y);

        assertEquals(z, m.getResult());
    }


    @Test
    @Parameters({"xmlParamsX", "xmlParamsY", "xmlParamsZ"})
    public void multiply_Test(int x, int y, int z) {

        m.multiply(x, y);

        assertEquals(z, m.getResult());
    }

    @Test(dataProvider = "DataForDiv")
    public void divide_Test(int x, int y, int z) {

        m.divide(x, y);

        assertEquals(z, m.getResult());
    }

    @Test(expectedExceptions = ArithmeticException.class)
    public void divideExceptionTest() {

        m.divide((int) Math.random(), 0);
        println("Division by zero! ");

    }
}
