/* Original Licensing Copyright
 * Copyright 2015 Federico Vera
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*  Modifications Licensing Copyright
 * 
 *  Lightweight BigDecimal array-based stack.
 *  Copyright (C) 2021  DZ-FSDev
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.dz_fs_dev.objecthunter.exp4j;

import java.math.BigDecimal;
import java.util.EmptyStackException;

/**
 * Lightweight BigDecimal array-based stack.
 *
 * @author DZ-FSDev, Federico Vera (dktcoding [at] gmail)
 * @since 17.0.1
 * @version 0.0.2
 */
class ArrayStack {
    private BigDecimal[] data;
    private int idx;

    /**
     * Constructs a new ArrayStack with an initial capacity of 5.
     */
    ArrayStack() {
        this(5);
    }

    /**
     * Constructs a new ArrayStack with a specified initial capacity.
     * 
     * @param initialCapacity The specified initial capacity of the array stack.
     * @since 0.0.2
     */
    ArrayStack(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException(
                    "Stack's capacity must be positive");
        }

        data = new BigDecimal[initialCapacity];
        idx = -1;
    }

    /**
     * Pushes a specified BigDecimal value into the stack.
     * 
     * @param value The specified BigDecimal value to be pushed. 
     * @since 0.0.2
     */
    void push(BigDecimal value) {
        if (idx + 1 == data.length) {
        	BigDecimal[] temp = new BigDecimal[(int) (data.length * 1.2) + 1];
            System.arraycopy(data, 0, temp, 0, data.length);
            data = temp;
        }

        data[++idx] = value;
    }

    /**
     * Returns the top of the stack without popping.
     * 
     * @return The top of the stack without popping.
     * @since 0.0.2
     */
    BigDecimal peek() {
        if (idx == -1) {
            throw new EmptyStackException();
        }
        return data[idx];
    }

    /**
     * Removes and returns the top of the stack.
     * 
     * @return The top of the stack.
     * @since 0.0.2
     */
    BigDecimal pop() {
        if (idx == -1) {
            throw new EmptyStackException();
        }
        return data[idx--];
    }

    /**
     * Returns true if empty; false otherwise.
     * 
     * @return True if empty; false otherwise.
     */
    boolean isEmpty() {
        return idx == -1;
    }

    /**
     * Returns the current size of the array stack.
     * 
     * @return The current size of the array stack.
     */
    int size() {
        return idx + 1;
    }
}
