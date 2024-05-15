let tossClientApiKey; // 토스 API 키를 외부에서 전달받기 위해 선언

function setTossApiKey(key) {
    tossClientApiKey = key;
}

let tossPayments;

function initTossPayments() {
    tossPayments = TossPayments(tossClientApiKey); // 토스 결제 인스턴스 생성
}

let jsons = {};

function initPaymentData(order) {
    let amount = order.amount;
    let orderId = order.orderId;
    let orderName = order.orderName;
    let customerName = order.customerName;
    let customerId = order.customerId;
    let point = order.point;

    let path = "/order/payment/toss/success";
    let successUrl = window.location.origin + path + "?point=" + point + "&customerId=" + customerId + "&";
    let failUrl = window.location.origin + path + "/fail";

    jsons = {
        "card": {
            "amount": amount,
            "orderId": orderId,
            "orderName": orderName,
            "successUrl": successUrl,
            "failUrl": failUrl,
            "customerName": customerName,
            "cardCompany": null,
            "cardInstallmentPlan": null,
            "maxCardInstallmentPlan": null,
            "useCardPoint": false,
            "flowMode": "DEFAULT"
        },
        "transfer": {
            "amount": amount,
            "orderId": orderId,
            "orderName": orderName,
            "successUrl": successUrl,
            "failUrl": failUrl,
            "customerName": customerName,
            "cashReceipt": {
                "type": "소득공제"
            }
        },
        "phone": {
            "amount": amount,
            "orderId": orderId,
            "orderName": orderName,
            "successUrl": successUrl,
            "failUrl": failUrl
        },
        "certificate": {
            "amount": amount,
            "orderId": orderId,
            "orderName": orderName,
            "successUrl": successUrl,
            "failUrl": failUrl
        },
        "bookcert": {
            "amount": amount,
            "orderId": orderId,
            "orderName": orderName,
            "successUrl": successUrl,
            "failUrl": failUrl
        },
        "gamecert": {
            "amount": amount,
            "orderId": orderId,
            "orderName": orderName,
            "successUrl": successUrl,
            "failUrl": failUrl
        }
    }
};


function pay(method, requestJson) {
    console.log(requestJson);
    tossPayments.requestPayment(method, requestJson)
        .catch(function (error) {

            if (error.code === "USER_CANCEL") {
                alert('결제를 취소했습니다.');
            } else {
                alert(error.message);
            }

        });
};