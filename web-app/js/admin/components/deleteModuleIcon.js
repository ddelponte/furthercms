jQuery("section#modules-edit section.module img.delete").click(function (event) {
    event.preventDefault();
    var moduleId = jQuery(this).closest("section").attr("data-module-id");
    var listItem = jQuery(this).closest("li");
    listItem.hide();

    // Modify the hidden text field, which contains JSON representation of modules to delete, by adding the selected module
    var modulesToDeleteHiddenField = jQuery('section#modules-edit section.module input[type=hidden]#modulesToDelete');
    var jsonObj = jQuery.parseJSON(modulesToDeleteHiddenField.val());
    if (jsonObj != null) {
        jsonObj[moduleId] = "delete";
        jQuery(modulesToDeleteHiddenField).val(JSON.stringify(jsonObj));
    }

});