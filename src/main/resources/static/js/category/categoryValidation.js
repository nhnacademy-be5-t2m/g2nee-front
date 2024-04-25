document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('categoryForm');
    form.addEventListener('submit', function (event) {
        const categoryName = document.getElementById('categoryName').value.trim();
        const categoryEngName = document.getElementById('categoryEngName').value.trim();
        const isActivated = document.querySelector('input[name="isActivated"]:checked');


        let validationErrors = [];

        // categoryName 유효성 검사 (1~50자)
        if (categoryName.length < 1 || categoryName.length > 50) {
            validationErrors.push("카테고리 명은 1~50자 사이여야 합니다.");
        }

        // categoryEngName 유효성 검사 (1~50자)
        if (categoryEngName.length < 1 || categoryEngName.length > 50) {
            validationErrors.push("카테고리 영문 명은 1~50자 사이여야 합니다.");
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
