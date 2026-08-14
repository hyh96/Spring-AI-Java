package com.huang.enterpriseai.ai.chat.controller;

import com.huang.enterpriseai.ai.chat.service.AiVectorService;
import com.huang.enterpriseai.vo.ResultVo;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;

/**
 * @Author: huang
 * @Description: TODO
 * @DateTime: 2026/8/14 15:36
 **/
@RequestMapping("/api/ai/vector")
@RestController
public class AiVectorController {

    private final AiVectorService aiVectorService;


    public AiVectorController(AiVectorService aiVectorService){
        this.aiVectorService=aiVectorService;
    }


    @PostMapping("/add")
    public ResultVo add(){
        aiVectorService.add();
        return ResultVo.success();
    }

    @GetMapping("/search")
    public ResultVo search(@RequestParam String query){
        return ResultVo.success(aiVectorService.search(query));
    }

}
