import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.testng.annotations.*;

import static java.lang.Integer.parseInt;
import static org.testng.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by EgorZhuravlev on 7/7/2016.
 */

//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Class1 {

    private static Mathematics m = new Mathematics();

    private static void println(String string) {
        System.out.println(string);
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

    @BeforeTest(alwaysRun = true)
    public void beforeTests() {
        //code
    }

    @AfterTest(alwaysRun = true)
    public void afterTests() {
        //code
    }

    @BeforeClass(alwaysRun = true)
    public static void testsStarted() {
        m = new Mathematics();
        println("All tests have started");
    }

    @AfterClass(alwaysRun = true)
    public static void testsFinished() throws IOException {
        println("All tests have finished");
        m = null;
    }

    @BeforeMethod(alwaysRun = true)
    public void testStarted() {
        m.setResult(0);
        println("Before Test");
    }

    @AfterMethod(alwaysRun = true)
    public void testFinished() {

        println("After Test");
    }

    @DataProvider(name = "fileData")
    public Object[][] testData() throws IOException {
        int numLines = 0;
        int i = 0;
        int j = 0;
        File file = new File("file.txt");

        BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()));
        while ((br.readLine()) != null)
        {
            numLines++;
        }
        br.close();

        Object[][] testData = new Object[numLines][3];
        Scanner sc = new Scanner(file);
        while (sc.hasNextInt()) {
            testData[j][i] = sc.nextInt();
            i++;

            if ((i % 3) == 0) {
                j++;
                i = 0;
            }
        }
        return testData;
    }

    @Test(dataProvider = "fileData", groups = "fast")
    public void add_Test(int x, int y, int z) {

        m.add(x, y);

        assertEquals(z, m.getResult());
    }

    @Test(timeOut = 10, dataProvider = "DataForDeduct", dependsOnMethods = {"divideExceptionTest"}, groups = "fast")
    public void deduct_Test(int x, int y, int z) {

        m.deduct(x, y);

        assertEquals(z, m.getResult());
    }

    @Test(groups = "fast")
    @Parameters({"xmlParamsX", "xmlParamsY", "xmlParamsZ"})
    public void multiply_Test(int x, int y, int z) {

        m.multiply(x, y);

        assertEquals(z, m.getResult());
    }

    @Test(dataProvider = "DataForDiv", groups = {"smoke", "fast"})
    public void divide_Test(int x, int y, int z) {

        m.divide(x, y);

        assertEquals(z, m.getResult());
    }

    @Test(expectedExceptions = ArithmeticException.class, groups = "fast")
    public void divideExceptionTest() {

        m.divide((int) Math.random(), 0);
        println("Division by zero! ");

    }
}
