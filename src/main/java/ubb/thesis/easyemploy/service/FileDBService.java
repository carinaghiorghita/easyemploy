package ubb.thesis.easyemploy.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ubb.thesis.easyemploy.domain.entities.FileDB;
import ubb.thesis.easyemploy.repository.FileDBRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FileDBService {
    @Autowired
    private final FileDBRepository fileDBRepository;

    @Transactional
    public FileDB save(MultipartFile file, Long user, Long post, boolean isCV) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        FileDB fileDB = new FileDB(fileName, file.getContentType(), file.getBytes(), user, post, isCV);
        if(isCV && fileDBRepository.existsFileDBByUserIdAndPostIdAndIsCV(user, post, true)) {
            this.fileDBRepository.deleteByUserIdAndPostIdAndIsCV(user, post, true);
        }
        else if(!isCV && fileDBRepository.existsFileDBByUserIdAndPostIdAndIsCV(user, post, false)) {
            this.fileDBRepository.deleteByUserIdAndPostIdAndIsCV(user, post, false);
        }
        return fileDBRepository.save(fileDB);
    }

    @Transactional
    public FileDB getById(Long id){
        return fileDBRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No file with this id"));
    }

    @Transactional
    public FileDB getCVByUser(Long userId, Long postId){
        return fileDBRepository.findAllByUserIdAndPostId(userId, postId)
                .stream()
                .filter(FileDB::isCV)
                .collect(Collectors.toList())
                .get(0);
    }

    @Transactional
    public FileDB getCLByUser(Long userId, Long postId){
        var coverLetters = fileDBRepository.findAllByUserIdAndPostId(userId, postId)
                .stream()
                .filter(fileDB -> !fileDB.isCV())
                .collect(Collectors.toList());
        if(coverLetters.isEmpty())
            return null;
        return coverLetters.get(0);
    }

    @Transactional
    public void deleteByUser(Long userId, Long post){
        fileDBRepository.deleteByUserIdAndPostId(userId, post);
    }

    @Transactional
    public Long getFileId(Long userId, Long post, boolean isCV){
        var file = fileDBRepository.findFileDBByUserIdAndPostIdAndIsCV(userId, post, isCV);
        if(file!=null)
            return file.getId();
        return -1L;
    }
}
