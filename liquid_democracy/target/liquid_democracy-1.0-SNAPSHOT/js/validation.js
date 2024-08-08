function encryptPass(pass) {
    //add more security 
    pass = md5(pass);
    return pass;
}

function sendAjaxGETmod(data, eid, callback) {
    "use strict";
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            document.getElementById(eid).innerHTML = xhr.responseText;
            callback();
        } else if (xhr.status !== 200) {
            alert("Request Failed. Return Status: " + xhr.status);
        }
    };
    var db = "";
    if (data.length !== 0) {
        db += "?" + data[0].name + "=" + data[0].value;
    }
    for (var i = 1; i < data.length; i++) {
        db += "&" + data[i].name + "=" + data[i].value;
    }
    xhr.open("GET", "memberServlet" + db, true);
    xhr.send();
}

function sendAjaxGETactive() {
    "use strict";
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            
            
        } else if (xhr.status !== 200) {
            alert("Request Failed. Return Status: " + xhr.status);
        }
    };
    xhr.open("GET", true);
    xhr.send();
}

function toggler(element) {
    "use strict";
    if (document.getElementById(element).style.display === "block") {
        document.getElementById(element).style.display = "none";
    } else {
        document.getElementById(element).style.display = "block";
    }
}

function togglerclass(element, i) {
    "use strict";
    if ((document.getElementsByClassName(element))[i].style.display === "block") {
        (document.getElementsByClassName(element))[i].style.display = "none";
    } else {
        (document.getElementsByClassName(element))[i].style.display = "block";
    }
}

function isCorrect(msgfield) {
    "use strict";
    msgfield.innerHTML = "";
}

function validateUser() {
    "use strict";
    var uid = document.getElementById("uid");
    var um = document.getElementById("uidmsg");
    if (uid.checkValidity() === false) {
        if (document.getElementById("pgtitle").innerHTML === "New Member Registration") {
            document.getElementById("phen").disabled = true;
        }
        if (uid.value !== "") {
            um.innerHTML = "Format: Only uppercase and lowercase characters, at least 8 characters";
            return false;
        }
    } else {
        um = document.getElementById("uidmsg");
        isCorrect(um);
        if (document.getElementById("pgtitle").innerHTML === "New Member Registration") {
            document.getElementById("phen").disabled = false;
        }
        sendAjaxGETmod([{name: "username", value: uid.value}], "uidmsg", function () {
            if (um.innerHTML !== "" && document.getElementById("pgtitle").innerHTML === "New Member Registration") {
                document.getElementById("phen").disabled = true;
                document.getElementById("phdis").checked = true;
                document.getElementById("faceactual").style.display = "none";
            }
        });
        return true;
    }
}

function validatePass() {
    "use strict";
    var pwd1 = document.getElementById("pass1");
    if (pwd1.checkValidity() === false && pwd1.value !== "") {
        document.getElementById("pwdmsg").innerHTML = "Format: 8-10 characters, at least one uppercase letter, lowercase letter, number and symbol";
        return false;
    } else {
        isCorrect(document.getElementById("pwdmsg"));
        return true;
    }
}

function checkPass() {
    "use strict";
    var pwd1 = document.getElementById("pass1");
    var pwd2 = document.getElementById("pass2");
    if (pwd2.value === "") {
        return false;
    }
    if (pwd1.value !== pwd2.value) {
        document.getElementById("pwd2msg").innerHTML = "Passwords must match!";
        return false;
    } else {
        isCorrect(document.getElementById("pwd2msg"));
        return true;
    }
}

function checkOldPass() {
    "use strict";
    var pwd0 = document.getElementById("pass0");
    var um = document.getElementById("pwd0msg");
    sendAjaxGETmod([{name: "password", value: encryptPass(pwd0.value)}], "pwd0msg", function () {});
    return true;
}

function validateFnm() {
    "use strict";
    var fnm = document.getElementById("fname");
    if (fnm.checkValidity() === false && fnm.value !== "") {
        document.getElementById("fnmmsg").innerHTML = "Format: 20 characters max";
        return false;
    } else {
        isCorrect(document.getElementById("fnmmsg"));
        return true;
    }
}

