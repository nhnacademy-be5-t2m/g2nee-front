function toggleSelect(isActivated) {
    var select = document.getElementById("ancestorCategoryLabel");
    if (isActivated) {
        select.style.display = "block";
    } else {
        select.style.display = "none";
        document.getElementsByName("ancestorCategoryId")[0].value = "0";
    }
}