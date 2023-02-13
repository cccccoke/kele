package com.wioyber.kele.core.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author cjg
 * @since 2023/2/11
 */
public interface ITestImportService {

    void testImport(MultipartFile file);

    void testExport(HttpServletResponse response);

}
