package com.runcoding.learn.buffer;

import java.nio.CharBuffer;

/**
 * @author runcoding
 * @desc 缓冲区 代码来源于 Java NOI.pdf
 * 2.1 缓冲区基础
 * 概念上，缓冲区是包在一个对象内的基本数据元素数组。Buffer 类相比一个简单数组的优点 是它将关于数据的数据内容和信息包含在一个单一的对象中。Buffer 类以及它专有的子类定义了 一个用于处理数据缓冲区的 API。
 * 2.1.1 属性
 * 所有的缓冲区都具有四个属性来提供关于其所包含的数据元素的信息。它们是:
 * 容量(Capacity)
 *    缓冲区能够容纳的数据元素的最大数量。这一容量在缓冲区创建时被设定，并且永远不能
 *  被改变。
 * 上界(Limit)
 * 缓冲区的第一个不能被读或写的元素。或者说，缓冲区中现存元素的计数。
 * 位置(Position)
 * 下一个要被读或写的元素的索引。位置会自动由相应的 get( )和 put( )函数更新。 标记(Mark)
 * 一个备忘位置。调用mark( )来设定mark=postion。调用reset( )设定position= mark。标记在设定前是未定义的(undefined)。
 *    这四个属性之间总是遵循以下关系:
 * 0 <= mark <= position <= limit <= capacity 让我们来看看这些属性在实际应用中的一些例子。图 2-2 展示了一个新创建的容量为 10
 * 的 ByteBuffer 逻辑视图。
 */
public class BufferFillDrain {

    public static void main(String[] argv) {
        /**创建一个缓冲区长度为100(如果定义的缓存区太下，会抛出BufferOverflowException 异常)*/
        CharBuffer buffer = CharBuffer.allocate(100);
        while (fillBuffer(buffer)) {
            /**将缓冲区由写入状态反转成可读取输出阶段。*/
            buffer.flip();
            drainBuffer(buffer);
            /**将缓冲区清空，注意数据没有清空，只是位置*/
            buffer.clear();
        }
    }

    /**排泄缓冲区中的数据*/
    private static void drainBuffer(CharBuffer buffer) {
        /**是否已经达到缓冲区的上界*/
        while (buffer.hasRemaining()) {
            /**读取当前位置的数据，并将position往后移动一位*/
            System.out.print(buffer.get());
        }
        System.out.println("");
    }

    /**填充缓冲区*/
    private static boolean fillBuffer(CharBuffer buffer) {
        if (index >= strings.length) {
            return (false);
        }
        String string = strings[index++];
        for (int i = 0; i < string.length(); i++) {
            buffer.put(string.charAt(i));
        }
        return (true);
    }

    private static int index = 0;
    private static String[] strings = {
            "A random string value",
            "The product of an infinite number of monkeys",
            "Hey hey we're the Monkees",
            "Opening act for the Monkees: Jimi Hendrix",
            "'Scuse me while I kiss this fly",
            "Help Me! Help Me!",
    };
}