function validateLnm() {
    "use strict";
    var lnm = document.getElementById("lname");
    if (lnm.checkValidity() === false && lnm.value !== "") {
        document.getElementById("lnmmsg").innerHTML = "Format: Minumum 4 characters, maximum 20 characters";
        return false;
    } else {
        isCorrect(document.getElementById("lnmmsg"));
        return true;
    }
}

function validateEmail() {
    "use strict";
    var email = document.getElementById("em");
    if (email.checkValidity() === false && email.value !== "") {
        document.getElementById("emlmsg").innerHTML = email.validationMessage;
        return false;
    } else {
        var um = document.getElementById("emlmsg");
        isCorrect(um);
        sendAjaxGETmod([{name: "email", value: email.value}], "emlmsg", function () {});
        return true;
    }
}

function validateCity() {
    "use strict";
    var city = document.getElementById("ct");
    if (city.checkValidity() === false && city.value !== "") {
        document.getElementById("citmsg").innerHTML = "Format: minimum 2 characters, maximum 20 characters";
        return false;
    } else {
        isCorrect(document.getElementById("citmsg"));
        return true;
    }
}

function validateOccupation() {
    "use strict";
    var ocp = document.getElementById("ocp");
    if (ocp.checkValidity() === false && ocp.value !== "") {
        document.getElementById("occmsg").innerHTML = "Format: minimum 2 characters, maximum 20 characters";
        return false;
    } else {
        isCorrect(document.getElementById("occmsg"));
        return true;
    }
}

function validateOver18(date) {
    var ud = new Date(date);
    var cd = new Date();
    var age = cd.getFullYear() - ud.getFullYear();
    var m = cd.getMonth() - ud.getMonth();
    if (m < 0 || (m === 0 && cd.getDate() < ud.getDate())) {
        age--;
    }
    if (age >= 18) {
        return true;
    } else {
        return false;
    }
}

function validateDate() {
    "use strict";
    var dt = document.getElementById("bd");
    if ((dt.checkValidity() === false || validateOver18(dt.value) === false) && dt.value !== "") {
        document.getElementById("dtmsg").innerHTML = "Format: yyyy-mm-dd and must be over 18";
        return false;
    } else {
        isCorrect(document.getElementById("dtmsg"));
        return true;
    }
}

function validateInterests() {
    "use strict";
    var intr = document.getElementById("intr");
    if (intr.checkValidity() === false && intr.value !== "") {
        document.getElementById("intmsg").innerHTML = "Format: Maximum 100 characters";
        return false;
    } else {
        isCorrect(document.getElementById("intmsg"));
        intr.style.border = "2px solid greenyellow";
        return true;
    }
}

function validateGeneral() {
    "use strict";
    var gen = document.getElementById("gen");
    if (gen.checkValidity() === false && gen.value !== "") {
        document.getElementById("genmsg").innerHTML = "Format: Maximum 500 characters";
        return false;
    } else {
        isCorrect(document.getElementById("genmsg"));
        return true;
    }
}

function validateInitTitle() {
    "use strict";
    var iid = document.getElementById("iid");
    if (iid.checkValidity() === false && iid.value !== "") {
        document.getElementById("iidmsg").innerHTML = "Format: 4 to 20 characters";
        return false;
    } else {
        isCorrect(document.getElementById("iidmsg"));
        return true;
    }
}

function validateInitCat() {
    "use strict";
    var icat = document.getElementById("icat");
    if (icat.checkValidity() === false && icat.value !== "") {
        document.getElementById("icatmsg").innerHTML = "Format: 4 to 20 characters";
        return false;
    } else {
        isCorrect(document.getElementById("icatmsg"));
        return true;
    }
}

function validateInitDesc() {
    "use strict";
    var idesc = document.getElementById("idesc");
    if (idesc.checkValidity() === false && idesc.value !== "") {
        document.getElementById("idescmsg").innerHTML = "Format: maximum 5000 characters";
        return false;
    } else {
        isCorrect(document.getElementById("idescmsg"));
        return true;
    }
}

function validateForm() {
    "use strict";
    var flag = true;
    var i;
    var errors = document.getElementsByClassName("errmsg");
    for (i = 0; i < errors.length; i += 1) {
        if (errors[i].innerHTML !== "" && errors[i].style.color === "red" && errors[i].style.display !== "none") {
            flag = false;
            //console.log("VAL: Found wrong input on: " + i);
        }
    }
    if (flag === true) {
        //console.log("VAL: Found no wrong input, return true");
    }
    return flag;
}

