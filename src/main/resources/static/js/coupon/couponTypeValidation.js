document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('couponTypeForm');
    form.addEventListener('submit', function (event) {
        const couponTypeName = document.getElementById('name').value.trim();
        const couponType = document.getElementById('type').value;
        const status = document.getElementById('status').value;
        const period = Number(document.getElementById('period').value.trim());
        const discount = Number(document.getElementById('discount').value.trim());
        const minimumOrderAmount = Number(document.getElementById('minimumOrderAmount').value.trim());
        const maximumDiscount = Number(document.getElementById('maximumDiscount').value.trim());

        let validationErrors = [];

        // couponType 유효성 검사 (1~20자)
        if (couponTypeName.length < 1 || couponTypeName.length > 20) {
            validationErrors.push("쿠폰 명은 1~20자 사이여야 합니다.");
        }

        // couponType 유효성 검사 (AMOUNT 또는 PERCENT)
        if (couponType !== 'AMOUNT' && couponType !== 'PERCENT') {
            validationErrors.push("쿠폰 종류는 'AMOUNT(금액쿠폰)' 또는 'PERCENT(퍼센트쿠폰)'여야 합니다.");
        }

        // status 유효성 검사 (DELETE, BATCH, INDIVIDUAL)
        if (status !== 'DELETE' && status !== 'BATCH' && status !== 'INDIVIDUAL') {
            validationErrors.push("쿠폰 상태는 'DELETE(삭제)' 또는 'BATCH(일괄발급)' 또는 'INDIVIDUAL(개별발급)'이어야 합니다.");
        }

        // period 유효성 검사
        if (isNaN(period)) {
            validationErrors.push("유효기간 값이 숫자가 아닙니다.");
        } else if (period <= 0) {
            validationErrors.push("유효기간은 0보다 커야 합니다.");
        }

        // discount 유효성 검사
        if (isNaN(discount)) {
            validationErrors.push("할인 값이 숫자가 아닙니다.");
        } else if (couponType === 'AMOUNT' && discount <= 0) {
            validationErrors.push("금액 쿠폰의 경우, 할인 금액은 0보다 커야 합니다.");
        } else if (couponType === 'PERCENT' && (discount <= 0 || discount > 100)) {
            validationErrors.push("퍼센트 쿠폰의 경우, 할인 퍼센트는 0보다 크고 100 이하여야 합니다.");
        }

        // minimumOrderAmount 유효성 검사
        if (isNaN(minimumOrderAmount) || minimumOrderAmount <= 0) {
            validationErrors.push("최소 주문 금액은 0보다 커야 합니다.");
        }

        // maximumDiscount 유효성 검사 (퍼센트 쿠폰의 경우에만 적용)
        if (couponType === 'PERCENT' && (isNaN(maximumDiscount) || maximumDiscount <= 0)) {
            validationErrors.push("퍼센트 쿠폰의 경우, 최대 할인 금액은 0보다 커야 합니다.");
        }

        // 정책에 맞는 유효성 검사 추가
        if (couponType === 'AMOUNT' && minimumOrderAmount <= 20000 && discount >= 3000) {
            validationErrors.push("20000원 이하 구매 시 3000원 할인미만이여야 합니다.");
        }


        if (couponType === 'PERCENT' && minimumOrderAmount >= 20000 && discount === 20 && maximumDiscount !== 10000) {
            validationErrors.push("20,000원 이상 구매 시 20% 할인, 최대 10,000원 할인이어야 합니다.");
        }

        //할인이 되지않는 경우 처리하기

        // 유효성 검사 실패 시 모달 팝업 표시
        if (validationErrors.length > 0) {
            event.preventDefault(); // 폼 제출 방지
            const modal = new bootstrap.Modal(document.getElementById('validationModal'));
            const validationModalBody = document.getElementById('validationErrors');
            validationErrors.push("<br/>(창을 닫으려면 창 밖 아무 곳이나 눌러주세요)");

            // 오류 메시지 업데이트
            validationModalBody.innerHTML = validationErrors.join("<br/>");

            modal.show();
        }
    });
});
