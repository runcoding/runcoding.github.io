package com.runcoding.guava.graph;

import com.alibaba.fastjson.JSON;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import org.apache.commons.collections.MapIterator;
import org.junit.Test;

import java.util.Set;

/**
 * @author runcoding
 * @date 2019-02-20
 * @desc:
 */
public class ValueGraphTest {



    private static final int NODE_COUNT = 10;
    @Test
    public void test(){
        MutableValueGraph<Integer, Double> graph = ValueGraphBuilder.directed().allowsSelfLoops(true)
                .expectedNodeCount(NODE_COUNT)
                .nodeOrder(ElementOrder.<Integer>insertion())
                .build();
        graph.addNode(1);
        // also adds nodes 2 and 3 if not already present
        graph.putEdgeValue(2, 3, 1.5);
        // edge values (like Map values) need not be unique
        graph.putEdgeValue(3, 5, 1.5);
        // updates the value for (2,3) to 2.0
        graph.putEdgeValue(2, 3, 2.0);

        Set<Integer> nodes = graph.nodes();
        System.out.println(JSON.toJSONString(nodes));

        Set<Integer> integers = graph.asGraph().adjacentNodes(2);
        System.out.println(JSON.toJSONString(integers));

        System.out.println(graph);
    }


}
