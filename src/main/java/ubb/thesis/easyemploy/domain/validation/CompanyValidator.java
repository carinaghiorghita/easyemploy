package ubb.thesis.easyemploy.domain.validation;

import ubb.thesis.easyemploy.domain.entities.Company;
import ubb.thesis.easyemploy.domain.exceptions.ValidationException;

public class CompanyValidator extends BaseUserValidator{
    public void validateName(Company entity){
        var name = entity.getName();
        if(name == null || name.equals(""))
            throw new ValidationException("Name is invalid!");

        if(!name.matches(NAME_REGEX))
            throw new ValidationException("Name contains illegal characters!");
    }
}
