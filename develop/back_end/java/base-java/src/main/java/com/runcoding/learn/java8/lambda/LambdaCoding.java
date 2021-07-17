package com.runcoding.learn.java8.lambda;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import lombok.*;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author runcoding
 * @desc 使用lambda 编程
 */
public class LambdaCoding {

    private  List<Order> orders = Lists.newArrayList(   new Order(1,"1001",BigDecimal.valueOf(1)),
                                                        new Order(5,"1005",BigDecimal.valueOf(5)),
                                                        new Order(3,"1003",BigDecimal.valueOf(3)));

    /**过滤 */
    @Test
    public void caseFilter(){
        orders = orders.stream()
                /**过滤id大于3的*/
                .filter(order -> order.getId()>3)
                /**改变过滤后的值*/
                .map(order -> {order.setNumber("10055");return order;})
                .collect(Collectors.toList());
        /**[{"id":5,"number":"10055","totalPrice":5}]*/
        System.out.println("Filter ="+JSON.toJSONString(orders));
    }

    /**reduce 将多个对象合并成一个*/
    @Test
    public void  caseByReduce(){
        BigDecimal totalPrice = orders.stream().map(o -> o.getTotalPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("统计订单金额="+totalPrice);


        List<List<Order>> lists = Lists.newArrayList(orders,orders);
        List<Order> orderReduce = lists.stream().reduce((oldVal, currVal) -> {
            oldVal.addAll(currVal);
            return oldVal;
        }).orElse(Lists.newArrayList());
        orderReduce.forEach(o->{
            if(o.getId() == 1){
                System.out.println(""+JSON.toJSONString(o));
            }
        });
        System.out.println("多对象聚合:"+JSON.toJSONString(orderReduce,SerializerFeature.DisableCircularReferenceDetect));
    }



    /**List转Map*/
    @Test
    public void  caseByListToMap(){
        Map<Integer, Order> orderMap = orders.stream().collect(Collectors.toMap(o -> o.getId(), o -> o));
        /**{1:{"id":1,"number":"1001","totalPrice":1},3:{"id":3,"number":"1003","totalPrice":3},5:{"id":5,"number":"1005","totalPrice":5}}*/
        System.out.println(JSON.toJSONString(orderMap));

        /**{5:["1005"],3:["1003"],1:["1001"]}*/
        Map<BigDecimal, List<String>> listMap = orders.stream().collect(
                        Collectors.groupingBy(order -> order.getTotalPrice(),
                        Collectors.mapping(order -> order.getNumber(),
                        Collectors.toList())));
        System.out.println(JSON.toJSONString(listMap));

        orders.set(2, new Order(5,"1003",BigDecimal.valueOf(3)));
        Map<Integer, String> map = orders.stream().collect(
                Collectors.toMap(
                        o -> o.getId(),
                        o -> o.getNumber(),
                        /**如果Map,key有重复,值又不同时会报错错误"Duplicate key 1005"。可以通过下面方式规避*/
                        (oldVal, newVal) -> {
                            System.out.println("key冲突时，值处理方案："+oldVal+"="+newVal);
                            return oldVal;
                        })
        );
        /**{1:"1001",5:"1005"}*/
        System.out.println(JSON.toJSONString(map));
    }

    /**List转Set*/
    @Test
    public void  caseByListToSet(){
        Set<Integer> set = orders.stream().map(order -> order.getId()).collect(Collectors.toSet());
        /**[1,3,5]*/
        System.out.println("orders List to Set ="+JSON.toJSONString(set));
    }

    /**排序*/
    @Test
    public void  caseBySorte(){
        Comparator<Order> byReducePrice = Comparator.comparing(o -> o.getTotalPrice());
        List<Order> orderSorted = orders.stream().sorted(byReducePrice.reversed()).collect(Collectors.toList());
        /**
         * 1. 降序: .sorted(byReducePrice.reversed()).
         *     [{"id":5,"number":"1005","totalPrice":5},{"id":3,"number":"1003","totalPrice":3},{"id":1,"number":"1001","totalPrice":1}]
         * 2. 升序：.sorted(byReducePrice).
         *    [{"id":1,"number":"1001","totalPrice":1},{"id":3,"number":"1003","totalPrice":3},{"id":5,"number":"1005","totalPrice":5}]
         * */

        System.out.println("排序 ="+JSON.toJSONString(orderSorted));
    }


    /** * 自定义带有index 的Lambda 表达式的foreach  */
    private  <E> void indexForEach( Iterable<? extends E> elements, BiConsumer<Integer, ? super E> action) {
        Objects.requireNonNull(elements);
        Objects.requireNonNull(action);
        int index = 0;
        for (E element : elements) {
            action.accept(index++, element);
        }
    }

    /**for循环*/
    @Test
    public void caseForEach(){
        /**普通for循环
         *  -> {"id":1,"number":"1001","totalPrice":1}
         *  -> {"id":5,"number":"1005","totalPrice":5}
         *  -> {"id":3,"number":"1003","totalPrice":3}
         * */
        orders.forEach((Order order)-> System.out.println(" -> " + JSON.toJSONString(order)));

        /**自定义带有index的for循环
         * 0 -> {"id":1,"number":"1001","totalPrice":1}
         * 1 -> {"id":5,"number":"1005","totalPrice":5}
         * 2 -> {"id":3,"number":"1003","totalPrice":3}
         * */
        this.indexForEach(orders, (index, order) -> System.out.println(index + " -> " + JSON.toJSONString(order)));
    }

    /**拷贝一个Object多分，内存指向相同地址*/
    @Test
    public void  copyObject(){
        /**[{"id":1,"number":"1001","totalPrice":1},{"id":1,"number":"1001","totalPrice":1}]*/
        List<Order> copyList = Stream.generate(()-> orders.get(0)).limit(2).collect(Collectors.toList());
        copyList.get(0).setId(2);
        /**[{"id":2,"number":"1001","totalPrice":1},{"id":2,"number":"1001","totalPrice":1}]*/
        System.out.println(JSON.toJSONString(copyList,SerializerFeature.DisableCircularReferenceDetect));
    }


}


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class Order{
    private Integer id;
    private String number;
    private BigDecimal totalPrice;
}