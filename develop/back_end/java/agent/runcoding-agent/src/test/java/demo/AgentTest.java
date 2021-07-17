package demo;

import java.util.ArrayList;
import java.util.List;

public class AgentTest {

    /**
     * https://www.jianshu.com/p/fe1448bf7d31
     * 步骤一:  编译生成代理文件
     * 步骤二:  add VM options: -javaagent:/Users/runcoding/projects/soft_develop/github/runcoding.github.io/develop/back_end/java/agent/runcoding-agent/target/my-agent.jar
     */
    public static void main(String[] args) throws Exception {
        boolean is = true;
        while (is) {
            List<Object> list = new ArrayList<Object>();
            list.add("hello world");
        }
    }
}
