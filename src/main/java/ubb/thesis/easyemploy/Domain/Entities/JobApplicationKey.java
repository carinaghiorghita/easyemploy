package ubb.thesis.easyemploy.Domain.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobApplicationKey implements Serializable {
    @Column(name = "user_id")
    Long userId;

    @Column(name = "post_id")
    Long postId;
}
