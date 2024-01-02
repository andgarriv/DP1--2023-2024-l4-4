import { formValidators } from "../../../validators/formValidators";

export const registerFormPlayerInputs = [
  {
    tag: "Name",
    name: "name",
    type: "text",
    defaultValue: "",
    isRequired: true,
    validators: [formValidators.notBlankValidator],
  },
  {
    tag: "Description",
    name: "description",
    type: "text",
    defaultValue: "",
    isRequired: true,
    validators: [formValidators.notBlankValidator],
  },
  {
    tag: "badgeNotAchieved",
    name: "badgeNotAchieved",
    type: "text",
    defaultValue: "https://cdn-icons-png.flaticon.com/128/5730/5730459.png",
    isRequired: true,
    validators: [formValidators.notBlankValidator, formValidators.validURLValidator],
  },
  {
    tag: "badgeAchieved",
    name: "badgeAchieved",
    type: "text",
    defaultValue: "https://cdn-icons-png.flaticon.com/512/5778/5778223.png",
    isRequired: true,
    validators: [formValidators.notBlankValidator, formValidators.validURLValidator],
  },
  {
    tag: "Threshold",
    name: "threshold",
    type: "double",
    defaultValue: "",
    isRequired: true,
    validators: [formValidators.notBlankValidator, formValidators.notNegativeValidator],
  },
  {
    tag: "Category",
    name: "Category",
    type: "Category",
    defaultValue: "",
    isRequired: true,
    validators: [formValidators.notNullValidator]
  }
];
