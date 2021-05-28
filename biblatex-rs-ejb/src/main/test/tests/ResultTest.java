package tests;

import com.ols.ruslan.neo.Tester;
import org.junit.Before;
import org.junit.Test;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class ResultTest {
    private static Tester tester;

    @Before
    public void init() {
        try {
            tester = new Tester();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void testPart(Integer number) {
        String fileEnd = String.valueOf(number);
        byte[] record = tester.getSourceByteArray(String.format("rusmarc%s.xml", fileEnd));
        String transformedRecord = tester.transform(record);

        try {
            assertEquals(transformedRecord.replaceAll("\n", ""), String.join("", Files.readAllLines(Paths.get(String.format("src/main/resources/biblatex%s.txt", fileEnd)))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test1() {
        testPart(1);
    }

    @Test
    public void test2() {
        testPart(2);
    }

    @Test
    public void test3() {
        testPart(3);
    }

    @Test
    public void test5() {
        testPart(5);
    }

    @Test
    public void test7() {
        testPart(7);
    }

    //@Test
    //public void test8() {testPart(8);}

    @Test
    public void test10() {
        testPart(10);
    }

    @Test
    public void test11() {
        testPart(11);
    }
}
