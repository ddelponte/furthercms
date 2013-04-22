$("#furtherCmsNavTree").jstree({
    "crrm": {
        "move": {
            "check_move": function (m) { // All moves are allowed.  Read more at http://www.jstree.com/documentation/core.html#_get_move
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

            var newParentNode = data.rslt.r;
            var newParentNodeId = newParentNode.attr("id");
            var parentId = newParentNodeId.substr(newParentNodeId.indexOf("_") + 1);

            moveCategory(id, parentId);

        });

/**
 * Makes an ajax call which persists the move in the database
 * @param id id of Category being moved
 * @param parentId id of Category the node is being moved to
 */
function moveCategory(id, parentId) {
    var categoryMoveUrl = jQuery("div[data-category-move-action]").attr("data-category-move-action");
    categoryMoveUrl = categoryMoveUrl + "?" + "id=" + id + "&parentId=" + parentId;

    jQuery.ajax(categoryMoveUrl)
            .done(function () {
                alert("success");
            })
            .fail(function () {
                alert("error");
            })
}