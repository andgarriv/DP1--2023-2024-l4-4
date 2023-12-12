import { formValidators } from "../../../validators/formValidators";

export const loginFormInputs = [
  
 {
    tag: "Nickname",
    name: "username",
    type: "text",
    defaultValue: "",
    isRequired: true,
    validators: [formValidators.notBlankValidator],
    style: {
      borderRadius: "5px",
      padding: "10px",  
    },
  },
  {
    tag: "Password",
    name: "password",
    type: "password",
    defaultValue: "",
    isRequired: true,
  validators: [formValidators.notBlankValidator, formValidators.validPasswordValidator],
  },
];