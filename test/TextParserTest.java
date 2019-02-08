import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@SuppressWarnings("javadoc")
public class TextParserTest {

	public enum CleanTestCases {
		HelloWorld("hello world", "hello world"),
		WithTabs("\t hello  world ", "\t hello  world "),
		WithPunctuation("hello, world!", "hello world"),
		WithDigit("hello 1 world", "hello  world"),
		WithSymbol("hello @world", "hello world"),
		WithUppercase("HELLO WORLD", "hello world"),
		WithExclamation("¡Hello world!", "hello world"),
		WithMarks("héḶlõ ẁörld", "hello world"),
		OnlySpaces("   ", "   "),
		OnlyDigits("1234567890", "");

		private final String text;
		private final String expected;

		private CleanTestCases(String text, String expected) {
			this.text = text;
			this.expected = expected;
		}

		public void test() {
			String actual = TextParser.clean(text);
			assertEquals(expected, actual);
		}
	}

	@ParameterizedTest
	@EnumSource(CleanTestCases.class)
	public void testClean(CleanTestCases testCase) {
		testCase.test();
	}

	public enum ParseTextTests {
		HelloWorld("hello world"),
		WithTabs("\t hello  world "),
		WithPunctuation("hello, world!"),
		WithDigit("hello 1 world"),
		WithSymbol("hello @world"),
		WithUppercase("HELLO WORLD"),
		WithExclamation("¡Hello world!"),
		WithMarks("héḶlõ ẁörld");

		private final String text;

		private ParseTextTests(String text) {
			this.text = text;
		}

		public void test() {
			String[] expected = new String[] {"hello", "world"};
			assertArrayEquals(expected, TextParser.parse(text));
		}
	}

	@ParameterizedTest
	@EnumSource(ParseTextTests.class)
	public void testParse(ParseTextTests testCase) {
		testCase.test();
	}

	public enum ParseTextEmptyTests {
		SingleSpace(" "),
		EmptyString(""),
		OnlyDigits("1234567890"),
		MixedSymbols("\t 11@ ");

		private final String text;

		private ParseTextEmptyTests(String text) {
			this.text = text;
		}

		public void test() {
			assertArrayEquals(new String[0], TextParser.parse(text));
		}
	}

	@ParameterizedTest
	@EnumSource(ParseTextEmptyTests.class)
	public void testParseEmpty(ParseTextEmptyTests testCase) {
		testCase.test();
	}
}
