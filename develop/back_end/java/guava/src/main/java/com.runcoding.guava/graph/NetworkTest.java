package com.runcoding.guava.graph;

import com.alibaba.fastjson.JSON;
import com.google.common.graph.*;
import org.junit.Test;

import java.util.Set;

/**
 * @author runcoding
 * @date 2019-02-20
 * @desc: https://www.jianshu.com/p/78786a4f2cf1
 *由于Network与Graph以及ValueGraph有很大的不同性，最大的不同点是Network中允许并行边
 * （即两个节点间可以有多条同向边，如：节点A和节点B可以有两条同向边：A->B: a-b-1，a-b-2），
 * 就导致了前面介绍的使用节点作为Map的key的数据结构GraphConnections的逻辑走不下去了，因为节点不唯一了。
 * 使得设计者重新设计了另一套使用边为Key的机制来实现节点的连接关系。
 *
 */
public class NetworkTest {

    private static final int NODE_COUNT = 10;
    private static final int EDGE_COUNT = 10;

    @Test
    public void  test(){
        MutableNetwork<Integer, String> network = NetworkBuilder.directed() //有向网
                .allowsParallelEdges(true) //允许并行边
                .allowsSelfLoops(true) //允许自环
                .nodeOrder(ElementOrder.<Integer>insertion()) //节点顺序
                .edgeOrder(ElementOrder.<String>insertion()) //边顺序
                .expectedNodeCount(NODE_COUNT) //期望节点数
                .expectedEdgeCount(EDGE_COUNT) //期望边数
                .build();

        network.addNode(1);
        // also adds nodes 2 and 3 if not already present
        network.addEdge( 2, 3,"2->3A");

        // returns {3}
        Set<Integer> successorsOfTwo = network.successors(2);
        // returns {"2->3"}
        Set<String> outEdgesOfTwo = network.outEdges(2);

        // by default
        // no effect; this edge is already present
        network.addEdge( 2, 3,"2->3B");
        network.addEdge( 2, 3,"2->3C");

        network.addEdge( 3, 4,"3->4A");
        network.addEdge( 3, 5,"3->5A");
        network.addEdge( 4, 5,"4->5A");

        // throws; node not in graph
        Set<String> inEdgesOfFour = network.inEdges(3);

        /**获取相邻边到节点3的入边和出边*/
        Set<String> adjacentEdges = network.adjacentEdges("2->3C");

        /** 获取两个节点的边："2->3A"  "2->3C" "2->3B"*/
        Set<String> edgesConnecting = network.edgesConnecting(2, 3);

        /**获取节点包含的所有边集合*/
        Set<String> incidentEdges = network.incidentEdges(4);

        /**通过边获取相邻节点*/
        EndpointPair<Integer> incidentNodes = network.incidentNodes("2->3B");

        System.out.println(JSON.toJSONString(network));
    }



}
