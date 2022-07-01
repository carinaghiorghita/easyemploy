package ubb.thesis.easyemploy.Domain.Validation;

import ubb.thesis.easyemploy.Domain.Entities.Post;
import ubb.thesis.easyemploy.Domain.Exceptions.ValidationException;

import java.util.Arrays;
import java.util.List;

public class PostValidator {
    public final static String titleRegex = "^(?U)[\\p{Alpha}\\-&\\. ]*";
    public final static List<String> validExperienceLevels = Arrays.asList("Internship", "Junior", "Mid-Level", "Senior", "Any");

    public void validateTitle(Post post){
        var title = post.getJobTitle();

        if(title==null || title.equals(""))
            throw new ValidationException("Job Title should not be empty!");

        if(!title.matches(titleRegex))
            throw new ValidationException("Job Title contains illegal characters!");
    }

    public void validateExperienceLevel(Post post){
        var experienceLevel = post.getExperienceLevel();
        if(!validExperienceLevels.contains(experienceLevel))
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
