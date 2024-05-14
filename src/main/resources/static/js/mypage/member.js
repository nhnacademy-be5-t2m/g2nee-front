function quit() {
    let password = document.getElementById('password');
    let memberId = document.getElementById('memberId');
    let username = document.getElementById('username');

    Swal.fire({
        title: '회원 탈퇴를 진행하시겠습니까?',
        text: "회원만의 각종 혜택이 사라집니다!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '회원 탈퇴'
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = "/mypage/quitMember";
        }
    })


}