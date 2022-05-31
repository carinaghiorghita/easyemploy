package ubb.thesis.easyemploy.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ubb.thesis.easyemploy.Domain.Entities.ResponseMessage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
public class FileController {

    @PostMapping(value="/api/upload")
    public ResponseEntity<ResponseMessage> upload(@RequestParam("file") MultipartFile file){
        try {
            File simpleFile = new File("src/main/resources/targetFile.tmp");

            try (OutputStream os = new FileOutputStream(simpleFile)) {
                os.write(file.getBytes());
            }
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            PDDocument document = PDDocument.load(simpleFile);

            String content = pdfTextStripper.getText(document);
            content = content.replace("\u00a0"," \r\n");
            content = content.replaceAll("(\\r|\\n)", "");
            content = content.replaceAll("( )+", " ");
            content = content.trim();

            var split = content.split(" ");
            var firstName = split[0];
            var lastName = split[1];
            var email = Arrays.stream(split).filter(word -> word.contains("@")).collect(Collectors.toList()).get(0);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(firstName+" "+lastName+" "+email));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("Could not process file."));
        }
    }
}
