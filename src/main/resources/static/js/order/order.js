function setDeliveryMessage() {
    let selectElement = document.querySelector('#selectMessage');
    let selectedOption = selectElement.options[selectElement.selectedIndex].value;
    let etcMessage = document.getElementById('etcMessage');
    let messageInput = document.getElementById('message');

    if (selectedOption === 'etc') {
        etcMessage.style.display = 'block';
        // etc 선택 시 textarea의 값을 message input에 설정
        messageInput.value = etcMessage.value;
    } else {
        etcMessage.style.display = 'none';
        // etc 이외의 경우 선택된 옵션 값을 message input에 설정
        messageInput.value = selectedOption;
    }
}

function setEmailDomain() {
    let selectElement = document.querySelector('#selectEmail');
    let selectedOption = selectElement.options[selectElement.selectedIndex].value;
    document.getElementById('emailDomain').value = selectedOption;
}


function setEtcMessage() {
    let messageInput = document.getElementById('message');
    messageInput.value = document.getElementById('etcMessage').value;
}

function packagePopUp(button) {
    let id = button.parentElement.parentElement.id;
    window.open("/order/selectPackage/" + id, "_blank", "width=600, height=700,top=200px,left=200px;");
}

function setPackageId(radio) {
    document.querySelector('#selectPackageId').value = radio.parentNode.querySelector('#packageId').value;
    document.querySelector('#selectPackagePrice').value = radio.parentNode.querySelector('#packagePrice').value;
}

function setPackage() {
    let id = document.getElementById('id').value;

    //기존의 해당 index의 package가격
    let originPackagePrice = parseInt(opener.document.getElementsByClassName('packagePrice')[parseInt(id)].textContent);

    //선택한 값 해당 order에 넣기
    let packageId = parseInt(document.querySelector('#selectPackageId').value);
    let packagePrice = parseInt(document.querySelector('#selectPackagePrice').value);
    opener.document.getElementsByClassName('packageId')[parseInt(id)].value = packageId;
    opener.document.getElementsByClassName('packagePrice')[parseInt(id)].textContent = String(packagePrice);

    //package값 reset 후 update
    textPriceReset(originPackagePrice, opener.document.querySelector('#totalPackagePrice'), "plus");
    textPriceUpdate(packagePrice, opener.document.querySelector('#totalPackagePrice'), "plus");

    setTotalPriceFromPopUp();
    window.close();
}

function setWishDate(string) {
    document.getElementById('inputDeliveryWishDate').value = string;
}

function setTotalPriceFromPopUp() {
    let finalTotalOriginPrice = parseInt(opener.document.querySelector('#finalTotalOriginPrice').textContent);
    let totalPackagePrice = parseInt(opener.document.querySelector('#totalPackagePrice').textContent);
    let totalBookSale = parseInt(opener.document.querySelector('#totalBookSale').textContent);
    let middleTotalSale = finalTotalOriginPrice + totalPackagePrice - totalBookSale;
    if (opener.document.querySelector('#totalPointSale') !== null) {
        let totalCouponSale = parseInt(opener.document.querySelector('#totalCouponSale').textContent);
        middleTotalSale = finalTotalOriginPrice + totalPackagePrice - totalBookSale - totalCouponSale;
    }

    let freeDeliveryStandard = opener.document.querySelector('#freeDeliveryStandard').value;
    if (middleTotalSale < freeDeliveryStandard) {
        opener.document.querySelector('#deliveryFee').textContent = String(opener.document.querySelector('#deliveryFeePolicy').value);
        middleTotalSale = middleTotalSale + parseInt(opener.document.querySelector('#deliveryFeePolicy').value);
    } else {
        opener.document.querySelector('#deliveryFee').textContent = String(0);
    }

    let finalTotalSalePrice = middleTotalSale;
    if (opener.document.querySelector('#totalPointSale') !== null) {
        let originPoint = opener.document.querySelector('#quantity').value;
        if (middleTotalSale < originPoint) {
            originPoint = middleTotalSale;
            opener.document.querySelector('#quantity').value = originPoint;
            opener.document.getElementById('totalPointSale').textContent = String(originPoint);
        }
        finalTotalSalePrice = finalTotalSalePrice - originPoint;
    }

    opener.document.querySelector('#finalTotalSalePrice').textContent = "";
    opener.document.querySelector('#finalTotalSalePrice').textContent = String(finalTotalSalePrice);
}

