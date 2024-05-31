package ubb.thesis.easyemploy.domain.dto;

@lombok.Setter
@lombok.Getter
public class ResponseMessage {
    private String message;

    public ResponseMessage(String message) {
        this.message = message;
    }

}
