package ubb.thesis.easyemploy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ubb.thesis.easyemploy.Domain.Entities.FileDB;

import java.util.List;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, Long> {
    List<FileDB> findAllByUsername(String username);
    boolean existsFileDBByUsernameAndIsCV(String username, boolean isCV);
    void deleteByUsernameAndIsCV(String username, boolean isCV);
}
