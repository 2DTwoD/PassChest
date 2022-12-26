function main(){
    const filterSearch = document.getElementById("filter");
    let pars = new Map();
    pars.set("filter", filterSearch.value);
    filter.addEventListener("click", () => {
        window.location.replace(getGetRequest("/search/systems", pars));
    })
}
function getGetRequest(href, pars){
    let result;
    result += href + "?";
    for(let [key, value] of pars){
        result += key + "=" + value + "&";
    }
    return result;
}
document.onload = main;