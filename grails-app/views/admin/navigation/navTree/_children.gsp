<ul>
    <g:each in="${children}" var="child">
        <li id="category_${child?.id}">
            <a href="${child?.urlKey}">${child?.name}</a>
            <fc:navTreeChildren category="${child}"/>
        </li>
    </g:each>
</ul>