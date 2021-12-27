/* Original Licensing Copyright
 * 
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
package com.dz_fs_dev.objecthunter.expr4j;

import com.dz_fs_dev.objecthunter.exp4j.function.Function;
import com.dz_fs_dev.objecthunter.exp4j.function.Functions;
import com.dz_fs_dev.objecthunter.exp4j.operator.Operator;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.dz_fs_dev.objecthunter.exp4j.tokenizer.FunctionToken;
import com.dz_fs_dev.objecthunter.exp4j.tokenizer.NumberToken;
import com.dz_fs_dev.objecthunter.exp4j.tokenizer.OperatorToken;
import com.dz_fs_dev.objecthunter.exp4j.tokenizer.Token;
import com.dz_fs_dev.objecthunter.exp4j.tokenizer.VariableToken;

/**
 * A container which holds a tokenized expression. Supports asynchronous 
 * evaluation of the expression.
 * 
 * @author DZ-FSDev, Frank Asseg
 * @since 17.0.1
 * @version 0.0.2
 */
public class Expression {

	private final Token[] tokens;

	private final Map<String, BigDecimal> variables;

	private final Set<String> userFunctionNames;

	/**
	 * Returns a map of default variables containing values for the following:
	 * <ul>
	 * 	<li>pi</li>
	 * 	<li>π</li>
	 * 	<li>φ</li>
	 * 	<li>e</li>
	 * </ul>
	 * 
	 * @return A map of default variables containing values.
	 * @since 0.0.2
	 */
	private static Map<String, BigDecimal> createDefaultVariables() {
		final Map<String, BigDecimal> vars = new HashMap<String, BigDecimal>(4);
		vars.put("pi", BigDecimal.valueOf(Math.PI));
		vars.put("π", BigDecimal.valueOf(Math.PI));
		vars.put("φ", BigDecimal.valueOf(1.61803398874d));
		vars.put("e", BigDecimal.valueOf(Math.E));
		return vars;
	}

	/**
	 * Creates a new expression that is a copy of the existing one.
	 *
	 * @param existing the expression to copy
	 */
	public Expression(final Expression existing) {
		this.tokens = Arrays.copyOf(existing.tokens, existing.tokens.length);
		this.variables = new HashMap<>();
		this.variables.putAll(existing.variables);
		this.userFunctionNames = new HashSet<>(existing.userFunctionNames);
	}

	/**
	 * 
	 * @param tokens
	 */
	Expression(final Token[] tokens) {
		this.tokens = tokens;
		this.variables = createDefaultVariables();
		this.userFunctionNames = Collections.emptySet();
	}

	/**
	 * 
	 * @param tokens
	 * @param userFunctionNames
	 */
	Expression(final Token[] tokens, Set<String> userFunctionNames) {
		this.tokens = tokens;
		this.variables = createDefaultVariables();
		this.userFunctionNames = userFunctionNames;
	}

	/**
	 * 
	 * @param name
	 * @param value
	 * @return
	 * @since 0.0.2
	 */
	public Expression setVariable(final String name, final BigDecimal value) {
		this.checkVariableName(name);
		this.variables.put(name, value);
		return this;
	}

	/**
	 * 
	 * @param name
	 */
	private void checkVariableName(String name) {
		if (this.userFunctionNames.contains(name) || Functions.getBuiltinFunction(name) != null) {
			throw new IllegalArgumentException("The variable name '" + name + "' is invalid. Since there exists a function with the same name");
		}
	}

