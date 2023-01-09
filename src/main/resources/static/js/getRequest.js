function getGetRequest(href, pars){
    let result = "";
    result += href + "?";
    for(let [key, value] of pars){
        result += key + "=" + value + "&";
    }
    return result;
}