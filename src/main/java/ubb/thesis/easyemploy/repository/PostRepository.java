package ubb.thesis.easyemploy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ubb.thesis.easyemploy.domain.entities.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
