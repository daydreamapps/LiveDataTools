package com.daydreamapplications.livedataextensions.util

import com.daydreamapplications.livedataextensions.util.NullableLogicalOperations.nullableAnd
import com.daydreamapplications.livedataextensions.util.NullableLogicalOperations.nullableOr
import com.daydreamapplications.livedataextensions.util.NullableLogicalOperations.nullableXAnd
import com.daydreamapplications.livedataextensions.util.NullableLogicalOperations.nullableXOr
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class NullableLogicalOperationsSingleSourceParameterisedTest(
    private val source: Boolean?,
    private val and: Boolean,
    private val or: Boolean,
    private val xand: Boolean,
    private val xor: Boolean
) {

    @Test
    internal fun `nullableAnd - single source - returns correct value`() {
        assertThat(nullableAnd(source)).isEqualTo(and)
    }

    @Test
    internal fun `nullableOr - single source - returns correct value`() {
        assertThat(nullableOr(source)).isEqualTo(or)
    }

    @Test
    internal fun `nullableXAnd - single source - returns correct value`() {
        assertThat(nullableXAnd(source)).isEqualTo(xand)
    }

    @Test
    internal fun `nullableXOr - single source - returns correct value`() {
        assertThat(nullableXOr(source)).isEqualTo(xor)
    }

    companion object {

        private fun argument(
            source: Boolean?,
            and: Boolean,
            or: Boolean,
            xand: Boolean,
            xor: Boolean
        ): Array<Any?> = arrayOf(source, and, or, xand, xor)

        @Parameterized.Parameters(name = "source: {0}")
        @JvmStatic
        fun arguments(): Iterable<Array<Any?>> {
            return listOf(
                argument(source = null, and = false, or = false, xand = true, xor = false),
                argument(source = false, and = false, or = false, xand = true, xor = false),
                argument(source = true, and = true, or = true, xand = true, xor = false)
            )
        }
    }
}