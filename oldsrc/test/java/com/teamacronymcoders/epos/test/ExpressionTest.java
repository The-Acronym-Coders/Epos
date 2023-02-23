/*
 * MIT License
 *
 * Copyright (c) 2019-2021 Team Acronym Coders
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.teamacronymcoders.epos.test;

import com.teamacronymcoders.epos.skill.Skill;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExpressionTest {

    @Test
    public void testDefaultExpressions() {
        Expression skill = new ExpressionBuilder(Skill.DEFAULT_SKILL_EXPRESSION).variable("x").build();
        double skill99Exp = 2_097_152d;
        Assertions.assertEquals(skill99Exp, skill.setVariable("x", 99).evaluate(),
                "The skill expression does not correctly evaluate at level 99.");
        long skill256Exp = 11_838_663_293_001l;
        Assertions.assertEquals(skill256Exp, (long) skill.setVariable("x", 256).evaluate(),
                "The skill expression does not correctly evaluate at level 256.");
    }

    @Test
    public void testNoVariableInputs() {
        Expression test = new ExpressionBuilder("0").variable("x").build();
        Assertions.assertEquals(0, test.evaluate());
        Assertions.assertEquals(0, test.setVariable("x", 14).evaluate());
    }
}
