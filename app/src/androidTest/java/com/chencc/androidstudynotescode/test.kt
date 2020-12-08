package com.chencc.androidstudynotescode

import java.util.*

fun main() {

//    [
//        [1,   4,  7, 11, 15],
//        [2,   5,  8, 12, 19],
//        [3,   6,  9, 16, 22],
//        [10, 13, 14, 17, 24],
//        [18, 21, 23, 26, 30]
//    ]


    val array = intArrayOf()



//    val c = ListNode(2)
//    val b = ListNode(3)
//    b.next = c
//    val a = ListNode(1)
//    a.next = b
//
//    println("reversePrint  :   ${reversePrint(a)?.toList()}")

    val a = intArrayOf(3,9,12,20,15,7)
    val b = intArrayOf(12,9,3,15,20,7)

    println("======>>>>>>   ${buildTree(a, b)}")

}


 class CQueue {
    //两个栈，一个出栈，一个入栈
    private val stack1: Stack<Int> = Stack()
    private val stack2: Stack<Int> = Stack()
    fun appendTail(value: Int) {
        stack1.push(value)
    }

    fun deleteHead(): Int {
        return if (!stack2.isEmpty()) {
            stack2.pop()
        } else {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop())
            }
            if (stack2.isEmpty()) -1 else stack2.pop()
        }
    }

}






/**
 * 重建二叉树
 */
class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

val map = hashMapOf<Int, Int>()  //标记中序遍历
var preo : IntArray? = null  // 保留的现需遍历
/**
 * 前序遍历 preorder = [3,9,20,15,7]
 * 中序遍历 inorder = [9,3,15,20,7]
 *
 *  利用原理,先序遍历的第一个节点就是根。在中序遍历中通过根 区分哪些是左子树的，哪些是右子树的
 */
fun buildTree(preorder: IntArray, inorder: IntArray): TreeNode? {
    // 解法1
    preo = preorder
    for (i in inorder.indices){
        map[inorder[i]] = i
    }

    return recursive(0, 0, inorder.size - 1)
    // 解法2
//    return build(preorder, inorder, Int.MAX_VALUE)
}

/**
 * @param pre_root_idx  先序遍历的索引
 * @param in_left_idx  中序遍历的索引
 * @param in_right_idx 中序遍历的索引
 */
fun recursive(pre_root_idx :Int, in_left_idx : Int, in_right_idx : Int) : TreeNode? {

    //相等就是自己
    if (in_left_idx > in_right_idx){
        return null
    }

    // root_idx 是在先序里面的
    println("TreeNode pre_root_idx :    $pre_root_idx")
    val treeNode = TreeNode(preo!![pre_root_idx])
    // 有了先序的,再根据先序的，在中序中获 当前根的索引
    val idx = map[preo!![pre_root_idx]] ?: 0

    // 左子树的根节点就是 左子树的前序遍历的第一个， 就是+1  左边边界就是left，右边边界是中间区分的idx-1
    treeNode.left = recursive(pre_root_idx + 1, in_left_idx, idx - 1);

    // 由根节点在中序遍历的index，区分成2段 ，index 就是跟

    // pre_root_idx 当前的跟节点， 左子树的长度 = 左子树的右边-左边  (idx-1 - in_left_idx +1)  最后+1就是左子树的跟了
    treeNode.right = recursive(pre_root_idx + (idx -1 - in_left_idx + 1 )  + 1, idx + 1, in_right_idx);

    return treeNode
}

private  var `in` = 0
private  var pre = 0

fun build(preorder: IntArray, inorder: IntArray, stop: Int) : TreeNode?{
    if (pre >= preorder.size){
        return null
    }
    if (inorder[`in`] == stop){
        `in`++
        return null
    }

    return TreeNode(preorder[pre++]).apply {
        left = build(preorder, inorder, `val`)
        right = build(preorder, inorder, stop)
    }

}












class ListNode(var `val`: Int) {
    var next: ListNode? = null
}



var size = 0


fun reversePrint(head: ListNode?) : IntArray?{
    //先获取链表长度，创建对应长度数组
    var currNode = head
    var len = 0
    while (currNode != null) {
        len++
        currNode = currNode.next
    }
    val result = IntArray(len)

    //再次遍历链表，将值倒序填充至结果数组
    currNode = head
    while (currNode != null) {
        result[len - 1] = currNode.`val`
        len--
        currNode = currNode.next
    }
    return result
}

/**
 * 链表翻转
 * 辅助栈
 */
var stack = Stack<ListNode>()
fun reversePrintStack(head: ListNode?): IntArray? {
    var h = head
    while (h != null){
        stack.push(h)
        h = h.next
    }
    array = IntArray(stack.size)
    for (i in 0 until stack.size){
        array!![i] = stack.pop().`val`
    }
    return array
}

/**
 * 链表翻转
 * 递归
 */
var array : IntArray? = null
fun reversePrintRecursion(head: ListNode?): IntArray? {
    if(head == null){
        array = IntArray(size)
        return array
    }else{
        val index = size
        size++
        reversePrintRecursion(head.next)
        array!![size - index - 1] = head.`val`
    }
    return array
}





fun replaceSpace(s : String) : String {
    val length = s.length
    val array = CharArray(length * 3)
    var size = 0

    for (i in 0 until length){
        val a = s[i]
        if (s[i] == ' '){
            array[size++] = '%'
            array[size++] = '2'
            array[size++] = '0'
        }else{
            array[size++] = a
        }
    }
    return String(array,0, size)
}




//fun replaceSpace(s: String): String {
//
////    return s.replace(" ","%20")
//
//    val builder = StringBuilder()
//    for(i in s.indices){
//        if (s[i] == ' '){
//            builder.append("%20")
//        }else{
//            builder.append(s[i])
//        }
//    }
//    return builder.toString()
//
//}

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