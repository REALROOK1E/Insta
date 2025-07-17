package com.zekai.insta.count.bizs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan("com.zekai.insta.count.bizs.domain.mapper")
public class InstaCountApp
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
