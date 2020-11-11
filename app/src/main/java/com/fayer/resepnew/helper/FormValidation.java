package com.fayer.resepnew.helper;

import android.widget.EditText;

public class FormValidation {

    public FormValidation(EditText input, String label, String rules) {
        String[] arrRules = rules.split(",");
        for (String val: arrRules) {
            if(val.equals("isEmpty")){
                if (ruleIsEmpty(input)) {
                    input.setError(label+" "+" harus di isi");
                    return;
                }
            }

            if (val.equals("trim")){
                input.setError(label+" "+" harus di isi");
                return;
            }
        }
    }

    public Boolean ruleIsEmptyTrim(EditText input){
        return input.getText().toString().trim().isEmpty();
    }

    public Boolean ruleIsEmpty(EditText input) {
        return input.getText().toString().isEmpty();
    }

}
