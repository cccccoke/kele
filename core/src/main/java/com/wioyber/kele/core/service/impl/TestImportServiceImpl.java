package com.wioyber.kele.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wioyber.kele.core.dao.TestImportDao;
import com.wioyber.kele.core.entity.po.TestImport;
import com.wioyber.kele.core.entity.vo.TestExcelVO;
import com.wioyber.kele.core.service.ITestImportService;
import com.wioyber.kele.core.service.excel.DemoImportImportListener;
import com.wioyber.kele.core.util.ExcelWriteUtil;
import com.wioyber.kele.core.support.excel.IEasyExcelImport;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author cjg
 * @since 2023/2/11
 */
@Service
public class TestImportServiceImpl implements ITestImportService, IEasyExcelImport<TestImport, TestImportDao> {

    @Resource
    private TestImportDao testImportDao;

    @Resource
    private DemoImportImportListener demoImportImportListener;

    @Override
    public void testImport(MultipartFile file) {
        this.readFile(file, TestExcelVO.class, demoImportImportListener, 1);
    }

    @Override
    public void testExport(HttpServletResponse response) {
        List<TestImport> testImports = testImportDao.selectList(Wrappers.lambdaQuery());
        List<TestExcelVO> testExcelVOS = BeanUtil.copyToList(testImports, TestExcelVO.class);
        ExcelWriteUtil.writeTemplate(response, "testWithTemplate", "/test.xlsx", TestExcelVO.class, testExcelVOS);
    }
}