function textPriceUpdate(updatePrice, totalPrice, operand) {
    let originToTalPrice = parseInt(totalPrice.textContent);
    if (operand === 'plus') {
        totalPrice.textContent = '';
        totalPrice.textContent = String(originToTalPrice + updatePrice);
    } else {
        totalPrice.textContent = '';
        totalPrice.textContent = String(originToTalPrice - updatePrice);
    }
}


function textPriceReset(priceElement, totalPrice, operand) {
    let originTotalPrice = parseInt(totalPrice.textContent);
    if (operand === "minus") {
        totalPrice.textContent = '';
        totalPrice.textContent = String(originTotalPrice + priceElement);
    } else {
        totalPrice.textContent = '';
        totalPrice.textContent = String(originTotalPrice - priceElement);
    }
}


function couponPopUp() {

}

function pointPopUp() {

}

function fullCouponPopUp() {

}

function buyNowButton() {
    let bookId = document.getElementById('bookId').value;
    let bookCount = document.getElementById('bookCount').value;
    let url = '/order/buyNow?bookId=' + bookId + '&bookCount=' + bookCount;
    window.location.href = url;
};

function handleDeliveryDateSelection() {
    // 선택된 라디오 버튼의 값 가져오기
    let selectedValue = document.querySelector('input[name="deliveryWishDate"]:checked').value;
    // 선택된 값이 "select"이면 날짜 입력 필드를 보이게 하고, 아니면 숨김
    let dateInputField = document.getElementById('wishDateView');
    if (selectedValue === 'select') {
        dateInputField.style.display = 'block';
    } else {
        dateInputField.style.display = 'none';
    }
}

// 라디오 버튼의 상태가 변할 때 위 함수를 호출하도록 설정
document.querySelectorAll('input[name="deliveryWishDate"]').forEach(function (radio) {
    radio.addEventListener('change', handleDeliveryDateSelection);
});

