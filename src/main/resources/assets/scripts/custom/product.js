function addToCart(itemId) {
    toggleOverlay(true);
    let itemForm = {
        itemId: itemId
    };
    $.ajax({
        type: "POST",
        url: "products",
        data: itemForm,
        dataType: "json",
        encode: true,
        success: ()=>success(),
        error: ()=>success()
    });
}

function success(){
    location.reload()
}
