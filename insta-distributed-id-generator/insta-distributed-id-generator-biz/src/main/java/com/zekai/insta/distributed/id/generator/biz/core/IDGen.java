package com.zekai.insta.distributed.id.generator.biz.core;


import cn.hutool.extra.tokenizer.Result;
import com.zekai.insta.distributed.id.generator.biz.core.IDGen.*;

public interface IDGen {
    Result get(String key);
    boolean init();
}
