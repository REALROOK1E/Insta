package com.zekai.insta.kv.biz;
import com.zekai.insta.kv.biz.domain.dataobject.NoteContentDO;
import com.zekai.insta.kv.biz.domain.repository.NoteContentRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

/**
 * @author: ZeKai
 * @date: 2025/7/8
 * @description:
 **/
@SpringBootTest(properties = {"spring.profiles.active=dev", "spring.data.cassandra.enabled=false"})
@Slf4j
class CassandraTests {

}
