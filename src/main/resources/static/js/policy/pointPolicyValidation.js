document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('pointPolicyForm');
    form.addEventListener('submit', function (event) {
        const policyName = document.getElementById('policyName').value.trim();
        const policyType = document.getElementById('policyType').value;
        const amount = Number(document.getElementById('amount').value.trim());

        let validationErrors = [];

        // policyName 유효성 검사 (1~20자)
        if (policyName.length < 1 || policyName.length > 20) {
            validationErrors.push("포인트 정책 명은 1~20자 사이여야 합니다.");
        }

        // policyType 유효성 검사 (AMOUNT 또는 PERCENT)
        if (policyType !== 'AMOUNT' && policyType !== 'PERCENT') {
            validationErrors.push("정책 종류는 '금액적립' 또는 '퍼센트적립'여야 합니다.");
        }

        // amount 유효성 검사
        if (isNaN(amount)) {
            validationErrors.push("입력된 값이 숫자가 아닙니다.");
        } else if (policyType === 'AMOUNT' && Math.floor(amount) < 0) {
            validationErrors.push("금액 적립 정책의 경우, 적립 수치는 0보다 커야 합니다.");
        } else if (policyType === 'PERCENT' && (amount < 0 || amount > 1)) {
            validationErrors.push("퍼센트 적립 정책의 경우, 적립 수치는 0~1 사이여야 합니다.");
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
