package indi.xm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @ProjectName: foodie-dev
 * @Package: indi.xm
 * @ClassName: Application
 * @Author: albert.fang
 * @Description: api启动类
 * @Date: 2021/9/13 15:38
 */
@SpringBootApplication
// 扫描 mybatis 通用 mapper 所在的包
@MapperScan(basePackages = "indi.xm.mapper")
// 指定项目启动时，扫描哪些包下的类
@ComponentScan(basePackages = {"indi.xm","org.n3r.idworker"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
