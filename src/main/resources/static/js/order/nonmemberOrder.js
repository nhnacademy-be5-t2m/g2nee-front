async function nonmemberOrder() {
    let orderId = document.querySelector('#orderId').value;
    let password = document.querySelector('#password').value;

    if (orderId === '') {
        Swal.fire({
            icon: 'warning',
            title: '아이디 입력 오류',
            text: '아이디를 다시 입력하여 주십시오.'
        })
        return false;
    } else if (password === '') {
        Swal.fire({
            icon: 'warning',
            title: '비밀번호 입력 오류',
            text: '비밀번호를 다시 입력하여 주십시오.'
        })
        return false;
    }
    let requestBody = {
        orderId: orderId,
        password: password
    };

    try {
        const response = await fetch('/member/nonMemberOrderCheck', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(requestBody),
        });

        const data = await response.text(); // 응답 데이터 파싱

        console.log(data);

        if (data === "SUCCESS") {
            const form = document.createElement('form');
            form.method = 'post';
            form.action = '/order/customer/orderDetail';

            const bookIdField = document.createElement('input');
            bookIdField.type = 'hidden';
            bookIdField.name = `orderId`;
            bookIdField.value = orderId;
            form.appendChild(bookIdField);

            const passwordField = document.createElement("input");
            passwordField.type = 'hidden';
            passwordField.name = `password`;
            passwordField.value = password;
            form.appendChild(passwordField);

            document.body.appendChild(form);
            form.submit();

            return true;
        } else {
            Swal.fire({
                icon: 'warning',
                title: '비회원 주문 조회 실패',
                text: data
            });
            return false;
        }
    } catch (error) {
        console.error('Error:', error);
        return false;
    }
}