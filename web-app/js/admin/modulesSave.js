jQuery(document).ready(function () {

    var moduleForms = jQuery("section#main-editable-content form");

    $("section#main-editable-content a.btn").click(function (event) {
        event.preventDefault();
        jQuery.each(moduleForms, function () {
            jQuery(this).ajaxSubmit();
            return false;
        });
    });



    var moduleFormInputs = jQuery("section#main-editable-content form input");
    moduleFormInputs.change(function () {
        //alert($(this).attr("name"));
    });

});