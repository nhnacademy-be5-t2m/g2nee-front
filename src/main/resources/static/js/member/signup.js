let emptyReg = /\s/g;
let usernameReg = /^[a-zA-Z0-9]{4,20}$|^[a-zA-Z0-9+-\_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/;
let passwordReg = /^(?=.*?[A-Za-z])(?=.*?[0-9])(?=.*?[~?!@#$%^&*_-]).{8,20}$/;
let nameReg = /^[a-zA-Z가-힣]{2,20}$/;
let nicknameReg = /^[a-zA-Z0-9가-힣+-\_.]{2,10}$/;
let emailReg = /^[a-zA-Z0-9+-\_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/;
let phoneNumberReg = /^\d{3}-\d{3,4}-\d{4}$/;

function setEmailDomain() {
    let selectElement = document.querySelector('.select.text');
    let selectedOption = selectElement.options[selectElement.selectedIndex].value;
    document.getElementById('emailDomain').value = selectedOption;
}

function validCheck(input, reg, message) {
    if (input.value.length !== 0) {
        if (reg.test(input.value) === false) {
            message.classList.remove('hide');
        } else {
            message.classList.add('hide');
        }
    } else {
        message.classList.add('hide');
    }
}

let inputUsername = document.querySelector('#username');
let invalidUsername = document.querySelector('.username-invalid');
let usableUsername = document.querySelector('.username-usable');
let unUsableUsername = document.querySelector('.username-unUsable');

inputUsername.onkeyup = function () {
    validCheck(inputUsername, usernameReg, invalidUsername)
}

//username 중복 체크
inputUsername.onchange = function () {
    checkName(inputUsername, invalidUsername, usableUsername, unUsableUsername, usernameReg, "아이디", "existsUsername");
}


function checkName(checkTarget, regMessage, usableName, unUsableName, reg, targetType, path) {
    let url = 'http://localhost:8081/shop/member/' + path;

    if (checkTarget.value.length !== 0) {
        if (reg.test(checkTarget.value) === false) {
            return;
        }
    }
    unUsableName.classList.add('hide');
    usableName.classList.add('hide');
    fetch(url, {
        method: 'POST',
        body: checkTarget.value
    })
        .then(function (response) {
            return response.json();
        })
        .then(function (data) {
            if (data === true) {
                usableName.classList.remove('hide');
                unUsableName.classList.add('hide');
            } else {
                unUsableName.classList.remove('hide');
                usableName.classList.add('hide');
            }
        })
        .catch(function (error) {
            console.error('요청 실패:', error);
        });
}

let inputPassword = document.querySelector('#password');
let invalidInputPassword = document.querySelector('.password-invalid');

inputPassword.onkeyup = function () {
    validCheck(inputPassword, passwordReg, invalidInputPassword);
}
let inputCheckPassword = document.querySelector('#passwordCheck');
let incorrectPassword = document.querySelector('.password-incorrect');
inputCheckPassword.onkeyup = function () {
    if (inputCheckPassword.value.length !== 0) {
        if (inputPassword.value === inputCheckPassword.value) {
            incorrectPassword.classList.add('hide');
        } else {
            incorrectPassword.classList.remove('hide');
        }
    } else {
        incorrectPassword.classList.add('hide');
    }
}


let inputName = document.querySelector('#name');
let invalidName = document.querySelector('.name-invalid');
inputName.onkeyup = function () {
    validCheck(inputName, nameReg, invalidName);
}

let inputNickname = document.querySelector('#nickname');
let invalidNickname = document.querySelector('.nickname-invalid');
let usableNickname = document.querySelector('.nickname-Usable');
let unUsableNickname = document.querySelector('.nickname-unUsable');
inputNickname.onkeyup = function () {
    validCheck(inputNickname, nicknameReg, invalidNickname);
}
//nickname 중복 체크
inputNickname.onchange = function () {
    checkName(inputNickname, invalidNickname, usableNickname, unUsableNickname, nicknameReg, "닉네임", "existsNickname");
}
// let inputEmail = document.querySelector('#email');
// let invalidEmail = document.querySelector('.email-invalid');
// inputEmail.onkeyup = function () {
//     validCheck(inputEmail, emailReg, invalidEmail);
// }


let inputPhoneNumber = document.querySelector('#phoneNumber');
let invalidPhoneNumber = document.querySelector('.phoneNumber-invalid');
inputPhoneNumber.onkeyup = function () {
    validCheck(inputPhoneNumber, phoneNumberReg, invalidPhoneNumber);
}