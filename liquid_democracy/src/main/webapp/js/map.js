function mapReset() {
    "use strict";
    var map = document.getElementById("map");
    var button = document.getElementById("mapbutton");
    var mapc = document.getElementById("mapcontainer");
    map.style.display = "none";
    button.value = "Show Map";
    mapc.style.display = "none";
}

function geocodeAddress() {
    "use strict";
    if (document.getElementById("adrmsg") !== null) {
        var msg = document.getElementById("adrmsg");
        var addr = document.registration.address;
        var city = document.registration.city;
        var country = document.registration.country;
        var geocoder = new google.maps.Geocoder();
        var final = addr.value + ", " + city.value + ", " + country.options[country.selectedIndex].text;
        console.log("MAP: " + final);
        msg.innerHTML = "Validating Address...";
        geocoder.geocode({"address": final}, function (result, status) {
            if (status === "OK") {
                msg.style.color = "greenyellow";
                msg.innerHTML = "Address Matching Found!";
                console.log("MAP: statusok");
                mapReset();
                document.getElementById("mapcontainer").style.display = "block";
            } else {
                msg.style.color = "red";
                msg.innerHTML = "Address Could Not Be Matched";
                console.log("MAP: statusnotok");
                mapReset();
            }
        });
    }
}

function geocodeAddressHM(addrs) {
    "use strict";
    if (document.getElementById("adrmsg") !== null) {
        
    }
}

function showHeatmap() {
    "use strict";
    var clins = document.getElementByClassName("imapcontainer");
    if (document.getElementsByClassName("vote_results").length > 0) {
        geocoder.geocode({"address": final}, function (result, status) {
            if (status === "OK") {
                mapResetClass();
                document.getElementById("mapcontainer").style.display = "block";
            } else {
                mapReset();
            }
        });
    }
}

function initMap() {
    "use strict";
    var msg = document.getElementById("adrmsg");
    var addr = document.registration.address;
    var city = document.registration.city;
    var country = document.registration.country;
    var geocoder = new google.maps.Geocoder();
    var final = addr.value + ", " + city.value + ", " + country.options[country.selectedIndex].text;
    geocoder.geocode({"address": final}, function (result, status) {
        if (status === "OK") {
            var map = new google.maps.Map(document.getElementById("map"), {
                zoom: 8,
                center: result[0].geometry.location
            });
            var marker = new google.maps.Marker({
                map: map,
                position: result[0].geometry.location
            });
        } else {
            msg.style.color = "red";
            msg.innerHTML = "Address Could Not Be Matched";
            console.log("MAP: statusnotok");
            mapReset();
        }
    });
}

function mapButton() {
    "use strict";
    var map = document.getElementById("map");
    var button = document.getElementById("mapbutton");
    if (button.value === "Show Map") {
        button.value = "Hide Map";
        map.style.display = "inline-block";
        initMap();
    } else {
        button.value = "Show Map";
        map.style.display = "none";
    }
}
