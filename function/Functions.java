/*
 * Copyright 2014 Frank Asseg
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
 *  Represents the built-in functions available for use in expressions.
 *  Copyright (C) 2022  DZ-FSDev
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
package com.dz_fs_dev.expr4j.function;

import java.math.BigDecimal;

/**
 * Represents the built-in functions available for use in expressions.
 * Added support for BigDecimal evaluation.
 * 
 * @author DZ-FSDev, Frank Asseg
 * @version 0.0.2
 */
public class Functions {
    private static final int INDEX_SIN = 0;
    private static final int INDEX_COS = 1;
    private static final int INDEX_TAN = 2;
    private static final int INDEX_CSC = 3;
    private static final int INDEX_SEC = 4;
    private static final int INDEX_COT = 5;
    private static final int INDEX_SINH = 6;
    private static final int INDEX_COSH = 7;
    private static final int INDEX_TANH = 8;
    private static final int INDEX_CSCH = 9;
    private static final int INDEX_SECH = 10;
    private static final int INDEX_COTH = 11;
    private static final int INDEX_ASIN = 12;
    private static final int INDEX_ACOS = 13;
    private static final int INDEX_ATAN = 14;
    private static final int INDEX_SQRT = 15;
    private static final int INDEX_CBRT = 16;
    private static final int INDEX_ABS = 17;
    private static final int INDEX_CEIL = 18;
    private static final int INDEX_FLOOR = 19;
    private static final int INDEX_POW = 20;
    private static final int INDEX_EXP = 21;
    private static final int INDEX_EXPM1 = 22;
    private static final int INDEX_LOG10 = 23;
    private static final int INDEX_LOG2 = 24;
    private static final int INDEX_LOG = 25;
    private static final int INDEX_LOG1P = 26;
    private static final int INDEX_LOGB = 27;
    private static final int INDEX_SGN = 28;
    private static final int INDEX_TO_RADIAN = 29;
    private static final int INDEX_TO_DEGREE = 30;

    private static final Function[] BUILT_IN_FUNCTIONS = new Function[31];

