package ubb.thesis.easyemploy.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ubb.thesis.easyemploy.domain.entities.Company;
import ubb.thesis.easyemploy.domain.entities.Post;
import ubb.thesis.easyemploy.domain.entities.User;
import ubb.thesis.easyemploy.repository.PostRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

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
        user.getFollowedCompanies().forEach(company -> posts.addAll(this.getPostsForCompany(company)));

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
        postRepository.deleteById(post.getId());
    }

    @Transactional
    public void updatePost(Post post){
        postRepository.findById(post.getId())
                .ifPresent(p -> {
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
