package plc.homework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Contains JUnit tests for {@link Regex}. A framework of the test structure 
 * is provided, you will fill in the remaining pieces.
 *
 * To run tests, either click the run icon on the left margin, which can be used
 * to run all tests or only a specific test. You should make sure your tests are
 * run through IntelliJ (File > Settings > Build, Execution, Deployment > Build
 * Tools > Gradle > Run tests using <em>IntelliJ IDEA</em>). This ensures the
 * name and inputs for the tests are displayed correctly in the run window.
 */
public class RegexTests {

    /**
     * This is a parameterized test for the {@link Regex#EMAIL} regex. The
     * {@link ParameterizedTest} annotation defines this method as a
     * parameterized test, and {@link MethodSource} tells JUnit to look for the
     * static method {@link #testEmailRegex()}.
     *
     * For personal preference, I include a test name as the first parameter
     * which describes what that test should be testing - this is visible in
     * IntelliJ when running the tests (see above note if not working).
     */
    @ParameterizedTest
    @MethodSource
    public void testEmailRegex(String test, String input, boolean success) {
        test(input, Regex.EMAIL, success);
    }

    /**
     * This is the factory method providing test cases for the parameterized
     * test above - note that it is static, takes no arguments, and has the same
     * name as the test. The {@link Arguments} object contains the arguments for
     * each test to be passed to the function above.
     */
    public static Stream<Arguments> testEmailRegex() {
        return Stream.of(
                Arguments.of("Alphanumeric", "thelegend27@gmail.com", true),
                Arguments.of("UF Domain", "otherdomain@ufl.edu", true),
                Arguments.of("Double Domain", "grosstre000@myplace.wcs.edu", true),
                Arguments.of("Address Numbers", "looktheresnums@w0w.org", true),
                Arguments.of("Amazom", "wearerealamazon@amazom.com", true),
                Arguments.of("Blank", "", false),
                Arguments.of("Just Symbols", "@.", false),
                Arguments.of("Just Domain", "@.woo", false),
                Arguments.of("Blank Address", "@nothing.biz", false),
                Arguments.of("Single Address", "i@almostnothing.biz", false),
                Arguments.of("No Domain Address", "thereisnothingafterthis@", false),
                Arguments.of("Space Before", "broken email@aol.com", false),
                Arguments.of("Space After", "brokenover@comcast. net", false),
                Arguments.of("Long Domain", "longdomain@com.google", false),
                Arguments.of("Short Domain", "shdo@top.g", false),
                Arguments.of("Domain Numbers", "domnums@ufl.0w0", false),
                Arguments.of("Domain Symbols", "domainsymbols@yahoo.(+)", false),
                Arguments.of("Capital Domain", "BIGDOMAIN@ufl.AHH", false),
                Arguments.of("Missing Domain Dot", "missingdot@gmailcom", false),
                Arguments.of("Double At Symbol", "repeatrepeat@@repeat.rep", false),
                Arguments.of("Symbols", "symbols#$%@gmail.com", false)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testOddStringsRegex(String test, String input, boolean success) {
        test(input, Regex.ODD_STRINGS, success);
    }

    public static Stream<Arguments> testOddStringsRegex() {
        return Stream.of(
                // what have eleven letters and starts with gas?
                Arguments.of("11 Characters", "automobiles", true),
                Arguments.of("13 Characters", "i<3pancakes13", true),
                Arguments.of("15 Characters", "i<3pancakes--15", true),
                Arguments.of("17 Characters", "i<3pancakes----17", true),
                Arguments.of("19 Characters", "i<3pancakes------19", true),
                Arguments.of("Empty String", "", false),
                Arguments.of("5 Characters", "5five", false),
                Arguments.of("14 Characters", "i<3pancakes14!", false),
                Arguments.of("18 Characters", "i<3pancakes14!!!!!", false),
                Arguments.of("21 Characters", "this is a long string", false)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testCharacterListRegex(String test, String input, boolean success) {
        test(input, Regex.CHARACTER_LIST, success);
    }

    public static Stream<Arguments> testCharacterListRegex() {
        return Stream.of(
                Arguments.of("Empty Set", "[]", true),
                Arguments.of("Single Element", "['a']", true),
                Arguments.of("Number", "['0']", true),
                Arguments.of("Symbol", "['$']", true),
                Arguments.of("Multiple Elements", "['a','b','c']", true),
                Arguments.of("Escapes", "['\u000B', '\n']", true),
                Arguments.of("Double Element", "['aa']", false),
                Arguments.of("One Bracket", "['a','b','c'", false),
                Arguments.of("Missing Brackets", "'a','b','c'", false),
                Arguments.of("Missing Commas", "['a' 'b' 'c']", false),
                Arguments.of("Empty and Newline", "['', '', '\n']", false)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testDecimalRegex(String test, String input, boolean success) {
        test(input, Regex.DECIMAL, success);
    }

    public static Stream<Arguments> testDecimalRegex() {
        return Stream.of(
                Arguments.of("Single Digit Decimal", "1.0", true),
                Arguments.of("Multiple Digit Decimal", "10100.001", true),
                Arguments.of("Single Decimal Leading Zero", "0.5", true),
                Arguments.of("Negative Decimal", "-1.0", true),
                Arguments.of("Zero Zero", "0.0", true),
                Arguments.of("Single Integer", "1", false),
                Arguments.of("Decimal No Integers", "1.", false),
                Arguments.of("Leading Zero Multiple Digit Decimal", "010100.001", false),
                Arguments.of("Decimal Double Leading Zero", "00.5", false),
                Arguments.of("Decimal No Leading Zero", ".5", false)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testStringRegex(String test, String input, boolean success) {
        test(input, Regex.STRING, success);
    }

    public static Stream<Arguments> testStringRegex() {
        return Stream.of(
                Arguments.of("Empty", "\"\"", true),
                Arguments.of("Classic HW", "\"Hello, World!\"", true),
                Arguments.of("Numbers", "\"1234567890\"", true),
                Arguments.of("Symbols", "\"!@#$%^&*()\"", true),
                Arguments.of("Good Escape", "\"1\\t2\"", true),
                Arguments.of("All Escapes", "\"\\b\\n\\r\\t\\'\\u000B\"", true),
                Arguments.of("Extra Quote", "\"termi\"nated\"", true),
                Arguments.of("Missing First Quote", "unterminated\"", false),
                Arguments.of("Missing Second Quote", "\"unterminated", false),
                Arguments.of("No Quotes", "no quotes", false),
                Arguments.of("Single Quotes", "'single quotes'", false),
                Arguments.of("Invalid Escape", "\"invalid\\escape\"", false)
        );
    }

    /**
     * Asserts that the input matches the given pattern. This method doesn't do
     * much now, but you will see this concept in future assignments.
     */
    private static void test(String input, Pattern pattern, boolean success) {
        Assertions.assertEquals(success, pattern.matcher(input).matches());
    }

}