function validateInput(iel, erel, erm, callback) { //wip
    "use strict";
    if (iel.checkValidity() === false && iel.value !== "") {
        erel.innerHTML = erm;
        return false;
    } else {
        isCorrect(erel);
        return true;
    }
}

function sendAjaxPOSTall() {
    "use strict";
    if (validateForm() === true) {
        var uid = document.registration.username.value;
        var pass1 = document.registration.password.value;
        pass1 = encryptPass(pass1);
        var pass2 = document.registration.password2.value;
        pass2 = encryptPass(pass2);
        var email = document.registration.email.value;
        var fnm = document.registration.firstname.value;
        var lnm = document.registration.lastname.value;
        var dob = document.registration.bday.value;
        var city = document.registration.city.value;
        var addr = document.registration.address.value;
        var genm = document.getElementById("male_button");
        var genf = document.getElementById("female_button");
        var geno = document.getElementById("other_button");
        var sex = (genm.checked ? genm.value : (genf.checked ? genf.value : geno.value));
        var ocp = document.registration.occupation.value;
        var intr = document.registration.interests.value;
        var gen = document.registration.generalinfo.value;
        var country = document.registration.country.value;
        var xhr = new XMLHttpRequest();
        xhr.onload = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var xhr2 = new XMLHttpRequest();
                xhr2.onload = function () {
                    if (xhr2.readyState === 4 && xhr2.status === 200) {
                        document.getElementById("navright").innerHTML = xhr2.responseText;
                        sendAjaxGETfornav("2regsuc");
                    } else if (xhr2.status === 400) {
                        alert(xhr2.responseText);
                    }
                };
                xhr2.open("POST", "loginServlet", true);
                xhr2.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xhr2.send("username=" + uid + "&password=" + pass1);
            } else if (xhr.status !== 200) {
                alert("Request Failed. Return Status: " + xhr.status + "\nError Message: " + xhr.responseText);
            }
        };
        xhr.open("POST", "memberServlet", true);
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhr.send("username=" + uid + "&pass1=" + pass1 + "&pass2=" + pass2 + "&email="
                + email + "&firstName=" + fnm + "&lastName=" + lnm + "&dateOfBirth="
                + dob + "&country=" + country + "&city=" + city + "&address=" + addr + "&gender=" + sex
                + "&occupation=" + ocp + "&interests=" + intr + "&general=" + gen);
    }
}

function update(data) {
    "use strict";
    if (validateForm() === true) {
        var xhr = new XMLHttpRequest();
        xhr.onload = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                getProfile();
            } else if (xhr.status !== 200) {
                alert("Request Failed. Return Status: " + xhr.status + "Error Message: " + xhr.responseText);
            }
        };
        if (data.name === "password") {
            data.value = encryptPass(data.value);
        }
        xhr.open("POST", "updateServlet", true);
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhr.send(data.name + "=" + data.value);
    }
}

function camtoggler() {
    var campop = document.getElementById('campop');
    if (campop.style.visibility === "visible") {
        campop.style.visibility = "hidden";
        return 0;
    } else {
        campop.style.visibility = "visible";
        return 1;
    }
}

function camhider() {
    "use strict";
    var camicon = document.getElementById("camera_icon");
    var cam = document.getElementById("campop");
    var loadicon = document.getElementById("loading_icon");
    cam.style.visibility = "hidden";
    camicon.style.display = "none";
    loadicon.style.display = "block";
}

function sendAjaxPOSTlogmini() {
    "use strict";
    var uid = document.loginmini.usernamemini.value;
    var pass = document.loginmini.passwordmini.value;
    pass = encryptPass(pass);
    var nr = document.getElementById("navright");
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            alert("Login Sucessful");
            nr.innerHTML = xhr.responseText;
            sendAjaxGETfornav("2homel");
        } else if (xhr.status === 400) {
            alert(xhr.responseText);
        }
    };
    xhr.open("POST", "loginServlet", true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("username=" + uid + "&password=" + pass);
}

