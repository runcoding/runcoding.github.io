package com.runcoding.learn.algorithm.leet;

public class TreeLinkNode {

    public  int val;

    public TreeLinkNode left ;

    public TreeLinkNode  right ;

    public  TreeLinkNode   next;

    public TreeLinkNode(int x) {
        val = x;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public TreeLinkNode getLeft() {
        return left;
    }

    public void setLeft(TreeLinkNode left) {
        this.left = left;
    }

    public TreeLinkNode getRight() {
        return right;
    }

    public void setRight(TreeLinkNode right) {
        this.right = right;
    }

    public TreeLinkNode getNext() {
        return next;
    }

    public void setNext(TreeLinkNode next) {
        this.next = next;
    }
}