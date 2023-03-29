package com.wioyber.kele.core.controller;

import com.wioyber.kele.core.annotation.SubmitLock;
import com.wioyber.kele.core.common.sys.Result;
import com.wioyber.kele.core.service.IAccountService;
import com.wioyber.kele.core.service.ITestImportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cjg
 * @since 2023/2/11
 */
@RestController
@RequestMapping("/import")
public class TestImportController {



    @Resource
    private ITestImportService iTestImportService;


    @Resource
    private IAccountService iAccountService;


    @GetMapping("/get")
    @SubmitLock(ttl = 2L)
    public Result<String> getOne() {
//        iAccountService.testException();
        return Result.success("aaa");
    }

    @GetMapping("/work")
    public Result<String> work() {
//        iAccountService.testException();
        return Result.success();
    }

    @PostMapping("/test")
    public Result<String> testImport(MultipartFile file){
        iTestImportService.testImport(file);
        return Result.success();
    }

    @PostMapping("/test/export")
    public void testExport(HttpServletResponse response){
        iTestImportService.testExport(response);
    }
}
