package ubb.thesis.easyemploy.controller;

import lombok.AllArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ubb.thesis.easyemploy.domain.dto.FileDto;
import ubb.thesis.easyemploy.domain.dto.ResponseMessage;
import ubb.thesis.easyemploy.domain.entities.FileDB;
import ubb.thesis.easyemploy.service.FileDBService;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@RestController
@AllArgsConstructor
public class FileController {
    private static final String CV_PROCESSING_ERROR = "Could not process CV.";
    private final FileDBService fileDBService;

    @PostMapping(value = "/api/processCV")
    public ResponseEntity<ResponseMessage> processCV(@RequestParam("cv") MultipartFile cv) {
        try {
            File simpleFile = new File("src/main/resources/targetFile.tmp");
            var content = getPDFContent(simpleFile);
            if (content.length < 2)
                return getResponseEntity(HttpStatus.EXPECTATION_FAILED, CV_PROCESSING_ERROR);

            return extractContent(content);
        } catch (Exception e) {
            return getResponseEntity(HttpStatus.EXPECTATION_FAILED, CV_PROCESSING_ERROR);
        }
    }

    private ResponseEntity<ResponseMessage> extractContent(String[] content) {

        var firstName = content[0];
        var lastName = content[1];
        var email = extractEmail(content);
        var phone = extractPhoneNumber(content);

        if (email == null || phone == null)
            return getResponseEntity(HttpStatus.EXPECTATION_FAILED, CV_PROCESSING_ERROR);

        return getResponseEntity(HttpStatus.OK, firstName + " " + lastName + " " + email + " " + phone);
    }

    private String extractEmail(String[] content) {
        return Arrays.stream(content)
                .filter(word -> word.contains("@"))
                .findFirst()
                .orElse(null);
    }

    private String extractPhoneNumber(String[] content) {
        return Arrays.stream(content)
                .filter(word -> word.matches("[+#(0-9][+#)(0-9]{3,}[0-9)]"))
                .findFirst()
                .orElse(null);
    }

    private static ResponseEntity<ResponseMessage> getResponseEntity(HttpStatus expectationFailed, String cvProcessingError) {
        return ResponseEntity
                .status(expectationFailed)
                .body(new ResponseMessage(cvProcessingError));
    }

    private String[] getPDFContent(File simpleFile) throws IOException {
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        PDDocument document = PDDocument.load(simpleFile);

        String content = pdfTextStripper.getText(document);

        content = content.replace("\u00a0", " \r\n");
        content = content.replaceAll("([\\r\\n])", "");
        content = content.replaceAll("( )+", " ");
        content = content.trim();
        return content.split(" ");
    }

    @PostMapping(value = "/api/uploadFiles")
    public void upload(@RequestParam("cv") MultipartFile cv,
                       @RequestParam("coverLetter") MultipartFile coverLetter,
                       @RequestParam("post") String post,
                       HttpSession httpSession) {
        try {
            var userId = (Long) httpSession.getAttribute("id");

            fileDBService.save(cv, userId, Long.parseLong(post), true);
            fileDBService.save(coverLetter, userId, Long.parseLong(post), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping(value = "/api/uploadFilesCV")
    public void uploadCV(@RequestParam("CV") MultipartFile cv,
                         @RequestParam("post") String post,
                         HttpSession httpSession) {
        try {
            var userId = (Long) httpSession.getAttribute("id");

            fileDBService.save(cv, userId, Long.parseLong(post), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping(value = "/api/uploadFilesCL")
    public void uploadCL(@RequestParam("coverLetter") MultipartFile coverLetter,
                         @RequestParam("post") String post,
                         HttpSession httpSession) {
        try {
            var userId = (Long) httpSession.getAttribute("id");

            fileDBService.save(coverLetter, userId, Long.parseLong(post), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "/api/getFile")
    public FileDto getFile(@RequestParam("id") Long id) {
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/getFileBytes/")
                .path(id.toString())
                .toUriString();
        return new FileDto(
                "",
                fileDownloadUri,
                "application/pdf",
                0);
    }

    @GetMapping("/api/getFileBytes/{id}")
    public ResponseEntity<byte[]> getFileBytes(@PathVariable String id) {
        FileDB fileDB = fileDBService.getById(Long.parseLong(id));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }
}
