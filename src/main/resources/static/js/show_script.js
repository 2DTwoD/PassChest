function main(){
    const hideAllText = "Скрыть все пароли";
    const showAllText = "Показать все пароли";
    const hideCurrentText = "Скрыть пароль";
    const showCurrentText = "Показать пароль";
    const secretTemplate= "******";
    const showButtons = document.getElementsByClassName("showButton");
    const showAllButton = document.getElementById("showAllButton");
    for(let button of showButtons){
        button.addEventListener("click", e => {
            showPassword(e.target, e.target, showAllButton, hideAllText, hideCurrentText, showCurrentText, secretTemplate);
            for (let button of showButtons) {
                if (button.value[0] !== showAllText[0]) {
                    return;
                }
            }
            showAllButton.value = showAllText;
        });
    }
    if(showAllButton !== null){
        showAllButton.addEventListener("click", () => {
            for(let button of showButtons) {
                showPassword(button, showAllButton, showAllButton, hideAllText, hideCurrentText, showCurrentText, secretTemplate);
            }
            if(showAllButton.value[0] === showAllText[0]){
                showAllButton.value = hideAllText;
            } else {
                showAllButton.value = showAllText;
            }
        });
    }
}
function showPassword(buttonWithId, buttonWithText, showAllButton, hideAllText, hideCurrentText, showCurrentText, secretTemplate){
    const passwordField = document.getElementById("password" + buttonWithId.id)
    if(buttonWithText.value[0] === showCurrentText[0]) {
        getPassword(buttonWithId).then(password => {
            passwordField.textContent = password;
            buttonWithId.value = hideCurrentText;
            showAllButton.value = hideAllText;
        });
    } else {
        passwordField.textContent = secretTemplate;
        buttonWithId.value = showCurrentText;
    }
}
async function getPassword(element){
    return await fetch("/get_pass/" + element.id).then(response => response.text());
}
window.addEventListener("load", main);