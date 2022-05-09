package ubb.thesis.easyemploy.Domain.Validation;

import org.mindrot.jbcrypt.BCrypt;
import ubb.thesis.easyemploy.Domain.Entities.BaseUser;
import ubb.thesis.easyemploy.Domain.Exceptions.ValidationException;

public class BaseUserValidator {
    public final static String usernameRegex = "^[a-zA-Z0-9\\-_.]*";
    public final static String phoneNumberRegex = "^[0-9+#)( ]*";
    public final static String emailUsernameRegex = "^[a-zA-Z0-9._]+";
    public final static String emailDomainProviderRegex = "^[a-zA-Z]+";
    public final static String emailDomainLocationRegex = "^[a-zA-Z]+";
    public final static String nameRegex = "^[A-Z][a-zA-Z\\-&\\. ]*";

    public void validateId(BaseUser entity) throws ValidationException {
        if(entity.getId()==null || entity.getId()<0)
            throw new ValidationException("Invalid ID.");
    }

    public void validateUsername(BaseUser entity){
        var username = entity.getUsername();

        if(username.length()<6)
            throw new ValidationException("Username must have at least 6 characters!");

        if(!username.matches(usernameRegex))
            throw new ValidationException("Username contains illegal character!");
    }

    public void validatePhoneNumber(BaseUser entity){
        var phoneNumber = entity.getPhoneNumber();

        if(phoneNumber.length() < 10)
            throw new ValidationException("Phone number is too short");

        if(!phoneNumber.matches(phoneNumberRegex))
            throw new ValidationException("Phone number contains illegal character!");
    }

    public void validateEmail(BaseUser entity){
        var email = entity.getEmail();

        if(email.split("@").length!=2){
            throw new ValidationException("Email format is not valid.");
        }

        var emailUsername = email.split("@")[0];
        if(!emailUsername.matches(emailUsernameRegex))
            throw new ValidationException("Email username contains illegal characters.");

        if(email.split("@")[1].split("\\.").length!=2){
            throw new ValidationException("Email format is not valid.");
        }

        var emailDomainProvider = email.split("@")[1].split("\\.")[0];
        if(!emailDomainProvider.matches(emailDomainProviderRegex))
            throw new ValidationException("Email domain provider is invalid.");

        var emailDomainLocation = email.split("@")[1].split("\\.")[1];
        if(!emailDomainLocation.matches(emailDomainLocationRegex))
            throw new ValidationException("Email domain location is invalid.");
    }
}