function sendAjaxPOSTchecklogin() {
    "use strict";
    var nr = document.getElementById("navright");
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var cred = xhr.responseText;
            cred = cred.split(" ");
            xhr = new XMLHttpRequest();
            xhr.onload = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    nr.innerHTML = xhr.responseText;
                    sendAjaxGETfornav("2homel");
                } else if (xhr.status === 400) {
                    alert(xhr.responseText);
                }
            };
            xhr.open("POST", "loginServlet", true);
            xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhr.send("username=" + cred[0] + "&password=" + cred[1]);
        } else if (xhr.status === 400) {
            nr.innerHTML = xhr.responseText;
            sendAjaxGETfornav("2homeu");
            var xhr2 = new XMLHttpRequest();
            xhr2.onload = function () {
                if (xhr2.readyState === 4 && xhr2.state !== 500) {
                    nr.innerHTML = xhr2.responseText;
                } else if (xhr2.state === 500) {
                    alert("Internal Server Error " + xhr2.state);
                }
            };
            xhr2.open("GET", "pageServlet" + "?page=" + "navrunlogged.htm", true);
            xhr2.send();
        } else if (xhr.status === 500) {
            alert(xhr.responseText);
        }
    };
    xhr.open("POST", "entryServlet", true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send();
}

function sendAdminRequest() {
    "use strict";
    var adcone = document.getElementById("adminconsole");
    var adcon = adcone.value.split(" ");
    var toSend = "command=";
    if (adcon.length > 0) {
        toSend += adcon[0];
        switch (adcon[0]) {
            case("addUser"):
                if (adcon.length !== 4) {
                    alert("Wrong format for addUser. Format: addUser <username> <email> <occupation>");
                } else {
                    toSend += "&username=" + adcon[1] + "&email=" + adcon[2] + "&occupation=" + adcon[3];
                }
                break;
            case("deleteUser"):
            case("makeAdmin"):
                if (adcon.length !== 2) {
                    alert("Wrong format for " + adcon[0] + ". Format: " + adcon[0] + " <username>");
                } else {
                    toSend += "&username=" + adcon[1];
                }
                break;
            case("deleteInit"):
                if (adcon.length !== 3) {
                    alert("Wrong format for " + adcon[0] + ". Format: " + adcon[0] + " <Title/ID> <Title/initid>");
                } else {
                    toSend += "&delmode=" + adcon[1] + "&initid=" + adcon[2];
                }
        }
        var xhr = new XMLHttpRequest;
        xhr.onload = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                alert(xhr.responseText);
                adcone.innerHTML = "";
            } else if (xhr.status !== 200) {
                alert(xhr.status + " " + xhr.responseText);
                adcone.innerHTML = "";
            }
        };
        xhr.open("POST", "adminServlet", true);
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhr.send(toSend);
    }
}

function sendAjaxPOSTlogout() {
    "use strict";
    var navr = document.getElementById("navright");
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            alert("Logout Successful");
            sendAjaxGETfornav("2homeu");
            var xhr2 = new XMLHttpRequest();
            xhr2.onload = function () {
                if (xhr2.readyState === 4 && xhr2.state !== 500) {
                    navr.innerHTML = xhr2.responseText;
                } else if (xhr2.state === 500) {
                    alert("Internal Server Error " + xhr2.state);
                }
            };
            xhr2.open("GET", "pageServlet" + "?page=" + "navrunlogged.htm", true);
            xhr2.send();
        } else {
            alert("Error " + xhr.status);
        }
    };
    xhr.open("POST", "logoutServlet", true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send();
}

function getProfile() {
    "use strict";
    var mc = document.getElementById("mastercontainer");
    sendAjaxGETfornav("2profile");
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status !== 500) {
            mc.innerHTML = xhr.responseText;
        } else if (xhr.status === 500) {
            alert(xhr.responseText);
        }
    };
    xhr.open("POST", "profileServlet", true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send();
}

function getMembers() {
    "use strict";
    var mc = document.getElementById("mastercontainer");
    sendAjaxGETfornav("2members");
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status !== 500) {
            mc.innerHTML = xhr.responseText;
        } else if (xhr.status === 500) {
            alert(xhr.responseText);
        }
    };
    xhr.open("POST", "userlistServlet", true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send();
}

