/**
 * This script displays actuator data on admin page
 * @author Ruslan Bessarab
 */

window.onload = function () {
    let requestsButton = document.getElementById("requests");
    requestsButton.addEventListener("click", getAllRequests);

    let variablesButton = document.getElementById("variables");
    variablesButton.addEventListener("click", getVar);

    let healthButton = document.getElementById("health");
    healthButton.addEventListener("click", getHealth);
}

//Requests Button
function getAllRequests() {
    let uri = "http://localhost:8080/actuator/metrics";
    let config = {
        method: "GET",
        mode: "cors",
        Headers: {
            "Content-Type": "application/json"
        }
    };

    fetch(uri, config)
        .then((response) => {
            return response.json();
        })
        .then((json) => {
            displayAllRequests(json);
        });
}

function displayAllRequests(json) {
    let contentArea = document.getElementById("content");
    let requestCounter = json.names.length;
    addRequestCard(contentArea, json.names, requestCounter);
}

function addRequestCard(contentArea, request, requestCounter) {
    if(contentArea !== "") {
        contentArea.innerHTML = "";
    }
    let card = document.createElement("div");
    let header = document.createElement("h3");

    header.setAttribute("class", "h-3");
    header.innerHTML = "Requests made: " + requestCounter;

    card.appendChild(header);

    contentArea.appendChild(card);
}

//Health Button
function getHealth() {
    let uri = "http://localhost:8080/actuator/health";
    let config = {
        method: "GET",
        mode: "cors",
        Headers: {
            "Content-Type": "application/json"
        }
    };

    fetch(uri, config)
        .then((response) => {
            return response.json();
        })
        .then((json) => {
            displayHealth(json);
        });
}

function displayHealth(json) {
    let card = document.createElement("div");
    let contentArea = document.getElementById("content");
    let status = json.status;

    let header = document.createElement("h3");
    header.setAttribute("class", "h-3");

    contentArea.innerHTML = "";
    header.innerHTML = "Health Status: " + status;

    card.appendChild(header);
    contentArea.appendChild(card);
}

//Environmental Variables Button
function getVar() {
    let uri = "http://localhost:8080/actuator/env";
    let config = {
        method: "GET",
        mode: "cors",
        Headers: {
            "Content-Type": "application/json"
        }
    };

    fetch(uri, config)
        .then((response) => {
            return response.json();
        })
        .then((json) => {
            console.log(json.propertySources);
            displayVars(json);
        });
}

function displayVars(json) {
    let contentArea = document.getElementById("content");
    let names = json.propertySources;
    let envNumber = json.propertySources.length;
    contentArea.innerHTML = "";

    let header = document.createElement("h3");
    header.innerHTML = "Number of Environmental Variables Used: " + envNumber;
    header.setAttribute("class", "h-3");

    contentArea.appendChild(header);

    for (let i = 0; i < names.length; i++) {
        let name = names[i];
        addVarsCard(contentArea, name);
    }
}

function addVarsCard(contentArea, property) {
    let card = document.createElement("div");
    let name = document.createElement("p");

    name.innerHTML = property.name;

    card.appendChild(name);
    contentArea.appendChild(card);
}