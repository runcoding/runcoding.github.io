package com.runcoding.controller.lottery;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.runcoding.dto.Dlo;
import com.runcoding.dto.LotteryList;
import org.apache.commons.io.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.List;

@RestController
public class LotteryController {

    /**
     * https://github.com/MZCretin/RollToolsApi
     * @return
     */
    @RequestMapping(value = "/lottery",method = RequestMethod.GET)
    public String lottery(){

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<LotteryList> lotteryList = restTemplate.getForEntity("http://www.mxnzp.com/api/lottery/ssq/lottery_list?page=1", LotteryList.class);
        String jsonString = JSON.toJSONString(lotteryList);

        return jsonString;
    }

    /**
     *    一等奖：投注号码与当期开奖号码全部相同(顺序不限，下同)，即中奖;
     * 　　二等奖：投注号码与当期开奖号码中的五个前区号码及任意一个后区号码相同，即中奖;
     * 　　三等奖：投注号码与当期开奖号码中的五个前区号码相同，即中奖;
     * 　　四等奖：投注号码与当期开奖号码中的任意四个前区号码及两个后区号码相同，即中奖;
     */
    public static void main(String[] args) throws Exception {
        InputStream stencilsetStream = LotteryController.class.getClassLoader().getResourceAsStream("lottery/dlt.json");
        String toString = IOUtils.toString(stencilsetStream, "utf-8");
        List<Dlo> dlts = JSON.parseArray(toString, Dlo.class);
        List<Dlo> djList = Lists.newArrayList();

        dlts.forEach(dlt->{
            /**一等奖*/
            djList.add(dlt);
            /**三等奖*/
            Dlo thirdPrize = dlt.clone();
            thirdPrize(thirdPrize);
            thirdPrize.setLevel(3);
            djList.add(thirdPrize);

            /**二等奖*/
            Dlo secondPrize1 = thirdPrize.clone();
            secondPrize1.setLevel(2);
            Dlo secondPrize2 = thirdPrize.clone();
            secondPrize2.setLevel(2);
            secondPrize(dlt,secondPrize1,1);
            secondPrize(dlt,secondPrize2,2);
            djList.add(secondPrize1);
            djList.add(secondPrize2);

            /**四等奖*/
            for (int i = 0; i < 5; i++) {
                Dlo fourthPrize = dlt.clone();
                fourthPrize.setLevel(4);
                fourthPrize(fourthPrize,i+1);
                djList.add(fourthPrize);
            }

        });
        String lotteryData = JSON.toJSONString(djList);
        System.out.println(lotteryData);
    }

