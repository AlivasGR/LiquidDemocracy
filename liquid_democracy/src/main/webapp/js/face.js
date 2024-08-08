"use strict";
/*
 Author: Panagiotis Papadakos papadako@csd.uoc.gr
 For the needs of the hy359 2017 course
 University of Crete
 */

/*  face recognition that is based on faceplusplus service */
var faceRec = (function () {
    // Object that holds anything related with the facetPlusPlus REST API Service
    var faceAPI = {
        apiKey: "l2jNgKbk1HXSR4vMzNygHXx2g8c_xT9c",
        apiSecret: "2T6XdZt4EYw-I7OhmZ6g1wtECl81e_Ip",
        app: "hy359",
        // Detect
        // https://console.faceplusplus.com/documents/5679127
        detect: "https://api-us.faceplusplus.com/facepp/v3/detect", // POST
        // Set User ID
        // https://console.faceplusplus.com/documents/6329500
        setuserId: "https://api-us.faceplusplus.com/facepp/v3/face/setuserid", // POST
        // Get User ID
        // https://console.faceplusplus.com/documents/6329496
        getDetail: "https://api-us.faceplusplus.com/facepp/v3/face/getdetail", // POST
        // addFace
        // https://console.faceplusplus.com/documents/6329371
        addFace: "https://api-us.faceplusplus.com/facepp/v3/faceset/addface", // POST
        // Search
        // https://console.faceplusplus.com/documents/5681455
        search: "https://api-us.faceplusplus.com/facepp/v3/search", // POST
        // Create set of faces
        // https://console.faceplusplus.com/documents/6329329
        create: "https://api-us.faceplusplus.com/facepp/v3/faceset/create", // POST
        // update
        // https://console.faceplusplus.com/documents/6329383
        update: "https://api-us.faceplusplus.com/facepp/v3/faceset/update", // POST
        // removeface
        // https://console.faceplusplus.com/documents/6329376
        removeFace: "https://api-us.faceplusplus.com/facepp/v3/faceset/removeface"// POST
    };

    // Object that holds anything related with the state of our append
    // Currently it only holds if the snap button has been pressed
    var state = {
        photoSnapped: false
    };

    // function that returns a binary representation of the canvas
    function getImageAsBlobFromCanvas(canvas) {
        // function that converts the dataURL to a binary blob object
        function dataURLtoBlob(dataUrl) {
            // Decode the dataURL
            var binary = atob(dataUrl.split(",")[1]);

            // Create 8-bit unsigned array
            var array = [];
            var i;
            for (i = 0; i < binary.length; i += 1) {
                array.push(binary.charCodeAt(i));
            }

            // Return our Blob object
            return new Blob([new Uint8Array(array)], {
                type: "image/jpg"
            });
        }

        var fullQuality = canvas.toDataURL("image/jpeg", 1.0);
        return dataURLtoBlob(fullQuality);

    }

    // function that returns a base64 representation of the canvas
    function getImageAsBase64FromCanvas(canvas) {
        // return only the base64 image not the header as reported in issue #2
        return canvas.toDataURL("image/jpeg", 1.0).split(",")[1];

    }

    // Function called when we upload an image
    function uploadImage(mode) {
        if (state.photoSnapped) {
            var canvas = document.getElementById(mode === "norm" ? "canvas" : "canvasmini");
            var image = getImageAsBlobFromCanvas(canvas);
            var data = new FormData();
            data.append("api_key", faceAPI.apiKey);
            data.append("api_secret", faceAPI.apiSecret);
            data.append("image_file", image);
            if (mode === "norm") {
                ajaxRequest("POST", faceAPI.detect, data);
            } else {
                data.append("outer_id", "hy359");
                ajaxRequest("POST", faceAPI.search, data);
            }
        } else {
            alert("No image has been taken!");
        }
    }

    // Function for initializing things (event handlers, etc...)
    function init(mode) {
        // Grab elements, create settings, etc.
        var canvas = document.getElementById(mode === "norm" ? "canvas" : "canvasmini");
        var context = canvas.getContext("2d");
        var video = document.getElementById(mode === "norm" ? "video" : "videomini");
        var mediaConfig = {
            video: true
        };
        var errBack = function (e) {
            console.log("An error has occurred!", e);
        };
        
        // Put video listeners into place
        if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
            navigator.mediaDevices.getUserMedia(mediaConfig).then(function (stream) {
                video.srcObject = stream;
                video.onloadedmetadata = function (e) {
                    video.play();
                };
            });
        }   
        // Trigger photo take
        document.getElementById(mode === "norm" ? "video" : "videomini").addEventListener("click", function () {
            context.drawImage(video, 0, 0, mode === "norm" ? 320 : 160, mode === "norm" ? 240 : 120);
            state.photoSnapped = true; // photo has been taken
            uploadImage(mode);
        });
    }
    /*
     * Parse input and act according to url if status is 200
     * Initiates a pipeline of calls of the form:
     * ajaxRequest->detectManager->ajaxRequest->setUserManager->ajaxRequest
     * If anything goes wrong in this pipeline, the function that detected the error exits without calling
     * the followup function, thus breaking the chain. Also implemented is search mode that is unrelated to the pipeline and is invoked at login time
     */
    function ajaxRequest(method, url, data) {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            console.log("FACEPP: State: " + this.readyState);
            if (this.readyState === 4 && this.status === 200) {
                console.log("FACEPP: Request successful");
                var results = JSON.parse(this.responseText);
                console.log(results);
                if (url === faceAPI.detect) {
                    console.log("FACEPP: Detect Mode");
                    if (results.faces.length === 0) {
                        alert("No Face Detected!");
                        return -1;
                    } else {
                        detectManager(data, results.faces[0].face_token);
                        return 1;
                    }
                }
                if (url === faceAPI.setuserId) {
                    console.log("FACEPP: setuserID mode");
                    setuserManager(data, results.face_token);
                    return 1;
                }
                if (url === faceAPI.addFace) {
                    console.log("FACEPP: addFace mode");
                    if (results.failure_detail.length > 0) {
                        alert("Server-side Error");
                        return -1;
                    } else {
                        document.getElementById("faceresult").innerHTML = "Face Recognition has been Set";
                        document.getElementById("facecont").style.display = "none";
                        return 1;
                    }
                }
                if (url === faceAPI.search) {
                    console.log("FACEPP: search mode");
                    if (results.results !== undefined) {
                        var temp = results.results[0];
                        var i;
                        for (i = 1; i < results.results.length; i += 1) {
                            if (results.results[i].confidence > temp.confidence) {
                                temp = results.results[i];
                            }
                        }
                        document.getElementById("loading_icon").style.display = "none";
                        document.loginmini.uidmini.value = temp.user_id;
                    } else {
                        alert("No face was detected");
                        document.getElementById("loading_icon").style.display = "none";
                        document.getElementById("camera_icon").style.display = "block";
                        document.loginmini.uidmini.value = "";
                    }
                    return 1;
                }
            }
        };
        xhttp.open(method, url, true);
        xhttp.send(data);
    }

    /* Preconditions: Detect successful, data contains apikey, apisecret, image_file
     * Postconditions: data contains apikey, apisecret, face_token, user_id */
    function detectManager(data, face_token) {
        data.delete("image_file");
        data.append("face_token", face_token);
        data.append("user_id", document.registration.username.value);
        ajaxRequest("POST", faceAPI.setuserId, data);
    }

    /* Preconditions: setuserId successful, data contains apikey, apisecret, face_token, user_id
     * Postconditions: data contains outer_id, face_tokens (only 1) */
    function setuserManager(data, face_token) {
        data.delete("user_id");
        data.delete("face_token");
        data.append("outer_id", "hy359");
        data.append("face_tokens", face_token);
        ajaxRequest("POST", faceAPI.addFace, data);
    }

    return {
        init: init
    };

}());

