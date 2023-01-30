function main(){
    const filterText = document.getElementById("filterText");
    const searchButton = document.getElementById("searchButton");
    const resetButton = document.getElementById("resetButton");
    const pars = new Map();
    filterText.addEventListener("keypress", (e) => {
        if (e.key === "Enter") {
            e.preventDefault();
            applyFilter(pars, filterText);
        }
    });
    searchButton.addEventListener("click", () => {
        applyFilter(pars, filterText);
    });
    resetButton.addEventListener("click", () => {
        pars.set("resetParameters", true);
        location.href = getGetRequest(location.origin + location.pathname, pars);
    });
}
function applyFilter(pars, filterText){
    pars.set("filter", filterText.value);
    location.href = getGetRequest(location.origin + location.pathname, pars);
}
window.addEventListener("load", main);