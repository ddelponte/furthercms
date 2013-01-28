<r:require modules="navTree"/>

<div id="furtherCmsNavTree" ${style ? "style=\"$style\"" : ""} ${cssClass ? "class=\"$cssClass\"" : ""}>

    <g:each in="${children}" var="child">

        <ul>
            <li id="category_${child?.id}">
                <a href="${g.createLink(controller: 'admin', action: 'edit', id: child?.id)}">${child?.name}</a>
                <fc:navTreeChildren category="${child}"/>
            </li>
        </ul>

    </g:each>

</div>
