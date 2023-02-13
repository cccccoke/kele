package com.wioyber.kele.core.service.impl;

import com.wioyber.kele.core.dao.TestImportDao;
import com.wioyber.kele.core.entity.po.TestImport;
import com.wioyber.kele.core.entity.vo.TestImportVO;
import com.wioyber.kele.core.service.ITestImportService;
import com.wioyber.kele.core.service.excel.DemoImportImportListener;
import com.wioyber.kele.core.util.excel.IEasyExcelImport;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author cjg
 * @since 2023/2/11
 */
@Service
public class TestImportServiceImpl implements ITestImportService, IEasyExcelImport<TestImport, TestImportDao> {

    @Resource
    private DemoImportImportListener demoImportImportListener;

    @Override
    public void testImport(MultipartFile file) {
        this.importFile(file, TestImportVO.class, demoImportImportListener, 1);
    }
}
