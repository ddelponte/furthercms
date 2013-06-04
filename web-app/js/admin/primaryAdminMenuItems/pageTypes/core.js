jQuery("form a.save").click(function (event) {
    event.preventDefault();
    var form = jQuery("#pageTypeForm");
    var actionUrl = form.attr("data-update-action-url");
    form.attr("action", actionUrl);
    form.submit();
});

jQuery("form a.delete").click(function (event) {
    var form = jQuery("#pageTypeForm");
    event.preventDefault();
    var deleteActionConfirmMessage = form.attr("data-delete-action-confirm-message");
    var confirmDelete = confirm(deleteActionConfirmMessage);

    if (confirmDelete) {
        var actionUrl = form.attr("data-delete-action-url");
        form.attr("action", actionUrl);
        form.submit()
    }

});

jQuery(".sortable").sortable({
    cursor: "move",
    forceHelperSize: true,
    forcePlaceholderSize: true,
    dropOnEmpty: true,
    connectWith: ".connectedSortable",
    receive: function (event, ui) {

        var receivingElement = jQuery(this);
        var receivingElementHiddenField = receivingElement.find(":hidden")
        var receivingElementHiddenFieldValue = receivingElementHiddenField.val();

        var sendingElement = ui.sender;
        var sendingElementHiddenField = sendingElement.find(":hidden");
        var sendingElementHiddenFieldValue = sendingElementHiddenField.val();
        var itemId = ui.item.attr("data-id");
        var itemValue;

        var sendingElementHiddenFieldJsonObj = jQuery.parseJSON(sendingElementHiddenFieldValue);
        if (sendingElementHiddenFieldJsonObj != null) {
            itemValue = sendingElementHiddenFieldJsonObj[itemId];
            delete sendingElementHiddenFieldJsonObj[itemId];
            jQuery(sendingElementHiddenField).val(JSON.stringify(sendingElementHiddenFieldJsonObj));
        }

        var receivingElementHiddenFieldJsonObj = jQuery.parseJSON(receivingElementHiddenFieldValue);
        if (receivingElementHiddenFieldJsonObj != null) {
            receivingElementHiddenFieldJsonObj[itemId] = itemValue;
            jQuery(receivingElementHiddenField).val(JSON.stringify(receivingElementHiddenFieldJsonObj));
        }

        applyActiveListOrder();

    }
}).disableSelection();

jQuery("form ul#ActiveModuleTypes").sortable({
    stop: function (event, ui) {
        applyActiveListOrder();
    }
});

/**
 * Updates the JSON data so that it corresonds to the order of the li elements
 */
function applyActiveListOrder() {

    var activeModuleTypeListItems = jQuery("form ul#ActiveModuleTypes").find("li");
    var activeModuleTypesHiddenField = jQuery("form ul#ActiveModuleTypes").find(":hidden");
    var jsonString = "{";

    activeModuleTypeListItems.each(function (index) {

        var comma = "";
        if (index > 0) {
            comma = ",";
        }

        var itemId = jQuery(this).attr("data-id")
        jsonString += comma + '"' + itemId + '":"Active"'

    });

    jsonString += "}"

    jQuery(activeModuleTypesHiddenField).val(jsonString);
}