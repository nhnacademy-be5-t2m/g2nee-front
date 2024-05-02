function setDefaultAddress(radio) {
    document.querySelector('#changeAddressId').value = radio.value;
}

function saveAddressPopUp() {
    let addressLength = document.querySelector('#addressLength').value;
    if (addressLength >= 10) {
        Swal.fire({
            icon: 'warning',
            title: '배송지 추가 오류',
            text: ' 10개이상의 배송지를 설정할 수 없습니다.'
        })
        return;
    }
    window.open("/mypage/address/save", "_blank", "width=600, height=700,top=200px,left=200px;");
}


async function changeDefaultAddress() {
    let addressId = document.querySelector('#changeAddressId').value;
    if (addressId === '') {
        Swal.fire({
            icon: 'warning',
            title: '기본 배송지 입력 오류',
            text: '변경할 배송지를 선택하여 주십시오.'
        })
        return;
    }
    try {
        const response = await fetch("/address/changeDefaultAddress", {
            method: 'POST',
            body: addressId
        });
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        window.location.reload();
    } catch (error) {
        console.error('요청 실패:', error);
        return false;
    }
}

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


function saveAddressButton() {
        let address = document.querySelector('#address').value;
    let zipcode = document.querySelector('#zipcode').value;
    if (address === '' || zipcode === '') {
        Swal.fire({
            icon: 'warning',
            title: '배송지 입력 오류',
            text: '우편번호찾기를 통해 주소를 입력해주십시오.'
        });
        return;
    }else{
        saveAddress()
            .then(() => {
                opener.parent.reload();
                window.close(); // 팝업 창 닫기
            });
    }
    return true;
}


function reload() {
    setTimeout(function () {
        location.reload();
    }, 300);
}

async function saveAddress() {
    let isDefault = document.querySelector('input[name="isDefault"]:checked').value;
    let address = document.querySelector('#address').value;
    let alias = document.querySelector('#alias').value;
    let detail = document.querySelector('#detail').value;
    let zipcode = document.querySelector('#zipcode').value;


    let requestBody = {
        isDefault: isDefault,
        address: address,
        alias: alias,
        detail: detail,
        zipcode: zipcode
    };
    try {
        const response = await fetch("/address/save", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestBody)
        });
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
    } catch (error) {
        console.error('요청 실패:', error);
        return false;
    }
    return true;
}
