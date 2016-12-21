/*
 * Copyright (c) 2016, CESAR.
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD license. See the LICENSE file for details.
 *
 */
package br.org.cesar.knot.lib;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public final class ThingList<T> implements ParameterizedType {

    private Class<T> mClass;

    public ThingList(Class<T> aClass) {
        if (aClass == null) {
            throw new IllegalArgumentException("The class could not be null");
        }
        mClass = aClass;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return new Type[]{mClass};
    }

    @Override
    public Type getOwnerType() {
        return null;
    }

    @Override
    public Type getRawType() {
        return List.class;
    }
}
