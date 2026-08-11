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
@Service
public class KnowledgeBaseService {

    private final KnowledgeBaseDao knowledgeBaseDao;

    public KnowledgeBaseService(KnowledgeBaseDao knowledgeBaseDao) {
        this.knowledgeBaseDao = knowledgeBaseDao;
    }

    @Transactional
    public KnowledgeBaseResponseDto create(CreateKnowledgeBaseRequestDto requestDto) {

        KnowledgeBaseEntity entity = new KnowledgeBaseEntity();
        entity.setName(requestDto.name().trim());
        entity.setDescription(requestDto.description());
        entity.setStatus(KnowledgeBaseStatus.ENABLED);
        knowledgeBaseDao.insert(entity);
        return convertToResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<KnowledgeBaseResponseDto> list() {
        List<KnowledgeBaseEntity> entityList = knowledgeBaseDao.selectList(Wrappers.<KnowledgeBaseEntity>lambdaQuery().orderByDesc(KnowledgeBaseEntity::getCreatedAt));
        return entityList.stream()
                .map(this::convertToResponseDto)
                .toList();
    }

    private KnowledgeBaseResponseDto convertToResponseDto(
            KnowledgeBaseEntity entity) {

        return new KnowledgeBaseResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    @Transactional(readOnly = true)
    public KnowledgeBaseResponseDto getById(String id) {
        KnowledgeBaseEntity entity = knowledgeBaseDao.selectById(id);
        if (entity == null) {
            throw new NoSuchElementException("知识库不存在");
        }
        return convertToResponseDto(entity);
    }

    @Transactional
    public KnowledgeBaseResponseDto update(String id, CreateKnowledgeBaseRequestDto requestDto) {
        KnowledgeBaseEntity entity = knowledgeBaseDao.selectById(id);
        if (entity == null) {
            throw new NoSuchElementException("知识库不存在");
        }
        entity.setName(requestDto.name().trim());
        entity.setDescription(requestDto.description());
        knowledgeBaseDao.updateById(entity);
        return convertToResponseDto(entity);
    }

    @Transactional
    public void delete(String id) {
        int count = knowledgeBaseDao.deleteById(id);
        if (count == 0) {
            throw new NoSuchElementException("知识库不存在");
        }
    }
}