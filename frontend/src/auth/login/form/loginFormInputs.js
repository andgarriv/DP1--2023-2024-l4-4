import { formValidators } from "../../../validators/formValidators";

export const loginFormInputs = [
  
 {
    tag: "Username",
    name: "username",
    type: "text",
    defaultValue: "",
    isRequired: true,
    validators: [formValidators.notEmptyValidator],
    style: {
      border: "1px solid #ccc",
      borderRadius: "5px",
      padding: "10px",
      /* Otros estilos personalizados según tus necesidades */
    },
  },
  {
    tag: "Password",
    name: "password",
    type: "password",
    defaultValue: "",
    isRequired: true,
    validators: [formValidators.notEmptyValidator],
  },
];