function setDefaultAddress(radio) {
    var selectedRow = radio.parentNode.parentNode;
    var form = document.getElementById("addressForm");
    var index = selectedRow.rowIndex - 1;
    var inputs = form.getElementsByTagName("input");
    for (var i = 0; i < inputs.length; i++) {
        if (inputs[i].type === "radio" && inputs[i] !== radio) {
            inputs[i].checked = false;
        }
    }
    inputs[index].checked = true;
}

function changeDefaultAddress() {
    var form = document.getElementById("addressForm");
    form.submit(); // Submit the form to update default address
}