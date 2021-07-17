> 致谢： Maintainer - [Kevin Naughton Jr.](https://github.com/kdn251)
> 致谢： 美团技术团队 - [红黑树深入剖析及Java实现](https://tech.meituan.com/redblack-tree.html)

## 数据结构
### Linked List
 * 链表即是由节点（Node）组成的线性集合，每个节点可以利用指针指向其他节点。它是一种包含了多个节点的、能够用于表示序列的数据结构。
 * **单向链表**: 链表中的节点仅指向下一个节点，并且最后一个节点指向空。
 * **双向链表**: 其中每个节点具有两个指针 p、n，使得 p 指向先前节点并且 n 指向下一个节点；最后一个节点的 n 指针指向 null。
 * **循环链表**：每个节点指向下一个节点并且最后一个节点指向第一个节点的链表。
 * 时间复杂度:
   * 索引: `O(n)`
   * 搜索: `O(n)`
   * 插入: `O(1)`
   * 移除: `O(1)`

### Stack
 * 栈是元素的集合，其包含了两个基本操作：push 操作可以用于将元素压入栈，pop 操作可以将栈顶元素移除。
 * 遵循后入先出（LIFO）原则。
 * 时间复杂度:
  * 索引: `O(n)`
  * 搜索: `O(n)`
  * 插入: `O(1)`
  * 移除: `O(1)`

### Queue
 * 队列是元素的集合，其包含了两个基本操作：enqueue 操作可以用于将元素插入到队列中，而 dequeeu 操作则是将元素从队列中移除。
 * 遵循先入先出原则 (FIFO)。
 * 时间复杂度:
  * 索引: `O(n)`
  * 搜索: `O(n)`
  * 插入: `O(1)`
  * 移除: `O(1)`

### 树(Tree)
* 树是无向、连通的无环图。

### 二叉树(Binary Tree) 
 * 二叉树即是每个节点最多包含左子节点与右子节点这两个节点的树形数据结构。
 * **满二叉树**: 树中的每个节点仅包含 0 或 2 个节点。
 * **完美二叉树（Perfect Binary Tree）**: 二叉树中的每个叶节点都拥有两个子节点，并且具有相同的高度。
 * **完全二叉树**: 除最后一层外，每一层上的结点数均达到最大值；在最后一层上只缺少右边的若干结点。

### 二叉搜索树(BST)Binary Search Tree

* 二叉查找树（Binary Search Tree，简称BST）是一棵二叉树，它的左子节点的值比父节点的值要小，右节点的值要比父节点的值大。它的高度决定了它的查找效率。<br>
  在理想的情况下，二叉查找树增删查改的时间复杂度为O(logN)（其中N为节点数），最坏的情况下为O(N)。当它的高度为logN+1时，我们就说二叉查找树是平衡的。
- 若它的左子树不空,则左子树上所有结点的值均小于它的根结点的值；
- 若它的右子树不空,则右子树上所有结点的值均大于它的根结点的值；
- 它的左、右子树也分别为二叉搜索树。
* 时间复杂度:
  * 索引: `O(log(n))`
  * 搜索: `O(log(n))`
  * 插入: `O(log(n))`
  * 删除: `O(log(n))`

<img src="https://tech.meituan.com/img/redblack-tree/tree-all.png" alt="Binary Search Tree" >
<img src="https://runcoding.github.io/static/wiki/learn-java/java/algorithm/BST.png" alt="Binary Search Tree" width="400" height="500">

#### BST的查找操作

````java
T  key = a search key
Node root = point to the root of a BST

while(true){
    if(root==null){
        break;
    }
    if(root.value.equals(key)){
        return root;
    }
    else if(key.compareTo(root.value)<0){
        root = root.left;
    }
    else{
        root = root.right;
    }
}
return null;
````
**从程序中可以看出，当BST查找的时候，先与当前节点进行比较：**

- 如果相等的话就返回当前节点；
- 如果少于当前节点则继续查找当前节点的左节点；
- 如果大于当前节点则继续查找当前节点的右节点。
- 直到当前节点指针为空或者查找到对应的节点，程序查找结束。

#### BST的插入操作

````java
Node node = create a new node with specify value
Node root = point the root node of a BST
Node parent = null;

//find the parent node to append the new node
while(true){
   if(root==null)break;
   parent = root;
   if(node.value.compareTo(root.value)<=0){
      root = root.left;  
   }else{
      root = root.right;
   } 
}
if(parent!=null){
   if(node.value.compareTo(parent.value)<=0){//append to left
      parent.left = node;
   }else{//append to right
      parent.right = node;
   }
}
````
插入操作先通过循环查找到待插入的节点的父节点，和查找父节点的逻辑一样，都是比大小，小的往左，大的往右。<br>
找到父节点后，对比父节点，小的就插入到父节点的左节点，大就插入到父节点的右节点上。

#### BST的删除操作

`删除操作的步骤如下：`

_查找到要删除的节点。_
如果待删除的节点是叶子节点，则直接删除。
如果待删除的节点不是叶子节点，则先找到待删除节点的中序遍历的后继节点，用该后继节点的值替换待删除的节点的值，然后删除后继节点。
<img src='https://tech.meituan.com/img/redblack-tree/bst-tree-remove.png'>

#### BST存在的问题
   BST存在的主要问题是，数在插入的时候会导致树倾斜，不同的插入顺序会导致树的高度不一样，而树的高度直接的影响了树的查找效率。<br>
理想的高度是logN，最坏的情况是所有的节点都在一条斜线上，这样的树的高度为N。<br>
**`基于BST存在的问题`**，一种新的树——平衡二叉查找树(Balanced BST)产生了。<br>
平衡树在插入和删除的时候，会通过旋转操作将高度保持在logN。其中两款具有代表性的平衡树分别为AVL树和红黑树。<br>
AVL树由于实现比较复杂，而且插入和删除性能差，在实际环境下的应用不如红黑树。<br>

### 红黑树(RBT)
红黑树（Red-Black Tree，以下简称RBTree）的实际应用非常广泛，比如Linux内核中的完全公平调度器、高精度计时器、ext3文件系统等等，<br>
各种语言的函数库如Java的TreeMap和TreeSet，C++ STL的map、multimap、multiset等。<br>
RBTree也是函数式语言中最常用的持久数据结构之一，在计算几何中也有重要作用。值得一提的是，`Java 8中HashMap的实现也因为用RBTree取代链表，性能有所提升。`<br>
 
红黑树是一棵二叉搜索树，它在每个结点上增加一个存储位来表示结点的颜色，可以是RED或BLACK。
通过对任何一条从根到叶子的简单路径上各个结点的颜色进行约束，红黑树没有一条路径会比其他路径长出2倍，
所以红黑树是近似平衡的，使得红黑树的查找、插入、删除等操作的时间复杂度最坏为O(log n)，
但需要注意到在红黑树上执行插入或删除后将不在满足红黑树性质，恢复红黑树的属性需要少量(O(log n))的颜色变更(实际是非常快速的)
和不超过三次树旋转(对于插入操作是两次)。虽然插入和删除很复杂，但操作时间仍可以保持为 O(log n) 次。具体如何保证？引出红黑树的5个性质。

**RBTree的定义如下**: 
- 任何一个节点都有颜色，黑色或者红色
- 根节点是黑色的
- 父子节点之间不能出现两个连续的红节点
- 任何一个节点向下遍历到其子孙的叶子节点，所经过的黑节点个数必须相等
- 空节点被认为是黑色的

数据结构表示如下：

````java 
class  Node<T>{
   public  T value;
   public   Node<T> parent;
   public   boolean isRed;
   public   Node<T> left;
   public   Node<T> right;
}
```` 
RBTree在理论上还是一棵BST树，但是它在对BST的插入和删除操作时会维持树的平衡，<br>
即保证树的高度在[logN,logN+1]（理论上，极端的情况下可以出现RBTree的高度达到2*logN，但实际上很难遇到）。<br>
这样RBTree的查找时间复杂度始终保持在O(logN)从而接近于理想的BST。<br>
RBTree的删除和插入操作的时间复杂度也是O(logN)。<br>
RBTree的查找操作就是BST的查找操作。<br>

#### RBTree的旋转操作
旋转操作(Rotate)的目的是使节点颜色符合定义，让RBTree的高度达到平衡。<br>
Rotate分为left-rotate（左旋）和right-rotate（右旋），区分左旋和右旋的方法是：待旋转的节点从左边上升到父节点就是右旋，待旋转的节点从右边上升到父节点就是左旋。
<img src='https://tech.meituan.com/img/redblack-tree/rotate-all.png'>

#### RBTree的查找操作
RBTree的查找操作和BST的查找操作是一样的。请参考BST的查找操作代码。

#### RBTree的插入操作
RBTree的插入与BST的插入方式是一致的，只不过是在插入过后，可能会导致树的不平衡，这时就需要对树进行旋转操作和颜色修复（在这里简称插入修复），使得它符合RBTree的定义。

新插入的节点是红色的，插入修复操作如果遇到父节点的颜色为黑则修复操作结束。也就是说，只有在父节点为红色节点的时候是需要插入修复操作的。

插入修复操作分为以下的三种情况，而且新插入的节点的父节点都是红色的：

- 叔叔节点也为红色。
- 叔叔节点为空，且祖父节点、父节点和新节点处于一条斜线上。
- 叔叔节点为空，且祖父节点、父节点和新节点不处于一条斜线上。

##### **插入操作-case 1**
case 1的操作是将父节点和叔叔节点与祖父节点的颜色互换，这样就符合了RBTRee的定义。即维持了高度的平衡，修复后颜色也符合RBTree定义的第三条和第四条。下图中，操作完成后A节点变成了新的节点。如果A节点的父节点不是黑色的话，则继续做修复操作。
插入修复case 1
<img src='https://tech.meituan.com/img/redblack-tree/insert-case1.png'>

##### **插入操作-case 2**
case 2的操作是将B节点进行右旋操作，并且和父节点A互换颜色。通过该修复操作RBTRee的高度和颜色都符合红黑树的定义。如果B和C节点都是右节点的话，只要将操作变成左旋就可以了。
插入修复case 2
<img src='https://tech.meituan.com/img/redblack-tree/insert-case2.png'>

##### **插入操作-case 3**
case 3的操作是将C节点进行左旋，这样就从case 3转换成case 2了，然后针对case 2进行操作处理就行了。case 2操作做了一个右旋操作和颜色互换来达到目的。如果树的结构是下图的镜像结构，则只需要将对应的左旋变成右旋，右旋变成左旋即可。
插入修复case 3
<img src='https://tech.meituan.com/img/redblack-tree/insert-case3.png'>

##### **插入操作的总结**
插入后的修复操作是一个向root节点回溯的操作，一旦牵涉的节点都符合了红黑树的定义，修复操作结束。<br>
之所以会向上回溯是由于case 1操作会将父节点，叔叔节点和祖父节点进行换颜色，有可能会导致祖父节点不平衡(红黑树定义3)。<br>
这个时候需要对祖父节点为起点进行调节（向上回溯）。<br>

祖父节点调节后如果还是遇到它的祖父颜色问题，操作就会继续向上回溯，直到root节点为止，根据定义root节点永远是黑色的。<br>
在向上的追溯的过程中，针对插入的3中情况进行调节。直到符合红黑树的定义为止。<br>
直到牵涉的节点都符合了红黑树的定义，修复操作结束。<br>

如果上面的3中情况如果对应的操作是在右子树上，做对应的镜像操作就是了。

#### RBTree的删除操作

删除操作首先需要做的也是BST的删除操作，删除操作会删除对应的节点，如果是叶子节点就直接删除，如果是非叶子节点，
会用对应的中序遍历的后继节点来顶替要删除节点的位置。<br>删除后就需要做删除修复操作，使的树符合红黑树的定义，符合定义的红黑树高度是平衡的。

删除修复操作在遇到被删除的节点是红色节点或者到达root节点时，修复操作完毕。<br>

删除修复操作是针对删除黑色节点才有的，当黑色节点被删除后会让整个树不符合RBTree的定义的第四条。<br>
需要做的处理是从兄弟节点上借调黑色的节点过来，如果兄弟节点没有黑节点可以借调的话，就只能往上追溯，将每一级的黑节点数减去一个，使得整棵树符合红黑树的定义。<br>

删除操作的总体思想是从兄弟节点借调黑色节点使树保持局部的平衡，如果局部的平衡达到了，就看整体的树是否是平衡的，如果不平衡就接着向上追溯调整。<br>

**删除修复操作分为四种情况(删除黑节点后)：**<br>

- 待删除的节点的兄弟节点是红色的节点。
- 待删除的节点的兄弟节点是黑色的节点，且兄弟节点的子节点都是黑色的。
- 待调整的节点的兄弟节点是黑色的节点，且兄弟节点的左子节点是红色的，右节点是黑色的(兄弟节点在右边)，如果兄弟节点在左边的话，就是兄弟节点的右子节点是红色的，左节点是黑色的。
- 待调整的节点的兄弟节点是黑色的节点，且右子节点是是红色的(兄弟节点在右边)，如果兄弟节点在左边，则就是对应的就是左节点是红色的。
`删除操作-case 1`<br>
由于兄弟节点是红色节点的时候，无法借调黑节点，所以需要将兄弟节点提升到父节点，由于兄弟节点是红色的，根据RBTree的定义，兄弟节点的子节点是黑色的，就可以从它的子节点借调了。<br>
case 1这样转换之后就会变成后面的case 2，case 3，或者case 4进行处理了。上升操作需要对C做一个左旋操作，如果是镜像结构的树只需要做对应的右旋操作即可。<br>
之所以要做case 1操作是因为兄弟节点是红色的，无法借到一个黑节点来填补删除的黑节点。<br>
 
<img src='https://tech.meituan.com/img/redblack-tree/remove-case1.png'>

##### 删除操作-case 2
case 2的删除操作是由于兄弟节点可以消除一个黑色节点，因为兄弟节点和兄弟节点的子节点都是黑色的，所以可以将兄弟节点变红，这样就可以保证树的局部的颜色符合定义了。<br>
这个时候需要将父节点A变成新的节点，继续向上调整，直到整颗树的颜色符合RBTree的定义为止。<br>

case 2这种情况下之所以要将兄弟节点变红，是因为如果把兄弟节点借调过来，会导致兄弟的结构不符合RBTree的定义，这样的情况下只能是将兄弟节点也变成红色来达到颜色的平衡。<br>
当将兄弟节点也变红之后，达到了局部的平衡了，但是对于祖父节点来说是不符合定义4的。这样就需要回溯到父节点，接着进行修复操作。<br>
<img src='https://tech.meituan.com/img/redblack-tree/remove-case2.png'>

##### 删除操作-case 3
case 3的删除操作是一个中间步骤，它的目的是将左边的红色节点借调过来，这样就可以转换成case 4状态了，在case 4状态下可以将D，E节点都阶段过来，<br>
通过将两个节点变成黑色来保证红黑树的整体平衡。
之所以说case-3是一个中间状态，是因为根据红黑树的定义来说，下图并不是平衡的，他是通过case 2操作完后向上回溯出现的状态。<br>
之所以会出现case 3和后面的case 4的情况，是因为可以通过借用侄子节点的红色，变成黑色来符合红黑树定义4.
<img src='https://tech.meituan.com/img/redblack-tree/remove-case3.png'>

##### 删除操作-case 4
Case 4的操作是真正的节点借调操作，通过将兄弟节点以及兄弟节点的右节点借调过来，并将兄弟节点的右子节点变成红色来达到借调两个黑节点的目的，<br>
这样的话，整棵树还是符合RBTree的定义的。

Case 4这种情况的发生只有在待删除的节点的兄弟节点为黑，且子节点不全部为黑，才有可能借调到两个节点来做黑节点使用，从而保持整棵树都符合红黑树的定义。<br>
<img src='https://tech.meituan.com/img/redblack-tree/remove-case4.png'>

##### 删除操作的总结
红黑树的删除操作是最复杂的操作，复杂的地方就在于当删除了黑色节点的时候，如何从兄弟节点去借调节点，以保证树的颜色符合定义。<br>
由于红色的兄弟节点是没法借调出黑节点的，这样只能通过选择操作让他上升到父节点，而由于它是红节点，所以它的子节点就是黑的，可以借调。<br>

对于兄弟节点是黑色节点的可以分成3种情况来处理，当所以的兄弟节点的子节点都是黑色节点时，可以直接将兄弟节点变红，这样局部的红黑树颜色是符合定义的。<br>
但是整颗树不一定是符合红黑树定义的，需要往上追溯继续调整。<br>

对于兄弟节点的子节点为左红右黑或者 (全部为红，右红左黑)这两种情况，可以先将前面的情况通过选择转换为后一种情况，在后一种情况下，<br>
因为兄弟节点为黑，兄弟节点的右节点为红，可以借调出两个节点出来做黑节点，这样就可以保证删除了黑节点，整棵树还是符合红黑树的定义的，因为黑色节点的个数没有改变。<br>

红黑树的删除操作是遇到删除的节点为红色，或者追溯调整到了root节点，这时删除的修复操作完毕。<br>

#### RBTree的Java实现

````java
package com.runcoding.learn.algorithm.tree.rbtree;

public class RBTreeNode<T extends Comparable<T>> {

    private T value;//node value

    private RBTreeNode<T> left;//left child pointer

    private RBTreeNode<T> right;//right child pointer

    private RBTreeNode<T> parent;//parent pointer

    private boolean red;//color is red or not red

    public RBTreeNode() {
    }

    public RBTreeNode(T value) {
        this.value = value;
    }

    public RBTreeNode(T value, boolean isRed) {
        this.value = value;
        this.red = isRed;
    }

    public T getValue() {
        return value;
    }

    void setValue(T value) {
        this.value = value;
    }

    RBTreeNode<T> getLeft() {
        return left;
    }

    void setLeft(RBTreeNode<T> left) {
        this.left = left;
    }

    RBTreeNode<T> getRight() {
        return right;
    }

    void setRight(RBTreeNode<T> right) {
        this.right = right;
    }

    RBTreeNode<T> getParent() {
        return parent;
    }

    void setParent(RBTreeNode<T> parent) {
        this.parent = parent;
    }

    boolean isRed() {
        return red;
    }

    boolean isBlack() {
        return !red;
    }

    /**
     * is leaf node
     **/
    boolean isLeaf() {
        return left == null && right == null;
    }

    void setRed(boolean red) {
        this.red = red;
    }

    void makeRed() {
        red = true;
    }

    void makeBlack() {
        red = false;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

````
**`实现代码:`**<a href='/learn-java/base-java/src/main/java/com/runcoding/learn/algorithm/tree/rbtree/RBTree.java'>RBTree.java</a>

#### 总结
作为平衡二叉查找树里面众多的实现之一，红黑树无疑是最简洁、实现最为简单的。红黑树通过引入颜色的概念，通过颜色这个约束条件的使用来保持树的高度平衡。作为平衡二叉查找树，旋转是一个必不可少的操作。通过旋转可以降低树的高度，在红黑树里面还可以转换颜色。

红黑树里面的插入和删除的操作比较难理解，这时要注意记住一点：操作之前红黑树是平衡的，颜色是符合定义的。在操作的时候就需要向兄弟节点、父节点、侄子节点借调和互换颜色，要达到这个目的，就需要不断的进行旋转。所以红黑树的插入删除操作需要不停的旋转，一旦借调了别的节点，删除和插入的节点就会达到局部的平衡（局部符合红黑树的定义），但是被借调的节点就不会平衡了，这时就需要以被借调的节点为起点继续进行调整，直到整棵树都是平衡的。在整个修复的过程中，插入具体的分为3种情况，删除分为4种情况。

整个红黑树的查找，插入和删除都是O(logN)的，原因就是整个红黑树的高度是logN，查找从根到叶，走过的路径是树的高度，删除和插入操作是从叶到根的，所以经过的路径都是logN。


#### AVL树
AVL树由于实现比较复杂，而且插入和删除性能差，在实际环境下的应用不如红黑树。<br>
1.LL型

在某一节点的左孩子的左子树上插入一个新的节点，使得该节点不再平衡。
举例 A B Ar Bl Br 在Bl下插入N，执行一次右旋即可，即把B变为父结点，原来的根节点A变为B的左孩子，B的右子树变为A的左子树。

2.RR型

与LL型是对称的，执行一次左旋即可。

3.LR型

指在AVL树某一结点左孩子的右子树上插入一个结点，使得该节点不在平衡。这时需要两次旋转，先左旋再右旋。

4.RL型

与LR对称，执行一次右旋，再执行一次左旋。

删除

1、被删的节点是叶子节点

将该节点直接从树中删除，并利用递归的特点和高度的变化，反向推算其父节点和祖先节点是否失衡。

2、被删的节点只有左子树或只有右子树

将左子树（右子树）替代原有节点的位置，并利用递归的特点和高度的变化，反向推算父节点和祖先节点是否失衡。

3、被删的节点既有左子树又有右子树

找到被删节点的左子树的最右端的节点，将该结点的的值赋给待删除结点，再用该结点的左孩子替换它本来的位置，然后释放该结点，并利用递归特点，反向推断父节点和祖父节点是否失衡。


### Trie
* 字典树，又称基数树或者前缀树，能够用于存储键为字符串的动态集合或者关联数组的搜索树。树中的节点并没有直接存储关联键值，而是该节点在树中的挂载位置决定了其关联键值。某个节点的所有子节点都拥有相同的前缀，整棵树的根节点则是空字符串。

![Alt text](https://runcoding.github.io/static/wiki/learn-java/java/algorithm/trie.png "Trie")

### Fenwick Tree
* 树状数组又称 Binary Indexed Tree，其表现形式为树，不过本质上是以数组实现。数组中的下标代表着树中的顶点，每个顶点的父节点或者子节点的下标能够通过位运算获得。数组中的每个元素包含了预计算的区间值之和，在整棵树更新的过程中同样会更新这些预计算的值。
* 时间复杂度:
  * 区间求值: `O(log(n))`
  * 更新: `O(log(n))`

![Alt text](https://runcoding.github.io/static/wiki/learn-java/java/algorithm/fenwickTree.png "Fenwick Tree")

### Segment Tree
* 线段树是用于存放间隔或者线段的树形数据结构，它允许快速的查找某一个节点在若干条线段中出现的次数.
* 时间复杂度:
  * 区间查询: `O(log(n))`
  * 更新: `O(log(n))`

![Alt text](https://runcoding.github.io/static/wiki/learn-java/java/algorithm/segmentTree.png "Segment Tree")

### Heap
* 堆是一种特殊的基于树的满足某些特性的数据结构，整个堆中的所有父子节点的键值都会满足相同的排序条件。堆更准确地可以分为最大堆与最小堆，在最大堆中，父节点的键值永远大于或者等于子节点的值，并且整个堆中的最大值存储于根节点；而最小堆中，父节点的键值永远小于或者等于其子节点的键值，并且整个堆中的最小值存储于根节点。
* 时间复杂度:
  * 访问: `O(log(n))`
  * 搜索: `O(log(n))`
  * 插入: `O(log(n))`
  * 移除: `O(log(n))`
  * 移除最大值 / 最小值: `O(1)`

<img src="https://runcoding.github.io/static/wiki/learn-java/java/algorithm/heap.png" alt="Max Heap" width="400" height="500">


### Hashing
* 哈希能够将任意长度的数据映射到固定长度的数据。哈希函数返回的即是哈希值，如果两个不同的键得到相同的哈希值，即将这种现象称为碰撞。
* **Hash Map**: Hash Map 是一种能够建立起键与值之间关系的数据结构，Hash Map 能够使用哈希函数将键转化为桶或者槽中的下标，从而优化对于目标值的搜索速度。
* 碰撞解决
  * **链地址法（Separate Chaining）**: 链地址法中，每个桶是相互独立的，包含了一系列索引的列表。搜索操作的时间复杂度即是搜索桶的时间（固定时间）与遍历列表的时间之和。
  * **开地址法（Open Addressing）**: 在开地址法中，当插入新值时，会判断该值对应的哈希桶是否存在，如果存在则根据某种算法依次选择下一个可能的位置，直到找到一个尚未被占用的地址。所谓开地址法也是指某个元素的位置并不永远由其哈希值决定。

![Alt text](https://runcoding.github.io/static/wiki/learn-java/java/algorithm/hash.png "Hashing")

### Graph
* 图是一种数据元素间为多对多关系的数据结构，加上一组基本操作构成的抽象数据类型。
    * **无向图（Undirected Graph）**: 无向图具有对称的邻接矩阵，因此如果存在某条从节点 u 到节点 v 的边，反之从 v 到 u 的边也存在。
    * **有向图（Directed Graph）**: 有向图的邻接矩阵是非对称的，即如果存在从 u 到 v 的边并不意味着一定存在从 v 到 u 的边。

<img src="https://runcoding.github.io/static/wiki/learn-java/java/algorithm/graph.png" alt="Graph" width="400" height="500">

## 算法

### 排序

#### 快速排序
* 稳定: 否
* 时间复杂度:
  * 最优时间: `O(nlog(n))`
  * 最坏时间: `O(n^2)`
  * 平均时间: `O(nlog(n))`

![Alt text](https://runcoding.github.io/static/wiki/learn-java/java/algorithm/quicksort.gif "Quicksort")

#### 归并排序
* 归并排序是典型的分治算法，它不断地将某个数组分为两个部分，分别对左子数组与右子数组进行排序，然后将两个数组合并为新的有序数组。
* 稳定: 是
* 时间复杂度:
  * 最优时间: `O(nlog(n))`
  * 最坏时间: `O(nlog(n))`
  * 平均时间: `O(nlog(n))`

![Alt text](https://runcoding.github.io/static/wiki/learn-java/java/algorithm/mergesort.gif "Mergesort")

#### 桶排序
* 桶排序将数组分到有限数量的桶子里。每个桶子再个别排序（有可能再使用别的排序算法或是以递归方式继续使用桶排序进行排序）。
* 时间复杂度:
  * 最优时间: `Ω(n + k)`
  * 最坏时间: `O(n^2)`
  * 平均时间:`Θ(n + k)`


![Alt text](https://runcoding.github.io/static/wiki/learn-java/java/algorithm/bucketsort.png "Bucket Sort")

#### 基数排序
* 基数排序类似于桶排序，将数组分割到有限数目的桶中；不过其在分割之后并没有让每个桶单独地进行排序，而是直接进行了合并操作。
* 时间复杂度:
  * 最优时间: `Ω(nk)`
  * 最坏时间: `O(nk)`
  * 平均时间: `Θ(nk)`

### 图算法

#### 深度优先搜索
* 深度优先算法是一种优先遍历子节点而不是回溯的算法。
* 时间复杂度: `O(|V| + |E|)`

![Alt text](https://runcoding.github.io/static/wiki/learn-java/java/algorithm/dfsbfs.gif "DFS / BFS Traversal")

#### 广度优先搜索
* 广度优先搜索是优先遍历邻居节点而不是子节点的图遍历算法。
* 时间复杂度: `O(|V| + |E|)`

![Alt text](https://runcoding.github.io/static/wiki/learn-java/java/algorithm/dfsbfs.gif "DFS / BFS Traversal")

#### 拓扑排序
* 拓扑排序是对于有向图节点的线性排序，如果存在某条从 u 到 v 的边，则认为 u 的下标先于 v。
* 时间复杂度: `O(|V| + |E|)`

#### Dijkstra 算法
* **Dijkstra 算法** 用于计算有向图中单源最短路径问题。
* 时间复杂度: `O(|V|^2)`

![Alt text](https://runcoding.github.io/static/wiki/learn-java/java/algorithm/dijkstra.gif "Dijkstra's")

#### Bellman-Ford 算法
* **Bellman-Ford 算法**是在带权图中计算从单一源点出发到其他节点的最短路径的算法。
* 尽管算法复杂度大于 Dijkstra 算法，但是它适用于包含了负值边的图。
* 时间复杂度:
  * 最优时间: `O(|E|)`
  - 最坏时间: `O(|V||E|)`

![Alt text](https://runcoding.github.io/static/wiki/learn-java/java/algorithm/bellman-ford.gif "Bellman-Ford")

#### Floyd-Warshall 算法
* **Floyd-Warshall 算法** 能够用于在无环带权图中寻找任意节点的最短路径。
* 时间复杂度:
  * 最优时间: `O(|V|^3)`
  * 最坏时间: `O(|V|^3)`
  * 平均时间: `O(|V|^3)`

#### Prim 算法
* **Prim 算法**是用于在带权无向图中计算最小生成树的贪婪算法。换言之，Prim 算法能够在图中抽取出连接所有节点的边的最小代价子集。
* 时间复杂度: `O(|V|^2)`

![Alt text](https://runcoding.github.io/static/wiki/learn-java/java/algorithm/prim.gif "Prim's Algorithm")

#### Kruskal 算法
* **Kruskal 算法**同样是计算图的最小生成树的算法，与 Prim 的区别在于并不需要图是连通的。
* 时间复杂度: `O(|E|log|V|)`

![Alt text](https://runcoding.github.io/static/wiki/learn-java/java/algorithm/kruskal.gif "Kruskal's Algorithm")

## 位运算
* 位运算即是在位级别进行操作的技术，合适的位运算能够帮助我们得到更快地运算速度与更小的内存使用。
* 测试第 k 位: `s & (1 << k)`
* 设置第 k 位: `s |= (1 << k)`
* 第 k 位置零: `s &= ~(1 << k)`
* 切换第 k 位值: `s ^= ~(1 << k)`
* 乘以 2: `s << n`
* 除以 2: `s >> n`
* 交集: `s & t`
* 并集: `s | t`
* 减法: `s & ~t`
* 交换 `x = x ^ y ^ (y = x)`
* 取出最小非 0 位（Extract lowest set bit）: `s & (-s)`
* 取出最小 0 位（Extract lowest unset bit）: `~s & (s + 1)`
* 交换值:
             ```
                x ^= y;
                y ^= x;
                x ^= y;
             ```

## 算法复杂度分析

#### 大 O 表示
* **大 O 表示** 用于表示某个算法的上限，往往用于描述最坏的情况。

![Alt text](https://runcoding.github.io/static/wiki/learn-java/java/algorithm/bigO.png "Theta Notation")

#### 小 O 表示
* **小 O 表示**用于描述某个算法的渐进上界，不过二者要更为紧密。

#### 大 Ω 表示
* **大 Ω 表示**用于描述某个算法的渐进下界。

![Alt text](https://runcoding.github.io/static/wiki/learn-java/java/algorithm/bigOmega.png "Theta Notation")

#### 小 ω 表示
* **Little Omega Notation**用于描述某个特定算法的下界，不过不一定很靠近。

#### Theta Θ 表示
* **Theta Notation**用于描述某个确定算法的确界。

![Alt text](https://runcoding.github.io/static/wiki/learn-java/java/algorithm/theta.png "Theta Notation")



#### 排序算法

- 稳定排序:插入排序、冒泡排序、归并排序、基数排序

- 插入排序[稳定]
适用于小数组,数组已排好序或接近于排好序速度将会非常快
复杂度：O(n^2) - O(n) - O(n^2) - O(1)[平均 - 最好 - 最坏 - 空间复杂度]

- 归并排序[稳定]
采用分治法
复杂度：O(nlogn) - O(nlgn) - O(nlgn) - O(n)[平均 - 最好 - 最坏 - 空间复杂度]

- 冒泡排序[稳定]
复杂度：O(n^2) - O(n) - O(n^2) - O(1)[平均 - 最好 - 最坏 - 空间复杂度]

- 基数排序 分配+收集[稳定]
复杂度： O(d(n+r)) r为基数d为位数 空间复杂度O(n+r)

- 树排序[不稳定]
应用：TreeSet的add方法、TreeMap的put方法
不支持相同元素,没有稳定性问题
复杂度：平均最差O(nlogn)

- 堆排序(就地排序)[不稳定]
复杂度：O(nlogn) - O(nlgn) - O(nlgn) - O(1)[平均 - 最好 - 最坏 - 空间复杂度]

- 快速排序[不稳定]
复杂度：O(nlgn) - O(nlgn) - O(n^2) - O(1)[平均 - 最好 - 最坏 - 空间复杂度]
栈空间0(lgn) - O(n)

- 选择排序[不稳定]
复杂度：O(n^2) - O(n^2) - O(n^2) - O(1)[平均 - 最好 - 最坏 - 空间复杂度]

- 希尔排序[不稳定]
复杂度 小于O(n^2) 平均 O(nlgn) 最差O(n^s)[1<s<2] 空间O(1)

九大内部排序算法代码及性能分析参见我的[GitHub](https://github.com/it-interview/easy-job/tree/master/algorithm)

#### 查找与散列

4.1 散列函数设计

* 直接定址法:```f(key) = a*key+b```

简单、均匀,不易产生冲突。但需事先知道关键字的分布情况,适合查找表较小且连续的情况,故现实中并不常用

* 除留余数法:```f(key) = key mod p (p<=m) p取小于表长的最大质数 m为表长```

* DJBX33A算法(time33哈希算法```hash = hash*33+(unsigned int)str[i];```

平方取中法 折叠法 更多....

4.2 冲突处理

闭散列(开放地址方法):要求装填因子a较小，闭散列方法把所有记录直接存储在散列表中

- 线性探测:易产生堆积现象(基地址不同堆积在一起)
- 二次探测:f(key) = (f(key)+di) % m di=1^2,-1^2,2^2,-2^2...可以消除基本聚集
- 随机探测:f(key) = (f(key)+di),di采用随机函数得到,可以消除基本聚集
- 双散列:避免二次聚集

开散列(链地址法):原地处理

- 同义词记录存储在一个单链表中,散列表中子存储单链表的头指针。
- 优点:无堆积 事先无需确定表长 删除结点易于实现 装载因子a>=1,缺点:需要额外空间

#### 跳表

为什么选择跳表？

目前经常使用的平衡数据结构有：B树，红黑树，AVL树，Splay Tree, Treep等。
想象一下，给你一张草稿纸，一只笔，一个编辑器，你能立即实现一颗红黑树，或者AVL树
出来吗？ 很难吧，这需要时间，要考虑很多细节，要参考一堆算法与数据结构之类的树，
还要参考网上的代码，相当麻烦。
用跳表吧，跳表是一种随机化的数据结构，目前开源软件 Redis 和 LevelDB 都有用到它，
它的效率和红黑树以及 AVL 树不相上下，但跳表的原理相当简单，只要你能熟练操作链表，
就能去实现一个 SkipList。

跳跃表是一种随机化数据结构，基于并联的链表，其效率可比拟于二叉查找树(对于大多数操作需要O(log n)平均时间)，并且对并发算法友好。

Skip list(跳表）是一种可以代替平衡树的数据结构，默认是按照Key值升序的。Skip list让已排序的数据分布在多层链表中，以0-1随机数决定一个数据的向上攀升与否，是一种“空间来换取时间”的一个算法，在每个节点中增加了指向下一层的指针，在插入、删除、查找时可以忽略一些不可能涉及到的结点，从而提高了效率。

在Java的API中已经有了实现：分别是

ConcurrentSkipListMap(在功能上对应HashTable、HashMap、TreeMap) ；

ConcurrentSkipListSet(在功能上对应HashSet)

Skip list的性质

(1) 由很多层结构组成，level是通过一定的概率随机产生的

(2) 每一层都是一个有序的链表，默认是升序

(3) 最底层(Level 1)的链表包含所有元素

(4) 如果一个元素出现在Level i 的链表中，则它在Level i 之下的链表也都会出现

(5) 每个节点包含两个指针，一个指向同一链表中的下一个元素，一个指向下面一层的元素

时间复杂度O(lgn) 最坏O(2lgn)

Java实现参见我的GitHub Repo [Algorithm](https://github.com/it-interview/algorithm)


#### 一致性Hash

第一：简单介绍
一致性哈希算法是分布式系统中常用的算法。比如，一个分布式的存储系统，要将对象存储到具体的节点上，如果采用普通的hash方法，将数据映射到具体的节点上，如key%N，N是机器节点数。

1、考虑到比如一个服务器down掉，服务器结点N变为N-1，映射公式必须变为key%(N-1)

2、访问量加重，需要添加服务器结点，N变为N+1，映射公式变为hash(object)%(N+1)

当出现1,2的情况意味着我们的映射都将无效，对服务器来说将是一场灾难，尤其是对缓存服务器来说，因为缓存服务器映射的失效，洪水般的访问都将冲向后台服务器。

第二点：hash算法的单调性

Hash 算法的一个衡量指标是单调性，单调性是指如果已经有一些内容通过哈希分派到了相应的缓冲中，又有新的缓冲加入到系统中。哈希的结果应能够保证原有已分配的内容可以被映射到新的缓冲中去，而不会被映射到旧的缓冲集合中的其他缓冲区。

consistent hash 也是一种hash 算法，简单的说，在移除 / 添加一个结点时，它能够尽可能小的改变已存在的映射关系，尽可能的满足单调性的要求。

第三点：将对象和服务器结点分别映射到环型空间

通常的一致性哈希做法是将 value 映射到一个 32 位的 key 值，也即是 0~2^32-1 次方的数值空间；我们可以将这个空间想象成一个首（ 0 ）尾（ 2^32-1 ）相接的圆环。

我们可以通过hash函数将我们的key映射到环型空间中，同时根据相同的哈希算法把服务器也映射到环型空间中，顺便提一下服务器或者某个计算节点的 hash 计算，一般的方法可以使用机器的 IP 地址或者机器名作为 hash 输入。

第四点：将对象映射到服务器

在这个环形空间中，如果沿着顺时针方向从对象的 key 值出发，直到遇见一个 服务器结点，那么就将该对象存储在这个服务器结点上，因为对象和服务器的hash 值是固定的，因此这个 cache 必然是唯一和确定的。

这时候考察某个服务器down机或者需要添加服务器结点，也就是移除和添加的操作，我们只需要几个对象的映射。

第五点：虚拟结点

 Hash 算法的另一个指标是平衡性 (Balance)。平衡性是指哈希的结果能够尽可能分布到所有的缓冲中去，这样可以使得所有的缓冲空间都得到利用。

 对于上述的做法，可能导致某些对象都映射到某个服务器，使得分布不平衡。为此可以采用“虚拟结点”的做法。

 “虚拟结点”（ virtual node ）是实际节点在 hash 空间的复制品，一实际结点对应了若干个“虚拟节点”，这个对应个数也成为“复制个数”，“虚拟节点”在 hash 空间中以 hash 值排列。引入“虚拟结点”会让我们的映射分布更为平衡一些。

引入“虚拟结点”前：
Hash(“192.168.1.1”);

引入“虚拟结点”后：
Hash(“192.168.1.1#1”);
Hash(“192.168.1.1#2”);

#### 如何判断链表是否有环

方法1：快慢指针法 2.设两个工作指针p、q，p总是向前走，但q每次都从头开始走，对于每个节点，看p走的步数是否和q一样。比如p从A走到D，用了4步，而q则用了14步。因而步数不等，出现矛盾，存在环。

#### 熟悉哪些算法？

- [哈希算法] 一致性哈希 time33哈希 FNV1_32_HASH
- [排序算法] 快速排序
- [搜索算法] DFS BFS
- [最小生成树算法] Kruskal Prim
- [最短路径算法] Dijkstra Floyed 

#### 算法题
- <a href='https://itimetraveler.github.io/2017/01/31/%E3%80%90%E7%AE%97%E6%B3%95%E3%80%91%E5%8D%95%E9%93%BE%E8%A1%A8%E5%8F%8D%E8%BD%AC%EF%BC%8C%E4%B8%A4%E4%B8%A4%E5%8F%8D%E8%BD%AC/'>反转单链表，两两反转</a>