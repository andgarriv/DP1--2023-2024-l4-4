import { formValidators } from "../../../validators/formValidators";

export const registerFormPlayerInputs = [
  {
    tag: "Name",
    name: "name",
    type: "text",
    defaultValue: "",
    isRequired: true,
    validators: [formValidators.notEmptyValidator],
  },
  {
    tag: "Surname",
    name: "surname",
    type: "text",
    defaultValue: "",
    isRequired: true,
    validators: [formValidators.notEmptyValidator],
  },
  {
    tag: "Password",
    name: "password",
    type: "password",
    defaultValue: "",
    isRequired: true,
    validators: [formValidators.notEmptyValidator],
  },
  {
    tag: "Birth Date",
    name: "birthDate",
    type: "date",
    defaultValue: "",
    isRequired: true,
    validators: [formValidators.notEmptyValidator && formValidators.validDateValidator]
  },
  {
    tag: "Nickname",
    name: "nickname",
    type: "text",
    defaultValue: "",
    isRequired: true,
    validators: [formValidators.notEmptyValidator],
  },
  {
    tag: "Avatar",
    name: "avatar",
    type: "text",
    defaultValue: "https://cdn-icons-png.flaticon.com/512/5556/5556468.png",
    isRequired: false,
    validators: [],
  },
];
