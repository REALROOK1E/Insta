package com.zekai.insta.start.controller;
import com.zekai.framework.common.response.PageResponse;
import com.zekai.framework.oplog.aspect.ApiOperationLog;
import com.zekai.insta.start.model.vo.SearchNoteReqVO;
import com.zekai.insta.start.model.vo.SearchNoteRspVO;
import com.zekai.insta.start.sevice.NoteService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zekai
 * @date 2025/7/23
 * @Descripson :
 */

@RestController
@RequestMapping("/search")
@Slf4j
public class NoteController {

    @Resource
    private NoteService noteService;

    @PostMapping("/note")
    @ApiOperationLog(description = "搜索笔记")
    public PageResponse<SearchNoteRspVO> searchNote(@RequestBody @Validated SearchNoteReqVO searchNoteReqVO) {
        return noteService.searchNote(searchNoteReqVO);
    }

}