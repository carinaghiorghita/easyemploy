package ubb.thesis.easyemploy.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ubb.thesis.easyemploy.Domain.Entities.ResponseMessage;

@RestController
public class FileController {

    @PostMapping(value="/api/upload")
    public ResponseEntity<ResponseMessage> upload(@RequestParam("file") MultipartFile file){
        System.out.println(file);
        var message = file.getOriginalFilename();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    }
}
