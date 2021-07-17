package com.runcoding.learn.algorithm.leet.linkedlist;// Reverse a singly linked list.

import com.runcoding.learn.algorithm.leet.ListNode;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class ReverseLinkedList {

    public ListNode reverseList(ListNode head) {
        
        if(head == null) return head;
    
        ListNode newHead = null;
        
        while(head != null) {
            
            ListNode next = head.next;
            head.next = newHead;
            newHead = head;
            head = next;
            
        }
        
        return newHead;
        
    }

}