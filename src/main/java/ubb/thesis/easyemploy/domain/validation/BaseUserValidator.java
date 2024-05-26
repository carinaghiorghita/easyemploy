package ubb.thesis.easyemploy.domain.validation;

import ubb.thesis.easyemploy.domain.entities.BaseUser;
import ubb.thesis.easyemploy.domain.exceptions.ValidationException;

public class BaseUserValidator {
    public static final String USERNAME_REGEX = "^[a-zA-Z0-9\\-_.]*";
    public static final String PHONE_NUMBER_REGEX = "^[0-9+#)( ]*";
    public static final String EMAIL_USERNAME_REGEX = "^[a-zA-Z0-9._+]+";
    public static final String EMAIL_DOMAIN_PROVIDER_REGEX = "^[a-zA-Z]+";
    public static final String EMAIL_DOMAIN_LOCATION_REGEX = "^[a-zA-Z]+";
    public static final String NAME_REGEX = "^(?U)[\\p{Alpha}\\-&. ]+";

    public void validateUsername(BaseUser entity){
        var username = entity.getUsername();

        if(username.length()<6)
            throw new ValidationException("Username must have at least 6 characters!");

        if(!username.matches(USERNAME_REGEX))
            throw new ValidationException("Username contains illegal character!");
    }

    public void validatePassword(String password){
        if(password.length()<6)
            throw new ValidationException("Password must have at least 6 characters!");
    }

    public void validatePhoneNumber(BaseUser entity){
        var phoneNumber = entity.getPhoneNumber();

        if(phoneNumber.length() < 10)
            throw new ValidationException("Phone number is too short");

        if(!phoneNumber.matches(PHONE_NUMBER_REGEX))
            throw new ValidationException("Phone number contains illegal character!");
    }

    public void validateEmail(BaseUser entity){
        var email = entity.getEmail();

        if(email.split("@").length!=2){
            throw new ValidationException("Email format is not valid.");
        }

        var emailUsername = email.split("@")[0];
        if(!emailUsername.matches(EMAIL_USERNAME_REGEX))
            throw new ValidationException("Email username contains illegal characters.");

        if(email.split("@")[1].split("\\.").length!=2){
            throw new ValidationException("Email format is not valid.");
        }

        var emailDomainProvider = email.split("@")[1].split("\\.")[0];
        if(!emailDomainProvider.matches(EMAIL_DOMAIN_PROVIDER_REGEX))
            throw new ValidationException("Email domain provider is invalid.");

        var emailDomainLocation = email.split("@")[1].split("\\.")[1];
        if(!emailDomainLocation.matches(EMAIL_DOMAIN_LOCATION_REGEX))
            throw new ValidationException("Email domain location is invalid.");
    }
}
