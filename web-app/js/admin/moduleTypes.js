// When user selects a module type from the module type panel, add it to the end of the modules in the main content area
jQuery("#available-module-types a").click(function (event) {

    event.preventDefault();
    var url = jQuery(this).attr("data-render-module-edit-action");

//    jQuery.get(url, function (data) {
////        $('.result').html(data);
//        alert(data);
//    });

    var jqxhr = jQuery.get(url, function (data) {
        alert(data);
    })
            .done(function (data) {
                alert(data);
            })
            .fail(function () {
                alert("error");
            })
            .always(function () {
                alert("finished");
            });

    // Insert the a new instance of the module editor into the content
});