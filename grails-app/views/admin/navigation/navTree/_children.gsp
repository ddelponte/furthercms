<ul>
    <g:each in="${children}" var="child">
        <li id="category_${child?.id}">
            <a href="${g.createLink(controller: 'admin', action: 'edit', id: child?.id)}">${child?.name}</a>
            <fc:navTreeChildren category="${child}"/>
        </li>
    </g:each>
</ul>