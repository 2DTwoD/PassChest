function main(){
    const hideAllText = "Скрыть все";
    const showAllText = "Показать все";
    const secretTemplate= "******";
    const passwordFields = document.getElementsByClassName("passwordFields");
    const showAllButton = document.getElementById("showAllButton");
    for(let passwordField of passwordFields){
        passwordField.addEventListener("click", e => {
            showPassword(e.target, showAllButton, hideAllText, secretTemplate, false);
            for (let passwordField of passwordFields) {
                if (passwordField.textContent !== secretTemplate) {
                    return;
                }
            }
            showAllButton.value = showAllText;
        });
    }
    if(showAllButton !== null){
        showAllButton.addEventListener("click", () => {
            for(let passwordField of passwordFields) {
                showPassword(passwordField, showAllButton, hideAllText, secretTemplate, true);
            }
            if(showAllButton.value[0] === showAllText[0]){
                showAllButton.value = hideAllText;
            } else {
                showAllButton.value = showAllText;
            }
        });
    }
}
function showPassword(passwordField, showAllButton, hideAllText, secretTemplate, all){
    let condition = all? showAllButton.value[0] !== hideAllText[0]: passwordField.textContent === secretTemplate;
    if(condition) {
        getPassword(passwordField).then(password => {
            passwordField.textContent = password;
            showAllButton.value = hideAllText;
        });
    } else {
        passwordField.textContent = secretTemplate;
    }
}
async function getPassword(element){
    return await fetch("/get_pass/" + element.id).then(response => response.text());
}
window.addEventListener("load", main);