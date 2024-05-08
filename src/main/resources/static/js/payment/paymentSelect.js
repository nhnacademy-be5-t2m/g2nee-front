// 선택된 결제 방법을 저장할 변수
let selectedPaymentMethod = null;

function selectPaymentMethod(method) {
    if (method === 'naver' || method === 'kakao') {
        alert("준비중 입니다. 현제 토스 결제만 지원합니다.");
        selectPaymentMethod('toss'); // 토스 결제를 기본 선택
        return;
    }

    // 버튼 색상 변경을 위해 모든 버튼에 기본 스타일을 적용
    document.getElementById("toss-button").style.backgroundColor = "white";
    document.getElementById("naver-button").style.backgroundColor = "white";
    document.getElementById("kakao-button").style.backgroundColor = "white";

    document.getElementById("toss-button").style.color = "darkgray";
    document.getElementById("naver-button").style.color = "darkgray";
    document.getElementById("kakao-button").style.color = "darkgray";

    // 선택한 버튼에 스타일 적용
    document.getElementById(`${method}-button`).style.backgroundColor = "#89cff0"; // 파란색 배경
    document.getElementById(`${method}-button`).style.color = "black"; // 흰색 글자

    // 선택된 결제 방법 설정
    selectedPaymentMethod = method;
}

function openPaymentWindow() {
    if (selectedPaymentMethod === null) {
        alert("결제 방법을 선택하세요!");
        return;
    }

    let windowWidth = 786;
    let windowHeight = 684;

    // 화면 중앙을 계산
    let left = (screen.width / 2) - (windowWidth / 2);
    let top = (screen.height / 2) - (windowHeight / 2);

    // 선택된 결제 방법에 따라 새 창을 엽니다.
    let url = `/orders/payments/${selectedPaymentMethod}`;
    let windowOptions = `width=${windowWidth},height=${windowHeight},top=${top},left=${left},menubar=no,toolbar=no,status=no,scrollbars=yes,location=no`;
    window.open(url, "_blank", windowOptions);
}