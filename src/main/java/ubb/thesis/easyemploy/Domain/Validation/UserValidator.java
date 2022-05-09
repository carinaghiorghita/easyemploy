package ubb.thesis.easyemploy.Domain.Validation;

import ubb.thesis.easyemploy.Domain.Entities.User;
import ubb.thesis.easyemploy.Domain.Exceptions.ValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator extends BaseUserValidator {
    public void validateFirstName(User entity){
        var firstName = entity.getFirstName();
        if(firstName == null || firstName.equals(""))
            throw new ValidationException("First name is invalid!");

        if(!firstName.matches(nameRegex))
            throw new ValidationException("First name contains illegal characters!");
    }

    public void validateLastName(User entity){
        var lastName = entity.getLastName();
        if(lastName == null || lastName.equals(""))
            throw new ValidationException("Last name is invalid!");

        if(!lastName.matches(nameRegex))
            throw new ValidationException("Last name contains illegal characters!");
    }
}
