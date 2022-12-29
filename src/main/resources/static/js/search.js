function main(){
    const filterText = document.getElementById("filterText");
    const filterButton = document.getElementById("filterButton");
    const selectPage = document.getElementById("selectPage");
    const prevPage = document.getElementById("prevPage");
    const nextPage = document.getElementById("nextPage");
    const pars = new Map();
    filterButton.addEventListener("click", () => {
        pars.set("filter", filterText.value);
        location.href = getGetRequest(path, pars);
    });
    selectPage.addEventListener("change", () => {
        pars.set("page", selectPage.value);
        location.href = getGetRequest(path, pars);
    });
    prevPage.addEventListener("click", () => {
        pars.set("page", page - 1);
        location.href = getGetRequest(path, pars);
    });
    nextPage.addEventListener("click", () => {
        pars.set("page", page + 1);
        location.href = getGetRequest(path, pars);
    });
}
function getGetRequest(href, pars){
    let result = "";
    result += href + "?";
    for(let [key, value] of pars){
        result += key + "=" + value + "&";
    }
    return result;
}
window.onload = main;