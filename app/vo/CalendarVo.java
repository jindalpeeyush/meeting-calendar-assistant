package vo;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

import org.mongodb.morphia.annotations.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity("schedules")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CalendarVo {
    @JsonIgnore
	@Id
	public ObjectId _id;
	
	public int start_at;
	public int end_at;
	public String employee_id;
	public String meeting_id;
}
