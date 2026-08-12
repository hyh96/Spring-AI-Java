package com.huang.enterpriseai.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.huang.enterpriseai.constants.KnowledgeBaseStatus;
import com.huang.enterpriseai.dto.CreateKnowledgeBaseRequestDto;
import com.huang.enterpriseai.dto.KnowledgeBaseResponseDto;
import com.huang.enterpriseai.model.KnowledgeBaseEntity;
import com.huang.enterpriseai.repository.KnowledgeBaseDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * @Author: huang
 * @Description: TODO
 * @DateTime: 2026/8/10 15:58
 **/

public interface KnowledgeBaseService {

    KnowledgeBaseResponseDto create(CreateKnowledgeBaseRequestDto requestDto);

    List<KnowledgeBaseResponseDto> list();

    KnowledgeBaseResponseDto getById(String id);

    KnowledgeBaseResponseDto update(String id, CreateKnowledgeBaseRequestDto requestDto);

    public void delete(String id);
}
