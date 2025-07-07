package com.zekai.insta.kv.biz.domain.repository;
import com.zekai.insta.kv.biz.domain.dataobject.NoteContentDO;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

/**
 * @author: ZeKai
 * @date: 2025/7/8
 * @description:
 **/
public interface NoteContentRepository  extends CassandraRepository<NoteContentDO, UUID>{
}
