let liked;
const url = '/likes';

document.querySelectorAll('.btn.btn-sm.text-dark.p-0').forEach(button => {
    button.addEventListener('click', function (event) {
        event.preventDefault();


        let memberId = this.dataset.memberid;
        let bookId = this.dataset.bookid;
        var icon = this.querySelector('i');
        var text = this.querySelector('span');
        var badge = document.querySelector('.badge')

        if (memberId === undefined) {
            alert('찜은 회원만 가능합니다');
            return;
        }

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
                    icon.className = 'fa-solid fa-heart';
                    text.textContent = '찜 해제';
                    likesNum++;
                } else {
                    icon.className = "fa-regular fa-heart";
                    text.textContent = '찜 하기';
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