function main(){
    const filterText = document.getElementById("filterText");
    const filterButton = document.getElementById("filterButton");
    const pars = new Map();
    filterButton.addEventListener("click", () => {
        pars.set("filter", filterText.value);
        location.href = getGetRequest(location.origin + location.pathname, pars);
    });
}
window.addEventListener("load", main);