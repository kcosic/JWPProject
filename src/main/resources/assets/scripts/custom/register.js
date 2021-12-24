'use strict'


function setupFormListeners(){
    const form = document.body.getElementsByClassName('needs-validation')[0];

    // Loop over them and prevent submission



    const password = document.getElementById("password")
        , repeatPassword = document.getElementById("repeatPassword");

    function validatePassword(){
        if(password.value !== repeatPassword.value) {
            repeatPassword.setCustomValidity("Passwords Don't Match");
        } else {
            repeatPassword.setCustomValidity('');
        }
    }

    password.onchange = validatePassword;
    repeatPassword.onkeyup = validatePassword;

    form.addEventListener('submit', function (event) {
        if (!form.checkValidity()) {
            if(password.value !== repeatPassword.value){
            }
            event.preventDefault()
            event.stopPropagation()
        } else if(password.value !== repeatPassword.value){
            repeatPassword.classList.add('is-invalid');
            event.preventDefault()
            event.stopPropagation()
        }

        repeatPassword.classList.add('is-valid');
        form.classList.add('was-validated')
    }, false);
}

document.addEventListener("DOMContentLoaded", function() {
    setupFormListeners();
});