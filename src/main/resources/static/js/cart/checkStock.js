async function checkStockAndBuy() {
    var cartItems = document.querySelectorAll('#cart');
    var orderList = [];
    console.log(cartItems)

    const cartDetails = document.querySelectorAll('.cartList');
    cartDetails.forEach((cartDetail, index) => {
        const bookId = cartDetail.querySelector('#cartBookId').value;
        const quantity = cartDetail.querySelector('#quantity').value;
        const salePrice = cartDetail.querySelector('#cartBookPrice').value;

        orderList.push({
            bookId: bookId,
            quantity: quantity,
            salePrice: salePrice
        });
    });
    let checkPriceMessages = [];
    await fetch('/books/checkPrice', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(orderList),

    })
        .then(response => response.json())
        .then(data => {
            data.forEach(book => {
                checkPriceMessages.push('[' + book.title + '] 현재 가격 : ' + book.salePrice);
            });

            if (checkPriceMessages.length > 0) {
                checkPriceMessages.push('책을 다시 담아주세요');
                Swal.fire({
                    icon: 'warning',
                    title: '가격이 변동된 책이 있습니다..',
                    html: checkPriceMessages.join('<br/>')
                });
                event.preventDefault();
            }
        });

    fetch('/books/checkStock', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(orderList),

    })
        .then(response => response.json())
        .then(data => {
            let checkQuantityMessages = [];
            data.forEach(book => {
                checkQuantityMessages.push('[' + book.title + '] 현재 재고 : ' + book.quantity);
            });

            if (checkQuantityMessages.length > 0) {
                Swal.fire({
                    icon: 'warning',
                    title: '재고가 부족한 책이 있습니다.',
                    html: checkQuantityMessages.join('<br/>')
                });

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