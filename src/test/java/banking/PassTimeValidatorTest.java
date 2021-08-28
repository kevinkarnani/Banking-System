package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PassTimeValidatorTest {
    public PassTimeValidator commandValidator;

    @BeforeEach
    public void setUp() {
        this.commandValidator = new PassTimeValidator();
    }

    @Test
    public void pass_time_between_1_and_60_is_valid() {
        assertTrue(this.commandValidator.validate("Pass 34"));
    }

    @Test
    public void pass_time_1_is_valid() {
        assertTrue(this.commandValidator.validate("Pass 1"));
    }

    @Test
    public void pass_time_60_is_valid() {
        assertTrue(this.commandValidator.validate("Pass 60"));
    }

    @Test
    public void pass_time_is_case_insensitive() {
        assertTrue(this.commandValidator.validate("PaSs 15"));
    }

    @Test
    public void pass_time_over_60_is_invalid() {
        assertFalse(this.commandValidator.validate("Pass 61"));
    }

    @Test
    public void pass_time_decimal_is_invalid() {
        assertFalse(this.commandValidator.validate("Pass 1.5"));
    }

    @Test
    public void pass_time_below_1_is_invalid() {
        assertFalse(this.commandValidator.validate("Pass 0"));
    }

    @Test
    public void pass_time_pass_is_missing() {
        assertFalse(this.commandValidator.validate("61"));
    }

    @Test
    public void pass_time_time_is_missing() {
        assertFalse(this.commandValidator.validate("Pass"));
    }

    @Test
    public void pass_time_time_is_alphanumeric() {
        assertFalse(this.commandValidator.validate("pass a1"));
    }
}
