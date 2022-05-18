package ubb.thesis.easyemploy.Service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ubb.thesis.easyemploy.Domain.Entities.Company;
import ubb.thesis.easyemploy.Domain.Entities.Post;
import ubb.thesis.easyemploy.Domain.Entities.User;
import ubb.thesis.easyemploy.Repository.PostRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {
    public static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserPostRelationService userPostRelationService;

    @Autowired
    private final PostRepository postRepository;

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    public List<Post> getPostsForCompany(Company company){
        return this.postRepository.findAll()
                .stream()
                .filter(p -> p.getCompany().equals(company))
                .collect(Collectors.toList());
    }

    public List<Post> getPostsFromFollowedCompanies(User user){
        List<Post> posts = new ArrayList<>();
        user.getFollowedCompanies().forEach(company -> {
            posts.addAll(this.getPostsForCompany(company));
        });

        return posts;
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
        //todo check if delete from users applications works lmao
        this.userPostRelationService.removeAllApplicants(post);
        postRepository.deleteById(post.getId());
    }

    @Transactional
    public void updatePost(Post post){
        postRepository.findById(post.getId())
                .ifPresent(p -> {
                    p.setApplicants(post.getApplicants());
                    p.setCompany(post.getCompany());
                    p.setDateCreated(post.getDateCreated());
                    p.setDateLastEdited(post.getDateLastEdited());
                    p.setDescription(post.getDescription());
                    p.setExperienceLevel(post.getExperienceLevel());
                    p.setJobTitle(post.getJobTitle());
                    p.setSalary(post.getSalary());
                });
    }
}
