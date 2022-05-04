package ubb.thesis.easyemploy.Domain.Validation;

import ubb.thesis.easyemploy.Domain.Entities.User;
import ubb.thesis.easyemploy.Domain.Exceptions.ValidationException;

public class UserValidator implements Validator<User> {

    @Override
    public void validate(User entity) throws ValidationException {
        if(entity.getId() == null)
            throw new ValidationException("ID should not be null");
    }
}
