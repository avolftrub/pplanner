<div class="content edit">
    <g:if test="${isNew}">
        <h1 class="newEntity"><g:message code="user.action.create.title"/></h1>
    </g:if>
    <g:else>
        <h1>
            <span class="createDate">${message(code: 'user.date.of.creation', args: [formatPlainDate(value: user.createDate)])}</span>
            <g:message code="user.action.edit.title"/>
        </h1>
    </g:else>


    <g:if test="${flash.message}">
        <h3>${flash.message}</h3>
    </g:if>

    <table class="entityEdit">
        <colgroup>
            <col width="30%">
            <col width="70%">
        </colgroup>

        <tr>
            <td><g:message code="user.firstName"/></td>
            <td>
                <g:textField name="firstName" value="${user.firstName}" class="${hasErrors(bean: user, field: 'firstName', 'errors')}"/>&nbsp;*
                <g:renderFieldErrors bean="${user}" field="firstName"/>
            </td>
        </tr>

        <tr>
            <td><g:message code="user.middleName"/></td>
            <td>
                <g:textField name="middleName" value="${user.middleName}" class="${hasErrors(bean: user, field: 'middleName', 'errors')}"/>
                <g:renderFieldErrors bean="${user}" field="middleName"/>
            </td>
        </tr>

        <tr>
            <td><g:message code="user.lastName"/></td>
            <td>
                <g:textField name="lastName" value="${user.lastName}" class="${hasErrors(bean: user, field: 'lastName', 'errors')}"/>&nbsp;*
                <g:renderFieldErrors bean="${user}" field="lastName"/>
            </td>
        </tr>

        <tr>
            <td><g:message code="user.username"/></td>
            <td>
                <g:textField autocomplete="off" name="username" value="${user.username}" class="${hasErrors(bean: user, field: 'username', 'errors')}"/>&nbsp;*
                <g:renderFieldErrors bean="${user}" field="username"/>
            </td>
        </tr>

        <g:if test="${!pwdChange}">
            <tr>
                <td colspan="2">
                    <a href="#" id="changePassword"><g:message code="user.change.password"/></a>
                </td>
            </tr>
        </g:if>
        <tr class="pwdBlock" style="display: ${pwdChange ? '' : 'none'};">
            <td><g:message code="user.password"/></td>
            <td>
                <g:passwordField autocomplete="off" name="password" value="${user.password}" class="w45 ${hasErrors(bean: user, field: 'password', 'errors')}"/>&nbsp;*
                <g:renderFieldErrors bean="${user}" field="password"/>
            </td>
        </tr>

        <tr class="pwdBlock" style="display: ${pwdChange ? '' : 'none'};">
            <td><g:message code="user.password2"/></td>
            <td>
                <g:passwordField autocomplete="off" name="password2" value="${user.password2}" class="w45 ${hasErrors(bean: user, field: 'password2', 'errors')}"/>&nbsp;*
                <g:renderFieldErrors bean="${user}" field="password"/>
            </td>
        </tr>
    </table>
</div>
