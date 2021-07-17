package com.runcoding.roaringbitmap;

import org.junit.Test;
import org.roaringbitmap.RoaringBitmap;

/**
 * @author runcoding
 * @date 2019-01-29
 * @desc: https://github.com/RoaringBitmap/RoaringBitmap
 * https://blog.csdn.net/yizishou/article/details/78342499
 */
public class RoaringBitmapTest {

    @Test
    public  void main() {
        RoaringBitmap rr = RoaringBitmap.bitmapOf(1,2,3,1000);
        RoaringBitmap rr2 = new RoaringBitmap();
        rr2.add(4000L,4255L);
        rr.select(3); // would return the third value or 1000
        rr.rank(2); // would return the rank of 2, which is index 1
        rr.contains(1000); // will return true
        rr.contains(7); // will return false

        RoaringBitmap rror = RoaringBitmap.or(rr, rr2);// new bitmap
        rr.or(rr2); //in-place computation
        boolean equals = rror.equals(rr);// true
        if(!equals) throw new RuntimeException("bug");
        // number of values stored?
        long cardinality = rr.getLongCardinality();
        System.out.println(cardinality);
        // a "forEach" is faster than this loop, but a loop is possible:
        for(int i : rr) {
            System.out.println(i);
        }
    }

}
