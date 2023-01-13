function main(){
    const showButtons = document.getElementsByClassName("showButton");
    const showAllButton = document.getElementById("showAllButton");
    for(let button of showButtons){
        button.addEventListener("click", e => {
            showPassword(e.target, e.target, showAllButton);
        })
    }
    if(showAllButton !== null){
        showAllButton.addEventListener("click", () => {
            for(let button of showButtons) {
                showPassword(button, showAllButton, showAllButton);
            }
            if(showAllButton.value[0] === 'П'){
                showAllButton.value = "Скрыть все пароли";
            } else {
                showAllButton.value = "Показать все пароли";
            }
        });
    }
}
function showPassword(buttonWithId, buttonWithText, showAllButton){
    const passwordField = document.getElementById("password" + buttonWithId.id)
    if(buttonWithText.value[0] === 'П') {
        const passwordPromise = getPassword(buttonWithId);
        passwordPromise.then(password => {
            passwordField.textContent = password;
            buttonWithId.value = "Скрыть пароль";
            showAllButton.value = "Скрыть все пароли";
        });
    } else {
        passwordField.textContent = "******";
        buttonWithId.value = "Показать пароль";
    }
}
async function getPassword(element){
    return await fetch("/get_pass" + element.id).then(response => response.text());
}
window.addEventListener("load", main);