package com.y3tu.yao.upms.utils;

import com.y3tu.tool.core.exception.BusinessException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * ClassName: BeforeCheckValidator
 * Description:
 * date: 2019/10/30 18:06
 *
 * @author zht
 */
public class BeforeValidator {
    private BindingResult bindingResult;

    public BeforeValidator(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public void validate() {
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            throw new BusinessException(allErrors.get(0).getDefaultMessage());
        }
    }

}
