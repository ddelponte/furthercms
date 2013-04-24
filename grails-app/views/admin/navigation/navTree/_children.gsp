<ul>

    <g:each in="${children}" var="child">

        <li id="category_${child?.id}">

            <a href="${g.createLink(controller: 'admin', action: 'edit', id: child?.id)}" class="${selectedNodeId == child?.id?.toString() ? 'jstree-clicked' : ''}">${child?.page?.title}</a>

            <fc:navTreeChildren category="${child}"/>

        </li>

    </g:each>

</ul>