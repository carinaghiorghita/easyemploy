package ubb.thesis.easyemploy.domain.dto;

@lombok.Data
@lombok.Builder
@lombok.AllArgsConstructor
public class FileDto {
    private String name;
    private String url;
    private String type;
    private long size;
}
