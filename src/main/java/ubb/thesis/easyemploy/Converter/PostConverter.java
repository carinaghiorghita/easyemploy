package ubb.thesis.easyemploy.Converter;

import ubb.thesis.easyemploy.Domain.DTO.PostDto;
import ubb.thesis.easyemploy.Domain.DTO.UserDto;
import ubb.thesis.easyemploy.Domain.Entities.Post;
import ubb.thesis.easyemploy.Domain.Entities.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

public class PostConverter implements Converter<Post, PostDto> {

    @Override
    public PostDto convertModelToDto(Post model) {
        CompanyConverter companyConverter = new CompanyConverter();
        UserConverter userConverter = new UserConverter();

        Set<UserDto> applicants = new HashSet<>();
        model.getApplicants().forEach(user ->
                applicants.add(userConverter.convertModelToDto(user))
        );

        return new PostDto(model.getId(), model.getJobTitle(), model.getExperienceLevel(), model.getSalary(), model.getDescription(), model.getDateCreated().toString(),companyConverter.convertModelToDto(model.getCompany()),applicants);
    }

    @Override
    public Post convertDtoToModel(PostDto dto) throws ParseException {
        CompanyConverter companyConverter = new CompanyConverter();
        UserConverter userConverter = new UserConverter();
        final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");

        Set<User> applicants = new HashSet<>();
        dto.getApplicants().forEach(user ->
                applicants.add(userConverter.convertDtoToModel(user))
        );

        return new Post(dto.getId(), dto.getJobTitle(), dto.getExperienceLevel(), dto.getSalary(), dto.getDescription(), formatter.parse(dto.getDateCreated()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), companyConverter.convertDtoToModel(dto.getCompany()), applicants);
    }
}
