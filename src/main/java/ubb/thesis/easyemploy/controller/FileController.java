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
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class FileController {
    public static final String CV_PROCESSING_ERROR = "Could not process CV.";
    private final FileDBService fileDBService;

    @PostMapping(value = "/api/processCV")
    public ResponseEntity<ResponseMessage> processCV(@RequestParam("cv") MultipartFile cv) {
        try {
            File simpleFile = new File("src/main/resources/targetFile.tmp");

            try (OutputStream os = new FileOutputStream(simpleFile)) {
                os.write(cv.getBytes());
            }

            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            PDDocument document = PDDocument.load(simpleFile);

            String content = pdfTextStripper.getText(document);

            content = content.replace("\u00a0", " \r\n");
            content = content.replaceAll("([\\r\\n])", "");
            content = content.replaceAll("( )+", " ");
            content = content.trim();

            var split = content.split(" ");

            if (split.length < 2)
                return ResponseEntity
                        .status(HttpStatus.EXPECTATION_FAILED)
                        .body(new ResponseMessage(CV_PROCESSING_ERROR));

            var firstName = split[0];
            var lastName = split[1];

            var emailList = Arrays.stream(split)
                    .filter(word -> word.contains("@")).collect(Collectors.toList());

            if (emailList.isEmpty())
                return ResponseEntity
                        .status(HttpStatus.EXPECTATION_FAILED)
                        .body(new ResponseMessage(CV_PROCESSING_ERROR));
            var email = emailList.get(0);

            var phoneList = Arrays.stream(split)
                    .filter(word -> word.matches("[+#(0-9][+#)(0-9]{3,}[0-9)]")).collect(Collectors.toList());

            if (phoneList.isEmpty())
                return ResponseEntity
                        .status(HttpStatus.EXPECTATION_FAILED)
                        .body(new ResponseMessage(CV_PROCESSING_ERROR));
            var phone = phoneList.get(0);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(firstName + " " + lastName + " " + email + " " + phone));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(CV_PROCESSING_ERROR));
        }

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
