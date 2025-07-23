package com.zekai.insta.start.sevice;

import com.zekai.framework.common.response.PageResponse;
import com.zekai.insta.start.model.vo.SearchNoteReqVO;
import com.zekai.insta.start.model.vo.SearchNoteRspVO;

public interface NoteService {
    /**
     * 搜索笔记
     * @param searchNoteReqVO
     * @return
     */
    PageResponse<SearchNoteRspVO> searchNote(SearchNoteReqVO searchNoteReqVO);
}
