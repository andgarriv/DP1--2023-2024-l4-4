import { registerFormVetInputs } from "./registerFormVetInputs";

export const registerFormOwnerInputs = [
  ...registerFormVetInputs,
  {
    tag: "Address",
    name: "address",
    type: "text",
    defaultValue: "",
    isRequired: true,
    validators: [],
  },
  {
    tag: "Telephone",
    name: "telephone",
    type: "text",
    defaultValue: "",
    isRequired: true,
    validators: [],
  },
];
