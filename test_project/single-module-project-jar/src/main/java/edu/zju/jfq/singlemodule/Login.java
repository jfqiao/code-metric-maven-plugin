package edu.zju.jfq.singlemodule;

/**
 * Created by fangqiao.jfq on 2017/7/12.
 */
public interface Login {
    void login(UserInfo info);

    boolean register(UserInfo info);

    void exit(UserInfo info);

    void logInfo(String logInfo);
}
