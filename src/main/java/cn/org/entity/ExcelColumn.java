package cn.org.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Documented;

/**
 * 自定义实体类所需要的bean（Excel属性标题、位置等）
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelColumn {
    //Excel标题
    String value() default "";

    //Excel从左往右排列位置
    int col() default 0;
}
