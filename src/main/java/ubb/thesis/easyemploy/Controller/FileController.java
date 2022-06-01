package ubb.thesis.easyemploy.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ubb.thesis.easyemploy.Domain.DTO.FileDto;
import ubb.thesis.easyemploy.Domain.DTO.ResponseMessage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.pdmodel.PDDocument;
import ubb.thesis.easyemploy.Domain.Entities.FileDB;
import ubb.thesis.easyemploy.Service.FileDBService;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class FileController {
    private final FileDBService fileDBService;

    @PostMapping(value="/api/processCV")
    public ResponseEntity<ResponseMessage> processCV(@RequestParam("CV") MultipartFile CV){
        try {
            File simpleFile = new File("src/main/resources/targetFile.tmp");

            try (OutputStream os = new FileOutputStream(simpleFile)) {
                os.write(CV.getBytes());
            }
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            PDDocument document = PDDocument.load(simpleFile);

            String content = pdfTextStripper.getText(document);
            content = content.replace("\u00a0"," \r\n");
            content = content.replaceAll("(\\r|\\n)", "");
            content = content.replaceAll("( )+", " ");
            content = content.trim();

            var split = content.split(" ");

            if(split.length < 2)
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("Could not process CV."));

            var firstName = split[0];
            var lastName = split[1];

            var emailList = Arrays.stream(split).filter(word -> word.contains("@")).collect(Collectors.toList());
            if(emailList.size()<1)
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("Could not process CV."));
            var email = emailList.get(0);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(firstName+" "+lastName+" "+email));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("Could not process CV."));
        }

    }

    @PostMapping(value="/api/uploadFiles")
    public void upload(@RequestParam("CV") MultipartFile CV, @RequestParam("CL") MultipartFile CL, HttpSession httpSession){
        try {
            var user = (String) httpSession.getAttribute("username");

            fileDBService.save(CV, user, true);
            fileDBService.save(CL, user, false);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @PostMapping(value="/api/uploadFilesCV")
    public void uploadCV(@RequestParam("CV") MultipartFile CV, HttpSession httpSession){
        try {
            var user = (String) httpSession.getAttribute("username");

            fileDBService.save(CV, user, true);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @PostMapping(value="/api/uploadFilesCL")
    public void uploadCL(@RequestParam("CL") MultipartFile CL, HttpSession httpSession){
        try {
            var user = (String) httpSession.getAttribute("username");

            fileDBService.save(CL, user, false);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @GetMapping(value="/api/getFile")
    public FileDto getFile(@RequestParam("id") Long id) {
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/getFileBytes/")
                .path(id.toString())
                .toUriString();
        return new FileDto(
                "",
                fileDownloadUri,
                "",
                0);
    }

    @GetMapping("/api/getFileBytes/{id}")
    public ResponseEntity getFileBytes(@PathVariable String id) {
        FileDB fileDB = fileDBService.getById(Long.parseLong(id));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());    }
}
