package ubb.thesis.easyemploy.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FileDto {
    private String name;
    private String url;
    private String type;
    private long size;
}
