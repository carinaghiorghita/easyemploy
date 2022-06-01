package ubb.thesis.easyemploy.Converter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ubb.thesis.easyemploy.Domain.DTO.PostExploreDto;
import ubb.thesis.easyemploy.Domain.Entities.Post;
import ubb.thesis.easyemploy.Service.JobApplicationService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;

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
        var date = dto.getDateCreated().equals("") ? LocalDateTime.now() : formatter.parse(dto.getDateCreated()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        var editedDate = dto.getDateLastEdited().equals("") ? LocalDateTime.now() : formatter.parse(dto.getDateCreated()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        return new Post(dto.getId(), dto.getJobTitle(), dto.getExperienceLevel(), dto.getSalary(), dto.getDescription(), date, editedDate, companyConverter.convertDtoToModel(dto.getCompany()));
    }
}
