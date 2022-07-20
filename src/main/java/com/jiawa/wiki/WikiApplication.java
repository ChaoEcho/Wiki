package com.jiawa.wiki;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class WikiApplication {

    private static final Logger LOG = LoggerFactory.getLogger(WikiApplication.class);

    public static void main(String[] args) {
        /**
         * 佛祖保佑项目正常运行
         */
        if(true){
            LOG.info(" \n......................阿弥陀佛......................\n" +
                    "                       _oo0oo_                      \n" +
                    "                      o8888888o                     \n" +
                    "                      88\" . \"88                     \n" +
                    "                      (| -_- |)                     \n" +
                    "                      0\\  =  /0                     \n" +
                    "                   ___/‘---’\\___                   \n" +
                    "                  .' \\|       |/ '.                 \n" +
                    "                 / \\\\|||  :  |||// \\                \n" +
                    "                / _||||| -卍-|||||_ \\               \n" +
                    "               |   | \\\\\\  -  /// |   |              \n" +
                    "               | \\_|  ''\\---/''  |_/ |              \n" +
                    "               \\  .-\\__  '-'  ___/-. /              \n" +
                    "             ___'. .'  /--.--\\  '. .'___            \n" +
                    "         .\"\" ‘<  ‘.___\\_<|>_/___.’>’ \"\".          \n" +
                    "       | | :  ‘- \\‘.;‘\\ _ /’;.’/ - ’ : | |        \n" +
                    "         \\  \\ ‘_.   \\_ __\\ /__ _/   .-’ /  /        \n" +
                    "    =====‘-.____‘.___ \\_____/___.-’___.-’=====     \n" +
                    "                       ‘=---=’                      \n" +
                    "                                                    \n" +
                    "....................佛祖保佑 ,永无BUG...................");
        }
        SpringApplication app = new SpringApplication(WikiApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("启动成功！！");
        LOG.info("地址: \thttp://127.0.0.1:{}", env.getProperty("server.port"));
    }

}
