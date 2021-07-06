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

package com.teamacronymcoders.epos.api.skill;

import com.teamacronymcoders.epos.api.IDescribable;
import com.teamacronymcoders.epos.api.registry.IDynamic;

// TODO: Document
public interface ISkill extends IDynamic<ISkill, ISkill>, IDescribable {

    /**
     * Returns the {@link ISkill} 's Max Level as an integer.
     *
     * @return Returns the Max Level of the {@link ISkill}
     */
    int getMaxLevel();

    // TODO: Rewrite equation library to use BigInteger instead of double, exp4j is
    // currently under Apache-2.0 so need to mention license

    /**
     * The experience scaling expression returned as a parsable {@link String}.
     *
     * @return The Experience-Scaling Expression as a {@link String}.
     */
    String getExpression();
}
