package web.mvc.hrm.service;

import web.mvc.hrm.web.form.EmployeeForm;
import org.apache.commons.validator.GenericValidator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static web.mvc.hrm.web.form.FormConstants.*;

public class ValidationService {

    public Map<String, List<String>> validate(EmployeeForm form) {
        Map<String, List<String>> errorMap = new HashMap<>();

        // validate id
        if(!GenericValidator.isBlankOrNull(form.getId())) {
            // valid long number
            // 1, MAX
            List<String> idErrors = new ArrayList<>();

            if(!GenericValidator.isLong(form.getId())) {
                idErrors.add("Id is not valid");
            }

            if(!idErrors.isEmpty()) {
                errorMap.put("id", idErrors);
            }
        }

        // validate first name
        List<String> firstNameErrors = new ArrayList<>();
        if(GenericValidator.isBlankOrNull(form.getFirstName())) {
           firstNameErrors.add("First name can not be empty");
        }

        if(!GenericValidator.isInRange(form.getFirstName().length(), FIRST_NAME_MIN_LENGTH, FIRST_NAME_MAX_LENGTH)) {
            firstNameErrors.add("First name can be min " + FIRST_NAME_MIN_LENGTH + " and max " + FIRST_NAME_MAX_LENGTH + " characters.");
        }

        // todo validate is alpha only


        if(!firstNameErrors.isEmpty()) {
            errorMap.put("firstName", firstNameErrors);
        }

        //////////////////////////////

        // validate last name
        List<String> lastNameErrors = new ArrayList<>();
        if(GenericValidator.isBlankOrNull(form.getLastName())) {
            lastNameErrors.add("Last name can not be empty");
        }

        if(!lastNameErrors.isEmpty()) {
            errorMap.put("lastName", lastNameErrors);
        }

        if(!GenericValidator.isInRange(form.getLastName().length(), LAST_NAME_MIN_LENGTH, LAST_NAME_MAX_LENGTH)) {
            lastNameErrors.add("Last name can be min " + LAST_NAME_MIN_LENGTH + " and max " + LAST_NAME_MAX_LENGTH + " characters.");
        }

        // todo validate is alpha only

        if(!lastNameErrors.isEmpty()) {
            errorMap.put("lastName", lastNameErrors);
        }

        /////////////////////

        // todo validate salary
        List<String> salaryErrors = new ArrayList<>();
        try {
            BigDecimal salary = new BigDecimal(form.getSalary());
        } catch (Exception e) {
            e.printStackTrace();
            salaryErrors.add("Maas sehvdir");
        }

        if(!salaryErrors.isEmpty()) {
            errorMap.put("salary", salaryErrors);
        }

        return errorMap;
    }
}
