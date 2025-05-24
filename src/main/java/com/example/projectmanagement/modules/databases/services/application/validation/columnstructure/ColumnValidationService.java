package com.example.projectmanagement.modules.databases.services.application.validation.columnstructure;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.example.projectmanagement.modules.databases.datastructure.form.TableColumnRegisterForm;

@Service
public class ColumnValidationService {

	private final ColumnStructureValidatorFactory validatorFactory;

	public ColumnValidationService(ColumnStructureValidatorFactory validatorFactory) {
		this.validatorFactory = validatorFactory;
	}
	
	public void setFalseToNull(TableColumnRegisterForm form) {
		form.setIsAutoIncrement(Boolean.TRUE.equals(form.getIsAutoIncrement()));
		form.setIsForign(Boolean.TRUE.equals(form.getIsForign()));
		form.setIsNullable(Boolean.TRUE.equals(form.getIsNullable()));
		form.setIsPrimary(Boolean.TRUE.equals(form.getIsPrimary()));
		form.setIsUnique(Boolean.TRUE.equals(form.getIsUnique()));
	}

	
	
	public void validateForm(BindingResult result, String dbms, TableColumnRegisterForm column) {

	    ColumnStructureValidator validator = validatorFactory.getValidator(dbms);

	    if (!column.getDataType().isBlank() && !column.getDefaultValue().isBlank()) {
	        ColumnStructureValidationResult defResult = validator.validateDefaultValue(
	            column.getDataType(), column.getDefaultValue()
	        );
	        if (!defResult.isValid()) {
	            result.rejectValue(
	                defResult.getField(),
	                defResult.getMessageKey(),
	                defResult.getMessageArgs(),
	                null
	            );
	        }
	    }

	    ColumnStructureValidationResult fkDeleteResult = validator.validateFkConstraint(
	        column.getIsNullable(), column.getOnDelete(), "ON DELETE"
	    );
	    if (!fkDeleteResult.isValid()) {
	        result.rejectValue(
	            fkDeleteResult.getField(),
	            fkDeleteResult.getMessageKey(),
	            fkDeleteResult.getMessageArgs(),
	            null
	        );
	    }

	    ColumnStructureValidationResult fkUpdateResult = validator.validateFkConstraint(
	        column.getIsNullable(), column.getOnUpdate(), "ON UPDATE"
	    );
	    if (!fkUpdateResult.isValid()) {
	        result.rejectValue(
	            fkUpdateResult.getField(),
	            fkUpdateResult.getMessageKey(),
	            fkUpdateResult.getMessageArgs(),
	            null
	        );
	    }
	}


}
