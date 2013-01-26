<r:require modules="navTree"/>

<div id="navTree">

    <ul>
        <li id="category_${rootCategory?.id}">
            <a href="${rootCategory?.urlKey}">${rootCategory?.name}</a>
            <fc:navTreeChildren category="${rootCategory}"/>
        </li>
    </ul>

</div>
