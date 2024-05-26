package ubb.thesis.easyemploy.domain.validation;

import ubb.thesis.easyemploy.domain.entities.User;
import ubb.thesis.easyemploy.domain.exceptions.ValidationException;

public class UserValidator extends BaseUserValidator {
    public void validateFirstName(User entity) {
        var firstName = entity.getFirstName();
        if (firstName == null || firstName.isEmpty())
            throw new ValidationException("First name is invalid!");

        if (!firstName.matches(NAME_REGEX))
            throw new ValidationException("First name contains illegal characters!");
    }

    public void validateLastName(User entity) {
        var lastName = entity.getLastName();
        if (lastName == null || lastName.isEmpty())
            throw new ValidationException("Last name is invalid!");

        if (!lastName.matches(NAME_REGEX))
            throw new ValidationException("Last name contains illegal characters!");
    }
}
