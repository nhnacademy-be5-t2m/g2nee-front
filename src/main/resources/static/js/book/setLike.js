let liked;
const url = '/likes';

document.querySelectorAll('#likes').forEach(button => {
    button.addEventListener('click', function (event) {

        let memberId = this.dataset.memberid;
        if (memberId === undefined) {
            Swal.fire({
                icon: 'warning',
                title: '찜은 회원만 가능합니다',
                text: '로그인을 해주세요'
            })
            event.preventDefault();
            return
        }
        let bookId = this.dataset.bookid;
        var icon = this.querySelector('i');
        var badge = document.querySelector('.badge')

        fetch(url, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({bookId: bookId, memberId: memberId})
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {

                liked = data.liked;
                let likesNum = parseInt(badge.textContent);

                console.log(liked)
                if (liked) {
                    icon.className = "fa-solid fa-heart text-primary";
                    likesNum++;
                } else {
                    icon.className = "fa-regular fa-heart text-primary";
                    likesNum--;
                }
                badge.textContent = likesNum;
            })
            .catch(error => {
                console.error('Error:', error);
                alert('에러가 발생했습니다. 다시 시도해주세요.');
            });
    });
});