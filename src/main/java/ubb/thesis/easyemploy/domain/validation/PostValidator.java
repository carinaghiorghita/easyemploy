package ubb.thesis.easyemploy.domain.validation;

import ubb.thesis.easyemploy.domain.entities.Post;
import ubb.thesis.easyemploy.domain.exceptions.ValidationException;

import java.util.Arrays;
import java.util.List;

public class PostValidator {
    public static final String TITLE_REGEX = "^(?U)[\\p{Alpha}\\-&\\. ]*";
    public static final List<String> VALID_EXPERIENCE_LEVELS = Arrays.asList("Internship", "Junior", "Mid-Level", "Senior", "Any");

    public void validateTitle(Post post){
        var title = post.getJobTitle();

        if(title==null || title.equals(""))
            throw new ValidationException("Job Title should not be empty!");

        if(!title.matches(TITLE_REGEX))
            throw new ValidationException("Job Title contains illegal characters!");
    }

    public void validateExperienceLevel(Post post){
        var experienceLevel = post.getExperienceLevel();
        if(!VALID_EXPERIENCE_LEVELS.contains(experienceLevel))
            throw new ValidationException("Experience Level is invalid!");
    }

    public void validateSalary(Post post){
        var salary = post.getSalary();

        if(salary==null)
            throw new ValidationException("Please enter salary");

        if(salary<300)
            throw new ValidationException("Please pay your employees a living wage");
    }
}
