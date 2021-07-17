package com.runcoding.spel;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class SpelTest {
    public static void main(String[] args){  
        //创建解析器  
        ExpressionParser parser = new SpelExpressionParser();
        //解析表达式  
        Expression expression = parser.parseExpression("('Hello'+'World').concat(#end)");
        //构造上下文  
        EvaluationContext context = new StandardEvaluationContext();
        //为end参数值来赋值  
        context.setVariable("end","!");  
        //打印expression表达式的值  
        System.out.println(expression.getValue(context));  
  
    }  
}  