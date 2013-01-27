<r:require modules="navTree"/>

<div id="navTree">

    <g:each in="${children}" var="child">

        <ul>
            <li id="category_${child?.id}">
                <a href="${child?.urlKey}">${child?.name}</a>
                <fc:navTreeChildren category="${child}"/>
            </li>
        </ul>

    </g:each>

</div>
