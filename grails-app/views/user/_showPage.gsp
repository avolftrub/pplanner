<div class="content show">
    <h1>
        <span class="createDate">${message(code: 'user.date.of.creation', args: [formatPlainDate(value: user.createDate)])}</span>
        ${user.name}
    </h1>

    <table class="entityShow">
        <colgroup>
            <col width="35%">
            <col width="65%">
        </colgroup>
        <tr>
            <td><g:message code="user.username"/></td>
            <td>${user.username}</td>
        </tr>
        <tr>
            <td><g:message code="user.role"/></td>
            <td>${message(code: 'ROLE.' + user.role.name)}</td>
        </tr>
        <tr>
            <td><g:message code="user.dealer"/></td>
            <td><g:link controller="dealer" action="show" id="${user.dealer?.id}">${user.dealer?.name}</g:link></td>
        </tr>
    </table>
</div>