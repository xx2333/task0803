package com.lwy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lwy.entity.PageResult;
import com.lwy.entity.QueryPageBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.MemberService;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Reference
    private MemberService memberService;

    @RequestMapping("/findPage")
    public PageResult findPage(QueryPageBean queryPageBean) {
        return memberService.findPage(queryPageBean);
    }

}
