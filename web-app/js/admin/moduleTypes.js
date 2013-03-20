// When user selects a module type from the module type panel, add it to the end of the modules in the main content area
jQuery("#available-module-types a").click(function (event) {

    event.preventDefault();
    var url = jQuery(this).attr("data-render-module-edit-action");
    jQuery('body').load(url);

    // Insert the a new instance of the module editor into the content
});