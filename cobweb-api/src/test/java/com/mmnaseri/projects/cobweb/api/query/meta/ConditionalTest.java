package com.mmnaseri.projects.cobweb.api.query.meta;

import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/15/17, 3:16 PM)
 */
public class ConditionalTest {

    @Test
    public void testNegation() throws Exception {
        Conditional<?> conditional = value -> true;
        assertThat(conditional.test(null), is(true));
        assertThat(conditional.not().test(null), is(false));
    }

    @Test
    public void testConjunctionWhenSomeAreFalse() throws Exception {
        AtomicInteger calls = new AtomicInteger(0);
        Conditional<?> conditional = value -> {
            calls.incrementAndGet();
            return true;
        };
        for (int i = 0; i < 10; i++) {
            conditional = conditional.and(value -> {
                calls.incrementAndGet();
                return true;
            });
        }
        for (int i = 0; i < 10; i++) {
            conditional = conditional.and(value -> {
                calls.incrementAndGet();
                return false;
            });
        }
        assertThat(conditional.test(null), is(false));
        assertThat(calls.get(), is(12));
    }

    @Test
    public void testConjunctionWhenAllAreTrue() throws Exception {
        AtomicInteger calls = new AtomicInteger(0);
        Conditional<?> conditional = value -> {
            calls.incrementAndGet();
            return true;
        };
        for (int i = 0; i < 10; i++) {
            conditional = conditional.and(value -> {
                calls.incrementAndGet();
                return true;
            });
        }
        assertThat(conditional.test(null), is(true));
        assertThat(calls.get(), is(11));
    }

    @Test
    public void testDisjunctionWhenAllAreFalse() throws Exception {
        AtomicInteger calls = new AtomicInteger(0);
        Conditional<?> conditional = value -> {
            calls.incrementAndGet();
            return false;
        };
        for (int i = 0; i < 10; i++) {
            conditional = conditional.or(value -> {
                calls.incrementAndGet();
                return false;
            });
        }
        assertThat(conditional.test(null), is(false));
        assertThat(calls.get(), is(11));
    }

    @Test
    public void testDisjunctionWhenSomeAreTrue() throws Exception {
        AtomicInteger calls = new AtomicInteger(0);
        Conditional<?> conditional = value -> {
            calls.incrementAndGet();
            return false;
        };
        for (int i = 0; i < 10; i++) {
            conditional = conditional.or(value -> {
                calls.incrementAndGet();
                return false;
            });
        }
        for (int i = 0; i < 10; i++) {
            conditional = conditional.or(value -> {
                calls.incrementAndGet();
                return true;
            });
        }
        assertThat(conditional.test(null), is(true));
        assertThat(calls.get(), is(12));
    }

}