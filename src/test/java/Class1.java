import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.testng.annotations.*;


import static java.lang.Integer.parseInt;

import static org.testng.Assert.assertEquals;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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



    private static List<String> getStringsFromFile(File file) throws IOException {
        List<String> info = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            String str = null;
            while((str = br.readLine()) != null) {
                info.add(str);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return info;
    }


    private static Object[][] getDataFromTextFile(File file, String divider) throws IOException {
        List<String> text = getStringsFromFile(file);
        int rowsCount = text.size();
        int columnsCount = text.get(0).split(divider).length;
        Object[][] objects = new Object[rowsCount][columnsCount];
        for (int i = 0; i < text.size(); i++) {
            objects[i] = text.get(i).split(divider);
            }
        return objects;
    }

//    public static void main(String[] args) {
//        File file_txt = new File("file.txt");
//        try {
//            getDataFromTextFile(file_txt, " ");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.print("ds");
//    }

    @DataProvider(name = "fileData")
    public static Object[][] testData() throws IOException {
        File file_txt = new File("file.txt");
        return getDataFromTextFile(file_txt, " ");
    }

//    @DataProvider(name = "fileData")
//    public Object[][] testData() throws IOException {
//        int i = 0;
//        int j = 0;
//        int numLines = 0;
//        File file = new File("file.txt");
//        BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()));
//        while(br.readLine() != null) {
//            numLines++;
//        }
//        br.close();
//
//        BufferedReader br2 = new BufferedReader(new FileReader(file.getAbsoluteFile()));
//        Object[][] testData = new Object[numLines][3];
//        String str;
//        while ((str = br.readLine()) != null) {
//            String[] s = str.split(" "); //15,19,21,dsfsdfsd
//            s.length
//            for(int k = 0; k < s.length; k++)
//            {
//                testData[i][j] = parseInt(s[k]);
//                j++;
//            }
//            j = 0;
//            i++;
//
//        }
//        br2.close();

//        Object[][] testData = new Object[numLines][];
//        Scanner sc = new Scanner(file);
//            while (sc.hasNext())
//            {
//                if(!sc.hasNextByte('\n'))
//                {
//                    testData[i][j] = sc.next();
//                    j++;
//                }
//                else
//                {
//                    i++;
//                }
//            }


//        return testData;
//    }

    @Test(dataProvider = "fileData", groups = "fast")
    public void add_Test(String x, String y, String z) {
        int intX = Integer.parseInt(x);
        int intY = Integer.parseInt(y);
        int intZ = Integer.parseInt(z);

        m.add(intX, intY);

        assertEquals(intZ, m.getResult());
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
