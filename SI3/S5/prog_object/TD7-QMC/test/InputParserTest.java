import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputParserTest {
    String input = "ABCD + A~BCD + AB~D";
    InputParser parser;

    @BeforeEach
    void setup() {
        parser = new InputParser(input);
    }


    @Test
    void variableCount() {
        assertEquals(4, parser.variableCount());
    }
}
