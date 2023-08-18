package com.bcsd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * @author bcsd
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class Application {
    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(Application.class, args);
        System.out.println("" +
                "    _______           _______  _______  _______  _______  _______ \n" +
                "   (  ____ \\|\\     /|(  ____ \\(  ____ \\(  ____ \\(  ____ \\(  ____ \\\n" +
                "   | (    \\/| )   ( || (    \\/| (    \\/| (    \\/| (    \\/| (    \\/\n" +
                "   | (_____ | |   | || |      | |      | (__    | (_____ | (_____ \n" +
                "   (_____  )| |   | || |      | |      |  __)   (_____  )(_____  )\n" +
                "         ) || |   | || |      | |      | (            ) |      ) |\n" +
                "   /\\____) || (___) || (____/\\| (____/\\| (____/\\/\\____) |/\\____) |\n" +
                "   \\_______)(_______)(_______/(_______/(_______/\\_______)\\_______)\n" +
                "                       (♥◠‿◠)ﾉﾞ  启动成功   ლ(´ڡ`ლ)ﾞ          ");
    }
}
