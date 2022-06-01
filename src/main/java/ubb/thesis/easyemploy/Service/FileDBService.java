package ubb.thesis.easyemploy.Service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ubb.thesis.easyemploy.Domain.Entities.FileDB;
import ubb.thesis.easyemploy.Repository.FileDBRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FileDBService {
    @Autowired
    private final FileDBRepository fileDBRepository;

    @Transactional
    public FileDB save(MultipartFile file, String user, Long post, boolean isCV) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB fileDB = new FileDB(fileName, file.getContentType(), file.getBytes(), user, post, isCV);
        if(isCV && fileDBRepository.existsFileDBByUsernameAndPostIdAndIsCV(user, post, true)) {
            this.fileDBRepository.deleteByUsernameAndPostIdAndIsCV(user, post, true);
        }
        else if(!isCV && fileDBRepository.existsFileDBByUsernameAndPostIdAndIsCV(user, post, false)) {
            this.fileDBRepository.deleteByUsernameAndPostIdAndIsCV(user, post, false);
        }
        return fileDBRepository.save(fileDB);
    }

    @Transactional
    public FileDB getById(Long id){
        return fileDBRepository.findById(id).get();
    }

    @Transactional
    public FileDB getCVByUsername(String username){
        return fileDBRepository.findAllByUsername(username)
                .stream()
                .filter(FileDB::isCV)
                .collect(Collectors.toList())
                .get(0);
    }

    @Transactional
    public FileDB getCLByUsername(String username){
        var coverLetters = fileDBRepository.findAllByUsername(username)
                .stream()
                .filter(fileDB -> !fileDB.isCV())
                .collect(Collectors.toList());
        if(coverLetters.size()==0)
            return null;
        return coverLetters.get(0);
    }

    @Transactional
    public void deleteByUsername(String username, Long post){
        fileDBRepository.deleteByUsernameAndPostId(username, post);
    }
}
