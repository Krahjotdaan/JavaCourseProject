package course_project.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemplatesAPI<T> {

    @JsonProperty("status")
    private int status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private T data;

    public TemplatesAPI(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}