jQuery("a.reorder").click(function (event) {
    event.preventDefault();
    openReorderModulesDialog();
});

var sortableModules = jQuery("#modules-edit ul li section.module");

var reorderModulesDialogForm = jQuery("div#reorder-modules-dialog-form");

var sortableModulesDialogList = jQuery("div#reorder-modules-dialog-form ul.sortable");

/**
 *
 * @param modulesList List of jQuery objects representing sortable modules
 */
function openReorderModulesDialog() {
    // update dialog
    sortableModulesDialogList.html("");

    jQuery.each(sortableModules, function (key, value) {
        var startTag = '<li class="ui-state-default" data-module-id="' + jQuery(value).attr("data-module-id") + '"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>';
        var endTag = '</li>'
        sortableModulesDialogList.append(startTag + jQuery(value).attr("data-module-name") + endTag);
    });

    reorderModulesDialogForm.dialog("open");
}

/**
 * The module reorder dialog
 */
reorderModulesDialogForm.dialog({
    autoOpen: false,
    modal: true,
    buttons: {
        Cancel: function () {
            $(this).dialog("close");
        },
        "Save": function () {
            // Update form display order values
            jQuery.each(sortableModules, function (key, value) {
                var moduleId = jQuery(value).attr("data-module-id");
                var displayOrderInput = jQuery('section[data-module-id="' + moduleId + '"] form input[name="displayOrder"]');
                displayOrderInput.val(key);
            });

            reorderModulesDialogForm.dialog("close");
            saveModules(true);

        }
    },
    close: function () {
        //Do stuff
    }
});

sortableModulesDialogList.sortable({
    forcePlaceholderSize: true,
    axis: 'y',
    start: function (event, ui) {

        jQuery(this).sortable('refreshPositions');

    },
    stop: function (event, ui) {

    }
});

sortableModulesDialogList.disableSelection();