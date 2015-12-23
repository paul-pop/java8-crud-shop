package com.andigital.parser;

/**
 * Generic parser interface used for parsing data
 *
 * Created by Paul Pop on 15/08/15.
 */
public interface Parser<T, V> {

    T parse(V from);
}
