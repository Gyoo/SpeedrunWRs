package ooo.gyoo.speedrunwrs.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Gyoo on 18/06/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SRComResponse<T> {

    T data;

    public T getData() {
        return this.data;
    }

    public void setData(final T data) {
        this.data = data;
    }
}