    static {
        BUILT_IN_FUNCTIONS[INDEX_SIN] = new Function("sin") {
            @Override
            public BigDecimal apply(BigDecimal... args) {
                return BigDecimal.valueOf(Math.sin(args[0].doubleValue()));
            }
        };
        BUILT_IN_FUNCTIONS[INDEX_COS] = new Function("cos") {
            @Override
            public BigDecimal apply(BigDecimal... args) {
                return BigDecimal.valueOf(Math.cos(args[0].doubleValue()));
            }
        };
        BUILT_IN_FUNCTIONS[INDEX_TAN] = new Function("tan") {
            @Override
            public BigDecimal apply(BigDecimal... args) {
                return BigDecimal.valueOf(Math.tan(args[0].doubleValue()));
            }
        };
        BUILT_IN_FUNCTIONS[INDEX_COT] = new Function("cot") {
            @Override
            public BigDecimal apply(BigDecimal... args) {
            	BigDecimal tan = BigDecimal.valueOf(Math.tan(args[0].doubleValue()));
                if (tan.equals(BigDecimal.ZERO)) {
                    throw new ArithmeticException("Division by zero in cotangent!");
                }
                return BigDecimal.ONE.divide(tan);
            }
        };
        BUILT_IN_FUNCTIONS[INDEX_LOG] = new Function("log") {
            @Override
            public BigDecimal apply(BigDecimal... args) {
                return BigDecimal.valueOf(Math.log(args[0].doubleValue()));
            }
        };
        BUILT_IN_FUNCTIONS[INDEX_LOG2] = new Function("log2") {
            @Override
            public BigDecimal apply(BigDecimal... args) {
                return BigDecimal.valueOf(Math.log(args[0].doubleValue()) / Math.log(2d));
            }
        };
        BUILT_IN_FUNCTIONS[INDEX_LOG10] = new Function("log10") {
            @Override
            public BigDecimal apply(BigDecimal... args) {
                return BigDecimal.valueOf(Math.log10(args[0].doubleValue()));
            }
        };
        BUILT_IN_FUNCTIONS[INDEX_LOG1P] = new Function("log1p") {
            @Override
            public BigDecimal apply(BigDecimal... args) {
                return BigDecimal.valueOf(Math.log1p(args[0].doubleValue()));
            }
        };
        BUILT_IN_FUNCTIONS[INDEX_ABS] = new Function("abs") {
            @Override
            public BigDecimal apply(BigDecimal... args) {
                return BigDecimal.valueOf(Math.abs(args[0].doubleValue()));
            }
        };
        BUILT_IN_FUNCTIONS[INDEX_ACOS] = new Function("acos") {
            @Override
            public BigDecimal apply(BigDecimal... args) {
                return BigDecimal.valueOf(Math.acos(args[0].doubleValue()));
            }
        };
        BUILT_IN_FUNCTIONS[INDEX_ASIN] = new Function("asin") {
            @Override
            public BigDecimal apply(BigDecimal... args) {
                return BigDecimal.valueOf(Math.asin(args[0].doubleValue()));
            }
        };
        BUILT_IN_FUNCTIONS[INDEX_ATAN] = new Function("atan") {
            @Override
            public BigDecimal apply(BigDecimal... args) {
                return BigDecimal.valueOf(Math.atan(args[0].doubleValue()));
            }
        };
        BUILT_IN_FUNCTIONS[INDEX_CBRT] = new Function("cbrt") {
            @Override
            public BigDecimal apply(BigDecimal... args) {
                return BigDecimal.valueOf(Math.cbrt(args[0].doubleValue()));
            }
        };
        BUILT_IN_FUNCTIONS[INDEX_FLOOR] = new Function("floor") {
            @Override
            public BigDecimal apply(BigDecimal... args) {
                return BigDecimal.valueOf(Math.floor(args[0].doubleValue()));
            }
        };
        BUILT_IN_FUNCTIONS[INDEX_SINH] = new Function("sinh") {
            @Override
            public BigDecimal apply(BigDecimal... args) {
                return BigDecimal.valueOf(Math.sinh(args[0].doubleValue()));
            }
        };
        BUILT_IN_FUNCTIONS[INDEX_SQRT] = new Function("sqrt") {
            @Override
            public BigDecimal apply(BigDecimal... args) {
                return BigDecimal.valueOf(Math.sqrt(args[0].doubleValue()));
            }
        };
        BUILT_IN_FUNCTIONS[INDEX_TANH] = new Function("tanh") {
            @Override
            public BigDecimal apply(BigDecimal... args) {
                return BigDecimal.valueOf(Math.tanh(args[0].doubleValue()));
            }
        };
        BUILT_IN_FUNCTIONS[INDEX_COSH] = new Function("cosh") {
            @Override
            public BigDecimal apply(BigDecimal... args) {
                return BigDecimal.valueOf(Math.cosh(args[0].doubleValue()));
            }
        };
        BUILT_IN_FUNCTIONS[INDEX_CEIL] = new Function("ceil") {
            @Override
            public BigDecimal apply(BigDecimal... args) {
                return BigDecimal.valueOf(Math.ceil(args[0].doubleValue()));
            }
        };
        BUILT_IN_FUNCTIONS[INDEX_POW] = new Function("pow", 2) {
            @Override
            public BigDecimal apply(BigDecimal... args) {
                return BigDecimal.valueOf(Math.pow(args[0].doubleValue(), args[1].doubleValue()));
            }
        };
        BUILT_IN_FUNCTIONS[INDEX_EXP] = new Function("exp", 1) {
            @Override
            public BigDecimal apply(BigDecimal... args) {
                return BigDecimal.valueOf(Math.exp(args[0].doubleValue()));
            }
        };
        BUILT_IN_FUNCTIONS[INDEX_EXPM1] = new Function("expm1", 1) {
            @Override
            public BigDecimal apply(BigDecimal... args) {
                return BigDecimal.valueOf(Math.expm1(args[0].doubleValue()));
            }
        };
        BUILT_IN_FUNCTIONS[INDEX_SGN] = new Function("signum", 1) {
            @Override
            public BigDecimal apply(BigDecimal... args) {
                if (args[0].doubleValue() > 0) {
                    return BigDecimal.ONE;
                } else if (args[0].doubleValue() < 0) {
                    return BigDecimal.ONE.negate();
                } else {
                    return BigDecimal.ZERO;
                }
            }
        };
        BUILT_IN_FUNCTIONS[INDEX_CSC] = new Function("csc") {
            @Override
            public BigDecimal apply(BigDecimal... args) {
                BigDecimal sin = BigDecimal.valueOf(Math.sin(args[0].doubleValue()));
                if (sin.doubleValue() == 0d) {
                    throw new ArithmeticException("Division by zero in cosecant!");
                }
                return BigDecimal.valueOf(1d / sin.doubleValue());
            }
        };
        BUILT_IN_FUNCTIONS[INDEX_SEC] = new Function("sec") {
            @Override
            public BigDecimal apply(BigDecimal... args) {
                BigDecimal cos = BigDecimal.valueOf(Math.cos(args[0].doubleValue()));
                if (cos.equals(BigDecimal.ZERO)) {
                    throw new ArithmeticException("Division by zero in secant!");
                }
                return BigDecimal.valueOf(1d / cos.doubleValue());
            }
        };
        BUILT_IN_FUNCTIONS[INDEX_CSCH] = new Function("csch") {
            @Override
            public BigDecimal apply(BigDecimal... args) {
                //this would throw an ArithmeticException later as sinh(0) = 0
                if (args[0].equals(BigDecimal.ZERO)) {
                    return BigDecimal.ZERO;
                }

                return BigDecimal.ONE.divide(BigDecimal.valueOf(Math.sinh(args[0].doubleValue())));
            }
        };
        BUILT_IN_FUNCTIONS[INDEX_SECH] = new Function("sech") {
            @Override
            public BigDecimal apply(BigDecimal... args) {
                return BigDecimal.valueOf(1d / Math.cosh(args[0].doubleValue()));
            }
        };
        BUILT_IN_FUNCTIONS[INDEX_COTH] = new Function("coth") {
            @Override
            public BigDecimal apply(BigDecimal... args) {
                return BigDecimal.valueOf(Math.cosh(args[0].doubleValue()) / Math.sinh(args[0].doubleValue()));
            }
        };
        BUILT_IN_FUNCTIONS[INDEX_LOGB] = new Function("logb", 2) {
            @Override
            public BigDecimal apply(BigDecimal... args) {
                return BigDecimal.valueOf(Math.log(args[1].doubleValue()) / Math.log(args[0].doubleValue()));
            }
        };
        BUILT_IN_FUNCTIONS[INDEX_TO_RADIAN] = new Function("toradian") {
            @Override
            public BigDecimal apply(BigDecimal... args) {
                return BigDecimal.valueOf(Math.toRadians(args[0].doubleValue()));
            }
        };
        BUILT_IN_FUNCTIONS[INDEX_TO_DEGREE] = new Function("todegree") {
            @Override
            public BigDecimal apply(BigDecimal... args) {
                return BigDecimal.valueOf(Math.toDegrees(args[0].doubleValue()));
            }
        };

    }

