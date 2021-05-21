package com.learn.authenticate.modules.users.controller.validate;

import com.learn.authenticate.modules.users.model.entity.User;
import com.learn.authenticate.modules.users.model.service.UserService;
import com.learn.authenticate.utils.transientObject.ValidateObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidate {
    private UserService userService;

    @Autowired
    public UserValidate(UserService userService) {
        this.userService = userService;
    }

    public ValidateObject addNewItem(User user) {
        List<String> errorList = new ArrayList<>();
        ValidateObject validateObject = new ValidateObject();
        if (user == null) {
            errorList.add("Data must not be null");
        } else {
            if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                errorList.add("Email is required");
            } else if(this.userService.findByEmail(user.getEmail()) != null){
                errorList.add("Email is Exist");
            }else{
                String regex = "^(.+)@(.+)$";
                Pattern pattern = Pattern.compile(regex);

                Matcher matcher = pattern.matcher(user.getEmail());
                if (!matcher.matches()) {
                    errorList.add("Email format not correct");
                }
            }
            if(user.getFirstName() == null || user.getFirstName().trim().isEmpty()){
                errorList.add("firstname is required");
            }
            if(user.getLastName() == null || user.getLastName().trim().isEmpty()){
                errorList.add("lastName is required");
            }
            if(user.getPhoneNumber() == null || user.getPhoneNumber().trim().isEmpty()){
                errorList.add("phoneNumber is required");
            }else{
//                String regex = "^(\\\\+98|0)?9\\\\d{9}$";
                String regex =  "^(0|\\+98)?([ ]|,|-|[()]){0,2}9[0|1|2|3|4]([ ]|,|-|[()]){0,3}(?:[0-9]([ ]|,|-|[()]){0,2}){8}$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(user.getPhoneNumber());
                if (!matcher.matches()) {
                    errorList.add("phoneNumber format not correct");
                }
            }
        }

        validateObject.setCount(errorList.size());
        validateObject.setMessages(errorList);
        if (errorList.size() > 0) {
            validateObject.setResult("error");
        } else {
            validateObject.setResult("success");
        }

        return validateObject;
    }
}
