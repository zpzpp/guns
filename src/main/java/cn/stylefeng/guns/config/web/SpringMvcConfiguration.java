package cn.stylefeng.guns.config.web;

import cn.stylefeng.guns.core.error.CustomErrorAttributes;
import cn.stylefeng.roses.kernel.security.cors.CorsFilterConfiguration;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * spring mvc的配置
 *
 * @author fengshuonan
 * @date 2020/4/11 10:23
 */
@Configuration
@Import({cn.hutool.extra.spring.SpringUtil.class, CorsFilterConfiguration.class})
public class SpringMvcConfiguration implements WebMvcConfigurer {

    /**
     * 重写系统的默认错误提示
     *
     * @author fengshuonan
     * @date 2020/12/16 15:36
     */
    @Bean
    public CustomErrorAttributes gunsErrorAttributes() {
        return new CustomErrorAttributes();
    }

    /**
     * json自定义序列化工具,long转string
     *
     * @author fengshuonan
     * @date 2020/12/13 17:16
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance).serializerByType(Long.TYPE, ToStringSerializer.instance);
    }

    /**
     * 静态资源映射
     *
     * @author fengshuonan
     * @date 2021/1/16 21:45
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");

        // 流程设计器
        registry.addResourceHandler("/designer/**").addResourceLocations("classpath:/designer/");
    }

}
