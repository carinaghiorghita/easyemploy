package ubb.thesis.easyemploy.Converter;

import ubb.thesis.easyemploy.Domain.DTO.UserExploreDto;
import ubb.thesis.easyemploy.Domain.Entities.User;

import java.util.HashSet;

public class UserConverter implements Converter<User, UserExploreDto> {

    @Override
    public UserExploreDto convertModelToDto(User model) {
        CompanyConverter companyConverter = new CompanyConverter();
        PostConverter postConverter = new PostConverter();

//        Set<CompanyExploreDto> followedCompanies = new HashSet<>();
//        model.getFollowedCompanies().forEach(company ->
//                followedCompanies.add(companyConverter.convertModelToDto(company))
//        );
//
//        Set<PostExploreDto> jobs = new HashSet<>();
//        model.getJobsApplied().forEach(post ->
//                jobs.add(postConverter.convertModelToDto(post))
//        );
        return new UserExploreDto(model.getId(), model.getEmail(), model.getUsername(), model.getPassword(), model.getFirstName(), model.getLastName(), model.getPhoneNumber(), model.isActivated(), new HashSet<>(), new HashSet<>());
    }

    @Override
    public User convertDtoToModel(UserExploreDto dto) {
        return new User(dto.getId(),dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getPhoneNumber(), dto.getUsername(), dto.getPassword(), dto.isActivated());
    }
}
