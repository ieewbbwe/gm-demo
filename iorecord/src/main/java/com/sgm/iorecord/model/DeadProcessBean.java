package com.sgm.iorecord.model;

/**
 * Created by s2s8tb on 2019/10/16.
 * 进程正常一天不被杀-拿当天最后一条数据
 * 进程多次被杀-以被杀为节点，多比数据求和
 * 判断被杀：
 * 1.IProcessObserver，按照进程被杀时间点，查找被杀前最后一笔数据 （最精确方式）
 * 2.同样的包名，不一样的唯一标识--按照进程包名分组，将唯一标识不同的数据求和
 * 3.按包名查找，寻找包名相同PID不同的数据，分别找出不同PID下的最后一条数据，在求和 （有漏洞，万一PID被复用给同一个包名就可能漏掉一些数据
 */

public class DeadProcessBean {
    private String PID;
    private String PROCESS;
}
