package homedoctor.medicine.dto.user;

import lombok.Data;

@Data
public class CreateUserResponse {
    public Long id;

    public CreateUserResponse(Long id) {
        this.id = id;
    }
}
