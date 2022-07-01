package ubb.thesis.easyemploy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ubb.thesis.easyemploy.Domain.Entities.FileDB;

import java.util.List;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, Long> {
    List<FileDB> findAllByUserId(Long userId);
    List<FileDB> findAllByUserIdAndPostId(Long userId, Long postId);
    FileDB findFileDBByUserIdAndPostIdAndIsCV(Long userId, Long postId, boolean isCV);
    boolean existsFileDBByUserIdAndPostIdAndIsCV(Long userId, Long postId, boolean isCV);
    void deleteByUserIdAndPostIdAndIsCV(Long userId, Long postId, boolean isCV);
    void deleteByUserIdAndPostId(Long userId, Long postId);
}
