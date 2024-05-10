// 책 리스트에서 장바구니 담기
document.querySelectorAll('#cartButton').forEach(button => {
    button.addEventListener('click', function () {
        let bookId = this.dataset.bookid;
        let quantityElement = document.querySelector('#quantity')
        let cartItemNum = parseInt(this.dataset.cartitemnum);
        var cartItemNumElement = document.querySelector('#cartItemNum');

        let quantity;
        if(quantityElement === null){
            quantity = 1;
        } else {
            quantity = parseInt(quantityElement.value);
        }

        let bookQuantity = parseInt(this.dataset.bookquantity);
        let bookTitle = this.dataset.booktitle;

        if (quantity > bookQuantity) {

            Swal.fire({
                icon: 'warning',
                title: '재고가 부족합니다.',
                text: '현재 재고 : ' + bookQuantity
            })
            return;
        }

        fetch('/carts', {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                bookId: bookId,
                quantity: quantity
            })
        })
            .then(
                Swal.fire({
                    position: "middle",
                    icon: "success",
                    title: bookTitle + " 을(를) 장바구니에 담았습니다.",
                    showConfirmButton: false,
                    timer: 1500
                })

            )
        cartItemNum += 1;
        cartItemNumElement.textContent = cartItemNum.toString();
    });
});
