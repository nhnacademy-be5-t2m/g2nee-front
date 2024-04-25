document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('deliveryForm');
    form.addEventListener('submit', function (event) {
        const deliveryFee = parseInt(document.getElementById('deliveryFee').value.trim());
        const freeDeliveryStandard = parseInt(document.getElementById('freeDeliveryStandard').value.trim());

        let validationErrors = [];

        // deliveryFee 유효성 검사 (숫자이고 0 초과)
        if (isNaN(deliveryFee) || deliveryFee <= 0) {
            validationErrors.push("배송비는 0보다 큰 값이어야 합니다.");
        }

        // freeDeliveryStandard 유효성 검사 (숫자이고 0 초과)
        if (isNaN(freeDeliveryStandard) || freeDeliveryStandard <= 0) {
            validationErrors.push("무료 배송 기준은 0보다 큰 값이어야 합니다.");
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
