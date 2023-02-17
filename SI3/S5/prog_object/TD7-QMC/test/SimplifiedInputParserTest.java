import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimplifiedInputParserTest {
    static final String input = "0 1 2 3 4 5 6 6 10";
    private SimplifiedInputParser parser;

    @BeforeEach
    void setup() {
        parser = new SimplifiedInputParser(input);
    }

    @Test
    void test() {
        var expected = List.of(0, 1, 2, 3, 4, 5, 6, 10);
        assertEquals(expected, parser.values());
    }

    @Test
    void bitCount() {
        assertEquals(4, parser.bitCount());
    }
}
