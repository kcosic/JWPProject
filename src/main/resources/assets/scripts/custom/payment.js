$(document).ready(function(){
    paypal.Buttons({
        style: {
            layout: 'vertical',
            color:  'blue',
            shape:  'rect',
            label:  'paypal'
        },
        createOrder: function(data, actions) {
            // Set up the transaction
            return actions.order.create({
                purchase_units: [{
                    amount: {
                        value: totalPrice,
                        currency_code: 'USD'
                        //unable to change currency in sandbox
                    }
                }]
            });
        },
        onApprove: function(data, actions) {
            let form = {
                payment: 'paypal'
            };
            $.ajax({
                type: "POST",
                url: "payment",
                data: form,
                encode: true,
                success:function (){
                    console.log("success")
                    location.replace("paymentSuccess")
                },
                complete:function (){
                    console.log("complete")
                    location.replace("paymentSuccess")
                },
            });
        },
        onError: function (err) {
            // For example, redirect to a specific error page
            window.location.replace("paymentError");
        }
    }).render('#paypal-button-container');
})
