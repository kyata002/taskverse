package com.taskverse.app.util

object ExpCalculator {

    // Công thức cơ bản: Level up khi exp >= level * 100
    fun calculateLevel(exp: Int): Int {
        var level = 1
        var requiredExp = 100
        var remainingExp = exp

        while (remainingExp >= requiredExp) {
            remainingExp -= requiredExp
            level++
            requiredExp = level * 100
        }
        return level
    }
}
