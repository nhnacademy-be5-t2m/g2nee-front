document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('packageForm');
    form.addEventListener('submit', function (event) {
        const isActivated = document.querySelector('input[name="isActivated"]:checked');
        const packageName = document.getElementById('name').value.trim(); // 패키지 이름
        const packagePrice = parseInt(document.getElementById('price').value.trim()); // 패키지 가격

        let validationErrors = [];

        // packageName 유효성 검사 (1~20자)
        if (packageName.length < 1 || packageName.length > 20) {
            validationErrors.push("패키지 이름은 1~20자 사이여야 합니다.");
        }

        // packagePrice 유효성 검사 (숫자이고 0 이상)
        if (isNaN(packagePrice) || packagePrice < 0) {
            validationErrors.push("가격은 0 이상이어야 합니다.");
        }

        // isActivated 유효성 검사 (라디오 버튼이 선택되어야 함)
        if (!isActivated) {
            validationErrors.push("활성화 상태는 선택되어야 합니다.");
        }

        // 유효성 검사 실패 시 모달 팝업 표시
        if (validationErrors.length > 0) {
            event.preventDefault(); // 폼 제출 방지
            const modal = new bootstrap.Modal(document.getElementById('validationModal'));
            const validationModalBody = document.getElementById('validationErrors');
            validationErrors.push("<br/>(창을 닫으려면 창밖 아무곳이나 눌러주세요)")

            // 오류 메시지 업데이트
            validationModalBody.innerHTML = validationErrors.join("<br/>");

            modal.show();
        }
    });
});
