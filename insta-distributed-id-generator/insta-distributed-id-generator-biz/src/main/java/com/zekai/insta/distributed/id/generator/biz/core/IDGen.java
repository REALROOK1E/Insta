package com.zekai.insta.distributed.id.generator.biz.core;


import com.zekai.insta.distributed.id.generator.biz.core.common.Result;

public interface IDGen {
    Result get(String key);
    boolean init();
}
