export const formValidators = {
    notBlankValidator: {
        validate: (value) => {
            return value.trim().length > 0;
        },
        message: "The field cannot be empty"
    },
    notNullValidator: {
        validate: (value) => {
            return value !== null;
        },
        message: "The field cannot be null"
    },
    notNegativeValidator: {
        validate: (value) => {
            return value >= 0.;
        },
        message: "The field cannot be negative"
    },
    validEmailValidator: {
        validate: (value) => {
            const parts = value.split('@');
            return parts.length === 2 && parts[1].includes('.');
        },
        message: "The email must contain @ and a domain"
    },
    validDateValidator: {
        validate: (value) => {
            const inputDate = new Date(value);
            const today = new Date();
            const sevenYearsAgo = new Date();
            sevenYearsAgo.setFullYear(sevenYearsAgo.getFullYear() - 7);
            return inputDate <= today && inputDate <= sevenYearsAgo;
        },
        message: "The date must not be in the future and the user must be at least 7 years old"
    },
    validPasswordValidator: {
        validate: (value) => {
            const hasLowercase = /[a-z]/.test(value);
            const hasUppercase = /[A-Z]/.test(value);
            const hasNumber = /\d/.test(value);
            const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(value);
            const isLongEnough = value.length >= 5;
    
            return hasLowercase && hasUppercase && hasNumber && hasSpecialChar && isLongEnough;
        },
        message: "The password must be at least 5 characters long and include a lowercase and uppercase letter, a number, and a special character"
    },
    validNicknameValidator: {
        validate: (value) => {
            return value.trim().length >= 5 && value.trim().length <= 15;
        },
        message: "The nickname must be between 5 and 15 characters long"
    },
    validURLValidator: {
        validate: (value) => {
            return value.endsWith(".jpg") || value.endsWith(".jpeg") || value.endsWith(".png");
        },
        message: "The URL must end with .jpg, .jpeg, .png."
    },
}