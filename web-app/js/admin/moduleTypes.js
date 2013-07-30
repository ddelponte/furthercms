// When user selects a module type from the module type panel, add it to the end of the modules in the main content area
jQuery("#available-module-types a").click(function (event) {

    event.preventDefault();
    var url = jQuery(this).attr("data-render-module-edit-action");

    var jqxhr = jQuery.get(url, function (data) {

    })
            .done(function (data) {
                var sectionStart = '<section class="module" data-module-name="HTML" data-module-id="3">';
                var moduleList = jQuery("section#modules-edit ul");
                moduleList.append(jQuery(data));
            })
            .fail(function () {
                alert("error");
            })

});