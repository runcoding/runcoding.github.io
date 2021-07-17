package com.runcoding.learn.java8.future;



import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 并行获取各个数据源的数据合并成一个数据组合
 * https://wangxin.io/2018/08/08/java/parallel_operation_by_completable_future/
 */
@Slf4j
public class ParallelTest {

    ThreadPoolExecutor  threadPoolExecutor = new ThreadPoolExecutor(
            3,
            6,
            1L, TimeUnit.MINUTES,
            new ArrayBlockingQueue<>(16),
            /**设置线程名称(方便定位问题)*/
            new ThreadFactory() {
                private final AtomicInteger mCount = new AtomicInteger(1);
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r,"Monitor-Test--" + mCount.getAndIncrement());
                }
            },
            /**拒绝策略使用当前运行线程执行，放弃异步执行使用同步*/
             new ThreadPoolExecutor.CallerRunsPolicy() );
    /**
     * 获取基本信息
     *
     * @return
     */
    public String getProductBaseInfo(String productId) {
        try {
            Thread.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return productId + "商品基础信息";
    }

    /**
     * 获取详情信息
     *
     * @return
     */
    public String getProductDetailInfo(String productId) {
        return productId + "商品详情信息";
    }

    /**
     * 获取sku信息
     *
     * @return
     */
    public String getProductSkuInfo(String productId) {
        return productId + "商品sku信息";
    }

    /**
     * 取得一个商品的所有信息（基础、详情、sku）
     *
     * @param productId
     * @return
     */
    public String getAllInfoByProductId(String productId) {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> getProductBaseInfo(productId),threadPoolExecutor);
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> getProductDetailInfo(productId),threadPoolExecutor);
        CompletableFuture<String> f3 = CompletableFuture.supplyAsync(() -> getProductSkuInfo(productId),threadPoolExecutor);
        //等待三个数据源都返回后，再组装数据。这里会有一个线程阻塞
        CompletableFuture.allOf(f1, f2, f3).join();
        try {
            String baseInfo = f1.get();
            String detailInfo = f2.get();
            String skuInfo = f3.get();
            return baseInfo + "" + detailInfo + skuInfo;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

   /**
    *   并行获取数据的计算
    */
    @Test
    public void testGetAllInfoParalleByProductId()  {
        long currentTimeMillis = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            String info = getAllInfoByProductId("1111");
        }
        log.info("并行获取数据的计算time={}",System.currentTimeMillis()-currentTimeMillis);

    }

   /**
    *   同步获取执行的处理
    */
    @Test
    public void testGetAllInfoDirectly() {
        long currentTimeMillis = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            String baseInfo = getProductBaseInfo("1111");
            String detailInfo = getProductDetailInfo("1111");
            String skuInfo = getProductSkuInfo("1111");
            String info = baseInfo + "" + detailInfo + skuInfo;
        }
        log.info("同步获取执行的处理,time={}",System.currentTimeMillis()-currentTimeMillis);
    }
}