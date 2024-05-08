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

// 직접 입력 textarea 값 변경 시 message input에 설정
document.getElementById('etcMessage').addEventListener('input', function() {
    let messageInput = document.getElementById('message');
    messageInput.value = this.value;
});

function packagePopUp(){
    window.open("/order/selectPackage", "_blank", "width=600, height=700,top=200px,left=200px;");
}

function couponPopUp(){

}
function pointPopUp(){

}
function fullCouponPopUp(){

}

function buyNowButton() {
    let bookId = document.getElementById('bookId').value;
    let bookCount = document.getElementById('bookCount').value;
    let url = '/order/buyNow?bookId=' + bookId + '&bookCount=' + bookCount;
    window.location.href=url;
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
document.querySelectorAll('input[name="deliveryWishDate"]').forEach(function(radio) {
    radio.addEventListener('change', handleDeliveryDateSelection);
});

function pointChange(){
    document.getElementById('finalTotalSalePrice').textContent
        =String(parseInt(document.getElementById('finalTotalSalePrice').textContent)+parseInt(document.getElementById('pointSale').textContent))
    document.getElementById('pointSale').textContent="0";

    let point = Number(document.getElementById('quantity').value);
    let maxPoint = document.getElementById('totalPoint').value;
    let finalTotalSalePrice = parseInt(document.getElementById('finalTotalSalePrice').textContent);
    if(point<0){
        document.getElementById('quantity').value=0;
        return;
    }
    if(point>=maxPoint){
        point = maxPoint;
    };
    if(finalTotalSalePrice-point<0){
        point=finalTotalSalePrice;
    };
    document.getElementById('pointSale').textContent=String(point);
    document.getElementById('quantity').value=point;
    document.getElementById('finalTotalSalePrice').textContent
        =String(parseInt(document.getElementById('finalTotalSalePrice').textContent)-point);
}