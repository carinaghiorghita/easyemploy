package ubb.thesis.easyemploy.Domain.Validation;

import ubb.thesis.easyemploy.Domain.Entities.Company;
import ubb.thesis.easyemploy.Domain.Exceptions.ValidationException;

public class CompanyValidator extends BaseUserValidator{
    public void validateName(Company entity){
        var name = entity.getName();
        if(name == null || name.equals(""))
            throw new ValidationException("Name is invalid!");

        if(!name.matches(nameRegex))
            throw new ValidationException("Name contains illegal characters!");
    }
}
