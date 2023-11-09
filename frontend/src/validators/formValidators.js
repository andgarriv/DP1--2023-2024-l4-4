export const formValidators = {
    notEmptyValidator: {
        validate: (value) => {
            return value.trim().length > 0;
        },
        message: "The field cannot be empty"
    },
    telephoneValidator: {
        validate: (value) => {
            return value.trim().length === 9 && /^\d+$/.test(value);
        },
        message: "The telephone number must be 9 digits long and contain only numbers"
    },
    notNoneTypeValidator: {
        validate: (value) => {
            return value !== "None";
        },
        message: "Please, select a type"
    },
    validPhoneNumberValidator: {
        validate: (value) => {
            return value.trim().length === 9 && /^\d+$/.test(value);
        },
        message: "The phone number must be 9 digits long and contain only numbers"
    },
    validDateValidator: {
        // Fecha anterior a la actual y mayor de 7 años
        validate: (value) => {
            const date = new Date(value);
            const today = new Date();
            const minDate = new Date();
            minDate.setFullYear(minDate.getFullYear() - 7);
            return date < today && date > minDate;
        },
        message: "The date must be before today and greater than 7 years"
    },


}