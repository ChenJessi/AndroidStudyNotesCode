package com.chencc.androidstudynotescode

import kotlin.math.sign

fun main() {

//    [
//        [1,   4,  7, 11, 15],
//        [2,   5,  8, 12, 19],
//        [3,   6,  9, 16, 22],
//        [10, 13, 14, 17, 24],
//        [18, 21, 23, 26, 30]
//    ]



}


fun findNumberIn2DArray(matrix: Array<IntArray>, target: Int): Boolean {

    if (matrix == null || matrix.isEmpty() || matrix[0].isEmpty()){
        return false
    }
    val rows = matrix.size
    var column  = matrix[0].lastIndex
    var row = 0
    while (row < rows && column >= 0){
        val num = matrix[row][column]
        when {
            num == target -> {
                return true
            }
            num > target -> {
                column--
            }
            num < target -> {
                row++
            }
        }
    }
    return false
}


//fun findRepeatNumber(nums: IntArray): Int {
//    val set  = HashSet<Int>()
//    val size  = nums.size
//    nums.forEach {
//        if (!set.add(it)){
//            return it
//        }
//    }
//    return -1
//}


//fun findRepeatNumber(nums: IntArray): Int {
//    val size  = nums.size
//   val temp = IntArray(size)
//    for (i in 0 until size){
//        temp[nums[i]]++
//        if (temp[nums[i]] > 1){
//            return nums[i]
//        }
//    }
//    return -1
//}