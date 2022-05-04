package ubb.thesis.easyemploy.Domain.Validation;

import ubb.thesis.easyemploy.Domain.Exceptions.ValidationException;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}
