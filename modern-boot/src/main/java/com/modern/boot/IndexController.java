
package com.modern.boot;

import com.modernframework.base.vo.Rs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 项目根路径提示信息
 * </p>
 *
 * @author <a href="mailto:brucezhang_jjz@163.com">zhangj</a>
 * @date 2018/11/12
 */
@RestController
@Slf4j
public class IndexController {

    @RequestMapping("/")
    public Rs<String> index() {
        return Rs.ok("modern boot...");
    }
}
