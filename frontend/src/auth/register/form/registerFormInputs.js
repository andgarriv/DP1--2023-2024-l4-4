import { formValidators } from "../../../validators/formValidators";

export const registerFormInputs = [
  {
    tag: "Name",
    name: "name",
    type: "text",
    defaultValue: "",
    isRequired: true,
    validators: [formValidators.notBlankValidator],
  },
  {
    tag: "Surname",
    name: "surname",
    type: "text",
    defaultValue: "",
    isRequired: true,
    validators: [formValidators.notBlankValidator],
  },
  {
    tag: "Nickname",
    name: "nickname",
    type: "text",
    defaultValue: "",
    isRequired: true,
    validators: [formValidators.notBlankValidator,
    formValidators.validNicknameValidator],
  },
  {
    tag: "Email",
    name: "email",
    type: "text",
    defaultValue: "",
    isRequired: true,
    validators: [formValidators.validEmailValidator],
  },
  {
    tag: "Password",
    name: "password",
    type: "password",
    defaultValue: "",
    isRequired: true,
    validators: [formValidators.notBlankValidator, formValidators.validPasswordValidator],
  },
  {
    tag: "Birth Date",
    name: "birthDate",
    type: "date",
    defaultValue: "",
    isRequired: true,
    validators: [formValidators.notNullValidator, formValidators.validDateValidator]
  },
  {
    tag: "Avatar",
    name: "avatar",
    type: "text",
    defaultValue: "https://cdn-icons-png.flaticon.com/512/5556/5556468.png",
    isRequired: false,
    validators: [formValidators.notBlankValidator],
  },
];
