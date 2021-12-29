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
        success: (response)=>success(response),
        error: (response)=>success(response)
    });
}

function success(response){
    console.log('success',response)
    location.reload()
}
