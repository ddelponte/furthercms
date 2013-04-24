<r:require modules="navTree"/>

<div id="furtherCmsNavTree" ${style ? "style=\"$style\"" : ""} ${cssClass ? "class=\"$cssClass\"" : ""}>

    <g:each in="${nodes}" var="child">

        <ul>

            <li id="category_${child?.id}">

                <a href="${g.createLink(controller: 'admin', action: 'edit', id: child?.id)}"
                   class="${selectedNodeId == child?.id?.toString() ? 'jstree-clicked' : ''}">${child?.page?.title}</a>

                <fc:navTreeChildren category="${child}"/>

            </li>

        </ul>

    </g:each>

</div>

<div data-category-move-action="${createLink(controller: 'category', action: 'move')}" style="visibility: hidden"></div>