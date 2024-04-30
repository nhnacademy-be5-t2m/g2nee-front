function loginSubmit() {
    let usernameInput = document.getElementById("username");
    let passwordInput = document.getElementById("password");

    let username = usernameInput.value;
    let password = passwordInput.value;

    if (username === "") {
        Swal.fire({
            icon: 'warning',
            title: '아이디를 입력하여 주십시오.',
            text: '아이디를 입력하여 주십시오.'
        })
    } else if (password === "") {
        Swal.fire({
            icon: 'warning',
            title: '패스워드를 입력하여 주십시오.',
            text: '패스워드를 입력하여 주십시오.'
        })
    } else {
        $.ajax({
            type: "post",
            async: true,
            url: "/member/auth",
            data: {
                "username": username,
                "password": password
            },
            success: function (data, textStatus, request) {
                if (request.getResponseHeader("X-LOGIN") === null) {
                    Swal.fire({
                        icon: 'warning',
                        title: '로그인 실패',
                        text: '아이디나 패스워드가 일치하지 않습니다.'
                    })
                } else {
                    window.location.href = "/";
                }
            }
        })
    }
}