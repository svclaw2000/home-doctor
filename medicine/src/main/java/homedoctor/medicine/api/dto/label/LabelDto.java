package homedoctor.medicine.api.dto.label;

import homedoctor.medicine.domain.Label;
import homedoctor.medicine.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LabelDto {

    private Long id;

    private User user;

    private String title;

    private String color;
}
