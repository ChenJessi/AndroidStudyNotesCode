package com.chencc.androidstudynotescode.leetcode


/**
 *  链表基础操作
 */

fun main() {

    // linkedlist  单链表
//    linkedlist()


    // binarytree  二叉树
    binarytree()
}


/**==========================================================================================================================================*/

/**
 * 单链表
 */

fun linkedlist(){
    var head = ListNode1(1, null)
    var next : ListNode1? = null
    for (i in 0..10){
        if (next == null){
            head.next = ListNode1(head.a + 1, null)
            next = head.next
        } else{
            next.next = ListNode1(next.a + 1, null)
            next = next.next
        }
    }
    println("next : ${head}")

    traverse1(head)
    traverse2(head)
}



/**
 * 迭代访问
 */
fun traverse1(head: ListNode1){
    var p : ListNode1? = head
    while (p != null){
        println("traverse1  ${p.a}")
        p = p.next
    }

}

/**
 * 递归访问
 */
fun traverse2(head: ListNode1?){
    println("traverse2  ${head?.a}")
    if (head?.next != null){
        traverse2(head.next)
    }
}


/**
 * 单链表
 */
data class ListNode1(
    var a: Int,
    var next: ListNode1? = null
)


/**======== 二叉树 ==================================================================================================================*/
/**
 * 二叉树
 */


fun binarytree(){

    var head = TreeNode1(0, null, null)

    tree(head)
    println("binarytree  traverse : $head")

    traverse(head)
}

/**
 * 创建二叉树
 */
var length = 1
fun tree(head : TreeNode1){
    if (length == 4){
        return
    }
    length += 1
    head.left = TreeNode1(head.a + 1, null, null)
    head.right = TreeNode1(head.a + 1, null, null)
    tree(head.left!!)
    tree(head.right!!)
}



/**
 * 遍历二叉树
 */
fun traverse(head: TreeNode1){
    println("head  ${head.a}")
    head.left?.let { traverse(it) }
    head.right?.let { traverse(it) }
}


/**
 * 二叉树
 */
data class TreeNode1(
    var a : Int,
    var left : TreeNode1?,
    var right : TreeNode1?
)