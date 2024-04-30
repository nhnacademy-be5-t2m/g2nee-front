function show(element) {
    var divToShow = document.getElementById("ancestorCategoryLabel");
    if (element.value === "true") {
        // 라디오 버튼이 활성화되었을 때
        divToShow.style.display = "block";
    } else {
        // 라디오 버튼이 비활성화되었을 때
        divToShow.style.display = "none";
    }
}