	/**
	 * 
	 * @param variables
	 * @return
	 * @since 0.0.2
	 */
	public Expression setVariables(Map<String, BigDecimal> variables) {
		for (Map.Entry<String, BigDecimal> v : variables.entrySet()) {
			this.setVariable(v.getKey(), v.getValue());
		}
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public Expression clearVariables() {
		this.variables.clear();
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public Set<String> getVariableNames() {
		final Set<String> variables = new HashSet<>();
		for (final Token t : tokens) {
			if (t.getType() == Token.TOKEN_VARIABLE)
				variables.add(((VariableToken) t).getName());
		}
		return variables;
	}

	/**
	 * 
	 * @param checkVariablesSet
	 * @return
	 */
	public ValidationResult validate(boolean checkVariablesSet) {
		final List<String> errors = new ArrayList<>(0);
		if (checkVariablesSet) {
			/* check that all vars have a value set */
			for (final Token t : this.tokens) {
				if (t.getType() == Token.TOKEN_VARIABLE) {
					final String var = ((VariableToken) t).getName();
					if (!variables.containsKey(var)) {
						errors.add("The setVariable '" + var + "' has not been set");
					}
				}
			}
		}

		int count = 0;
		for (Token tok : this.tokens) {
			switch (tok.getType()) {
			case Token.TOKEN_NUMBER:
			case Token.TOKEN_VARIABLE:
				count++;
				break;
			case Token.TOKEN_FUNCTION:
				final Function func = ((FunctionToken) tok).getFunction();
				final int argsNum = func.getNumArguments();
				if (argsNum > count) {
					errors.add("Not enough arguments for '" + func.getName() + "'");
				}
				if (argsNum > 1) {
					count -= argsNum - 1;
				} else if (argsNum == 0) {
					// see https://github.com/fasseg/exp4j/issues/59
					count++;
				}
				break;
			case Token.TOKEN_OPERATOR:
				Operator op = ((OperatorToken) tok).getOperator();
				if (op.getNumOperands() == 2) {
					count--;
				}
				break;
			}
			if (count < 1) {
				errors.add("Too many operators");
				return new ValidationResult(false, errors);
			}
		}
		if (count > 1) {
			errors.add("Too many operands");
		}
		return errors.size() == 0 ? ValidationResult.SUCCESS : new ValidationResult(false, errors);

	}

	public ValidationResult validate() {
		return validate(true);
	}

	/**
	 * 
	 * @param executor
	 * @return
	 * @since 0.0.2
	 */
	public Future<BigDecimal> evaluateAsync(ExecutorService executor) {
		return executor.submit(this::evaluate);
	}

	/**
	 * 
	 * @return
	 * @since 0.0.2
	 */
	public BigDecimal evaluate() {
		final ArrayStack output = new ArrayStack();
		for (Token t : tokens) {
			if (t.getType() == Token.TOKEN_NUMBER) {
				output.push(((NumberToken) t).getValue());
			} else if (t.getType() == Token.TOKEN_VARIABLE) {
				final String name = ((VariableToken) t).getName();
				final BigDecimal value = this.variables.get(name);
				if (value == null) {
					throw new IllegalArgumentException("No value has been set for the setVariable '" + name + "'.");
				}
				output.push(value);
			} else if (t.getType() == Token.TOKEN_OPERATOR) {
				OperatorToken op = (OperatorToken) t;
				if (output.size() < op.getOperator().getNumOperands()) {
					throw new IllegalArgumentException("Invalid number of operands available for '" + op.getOperator().getSymbol() + "' operator");
				}
				if (op.getOperator().getNumOperands() == 2) {
					/* pop the operands and push the result of the operation */
					BigDecimal rightArg = output.pop();
					BigDecimal leftArg = output.pop();
					output.push(op.getOperator().apply(leftArg, rightArg));
				} else if (op.getOperator().getNumOperands() == 1) {
					/* pop the operand and push the result of the operation */
					BigDecimal arg = output.pop();
					output.push(op.getOperator().apply(arg));
				}
			} else if (t.getType() == Token.TOKEN_FUNCTION) {
				FunctionToken func = (FunctionToken) t;
				final int numArguments = func.getFunction().getNumArguments();
				if (output.size() < numArguments) {
					throw new IllegalArgumentException("Invalid number of arguments available for '" + func.getFunction().getName() + "' function");
				}
				/* collect the arguments from the stack */
				BigDecimal[] args = new BigDecimal[numArguments];
				for (int j = numArguments - 1; j >= 0; j--) {
					args[j] = output.pop();
				}
				output.push(func.getFunction().apply(args));
			}
		}
		if (output.size() > 1) {
			throw new IllegalArgumentException("Invalid number of items on the output queue. Might be caused by an invalid number of arguments for a function.");
		}
		return output.pop();
	}
}
