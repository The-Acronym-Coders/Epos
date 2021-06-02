package com.teamacronymcoders.epos.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.teamacronymcoders.epos.skill.Skill;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class ExpressionTest {

    @Test
    public void testDefaultExpressions() {
        Expression skill = new ExpressionBuilder(Skill.DEFAULT_SKILL_EXPRESSION)
                .variable("x")
                .build();
        double skill99Exp = 2_097_152d;
        Assertions.assertEquals(skill99Exp, skill.setVariable("x", 99).evaluate(),
                "The skill expression does not correctly evaluate at level 99.");
        long skill256Exp = 11_838_663_293_001l;
        Assertions.assertEquals(skill256Exp, (long) skill.setVariable("x", 256).evaluate(),
                "The skill expression does not correctly evaluate at level 256.");
    }
}