function getHeatmap(initid) {
    "use strict";
    var mc = document.getElementById("mastercontainer");
    var xhr = new XMLHttpRequest();
    sendAjaxGETfornav("mappage");
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status !== 500) {
            mc.innerHTML = "<p class = \"errmsg\" id =\"adrmsg\">Heatmap</p><div id =\"mapcontainer\">" +
        "<input type =\"button\" value = \"Show Map\" id = \"mapbutton\" onclick = \"mapButton()\"/>" +
        "\<div id =\"map\"></div>" +
        "</div>";
            var res = xhr.responseText;
            
        } else if (xhr.status === 500) {
            alert(xhr.responseText);
        }
    };
    xhr.open("POST", "mappageServlet", true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("initid=" + initid); 
}

function getInitiatives() {
    "use strict";
    var mc = document.getElementById("mastercontainer");
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status !== 500) {
            var xhr2 = new XMLHttpRequest();
            xhr2.onload = function () {
                if (xhr2.readyState === 4 && xhr.state !== 500) {
                    mc.innerHTML = xhr2.responseText;
                    var list = document.getElementById("initiative_list");
                    list.innerHTML = xhr.responseText;
                    sendAjaxGETfornav("2initiatives");
                } else if (xhr.state === 500) {
                    alert("Internal Server Error");
                }
            };
            xhr2.open("GET", "pageServlet" + "?page=mcinitiatives.htm", true);
            xhr2.send();
        } else if (xhr.status === 500) {
            alert("Internal Server Error");
        }
    };
    xhr.open("POST", "getInitServlet", true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("mode=user");
}

function getUserInitiatives() {
    "use strict";
    var list = document.getElementById("initiative_list");
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.state !== 500) {
            list.innerHTML = xhr.responseText;
        } else if (xhr.state === 500) {
            alert("Internal Server Error");
        }
    };
    xhr.open("POST", "getInitServlet", true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("mode=user");
}

function getActiveInitiatives() {
    "use strict";
    var list = document.getElementById("initiative_list");
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.state !== 500) {
            list.innerHTML = xhr.responseText;
            document.getElementById("myinit_search").value = ""; //clear searchbar
        } else if (xhr.state === 500) {
            alert("Internal Server Error");
        }
    };
    xhr.open("POST", "getInitServlet", true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("mode=active");

}

function getClosedInitiatives() {
    "use strict";
    var list = document.getElementById("initiative_list");
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.state !== 500) {
            list.innerHTML = xhr.responseText;
            document.getElementById("myinit_search").value = ""; //clear searchbar
        } else if (xhr.state === 500) {
            alert("Internal Server Error");
        }
    };
    xhr.open("POST", "getInitServlet", true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("mode=ended");
}

function sendAjaxPOSTnewinit() {
    "use strict";
    if (validateForm() === true) {
        var iid = document.newinitform.inittitle.value;
        var icat = document.newinitform.initcat.value;
        var idesc = document.newinitform.initdesc.value;
        var xhr = new XMLHttpRequest();
        xhr.onload = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                getInitiatives();
            } else if (xhr.status === 500) {
                alert("Internal Server Error");
            }
        };
        xhr.open("POST", "getInitServlet", true);
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhr.send("mode=add" + "&category=" + icat + "&title=" + iid + "&description=" + idesc);
    }
}

function updateInitiative(field, phase) { //wip
    "use strict";
    switch (phase) {
        case("before"):
            document.getElementsByClassName("initiative_" + field)[0].style.display = "none";
            document.getElementsByClassName("initiative_" + field + "_edit")[0].style.display = "block";
            break;
        case("after"):
            if (document.getElementsByClassName("initiative_" + field + "_edit")[0].checkValidity() === true) {
                //ajax request
                getUserInitiatives();
            } else {
                document.getElementsByClassName("initiative_" + field)[0].style.display = "block";
                document.getElementsByClassName("initiative_" + field + "_edit")[0].style.display = "none";
            }
            break;
    }
}

function sendAjaxPOSTeditinit(i, param) {
    "use strict";
    var initid = document.getElementById("id" + i);
    var iid = document.getElementsByClassName("iid");
    var icat = document.getElementsByClassName("icat");
    var idesc = document.getElementsByClassName("idesc");
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            getUserInitiatives();
        } else if (xhr.status === 500) {
            alert("Internal Server Error");
        } else if (xhr.status === 400) {
            alert(xhr.responseText);
        }
    };
    xhr.open("POST", "getInitServlet", true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("mode=update" + "&initid=" + initid.innerHTML + "&category=" + icat[param].value + "&title=" + iid[param].value + "&description=" + idesc[param].value);
}

