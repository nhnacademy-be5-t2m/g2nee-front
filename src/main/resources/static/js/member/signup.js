let usernameReg = /^[a-zA-Z0-9]{4,20}$|^[a-zA-Z0-9+-\_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/;
let usernameCheck = false;
let passwordReg = /^(?=.*?[A-Za-z])(?=.*?[0-9])(?=.*?[~?!@#$%^&*_-]).{8,20}$/;
let passwordCheck = false;
let nameReg = /^[a-zA-Z가-힣]{2,20}$/;
let nameCheck = false;
let nicknameReg = /^[a-zA-Z0-9가-힣+-\_.]{2,10}$/;
let nicknameCheck = false;

let phoneNumberReg = /^\d{3}-\d{3,4}-\d{4}$/;
let phoneNumberCheck = false;

let inputEmailId = document.querySelector('#emailId');
let inputEmailDomain = document.querySelector('#emailDomain');
let inputDate = document.querySelector('#date');

function setEmailDomain() {
    let selectElement = document.querySelector('#selectEmail');
    let selectedOption = selectElement.options[selectElement.selectedIndex].value;
    document.getElementById('emailDomain').value = selectedOption;
}

function validCheck(input, reg, message) {
    if (input.value.length !== 0) {
        if (reg.test(input.value) === false) {
            message.classList.remove('hide');
            return false;
        } else {
            message.classList.add('hide');
            return true;
        }
    } else {
        message.classList.add('hide');
        return false;
    }
}

let inputUsername = document.querySelector('#username');
let invalidUsername = document.querySelector('.username-invalid');
let usableUsername = document.querySelector('.username-usable');
let unUsableUsername = document.querySelector('.username-unUsable');

//username 중복 체크
inputUsername.onchange = function () {
    usernameCheck = validCheck(inputUsername, usernameReg, invalidUsername);
    checkName(inputUsername, invalidUsername, usableUsername, unUsableUsername, usernameReg, "아이디", "existsUsername")
        .then((result) => {
            usernameCheck = result;
            return result;
        });
}

async function checkName(checkTarget, regMessage, usableName, unUsableName, reg, targetType, path) {
    unUsableName.classList.add('hide');
    usableName.classList.add('hide');

    if (checkTarget.value.length !== 0) {
        if (reg.test(checkTarget.value) === false) {
            return false;
        }
    }

    try {
        const response = await fetch("/member/" + path, {
            method: 'POST',
            body: checkTarget.value
        });
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const data = await response.json();
        if (data === false) {
            usableName.classList.remove('hide');
            return true;
        } else {
            unUsableName.classList.remove('hide');
            return false;
        }
    } catch (error) {
        console.error('요청 실패:', error);
        return false;
    }
}

let inputPassword = document.querySelector('#password');
let invalidInputPassword = document.querySelector('.password-invalid');
let inputCheckPassword = document.querySelector('#passwordCheck');
let incorrectPassword = document.querySelector('.password-incorrect');

inputPassword.onchange = function () {
    passwordCheck = validCheck(inputPassword, passwordReg, invalidInputPassword);
    passwordEqCheck(inputPassword, inputCheckPassword)
}

inputCheckPassword.onchange = function () {
    passwordEqCheck(inputPassword, inputCheckPassword)
}

function passwordEqCheck(password, checkPassword) {
    if (checkPassword.value.length !== 0) {
        if (password.value === checkPassword.value) {
            incorrectPassword.classList.add('hide');
            passwordCheck = true;
        } else {
            incorrectPassword.classList.remove('hide');
            passwordCheck = false;
        }
    } else {
        incorrectPassword.classList.add('hide');
        passwordCheck = false;
    }
}


let inputName = document.querySelector('#name');
let invalidName = document.querySelector('.name-invalid');
inputName.onchange = function () {
    nameCheck = validCheck(inputName, nameReg, invalidName);
}

let inputNickname = document.querySelector('#nickname');
let invalidNickname = document.querySelector('.nickname-invalid');
let usableNickname = document.querySelector('.nickname-usable');
let unUsableNickname = document.querySelector('.nickname-unUsable');
//nickname 중복 체크
inputNickname.onchange = function () {
    nicknameCheck = validCheck(inputNickname, nicknameReg, invalidNickname);
    checkName(inputNickname, invalidNickname, usableNickname, unUsableNickname, nicknameReg, "닉네임", "existsNickname")
        .then((result) => {
            nicknameCheck = result;
            return result;
        });
}

let inputPhoneNumber = document.querySelector('#phoneNumber');
let invalidPhoneNumber = document.querySelector('.phoneNumber-invalid');
inputPhoneNumber.onchange = function () {
    phoneNumberCheck = validCheck(inputPhoneNumber, phoneNumberReg, invalidPhoneNumber);
}

function signupSubmit() {
    if (usernameCheck === false) {
        Swal.fire({
            icon: 'warning',
            title: '아이디 입력 오류',
            text: '아이디를 다시 입력하여 주십시오.'
        })
    } else if (passwordCheck === false) {
        Swal.fire({
            icon: 'warning',
            title: '비밀번호 입력 오류',
            text: '비밀번호를 다시 입력하여 주십시오.'
        })
    } else if (nameCheck === false) {
        Swal.fire({
            icon: 'warning',
            title: '이름 입력 오류',
            text: '이름을 다시 입력하여 주십시오.'
        })
    } else if (nicknameCheck === false) {
        Swal.fire({
            icon: 'warning',
            title: '닉네임 입력 오류',
            text: '닉네임을 다시 입력하여 주십시오.'
        })
    } else if (inputEmailId.value === "" || inputEmailDomain.value === "") {
        Swal.fire({
            icon: 'warning',
            title: 'email 입력 오류',
            text: 'email을 다시 입력하여 주십시오.'
        })
    } else if (phoneNumberCheck === false) {
        Swal.fire({
            icon: 'warning',
            title: '전화번호 입력 오류',
            text: '전화번호를 다시 입력하여 주십시오.'
        })
    } else if (inputDate.value === "") {
        Swal.fire({
            icon: 'warning',
            title: '생년월일 입력 오류',
            text: '생년월일을 입력하여 주십시오.'
        })
    } else {
        let email = inputEmailId.value + '@' + inputEmailDomain.value;
        document.querySelector('#email').value = email;

        let selectedDate = inputDate.value;
        let formattedDate = selectedDate.replace(/-/g, '');
        document.querySelector('#birthday').value = formattedDate;
        return true;
    }
    return false;

}