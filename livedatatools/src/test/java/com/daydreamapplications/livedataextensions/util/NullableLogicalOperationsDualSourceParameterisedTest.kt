package com.daydreamapplications.livedatatools.util

import com.daydreamapplications.livedatatools.util.NullableLogicalOperations.nullableAnd
import com.daydreamapplications.livedatatools.util.NullableLogicalOperations.nullableOr
import com.daydreamapplications.livedatatools.util.NullableLogicalOperations.nullableXAnd
import com.daydreamapplications.livedatatools.util.NullableLogicalOperations.nullableXOr
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class NullableLogicalOperationsDualSourceParameterisedTest(
    private val firstSource: Boolean?,
    private val secondSource: Boolean?,
    private val and: Boolean,
    private val or: Boolean,
    private val xand: Boolean,
    private val xor: Boolean
) {

    @Test
    internal fun `nullableAnd - dual source - returns correct value`() {
        assertThat(nullableAnd(firstSource, secondSource)).isEqualTo(and)
    }

    @Test
    internal fun `nullableOr - dual source - returns correct value`() {
        assertThat(nullableOr(firstSource, secondSource)).isEqualTo(or)
    }

    @Test
    internal fun `nullableXAnd - dual source - returns correct value`() {
        assertThat(nullableXAnd(firstSource, secondSource)).isEqualTo(xand)
    }

    @Test
    internal fun `nullableXOr - dual source - returns correct value`() {
        assertThat(nullableXOr(firstSource, secondSource)).isEqualTo(xor)
    }

    companion object {

        private fun argument(
            firstSource : Boolean?,
            secondSource : Boolean?,
            and: Boolean,
            or: Boolean,
            xand: Boolean,
            xor: Boolean
        ): Array<Any?> = arrayOf(firstSource, secondSource, and, or, xand, xor)

        @Parameterized.Parameters(name = "first: {0}, second {1}")
        @JvmStatic
        fun arguments(): Iterable<Array<Any?>> {
            return listOf(
                argument(firstSource = null, secondSource = null, and = false, or = false, xand = true, xor = false),
                argument(firstSource = null, secondSource = false, and = false, or = false, xand = true, xor = false),
                argument(firstSource = null, secondSource = true, and = false, or = true, xand = false, xor = true),
                argument(firstSource = false, secondSource = null, and = false, or = false, xand = true, xor = false),
                argument(firstSource = false, secondSource = false, and = false, or = false, xand = true, xor = false),
                argument(firstSource = false, secondSource = true, and = false, or = true, xand = false, xor = true),
                argument(firstSource = true, secondSource = null, and = false, or = true, xand = false, xor = true),
                argument(firstSource = true, secondSource = false, and = false, or = true, xand = false, xor = true),
                argument(firstSource = true, secondSource = true, and = true, or = true, xand = true, xor = false)
            )
        }
    }
}