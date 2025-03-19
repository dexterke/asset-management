package com.mylab.assetmanagement.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BusinessException extends RuntimeException {

    private List<ErrorModel> errors = null;

    public BusinessException(List<ErrorModel> errors) {
        this.errors = new ArrayList<>(errors);
    }

    public List<ErrorModel> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    public void setErrors(List<ErrorModel> errors) {
        this.errors = new ArrayList<>(errors);
    }
}
