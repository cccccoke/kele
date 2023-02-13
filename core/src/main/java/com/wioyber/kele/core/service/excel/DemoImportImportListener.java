package com.wioyber.kele.core.service.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.wioyber.kele.core.dao.TestImportDao;
import com.wioyber.kele.core.entity.po.TestImport;
import com.wioyber.kele.core.entity.vo.TestExcelVO;
import com.wioyber.kele.core.util.excel.listener.AbstractExcelImportListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author cjg
 * @since 2023/2/10
 */
@Component
public class DemoImportImportListener
        extends AbstractExcelImportListener<TestImport, TestExcelVO, TestImportDao> {

    @Resource
    private TestImportDao testImportDao;

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        this.checkHead(1, "test", headMap, context);
    }

    @Override
    protected TestImportDao getM() {
        return testImportDao;
    }

    @Override
    protected Supplier<TestImport> getP() {
        return TestImport::new;
    }
}
