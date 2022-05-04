package ubb.thesis.easyemploy.Domain.Validation;

import ubb.thesis.easyemploy.Domain.Entities.Company;
import ubb.thesis.easyemploy.Domain.Exceptions.ValidationException;

public class CompanyValidator implements Validator<Company> {
    @Override
    public void validate(Company entity) throws ValidationException {
        if(entity.getId() == null)
            throw new ValidationException("ID should not be null");
    }
}