function sendAjaxPOSTdelinit(param) {
    "use strict";
    var initid = document.getElementById("id" + param).innerHTML;
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            getUserInitiatives();
        } else if (xhr.status === 500) {
            alert("Internal Server Error");
        }
    };
    xhr.open("POST", "getInitServlet", true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("mode=delete" + "&initid=" + initid);
}

function sendAjaxPOSTactivate(i, param) {
    "use strict";
    var initid = document.getElementById("id" + i).innerHTML;
    var date = document.getElementsByClassName("initdate");
    var time = document.getElementsByClassName("inittime");
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            getUserInitiatives();
            alert("Initiative " + initid + " activated"); //DEBUG alert
        } else if (xhr.status === 500) {
            alert("Internal Server Error");
        } else if (xhr.status === 400) {
            alert(xhr.responseText);
        }
    };
    xhr.open("POST", "getInitServlet", true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("mode=activate" + "&initid=" + initid + "&date=" + date[param].value + "&time=" + time[param].value);
}

//this functions is called when the user clicks either the upvote
//or the downvote button and sends his vote to the server
//the function also checks for invalid input of the parameter vote
function sendVote(param, j, vote) {
    "use strict";
    var initid = document.getElementById("id" + param).innerHTML;
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var upv = (document.getElementsByClassName("initUpv"))[j].style;
            var dnv = (document.getElementsByClassName("initDownv"))[j].style;
            if (vote === "true") {
                upv.border = "2px solid #93d7ff";
                upv.borderRadius = "7px";
                dnv.border = "2px solid transparent";
            } else {
                dnv.border = "2px solid #93d7ff";
                dnv.borderRadius = "7px";
                upv.border = "2px solid transparent";
            }
        } else if (xhr.status === 500) {
            alert("Internal Server Error");
        } else if (xhr.status === 400) {
            alert(xhr.responseText);
        }
    };
    //check for invalid vote input
    if (vote !== "true" && vote !== "false") {
        alert("invalid vote" + vote);
        return;
    }

    xhr.open("POST", "VoteServlet", true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("vote=" + vote + "&initid=" + initid);
}

//searches for the initiative with the same title as the one from the 
//search bar depending on which page the user is on (myinitiatives/active/ended)
function searchInitiative() {
    "use strict";
    var query = document.getElementById("myinit_search").value; //search parameter
    var mode = document.getElementById("initpage_select").value; //page mode

    var list = document.getElementById("initiative_list");
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.state !== 500) {
            list.innerHTML = xhr.responseText;
        } else if (xhr.state === 500) {
            alert("Internal Server Error");
        }
    };
    xhr.open("POST", "getInitServlet", true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");


    if (mode === "my_init") { //user
        xhr.send("mode=search" + "&searchMode=user" + "&query=" + query);
    } else if (mode === "active_init") { //active
        xhr.send("mode=search" + "&searchMode=active" + "&query=" + query);
    } else if (mode === "closed_init") { //ended
        xhr.send("mode=search" + "&searchMode=ended" + "&query=" + query);
    } else {
        alert("undefined page mode");
    }
}

function addDelegatorPost(initid, delegatortxtID) {
    console.log("delegatortxtID= " + "delegatortxt"+delegatortxtID + " initid= " +initid);
    var delegatortxt = document.getElementById("delegatortxt"+delegatortxtID).value;
    var xhr = new XMLHttpRequest();

    console.log("Add delegator " + delegatortxt + " at initiative " + initid);
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.state !== 500) {
            alert( xhr.responseText);
        } else if (xhr.state === 500) {
            alert("Internal Server Error");
        }
    };
    xhr.open("POST", "AddDelegator", true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("initID="+initid +"&delegatorName=" + delegatortxt);

}

