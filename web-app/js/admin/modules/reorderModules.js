jQuery("a.reorder").click(function (event) {
    event.preventDefault();
    openReorderModulesDialog();
});

var sortableModules = jQuery("#modules-edit ul li section.module");

var reorderModulesDialogForm = jQuery("div#reorder-modules-dialog-form");

var sortableModulesList = jQuery("div#reorder-modules-dialog-form ul.sortable");

/**
 *
 * @param modulesList List of jQuery objects representing sortable modules
 */
function openReorderModulesDialog() {
    // update dialog
    var startTag = '<li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>';
    var endTag = '</li>'
    sortableModulesList.html("");

    jQuery.each(sortableModules, function (key, value) {
        // update dialog
        sortableModulesList.append(startTag + jQuery(value).attr("data-module-name") + endTag);
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

            // Save the module ids as a JSON object in the hidden field in their new sort order
        }
    },
    close: function () {
        //Do stuff
    }
});

sortableModulesList.sortable({
    forcePlaceholderSize: true,
    axis: 'y',
    start: function (event, ui) {

        jQuery(this).sortable('refreshPositions');

    },
    stop: function (event, ui) {

    }
});

sortableModulesList.disableSelection();