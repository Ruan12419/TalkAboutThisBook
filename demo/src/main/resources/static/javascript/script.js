// This variable sets if the content of the discussions will reload or not
var reload = true;

// Global variable to be used on two functions
var discussionId;

// When  the 'content' class is ready if loops every 12 seconds to reload itself and show new contents from server if any
$(".content").ready(function () {
    setInterval(function () {
        if (reload) {
            $(".content").load(location.href + " .content");
            console.log("RELOADED");
        }

    }, 12000);
});

// When the input text to post a comment is focused this function  will stop the 'content' class reload
function stopReload() {
    reload = false;

    // When  the user submit the form this event will get caught and prevent the default behavior of the form
    $("#postComment").submit(function (event) {
        event.preventDefault();
        console.log("prevented");
        // Functions to send the form information and show the new comment
        sendForm();
        getComment(discussionId);

        // Reload 'content' class
        $(".content").load(location.href + " .content");

        // 'reload' variable back to true
        reload = true;
    });
}

// When the input text to post a comment loses focus this function will set the 'reload' variable back to true
function reloadAgain() {
    reload = true;
}

// Instantiate XMLHttpRequest
function buildRequest() {
    try {
        var request = new XMLHttpRequest();
    } catch (IEAtual) {
        try {
            var request = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (IEAntigo) {
            try {
                var request = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (fail) {
                var request = false;
            }
        }
    }
    if (!request) {

        // Alert the user if his browser does not support ajax
        alert("Your Browser Do Not Support Ajax!");
    } else {
        return request;
    }
}

// This function will take the values from the form, set the configurations for the ajax and send the POST method to server
function sendForm() {
    var id = document.querySelector("#id").value;
    var parentId = document.querySelector("#parentId").value
    var text = document.querySelector("#text").value


    var form = document.querySelector("#postComment");

    var xhr = buildRequest();

    // Async set to false
    xhr.open(form.method, form.action, true);

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    // Build the json data
    var json = {
        "id": id,
        "parentId": parentId,
        "text": text
    };


    $.ajax({
        type: "POST",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token)
        },
        url: "/comment-discussion",
        data: json,
        dataType: "json",
        timeout: 100000,
        success: function (data) {
            console.log("SUCCESS: ", data);
            $('#info').html(data);
        },
        error: function (e) {
            console.log("ERROR: ", e);

        },
        done: function (e) {
            console.log("DONE");
        }
    });
    discussionId = id;
}

// This variable will take the data returned on the function below
var comment;

// Send GET request to server to get the new comment
function getComment(id) {
    $.get("/comment?discussionId=" + id, function (data) {
        comment = data[data.length - 1];
        addComment();
    });
}

// Clone the last comment if any and past the data of new comment
function addComment() {

    // Takes the id of the comment before the new one
    var id = comment.id - 1;

    var original = document.getElementById("#comment-" + id);
    if (original != null) {
        var clone = original.cloneNode(true);
        clone.id = "#comment-" + comment.id;
        clone.firstElementChild.firstElementChild.childNodes[3].innerHTML = comment.text;
        clone.firstElementChild.firstElementChild.childNodes[1].firstElementChild.innerHTML = comment.user.firstName;
        clone.firstElementChild.firstElementChild.childNodes[1].lastChild.innerHTML = comment.user.lastName;
        clone.onclick = sendForm;
        original.parentNode.appendChild(clone);
    }
}

function userInfo() {
    $(".user-options").attr("hidden", false);
}