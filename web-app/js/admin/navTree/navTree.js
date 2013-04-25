jQuery("#furtherCmsNavTree").jstree({
    "crrm": {
        "move": {
            "check_move": function (m) { // Read more at http://www.jstree.com/documentation/core.html#_get_move
                return true;
            }
        }
    },
    "dnd": {
        "drop_target": false,
        "drag_target": false
    },
    "plugins": [ "themes", "html_data", "cookies", "crrm", "dnd" ]
}).bind("move_node.jstree", function (event, data) { // Tree node move has completed

            var movedNode = data.rslt.o;
            var movedNodeId = movedNode.attr("id");
            var id = movedNodeId.substr(movedNodeId.indexOf("_") + 1);

            var newParentNode = data.rslt.np;
            var newParentNodeId = newParentNode.attr("id");
            var parentId = newParentNodeId.substr(newParentNodeId.indexOf("_") + 1);

            var rollbackObject = data.rlbk;
            var positions = jQuery.parseJSON("{}");

            jQuery.each(jQuery("li#" + newParentNodeId + " ul li"), function (index, value) {
                var nodeId = jQuery(value).attr("id");
                var categoryId = nodeId.substr(nodeId.indexOf("_") + 1);
                positions[index] = categoryId;
            });

            moveCategory(id, parentId, rollbackObject, JSON.stringify(positions));

        });

/**
 * Makes an ajax call which persists the move in the database
 * @param id id of Category being moved
 * @param parentId id of Category the node is being moved to
 * @param rollbackObject this object will be used to rollback invalid moves
 * @param positions JSON representation of the moved node and its siblings, properly ordered in the form {position: category.id}.  For example: "{0:1, 1:2, 2:88}"
 */
function moveCategory(id, parentId, rollbackObject, positions) {
    var categoryMoveUrl = jQuery("div[data-category-move-action]").attr("data-category-move-action");
    categoryMoveUrl = categoryMoveUrl + "?" + "id=" + id + "&parentId=" + parentId + "&positions=" + encodeURIComponent(positions);

    jQuery.ajax(categoryMoveUrl)
            .done(function (jsonData) {
                if (!jsonData.success) {
                    alert(jsonData.message)
                    jQuery.jstree.rollback(rollbackObject);
                }
            })
            .fail(function (jsonData) {
                alert(jsonData.message)
                jQuery.jstree.rollback(rollbackObject);
            })
}