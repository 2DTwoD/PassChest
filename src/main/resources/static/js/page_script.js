function main(){
    const selectPage = document.getElementById("selectPage");
    const prevPage = document.getElementById("prevPage");
    const nextPage = document.getElementById("nextPage");
    const path = location.origin + location.pathname;
    const pars = new Map();
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
window.addEventListener("load", main);