package com.runcoding.learn.concurrent.piped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * @author: runcoding
 * @email: runcoding@163.com
 * @created Time: 2018/6/28 18:14
 * @description Copyright (C), 2017-2018,
 **/
public class PipedTest {

    private static  Logger  LOGGER  = LoggerFactory.getLogger(PipedTest.class);

    public static void main(String[] args) throws IOException {
        piped();
    }


    public static void piped() throws IOException {
        //面向于字符 PipedInputStream 面向于字节
        PipedWriter writer = new PipedWriter();
        PipedReader reader = new PipedReader();
        //输入输出流建立连接
        writer.connect(reader);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("running");
                try {
                    for (int i = 0; i < 10; i++) {
                        writer.write(i+"");
                        Thread.sleep(10);
                    }
                } catch (Exception e) {
                } finally {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("running2");
                int msg = 0;
                try {
                    while ((msg = reader.read()) != -1) {
                        LOGGER.info("msg={}", (char) msg);
                    }
                } catch (Exception e) {
                }
            }
        });
        t1.start();
        t2.start();
    }


}
