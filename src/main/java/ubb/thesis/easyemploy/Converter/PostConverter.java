package ubb.thesis.easyemploy.Converter;

import ubb.thesis.easyemploy.Domain.DTO.PostExploreDto;
import ubb.thesis.easyemploy.Domain.Entities.Post;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.HashSet;

public class PostConverter implements Converter<Post, PostExploreDto> {

    @Override
    public PostExploreDto convertModelToDto(Post model) {
        CompanyConverter companyConverter = new CompanyConverter();
        UserConverter userConverter = new UserConverter();

        return new PostExploreDto(model.getId(), model.getJobTitle(), model.getExperienceLevel(), model.getSalary(), model.getDescription(), model.getDateCreated().toString(),companyConverter.convertModelToDto(model.getCompany()),model.getApplicants().size());
    }

    @Override
    public Post convertDtoToModel(PostExploreDto dto) throws ParseException {
        CompanyConverter companyConverter = new CompanyConverter();
        UserConverter userConverter = new UserConverter();
        final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");

        return new Post(dto.getId(), dto.getJobTitle(), dto.getExperienceLevel(), dto.getSalary(), dto.getDescription(), formatter.parse(dto.getDateCreated()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), companyConverter.convertDtoToModel(dto.getCompany()), new HashSet<>());
    }
}
