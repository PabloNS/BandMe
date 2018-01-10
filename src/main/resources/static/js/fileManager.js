$(document).ready(function () {
    $("#btnSubmit").click(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        doAjax();
    });
});

function doAjax() {
    // Get form
    var form = $('#fileUploadForm')[0];
    var data = new FormData(form);

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "file/upload",
        data: data,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        success: function (data) {
            $("#result").text(data);
            loadImage();
        },
        error: function (e) {
            $("#result").text(e.responseText);
        }
    });
};

function loadImage() {
   $.ajax({
        type: "GET",
        url: "profilePicture",
        success: function (data) {
           document.getElementById("profilePicture").src = "data:image/png;base64," + data;
        }
    });
};
