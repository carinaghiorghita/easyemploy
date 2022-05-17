package ubb.thesis.easyemploy.Service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ubb.thesis.easyemploy.Domain.Entities.Post;
import ubb.thesis.easyemploy.Repository.PostRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {
    public static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private final PostRepository postRepository;

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    public List<Post> getPostsForCompany(Long id){
        return this.postRepository.findAll()
                .stream()
                .filter(p -> p.getCompany().getId().equals(id))
                .collect(Collectors.toList());
    }

    public Post getPostById(Long id){
        return this.postRepository.findAll()
                .stream()
                .filter(p -> p.getId().equals(id))
                .collect(Collectors.toList())
                .get(0);
    }

    public void savePost(Post post){
        postRepository.save(post);
    }

    public void deletePost(Post post){
        //todo add delete from users applications
        postRepository.deleteById(post.getId());
    }

    @Transactional
    public void updatePost(Post post){
        postRepository.findById(post.getId())
                .ifPresent(p -> {
                    p.setApplicants(post.getApplicants());
                    p.setCompany(post.getCompany());
                    p.setDateCreated(post.getDateCreated());
                    p.setDescription(post.getDescription());
                    p.setExperienceLevel(post.getExperienceLevel());
                    p.setJobTitle(post.getJobTitle());
                    p.setSalary(post.getSalary());
                });
    }
}
