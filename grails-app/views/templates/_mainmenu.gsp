<div class="nav" role="navigation">
    <ul>
        <shiro:hasRole name="${ShiroRole.ROLE_ADMIN}">
            <li><g:link class="home" controller="user" action="list"><g:message code="section.users"/></g:link></li>
            <li><g:link class="home" controller="dealer" action="list"><g:message code="section.dealers"/></g:link></li>
        </shiro:hasRole>
        <li><g:link class="create" controller="project" action="list"><g:message code="section.projects"/></g:link></li>
    </ul>
</div>
