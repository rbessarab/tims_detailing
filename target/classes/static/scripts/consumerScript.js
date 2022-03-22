/**
 * This script displays data from the API
 *
 * @author Ruslan Bessarab
 */

window.onload = function () {
    getAllPackages();
}

function getAllPackages() {
    let uri = "http://localhost:8080/detailing/allPackages";
    let config = {
        method: "GET",
        mode: "cors",
        Headers: {
            "Content-Type": "application/json"
        }
    };

    //show the data on the page
    fetch(uri, config)
        .then((response) => {
            return response.json();
        })
        .then((json) => {
            displayAllPackages(json);
        });
}

function displayAllPackages(jsonData) {
    let contentArea = document.getElementById("content");

    for (let i = 0; i < jsonData.length; i++) {
        let singlePackage = jsonData[i];
        addPackageCard(contentArea, singlePackage);
    }
}

function addPackageCard(contentArea, singlePackage) {
    let card = document.createElement("div");
    let name = document.createElement("p");
    let carModel = document.createElement("p");
    let price = document.createElement("p");
    let additional = document.createElement("p");
    let date = document.createElement("p");
    let points = document.createElement("p");

    name.innerHTML = "<strong>Package: </strong>" + singlePackage.package_name;
    carModel.innerHTML = "<strong>Car: </strong>" + singlePackage.car_model;
    price.innerHTML = "<strong>Price: </strong>" + singlePackage.price;
    additional.innerHTML = "<strong>Additional Options: </strong>" + singlePackage.additional_options;
    date.innerHTML = "<strong>Date: </strong>" + singlePackage.date;
    points.innerHTML = "<strong>Points: </strong>" + singlePackage.points;

    card.setAttribute("class", "col-4 card pl-4");
    card.setAttribute("id", "card");
    card.appendChild(name);
    card.appendChild(carModel);
    card.appendChild(price);
    card.appendChild(additional);
    card.appendChild(date);
    card.appendChild(points);

    if(singlePackage.additional_options) {
        getAllOptions(singlePackage.packageId);
    }

    contentArea.appendChild(card);
}

function getAllOptions(id) {
    let uri = "http://localhost:8080/detailing/package/" + id + "/options";
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
            displayAllOptions(json);
        });
}

function displayAllOptions(json) {
    let contentArea = document.getElementById("card");

    for (let i = 0; i < json.length; i++) {
        let option = json[i];
        addOptionCard(contentArea, option);
    }
}

function addOptionCard(contentArea, option) {
    let card = document.createElement("div");
    let name = document.createElement("p");
    let points = document.createElement("p");

    name.innerHTML = "<strong>Option Name: </strong>" + option.name;
    points.innerHTML = "<strong>Option Points: </strong>" + option.points;

    card.appendChild(name);
    card.appendChild(points);

    contentArea.appendChild(card);
}