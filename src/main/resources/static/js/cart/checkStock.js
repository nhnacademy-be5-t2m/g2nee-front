// 주문 전 재고 확인
document.getElementById('orderButton').addEventListener('click', function () {
    var cartItems = document.querySelectorAll('#bookItem');
    var orderList = [];
    console.log(cartItems)

    cartItems.forEach(function (cart) {
        console.log(cart)

        var bookId = cart.querySelector('button[id="deleteButton"]').getAttribute('data-bookId');
        var quantity = cart.querySelector('input[id="quantity"]').value;

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
                warningMessages.push(book.title + ' 현재 재고: ' + book.quantity);
            });

            if (warningMessages.length > 0) {
                Swal.fire({
                    icon: 'warning',
                    title: '재고가 부족한 책이 있습니다.',
                    html: warningMessages.join('<br/>')
                });
            }
        })
        .catch((error) => console.error('Error:', error));
});