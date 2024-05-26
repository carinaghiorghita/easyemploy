package ubb.thesis.easyemploy.domain.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class JobApplicationKey implements Serializable {
    @Column(name = "user_id")
    Long userId;

    @Column(name = "post_id")
    Long postId;
}
