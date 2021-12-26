



function saveToLocalStorage(user){
    localStorage.setItem('user', JSON.parse(user));
}

window.onload = function () {
    if(currentUser) {
        saveToLocalStorage(currentUser)
    }
    else{
        saveToLocalStorage('user', JSON.parse({id: 0}))
    }
}