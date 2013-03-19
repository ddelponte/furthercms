// When user selects a module type from the module type panel, add it to the end of the modules in the main content area
jQuery("#available-module-types a").click(function (event) {

    event.preventDefault();
    alert(jQuery(this).attr("data-module-type-id"));

    // Insert the a new instance of the module editor into the content
});