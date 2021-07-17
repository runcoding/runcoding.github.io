package com.runcoding.guava.graph;

import com.google.common.collect.Sets;
import com.google.common.graph.*;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author runcoding
 * @date 2019-02-20
 * @desc: Graph是最简单和最基本的图类型。它定义了用于处理节点到节点关系的基本操作，
 * 例如获取后继节点：successors(node)，获取相邻节点：adjacentNodes(node)和获取节点的入度值：
 * inDegree(node)。它的节点是第一类唯一对象，您可以将它们视为类似于将键映射到图形内部数据结构中。
 * https://my.oschina.net/piorcn/blog/812118
 * https://www.jianshu.com/p/78786a4f2cf1
 */
public class GraphTest {


    private static final int NODE_COUNT = 10;

    @Test
    public void test(){
        MutableGraph<Integer> graph = GraphBuilder
                //指定为有向图
                .directed()
                //节点按插入顺序输出
                .nodeOrder(ElementOrder.<Integer>insertion())
                //(还可以取值无序unordered()、节点类型的自然顺序natural())
                //预期节点数
                .expectedNodeCount(NODE_COUNT)
                //允许自环(自环既是入度也是出度)
                .allowsSelfLoops(true)
                .build();
        graph.addNode(1);

        graph.putEdge(1, 2);

        // also adds nodes 2 and 3 if not already present
        graph.putEdge(2, 3);

        graph.putEdge(2, 4);

        // no effect; Graph does not support parallel edges
        graph.putEdge(2, 5);

        graph.putEdge(3, 5);


        /**返回节点*/
        Set<Integer> nodes = graph.nodes();

        /**返回前置节点的数据*/
        Set<Integer> predecessors = graph.predecessors(5);

        /**获取节点2的后继节点*/
        Set<Integer> successors = graph.successors(2);

        /**获取2的相邻节点(前后)*/
        Set<Integer> adjacentNodes = graph.adjacentNodes(2);

        /**获取2节点的入度,1-->2;连接2的数量*/
        int inDegree = graph.inDegree(2);
        /**获取2节点的出度，2-->3；被2连接的数量*/
        int outDegree = graph.outDegree(2);

        /**1联通2*/
        boolean hasEdgeConnecting2 = graph.hasEdgeConnecting(1, 2);
        boolean hasEdgeConnecting3 = graph.hasEdgeConnecting(1, 3);

        /**生成环*/
        graph.putEdge(5, 3);
        boolean cycle = Graphs.hasCycle(graph);
        HashSet<Integer> subNodes = Sets.newHashSet(2,3, 5);

        /**获取包含[2、3、5]的子图。 isDirected: true, allowsSelfLoops: true, nodes: [2, 3, 5], edges: [<2 -> 5>, <2 -> 3>, <3 -> 5>, <5 -> 3>]*/
        MutableGraph<Integer> subGraph = Graphs.inducedSubgraph(graph, subNodes);

        /**2的可到列表。[2\3\4\5]*/
        Set<Integer> reachableNodes = Graphs.reachableNodes(graph, 2);


        //深度优先-后序
        Iterable<Integer> dfs = Traverser.forGraph(graph).depthFirstPostOrder(2);
        dfs.forEach(n->{
            System.out.println("深度优先-后序="+n);
        });
        //深度优先-前序
        Iterable<Integer> dfsPre =Traverser.forGraph(graph).depthFirstPreOrder(2);
        dfsPre.forEach(n->{
            System.out.println("深度优先-前序="+n);
        });
        //广度优先
        Iterable<Integer> bfs =Traverser.forGraph(graph).breadthFirst(2);
        bfs.forEach(n->{
            System.out.println("广度优先="+n);
        });


        System.out.println(graph);
    }


}
