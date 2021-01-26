package com.daydreamapplications.livedatatools

object Bools {

    /**
     * returns true if either boolean argument is true
     */
    fun nullableOr(first: Boolean?, second: Boolean?): Boolean {
        return first == true || second == true
    }

    /**
     * returns true if either boolean argument is true
     */
    fun nullableOr(first: Boolean?, second: Boolean?, third: Boolean?): Boolean {
        return first == true || second == true || third == true
    }

    /**
     * returns true if either boolean argument is true
     */
    fun nullableOr(first: Boolean?, second: Boolean?, third: Boolean?, fourth: Boolean?): Boolean {
        return first == true || second == true || third == true || fourth == true
    }

    /**
     * returns true if either boolean argument is true
     */
    fun nullableAnd(first: Boolean?, second: Boolean?): Boolean {
        return first == true && second == true
    }

    /**
     * returns true if either boolean argument is true
     */
    fun nullableAnd(first: Boolean?, second: Boolean?, third: Boolean?): Boolean {
        return first == true && second == true && third == true
    }

    /**
     * returns true if either boolean argument is true
     */
    fun nullableAnd(first: Boolean?, second: Boolean?, third: Boolean?, fourth: Boolean?): Boolean {
        return first == true && second == true && third == true && fourth == true
    }
}