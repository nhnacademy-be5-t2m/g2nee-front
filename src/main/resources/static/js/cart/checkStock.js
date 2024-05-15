async function checkStockAndBuy() {
    var cartItems = document.querySelectorAll('#cart');
    var orderList = [];
    console.log(cartItems)

    const cartDetails = document.querySelectorAll('.cartList');
    cartDetails.forEach((cartDetail, index) => {
        const bookId = cartDetail.querySelector('#cartBookId').value;
        const quantity = cartDetail.querySelector('#quantity').value;

        orderList.push({
            bookId: bookId,
            quantity: quantity
        });
    });
    fetch('/books/stock', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(orderList),
    })
        .then(response => response.json())
        .then(data => {
            let warningMessages = [];
            data.forEach(book => {
                warningMessages.push('[' + book.title + '] 현재 재고 : ' + book.quantity);
            });

            if (warningMessages.length > 0) {
                Swal.fire({
                    icon: 'warning',
                    title: '재고가 부족한 책이 있습니다.',
                    html: warningMessages.join('<br/>')
                });
                return false;
            } else {

                const form = document.createElement('form');
                form.method = 'post';
                form.action = '/order/buyCart';

                const cartDetails = document.querySelectorAll('.cartList');
                cartDetails.forEach((cartDetail, index) => {
                    const bookId = cartDetail.querySelector('#cartBookId').value;
                    const quantity = cartDetail.querySelector('#quantity').value;

                    const bookIdField = document.createElement('input');
                    bookIdField.type = 'hidden';
                    bookIdField.name = `bookOrderList[${index}].bookId`;
                    bookIdField.value = bookId;
                    form.appendChild(bookIdField);

                    const quantityField = document.createElement("input");
                    quantityField.type = 'hidden';
                    quantityField.name = `bookOrderList[${index}].bookCount`;
                    quantityField.value = quantity;
                    form.appendChild(quantityField);

                });
                document.body.appendChild(form);
                form.submit();
                return true;
            }
        })
        .catch((error) => console.error('Error:', error));
}