package com.mmnaseri.projects.cobweb.api.query.dsl.impl;

import com.mmnaseri.projects.cobweb.api.query.dsl.ConditionalExpression;
import com.mmnaseri.projects.cobweb.api.query.dsl.StringConditionalExpression;
import com.mmnaseri.projects.cobweb.api.query.dsl.ValueReader;
import com.mmnaseri.projects.cobweb.domain.content.Persistent;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 3:10 PM)
 */
public class DefaultStringConditionalExpression<K extends Serializable & Comparable<K>, P extends Persistent<K>> extends AbstractConditionalExpression<K, P> implements StringConditionalExpression<K, P> {

    private final String subject;
    private final ValueReader<P, String> reader;

    public DefaultStringConditionalExpression(String subject, ValueReader<P, String> reader) {
        this.subject = subject;
        this.reader = reader;
    }

    private String name(String operator, String value) {
        return subject + " " + operator + " \"" + value + "\"";
    }

    @Override
    public ConditionalExpression<K, P> is(String value) {
        return setConditional(name("==", value), p -> reader.read(p).equals(value));
    }

    @Override
    public ConditionalExpression<K, P> isNot(String value) {
        return setConditional(name("!=", value), p -> !reader.read(p).equals(value));
    }

    @Override
    public ConditionalExpression<K, P> isIgnoreCase(String value) {
        return setConditional(name("~=", value), p -> reader.read(p).equalsIgnoreCase(value));
    }

    @Override
    public ConditionalExpression<K, P> isNotIgnoreCase(String value) {
        return setConditional(name("!~=", value), p -> !reader.read(p).equalsIgnoreCase(value));
    }

    @Override
    public ConditionalExpression<K, P> contains(String needle) {
        return setConditional(name("has", needle), p -> reader.read(p).contains(needle));
    }

    @Override
    public ConditionalExpression<K, P> doesNotContain(String needle) {
        return setConditional(name("!has", needle), p -> !reader.read(p).contains(needle));
    }

    @Override
    public ConditionalExpression<K, P> matches(String pattern) {
        return setConditional(name("~~", pattern), p -> reader.read(p).matches(pattern));
    }

    @Override
    public ConditionalExpression<K, P> doesNotMatch(String pattern) {
        return setConditional(name("!~~", pattern), p -> !reader.read(p).matches(pattern));
    }

    @Override
    public ConditionalExpression<K, P> startsWith(String prefix) {
        return setConditional(name("/=", prefix), p -> reader.read(p).startsWith(prefix));
    }

    @Override
    public ConditionalExpression<K, P> doesNotStartWith(String prefix) {
        return setConditional(name("!/=", prefix), p -> !reader.read(p).startsWith(prefix));
    }

    @Override
    public ConditionalExpression<K, P> endsWith(String suffix) {
        return setConditional(name("=\\", suffix), p -> reader.read(p).endsWith(suffix));
    }

    @Override
    public ConditionalExpression<K, P> doesNotEndWith(String suffix) {
        return setConditional(name("!=\\", suffix), p -> !reader.read(p).endsWith(suffix));
    }

}
