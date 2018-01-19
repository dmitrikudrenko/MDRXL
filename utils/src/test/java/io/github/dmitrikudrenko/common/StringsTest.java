package io.github.dmitrikudrenko.common;

import io.github.dmitrikudrenko.utils.common.Strings;
import org.junit.Test;

import static junit.framework.Assert.*;

public class StringsTest {
    @Test
    public void common() {
        //not blank
        assertFalse(Strings.isNotBlank(null));
        assertFalse(Strings.isNotBlank(""));
        assertFalse(Strings.isNotBlank(" "));
        assertFalse(Strings.isNotBlank("  "));
        assertTrue(Strings.isNotBlank(" 1"));
        assertTrue(Strings.isNotBlank("1"));
        assertTrue(Strings.isNotBlank("a"));
    }
}
