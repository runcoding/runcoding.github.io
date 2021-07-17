package com.runcoding.learn.io;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.util.List;

/**
 * @author runcoding
 * @date 2019-07-12
 * @desc:
 */
@Slf4j
public class IOUtilsTest {
    @Test
    public void readTest() throws IOException {
        byte[] bytes = new byte[6];
        InputStream is = IOUtils.toInputStream("hello world");

        IOUtils.read(is, bytes);
        log.info("读取6个bytes = {}",new String(bytes));

        /**读取远程文件*/
        byte[] toByteArrayByUrl = IOUtils.toByteArray(new URL("https://www.baidu.com/"));
        String string = IOUtils.toString(new URL("https://www.baidu.com/"), "utf-8");

        /**读取本地文件*/
        byte[] toByteArrayByFile = IOUtils.toByteArray(new FileInputStream("/storage/logs/io.txt"));
        log.info("读取测试");
    }

    @Test
    public void writeLinesTest() throws IOException {
        List<String> lines = Lists.newArrayList("Java","Mysql");
        OutputStream os = new FileOutputStream("/storage/logs/io.txt");
        /**文件输出*/
        IOUtils.writeLines(lines,IOUtils.LINE_SEPARATOR,os);
    }

    @Test
    public void readLinesTest() throws IOException {
        InputStream is = new FileInputStream("/storage/logs/io.txt");
        List<String> lines = IOUtils.readLines(is);
        lines.forEach(line-> log.info("读取line={}",line));
    }

    @Test
    public void contentEqualsTest() throws IOException {
        InputStream is1 = IOUtils.toInputStream("java");
        InputStream is2 = IOUtils.toInputStream("java");
        log.info("比较is1 == is2 , {}",IOUtils.contentEquals(is1,is2));
    }

    @Test
    public void copyTest() throws IOException {
        InputStream is = IOUtils.toInputStream("java");
        OutputStream os = new FileOutputStream("/storage/logs/io2.txt");
        /**文件输出*/
        IOUtils.copy(is,os);

        InputStream isLarge = IOUtils.toInputStream("java");
        OutputStream osLarge = new FileOutputStream("/storage/logs/osLarge.txt");
        /**复制大文件几个G*/
        IOUtils.copyLarge(isLarge,osLarge);
    }
}
