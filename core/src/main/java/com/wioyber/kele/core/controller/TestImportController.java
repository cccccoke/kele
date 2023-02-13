package com.wioyber.kele.core.controller;

import com.wioyber.kele.core.common.sys.Result;
import com.wioyber.kele.core.service.ITestImportService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author cjg
 * @since 2023/2/11
 */
@RestController
@RequestMapping("/import")
public class TestImportController {

    @Resource
    private ITestImportService iTestImportService;


    @PostMapping("/test")
    public Result<String> testImport(MultipartFile file){
        iTestImportService.testImport(file);
        return Result.success();
    }
}
