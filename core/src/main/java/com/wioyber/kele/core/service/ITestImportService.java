package com.wioyber.kele.core.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author cjg
 * @since 2023/2/11
 */
public interface ITestImportService {

    void testImport(MultipartFile file);

}
