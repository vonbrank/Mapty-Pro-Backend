package cn.edu.hit.maptypro.response;


import lombok.Data;

import java.util.Date;

@Data
public class Response {
    private int code;
    private String description;
    private Date timestamp = new Date();
    private Object data;

    Response(int code, String description, Object data) {
        this.code = code;
        this.description = description;
        this.data = data;
    }
}