    private static void fourthPrize(Dlo fourthPrize,int num){
        int sum  = 0;
        if(fourthPrize.getR1() >0 && (++sum - num) == 0){ fourthPrize.setR1(0); return; }
        if(fourthPrize.getR2() >0 && (++sum - num) == 0){ fourthPrize.setR2(0); return; }
        if(fourthPrize.getR3() >0 && (++sum - num) == 0){ fourthPrize.setR3(0); return; }
        if(fourthPrize.getR4() >0 && (++sum - num) == 0){ fourthPrize.setR4(0); return; }
        if(fourthPrize.getR5() >0 && (++sum - num) == 0){ fourthPrize.setR5(0); return; }
        if(fourthPrize.getR6() >0 && (++sum - num) == 0){ fourthPrize.setR6(0); return; }
        if(fourthPrize.getR7() >0 && (++sum - num) == 0){ fourthPrize.setR7(0); return; }
        if(fourthPrize.getR8() >0 && (++sum - num) == 0){ fourthPrize.setR8(0); return; }
        if(fourthPrize.getR9() >0 && (++sum - num) == 0){ fourthPrize.setR9(0); return; }
        if(fourthPrize.getR10() >0 && (++sum - num) == 0){ fourthPrize.setR10(0); return; } 

        if(fourthPrize.getR11() >0 && (++sum - num) == 0){ fourthPrize.setR11(0); return; }
        if(fourthPrize.getR12() >0 && (++sum - num) == 0){ fourthPrize.setR12(0); return; }
        if(fourthPrize.getR13() >0 && (++sum - num) == 0){ fourthPrize.setR13(0); return; }
        if(fourthPrize.getR14() >0 && (++sum - num) == 0){ fourthPrize.setR14(0); return; }
        if(fourthPrize.getR15() >0 && (++sum - num) == 0){ fourthPrize.setR15(0); return; }
        if(fourthPrize.getR16() >0 && (++sum - num) == 0){ fourthPrize.setR16(0); return; }
        if(fourthPrize.getR17() >0 && (++sum - num) == 0){ fourthPrize.setR17(0); return; }
        if(fourthPrize.getR18() >0 && (++sum - num) == 0){ fourthPrize.setR18(0); return; }
        if(fourthPrize.getR19() >0 && (++sum - num) == 0){ fourthPrize.setR19(0); return; }
        if(fourthPrize.getR20() >0 && (++sum - num) == 0){ fourthPrize.setR20(0); return; } 

        if(fourthPrize.getR21() >0 && (++sum - num) == 0){ fourthPrize.setR21(0); return; }
        if(fourthPrize.getR22() >0 && (++sum - num) == 0){ fourthPrize.setR22(0); return; }
        if(fourthPrize.getR23() >0 && (++sum - num) == 0){ fourthPrize.setR23(0); return; }
        if(fourthPrize.getR24() >0 && (++sum - num) == 0){ fourthPrize.setR24(0); return; }
        if(fourthPrize.getR25() >0 && (++sum - num) == 0){ fourthPrize.setR25(0); return; }
        if(fourthPrize.getR26() >0 && (++sum - num) == 0){ fourthPrize.setR26(0); return; }
        if(fourthPrize.getR27() >0 && (++sum - num) == 0){ fourthPrize.setR27(0); return; }
        if(fourthPrize.getR28() >0 && (++sum - num) == 0){ fourthPrize.setR28(0); return; }
        if(fourthPrize.getR29() >0 && (++sum - num) == 0){ fourthPrize.setR29(0); return; }
        if(fourthPrize.getR30() >0 && (++sum - num) == 0){ fourthPrize.setR30(0); return; } 

        if(fourthPrize.getR31() >0 && (++sum - num) == 0){ fourthPrize.setR31(0); return; }
        if(fourthPrize.getR32() >0 && (++sum - num) == 0){ fourthPrize.setR32(0); return; }
        if(fourthPrize.getR33() >0 && (++sum - num) == 0){ fourthPrize.setR33(0); return; }
        if(fourthPrize.getR34() >0 && (++sum - num) == 0){ fourthPrize.setR34(0); return; }
        if(fourthPrize.getR35() >0 && (++sum - num) == 0){ fourthPrize.setR35(0); return; } 

        
    }

    private static void secondPrize(Dlo dlo,Dlo secondPrize,int num){
          if(dlo.getL1() >0 && --num  == 0){ secondPrize.setL1(dlo.getL1()); }
          if(dlo.getL2() >0 && --num  == 0){ secondPrize.setL2(dlo.getL2()); }
          if(dlo.getL3() >0 && --num  == 0){ secondPrize.setL3(dlo.getL3()); }
          if(dlo.getL4() >0 && --num  == 0){ secondPrize.setL4(dlo.getL4()); }
          if(dlo.getL5() >0 && --num  == 0){ secondPrize.setL5(dlo.getL5()); }
          if(dlo.getL6() >0 && --num  == 0){ secondPrize.setL6(dlo.getL6()); }
          if(dlo.getL7() >0 && --num  == 0){ secondPrize.setL7(dlo.getL7()); }
          if(dlo.getL8() >0 && --num  == 0){ secondPrize.setL8(dlo.getL8()); }
          if(dlo.getL9() >0 && --num  == 0){ secondPrize.setL9(dlo.getL9()); }
          if(dlo.getL10() >0 && --num  == 0){ secondPrize.setL10(dlo.getL10()); }
          if(dlo.getL11() >0 && --num  == 0){ secondPrize.setL11(dlo.getL11()); }
          if(dlo.getL12() >0 && --num  == 0){ secondPrize.setL12(dlo.getL12()); }

    }
    private static void thirdPrize(Dlo thirdPrize){
        thirdPrize.setL1(0);
        thirdPrize.setL2(0);
        thirdPrize.setL3(0);
        thirdPrize.setL4(0);
        thirdPrize.setL5(0);
        thirdPrize.setL6(0);
        thirdPrize.setL7(0);
        thirdPrize.setL8(0);
        thirdPrize.setL9(0);
        thirdPrize.setL10(0);
        thirdPrize.setL11(0);
        thirdPrize.setL12(0);
    }



}