function postAddress() {
    new daum.Postcode({
        oncomplete: function (data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.roadAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if (data.userSelectedType === 'R') {
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                document.getElementById("address").value = extraAddr;

            } else {
                document.getElementById("address").value = '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('zipcode').value = data.zonecode;
            document.getElementById("address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("detail").focus();
        }
    }).open();
}


function pointChange() {
    document.getElementById('finalTotalSalePrice').textContent
        = String(parseInt(document.getElementById('finalTotalSalePrice').textContent) + parseInt(document.getElementById('totalPointSale').textContent))
    document.getElementById('totalPointSale').textContent = "0";

    let point = Number(document.getElementById('quantity').value);
    let maxPoint = document.getElementById('totalPoint').value;
    let finalTotalSalePrice = parseInt(document.getElementById('finalTotalSalePrice').textContent);
    if (point < 0) {
        document.getElementById('quantity').value = 0;
        return;
    }
    if (point >= maxPoint) {
        point = maxPoint;
    }
    ;
    if (finalTotalSalePrice - point < 0) {
        point = finalTotalSalePrice;
    }
    ;
    document.getElementById('totalPointSale').textContent = String(point);
    document.getElementById('quantity').value = point;
    document.getElementById('finalTotalSalePrice').textContent
        = String(parseInt(document.getElementById('finalTotalSalePrice').textContent) - point);
}

let passwordReg = /^(?=.*?[A-Za-z])(?=.*?[0-9])(?=.*?[~?!@#$%^&*_-]).{8,20}$/;
let nameReg = /^[a-zA-Z가-힣]{2,20}$/;
let emailReg = /^[a-zA-Z0-9+-\_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/;
let phoneNumberReg = /^\d{3}-\d{3,4}-\d{4}$/;

function submitOrderForm() {
    const form = document.createElement('form');
    form.method = 'post';
    form.action = '/order/payment';

    const receiverName = String(document.getElementById('name').value);
    if (validCheck(receiverName, nameReg) === false) {
        Swal.fire({
            icon: 'warning',
            title: '이름 입력 오류',
            text: '이름을 다시 입력하여 주십시오.'
        })
        return;
    }
    const receiverPhoneNumber = String(document.getElementById('phoneNumber').value);
    if (validCheck(receiverPhoneNumber, phoneNumberReg) === false) {
        Swal.fire({
            icon: 'warning',
            title: '전화번호 입력 오류',
            text: '전화번호를 다시 입력하여 주십시오.'
        })
        return;
    }
    const receiverEmail = String(document.getElementById('emailId').value) + '@' + String(document.getElementById('emailDomain').value);
    if (validCheck(receiverEmail, emailReg) === false) {
        Swal.fire({
            icon: 'warning',
            title: '이메일 입력 오류',
            text: '이메일을 다시 입력하여 주십시오.'
        })
        return;
    }

    const receiveAddress = String(document.getElementById('address').value);
    const zipcode = String(document.getElementById('zipcode').value);
    const detailAddress = String(document.getElementById('detail').value);
    if (receiveAddress === '' || zipcode === '' || detailAddress === '') {
        Swal.fire({
            icon: 'warning',
            title: '주소정보 입력 오류',
            text: '주소정보를 다시 입력하여 주십시오.'
        })
        return;
    }

    const orderDetails = document.querySelectorAll('.orderDetail');
    orderDetails.forEach((orderDetail, index) => {
        const bookId = orderDetail.querySelector('#bookId').value;
        const price = orderDetail.querySelector('#bookFinalPrice').textContent;
        const quantity = orderDetail.querySelector('#bookCount').textContent;
        const packageId = orderDetail.querySelector('#packageId').value;


        const bookIdField = document.createElement('input');
        bookIdField.type = 'hidden';
        bookIdField.name = `orderDetailList[${index}].bookId`;
        bookIdField.value = bookId;
        form.appendChild(bookIdField);

        const priceField = document.createElement("input");
        priceField.type = 'hidden';
        priceField.name = `orderDetailList[${index}].price`;
        priceField.value = price;
        form.appendChild(priceField);

        const quantityField = document.createElement("input");
        quantityField.type = 'hidden';
        quantityField.name = `orderDetailList[${index}].quantity`;
        quantityField.value = quantity;
        form.appendChild(quantityField);

        const packageIdField = document.createElement("input");
        packageIdField.type = 'hidden';
        packageIdField.name = `orderDetailList[${index}].packageId`;
        packageIdField.value = packageId;
        form.appendChild(packageIdField);

        if (orderDetail.querySelector('#couponId') != null) {
            const couponIdField = document.createElement('input');
            couponIdField.type = 'hidden';
            couponIdField.name = `orderDetailList[${index}].couponId`;
            couponIdField.value = orderDetail.querySelector('#couponId').value;
            form.appendChild(couponIdField);
        }

    });
    if (document.querySelector('input[name="deliveryWishDate"]:checked').value === 'select') {
        const deliveryWishDate = document.getElementById('date').value;
        let deliveryField = document.createElement("input");
        deliveryField.setAttribute("type", "hidden");
        deliveryField.setAttribute("name", "deliveryWishDate");
        deliveryField.setAttribute("value", deliveryWishDate);
        form.appendChild(deliveryField);
    }
    const finalTotalOriginPrice = document.getElementById('finalTotalOriginPrice').textContent;
    const finalTotalSalePrice = document.getElementById('finalTotalSalePrice').textContent;
    const deliveryFee = document.getElementById('deliveryFee').textContent;
    const message = String(document.getElementById('message').value);

    let netAmountField = document.createElement("input");
    netAmountField.setAttribute("type", "hidden");
    netAmountField.setAttribute("name", "netAmount");
    netAmountField.setAttribute("value", finalTotalOriginPrice);
    form.appendChild(netAmountField);

    let orderAmountField = document.createElement("input");
    orderAmountField.setAttribute("type", "hidden");
    orderAmountField.setAttribute("name", "orderAmount");
    orderAmountField.setAttribute("value", finalTotalSalePrice);
    form.appendChild(orderAmountField);

    let deliveryFeeField = document.createElement("input");
    deliveryFeeField.setAttribute("type", "hidden");
    deliveryFeeField.setAttribute("name", "deliveryFee");
    deliveryFeeField.setAttribute("value", deliveryFee);
    form.appendChild(deliveryFeeField);

    let receiverNameField = document.createElement("input");
    receiverNameField.setAttribute("type", "hidden");
    receiverNameField.setAttribute("name", "receiverName");
    receiverNameField.setAttribute("value", receiverName);
    form.appendChild(receiverNameField);

    let receiverPhoneNumberField = document.createElement("input");
    receiverPhoneNumberField.setAttribute("type", "hidden");
    receiverPhoneNumberField.setAttribute("name", "receiverPhoneNumber");
    receiverPhoneNumberField.setAttribute("value", receiverPhoneNumber);
    form.appendChild(receiverPhoneNumberField);

    let emailField = document.createElement("input");
    emailField.setAttribute("type", "hidden");
    emailField.setAttribute("name", "receiverEmail");
    emailField.setAttribute("value", receiverEmail);
    form.appendChild(emailField);

    let addressField = document.createElement("input");
    addressField.setAttribute("type", "hidden");
    addressField.setAttribute("name", "receiverAddress");
    addressField.setAttribute("value", receiveAddress);
    form.appendChild(addressField);

    let zipcodeField = document.createElement("input");
    zipcodeField.setAttribute("type", "hidden");
    zipcodeField.setAttribute("name", "zipcode");
    zipcodeField.setAttribute("value", zipcode);
    form.appendChild(zipcodeField);

    let detailAddressField = document.createElement("input");
    detailAddressField.setAttribute("type", "hidden");
    detailAddressField.setAttribute("name", "detailAddress");
    detailAddressField.setAttribute("value", detailAddress);
    form.appendChild(detailAddressField);

    let messageField = document.createElement("input");
    messageField.setAttribute("type", "hidden");
    messageField.setAttribute("name", "message");
    messageField.setAttribute("value", message);
    form.appendChild(messageField);

    if (document.querySelector('#totalCouponId') === null) {
        const passwordInput = String(document.getElementById('password').value);
        if (validCheck(passwordInput, passwordReg) === false) {
            Swal.fire({
                icon: 'warning',
                title: '비밀번호 입력 오류',
                text: '비밀번호를 다시 입력하여 주십시오.'
            })
            return;
        }
        if (String(document.getElementById('password').value) !== String(document.getElementById('passwordCheck').value)) {
            Swal.fire({
                icon: 'warning',
                title: '비밀번호 입력 오류',
                text: '비밀번호가 일치하지 않습니다.'
            })
            return;
        }

        let passwordField = document.createElement("input");
        passwordField.setAttribute("type", "hidden");
        passwordField.setAttribute("name", "password");
        passwordField.setAttribute("value", passwordInput);
        form.appendChild(passwordField);
    } else {
        if(document.querySelector('#totalCouponId').textContent !== ''){
            let couponIdField = document.createElement("input");
            couponIdField.setAttribute("type", "hidden");
            couponIdField.setAttribute("name", "couponId");
            couponIdField.setAttribute("value", document.querySelector('#totalCouponId').textContent);
            form.appendChild(couponIdField);
        }
        let pointField = document.createElement("input");
        pointField.setAttribute("type", "hidden");
        pointField.setAttribute("name", "point");
        pointField.setAttribute("value", document.querySelector('#totalPointSale').textContent);
        form.appendChild(pointField);
    }


    document.body.appendChild(form);
    form.submit();
    return true;
}

function validCheck(input, reg) {
    if (input.length !== 0) {
        if (reg.test(input) === false) {
            return false;
        } else {
            return true;
        }
    } else {
        return false;
    }
}