function sendAjaxGETfornav(mode) {
    "use strict";
    var xhr = new XMLHttpRequest();
    var xhr2 = new XMLHttpRequest();
    var xhr3 = new XMLHttpRequest();
    var nl = document.getElementById("navleft");
    var nc = document.getElementById("navcenter");
    var mc = document.getElementById("mastercontainer");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.state !== 500) {
            if (xhr.responseText !== "") {
                nl.innerHTML = xhr.responseText;
            }
        } else if (xhr.state === 500) {
            alert("Internal Server Error " + xhr.state);
        }
    };
    xhr2.onreadystatechange = function () {
        if (xhr2.readyState === 4 && xhr2.state !== 500) {
            if (xhr2.responseText !== "") {
                nc.innerHTML = xhr2.responseText;
            }
        } else if (xhr2.state === 500) {
            alert("Internal Server Error " + xhr2.state);
        }
    };
    xhr3.onreadystatechange = function () {
        if (xhr3.readyState === 4 && xhr3.state !== 500) {
            if (xhr3.responseText !== "") {
                mc.innerHTML = xhr3.responseText;
            }
        } else if (xhr3.state === 500) {
            alert("Internal Server Error " + xhr3.state);
        }
    };
    switch (mode) {
        case("home2reg"):
            xhr.open("GET", "pageServlet", true);
            xhr2.open("GET", "pageServlet" + "?page=" + "navcreg.htm", true);
            xhr3.open("GET", "pageServlet" + "?page=" + "mcregister.htm", true);
            break;
        case("2members"):
            xhr.open("GET", "pageServlet", true);
            xhr2.open("GET", "pageServlet" + "?page=" + "navcmembers.htm", true);
            xhr3.open("GET", "pageServlet", true);
            break;
        case("2homeu"):
            xhr.open("GET", "pageServlet" + "?page=" + "navlhome.htm", true);
            xhr2.open("GET", "pageServlet" + "?page=" + "navchome.htm", true);
            xhr3.open("GET", "pageServlet" + "?page=" + "mchome.htm", true);
            break;
        case("2homel"):
            xhr.open("GET", "pageServlet" + "?page=" + "navllogged.htm", true);
            xhr2.open("GET", "pageServlet" + "?page=" + "navchome.htm", true);
            xhr3.open("GET", "pageServlet" + "?page=" + "mchome.htm", true);
            break;
        case("2home"):
            xhr.open("GET", "pageServlet", true);
            xhr2.open("GET", "pageServlet" + "?page=" + "navchome.htm", true);
            xhr3.open("GET", "pageServlet" + "?page=" + "mchome.htm", true);
            break;
        case("2profile"):
            xhr.open("GET", "pageServlet", true);
            xhr2.open("GET", "pageServlet" + "?page=" + "navcprofile.htm", true);
            xhr3.open("GET", "pageServlet", true);
            break;
        case("2initiatives"):
            xhr.open("GET", "pageServlet", true);
            xhr2.open("GET", "pageServlet" + "?page=" + "navcinitiatives.htm", true);
            xhr3.open("GET", "pageServlet", true);
            break;
        case("2newinit"):
            xhr.open("GET", "pageServlet", true);
            xhr2.open("GET", "pageServlet" + "?page=" + "navcnewinit.htm", true);
            xhr3.open("GET", "pageServlet" + "?page=" + "mcnewinit.htm", true);
            break;
        case("2initedit"):
            xhr.open("GET", "pageServlet", true);
            xhr2.open("GET", "pageServlet" + "?page=" + "navcinitedit.htm", true);
            xhr3.open("GET", "pageServlet" + "?page=" + "mcinitedit.htm", true);
            break;
        case("2regsuc"):
            xhr.open("GET", "pageServlet" + "?page=" + "navllogged.htm", true);
            xhr2.open("GET", "pageServlet" + "?page=" + "navcregsuc.htm", true);
            xhr3.open("GET", "pageServlet" + "?page=" + "mcregsuc.htm", true);
            break;
        case("mappage"): 
            xhr.open("GET", "pageServlet", true);
            xhr2.open("GET", "pageServlet" + "?page=" + "navcregsuc.htm", true);
            xhr3.open("GET", "pageServlet", true);
        default:
            xhr.open("GET", "pageServlet", true);
            xhr2.open("GET", "pageServlet", true);
            xhr3.open("GET", "pageServlet", true);
    }
    xhr.send();
    xhr2.send();
    xhr3.send();
}