    /**
     * Get the builtin function for a given name
     *
     * @param name te name of the function
     * @return a Function instance
     */
    public static Function getBuiltinFunction(final String name) {

        switch (name) {
            case "sin":
                return BUILT_IN_FUNCTIONS[INDEX_SIN];
            case "cos":
                return BUILT_IN_FUNCTIONS[INDEX_COS];
            case "tan":
                return BUILT_IN_FUNCTIONS[INDEX_TAN];
            case "cot":
                return BUILT_IN_FUNCTIONS[INDEX_COT];
            case "asin":
                return BUILT_IN_FUNCTIONS[INDEX_ASIN];
            case "acos":
                return BUILT_IN_FUNCTIONS[INDEX_ACOS];
            case "atan":
                return BUILT_IN_FUNCTIONS[INDEX_ATAN];
            case "sinh":
                return BUILT_IN_FUNCTIONS[INDEX_SINH];
            case "cosh":
                return BUILT_IN_FUNCTIONS[INDEX_COSH];
            case "tanh":
                return BUILT_IN_FUNCTIONS[INDEX_TANH];
            case "abs":
                return BUILT_IN_FUNCTIONS[INDEX_ABS];
            case "log":
                return BUILT_IN_FUNCTIONS[INDEX_LOG];
            case "log10":
                return BUILT_IN_FUNCTIONS[INDEX_LOG10];
            case "log2":
                return BUILT_IN_FUNCTIONS[INDEX_LOG2];
            case "log1p":
                return BUILT_IN_FUNCTIONS[INDEX_LOG1P];
            case "ceil":
                return BUILT_IN_FUNCTIONS[INDEX_CEIL];
            case "floor":
                return BUILT_IN_FUNCTIONS[INDEX_FLOOR];
            case "sqrt":
                return BUILT_IN_FUNCTIONS[INDEX_SQRT];
            case "cbrt":
                return BUILT_IN_FUNCTIONS[INDEX_CBRT];
            case "pow":
                return BUILT_IN_FUNCTIONS[INDEX_POW];
            case "exp":
                return BUILT_IN_FUNCTIONS[INDEX_EXP];
            case "expm1":
                return BUILT_IN_FUNCTIONS[INDEX_EXPM1];
            case "signum":
                return BUILT_IN_FUNCTIONS[INDEX_SGN];
            case "csc":
                return BUILT_IN_FUNCTIONS[INDEX_CSC];
            case "sec":
                return BUILT_IN_FUNCTIONS[INDEX_SEC];
            case "csch":
                return BUILT_IN_FUNCTIONS[INDEX_CSCH];
            case "sech":
                return BUILT_IN_FUNCTIONS[INDEX_SECH];
            case "coth":
                return BUILT_IN_FUNCTIONS[INDEX_COTH];
            case "toradian":
                return BUILT_IN_FUNCTIONS[INDEX_TO_RADIAN];
            case "todegree":
                return BUILT_IN_FUNCTIONS[INDEX_TO_DEGREE];
            default:
                return null;
        }
    }
}
