package com.altair.eightstar.web.rest;

import com.altair.eightstar.util.FileUploadUtil;
import com.altair.eightstar.web.rest.errors.BadRequestAlertException;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import tech.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/api")
public class FileUploadResource {

    private static final String CLASS_NAME = "uploadFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @PostMapping("/upload")
    public ResponseEntity<Void> uploadFile(
        @RequestParam("image") MultipartFile multipartFile,
        @RequestParam("checkInId") Long checkInId,
        @RequestParam("category") String category
    ) throws IOException {
        /*  if (!category.equals("identity") || !category.equals("cleanRoom") || !category.equals("miniBar"))
        {
            throw new BadRequestAlertException("Invalid Category", CLASS_NAME, "CategoryInvalid");
        }*/

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        switch (category) {
            case "identity":
                FileUploadUtil.saveFile("identity/" + checkInId, fileName, multipartFile);
                break;
            case "cleanRoom":
                FileUploadUtil.saveFile("cleanRoom/" + checkInId, fileName, multipartFile);
                break;
            case "miniBar":
                FileUploadUtil.saveFile("miniBar/" + checkInId, fileName, multipartFile);
                break;
        }

        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, CLASS_NAME, checkInId.toString()))
            .build();
    }
}
