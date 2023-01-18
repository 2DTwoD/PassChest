function main(){
    const filterText = document.getElementById("filterText");
    const searchButton = document.getElementById("searchButton");
    const resetButton = document.getElementById("resetButton");
    const pars = new Map();
    searchButton.addEventListener("click", () => {
        pars.set("filter", filterText.value);
        location.href = getGetRequest(location.origin + location.pathname, pars);
    });
    resetButton.addEventListener("click", () => {
        pars.set("resetParameters", true);
        location.href = getGetRequest(location.origin + location.pathname, pars);
    });
}
window.addEventListener("load", main);