package ubb.thesis.easyemploy.Converter;

import ubb.thesis.easyemploy.Domain.DTO.CompanyDto;
import ubb.thesis.easyemploy.Domain.DTO.PostDto;
import ubb.thesis.easyemploy.Domain.DTO.UserDto;
import ubb.thesis.easyemploy.Domain.Entities.Company;
import ubb.thesis.easyemploy.Domain.Entities.User;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

public class UserConverter implements Converter<User, UserDto> {

    @Override
    public UserDto convertModelToDto(User model) {
        CompanyConverter companyConverter = new CompanyConverter();
        PostConverter postConverter = new PostConverter();

        Set<CompanyDto> followedCompanies = new HashSet<>();
        model.getFollowedCompanies().forEach(company ->
                followedCompanies.add(companyConverter.convertModelToDto(company))
        );

        Set<PostDto> jobs = new HashSet<>();
        model.getJobsApplied().forEach(post ->
                jobs.add(postConverter.convertModelToDto(post))
        );
        return new UserDto(model.getId(), model.getEmail(), model.getUsername(), model.getPassword(), model.getFirstName(), model.getLastName(), model.getPhoneNumber(), model.isActivated(), followedCompanies, jobs);
    }

    @Override
    public User convertDtoToModel(UserDto dto) {
        return new User(dto.getId(),dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getPhoneNumber(), dto.getUsername(), dto.getPassword(), dto.isActivated());
    }
}
