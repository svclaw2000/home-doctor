package homedoctor.medicine.api.dto.alarm.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DeleteAlarmResponse {

    private Long id;

    private String title;
}