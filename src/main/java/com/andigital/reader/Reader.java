package com.andigital.reader;

import java.io.IOException;

/**
 * Generic reader interface used for reading data, throws {@link IOException} if errors occur
 *
 * Created by Paul Pop on 22/12/2015.
 */
public interface Reader<T, V> {

    T read(V from) throws IOException;
}
