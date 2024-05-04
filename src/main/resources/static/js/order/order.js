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

}

function couponPopUp(){

}