package ubb.thesis.easyemploy.converter;

import lombok.AllArgsConstructor;
import ubb.thesis.easyemploy.domain.dto.PostExploreDto;
import ubb.thesis.easyemploy.domain.entities.Post;
import ubb.thesis.easyemploy.service.JobApplicationService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;

@AllArgsConstructor
public class PostConverter implements Converter<Post, PostExploreDto> {
    private final JobApplicationService jobApplicationService;

    @Override
    public PostExploreDto convertModelToDto(Post model) {
        CompanyConverter companyConverter = new CompanyConverter();

        return new PostExploreDto(model.getId(), model.getJobTitle(), model.getExperienceLevel(), model.getSalary(), model.getDescription(), model.getDateCreated().toString(),model.getDateLastEdited().toString(),companyConverter.convertModelToDto(model.getCompany()), this.jobApplicationService.getApplicantsForPost(model).size());
    }

    @Override
    public Post convertDtoToModel(PostExploreDto dto) throws ParseException {
        CompanyConverter companyConverter = new CompanyConverter();
        final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        var date = dto.getDateCreated().isEmpty() ? LocalDateTime.now() : formatter.parse(dto.getDateCreated()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        var editedDate = dto.getDateLastEdited().isEmpty() ? LocalDateTime.now() : formatter.parse(dto.getDateCreated()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        var builder = new Post.Builder();
        return builder
                .id(dto.getId())
                .jobTitle(dto.getJobTitle())
                .experienceLevel(dto.getExperienceLevel())
                .salary(dto.getSalary())
                .description(dto.getDescription())
                .dateCreated(date)
                .dateLastEdited(editedDate)
                .company(companyConverter.convertDtoToModel(dto.getCompany()))
                .build();
    }
}
