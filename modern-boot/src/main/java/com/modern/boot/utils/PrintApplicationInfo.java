package com.modern.boot.utils;

import com.modernframework.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * <p>
 * 打印项目信息
 * </p>
 *
 * @date 2019-05-08
 **/
@Slf4j
public class PrintApplicationInfo {


    /**
     * 执行之前，打印前置条件提示
     */
    public static void printTip() {
        StringBuffer tip = new StringBuffer();
        tip.append("======================================================================================\n");
        tip.append("                                                                                  \n");
        tip.append("                               !!!准备工作!!!                                      \n");
//        tip.append(" 1.请先在MySQL中创建数据库，默认数据库名称为：spring_boot_plus                        \n");
//        tip.append(" 2.数据库脚本在项目docs/mysql_spring_boot_plus.sql                                       \n");
//        tip.append(" 3.请先启动redis服务                                                               \n");
//        tip.append(" 4.更多注意事项：请查看: https://springboot.plus                                                                                 \n");
        tip.append("                                                                                  \n");
        tip.append("======================================================================================\n");
        log.info("\n{}", tip);
    }

    /**
     * 启动成功之后，打印项目信息
     */
    public static void print(ConfigurableApplicationContext context) {
        ConfigurableEnvironment environment = context.getEnvironment();

        // 项目名称
        String projectFinalName = environment.getProperty("info.project-finalName");
        // 项目版本
        String projectVersion = environment.getProperty("info.project-version");
        // 项目profile
        String profileActive = environment.getProperty("spring.profiles.active");
        // 项目路径
        String contextPath = StringUtils.nullToDefault(environment.getProperty("server.servlet.context-path"), "");
        // 项目端口
        String port = environment.getProperty("server.port");

        log.info("projectFinalName : {}", projectFinalName);
        log.info("projectVersion : {}", projectVersion);
        log.info("profileActive : {}", profileActive);
        log.info("contextPath : {}", contextPath);
        log.info("port : {}", port);
        String startSuccess = "        )               )                                   \n" +
                "     ( /(    )  (    ( /(         (              (          \n" +
                " (   )\\())( /(  )(   )\\())  (    ))\\   (   (    ))\\ (   (   \n" +
                " )\\ (_))/ )(_))(()\\ (_))/   )\\  /((_)  )\\  )\\  /((_))\\  )\\  \n" +
                "((_)| |_ ((_)_  ((_)| |_   ((_)(_))(  ((_)((_)(_)) ((_)((_) \n" +
                "(_-<|  _|/ _` || '_||  _|  (_-<| || |/ _|/ _| / -_)(_-<(_-< \n" +
                "/__/ \\__|\\__,_||_|   \\__|  /__/ \\_,_|\\__|\\__| \\___|/__//__/ \n";

        String homeUrl = "http://" + IpUtil.getLocalhostIp() + ":" + port + contextPath;
//        String swaggerUrl = "http://" + IpUtil.getLocalhostIp() + ":" + port + contextPath + "docs";
        log.info("home:{}", homeUrl);
//        log.info("docs:{}", swaggerUrl);
        log.info("start success...........");
        log.info("\n{}", startSuccess);
    }

}
