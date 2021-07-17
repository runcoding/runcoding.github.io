package com.runcoding.learn.algorithm;

/**
 * @Author: runcoding
 * @Email: runcoding@163.com
 * @Created Time: 2017/9/5 16:17
 * @Description https://itimetraveler.github.io/2017/01/31/%E3%80%90%E7%AE%97%E6%B3%95%E3%80%91%E5%8D%95%E9%93%BE%E8%A1%A8%E5%8F%8D%E8%BD%AC%EF%BC%8C%E4%B8%A4%E4%B8%A4%E5%8F%8D%E8%BD%AC/
 **/
public class TestListNodeAlgorithm {

    /***
     * 题目：定义一个函数，输入一个链表的头结点，反转该链表并输出反转后链表的头结点。
     *  1 --> 2 --> 4 --> 3
     *  3 --> 4 --> 2 --> 1
    解题思路：
     使用三个指针指向：当前节点A，下个节点B，以及下下个节点C。
     遍历时，如下首先记录下下个节点C，然后节点B的指针断开并指向A。然后移动进入下一组。
     A -> B -> C ->D -> E
     A <- B C ->D -> E
     整个过程只需遍历链表一次，效率提高不少，且需要的外部空间也较第一种方法要少很多，实现代码如下
     */
    public static ListNode reverseList(ListNode head){
        if(head == null)
            return null;
        /**当前节点A*/
        ListNode a = head;
        /**下个节点B*/
        ListNode b = head.next;
        /**下下个节点C*/
        ListNode temp;
        /**头结点的指针先清空*/
        head.next = null;
        /**有可能链表只有一个节点，所以需要看b是否为null*/
        while(b != null){
            /** 记录C节点*/
            temp = b.next;
            /** a->b 反向*/
            b.next = a;
            if(temp == null){
                break;
            }
            /**移动到下一个节点*/
            a = b;
            b = temp;
        }
        return b == null ? a : b;
    }

    /**
     问题二、单链表两两反转
     一个链表：a->b->c->d->e
     每两个元素进行反转：b->a->d->c->e
     输入：链表头指针
     输出：反转后的链表头指针
     要求：不新建节点
     *
      已知一个链表：a->b->c->d->e
      每两个元素进行反转：b->a->d->c->e
     分析：
        我们需要两个“指针”指着当前要反转的两个值current和next。 两两反转后，我们还需要记录下一个的值，即反转A和B后， 需要记录 C 值，
     我们才能够不断向下走，直到到达链表末端。所以，需要另一个指向下一个值的“指针”，即nextNext。反转以后，A的下一个是C， 但是，实际上，
     A的下一个应该是D，所以，每次反转时，我们需要更新前一个值的下一个值，也就是说把 A -> C 改成 A -> D，所以需要prev指针。
     所以，要完成这个操作，我们总共需要4个“指针”。

     所以最终我们一共需要分析四个指针： prev，current，next， nextNext。
     */
    public static ListNode reversePairedList(ListNode head){
        if(head == null) return null;
        ListNode a = head;          //当前节点A
        ListNode b = head.next;     //下个节点B
        ListNode temp;              //下下个节点C
        ListNode previous = null;   //上一组的尾指针，在下一组反转后需要改变
        ListNode newHead = b == null ? a : b;
        while(b != null){
            temp   = b.next;  // 记录C节点
            b.next = a;     // a->b 反向
            a.next = temp;
            if(previous != null){
                previous.next = b;
            }
            if(temp == null){
                break;
            }
            previous = a;
            a = temp;       //移动到下一组
            b = temp.next;
        }
        return newHead;
    }

    public static void main(String[] args) {
        ListNode a1 = new ListNode(5);
        ListNode a2 = new ListNode(4);
        ListNode a3 = new ListNode(30);
        ListNode a4 = new ListNode(78);
        ListNode a5 = new ListNode(99);
        a1.next = a2;
        a2.next = a3;
        a3.next = a4;
        a4.next = a5;
        //反转单链表
        ListNode //node = reverseList(a1);
                 node = reversePairedList(a1);
        //打印输出结果
        while (node != null){
            System.out.print(node.val);
            node = node.next;
            System.out.print(node != null ? "->" : "");
            System.gc();
        }
    }

}
/***
 * @link https://itimetraveler.github.io/2017/01/31/%E3%80%90%E7%AE%97%E6%B3%95%E3%80%91%E5%8D%95%E9%93%BE%E8%A1%A8%E5%8F%8D%E8%BD%AC%EF%BC%8C%E4%B8%A4%E4%B8%A4%E5%8F%8D%E8%BD%AC/
 */

class ListNode {

    int val;

    com.runcoding.learn.algorithm.ListNode next;

    ListNode(int x) { val = x; }


}
